package br.com.joao.automatizado.automatizado.controll;

 
import java.io.File;

import br.com.joao.automatizado.automatizado.model.PainelModel;
import br.com.joao.automatizado.automatizado.view.FileChooserTela;
import br.com.joao.automatizado.automatizado.view.PainelPrincipal;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
 

public class PainelControll {
	private PainelPrincipal painelPrincipal;
	private PainelModel painelModel=new PainelModel();
	private FileChooserTela fileChooserTela=new FileChooserTela(painelModel);
	private ChangeListener<String> referenciaListener ;
	private  ChangeListener<String> quantidadeListener;
 
 
	public PainelControll(PainelPrincipal painelPrincipal) {
		this.painelPrincipal = painelPrincipal;
	    referenciaListener=setAceitarApenasNumeros(painelPrincipal.getReferenciaField());
		quantidadeListener=setAceitarApenasNumeros(painelPrincipal.getQuantidadeField());
		setListeners();
	}
	private void processarExcel() {
 		painelModel.carregarExcel(fileChooserTela.getFileEscolhido());
		painelModel.criarSheet(0);
		if(painelPrincipal.getOpcaoDeProcura().getValue().equals("Por nome")) {
			painelModel.eliminarDuplicatasESomarQuantidades(painelPrincipal.getQuantidadeField().getText(),painelPrincipal.getReferenciaField().getText());
			//painelModel.eliminarDuplicatasESomarQuantidades("Quantidade em estoque","Código do item");

					 
		}
		
		else if(painelPrincipal.getOpcaoDeProcura().getValue().equals("Por numero")) {
			painelModel.eliminarDuplicatasESomarQuantidades(Integer.parseInt(painelPrincipal.getQuantidadeField().getText()) ,Integer.parseInt(painelPrincipal.getReferenciaField().getText()));

		}
 	}

	private void setListeners() {
		 painelPrincipal.setBotaoEscolherListener(x->{fileChooserTela.buscarArquivo();painelPrincipal.setTextBoxPath(fileChooserTela.getFileEscolhido());});
 		 painelPrincipal.setBotaoEnviarListener(x->{processarExcel();fileChooserTela.salvarArquivoExcel(painelModel.getSheetAtualizado());painelPrincipal.adicionarTxtInterface();});
 		 painelPrincipal.setOpcaoDeProcuraListener(x->{ComboBoxOpcoes();});
 		 painelPrincipal.setTxtBotaoListener(x->{fileChooserTela.paraExtensaoTxt();fileChooserTela.salvarArquivoExcel(painelModel.gerarTxt(painelPrincipal.getBotaoDataText()));});
	}
	private void ComboBoxOpcoes() {
		if(painelPrincipal.getOpcaoDeProcura().getValue().equals("Por nome") ){
			painelPrincipal.setQuantidadeLabelText("Insira o nome da coluna 'quantidade':");
			painelPrincipal.setReferenciaLabelText("Insira o nome da coluna 'referência':");
			painelPrincipal.getReferenciaField().textProperty().removeListener(referenciaListener);
			painelPrincipal.getQuantidadeField().textProperty().removeListener(quantidadeListener);
			painelPrincipal.getReferenciaField().setText("Código do item");
			painelPrincipal.getQuantidadeField().setText("Quantidade em estoque");
 		}
		else if(painelPrincipal.getOpcaoDeProcura().getValue().equals("Por numero")	){ 
			painelPrincipal.getReferenciaField().clear();
			painelPrincipal.getQuantidadeField().clear();
			painelPrincipal.setQuantidadeLabelText("Insira o índice da coluna 'quantidade':");
			painelPrincipal.setReferenciaLabelText("Insira o índice da coluna 'referência':");
			 
		    painelPrincipal.getReferenciaField().textProperty().addListener(referenciaListener);
		    painelPrincipal.getQuantidadeField().textProperty().addListener(quantidadeListener);

		     
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
