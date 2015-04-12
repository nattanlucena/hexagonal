package teste.es;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import teste.es.domain.Produto;
import teste.es.domain.ProdutoService;
import teste.es.generic.event.CreateRequestEvent;
import teste.es.generic.event.CreateResponseEvent;
import teste.es.generic.event.DeleteRequestEvent;
import teste.es.generic.event.DeleteResponseEvent;
import teste.es.generic.event.ListRequestEvent;
import teste.es.generic.event.ListResponseEvent;
import teste.es.generic.event.UpdateRequestEvent;
import teste.es.generic.event.UpdateResponseEvent;
import teste.es.generic.event.ViewRequestEvent;
import teste.es.generic.event.ViewResponseEvent;
import teste.es.rest.ProdutoController;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProdutoURITest {

	MockMvc mockMvc;

	@InjectMocks
	ProdutoController controller;

	@Mock
	ProdutoService service;

	private Produto produto = ProdutoData.produto1();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = standaloneSetup(controller).build();

		produto.setId(1);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void test1ListProdutos() throws Exception {

		when(service.request(any(ListRequestEvent.class))).thenReturn(
				new ListResponseEvent<Produto>(ProdutoData.produtos()));

		mockMvc.perform(
				MockMvcRequestBuilders.get("/produtos").accept(
						MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].nome").value("Mesa"))
				.andExpect(jsonPath("$[0].descricao").value("Mesa para jantar"))
				.andExpect(jsonPath("$[1].id").value(2))
				.andExpect(jsonPath("$[1].nome").value("Cadeira"))
				.andExpect(jsonPath("$[1].descricao").value("Cadeira"))
				.andExpect(jsonPath("$[2].id").value(3))
				.andExpect(jsonPath("$[2].nome").value("Sofá"))
				.andExpect(jsonPath("$[2].descricao").value("Sofá em L"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test2CreateProduto() throws Exception {

		when(service.request(any(CreateRequestEvent.class))).thenReturn(
				new CreateResponseEvent<Produto>(produto));

		mockMvc.perform(
				MockMvcRequestBuilders.post("/produtos")
						.content(ProdutoData.produtoJson1())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.nome").value("Mesa"))
				.andExpect(jsonPath("$.descricao").value("Mesa para jantar"))
				.andExpect(jsonPath("$.preco").value(110.0))
				.andExpect(jsonPath("$.quantidade").value(1));
		;
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test3UpdateProduto() throws Exception {

		when(service.request(any(UpdateRequestEvent.class))).thenReturn(
				new UpdateResponseEvent<Produto>(produto));

		String uri = "/produtos/" + produto.getId();

		produto.setNome("Nome Mesa Changed!");
		produto.setDescricao("Descricao Alterada!");
		produto.setPreco(400);
		produto.setQuantidade(4);
		
		mockMvc.perform(
				MockMvcRequestBuilders.put(uri)
						.content(ProdutoData.produtoToJson(produto))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.nome").value("Nome Mesa Changed!"))
				.andExpect(jsonPath("$.descricao").value("Descricao Alterada!"))
				.andExpect(jsonPath("$.preco").value(400.0))
				.andExpect(jsonPath("$.quantidade").value(4));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test4ViewProduto() throws Exception {

		when(service.request(any(ViewRequestEvent.class))).thenReturn(
				new ViewResponseEvent<Produto>(produto.getId(), produto));

		String uri = "/produtos/" + produto.getId();
		mockMvc.perform(
				MockMvcRequestBuilders.get(uri).accept(
						MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.nome").value("Mesa"))
				.andExpect(jsonPath("$.descricao").value("Mesa para jantar"))
				.andExpect(jsonPath("$.quantidade").value(1))
				.andExpect(jsonPath("$.preco").value(110.0));
	}

	@Test
	public void test5DeleteProduto() throws Exception {

		when(service.request(any(DeleteRequestEvent.class))).thenReturn(
				new DeleteResponseEvent<Produto>(produto));

		String uri = "/produtos/" + produto.getId();
		mockMvc.perform(
				MockMvcRequestBuilders.delete(uri).accept(
						MediaType.APPLICATION_JSON)).andExpect(
				status().isNoContent());
	}

}
