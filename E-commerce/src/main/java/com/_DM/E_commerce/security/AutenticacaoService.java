package com._DM.E_commerce.security;

import com._DM.E_commerce.Repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public AutenticacaoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = usuarioRepository.findByLogin(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("Usuario nao encontrado: " + username);
        }
        return userDetails;
    }
}
