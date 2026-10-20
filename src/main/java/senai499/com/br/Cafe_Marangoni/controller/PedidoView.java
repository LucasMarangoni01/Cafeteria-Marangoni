package senai499.com.br.Cafe_Marangoni.controller;

import senai499.com.br.Cafe_Marangoni.model.Pedido;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record PedidoView(Long id, String numero, String clienteNome, String telefone, String modalidade,
                         Integer mesa, String endereco, List<String> itens, BigDecimal total,
                         String status, String criadoEm) {

    public static PedidoView de(Pedido p) {
        return new PedidoView(
                p.getId(), String.format("#%04d", p.getId()), p.getClienteNome(), p.getTelefone(),
                p.getModalidade(), p.getMesa(), p.getEndereco(),
                p.getItens().stream().map(i -> i.getQuantidade() + " × " + i.getNomeProduto()).toList(),
                p.getTotal(), p.getStatus(),
                p.getCriadoEm().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }
}