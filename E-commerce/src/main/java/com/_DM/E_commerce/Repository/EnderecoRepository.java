package com._DM.E_commerce.Repository;

import com._DM.E_commerce.Entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {
    List<Endereco> findByClienteId(UUID clienteId);
}
