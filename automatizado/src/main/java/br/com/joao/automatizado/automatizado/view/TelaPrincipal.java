package br.com.joao.automatizado.automatizado.view;

import br.com.joao.automatizado.automatizado.controll.PainelControll;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TelaPrincipal extends Application {
	PainelPrincipal painelPrincipal = new PainelPrincipal();
	VBox painel = painelPrincipal.getPainel();
 
	public void start(Stage primaryStage) throws Exception {
  	 PainelControll painelControlador=new PainelControll(painelPrincipal);
  
   
    	 Scene cena = new Scene(painel, 700, 325);
     
		primaryStage.setTitle("Automatizador de estoque");
 		primaryStage.setScene(cena);
		primaryStage.show();
		primaryStage.setMaximized(false);	
		primaryStage.setResizable(false);
	}

	 

	public static void main(String[] args) {
		launch(args);
	}

}
