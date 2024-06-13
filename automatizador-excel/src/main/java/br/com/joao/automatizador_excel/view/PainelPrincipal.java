 package br.com.joao.automatizador_excel.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PainelPrincipal  extends JPanel{
	JButton botaoEscolher=new JButton("Escolher Arquivo");	
	JButton enviar=new JButton("Enviar arquivo");
	
PainelPrincipal(){
	inicializar();	
}
private void inicializar() {
	setLayout(new BorderLayout());
 	/* GridBagConstraints constrains=new GridBagConstraints();
	constrains.gridx=0;
	constrains.gridy=0;
	constrains.gridheight=1;
	constrains.gridwidth=1;
	constrains.insets=new Insets(0,0,0,0);
	constrains.anchor=GridBagConstraints.WEST;
   	botaoEscolher.setPreferredSize(new Dimension(100,100));*/
    //botaoEscolher.setPreferredSize(new Dimension(150, 50));

     add(botaoEscolher,BorderLayout.CENTER); 
     add(enviar,BorderLayout.SOUTH); 
     

 }
public void setEscolherActionListener(ActionListener e) {
	botaoEscolher.addActionListener(e);
}
public void setEnviarActionListener(ActionListener e) {
	enviar.addActionListener(e);
}
}
