package application;

import org.opencv.core.Mat;

public class Caracteristicas {

	public static final int RET_FAIL 	= 0;
	public static final int RET_BOB 	= 1;
	public static final int RET_SELMA 	= 2;
	
	public static final int RET_CARACTERISTICA_CABELO = 1;
	public static final int RET_CARACTERISTICA_COLAR = 2;
	public static final String ARCHIVE_ARFF_NAME = "caracteristicas.arff";
	
	private double cabeloBob;
	private double cabeloSelma;
	private double colarBob;
	private double colarSelma;
	
	
	private double personagem;
	private String personagemName;
	private String personagemRespotaIA;
	
	private Mat imagemOriginal;
	private Mat imagemProcessada; 
	
	public void reset() {
		this.cabeloBob = 0;
		this.cabeloSelma = 0;
		this.personagem = 0;
		this.personagemName = "";
	}
	
	public void normalize(int w, int h) {
		
		int porcentagem;
		
		porcentagem = w * h;
		
		this.cabeloBob 	 = (this.cabeloBob   / porcentagem) * 100;
		this.cabeloSelma = (this.cabeloSelma / porcentagem) * 100;
		this.colarBob 	 = (this.colarBob 	 / porcentagem) * 100;
		this.colarSelma	 = (this.colarSelma  / porcentagem) * 100;
		
	}
	
	public void discoverPlayer(String file) {
		
		String[] nome = file.split(" ");
		
		switch(nome[0].toUpperCase()) {
		
			case "BOB":
				setPersonagem(RET_BOB);
				setPersonagemName("Bob");
				break;
				
			case "SELMA":
				setPersonagem(RET_SELMA);
				setPersonagemName("Selma");
				break;
			
			default:
				setPersonagem(RET_FAIL);
				break;
		
		}
		
	}
	
	public void addBob(int caracteristica) {
		
		switch (caracteristica) {
		
			case RET_CARACTERISTICA_CABELO:
				cabeloBob++;
				break;
				
			case RET_CARACTERISTICA_COLAR:
				colarBob++;
				break;

		}
		
	}

	public void addSelma(int caracteristica) {
		
		switch (caracteristica) {
		
			case RET_CARACTERISTICA_CABELO:
				cabeloSelma++;
				break;
				
			case RET_CARACTERISTICA_COLAR:
				colarSelma++;
				break;

		}
	}
	
	public double getCabeloBob() {
		return this.cabeloBob;
	}
	
	public void setCabeloBob(double valor) {
		this.cabeloBob = valor;
	}
	
	public double getCabeloSelma() {
		return this.cabeloSelma;
	}
	
	public void setCabeloSelma(double valor) {
		this.cabeloSelma = valor;
	}
	
	public double getPersonagem() {
		return this.personagem;
	}
	
	public void setPersonagem(double idPerson) {
		this.personagem = idPerson;
	}

	public String getPersonagemName() {
		return personagemName;
	}

	public void setPersonagemName(String personagemName) {
		this.personagemName = personagemName;
	}

	public Mat getImagemOriginal() {
		return imagemOriginal;
	}

	public void setImagemOriginal(Mat imagemOriginal) {
		this.imagemOriginal = imagemOriginal;
	}

	public Mat getImagemProcessada() {
		return imagemProcessada;
	}

	public void setImagemProcessada(Mat imagemProcessada) {
		this.imagemProcessada = imagemProcessada;
	}

	public String getPersonagemRespotaIA() {
		return personagemRespotaIA;
	}

	public void setPersonagemRespotaIA(String personagemRespotaIA) {
		this.personagemRespotaIA = personagemRespotaIA;
	}

	public double getColarBob() {
		return colarBob;
	}

	public void setColarBob(double colarBob) {
		this.colarBob = colarBob;
	}

	public double getColarSelma() {
		return colarSelma;
	}

	public void setColarSelma(double colarSelma) {
		this.colarSelma = colarSelma;
	}
	
}
