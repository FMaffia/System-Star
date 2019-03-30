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
		plottaSottoCartelle(sottoCartelle,nodoBase);
	}
	
	private void plottaSottoCartelle(File[] sottoCartelle, Nodo nodoBase) {
		for (int x = 0; x < sottoCartelle.length; x++) {
			Nodo satellite = new Nodo();
			//Centro centroSatellite = new Centro(,Costanti.CENTRO_QUADRO_Y);

		}
	}


	public void disegnaDirectory(Nodo nodo) {
		noFill();
		stroke(255);
		ellipse(nodo.getCentroNodo().getX(), nodo.getCentroNodo().getY(), Costanti.DIAMETRO_NODO_ROOT, Costanti.DIAMETRO_NODO_ROOT);
		stroke(250,12,129);
		fill(0);
		ellipse(nodo.getCentroNodo().getX(), nodo.getCentroNodo().getY(), Costanti.DIAMETRO_NODO_ROOT-10, Costanti.DIAMETRO_NODO_ROOT-10);
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
