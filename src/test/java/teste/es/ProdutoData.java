package teste.es;

import java.util.LinkedList;
import java.util.List;

import teste.es.domain.Produto;
import teste.es.rest.ProdutoRest;


public class ProdutoData {

	public static Produto buildProduto(Integer id, String nome, String descricao, int quantidade, double preco) {
		Produto produto = new Produto();
		produto.setId(id);
		produto.setNome(nome);
		produto.setDescricao(descricao);
		produto.setPreco(preco);
		produto.setQuantidade(quantidade);
		return produto;
	}

	public static String produtoToJson(Produto produto) {
		return TestUtil.toJson(ProdutoRest.fromCore(produto));
	}

	public static Produto produto1() {
		return buildProduto(null, "Mesa", "Mesa para jantar", 1, 110.0);
	}

	public static Produto produto2() {
		return buildProduto(null, "Cadeira", "Cadeira", 4, 10.0);
	}

	public static Produto produto3() {
		return buildProduto(null, "Sofá", "Sofá em L", 1, 1000.0);
	}

	public static Produto produto4() {
		return buildProduto(null, "Estante", "Estante pequena", 2, 200);
	}

	public static Produto produto5() {
		return buildProduto(null, "TV", "Smartv", 1, 100.0);
	}

	public static ProdutoRest produtoRest1() {
		return ProdutoRest.fromCore(produto1());
	}

	public static ProdutoRest produtoRest2() {
		return ProdutoRest.fromCore(produto2());
	}

	public static ProdutoRest produtoRest3() {
		return ProdutoRest.fromCore(produto3());
	}

	public static String produtoJson1() {
		ProdutoRest produto = produtoRest1();
		produto.setId(13);
		return TestUtil.toJson(produto);
	}

	public static List<Produto> produtos() {
		List<Produto> produtos = new LinkedList<Produto>();
		Produto p1 = produto1();
		p1.setId(1);
		produtos.add(p1);
		Produto p2 = produto2();
		p2.setId(2);
		produtos.add(p2);
		Produto p3 = produto3();
		p3.setId(3);
		produtos.add(p3);
		return produtos;
	}

}
