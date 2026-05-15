package com._DM.E_commerce.Dtos;

import com._DM.E_commerce.Entity.Usuario;
import com._DM.E_commerce.Enum.Role;

import java.util.UUID;

public record UsuarioDTO(
        UUID id,
        String nome,
        String email,
        String telefone,
        String imgUrl,
        Role roles
) {
    public UsuarioDTO(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getImgUrl(),
                usuario.getRoles()
        );
    }
}
