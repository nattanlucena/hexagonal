package teste.es.rest;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import teste.es.domain.Produto;
import teste.es.generic.RestUtil;

@XmlRootElement
public class ProdutoRest implements Serializable {

	private static final long serialVersionUID = 4431345413823505552L;

	private Integer id;
	private String nome;
	private String descricao;
	private double preco;
	private int quantidade;

	public ProdutoRest() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public static ProdutoRest fromCore(Produto produto) {
		return RestUtil.convert(produto, new ProdutoRest());
	}

	public Produto toCore() {
		return RestUtil.convert(this, new Produto());
	}

	@Override
	public String toString() {
		return "ProdutoRest [id=" + id + ", nome=" + nome + ", descricao="
				+ descricao + ", preco=" + preco + ", quantidade=" + quantidade
				+ "]";
	}

}
