package application;

import java.io.File;

import application.Ferramentas;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AppController {
	
	File arq;

	@FXML private ImageView imageViewOriginal;
	@FXML private ImageView ImageViewProcessada;
	
	@FXML private Label lblRepostaIA;
	@FXML private Label lblRespotaReal;
	
	@FXML
	public void selecionaImagem() {
		
		arq = Ferramentas.buscaImg();
		if(arq != null) {
			Image img = new Image(arq.toURI().toString());
			imageViewOriginal.setImage(img);
			imageViewOriginal.setFitWidth(img.getWidth());
			imageViewOriginal.setFitHeight(img.getHeight());
		}
		
	}	
	
	@FXML
	public void analisaBanco() {
		WekaManager.extrair();
	}
	
	@FXML
	void testaImagemUnica() {
		
		Caracteristicas carac;
		
		if(arq == null)
			return;

		carac = Ferramentas.extraiCaracteristicas(arq);

		Image img = ImageManager.mat2Image(carac.getImagemProcessada());
		ImageViewProcessada.setImage(img);
		ImageViewProcessada.setFitWidth(img.getWidth());
		ImageViewProcessada.setFitHeight(img.getHeight());

	}
	
	@FXML
	void testaImagemMassa() {
		
		Caracteristicas carac;
		
		if(arq == null)
			return;

		carac = Ferramentas.extraiCaracteristicas(arq);

		Image img = ImageManager.mat2Image(carac.getImagemProcessada());
		ImageViewProcessada.setImage(img);
		ImageViewProcessada.setFitWidth(img.getWidth());
		ImageViewProcessada.setFitHeight(img.getHeight());

		carac = WekaManager.naiveBayes(carac);

		lblRepostaIA.setText(carac.getPersonagemRespotaIA());
		lblRespotaReal.setText(carac.getPersonagemName());

	}
	
}
