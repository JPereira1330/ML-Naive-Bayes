package application;

import java.io.File;

import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class Ferramentas {

	public static Caracteristicas extraiCaracteristicas(File fileUrl){
		
		Color cor;
		Image img;
		double r,g,b;
		PixelReader pr;
		int imgW, imgH, ret;
		Caracteristicas carac;
		
		carac = new Caracteristicas();
		img = new Image(fileUrl.toURI().toString());
		pr = img.getPixelReader();
		
		carac.setImagemOriginal(Imgcodecs.imread(fileUrl.getPath()));
		carac.setImagemProcessada(carac.getImagemOriginal().clone());
		
		imgW = (int) img.getWidth();
		imgH = (int) img.getHeight();
		
		for (int i = 0; i < imgH; i++) {
			for (int j = 0; j < imgW; j++) {
				
				cor = pr.getColor(j, i);
			
				r = cor.getRed() 	* 255;
				g = cor.getGreen() 	* 255;
				b = cor.getBlue() 	* 255;
				
				// Testando Cabelo
				ret = verificaCabelo(r,g,b);
				if(ret != Caracteristicas.RET_FAIL) {
					carac.getImagemProcessada().put(i, j, aplicaModificacao(ret,carac, Caracteristicas.RET_CARACTERISTICA_CABELO));
				}
				
				// Testando Colar
				ret = verificaColar(r,g,b);
				if(ret != Caracteristicas.RET_FAIL) {
					carac.getImagemProcessada().put(i, j, aplicaModificacao(ret,carac, Caracteristicas.RET_CARACTERISTICA_COLAR));
				}
				
				// Testando Roupa
				ret = verificaRoupa(r,g,b);
				if(ret != Caracteristicas.RET_FAIL) {
					carac.getImagemProcessada().put(i, j, aplicaModificacao(ret,carac, Caracteristicas.RET_CARACTERISTICA_ROUPA));
				}
				
			}
		}
		
		// Nornalizar as caracteristicas
		carac.normalize(imgW, imgH);
		carac.discoverPlayer(fileUrl.getName());
			
		return carac;
	}
	
	public static File buscaImg() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new 
				   FileChooser.ExtensionFilter(
						   "Imagens", "*.jpg", "*.JPG", 
						   "*.png", "*.PNG", "*.gif", "*.GIF", 
						   "*.bmp", "*.BMP")); 	
		 fileChooser.setInitialDirectory(new File("C:\\Users\\Ny3x\\Documents\\Projetos\\Trabalho-01-MachineLearning\\src\\imagens"));
		 File imgSelec = fileChooser.showOpenDialog(null);
		 try {
			 if (imgSelec != null) {
			    return imgSelec;
			 }
		 } catch (Exception e) {
			e.printStackTrace();
		 }
		 return null;
	}
	
	private static double[] aplicaModificacao(int result, Caracteristicas caract, int idCaracter) {
		
		switch (result) {
		
			case Caracteristicas.RET_BOB:
				caract.addBob(idCaracter);
				return new double[] {255,0,0};

			case Caracteristicas.RET_SELMA:
				caract.addSelma(idCaracter);
				return new double[] {0,255,0};
				
			default:
				break;
		}
				
		return new double[] {255,255,255};
	}
	
	private static int verificaCabelo(double r, double g, double b) {
		
		if( ( r <= 200 && r >= 70 ) && ( g <= 51 && g >= 0 ) && ( b <= 65 && b >= 12 ) ) {
			return Caracteristicas.RET_BOB;
		}

		if( (r <= 162 && g <= 147 && b <= 202) && (r >= 101 && g >= 100 && b >= 170)) {
			return Caracteristicas.RET_SELMA;
		}
			
		return Caracteristicas.RET_FAIL;
	}
	
	private static int verificaColar(double r, double g, double b) {
		
		if( (r <= 230 && g <= 225 && b <= 195) && (r >= 200 && g >= 180 && b >= 130)){
			return Caracteristicas.RET_BOB;
		}

		if( (r <= 210 && g <= 130 && b <= 80) && (r >= 165 && g >= 100 && b >= 5)) {
			return Caracteristicas.RET_SELMA;
		}
			
		return Caracteristicas.RET_FAIL;
	}
	
	private static int verificaRoupa(double r, double g, double b) {
		
//		if( (r <= 230 && g <= 225 && b <= 195) && (r >= 200 && g >= 180 && b >= 130)){
//			return Caracteristicas.RET_BOB;
//		}

		if( (r <= 100 && g <= 140 && b <= 251) && (r >= 40 && g >= 110 && b >= 146)) {
			return Caracteristicas.RET_SELMA;
		}
			
		return Caracteristicas.RET_FAIL;
	}
	
}
