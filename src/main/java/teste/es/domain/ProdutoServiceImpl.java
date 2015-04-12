package teste.es.domain;


import org.springframework.stereotype.Service;

import teste.es.data.ProdutoRepository;
import teste.es.generic.GenericServiceImpl;

@Service
public class ProdutoServiceImpl extends
		GenericServiceImpl<Produto, ProdutoRepository> implements ProdutoService {

}
