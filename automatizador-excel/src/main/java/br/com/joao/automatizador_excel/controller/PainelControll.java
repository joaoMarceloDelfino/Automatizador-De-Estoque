package br.com.joao.automatizador_excel.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import br.com.joao.automatizador_excel.view.FileChooserTela;
import br.com.joao.automatizador_excel.view.PainelPrincipal;
 

public class PainelControll {
	private PainelPrincipal painelPrincipal;
	private FileChooserTela fileChooserTela = new FileChooserTela();
	private List<String> campos;

	public PainelControll(PainelPrincipal painelPrincipal) {
		this.painelPrincipal = painelPrincipal;
		// fileChooser.setFileFilter("JPG", "JPG");
		setActionListeners();
	}

	private void setActionListeners() {
		painelPrincipal.setEscolherActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooserTela.inicializar();
				fileChooserTela.setLocationRelativeTo(painelPrincipal);
			}

		});
	}

	
		
	}

