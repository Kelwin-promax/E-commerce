package com._DM.E_commerce.Dtos;

import com._DM.E_commerce.Enum.Role;

public record UsuarioCreateDTO(
        String nome,
        String email,
        String telefone,
        String senha,
        Role role
) {}
