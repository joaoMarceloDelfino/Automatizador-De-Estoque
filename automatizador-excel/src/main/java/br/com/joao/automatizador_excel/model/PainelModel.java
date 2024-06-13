package br.com.joao.automatizador_excel.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PainelModel {
	Workbook workbook;
	Sheet sheet;
	List<Number> quantidades = new ArrayList<Number>();
	List<String> referencias = new ArrayList<String>();

	PainelModel(File excel) {
		carregarExcel(excel);
	}

	private void carregarExcel(File excel) {
		try {
			FileInputStream fileInputStream = new FileInputStream(excel);
			workbook = new XSSFWorkbook(fileInputStream);
		} catch (IOException e) {
			System.err.println("Arquivo xslx nao encontrado");
			e.printStackTrace();
		}
	}

	public void criarSheet(int index) {
		if (workbook == null) {
			throw new IllegalStateException("workbook nao encontrado. Por favor crie o workbook");
		}
			sheet = workbook.getSheetAt(index);
		
	}

	private Integer getColunaIndice(String equivalencia) throws IOException {
		sheetCriadoVerificacao();

		for (Cell celula : sheet.getRow(sheet.getFirstRowNum())) {
			if (celula.getStringCellValue().equals(equivalencia)) {
				return celula.getColumnIndex();
			}
		}

		return null;
	}

	public void iterarColunaString(String ColunaNome) {
		sheetCriadoVerificacao();
		try {
			int colunaIndex = getColunaIndice(ColunaNome);
			int nLinhas = sheet.getPhysicalNumberOfRows();
			for (int i = sheet.getFirstRowNum() + 1; i < nLinhas; i++) {
				referencias.add(sheet.getRow(i).getCell(colunaIndex).getStringCellValue());
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Coluna nao encontrada");
		}
	}

	public void iterarColunaNumerica(String ColunaNome) {
		sheetCriadoVerificacao();
		try {
			int colunaIndex = getColunaIndice(ColunaNome);
			int nLinhas = sheet.getPhysicalNumberOfRows();
			for (int i = sheet.getFirstRowNum() + 1; i < nLinhas; i++) {
				quantidades.add(sheet.getRow(i).getCell(colunaIndex).getNumericCellValue());
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Coluna nao encontrada");
		}

	}

	/*private void procurarCopia() {
		for (int i = 0; i < quantidades.size(); i++) {
			for (int j = i + 1; j < quantidades.size(); j++) {
				if (quantidades.get(i).equals(quantidades.get(j))) {
					System.out.println("Valores duplicados encontrados: " + quantidades.get(i));
				}
			}
		}
	}*/
	private void sheetCriadoVerificacao() {
		if (sheet == null) {
			throw new IllegalStateException("A planilha não foi criada. Chame o método criarSheet primeiro.");
		}
	}
}
