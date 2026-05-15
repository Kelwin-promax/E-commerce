package com._DM.E_commerce.Service;

import com._DM.E_commerce.Dtos.UsuarioCreateDTO;
import com._DM.E_commerce.Dtos.UsuarioDTO;
import com._DM.E_commerce.Entity.Usuario;
import com._DM.E_commerce.Repositories.UsuarioRepository;
import com._DM.E_commerce.Service.Exceptions.DatabaseException;
import com._DM.E_commerce.Service.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private ImageStorageService imageStorageService;

    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAll() {
        return repository.findAll().stream().map(UsuarioDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioDTO findById(UUID id) {
        Usuario entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return new UsuarioDTO(entity);
    }

    @Transactional
    public UsuarioDTO insert(UsuarioCreateDTO dto) {
        Usuario entity = new Usuario();
        entity.setNome(dto.nome());
        entity.setEmail(dto.email());
        entity.setTelefone(dto.telefone());
        entity.setSenha(dto.senha()); // In a real app, encrypt this!
        entity.setRoles(dto.role());
        entity = repository.save(entity);
        return new UsuarioDTO(entity);
    }

    @Transactional
    public UsuarioDTO update(UUID id, UsuarioCreateDTO dto) {
        try {
            Usuario entity = repository.getReferenceById(id);
            entity.setNome(dto.nome());
            entity.setEmail(dto.email());
            entity.setTelefone(dto.telefone());
            if (dto.senha() != null && !dto.senha().isBlank()) {
                entity.setSenha(dto.senha());
            }
            if (dto.role() != null) {
                entity.setRoles(dto.role());
            }
            entity = repository.save(entity);
            return new UsuarioDTO(entity);
        } catch (jakarta.persistence.EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(id);
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    @Transactional
    public UsuarioDTO uploadFoto(UUID id, MultipartFile foto) {
        Usuario entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        String oldImageUrl = entity.getImgUrl();
        String newImageUrl = imageStorageService.saveUserImage(id, foto);

        entity.setImgUrl(newImageUrl);
        entity = repository.save(entity);
        imageStorageService.deleteByPublicUrl(oldImageUrl);

        return new UsuarioDTO(entity);
    }
}
