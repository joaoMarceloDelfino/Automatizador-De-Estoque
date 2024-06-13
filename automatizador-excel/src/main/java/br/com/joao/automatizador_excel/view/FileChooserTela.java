 package br.com.joao.automatizador_excel.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooserTela extends JDialog {
	JPanel painel = new JPanel();
	JFileChooser fileChooser = new JFileChooser();
	FileNameExtensionFilter filtro;

	public void inicializar() {
		painel.setLayout(new GridLayout());
		setFileFilterXLSX();
		painel.add(fileChooser);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setPreferredSize(new Dimension(350, 350));
		add(painel);
		pack();
		setVisible(true);

	}

	/*
	 * public void setFileFilter(String descricao, String...extensoes) { filtro=new
	 * FileNameExtensionFilter(descricao,extensoes);
	 * fileChooser.setFileFilter(filtro);
	 * 
	 * }
	 */
	private void setFileFilterXLSX() {
		filtro = new FileNameExtensionFilter(".xlsx", "XLSX");
		fileChooser.setFileFilter(filtro);

	}

	public File getChooserFile() {
		return fileChooser.getSelectedFile();
	}

}
