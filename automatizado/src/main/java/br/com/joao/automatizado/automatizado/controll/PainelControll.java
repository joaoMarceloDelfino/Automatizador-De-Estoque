package br.com.joao.automatizado.automatizado.controll;

import br.com.joao.automatizado.automatizado.view.FileChooserTela;
import br.com.joao.automatizado.automatizado.view.PainelPrincipal;
 

public class PainelControll {
	private PainelPrincipal painelPrincipal;
	private FileChooserTela fileChooserTela = new FileChooserTela();
 
	public PainelControll(PainelPrincipal painelPrincipal) {
		this.painelPrincipal = painelPrincipal;
		setListeners();
		 
	}

	private void setListeners() {
		 painelPrincipal.setBotaoEscolherListener(x->fileChooserTela.buscarArquivo());
		 painelPrincipal.setBotaoEnviarListener(x->fileChooserTela.salvarArquivo());
	}

}
