package teste.es.data;


import org.springframework.data.jpa.repository.JpaRepository;

import teste.es.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}
