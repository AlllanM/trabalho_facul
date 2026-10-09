package com.baozistore;

import com.baozistore.model.Cliente;
import com.baozistore.model.Pedido;
import com.baozistore.model.Produto;
import com.baozistore.repository.ClienteRepository;
import com.baozistore.repository.PedidoRepository;
import com.baozistore.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BaoziStoreApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    private Cliente clienteSalvo;
    private Produto produtoSalvo;
    private Pedido pedidoSalvo;

    @BeforeEach
    void setup() {
        pedidoRepository.deleteAll();
        clienteRepository.deleteAll();
        produtoRepository.deleteAll();

        clienteSalvo = clienteRepository.save(new Cliente("Allan Almeida4976216", LocalDate.of(2026, 10, 6)));
        produtoSalvo = produtoRepository.save(new Produto("Baozi Tradicional", new BigDecimal("8.50"), true));
        pedidoSalvo = pedidoRepository.save(new Pedido(clienteSalvo.getId(), produtoSalvo.getId(), 5));
    }

    @Test
    @DisplayName("Deve inicializar a aplicação sem erros")
    void contextLoads() {
    }

    // --- TESTES CLIENTE ---

    @Test
    @DisplayName("Deve criar um cliente com sucesso")
    void testCriarCliente() throws Exception {
        Cliente cliente = new Cliente("Maria Silva", LocalDate.of(2026, 1, 15));

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Maria Silva"))
                .andExpect(jsonPath("$.clienteDesde").value("2026-01-15"));
    }

    @Test
    @DisplayName("Deve listar todos os clientes")
    void testListarClientes() throws Exception {
        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].nome").value("Allan Almeida4976216"));
    }

    @Test
    @DisplayName("Deve buscar cliente por ID")
    void testBuscarClientePorId() throws Exception {
        mockMvc.perform(get("/clientes/" + clienteSalvo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(clienteSalvo.getId()))
                .andExpect(jsonPath("$.nome").value("Allan Almeida4976216"));
    }

    @Test
    @DisplayName("Deve retornar 404 ao buscar cliente inexistente")
    void testBuscarClienteInexistente() throws Exception {
        mockMvc.perform(get("/clientes/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @DisplayName("Deve atualizar um cliente existente")
    void testAtualizarCliente() throws Exception {
        Cliente clienteAtualizado = new Cliente("Allan Almeida Editado", LocalDate.of(2026, 10, 6));

        mockMvc.perform(put("/clientes/" + clienteSalvo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Allan Almeida Editado"));
    }

    @Test
    @DisplayName("Deve deletar um cliente")
    void testDeletarCliente() throws Exception {
        mockMvc.perform(delete("/clientes/" + clienteSalvo.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/clientes/" + clienteSalvo.getId()))
                .andExpect(status().isNotFound());
    }

    // --- TESTES PRODUTO ---

    @Test
    @DisplayName("Deve criar um produto com sucesso")
    void testCriarProduto() throws Exception {
        Produto produto = new Produto("Baozi de Carne", new BigDecimal("10.00"), true);

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Baozi de Carne"))
                .andExpect(jsonPath("$.preco").value(10.00))
                .andExpect(jsonPath("$.estoque").value(true));
    }

    @Test
    @DisplayName("Deve listar todos os produtos")
    void testListarProdutos() throws Exception {
        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].nome").value("Baozi Tradicional"));
    }

    @Test
    @DisplayName("Deve buscar produto por ID")
    void testBuscarProdutoPorId() throws Exception {
        mockMvc.perform(get("/produtos/" + produtoSalvo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(produtoSalvo.getId()))
                .andExpect(jsonPath("$.nome").value("Baozi Tradicional"));
    }

    @Test
    @DisplayName("Deve atualizar um produto")
    void testAtualizarProduto() throws Exception {
        Produto produtoAtualizado = new Produto("Baozi Tradicional Gourmet", new BigDecimal("12.50"), true);

        mockMvc.perform(put("/produtos/" + produtoSalvo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Baozi Tradicional Gourmet"))
                .andExpect(jsonPath("$.preco").value(12.50));
    }

    @Test
    @DisplayName("Deve deletar um produto")
    void testDeletarProduto() throws Exception {
        mockMvc.perform(delete("/produtos/" + produtoSalvo.getId()))
                .andExpect(status().isNoContent());
    }

    // --- TESTES PEDIDO ---

    @Test
    @DisplayName("Deve criar um pedido com cliente e produto válidos")
    void testCriarPedido() throws Exception {
        Pedido pedido = new Pedido(clienteSalvo.getId(), produtoSalvo.getId(), 3);

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.clienteId").value(clienteSalvo.getId()))
                .andExpect(jsonPath("$.produtoId").value(produtoSalvo.getId()))
                .andExpect(jsonPath("$.quantidade").value(3));
    }

    @Test
    @DisplayName("Deve retornar 404 ao criar pedido com cliente inexistente")
    void testCriarPedidoClienteInexistente() throws Exception {
        Pedido pedido = new Pedido(99999L, produtoSalvo.getId(), 5);

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Cliente não encontrado com ID: 99999"));
    }

    @Test
    @DisplayName("Deve retornar 404 ao criar pedido com produto inexistente")
    void testCriarPedidoProdutoInexistente() throws Exception {
        Pedido pedido = new Pedido(clienteSalvo.getId(), 99999L, 5);

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Produto não encontrado com ID: 99999"));
    }

    @Test
    @DisplayName("Deve listar todos os pedidos")
    void testListarPedidos() throws Exception {
        mockMvc.perform(get("/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].quantidade").value(5));
    }

    @Test
    @DisplayName("Deve buscar pedido por ID")
    void testBuscarPedidoPorId() throws Exception {
        mockMvc.perform(get("/pedidos/" + pedidoSalvo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(pedidoSalvo.getId()))
                .andExpect(jsonPath("$.quantidade").value(5));
    }

    @Test
    @DisplayName("Deve deletar um pedido")
    void testDeletarPedido() throws Exception {
        mockMvc.perform(delete("/pedidos/" + pedidoSalvo.getId()))
                .andExpect(status().isNoContent());
    }
}
