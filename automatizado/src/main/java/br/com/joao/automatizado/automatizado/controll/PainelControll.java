package br.com.joao.automatizado.automatizado.controll;

 


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
	private  ChangeListener<String> terceirosListener;
 
 
	public PainelControll(PainelPrincipal painelPrincipal) {
		this.painelPrincipal = painelPrincipal;
		setTextListeners();
 		setActionListeners();
	}
	private void processarExcel() {
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
 	}

	private void setTextListeners() {
		 referenciaListener=setAceitarApenasNumeros(painelPrincipal.getReferenciaField());
		 quantidadeListener=setAceitarApenasNumeros(painelPrincipal.getQuantidadeField());
		 terceirosListener=setAceitarApenasNumeros(painelPrincipal.getTerceirosField());

	}
	private void setActionListeners() {
		 painelPrincipal.setBotaoEscolherListener(x->{fileChooserTela.buscarArquivo();painelPrincipal.setTextBoxPath(fileChooserTela.getFileEscolhido());});
 		 painelPrincipal.setBotaoEnviarListener(x->{processarExcel();fileChooserTela.salvarArquivoExcel(painelModel.getSheetAtualizado());painelPrincipal.adicionarTxtInterface();});
 		 painelPrincipal.setOpcaoDeProcuraListener(x->{ComboBoxOpcoes();});
 		 painelPrincipal.setTxtBotaoListener(x->{fileChooserTela.paraExtensaoTxt();fileChooserTela.salvarArquivoExcel(painelModel.gerarTxt(painelPrincipal.getBotaoDataText()));});
 		 painelPrincipal.setCheckBoxListener(x->{CheckBoxOpcoes();});
	}
	private void ComboBoxOpcoes() {
		if(painelPrincipal.getOpcaoDeProcura().getValue().equals("Por nome") ){
			painelPrincipal.setQuantidadeLabelText("Insira o nome da coluna 'quantidade':");
			painelPrincipal.setReferenciaLabelText("Insira o nome da coluna 'referência':");
			painelPrincipal.setTerceirosLabelText("Insira o nome da coluna 'quantidade em poder de terceiros':");
			painelPrincipal.getReferenciaField().textProperty().removeListener(referenciaListener);
			painelPrincipal.getQuantidadeField().textProperty().removeListener(quantidadeListener);
			painelPrincipal.getTerceirosField().textProperty().removeListener(terceirosListener);
			painelPrincipal.getReferenciaField().setText("Código do item");
			painelPrincipal.getQuantidadeField().setText("Quantidade em estoque");
			if(painelPrincipal.getTerceirosCheckBox().isSelected()) {
				painelPrincipal.getTerceirosField().setText("Estoque em poder de terceiros");
			}
  		}
		else if(painelPrincipal.getOpcaoDeProcura().getValue().equals("Por numero")	){ 
			painelPrincipal.getReferenciaField().clear();
			painelPrincipal.getQuantidadeField().clear();
			painelPrincipal.getTerceirosField().clear();
			painelPrincipal.setQuantidadeLabelText("Insira o índice da coluna 'quantidade':");
			painelPrincipal.setReferenciaLabelText("Insira o índice da coluna 'referência':");
			painelPrincipal.setTerceirosLabelText("Insira o índice da coluna 'quantidade em poder de terceiros':");

			 
		    painelPrincipal.getReferenciaField().textProperty().addListener(referenciaListener);
		    painelPrincipal.getQuantidadeField().textProperty().addListener(quantidadeListener);
		    painelPrincipal.getTerceirosField().textProperty().addListener(terceirosListener);


		     
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
			if(painelPrincipal.getOpcaoDeProcura().getValue().equals("Por nome")) {
				painelPrincipal.getTerceirosField().textProperty().setValue("Estoque em poder de terceiros");
			}
 
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
