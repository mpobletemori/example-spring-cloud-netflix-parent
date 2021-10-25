package com.ms.oauth2.usuarios.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ms.oauth2.usuarios.models.entity.Usuario;

@RepositoryRestResource(path="usuarios")
public interface UsuarioDAO extends PagingAndSortingRepository<Usuario,Long>{
    Usuario findByUsername(String username);
}
