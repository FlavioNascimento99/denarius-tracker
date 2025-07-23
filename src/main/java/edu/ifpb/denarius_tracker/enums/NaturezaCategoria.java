package edu.ifpb.denarius_tracker.enums;

public enum NaturezaCategoria {
	
	ENTRADA("Entrada"),
	SAIDA("Saida"),
	INVESTIMENTO("Investimento");
	
	private final String descricao;
	
	NaturezaCategoria(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
