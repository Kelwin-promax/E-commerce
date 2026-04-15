package com._DM.E_commerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface Pagamento extends JpaRepository<com._DM.E_commerce.Entity.Pagamento, UUID> {
}
