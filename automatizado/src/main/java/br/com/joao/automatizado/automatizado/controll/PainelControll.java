package br.com.joao.automatizado.automatizado.controll;

import java.io.File;

import br.com.joao.automatizado.automatizado.model.PainelModel;
import br.com.joao.automatizado.automatizado.view.FileChooserTela;
import br.com.joao.automatizado.automatizado.view.PainelPrincipal;
 

public class PainelControll {
	private PainelPrincipal painelPrincipal;
	private PainelModel painelModel=new PainelModel();
	private FileChooserTela fileChooserTela=new FileChooserTela(painelModel);
 
	public PainelControll(PainelPrincipal painelPrincipal) {
		this.painelPrincipal = painelPrincipal;
		setListeners();
	}
	private void processarExcel() {
 		painelModel.carregarExcel(fileChooserTela.getFileEscolhido());
		painelModel.criarSheet(0);
		painelModel.eliminarDuplicatasESomarQuantidades("Quantidade em estoque","Código do item");
	}

	private void setListeners() {
		 painelPrincipal.setBotaoEscolherListener(x->fileChooserTela.buscarArquivo());
		 painelPrincipal.setBotaoEnviarListener(x->{processarExcel();fileChooserTela.salvarArquivo();});
	}

}
