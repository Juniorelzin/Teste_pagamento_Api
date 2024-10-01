package com.example;

public interface PagamentoAPI {
    boolean validarSaldo(String clienteId, double valor);
    boolean autorizarPagamento(Pedido pedido);
}

