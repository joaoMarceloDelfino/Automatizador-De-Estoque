package br.com.joao.automatizador_excel.model;

import java.util.Collection;
import java.util.Objects;

public class Item {
private int linha;
private String referencia;
private Double quantidade;

public Item(int linha, String referencia, Double quantidade) {
	this.linha = linha;
	this.referencia = referencia;
	this.quantidade = quantidade;
}
public int getLinha() {
	return linha;
}
public void setLinha(int linha) {
	this.linha = linha;
}
public String getReferencia() {
	return referencia;
}
public void setReferencia(String referencia) {
	this.referencia = referencia;
}
public double getQuantidade() {
	return quantidade;
}
public void setQuantidade(Double quantidade) {
	this.quantidade = quantidade;
}
public void addQuantidade(Double quantidade) {
	 this.quantidade=quantidade;
}
@Override
public int hashCode() {
	return Objects.hash(referencia);
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Item other = (Item) obj;
	return Objects.equals(referencia, other.referencia);
}


}
