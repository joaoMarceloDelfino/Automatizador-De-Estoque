package br.com.joao.automatizado.automatizado.view;

import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class PainelPrincipal {
	 VBox painel;
	 Button botaoEscolherArquivo=new Button("Escolha o arquivo");
	 Button botaoEnviar=new Button("Enviar o arquivo");
	 public PainelPrincipal() {
		 InicializarPainel();
	 }
	 public void InicializarPainel() {
			botaoEscolherArquivo.setPrefSize(150, 450);
			painel=new VBox(botaoEscolherArquivo,botaoEnviar);
	 	}
	 
	 public void setBotaoEscolherListener(EventHandler<ActionEvent> acao) {
		 botaoEscolherArquivo.setOnAction(acao);
	 }
	 
	 public void setBotaoEnviarListener(EventHandler<ActionEvent> acao) {
		 botaoEnviar.setOnAction(acao);
	 }

	public VBox getPainel() {
		return painel;
	}

	public void setPainel(VBox painel) {
		this.painel = painel;
	}
	
	public Button getBotaoEscolherArquivo() {
		return botaoEscolherArquivo;
	}



	public void setBotaoEscolherArquivo(Button botaoEscolherArquivo) {
		this.botaoEscolherArquivo = botaoEscolherArquivo;
	}



	public Button getBotaoEnviar() {
		return botaoEnviar;
	}



	public void setBotaoEnviar(Button botaoEnviar) {
		this.botaoEnviar = botaoEnviar;
	}



	 
	

}
