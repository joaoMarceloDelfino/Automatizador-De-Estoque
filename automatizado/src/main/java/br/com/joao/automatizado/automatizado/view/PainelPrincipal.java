package br.com.joao.automatizado.automatizado.view;

 
import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PainelPrincipal {
	private Label arquivoLabel = new Label("Escolha o Arquivo:");
	private TextField caminhoArquivoField = new TextField();
	private Button botaoEscolherArquivo = new Button("Escolha o arquivo");
	private Button botaoEnviar = new Button("Enviar o arquivo");
	private HBox arquivoBox;
	private ComboBox<String> opcaoDeProcura= new ComboBox<String>();
	private Label opcaoDeProcuraLabel=new Label("Selecione a opção de procura das colunas:");
	private HBox opcaoDeProcuraBox;
 	private Label quantidadeLabel=new Label("Insira o nome da coluna 'quantidade':");
	private TextField quantidadeField= new TextField("Quantidade em estoque");
	private HBox quantidadeBox;
	private TextField referenciaField= new TextField("Código do item");
	private Label referenciaLabel=new Label("Insira o nome da coluna 'referência':");;
	private HBox referenciaBox;
	private Label terceirosCheckBoxLabel=new Label("Selecione se a empresa possui estoque em poder de terceiros:");
	private CheckBox terceirosCheckBox=new CheckBox();
	private Label terceirosLabel=new Label("Insira o nome da coluna 'quantidade em poder de terceiros':");
	private TextField terceirosField=new TextField();
	private HBox terceirosBox;
	private HBox terceirosCheckBoxBox;
 	private VBox painel;
 	private Label dataLabel;
 	private DatePicker botaoData;
 	private HBox dataBox;
 	private Button criarTxtButton=new Button("criar txt");	
	
 	public PainelPrincipal() {
		inicializarPainel();
	}

	private void inicializarPainel() {
 		caminhoArquivoField.setDisable(true);
 		terceirosField.setDisable(true);
		caminhoArquivoField.setPrefSize(300,10);
		opcaoDeProcura.getItems().addAll("Por nome", "Por numero");
		opcaoDeProcura.setValue("Por nome");
		terceirosCheckBoxBox=new HBox(10,terceirosCheckBoxLabel,terceirosCheckBox);
		terceirosBox=new HBox(10,terceirosLabel,terceirosField);
		quantidadeBox=new HBox(10,quantidadeLabel,quantidadeField);
		referenciaBox=new HBox(10,referenciaLabel,referenciaField);
		arquivoBox = new HBox(10,arquivoLabel, caminhoArquivoField, botaoEscolherArquivo);
		opcaoDeProcuraBox=new HBox(10,opcaoDeProcuraLabel,opcaoDeProcura);
 		painel = new VBox(10,opcaoDeProcuraBox,referenciaBox,quantidadeBox,terceirosCheckBoxBox,terceirosBox,arquivoBox,botaoEnviar);
 	
 		

	}
	 

	public void setBotaoEscolherListener(EventHandler<ActionEvent> acao) {
		botaoEscolherArquivo.setOnAction(acao);
	}
	public void setCheckBoxListener(EventHandler<ActionEvent> acao) {
		terceirosCheckBox.setOnAction(acao);
	}

	public void setBotaoEnviarListener(EventHandler<ActionEvent> acao) {
		botaoEnviar.setOnAction(acao);
	}
	public void setOpcaoDeProcuraListener(EventHandler<ActionEvent> acao) {
		opcaoDeProcura.setOnAction(acao);
	}
	public void setTxtBotaoListener(EventHandler<ActionEvent> acao) {
		criarTxtButton.setOnAction(acao);
	}
	public void setTextBoxPath(File arquivo) {
		caminhoArquivoField.setText(arquivo.getAbsolutePath());
	}
	public void adicionarTxtInterface() {
		if(!painel.getChildren().contains(dataBox)) {
			dataLabel=new Label("Insira a data final referente ao estoque:");
			botaoData=new DatePicker();
			dataBox=new HBox(dataLabel,botaoData);
			painel.getChildren().addAll(dataBox,criarTxtButton);
		}
	}
	public void excluirTxtInterface() {
		if(painel.getChildren().contains(dataBox)) {
			painel.getChildren().removeAll(dataBox,criarTxtButton);
		}
	}

	public VBox getPainel() {
		return painel;
	}
    public ComboBox<String> getOpcaoDeProcura() {
    	return opcaoDeProcura;
    }
	public void setQuantidadeLabelText(String quantidadeLabel) {
		this.quantidadeLabel.setText(quantidadeLabel); 
		
	}

	public void setReferenciaLabelText(String referenciaLabel) {
		this.referenciaLabel.setText(referenciaLabel);
	}
	public void setTerceirosLabelText(String terceirosLabel) {
		 this.terceirosLabel.setText(terceirosLabel);
	}


	public TextField getQuantidadeField() {
		return quantidadeField;
	}

	public TextField getReferenciaField() {
		return referenciaField;
	}

	public String getBotaoDataText() {
		return botaoData.getEditor().getText();
	}
	public CheckBox getTerceirosCheckBox() {
		return terceirosCheckBox;
	}

	public Label getTerceirosLabel() {
		return terceirosLabel;
	}

	public TextField getTerceirosField() {
		return terceirosField;
	}
	
	
    
    
}
