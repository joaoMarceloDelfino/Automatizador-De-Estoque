package br.com.joao.automatizado.automatizado.view;

import br.com.joao.automatizado.automatizado.controll.PainelControll;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TelaPrincipal extends Application {
PainelPrincipal painelPrincipal=new PainelPrincipal();
	public void start(Stage primaryStage) throws Exception {
  	 PainelControll painelControlador=new PainelControll(painelPrincipal);
  	 BorderPane mainPainel=new BorderPane();
     Region painelCentral = new StackPane(painelPrincipal.getPainel());
     mainPainel.setCenter(painelCentral);
   	 Scene cena = new Scene(mainPainel, 1000, 350);
     painelCentral.prefWidthProperty().bind(mainPainel.widthProperty());
    painelCentral.prefHeightProperty().bind(mainPainel.heightProperty());
   // BorderPane.setAlignment(painelCentral, Pos.CENTER);

    


 
     
		primaryStage.setTitle("Automatizador de excel");
		primaryStage.setScene(cena);
		primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}

	

}
