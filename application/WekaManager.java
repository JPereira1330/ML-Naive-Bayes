package application;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import weka.classifiers.bayes.NaiveBayes;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;


public class WekaManager {

	public static void extrair() {
		
		int len;
		List<Caracteristicas> caracteristica = new ArrayList<>();
		
		File diretorio = new File("src\\imagens");
	    File[] arquivos = diretorio.listFiles();
	    
		String exportacao = "@relation caracteristicas\n\n";
			exportacao += "@attribute cabelo_bob real\n";
			exportacao += "@attribute cabelo_selma real\n";
			exportacao += "@attribute colar_bob real\n";
			exportacao += "@attribute colar_selma real\n";
			exportacao += "@attribute roupa_bob real\n";
			exportacao += "@attribute roupa_selma real\n";
			exportacao += "@attribute classe {Bob, Selma}\n\n";
			exportacao += "@data\n";
	    
	    for (File imagem : arquivos) {
	    
	    	caracteristica.add(Ferramentas.extraiCaracteristicas(imagem));
	    	
	    	len = caracteristica.size() - 1;
	    	
	    	System.out.println(" ["+caracteristica.get(len).getPersonagemName()+"] ");
	    	System.out.println(" \t Bob	Cabelo: "+caracteristica.get(len).getCabeloBob());
	    	System.out.println(" \t Selma Cabelo: "+caracteristica.get(len).getCabeloSelma());
	    	System.out.println(" \t Bob Colar: "+caracteristica.get(len).getColarBob());
	    	System.out.println(" \t Selma Colar: "+caracteristica.get(len).getColarSelma());
	    	System.out.println(" \t Bob Roupa: "+caracteristica.get(len).getRoupaBob());
	    	System.out.println(" \t Selma Roupa: "+caracteristica.get(len).getRoupaSelma());
	    	System.out.println();
	    	
        	exportacao += caracteristica.get(len).getCabeloBob() + "," 
                    + caracteristica.get(len).getCabeloSelma() + "," 
            		+ caracteristica.get(len).getColarBob() + ","
            		+ caracteristica.get(len).getColarSelma() + ","
            		+ caracteristica.get(len).getRoupaBob() + ","
            		+ caracteristica.get(len).getRoupaSelma() + ","
                    + caracteristica.get(len).getPersonagemName() + "\n";
	    	
	    }
	    
        try {
        	File arquivo = new File(Caracteristicas.ARCHIVE_ARFF_NAME);
        	FileOutputStream f = new FileOutputStream(arquivo);
        	f.write(exportacao.getBytes());
        	f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}

	public static Caracteristicas naiveBayes(Caracteristicas carac) {
		
		double[] retorno = {0,0};
		
		try {
			
			DataSource ds = new DataSource(Caracteristicas.ARCHIVE_ARFF_NAME);
			
			Instances instancias = ds.getDataSet();
			instancias.setClassIndex(instancias.numAttributes() - 1);
			
			NaiveBayes nb = new NaiveBayes();
			nb.buildClassifier(instancias);
			
			Instance novo = new DenseInstance(instancias.numAttributes());
			novo.setDataset(instancias);
			novo.setValue(0, carac.getCabeloBob());
			novo.setValue(1, carac.getCabeloSelma());
			novo.setValue(2, carac.getColarBob());
			novo.setValue(3, carac.getColarSelma());
			novo.setValue(4, carac.getRoupaBob());
			novo.setValue(5, carac.getRoupaSelma());
			
			retorno = nb.distributionForInstance(novo);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		carac.setPersonagemRespotaIA((retorno[0] > retorno[1] ? "Bob" : "Selma"));
		
		return carac;
	}
	
	
}
