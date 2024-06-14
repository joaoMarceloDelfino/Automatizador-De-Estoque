package br.com.joao.automatizado.automatizado.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TelaPrincipal extends Application {

	public void start(Stage primaryStage) throws Exception {
		Pane painel = new Pane();
		Scene cena = new Scene(painel, 500, 350);
		primaryStage.setTitle("Automatizador de excel");
		primaryStage.setScene(cena);
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);

	}

}
