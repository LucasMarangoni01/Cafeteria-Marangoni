package senai499.com.br.Cafe_Marangoni.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import senai499.com.br.Cafe_Marangoni.model.ItemPedido;
import senai499.com.br.Cafe_Marangoni.model.Pedido;
import senai499.com.br.Cafe_Marangoni.model.Produto;
import senai499.com.br.Cafe_Marangoni.repository.PedidoRepository;
import senai499.com.br.Cafe_Marangoni.repository.ProdutoRepository;
import senai499.com.br.Cafe_Marangoni.repository.UsuarioRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class PedidoApiController {

    public static final BigDecimal TAXA_ENTREGA = new BigDecimal("8.00");

    public record ItemReq(String produto, Integer quantidade) {}
    public record PedidoReq(String clienteNome, String telefone, String modalidade,
                            Integer mesa, String endereco, List<ItemReq> itens) {}

    private final PedidoRepository pedidos;
    private final ProdutoRepository produtos;
    private final UsuarioRepository usuarios;

    public PedidoApiController(PedidoRepository pedidos, ProdutoRepository produtos, UsuarioRepository usuarios) {
        this.pedidos = pedidos;
        this.produtos = produtos;
        this.usuarios = usuarios;
    }

    @PostMapping("/api/pedidos")
    public ResponseEntity<?> criar(@RequestBody PedidoReq req, HttpSession session) {
        if (req == null || req.clienteNome() == null || req.clienteNome().isBlank()
                || req.itens() == null || req.itens().isEmpty()) {
            return erro(400, "Informe seu nome e adicione ao menos um item.");
        }
        String telefone = req.telefone() == null ? "" : req.telefone().replaceAll("\\D", "");
        if (telefone.length() < 10 || telefone.length() > 11) return erro(400, "Informe um telefone com DDD.");

        String modalidade = req.modalidade() == null ? "" : req.modalidade().toUpperCase();
        if (!List.of("BALCAO", "MESA", "ENTREGA").contains(modalidade)) return erro(400, "Escolha como deseja receber.");
        if (modalidade.equals("MESA") && (req.mesa() == null || req.mesa() < 1 || req.mesa() > 15)) {
            return erro(400, "Selecione uma mesa de 1 a 15.");
        }
        if (modalidade.equals("ENTREGA") && (req.endereco() == null || req.endereco().trim().length() < 10)) {
            return erro(400, "Informe o endereço completo para entrega.");
        }

        Pedido pedido = new Pedido();
        pedido.setClienteNome(req.clienteNome().trim());
        pedido.setTelefone(telefone);
        pedido.setModalidade(modalidade);
        pedido.setMesa(modalidade.equals("MESA") ? req.mesa() : null);
        pedido.setEndereco(modalidade.equals("ENTREGA") ? req.endereco().trim() : null);
        pedido.setStatus("RECEBIDO");
        pedido.setCriadoEm(LocalDateTime.now());

        BigDecimal total = BigDecimal.ZERO;
        for (ItemReq item : req.itens()) {
            if (item.quantidade() == null || item.quantidade() < 1 || item.quantidade() > 50) {
                return erro(400, "Quantidade inválida (use de 1 a 50).");
            }
            Produto produto = item.produto() == null ? null : produtos.findBySlug(item.produto()).orElse(null);
            if (produto == null) return erro(400, "Produto inexistente: " + item.produto());

            ItemPedido linha = new ItemPedido();
            linha.setPedido(pedido);
            linha.setNomeProduto(produto.getNome());
            linha.setPrecoUnitario(produto.getPreco()); // preço vem do banco, nunca do navegador
            linha.setQuantidade(item.quantidade());
            pedido.getItens().add(linha);
            total = total.add(produto.getPreco().multiply(BigDecimal.valueOf(item.quantidade())));
        }
        if (modalidade.equals("ENTREGA")) total = total.add(TAXA_ENTREGA);
        pedido.setTotal(total);

        Long usuarioId = (Long) session.getAttribute("usuarioId");
        if (usuarioId != null) usuarios.findById(usuarioId).ifPresent(pedido::setUsuario);

        pedidos.save(pedido);
        return ResponseEntity.status(201).body(PedidoView.de(pedido));
    }

    @GetMapping("/api/pedidos/meus")
    public ResponseEntity<?> meus(HttpSession session) {
        Long usuarioId = (Long) session.getAttribute("usuarioId");
        if (usuarioId == null) return erro(401, "Não autenticado.");
        return ResponseEntity.ok(pedidos.findByUsuarioIdOrderByCriadoEmDesc(usuarioId)
                .stream().map(PedidoView::de).toList());
    }

    private static ResponseEntity<?> erro(int status, String mensagem) {
        return ResponseEntity.status(status).body(Map.of("mensagem", mensagem));
    }
}