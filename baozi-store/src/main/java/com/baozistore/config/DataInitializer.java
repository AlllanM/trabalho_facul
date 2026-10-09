package com.baozistore.config;

import com.baozistore.model.Cliente;
import com.baozistore.model.Pedido;
import com.baozistore.model.Produto;
import com.baozistore.repository.ClienteRepository;
import com.baozistore.repository.PedidoRepository;
import com.baozistore.repository.ProdutoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(
            ClienteRepository clienteRepository,
            ProdutoRepository produtoRepository,
            PedidoRepository pedidoRepository) {
        return args -> {
            // Se o banco estiver vazio, inicializa com os dados do estudo de caso da faculdade
            if (clienteRepository.count() == 0) {
                Cliente clienteDemo = new Cliente("Allan Almeida4976216", LocalDate.of(2026, 10, 6));
                clienteRepository.save(clienteDemo);

                Produto produtoDemo = new Produto("Baozi Tradicional", new BigDecimal("8.50"), true);
                produtoRepository.save(produtoDemo);

                Pedido pedidoDemo = new Pedido(clienteDemo.getId(), produtoDemo.getId(), 5);
                pedidoRepository.save(pedidoDemo);

                System.out.println(">>> [Baozi Store] Dados iniciais carregados com sucesso!");
                System.out.println(">>> Cliente: Allan Almeida4976216");
                System.out.println(">>> Produto: Baozi Tradicional (R$ 8,50)");
                System.out.println(">>> Pedido: 5 unidades de Baozi Tradicional");
            }
        };
    }
}
