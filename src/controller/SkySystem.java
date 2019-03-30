package controller;

import java.io.File;

import model.Centro;
import model.Nodo;
import processing.core.PApplet;
import utility.Costanti;

public class SkySystem extends PApplet {

	private File root = new File(Costanti.PATH);
	
	public static void main(String[] args) {
		PApplet.main("controller.SkySystem");
	}


	public void settings(){
		size(Costanti.WIDTH, Costanti.HEIGHT);
	}

	public void setup(){
		background(0);
		this.sky();
		this.start(root);
	}
	public void draw() {
		
	}

	
	public void start(File rootFile) {
		Nodo nodoBase = new Nodo();
		Centro centroRoot = new Centro(Costanti.CENTRO_QUADRO_X,Costanti.CENTRO_QUADRO_Y);
		nodoBase.setCentroNodo(centroRoot);
		nodoBase.setNodo(rootFile);
		File[] sottoCartelle = nodoBase.getNodo().listFiles();
		nodoBase.setSottoCartelle(sottoCartelle);
		this.disegnaDirectory(nodoBase);
		calcolaCentriSatelliti(sottoCartelle,nodoBase);
	}

	
	public void calcolaCentriSatelliti(File[] sottoCartelle, Nodo nodoCorrente) {
		float periodo = TWO_PI/sottoCartelle.length;
		float a = 0;
		for(int i = 0; i < sottoCartelle.length; i++ ) {
			a = a + periodo;
			float angle = PApplet.radians(a);
			float x = nodoCorrente.getCentroNodo().getX() + ((Costanti.RAGGIO_ORBITA) * PApplet.sin(angle));
			float y = nodoCorrente.getCentroNodo().getY() + ((Costanti.RAGGIO_ORBITA)) * PApplet.cos(angle);
			Nodo satellite = new Nodo();
			Centro centroSatellite = new Centro(x,y);
			satellite.setCentroNodo(centroSatellite);
			satellite.setNodo(sottoCartelle[i]);
			satellite.setSottoCartelle(sottoCartelle[i].listFiles());
			if (sottoCartelle[i].isDirectory()) {
				disegnaDirectory(satellite);
				calcolaCentriSatelliti(satellite.getSottoCartelle(),satellite);
			} 
		}
	}
	



	public void disegnaDirectory(Nodo nodo) {
		noFill();
		stroke(255);
		ellipse(nodo.getCentroNodo().getX(), nodo.getCentroNodo().getY(), Costanti.RAGGIO_ROOT, Costanti.RAGGIO_ROOT);
		stroke(250,12,129);
		fill(0);
		ellipse(nodo.getCentroNodo().getX(), nodo.getCentroNodo().getY(), Costanti.RAGGIO_ROOT-10, Costanti.RAGGIO_ROOT-10);
		textAlign(CENTER,CENTER);
		textSize(13);
		fill(255);
		text(nodo.getNodo().listFiles().length,nodo.getCentroNodo().getX(),nodo.getCentroNodo().getY());
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
