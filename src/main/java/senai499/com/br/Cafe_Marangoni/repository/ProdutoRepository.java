package senai499.com.br.Cafe_Marangoni.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senai499.com.br.Cafe_Marangoni.model.Produto;
import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findBySlug(String slug);
    List<Produto> findAllByOrderByIdAsc();
}