package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.sun.org.apache.bcel.internal.classfile.ConstantInteger;

import model.Centro;
import model.Nodo;
import processing.core.PApplet;
import processing.core.PFont;
import processing.event.MouseEvent;
import utility.Costanti;

public class SkySystem extends PApplet {

	private File root = new File(Costanti.PATH);
	Nodo nodoBase = new Nodo();
	private int  profonditaROOT;
	private int totFile = 0;
	PFont font;


	public static void main(String[] args) {
		PApplet.main("controller.SkySystem");
	}

	public void settings(){
		size(Costanti.WIDTH, Costanti.HEIGHT);
		smooth();

	}

	public void setup(){
		background(0);
		font = createFont("AlegreyaSans-Thin-20.vlw", 13);
		this.profonditaROOT = this.splitPath(this.root.getPath()).size();
		this.sky();
		this.start(root);
		titolo();
		disegnaSatelliti(nodoBase,1);
	}
	public void draw() {
		if(mousePressed == true && mouseX > Costanti.WIDTH - 50 && mouseX < Costanti.WIDTH && mouseY > Costanti.HEIGHT - 30 && mouseY < Costanti.HEIGHT) {
			saveImagine();
			System.out.println("salva");
		}
		
	}

	public void start(File rootFile) {
		Centro centroRoot = new Centro(Costanti.CENTRO_QUADRO_X,Costanti.CENTRO_QUADRO_Y);
		nodoBase.setCentroNodo(centroRoot);
		nodoBase.setNodo(rootFile);
		File[] sottoCartelle = nodoBase.getNodo().listFiles();
		nodoBase.setSottoCartelle(sottoCartelle);
		disegnaSatelliti(nodoBase,profonditaROOT);
		calcolaCentriSatelliti(sottoCartelle,nodoBase);
	}

	public void calcolaCentriSatelliti(File[] sottoCartelle, Nodo satellite) {
		float periodo = 0;
		float a = 0;
		float raggio = 0;
		int livello = 0;
		if(sottoCartelle.length > 0) {
			periodo = 360/sottoCartelle.length;
		}

		List<File> listaFiles = this.filtraFile(sottoCartelle);
		for(File f : listaFiles) {
			Nodo satelliteFiglio = new Nodo();
			float angle = PApplet.radians(a);

			livello = this.calcolaLivello(f.getPath());
			//System.out.println("path: " + f.getPath() + "  livello = " + livello);

			if(f.isDirectory()) {
				raggio = Costanti.RAGGIO_ORBITA + 100;
			}
			else {
				raggio = Costanti.RAGGIO_ORBITA+50;
			}
			float x = satellite.getCentroNodo().getX() + ((raggio / livello) * PApplet.cos(angle+PI/2));
			float y = satellite.getCentroNodo().getY() + ((raggio / livello) * PApplet.sin(angle+PI/2));
			
			float lineaX = satellite.getCentroNodo().getX() + (Costanti.RAGGIO - livello*10)/2 * PApplet.cos(angle+PI/2);
			float lineaY = satellite.getCentroNodo().getY() + (Costanti.RAGGIO - livello*10)/2 * PApplet.sin(angle+PI/2);

			totFile +=1;
			satelliteFiglio.setNodo(f);
			satelliteFiglio.setSottoCartelle(f.listFiles());
			Centro centroSatellite = new Centro(x,y);
			satelliteFiglio.setCentroNodo(centroSatellite);

			if (f.isDirectory()) {
				strokeWeight(1);
				line(lineaX,lineaY, satelliteFiglio.getCentroNodo().getX(), satelliteFiglio.getCentroNodo().getY());
				disegnaSatelliti(satelliteFiglio,livello);
				calcolaCentriSatelliti(satelliteFiglio.getSottoCartelle(),satelliteFiglio);
			} else {
				line(lineaX,lineaY, satelliteFiglio.getCentroNodo().getX(), satelliteFiglio.getCentroNodo().getY());
				disegnaSatelliti(satelliteFiglio,livello);
			}
			a = a + periodo;
		}
	}

	private int calcolaLivello(String pathString) {
		int profonditaTOT = this.splitPath(pathString).size();
		int profondita = profonditaTOT - this.profonditaROOT;
		return profondita;
	}

	public List<String> splitPath(String pathString){
		Path path = Paths.get(pathString);
		return Arrays.asList(StreamSupport.stream(path.spliterator(), false).map(Path::toString)
				.toArray(String[]::new));
	}
	public List<File> filtraFile(File[] sottoCartelle)
	{
		List<File> lista = Arrays.asList(sottoCartelle);
		return lista.stream().filter(f -> !f.getName().toUpperCase().contains(("DS_STORE"))).collect(Collectors.toList());
	}

	public void disegnaSatelliti(Nodo nodo, int livello) {
		noFill();
		stroke(255,255- 30*livello);
		ellipse(nodo.getCentroNodo().getX(), nodo.getCentroNodo().getY(), Costanti.RAGGIO - livello*10, Costanti.RAGGIO-livello*10);
		stroke(250,12,129,255 - 20*livello);
		fill(0);
		ellipse(nodo.getCentroNodo().getX(), nodo.getCentroNodo().getY(), (Costanti.RAGGIO-10)-livello*10, (Costanti.RAGGIO-10)-livello*10);
		textAlign(CENTER,CENTER);
		textSize(13);
		fill(255, 255 - 20*livello);
		if(Costanti.CLEAN == false) {
			if(nodo.getNodo().isDirectory()) {
				text(nodo.getNodo().listFiles().length,nodo.getCentroNodo().getX(),nodo.getCentroNodo().getY());
			} else {
				text(prendiEstenzione(nodo.getNodo()),nodo.getCentroNodo().getX(),nodo.getCentroNodo().getY());			
			}
		}
	}

	private String prendiEstenzione(File file) {
		String estenzione = "";
		if(file.getName().contains(".")) {
			estenzione = file.getName().substring(file.getName().lastIndexOf("."));
		}
		return estenzione;
	}


	public void sky() {
		for(int x = 0; x < 300; x++) {
			if(x%2 == 0) {
				stroke(250,12,129);
			} else {
				stroke(255);
			}
			strokeWeight(random(1,2));
			point(random(0,Costanti.WIDTH), random (0,Costanti.HEIGHT));
		}
	}
	public void titolo() {
		stroke(255);
		strokeWeight(1);
		line(0,Costanti.HEIGHT-30,Costanti.WIDTH, Costanti.HEIGHT -30);
		noStroke();
		fill(0);
		rect(0,Costanti.HEIGHT - 30,Costanti.WIDTH,30);
		fill(255);
		textAlign(CENTER);
		textFont(font);
		text("System Star: " + totFile + " files - 31/03/2019" ,0,Costanti.HEIGHT - 20,Costanti.WIDTH,Costanti.HEIGHT - 20);
		fill(250,12,129,255);
		rect(Costanti.WIDTH - 50,Costanti.HEIGHT - 30,Costanti.WIDTH,30);
		fill(255);
		text("SAVE",Costanti.WIDTH - 25,Costanti.HEIGHT-10 );
	}
	
	public void saveImagine() {
		save("SystemStar.jpg");
	}
}
