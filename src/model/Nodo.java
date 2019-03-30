package model;

import java.io.File;

public class Nodo {
	String nomeFile;
	Centro centroNodo;
	File Nodo;
	File[] sottoCartelle;
	
	public Nodo() {}
	
	public Nodo(String nomeFile, Centro centroNodo, File nodo, File[] sottoCartelle) {
		this.nomeFile = nomeFile;
		this.centroNodo = centroNodo;
		Nodo = nodo;
		this.sottoCartelle = sottoCartelle;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public Centro getCentroNodo() {
		return centroNodo;
	}
	public void setCentroNodo(Centro centroNodo) {
		this.centroNodo = centroNodo;
	}
	public File getNodo() {
		return Nodo;
	}
	public void setNodo(File nodo) {
		Nodo = nodo;
	}
	public File[] getSottoCartelle() {
		return sottoCartelle;
	}
	public void setSottoCartelle(File[] sottoCartelle) {
		this.sottoCartelle = sottoCartelle;
	}




}
