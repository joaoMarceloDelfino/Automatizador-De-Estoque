package br.com.joao.automatizado.automatizado.controll;

 


 
import java.nio.file.FileAlreadyExistsException;

import br.com.joao.automatizado.automatizado.model.PainelModel;
import br.com.joao.automatizado.automatizado.view.FileChooserTela;
import br.com.joao.automatizado.automatizado.view.PainelPrincipal;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
 import javafx.scene.control.TextField;
 

public class PainelControll {
	private PainelPrincipal painelPrincipal;
	private PainelModel painelModel=new PainelModel();
	private FileChooserTela fileChooserTela=new FileChooserTela();
	private ChangeListener<String> referenciaListener;
	private  ChangeListener<String> quantidadeListener;
	private  ChangeListener<String> terceirosListener;
	private Alert alerta;
	//private ProgressBar barraProgresso=new ProgressBar();
 
 
	public PainelControll(PainelPrincipal painelPrincipal) {
		this.painelPrincipal = painelPrincipal;
		setTextListeners();
 		setActionListeners();
	}
	private void processarExcel() throws Exception {
 		painelModel.carregarExcel(fileChooserTela.getFileEscolhido());
		painelModel.criarSheet(0);
		
			if(painelPrincipal.getOpcaoDeProcura().getValue().equals("Por nome")&& !painelPrincipal.getTerceirosCheckBox().isSelected()) {
				painelModel.eliminarDuplicatasESomarQuantidades(painelPrincipal.getQuantidadeField().getText(),painelPrincipal.getReferenciaField().getText());
			  return;	 
			}
			else if(painelPrincipal.getOpcaoDeProcura().getValue().equals("Por nome")&& painelPrincipal.getTerceirosCheckBox().isSelected()) {
				painelModel.eliminarDuplicatasESomarQuantidadesTerceiros(painelPrincipal.getQuantidadeField().getText(),painelPrincipal.getReferenciaField().getText(),painelPrincipal.getTerceirosField().getText());
			}
			else if(painelPrincipal.getOpcaoDeProcura().getValue().equals("Por numero")&& !painelPrincipal.getTerceirosCheckBox().isSelected()) {
				painelModel.eliminarDuplicatasESomarQuantidades(Integer.parseInt(painelPrincipal.getQuantidadeField().getText()) ,Integer.parseInt(painelPrincipal.getReferenciaField().getText()));
			 return;
			}
			else if(painelPrincipal.getOpcaoDeProcura().getValue().equals("Por numero")&& painelPrincipal.getTerceirosCheckBox().isSelected()) {
				painelModel.eliminarDuplicatasESomarQuantidadesTerceiros(Integer.parseInt(painelPrincipal.getQuantidadeField().getText()) ,
						Integer.parseInt(painelPrincipal.getReferenciaField().getText()),Integer.parseInt(painelPrincipal.getTerceirosField().getText()));
			 return;
			}
		
 			/*e.printStackTrace();
 			alerta=new Alert(Alert.AlertType.ERROR);
 			alerta.setHeaderText("ALERTA!");
 			alerta.setContentText("O arquivo não pôde ser importado!");*/
		
 	}

	private void setTextListeners() {
		 referenciaListener=setAceitarApenasNumeros(painelPrincipal.getReferenciaField());
		 quantidadeListener=setAceitarApenasNumeros(painelPrincipal.getQuantidadeField());
		 terceirosListener=setAceitarApenasNumeros(painelPrincipal.getTerceirosField());

	}
	private void setActionListeners() {
		 painelPrincipal.setBotaoEscolherListener(x->{botaoEscolherAcao();});
 		 painelPrincipal.setBotaoEnviarListener(x->{botaoEnviarAcao();});
 		 painelPrincipal.setOpcaoDeProcuraListener(x->{ComboBoxOpcoes();});
 		 painelPrincipal.setTxtBotaoListener(x->{botaoTxtAcao();});
 		 painelPrincipal.setCheckBoxListener(x->{CheckBoxOpcoes();});
	}
	
	private void ComboBoxOpcoes() {
		if(painelPrincipal.getOpcaoDeProcura().getValue().equals("Por nome") ){
			painelPrincipal.setQuantidadeLabelText("Insira o nome da coluna 'quantidade':");
			painelPrincipal.setReferenciaLabelText("Insira o nome da coluna 'código do item':");
			painelPrincipal.setTerceirosLabelText("Insira o nome da coluna 'quantidade em poder de terceiros':");
			painelPrincipal.getReferenciaField().textProperty().removeListener(referenciaListener);
			painelPrincipal.getQuantidadeField().textProperty().removeListener(quantidadeListener);
			painelPrincipal.getTerceirosField().textProperty().removeListener(terceirosListener);
			if(painelPrincipal.getTerceirosCheckBox().isSelected()) {
			}
  		}
		else if(painelPrincipal.getOpcaoDeProcura().getValue().equals("Por numero")	){ 
			painelPrincipal.getReferenciaField().clear();
			painelPrincipal.getQuantidadeField().clear();
			painelPrincipal.getTerceirosField().clear();
			painelPrincipal.setQuantidadeLabelText("Insira o índice da coluna 'quantidade':");
			painelPrincipal.setReferenciaLabelText("Insira o índice da coluna 'código do item':");
			painelPrincipal.setTerceirosLabelText("Insira o índice da coluna 'quantidade em poder de terceiros':");
		    painelPrincipal.getReferenciaField().textProperty().addListener(referenciaListener);
		    painelPrincipal.getQuantidadeField().textProperty().addListener(quantidadeListener);
		    painelPrincipal.getTerceirosField().textProperty().addListener(terceirosListener);


		     
		}
	}
	private void botaoEscolherAcao() {
		try {
			fileChooserTela.buscarArquivo();
			painelPrincipal.excluirTxtInterface();
			painelPrincipal.setTextBoxPath(fileChooserTela.getFileEscolhido());
		} catch (FileAlreadyExistsException e) {
 			System.out.println("Arquivo selecionado e igual ao arquivo anteriormente selecionado");
		}
	}
	private void botaoEnviarAcao() {
		try {
			fileChooserTela.paraExtensaoXlsx();
			processarExcel();
			fileChooserTela.salvarArquivoExcel(painelModel.getSheetAtualizado());
			painelPrincipal.adicionarTxtInterface();
		} catch (Exception e) {
 			e.printStackTrace();
 			alerta=new Alert(Alert.AlertType.ERROR);
 			alerta.setHeaderText("O arquivo não pôde ser importado!");
 			alerta.setContentText("Verifique se o informado nas caixas de texto equivale ao arquivo excel ou se há células em branco.");
 			alerta.show();
		}
	}
	private void botaoTxtAcao() {
		try {
			fileChooserTela.paraExtensaoTxt();
			if(!painelPrincipal.getTerceirosCheckBox().isSelected()) {
				fileChooserTela.salvarArquivoExcel(painelModel.gerarTxt(painelPrincipal.getBotaoDataText())); 
			}
			else {
				fileChooserTela.salvarArquivoExcel(painelModel.gerarTxtTerceiros(painelPrincipal.getBotaoDataText())); 

			}
 		} catch (Exception e) {
 			e.printStackTrace();
 			alerta=new Alert(Alert.AlertType.ERROR);
 			alerta.setHeaderText("Alerta!");
 			alerta.setContentText("O arquivo .txt não pôde ser criado!");
 			alerta.show();
		}
	}
	private void CheckBoxOpcoes() {
		//"Estoque em poder de terceiros"
		if(!painelPrincipal.getTerceirosCheckBox().isSelected()) {
			painelPrincipal.getTerceirosField().setDisable(true);
			painelPrincipal.getTerceirosField().clear();
 		}
		else {
			painelPrincipal.getTerceirosField().setDisable(false);

		}
	}
	 
	private ChangeListener<String> setAceitarApenasNumeros(TextField textField) {
 		ChangeListener<String> changeListener=new ChangeListener<String>() {
            
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
         				 if(!newValue.matches("\\d*")) {
         					 textField.setText(newValue.replaceAll("[^\\d]",""));
         				 }
  			}
			 
		 };  
		 return changeListener;
    
	}
	

}
