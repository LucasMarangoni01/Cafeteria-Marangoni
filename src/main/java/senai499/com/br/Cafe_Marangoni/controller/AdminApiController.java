package senai499.com.br.Cafe_Marangoni.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import senai499.com.br.Cafe_Marangoni.model.Pedido;
import senai499.com.br.Cafe_Marangoni.repository.PedidoRepository;

import java.util.List;
import java.util.Map;

@RestController
public class AdminApiController {

    public record StatusReq(String status) {}

    private static final List<String> STATUS_VALIDOS =
            List.of("RECEBIDO", "EM_PREPARO", "PRONTO", "FINALIZADO", "CANCELADO");

    private final PedidoRepository pedidos;

    public AdminApiController(PedidoRepository pedidos) {
        this.pedidos = pedidos;
    }

    @GetMapping("/api/admin/pedidos")
    public ResponseEntity<?> listar(HttpSession session) {
        if (!admin(session)) return erro(403, "Acesso restrito ao administrador.");
        return ResponseEntity.ok(pedidos.findAllByOrderByCriadoEmDesc().stream().map(PedidoView::de).toList());
    }

    @PatchMapping("/api/admin/pedidos/{id}/status")
    public ResponseEntity<?> mudarStatus(@PathVariable Long id, @RequestBody StatusReq req, HttpSession session) {
        if (!admin(session)) return erro(403, "Acesso restrito ao administrador.");
        if (req == null || !STATUS_VALIDOS.contains(req.status())) return erro(400, "Status inválido.");
        Pedido pedido = pedidos.findById(id).orElse(null);
        if (pedido == null) return erro(404, "Pedido não encontrado.");
        pedido.setStatus(req.status());
        pedidos.save(pedido);
        return ResponseEntity.ok(PedidoView.de(pedido));
    }

    private static boolean admin(HttpSession session) {
        return "ADMIN".equals(session.getAttribute("perfil"));
    }

    private static ResponseEntity<?> erro(int status, String mensagem) {
        return ResponseEntity.status(status).body(Map.of("mensagem", mensagem));
    }
}