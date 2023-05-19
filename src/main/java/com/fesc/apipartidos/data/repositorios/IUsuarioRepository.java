package com.fesc.apipartidos.data.repositorios;

import com.fesc.apipartidos.data.entidades.UsuarioEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository //indicar que es un repositorio que es el que tiene las consulta SQL
public interface IUsuarioRepository extends CrudRepository<UsuarioEntity, Long> { //se extiende de una clase que contiene el CRUD y se especifica con que entidad se va a trabajar y el tipo de id

    public UsuarioEntity findByEmail(String email);
    public UsuarioEntity findByUsername(String username);
}
