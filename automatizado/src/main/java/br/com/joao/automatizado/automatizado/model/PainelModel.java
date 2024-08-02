package br.com.joao.automatizado.automatizado.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
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

import br.com.joao.automatizado.automatizado.view.PainelPrincipal;

public class PainelModel  {
	Workbook workbook;
	Sheet sheet;
	File excel;
  	int primeiraLinha;
	int ultimaLinha;
	int primeiraColuna;
	File txt;
	Writer escritor;
	BufferedWriter escritorBuffer;
	List<Item>itensAtualizados;
	 
 

	public PainelModel() {
 	}

	public void setExcel(File excel) {
		this.excel = excel;
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
		primeiraLinha = encontrarPrimeiraLinha();
		ultimaLinha = encontrarUltimaLinha();

	}
	 

	private Integer getColunaIndice(String equivalencia) {
		sheetCriadoVerificacao();
		 
		//System.out.println("Numero de linhas do excel" + ultimaLinha);
		//System.out.println("Primeira linha do excel:" + primeiraLinha);

		try {
			for (Cell celula : sheet.getRow(primeiraLinha)) {
				if (celula.getStringCellValue().strip().equals(equivalencia)) {
					//System.out.println("oi");
					System.out.println("index="+celula.getColumnIndex());
					return celula.getColumnIndex();
				}
			}
		} catch (NullPointerException e) {
			System.err.println("Coluna " + equivalencia + " nao encontrada");
			// e.printStackTrace();
		}

		return null;
	}

	private Integer encontrarPrimeiraLinha() {
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			if(sheet.getRow(i)!=null) {
			Row linha = sheet.getRow(i);

			for (int j = 0; j < linha.getLastCellNum(); j++) {
				if (linha.getCell(j).getCellType() != CellType.BLANK == true
						&& linha.getCell(j + 1).getCellType() != CellType.BLANK == true
						&& linha.getCell(j + 2).getCellType() != CellType.BLANK == true
						&& linha.getCell(j + 3).getCellType() != CellType.BLANK == true) {
					primeiraColuna = j;
					return linha.getRowNum();

				}
			}
			}

		}
		return null;
	}

	private Integer encontrarUltimaLinha() {
		for (int i = primeiraLinha ; i < sheet.getLastRowNum(); i++) {
			Row linha = sheet.getRow(i);

			if (linha.getCell(primeiraColuna).getCellType() == CellType.BLANK == true
					&& linha.getCell(primeiraColuna).getCellType() == CellType.BLANK == true
					&& linha.getCell(primeiraColuna).getCellType() == CellType.BLANK == true
					&& linha.getCell(primeiraColuna).getCellType() == CellType.BLANK == true) {
				return linha.getRowNum() ;

			}

		}
		return sheet.getLastRowNum();
	}

	public List<String> iterarColunaString(String colunaNome, int colunaIndex) {
		sheetCriadoVerificacao();
		List<String> rowValores = new ArrayList<String>();
 		try {
 			//System.out.println("indice coluna referencia" + colunaIndex);
			for (int i = primeiraLinha+1; i < ultimaLinha; i++) {
				if (sheet.getRow(i) != null && sheet.getRow(i).getCell(colunaIndex) != null
						&& sheet.getRow(i).getCell(colunaIndex).getCellType() != CellType.BLANK) {
					rowValores.add(sheet.getRow(i).getCell(colunaIndex).getStringCellValue().strip());
				}
			}
			if (rowValores.isEmpty()) {
				throw new IllegalArgumentException();
			}
			return rowValores;

		} catch (IllegalArgumentException e) {
			System.err.println("Coluna nao encontrada");
		}
		return null;
	}
	
	public List<String> iterarColunaString(int valor) {
		sheetCriadoVerificacao();
		List<String> rowValores = new ArrayList<String>();
 		try {
 			for (int i = primeiraLinha ; i < ultimaLinha; i++) {
				if (sheet.getRow(i) != null && sheet.getRow(i).getCell(valor) != null
						&& sheet.getRow(i).getCell(valor).getCellType() != CellType.BLANK) {
					rowValores.add(sheet.getRow(i).getCell(valor).getStringCellValue().strip());
				}
			}
			if (rowValores.isEmpty()) {
				throw new IllegalArgumentException();
			}
			return rowValores;

		} catch (IllegalArgumentException e) {
			System.err.println("Coluna nao encontrada");
		}
		return null;
	}

	 

	public List<Double> iterarColunaNumerica(String colunaNome, int colunaIndex ) {
		sheetCriadoVerificacao();
		List<Double> rowValores = new ArrayList<Double>();
 		try {
			//System.out.println("indice coluna quantidade" + colunaIndex);
 
			for (int i = primeiraLinha+1; i < ultimaLinha; i++) {
				if (sheet.getRow(i) != null && sheet.getRow(i).getCell(colunaIndex) != null) {
					Cell celula = sheet.getRow(i).getCell(colunaIndex);

					if (celula.getCellType() == CellType.NUMERIC) {
						rowValores.add(celula.getNumericCellValue());
					} else if (celula.getCellType() == CellType.FORMULA) {
						FormulaEvaluator evaluador = workbook.getCreationHelper().createFormulaEvaluator();
						CellValue valorCelula = evaluador.evaluate(celula);
						if (valorCelula.getCellType() == CellType.NUMERIC) {
							rowValores.add(valorCelula.getNumberValue());
						}
					} else {
					//	System.out.println("Formula nao resolvida" + "Celula linha: " + celula.getRowIndex()
								//+ "coluna: " + celula.getColumnIndex());
					}

				}
			}
			if (rowValores.isEmpty()) {
				throw new IllegalArgumentException();
			}
			return rowValores;

		} catch (Exception e) {
			System.err.println("Coluna nao encontrada");
			System.out.println(e.getMessage());
		}
		return null;

	}
	public List<Double> iterarColunaNumerica(int valor) {
		sheetCriadoVerificacao();		
		List<Double> rowValores = new ArrayList<Double>();
		try {
			//System.out.println("indice coluna quantidade" + colunaIndex);
			 

			for (int i = primeiraLinha ; i < ultimaLinha; i++) {
				if (sheet.getRow(i) != null && sheet.getRow(i).getCell(valor) != null) {
					Cell celula = sheet.getRow(i).getCell(valor);

					if (celula.getCellType() == CellType.NUMERIC) {
						rowValores.add(celula.getNumericCellValue());
					} else if (celula.getCellType() == CellType.FORMULA) {
						FormulaEvaluator evaluador = workbook.getCreationHelper().createFormulaEvaluator();
						CellValue valorCelula = evaluador.evaluate(celula);
						if (valorCelula.getCellType() == CellType.NUMERIC) {
							rowValores.add(valorCelula.getNumberValue());
						}
					} else {
					//	System.out.println("Formula nao resolvida" + "Celula linha: " + celula.getRowIndex()
								//+ "coluna: " + celula.getColumnIndex());
					}

				}
			}
			if (rowValores.isEmpty()) {
				throw new IllegalArgumentException();
			}
			return rowValores;

		} catch (Exception e) {
			System.err.println("Coluna nao encontrada");
			System.out.println(e.getMessage());
		}
		return null;

	}
 

	private List<Item> criarObjetos(List<Double> listaQuantidade, List<String> listaReferencia, int adicaoaPrimeiraLinha) {
		List<Item> itens = new ArrayList<Item>();
		Iterator<Double> listaNumericaIterator = listaQuantidade.iterator();
		Iterator<String> listaStringIterator = listaReferencia.iterator();
		for (int i = primeiraLinha+adicaoaPrimeiraLinha ; i < ultimaLinha; i++) {
			itens.add(new Item(i, listaStringIterator.next(), listaNumericaIterator.next()));
		}
		return itens;

	}
	private List<ItemTerceiros> criarObjetosComTerceiros(List<Double> listaQuantidade, List<String> listaReferencia,List<Double>listaTerceiros, int adicaoaPrimeiraLinha) {
		List<ItemTerceiros> itens = new ArrayList<ItemTerceiros>();
		Iterator<Double> listaQuantidadeIterator = listaQuantidade.iterator();
		Iterator<Double> listaTerceirosIterator=listaTerceiros.iterator();
		Iterator<String> listaReferenciaIterator = listaReferencia.iterator();
		
		for (int i = primeiraLinha+adicaoaPrimeiraLinha ; i < ultimaLinha; i++) {
			itens.add(new ItemTerceiros(i, listaReferenciaIterator.next(), listaQuantidadeIterator.next(),listaTerceirosIterator.next()));
		}
		return itens;

	}

	private void verificarDuplicata(List<Item> itens, Set<Item> naoDuplicados, List<Item> duplicados) {

		for (Item item : itens) {
			if (!naoDuplicados.add(item)) {
				duplicados.add(item);
			}
		}
	}
	private void verificarDuplicataTerceiros(List<ItemTerceiros> itens, Set<ItemTerceiros> naoDuplicados, List<ItemTerceiros> duplicados) {

		for (ItemTerceiros item : itens) {
			if (!naoDuplicados.add(item)) {
				duplicados.add(item);
			}
		}
	}
	
	private void somarDuplicatas(Set<Item> naoDuplicados, List<Item> duplicados, List<Integer> linhasExcluir,
			List<Item> naoDuplicadosEAtualizados) {
		HashMap<Item, Double> duplicadosMapa = new HashMap<Item, Double>();
		Double quantidadeDuplicada;

		for (Item item : duplicados) {
			linhasExcluir.add(item.getLinha());
			duplicadosMapa.merge(item, item.getQuantidade(), Double::sum);
		}

		for (Item item : naoDuplicados) {
			quantidadeDuplicada = duplicadosMapa.get(item);
			if (quantidadeDuplicada != null) {
				item.addQuantidade(quantidadeDuplicada);
				naoDuplicadosEAtualizados.add(item);

			}
		}
	}
	private void somarDuplicatasTerceiros(Set<ItemTerceiros> naoDuplicados, List<ItemTerceiros> duplicados, List<Integer> linhasExcluir,
			List<ItemTerceiros> naoDuplicadosEAtualizados) {
		HashMap<ItemTerceiros, Double> duplicadosMapaQuantidade = new HashMap<ItemTerceiros, Double>();
		HashMap<ItemTerceiros,Double>duplicadosMapaQuantidadeTerceiros=new HashMap<ItemTerceiros,Double>();
		Double quantidadeDuplicada;
		Double quantidadeTerceirosDuplicado;

		for (ItemTerceiros item : duplicados) {
			linhasExcluir.add(item.getLinha());
			duplicadosMapaQuantidade.merge(item, item.getQuantidade(), Double::sum);
			if(item.temTerceiros) {
				duplicadosMapaQuantidadeTerceiros.merge(item, item.getQuantidadeTerceiros(),Double::sum);
			}
 		}

		for (ItemTerceiros item : naoDuplicados) {
			quantidadeDuplicada = duplicadosMapaQuantidade.get(item);
			quantidadeTerceirosDuplicado=duplicadosMapaQuantidadeTerceiros.get(item);
			if (quantidadeDuplicada != null) {
				item.addQuantidade(quantidadeDuplicada);
				if(!item.temTerceiros) {
 					naoDuplicadosEAtualizados.add(item);
					continue;
				}
			
			}
			 if(quantidadeTerceirosDuplicado!=null) {
				item.addQuantidadeTerceiros(quantidadeTerceirosDuplicado);
				naoDuplicadosEAtualizados.add(item);
			}
		}
	}

	private void atualizarQuantidade(List<Item> naoDuplicadosEAtualizados, int colunaIndex) {
		for (Item item : naoDuplicadosEAtualizados) {
			Cell celula=sheet.getRow(item.getLinha()).getCell(colunaIndex);
			celula.setBlank();
			celula.setCellValue(item.getQuantidade());
 
		}
	}
	private void atualizarQuantidadeTerceiros(List<ItemTerceiros> naoDuplicadosEAtualizados, int quantidadeIndex,int quantidadeTerceirosIndex ) {
		for (ItemTerceiros item : naoDuplicadosEAtualizados) {
			Cell celula=sheet.getRow(item.getLinha()).getCell(quantidadeIndex);
			celula.setBlank();
			celula.setCellValue(item.getQuantidade());
			if(item.temTerceiros) {
				celula=sheet.getRow(item.getLinha()).getCell(quantidadeTerceirosIndex);
				celula.setBlank();
				celula.setCellValue(item.getQuantidadeTerceiros());
			}
		}
	}

	private void excluirLinhasDuplicadas(List<Integer> linhasExcluir) {
	    Collections.sort(linhasExcluir, Collections.reverseOrder());
		int nLinha;
		int ultimaLinha;
		for (Integer linha : linhasExcluir) {
			Row row = sheet.getRow(linha);
			if(row!=null) {
			for (Cell cell : row) {
				 
				if (cell.getCellType() == CellType.FORMULA) {
					cell.removeFormula();
					 
					}
			}
				 
				
			}
			 nLinha=row.getRowNum();
			 ultimaLinha=sheet.getLastRowNum();
			 System.out.println(ultimaLinha);
			 if(nLinha>=0&&nLinha<ultimaLinha) {
				 sheet.shiftRows(nLinha+1, ultimaLinha, -1);
			 }
		 
 
		}
 

	}

	

	public File getSheetAtualizado() {
		try {
			File fileTemporario = File.createTempFile("temp_sheet", ".xlsx");
			;
			OutputStream sheetSaida = new FileOutputStream(fileTemporario);
			workbook.write(sheetSaida);
			sheetSaida.close();
			return fileTemporario;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public File gerarTxt(String dataEstoque) {
		try {
			txt=new File("arquivo.txt");
			escritor=new FileWriter(txt);
			escritorBuffer=new BufferedWriter(escritor);
			Iterator<Item>itensAtualizadosIterator=itensAtualizados.iterator();
			while(itensAtualizadosIterator.hasNext()) {
			Item itemAtual=itensAtualizadosIterator.next();
  			escritorBuffer.write("K200||");
 			escritorBuffer.write(dataEstoque+"||");
            escritorBuffer.write(itemAtual.getReferencia()+"||");
            escritorBuffer.write(itemAtual.getQuantidade()+"||");
            if(itensAtualizadosIterator.hasNext()) {
                escritorBuffer.newLine();
            }
  			}
			
			escritorBuffer.flush();
			escritorBuffer.close();
			escritor.close();
          return txt;
		} catch (IOException e) {
			e.printStackTrace();
		}
       return null;
	}
	

	public void eliminarDuplicatasESomarQuantidades(String quantidadeNome, String referenciaNome) {
 
		int quantidadeIndex;
		int referenciaIndex;
		quantidadeIndex=0;
		referenciaIndex=0;
		quantidadeIndex = getColunaIndice(quantidadeNome);
		referenciaIndex = getColunaIndice(referenciaNome);
		Set<Item> naoDuplicados = new HashSet<Item>();
		List<Item> duplicados = new ArrayList<Item>();
		List<Integer> linhasExcluir = new ArrayList<Integer>();
		List<Item> naoDuplicadosEAtualizados = new ArrayList<Item>();
		List<String> referenciaColumn = iterarColunaString(referenciaNome, referenciaIndex);
		List<Double> quantidadeColumn = iterarColunaNumerica(quantidadeNome, quantidadeIndex);
		System.out.println("Primeira linha"+primeiraLinha);
		System.out.println("a coluna da referencia e:"+referenciaIndex );
		System.out.println("a coluna da quantidade e:"+quantidadeIndex );
		List<Item> itens = criarObjetos(quantidadeColumn, referenciaColumn,1);
		verificarDuplicata(itens, naoDuplicados, duplicados);
		somarDuplicatas(naoDuplicados, duplicados, linhasExcluir, naoDuplicadosEAtualizados);
		atualizarQuantidade(naoDuplicadosEAtualizados, quantidadeIndex);
		excluirLinhasDuplicadas(linhasExcluir);
		primeiraLinha=encontrarPrimeiraLinha();
		ultimaLinha=encontrarUltimaLinha();
		referenciaColumn = iterarColunaString(referenciaNome, referenciaIndex);
	    quantidadeColumn = iterarColunaNumerica(quantidadeNome, quantidadeIndex);
	    itensAtualizados=criarObjetos(quantidadeColumn, referenciaColumn,1);
		
		
	}
	public void eliminarDuplicatasESomarQuantidadesTerceiros(String quantidadeNome, String referenciaNome, String quantidadeTerceiros) {
 		int quantidadeIndex;
		int referenciaIndex;
		int quantidadeTerceirosIndex;
		quantidadeIndex=0;
		referenciaIndex=0;
		quantidadeTerceirosIndex=0;
		quantidadeIndex = getColunaIndice(quantidadeNome);
		referenciaIndex = getColunaIndice(referenciaNome);
		quantidadeTerceirosIndex=getColunaIndice(quantidadeTerceiros);
		Set<ItemTerceiros> naoDuplicados = new HashSet<ItemTerceiros>();
		List<ItemTerceiros> duplicados = new ArrayList<ItemTerceiros>();
		List<Integer> linhasExcluir = new ArrayList<Integer>();
		List<ItemTerceiros> naoDuplicadosEAtualizados = new ArrayList<ItemTerceiros>();
		List<String> referenciaColumn = iterarColunaString(referenciaNome, referenciaIndex);
		List<Double> quantidadeColumn = iterarColunaNumerica(quantidadeNome, quantidadeIndex);
		List<Double>quantidadeTerceirosColumn=iterarColunaNumerica(quantidadeTerceiros,quantidadeTerceirosIndex);
		List<ItemTerceiros> itens = criarObjetosComTerceiros(quantidadeColumn, referenciaColumn,quantidadeTerceirosColumn,1);
		verificarDuplicataTerceiros(itens, naoDuplicados, duplicados);
		somarDuplicatasTerceiros(naoDuplicados, duplicados, linhasExcluir, naoDuplicadosEAtualizados);
		atualizarQuantidadeTerceiros(naoDuplicadosEAtualizados, quantidadeIndex, quantidadeTerceirosIndex);
		excluirLinhasDuplicadas(linhasExcluir);
		primeiraLinha=encontrarPrimeiraLinha();
		ultimaLinha=encontrarUltimaLinha();
		referenciaColumn = iterarColunaString(referenciaNome, referenciaIndex);
	    quantidadeColumn = iterarColunaNumerica(quantidadeNome, quantidadeIndex);
	    itensAtualizados=criarObjetos(quantidadeColumn, referenciaColumn,1);
		
		
	}
	
	public void eliminarDuplicatasESomarQuantidades(int quantidadeColuna, int referenciaColuna ) {
 		int quantidadeIndex=quantidadeColuna-1;
		int referenciaIndex=referenciaColuna-1;
		quantidadeIndex=quantidadeColuna-1;
		referenciaIndex=referenciaColuna-1;
		Set<Item> naoDuplicados = new HashSet<Item>();
		List<Item> duplicados = new ArrayList<Item>();
		List<Integer> linhasExcluir = new ArrayList<Integer>();
		List<Item> naoDuplicadosEAtualizados = new ArrayList<Item>();
 		List<String> referenciaColumn = iterarColunaString(referenciaIndex);
  		List<Double> quantidadeColumn = iterarColunaNumerica(quantidadeIndex);
 		List<Item> itens = criarObjetos(quantidadeColumn, referenciaColumn,0);
		verificarDuplicata(itens, naoDuplicados, duplicados);
		somarDuplicatas(naoDuplicados, duplicados, linhasExcluir, naoDuplicadosEAtualizados);
 		atualizarQuantidade(naoDuplicadosEAtualizados, quantidadeIndex);
		excluirLinhasDuplicadas(linhasExcluir);
		primeiraLinha=encontrarPrimeiraLinha();
		ultimaLinha=encontrarUltimaLinha();
		referenciaColumn = iterarColunaString(referenciaIndex);
	    quantidadeColumn = iterarColunaNumerica(quantidadeIndex);
	    itensAtualizados=criarObjetos(quantidadeColumn, referenciaColumn,0);
 	}
	public void eliminarDuplicatasESomarQuantidadesTerceiros(int quantidadeColuna, int referenciaColuna, int quantidadeTerceirosColuna ) {
 		int quantidadeIndex=quantidadeColuna-1;
		int referenciaIndex=referenciaColuna-1;
 		int quantidadeTerceirosIndex=quantidadeTerceirosColuna-1;
		Set<ItemTerceiros> naoDuplicados = new HashSet<ItemTerceiros>();
		List<ItemTerceiros> duplicados = new ArrayList<ItemTerceiros>();
		List<Integer> linhasExcluir = new ArrayList<Integer>();
		List<ItemTerceiros> naoDuplicadosEAtualizados = new ArrayList<ItemTerceiros>();
 		List<String> referenciaColumn = iterarColunaString(referenciaIndex);
  		List<Double> quantidadeColumn = iterarColunaNumerica(quantidadeIndex);
  		List<Double> quantidadeTerceirosColumn = iterarColunaNumerica(quantidadeTerceirosIndex);
 		List<ItemTerceiros> itens = criarObjetosComTerceiros(quantidadeColumn, referenciaColumn,quantidadeTerceirosColumn,0);
		verificarDuplicataTerceiros(itens, naoDuplicados, duplicados);
		somarDuplicatasTerceiros(naoDuplicados, duplicados, linhasExcluir, naoDuplicadosEAtualizados);
 		atualizarQuantidadeTerceiros(naoDuplicadosEAtualizados, quantidadeIndex,quantidadeTerceirosIndex);
		excluirLinhasDuplicadas(linhasExcluir);
		primeiraLinha=encontrarPrimeiraLinha();
		ultimaLinha=encontrarUltimaLinha();
		referenciaColumn = iterarColunaString(referenciaIndex);
	    quantidadeColumn = iterarColunaNumerica(quantidadeIndex);
	    itensAtualizados=criarObjetos(quantidadeColumn, referenciaColumn,0);
 	}
	

	private void sheetCriadoVerificacao() {
		if (sheet == null) {
			throw new IllegalStateException("A planilha não foi criada. Chame o método criarSheet primeiro.");
		}

	}

	
}
