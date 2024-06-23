package br.com.joao.automatizado.automatizado.view;

import br.com.joao.automatizado.automatizado.controll.PainelControll;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TelaPrincipal extends Application {
PainelPrincipal painelPrincipal=new PainelPrincipal();
	public void start(Stage primaryStage) throws Exception {
 		 
 	// painel=new VBox(20,botaoEscolherArquivo,botaoEnviar);
 	 PainelControll painelControlador=new PainelControll(painelPrincipal);
  	 Scene cena = new Scene(painelPrincipal.getPainel(), 500, 350);
     
		primaryStage.setTitle("Automatizador de excel");
		primaryStage.setScene(cena);
		primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}

	

}
