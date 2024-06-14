 package br.com.joao.automatizado.automatizado.view;

 
 import java.io.File;


 

import javafx.application.Platform;
 import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class FileChooserTela  {
	File file;
	public void buscarArquivo( ) {
		Platform.runLater(()->{
				FileChooser fileChooser=new FileChooser();
				fileChooser.setTitle("Selecione o excel");
				fileChooser.getExtensionFilters().add(new ExtensionFilter("XLSX","*.xlsx"));
				file=fileChooser.showOpenDialog(null);
	 		});
	 		//componente.add(painel);
	         
		
	 
		 
		/*painel.setLayout(new GridLayout());
		setFileFilterXLSX();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setPreferredSize(new Dimension(350, 350));*/
		
 
	}

	/*
	 * public void setFileFilter(String descricao, String...extensoes) { filtro=new
	 * FileNameExtensionFilter(descricao,extensoes);
	 * fileChooser.setFileFilter(filtro);
	 * 
	 * }
	 */
	private void setFileFilterXLSX() {
	
	}

	/*public File getChooserFile() {
	}*/

}
