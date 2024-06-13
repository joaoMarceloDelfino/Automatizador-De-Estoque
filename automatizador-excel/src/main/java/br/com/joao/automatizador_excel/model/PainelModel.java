package br.com.joao.automatizador_excel.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class PainelModel {
	Workbook workbook;
	Sheet sheet;
	List<Double> quantidades = new ArrayList<Double>();
	List<String> referencias = new ArrayList<String>();
	List<Item> itens = new ArrayList<Item>();
	List<Item> duplicados = new ArrayList<Item>();
	Set<Item> naoDuplicados = new HashSet();
	List<Integer>linhasExcluir=new ArrayList();
	List<Item>naoDuplicadoseAtualizados=new ArrayList();

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

	int nLinhas = sheet.getPhysicalNumberOfRows();

	public void iterarColunaString(String ColunaNome) {
		sheetCriadoVerificacao();
		try {
			int colunaIndex = getColunaIndice(ColunaNome);
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

	private void criarObjetos() {
		Iterator<Double> quantidadesIterator = quantidades.iterator();
		Iterator<String> referenciaIterator = referencias.iterator();
		for (int i = sheet.getFirstRowNum() + 1; i < nLinhas; i++) {
			itens.add(new Item(i, referenciaIterator.next(), quantidadesIterator.next()));
		}

	}

	private void verificarDuplicata() {

		for (Item item : itens) {
			if (!naoDuplicados.add(item)) {
				duplicados.add(item);
			}
		}
	
	}

	private void somarDuplicatas() {
	HashMap<Item, Double> duplicadosMapa = new HashMap();

		for (Item item : duplicados) {
 			linhasExcluir.add(item.getLinha());
			duplicadosMapa.merge(item, item.getQuantidade(), Double::sum);
 		}
		for (Item item : naoDuplicados) {
			
        	Double quantidadeDuplicada=duplicadosMapa.get(item);
        	if(quantidadeDuplicada!=null) {
            	item.addQuantidade(quantidadeDuplicada);
            	naoDuplicadoseAtualizados.add(item);
        	}
 		}
	}
//TODO: FAZER COM QUE AS LINHAS DAS DUPLICATAS SEJAM REMOVIDAS E OS CAMPOS ATUALIZADOS ATUALIZADOS
 
	private void sheetCriadoVerificacao() {
		if (sheet == null) {
			throw new IllegalStateException("A planilha não foi criada. Chame o método criarSheet primeiro.");
		}
	}
}
