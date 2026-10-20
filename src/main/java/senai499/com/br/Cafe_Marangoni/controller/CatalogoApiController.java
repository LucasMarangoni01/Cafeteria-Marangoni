package senai499.com.br.Cafe_Marangoni.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import senai499.com.br.Cafe_Marangoni.model.Produto;
import senai499.com.br.Cafe_Marangoni.repository.ProdutoRepository;

import java.math.BigDecimal;
import java.util.*;

@RestController
public class CatalogoApiController {

    public record ProdutoView(String id, String name, String description, BigDecimal price, String image) {}
    public record CategoriaView(String id, String title, String description, String image, List<ProdutoView> products) {}
    public record Catalogo(BigDecimal taxaEntrega, List<CategoriaView> categorias) {}

    private final ProdutoRepository produtos;

    public CatalogoApiController(ProdutoRepository produtos) {
        this.produtos = produtos;
    }

    @GetMapping("/api/catalogo")
    public Catalogo catalogo() {
        Map<String, CategoriaView> grupos = new LinkedHashMap<>();
        for (Produto p : produtos.findAllByOrderByIdAsc()) {
            CategoriaView c = grupos.computeIfAbsent(p.getCategoriaSlug(), k -> new CategoriaView(
                    p.getCategoriaSlug(), p.getCategoriaTitulo(), p.getCategoriaDescricao(), p.getImagem(), new ArrayList<>()));
            c.products().add(new ProdutoView(p.getSlug(), p.getNome(), p.getDescricao(), p.getPreco(), p.getImagem()));
        }
        return new Catalogo(PedidoApiController.TAXA_ENTREGA, new ArrayList<>(grupos.values()));
    }
}