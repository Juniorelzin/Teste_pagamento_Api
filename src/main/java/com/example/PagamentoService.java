package com.example;

public class PagamentoService {
    private PagamentoAPI pagamentoAPI;

    public PagamentoService(PagamentoAPI pagamentoAPI) {
        this.pagamentoAPI = pagamentoAPI;
    }

    public boolean processarPagamento(Pedido pedido) {
        // Verifica o saldo do cliente
        if (!pagamentoAPI.validarSaldo(pedido.getClienteId(), pedido.getValorTotal())) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        // Autoriza o pagamento
        return pagamentoAPI.autorizarPagamento(pedido);
    }
}

