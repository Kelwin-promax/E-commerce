package com._DM.E_commerce.Repository;

import com._DM.E_commerce.Entity.ItemCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, UUID> {
    Optional<ItemCarrinho> findByCarrinhoIdAndProdutoId(UUID carrinhoId, UUID produtoId);
}
