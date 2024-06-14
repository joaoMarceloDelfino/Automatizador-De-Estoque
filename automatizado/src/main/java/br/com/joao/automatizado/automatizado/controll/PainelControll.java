package br.com.joao.automatizado.automatizado.controll;

import java.io.File;

import br.com.joao.automatizado.automatizado.model.PainelModel;
import br.com.joao.automatizado.automatizado.view.FileChooserTela;
import br.com.joao.automatizado.automatizado.view.PainelPrincipal;
 

public class PainelControll {
	private PainelPrincipal painelPrincipal;
	private FileChooserTela fileChooserTela = new FileChooserTela();
	private PainelModel painelModel;
	File excelAtualizado;
 
	public PainelControll(PainelPrincipal painelPrincipal) {
		this.painelPrincipal = painelPrincipal;
		setListeners();
	}
	private void processarExcel() {
		painelModel=new PainelModel(fileChooserTela.getFileEscolhido());
		painelModel.criarSheet(0);
		painelModel.eliminarDuplicatasESomarQuantidades("Quantidade em estoque" , "Código do item");
		painelModel.getSheetAtualizado( );
	}

	private void setListeners() {
		 painelPrincipal.setBotaoEscolherListener(x->fileChooserTela.buscarArquivo());
		 painelPrincipal.setBotaoEnviarListener(x->fileChooserTela.salvarArquivo());
	}

}
