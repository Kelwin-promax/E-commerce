package com._DM.E_commerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface Pedido extends JpaRepository<com._DM.E_commerce.Entity.Pedido, UUID> {
}
