package br.com.joao.automatizado.automatizado.model;

import java.util.Objects;

public class ItemTerceiros extends Item {
int linha;
String referencia;
double quantidade;
double quantidadeTerceiros;
boolean temTerceiros;

	public ItemTerceiros(int linha, String referencia, double quantidade, double quantidadeTerceiros) {
		super(linha, referencia, quantidade);
		this.linha=linha;
		this.referencia=referencia;
		this.quantidade=quantidade;
		this.quantidadeTerceiros=quantidadeTerceiros;
		temTerceiros=quantidadeTerceiros>0;
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
	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}
	public void addQuantidadeTerceiros(double quantidadeTerceiros) {
		this.quantidadeTerceiros+=quantidadeTerceiros;
	}
	public double getQuantidadeTerceiros() {
		return quantidadeTerceiros;
	}
	public void setQuantidadeTerceiros(double quantidadeTerceiros) {
		this.quantidadeTerceiros = quantidadeTerceiros;
	}
	public void addQuantidade(double quantidade) {
		 this.quantidade+=quantidade;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(quantidadeTerceiros, referencia,temTerceiros);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		ItemTerceiros other = (ItemTerceiros) obj;

		if((Objects.equals(referencia, other.referencia)&&this.quantidadeTerceiros>0&&other.quantidadeTerceiros>0) ) {
			return true;
		}
		else if(Objects.equals(referencia, other.referencia)&&(this.quantidadeTerceiros==0&&other.quantidadeTerceiros==0)){
			return true;
		}
		return false;
	}
	

	
}
