package br.com.joao.automatizado.automatizado.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import br.com.joao.automatizado.automatizado.model.PainelModel;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import java.nio.file.*;

public class FileChooserTela {
	File fileEscolhido;
	File fileAnterior;
	FileChooser fileChooser;

	public void buscarArquivo() throws FileAlreadyExistsException {
		fileChooser = new FileChooser();
		fileChooser.setTitle("Selecione o arquivo excel");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("XLSX", "*.xlsx"));
		fileEscolhido = fileChooser.showOpenDialog(null);
		if (fileAnterior != null) {
			if (fileEscolhido.equals(fileAnterior)) {
				throw new FileAlreadyExistsException("Arquivo já selecionado");
			}
		}
		fileAnterior = fileEscolhido;
	}

	public void salvarArquivoExcel(File arquivoSalvar) {
		if (fileEscolhido != null) {
			fileChooser.setTitle("Salvar o arquivo");

			File fileSalvo = fileChooser.showSaveDialog(null);
			if (fileSalvo != null) {
				try {
					Files.copy(new FileInputStream(arquivoSalvar), Paths.get(fileSalvo.getAbsolutePath()),
							StandardCopyOption.REPLACE_EXISTING);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void paraExtensaoTxt() {
		fileChooser.setTitle("Salvar o txt");
		fileChooser.getExtensionFilters().clear();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("TXT", "*.txt"));
	}

	public void paraExtensaoXlsx() {
		fileChooser.setTitle("Salvar o txt");
		fileChooser.getExtensionFilters().clear();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("XLSX", "*.xlsx"));
	}

	public File getFileEscolhido() {
		return fileEscolhido;
	}

}
