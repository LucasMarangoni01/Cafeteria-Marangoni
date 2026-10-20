package senai499.com.br.Cafe_Marangoni.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Getter @Setter @NoArgsConstructor
public class Produto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true) private String slug; // ex.: "espresso"
    @Column(nullable = false) private String nome;
    @Column(length = 400) private String descricao;
    @Column(nullable = false, precision = 10, scale = 2) private BigDecimal preco;
    private String imagem;
    private String categoriaSlug;
    private String categoriaTitulo;
    private String categoriaDescricao;
}