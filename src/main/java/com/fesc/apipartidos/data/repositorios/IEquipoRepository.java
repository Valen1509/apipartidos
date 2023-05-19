package com.fesc.apipartidos.data.repositorios;

import com.fesc.apipartidos.data.entidades.EquipoEntity;
import com.fesc.apipartidos.data.entidades.UsuarioEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEquipoRepository extends CrudRepository<EquipoEntity, Long> {

    EquipoEntity findById(long id);
}
