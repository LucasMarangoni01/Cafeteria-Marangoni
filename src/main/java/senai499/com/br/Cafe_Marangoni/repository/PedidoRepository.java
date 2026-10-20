package senai499.com.br.Cafe_Marangoni.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senai499.com.br.Cafe_Marangoni.model.Pedido;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuarioIdOrderByCriadoEmDesc(Long usuarioId);
    List<Pedido> findAllByOrderByCriadoEmDesc();
}