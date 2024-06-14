package br.com.joao.automatizado.automatizado.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

 

public class PainelModel {
	Workbook workbook;
	Sheet sheet;
	int quantidadeIndex;
	int ReferenciaIndex;
	int nLinhas;

	/*List<Double> quantidades = new ArrayList<Double>();
	List<String> referencias = new ArrayList<String>();
 	List<Item> duplicados = new ArrayList<Item>();
	Set<Item> naoDuplicados = new HashSet();
	List<Integer>linhasExcluir=new ArrayList();
	List<Item>naoDuplicadoseAtualizados=new ArrayList();*/

	PainelModel(File excel) {
		carregarExcel(excel);
	}

	public void carregarExcel(File excel) {
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
	    nLinhas=sheet.getPhysicalNumberOfRows();

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

 
	public List<String> iterarColunaString(String ColunaNome,int colunaIndex) {
		sheetCriadoVerificacao();
		List<String>rowValores=new ArrayList<String>();
		try {
			colunaIndex = getColunaIndice(ColunaNome);
			for (int i = sheet.getFirstRowNum() + 1; i < nLinhas; i++) {
				//referencias.add(sheet.getRow(i).getCell(colunaIndex).getStringCellValue());
				rowValores.add(sheet.getRow(i).getCell(colunaIndex).getStringCellValue());
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Coluna nao encontrada");
		}
		return rowValores;
	}

	public List<Double> iterarColunaNumerica(String ColunaNome,int colunaIndex) {
		sheetCriadoVerificacao();
		List<Double>rowValores=new ArrayList<Double>();
		try {
			colunaIndex = getColunaIndice(ColunaNome);
			int nLinhas = sheet.getPhysicalNumberOfRows();
			for (int i = sheet.getFirstRowNum() + 1; i < nLinhas; i++) {
				rowValores.add(sheet.getRow(i).getCell(colunaIndex).getNumericCellValue());
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Coluna nao encontrada");
		}
		return rowValores;

	}
	/*private void getColunaNumerica(String ColunaNome, int colunaIndex, List<Double>listaNumerica) {
		listaNumerica.addAll(iterarColunaNumerica(ColunaNome,colunaIndex));
	}
	private void getColunaString(String ColunaNome, int colunaIndex, List<String>listaString) {
		listaString.addAll(iterarColunaString(ColunaNome,colunaIndex));
	}*/


	private List<Item>  criarObjetos(List<Double>listaNumerica,List<String>listaString) {
		List<Item>itens=new ArrayList();
		Iterator<Double> listaNumericaIterator = listaNumerica.iterator();
		Iterator<String> listaStringIterator = listaString.iterator();
		for (int i = sheet.getFirstRowNum() + 1; i < nLinhas; i++) {
			itens.add(new Item(i, listaStringIterator.next(), listaNumericaIterator.next()));
		}
		return itens;

	}

	private void verificarDuplicata(List<Item> itens,Set<Item>naoDuplicados,List<Item>duplicados) {

		for (Item item : itens) {
			if (!naoDuplicados.add(item)) {
				duplicados.add(item);
			}
		}
	
	}

		private void somarDuplicatas(Set<Item>naoDuplicados,List<Item>duplicados,List<Integer>linhasExcluir,List<Item>naoDuplicadosEAtualizados) {
		HashMap<Item, Double> duplicadosMapa = new HashMap();
	
			for (Item item : duplicados) {
	 			linhasExcluir.add(item.getLinha());
				duplicadosMapa.merge(item, item.getQuantidade(), Double::sum);
	 		}
			for (Item item : naoDuplicados) {
				
	        	Double quantidadeDuplicada=duplicadosMapa.get(item);
	        	if(quantidadeDuplicada!=null) {
	            	item.addQuantidade(quantidadeDuplicada);
	            	naoDuplicadosEAtualizados.add(item);
	        	}
	 		}
		}
		private void atualizarQuantidade(List<Item>naoDuplicadosEAtualizados,int colunaIndex) {
			
			for(Item item:naoDuplicadosEAtualizados) {
				Row linha=sheet.getRow(item.getLinha());
				Cell cell=linha.getCell(colunaIndex);
				cell.setCellValue(item.getQuantidade());
 	     	}
		}
		private void excluirLinhasDuplicadas(List<Integer> linhasExcluir) {
			for(Integer linha:linhasExcluir) {
				sheet.removeRow(sheet.getRow(linha));
			}
		}
		private OutputStream getSheetAtualizado(File outputFile) {
			try {
				OutputStream sheetSaida=new FileOutputStream(outputFile);
				workbook.write(sheetSaida);
				sheetSaida.close();
				return sheetSaida;

			} catch (IOException e) {
 				e.printStackTrace();
			}
			return null;
 		}
		public void eliminarDuplicatasESomarQuantidades(String quantidade,String referencia) {
			Set<Item>naoDuplicados=new HashSet();
			List<Item>duplicados=new ArrayList();
			List<Integer>linhasExcluir=new ArrayList();
			List<Item>naoDuplicadosEAtualizados=new ArrayList();
			List<Double>quantidadeColumn=iterarColunaNumerica(quantidade, quantidadeIndex);
			List<String>referenciaColumn=iterarColunaString(referencia, ReferenciaIndex);
			List<Item>itens=criarObjetos(quantidadeColumn, referenciaColumn);
			verificarDuplicata(itens, naoDuplicados, duplicados);
			somarDuplicatas(naoDuplicados, duplicados, linhasExcluir, naoDuplicadosEAtualizados);
			atualizarQuantidade(naoDuplicadosEAtualizados, quantidadeIndex);
			excluirLinhasDuplicadas(linhasExcluir);
 		}
  
	private void sheetCriadoVerificacao() {
		if (sheet == null) {
			throw new IllegalStateException("A planilha não foi criada. Chame o método criarSheet primeiro.");
		}
	}
}
