package com.ms.oauth2.usuarios.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    @Column(unique = true,length = 20) 
	private String username;
    @Column(length = 60) 
	private String password;
	private Boolean enabled;
	private String nombre;
	private String apellido;
	@Column(unique = true,length = 100) 
	private String email;
	
	@ManyToMany(fetch = FetchType.LAZY)
	//personalizar tabla intermedia
	//definir nombre tabla intermedia
	@JoinTable(name = "usuarios_roles"
	//definir id de tabla principal
	,joinColumns = @JoinColumn(name="usuario_id")
	//definir id de tabla secundaria
	,inverseJoinColumns = @JoinColumn(name="role_id")
	//definir restricciones para que sea unico el par de ids
	,uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id","role_id"})})
	@Singular("roles")
	private List<Rol> roles;

}
