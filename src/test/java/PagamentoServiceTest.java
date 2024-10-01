
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apiguardian.api.API;

import com.example.Pedido;
import com.example.PagamentoAPI;
import com.example.PagamentoService;

public class PagamentoServiceTest {

    private PagamentoAPI pagamentoAPI;
    private PagamentoService pagamentoService;

    @BeforeEach
    public void setUp() {
     
        pagamentoAPI = Mockito.mock(PagamentoAPI.class);
  
        pagamentoService = new PagamentoService(pagamentoAPI);
    }

    @Test
    public void TestProcessamentoPagamentoComSaldoSuficiente() {
        //GIVEN
        Pedido newPedido = new Pedido(1l, "10", 22.90);
        when(pagamentoAPI.validarSaldo(newPedido.getClienteId(), newPedido.getValorTotal())).thenReturn(true);
        when(pagamentoAPI.autorizarPagamento(newPedido)).thenReturn(true);

        // WHEN
        boolean result = pagamentoService.processarPagamento(newPedido);

        // THEN
        verify(pagamentoAPI).validarSaldo(newPedido.getClienteId(), newPedido.getValorTotal());

        assertTrue(result);

    }


    @Test
    public void TestPagamentoComSaldoInsuficiente() {
        //GIVEN
        Pedido newPedido = new Pedido(1l, "10", 22.90);
        when(pagamentoAPI.validarSaldo(newPedido.getClienteId(), newPedido.getValorTotal())).thenReturn(false);
        

        // WHEN
        

        // THEN  
        assertThrows(IllegalArgumentException.class, () -> {
            pagamentoService.processarPagamento(newPedido);
        });
        
        verify(pagamentoAPI, times(1)).validarSaldo(newPedido.getClienteId(), newPedido.getValorTotal());
        verify(pagamentoAPI,never()).autorizarPagamento(newPedido);


    }

    @Test
    public void TestFalhaComunicacaoAPIPagamento() {
        //GIVEN
        Pedido newPedido = new Pedido(1l, "10", 22.90);
        when(pagamentoAPI.validarSaldo(newPedido.getClienteId(), newPedido.getValorTotal())).thenThrow(RuntimeException.class);
        

        // WHEN
        

        // THEN  
        assertThrows(RuntimeException.class, () -> {
            pagamentoService.processarPagamento(newPedido);
        });
        
        verify(pagamentoAPI, times(1)).validarSaldo(newPedido.getClienteId(), newPedido.getValorTotal());
        verify(pagamentoAPI,never()).autorizarPagamento(newPedido);


    }
}

