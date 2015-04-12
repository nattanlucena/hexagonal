package teste.es.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

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

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService service;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<ProdutoRest> listProdutos() {

		ListResponseEvent<Produto> response = service
				.request(new ListRequestEvent<Produto>());

		List<ProdutoRest> Produtos = new ArrayList<ProdutoRest>();

		for (Produto Produto : response.getObjects()) {
			Produtos.add(ProdutoRest.fromCore(Produto));
		}

		return Produtos;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ProdutoRest> createProduto(
			@RequestBody ProdutoRest produtoRest, UriComponentsBuilder builder) {

		CreateRequestEvent<Produto> request = new CreateRequestEvent<Produto>(
				produtoRest.toCore());

		CreateResponseEvent<Produto> response;

		try {
			response = service.request(request);
		} catch (ConstraintViolationException exception) {
			Logger.getLogger(ProdutoController.class.getSimpleName()).
			log(Level.ALL, exception.getMessage(), exception);
			return new ResponseEntity<ProdutoRest>(HttpStatus.BAD_REQUEST);
		}

		ProdutoRest createdProdutoRest = ProdutoRest.fromCore(response.getObject());			

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/Produto/id")
				.buildAndExpand(createdProdutoRest.getId().toString()).toUri());

		return new ResponseEntity<ProdutoRest>(createdProdutoRest, headers,
				HttpStatus.CREATED);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<ProdutoRest> viewProduto(@PathVariable Integer id) {

		ViewResponseEvent<Produto> response = service
				.request(new ViewRequestEvent<Produto>(id));

		if (!response.isEntityFound()) {
			return new ResponseEntity<ProdutoRest>(HttpStatus.NOT_FOUND);
		}

		ProdutoRest produtoRest = ProdutoRest.fromCore(response.getObject());

		return new ResponseEntity<ProdutoRest>(produtoRest, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<ProdutoRest> deleteProduto(@PathVariable Integer id) {

		DeleteResponseEvent<Produto> response = service
				.request(new DeleteRequestEvent(id));

		if (!response.isEntityFound()) {
			return new ResponseEntity<ProdutoRest>(HttpStatus.NOT_FOUND);
		}

		ProdutoRest produtoRest = ProdutoRest.fromCore(response.getObject());

		if (!response.isOperationCompleted()) {
			return new ResponseEntity<ProdutoRest>(produtoRest, HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity<ProdutoRest>(produtoRest, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<ProdutoRest> updateProduto(@PathVariable Integer id,
			@RequestBody ProdutoRest ProdutoRest) {
		
		UpdateResponseEvent<Produto> response;
		try {
			response = service.request(new UpdateRequestEvent<Produto>(id,
					ProdutoRest.toCore()));
		} catch (ConstraintViolationException exception) {
			Logger.getLogger(ProdutoController.class.getSimpleName()).
			log(Level.ALL, exception.getMessage(), exception);
			return new ResponseEntity<ProdutoRest>(HttpStatus.BAD_REQUEST);
		}

		return generateReturn(ProdutoRest, response);
	}

	private ResponseEntity<ProdutoRest> generateReturn(ProdutoRest produtoRest,
			UpdateResponseEvent<Produto> response) {
		if (!response.isEntityFound()) {
			return new ResponseEntity<ProdutoRest>(HttpStatus.NOT_FOUND);
		}

		ProdutoRest updatedProdutoRest = ProdutoRest.fromCore(response.getObject());

		if (!response.isOperationCompleted()) {
			return new ResponseEntity<ProdutoRest>(produtoRest,
					HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity<ProdutoRest>(updatedProdutoRest, HttpStatus.OK);
	}
}
