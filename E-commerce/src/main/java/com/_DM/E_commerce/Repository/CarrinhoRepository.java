package com._DM.E_commerce.Repository;

import com._DM.E_commerce.Entity.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CarrinhoRepository extends JpaRepository<Carrinho, UUID> {
    Optional<Carrinho> findByClienteId(UUID clienteId);
}
