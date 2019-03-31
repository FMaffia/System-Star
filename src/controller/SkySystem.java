package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import model.Centro;
import model.Nodo;
import processing.core.PApplet;
import utility.Costanti;

public class SkySystem extends PApplet {

	private File root = new File(Costanti.PATH);
	Nodo nodoBase = new Nodo();
	private int  profonditaROOT;


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
				raggio = Costanti.RAGGIO + 100;
			}
			else {
				raggio = Costanti.RAGGIO + 50;
			}
			float x = satellite.getCentroNodo().getX() + ((raggio/livello) * PApplet.cos(angle));
			float y = satellite.getCentroNodo().getY() + ((raggio/livello) * PApplet.sin(angle));
			
			satelliteFiglio.setNodo(f);
			satelliteFiglio.setSottoCartelle(f.listFiles());
			Centro centroSatellite = new Centro(x,y);
			satelliteFiglio.setCentroNodo(centroSatellite);
			if (f.isDirectory()) {
				disegnaLinea(satellite, satelliteFiglio);
				disegnaSatelliti(satelliteFiglio,livello);
				calcolaCentriSatelliti(satelliteFiglio.getSottoCartelle(),satelliteFiglio);
			} else {
				disegnaLinea(satellite, satelliteFiglio);
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
		stroke(255);
		ellipse(nodo.getCentroNodo().getX(), nodo.getCentroNodo().getY(), Costanti.RAGGIO/livello, Costanti.RAGGIO/livello);
		stroke(250,12,129);
		fill(0);
		ellipse(nodo.getCentroNodo().getX(), nodo.getCentroNodo().getY(), (Costanti.RAGGIO-10)/livello, (Costanti.RAGGIO-10)/livello);
		textAlign(CENTER,CENTER);
		textSize(13);
		fill(255);
		if(nodo.getNodo().isDirectory()) {
			text(nodo.getNodo().listFiles().length,nodo.getCentroNodo().getX(),nodo.getCentroNodo().getY());
		} else {
			text(prendiEstenzione(nodo.getNodo()),nodo.getCentroNodo().getX(),nodo.getCentroNodo().getY());			
		}
	}

	private String prendiEstenzione(File file) {
		return file.getName().substring(file.getName().lastIndexOf("."));
	}

	public void disegnaLinea(Nodo satellitePadre, Nodo satelliteFiglio) {
		stroke(255);
		strokeWeight(1);
		line(satellitePadre.getCentroNodo().getX(), satellitePadre.getCentroNodo().getY(), satelliteFiglio.getCentroNodo().getX(), satelliteFiglio.getCentroNodo().getY());
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

}
