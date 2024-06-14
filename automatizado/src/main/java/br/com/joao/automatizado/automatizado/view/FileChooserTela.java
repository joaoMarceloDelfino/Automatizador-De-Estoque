 package br.com.joao.automatizado.automatizado.view;

 
 import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.application.Platform;
 import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import java.nio.file.*;

public class FileChooserTela  {
	File fileEscolhido;
	FileChooser fileChooser;
	public void buscarArquivo( ) {
		 
				fileChooser=new FileChooser();
				fileChooser.setTitle("Selecione o excel");
				fileChooser.getExtensionFilters().add(new ExtensionFilter("XLSX","*.xlsx"));
				fileEscolhido=fileChooser.showOpenDialog(null);
	 		
	 	
	}
	public void salvarArquivo() {
		if(fileEscolhido!=null) {
			 
			File fileSalvo=fileChooser.showSaveDialog(null);
			if(fileSalvo!=null) {
 				try {
					Files.copy(Paths.get(fileEscolhido.getAbsolutePath()) ,Paths.get(fileSalvo.getAbsolutePath()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public File getFileEscolhido() {
		return fileEscolhido;
	}
	
}
