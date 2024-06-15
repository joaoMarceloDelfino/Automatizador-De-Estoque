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
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

 

public class PainelModel {
	Workbook workbook;
	Sheet sheet;
	File excel;
	int quantidadeIndex;
	int ReferenciaIndex;
 	int primeiraLinha;
	int ultimaLinha;
	int primeiraColuna;

	/*List<Double> quantidades = new ArrayList<Double>();
	List<String> referencias = new ArrayList<String>();
 	List<Item> duplicados = new ArrayList<Item>();
	Set<Item> naoDuplicados = new HashSet();
	List<Integer>linhasExcluir=new ArrayList();
	List<Item>naoDuplicadoseAtualizados=new ArrayList();*/

	public PainelModel( ) {
 	}
	public void setExcel(File excel) {
	       this.excel=excel;
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
 
	}
	/*private  void limparLinhasVazias(){
		for(Row linha:sheet) {
			for(Cell celula:linha) {
				if(celula!=null && celula.getCellType() != CellType.BLANK) {
					 break;
				}
	
			}
			sheet.removeRow(linha);
 		}
 	}*/

	private Integer getColunaIndice(String equivalencia) {
		sheetCriadoVerificacao();
		primeiraLinha=encontrarPrimeiraLinha();
		ultimaLinha=encontrarUltimaLinha();
 	    System.out.println("Numero de linhas do excel"+ultimaLinha);
		System.out.println("Primeira linha do excel:"+primeiraLinha );

		try {
			for (Cell celula : sheet.getRow(primeiraLinha)) {
				if (celula.getStringCellValue().strip().equals(equivalencia)) {
					System.out.println("oi");
					return celula.getColumnIndex();
				}
			}
		} catch (NullPointerException e) {
 			System.err.println("Coluna "+equivalencia+" nao encontrada");
			//e.printStackTrace();
 		}

		return null;
	}
	
	private Integer encontrarPrimeiraLinha() {
			for(int i=0;i<sheet.getLastRowNum();i++) {
				Row linha=sheet.getRow(i);
				for(int j=0;j<linha.getLastCellNum();j++) {
					if(linha.getCell(j).getCellType()!=CellType.BLANK==true&&
							linha.getCell(j+1).getCellType()!=CellType.BLANK==true&&
							linha.getCell(j+2).getCellType()!=CellType.BLANK==true&&
							linha.getCell(j+3).getCellType()!=CellType.BLANK==true) {
						primeiraColuna=j;
						return linha.getRowNum();
					}

							
				}
				
			}
		return null;
	}
	private Integer encontrarUltimaLinha() {
		for(int i=primeiraLinha+1;i<sheet.getLastRowNum();i++) {
			Row linha=sheet.getRow(i);
				if(linha.getCell(primeiraColuna).getCellType()==CellType.BLANK==true&&
						linha.getCell(primeiraColuna).getCellType()==CellType.BLANK==true&&
						linha.getCell(primeiraColuna).getCellType()==CellType.BLANK==true&&
						linha.getCell(primeiraColuna).getCellType()==CellType.BLANK==true) {
					return linha.getRowNum()-1;
				}

						
			
			
		}
		return sheet.getLastRowNum();
	}

 
	public List<String> iterarColunaString(String ColunaNome,int colunaIndex) {
		sheetCriadoVerificacao();
		List<String>rowValores=new ArrayList<String>();
		try {
			colunaIndex = getColunaIndice(ColunaNome);
			System.out.println("indice coluna referencia"+colunaIndex);
			for (int i = primeiraLinha+1; i < ultimaLinha; i++) {
 				if(sheet.getRow(i)!=null&&sheet.getRow(i).getCell(colunaIndex)!=null) {
 					 rowValores.add(sheet.getRow(i).getCell(colunaIndex).getStringCellValue().strip());
 					//System.out.println(sheet.getRow(i).getCell(colunaIndex).getStringCellValue().strip());
				}
 			}
			if(rowValores.isEmpty()) {
				throw new IllegalArgumentException();
			}
			return rowValores;


		} catch (IllegalArgumentException e) {
			System.err.println("Coluna nao encontrada");
		}
		return null;
 	}
	 
	


	public List<Double> iterarColunaNumerica(String ColunaNome,int colunaIndex) {
		sheetCriadoVerificacao();
		List<Double>rowValores=new ArrayList<Double>();
		try {
			colunaIndex = getColunaIndice(ColunaNome);
			System.out.println("indice coluna quantidade"+colunaIndex);

 			for (int i = primeiraLinha+1; i <= ultimaLinha; i++) {
				if(sheet.getRow(i)!=null&&sheet.getRow(i).getCell(colunaIndex)!=null) {
					Cell celula=sheet.getRow(i).getCell(colunaIndex);
					
					if(celula.getCellType()==CellType.NUMERIC) {	
						rowValores.add(celula.getNumericCellValue());
 					}
					else if(celula.getCellType()==CellType.FORMULA) {
						FormulaEvaluator evaluador=workbook.getCreationHelper().createFormulaEvaluator();
						CellValue valorCelula=evaluador.evaluate(celula);
						if(valorCelula.getCellType()==CellType.NUMERIC) {
							rowValores.add(valorCelula.getNumberValue());
						}
					}
					else {
						System.out.println("Formula nao resolvida"+"Celula linha: "+celula.getRowIndex()+"coluna: "+celula.getColumnIndex());
					}
					 
 				}
 			}
			if(rowValores.isEmpty()) {
				throw new IllegalArgumentException();
			}
			return rowValores;


		} catch (Exception e) {
 			System.err.println("Coluna nao encontrada");
 			System.out.println(e.getMessage());
		}
		return null;

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
		for (int i = sheet.getFirstRowNum() + 1; i < ultimaLinha; i++) {
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
		public File getSheetAtualizado() {
				try {
					File fileTemporario = File.createTempFile("temp_sheet", ".xlsx");;
					OutputStream sheetSaida=new FileOutputStream(fileTemporario);
					workbook.write(sheetSaida);
					sheetSaida.close();
					return fileTemporario;
	
				} catch (IOException e) {
	 				e.printStackTrace();
				}
				return null;
	 		}
		public void eliminarDuplicatasESomarQuantidades(String quantidade,String referencia) {
			Set<Item>naoDuplicados=new HashSet<Item>();
			List<Item>duplicados=new ArrayList<Item>();
			List<Integer>linhasExcluir=new ArrayList<Integer>();
			List<Item>naoDuplicadosEAtualizados=new ArrayList<Item>();
			//getValueTeste();
			List<String>referenciaColumn=iterarColunaString(referencia, ReferenciaIndex);
			List<Double>quantidadeColumn=iterarColunaNumerica(quantidade, quantidadeIndex);
			quantidadeColumn.forEach(System.out::println);
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

