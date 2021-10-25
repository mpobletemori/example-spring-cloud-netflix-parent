package com.ms.oauth2.usuarios.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ms.oauth2.usuarios.models.entity.Usuario;

public interface UsuarioDAO extends PagingAndSortingRepository<Usuario,Long>{
    Usuario findByUserName(String userName);
}
