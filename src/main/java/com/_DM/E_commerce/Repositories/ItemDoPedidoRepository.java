package com._DM.E_commerce.Repositories;

import com._DM.E_commerce.Entity.ItemDoPedido;
import com._DM.E_commerce.Entity.ItemDoPedidoPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDoPedidoRepository extends JpaRepository<ItemDoPedido, ItemDoPedidoPK> {
}
