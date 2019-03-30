package controller;

import java.io.File;

import model.Centro;
import model.Nodo;
import processing.core.PApplet;
import utility.Costanti;

public class SkySystem extends PApplet {

	private File root = new File(Costanti.PATH);
	private Nodo nodoBase;
	
	public static void main(String[] args) {
		PApplet.main("controller.SkySystem");
	}


	public void settings(){
		size(Costanti.WIDTH, Costanti.HEIGHT);
	}

	public void setup(){
		background(0);
		sky();
		nodoBase = new Nodo();
		Centro centroRoot = new Centro(Costanti.CENTRO_QUADRO_X,Costanti.CENTRO_QUADRO_Y);
		nodoBase.setCentroNodo(centroRoot);
		nodoBase.setNodo(root);
		System.out.println(nodoBase.getNodo().listFiles().length);
		disegnaDirectory(nodoBase);
	}
	public void draw() {
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
