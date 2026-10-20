package senai499.com.br.Cafe_Marangoni.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Pedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne private Usuario usuario; // null = pedido de visitante
    private String clienteNome;
    private String telefone;
    private String modalidade; // BALCAO, MESA ou ENTREGA
    private Integer mesa;
    @Column(length = 300) private String endereco;
    private BigDecimal total;
    private String status;     // RECEBIDO, EM_PREPARO, PRONTO, FINALIZADO, CANCELADO
    private LocalDateTime criadoEm;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ItemPedido> itens = new ArrayList<>();
}