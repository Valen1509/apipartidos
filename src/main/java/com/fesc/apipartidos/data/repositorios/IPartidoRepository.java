package com.fesc.apipartidos.data.repositorios;


import com.fesc.apipartidos.data.entidades.PartidosEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPartidoRepository extends PagingAndSortingRepository<PartidosEntity, Long> {

    List<PartidosEntity> getByUsuarioEntityIdOrderByCreadoDesc(long usuarioEntityId);

    @Query(nativeQuery = true, value = "SELECT * FROM partido ORDER BY creado DESC  LIMIT 10") // se especifica que es una consulta nativa lo que significa que se escribio en el leguaje SQL, e indica que va seleccionartodos los datos de la tabla "partido" y los ordena de forma descendente degun la columna "creado" y limila la salida a losprimeros 10 resultados
    List<PartidosEntity> partidosCreados();

    PartidosEntity findByIdPartido(String id);
}