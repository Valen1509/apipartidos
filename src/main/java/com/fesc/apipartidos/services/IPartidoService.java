package com.fesc.apipartidos.services;

import com.fesc.apipartidos.shared.PartidoDto;

import java.util.List;

public interface IPartidoService {

    PartidoDto crearPartido(PartidoDto partidoCrearDto);

    List<PartidoDto> partidosCreados(); //creamos el metodo lista

    PartidoDto detallePartido(String id);

    PartidoDto actualizarPartido(String idPartido, PartidoDto partidoActualizarDto);

    void eliminarPartido(String idpartido, long idUsuario);

}
