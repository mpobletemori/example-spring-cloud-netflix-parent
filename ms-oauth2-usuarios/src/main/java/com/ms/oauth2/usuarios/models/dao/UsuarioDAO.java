package com.ms.oauth2.usuarios.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.ms.commons.oauth2.usuarios.models.entity.Usuario;



@RepositoryRestResource(path="usuarios")
public interface UsuarioDAO extends PagingAndSortingRepository<Usuario,Long>{
	@RestResource(path = "buscar-username")
    Usuario findByUsername(@Param("username") String username);
}
