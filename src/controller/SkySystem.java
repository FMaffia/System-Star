package controller;

import java.io.File;
import java.io.IOException;

import model.Centro;
import model.Nodo;
import processing.core.PApplet;
import utility.Costanti;

public class SkySystem extends PApplet {

	private File root = new File(Costanti.PATH);
	Nodo nodoBase = new Nodo();
	private int livello = 0;


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
		disegnaSatelliti(nodoBase);

	}
	public void draw() {
	}

	public void start(File rootFile) {
		Centro centroRoot = new Centro(Costanti.CENTRO_QUADRO_X,Costanti.CENTRO_QUADRO_Y);
		nodoBase.setCentroNodo(centroRoot);
		nodoBase.setNodo(rootFile);
		File[] sottoCartelle = nodoBase.getNodo().listFiles();
		nodoBase.setSottoCartelle(sottoCartelle);
		disegnaSatelliti(nodoBase);
		calcolaCentriSatelliti(sottoCartelle,nodoBase);
	}

	public void calcolaCentriSatelliti(File[] sottoCartelle, Nodo satellite) {
		float periodo = 0;
		float a = 0;
		float raggio = 0;
		if(sottoCartelle.length > 0) {
			periodo = 360/sottoCartelle.length;
		}
		for(int i = 0; i < sottoCartelle.length; i++ ) {
			//			if(prendiEstenzione(sottoCartelle[i]).equals(".DS_Store") == true) {
			//				satelliteFiglio.setNodo(null);
			//			} else {
			Nodo satelliteFiglio = new Nodo();
			float angle = PApplet.radians(a);
			if(sottoCartelle[i].isDirectory()) {
				raggio = Costanti.RAGGIO_ORBITA + 100;
			}
			else {
				raggio = Costanti.RAGGIO_ORBITA;
			}
			float x = satellite.getCentroNodo().getX() + ((raggio) * PApplet.cos(angle));
			float y = satellite.getCentroNodo().getY() + ((raggio) * PApplet.sin(angle));
			satelliteFiglio.setNodo(sottoCartelle[i]);
			satelliteFiglio.setSottoCartelle(sottoCartelle[i].listFiles());
			Centro centroSatellite = new Centro(x,y);
			satelliteFiglio.setCentroNodo(centroSatellite);
			if (sottoCartelle[i].isDirectory()) {
				disegnaLinea(satellite, satelliteFiglio);
				disegnaSatelliti(satelliteFiglio);
				calcolaCentriSatelliti(satelliteFiglio.getSottoCartelle(),satelliteFiglio);
			} else {
				disegnaLinea(satellite, satelliteFiglio);
				disegnaSatelliti(satelliteFiglio);
			}
			a = a + periodo;
		}
	}

	public void disegnaSatelliti(Nodo nodo) {
		noFill();
		stroke(255);
		ellipse(nodo.getCentroNodo().getX(), nodo.getCentroNodo().getY(), Costanti.RAGGIO_ROOT, Costanti.RAGGIO_ROOT);
		stroke(250,12,129);
		fill(0);
		ellipse(nodo.getCentroNodo().getX(), nodo.getCentroNodo().getY(), Costanti.RAGGIO_ROOT-10, Costanti.RAGGIO_ROOT-10);
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
