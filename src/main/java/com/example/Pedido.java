package com.example;

public class Pedido {
    private Long id;
    private String clienteId;
    private double valorTotal;

    public Pedido(Long id, String clienteId, double valorTotal) {
        this.id = id;
        this.clienteId = clienteId;
        this.valorTotal = valorTotal;
    }

    public Long getId() { return id; }
    public String getClienteId() { return clienteId; }
    public double getValorTotal() { return valorTotal; }
}

