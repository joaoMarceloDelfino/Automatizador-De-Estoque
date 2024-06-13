 package br.com.joao.automatizador_excel.view;

import java.awt.Dimension;


import javax.swing.JFrame;

import br.com.joao.automatizador_excel.controller.PainelControll;

 
public class Tela extends JFrame {
	PainelPrincipal painelPrincipal=new PainelPrincipal();

public Tela() {
  config();	
}
private void config() {
	PainelControll painelController=new PainelControll(painelPrincipal);
	setTitle("Conversor de Excel");
  	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	setPreferredSize(new Dimension(500,350));
	add(painelPrincipal);
 	setLocationRelativeTo(null);
 	pack();
	setVisible(true);
	
}
}
