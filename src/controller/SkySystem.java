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
import utility.Costanti;

public class SkySystem extends PApplet {

	private File root = new File(Costanti.PATH);
	Nodo nodoBase = new Nodo();
	private int  profonditaROOT;
	private int totFile = 0;
 
	public static void main(String[] args) {
		PApplet.main("controller.SkySystem");
	}

	public void settings(){
		size(Costanti.WIDTH, Costanti.HEIGHT);
	}

	public void setup(){
		background(0);
		this.profonditaROOT = this.splitPath(this.root.getPath()).size();
		this.sky();
		this.start(root);
		disegnaSatelliti(nodoBase,1);
		System.out.println(totFile);
	}
	public void draw() {}

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
			
			livello = this.calcolaLivello(f.getPath()); System.out.println("path: " + f.getPath() + "  livello = " + livello);
			
			if(f.isDirectory()) {
				raggio = Costanti.RAGGIO_ORBITA + 20;
			}
			else {
				raggio = Costanti.RAGGIO_ORBITA;
			}
			float x = satellite.getCentroNodo().getX() + ((raggio / livello) * PApplet.cos(angle));
			float y = satellite.getCentroNodo().getY() + ((raggio / livello) * PApplet.sin(angle));
			System.out.println(Costanti.RAGGIO_ORBITA/livello);
			totFile +=1;
			satelliteFiglio.setNodo(f);
			satelliteFiglio.setSottoCartelle(f.listFiles());
			Centro centroSatellite = new Centro(x,y);
			satelliteFiglio.setCentroNodo(centroSatellite);
			
			if (f.isDirectory()) {
			line(satellite.getCentroNodo().getX(), satellite.getCentroNodo().getY(), satelliteFiglio.getCentroNodo().getX(), satelliteFiglio.getCentroNodo().getY());
				disegnaSatelliti(satelliteFiglio,livello);
				calcolaCentriSatelliti(satelliteFiglio.getSottoCartelle(),satelliteFiglio);
			} else {
				line(satellite.getCentroNodo().getX(), satellite.getCentroNodo().getY(), satelliteFiglio.getCentroNodo().getX(), satelliteFiglio.getCentroNodo().getY());
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
		if(nodo.getNodo().isDirectory()) {
			text(nodo.getNodo().listFiles().length,nodo.getCentroNodo().getX(),nodo.getCentroNodo().getY());
		} else {
			text(prendiEstenzione(nodo.getNodo()),nodo.getCentroNodo().getX(),nodo.getCentroNodo().getY());			
		}
	}

	private String prendiEstenzione(File file) {
		return file.getName().substring(file.getName().lastIndexOf("."));
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
		fill(0);
		rect(0,Costanti.HEIGHT - 30,Costanti.WIDTH,Costanti.HEIGHT - 30);
	}

}
