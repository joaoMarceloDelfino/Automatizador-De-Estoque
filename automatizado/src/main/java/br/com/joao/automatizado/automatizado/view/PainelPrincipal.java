package br.com.joao.automatizado.automatizado.view;

 
 import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class PainelPrincipal {
	private Label arquivoLabel = new Label("Escolha o Arquivo:");
	private TextField caminhoArquivoField = new TextField();
	private Button botaoEscolherArquivo = new Button("Escolher o arquivo");
	private HBox arquivoBox=new HBox(10,arquivoLabel, caminhoArquivoField, botaoEscolherArquivo);
	private Button botaoEnviar = new Button("Enviar o arquivo");
	private ComboBox<String> opcaoDeProcura= new ComboBox<String>();
	private Label opcaoDeProcuraLabel=new Label("Selecione a opção de procura das colunas:");
	private HBox opcaoDeProcuraBox=new HBox(10,opcaoDeProcuraLabel,opcaoDeProcura);
 	private Label quantidadeLabel=new Label("Insira o nome da coluna 'quantidade':");
	private TextField quantidadeField= new TextField();
	private HBox quantidadeBox=new HBox(10,quantidadeLabel,quantidadeField);;
	private TextField referenciaField= new TextField();
	private Label referenciaLabel=new Label("Insira o nome da coluna 'código do item':");;
	private HBox referenciaBox=new HBox(10,referenciaLabel,referenciaField);;
	private Label terceirosCheckBoxLabel=new Label("Selecione se a empresa possui estoque em poder de terceiros:");
	private CheckBox terceirosCheckBox=new CheckBox();
	private Label terceirosLabel=new Label("Insira o nome da coluna 'quantidade em poder de terceiros':");
	private TextField terceirosField=new TextField();
	private HBox terceirosBox=new HBox(10,terceirosLabel,terceirosField);;
	private HBox terceirosCheckBoxBox=new HBox(10,terceirosCheckBoxLabel,terceirosCheckBox);;
 	private VBox painel;
 	private Label dataLabel;
 	private DatePicker botaoData;
 	private HBox dataBox;
 	private Button criarTxtButton=new Button("Criar txt");	
	Insets margem=new Insets(0,0,0,10);

	
 	public PainelPrincipal() {
		inicializarPainel();
	}

	private void inicializarPainel() {
 		caminhoArquivoField.setDisable(true);
 		terceirosField.setDisable(true);
		caminhoArquivoField.setPrefSize(300,10);
		opcaoDeProcura.getItems().addAll("Por nome", "Por numero");
		opcaoDeProcura.setValue("Por nome");
  		painel = new VBox(10);
 		VBox.setVgrow(painel, Priority.ALWAYS);
 		painel.getChildren().addAll(opcaoDeProcuraBox,referenciaBox,quantidadeBox,terceirosCheckBoxBox,terceirosBox,arquivoBox,botaoEnviar);
 		botaoEnviar.setMaxHeight(255);
 		
 	    setarMargemParaTodos();
 		

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
			dataBox=new HBox(10,dataLabel,botaoData);
			painel.getChildren().addAll(dataBox,criarTxtButton);
			VBox.setMargin(dataBox, margem);
			VBox.setMargin(criarTxtButton, margem);

		}
	}
	public void excluirTxtInterface() {
		if(painel.getChildren().contains(dataBox)) {
			painel.getChildren().removeAll(dataBox,criarTxtButton);
		}
	}
	private void setarMargemParaTodos() {
 		for(Node filho:painel.getChildren()) {
			VBox.setMargin(filho,margem);
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
