package com.fesc.apipartidos.services;

import com.fesc.apipartidos.data.entidades.EquipoEntity;
import com.fesc.apipartidos.data.entidades.PartidosEntity;
import com.fesc.apipartidos.data.entidades.UsuarioEntity;
import com.fesc.apipartidos.data.repositorios.IEquipoRepository;
import com.fesc.apipartidos.data.repositorios.IPartidoRepository;
import com.fesc.apipartidos.data.repositorios.IUsuarioRepository;
import com.fesc.apipartidos.shared.PartidoDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PartidoService implements IPartidoService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IUsuarioRepository iUsuarioRepository;

    @Autowired
    IEquipoRepository iEquipoRepository;

    @Autowired
    IPartidoRepository iPartidoRepository;

    @Override
    public PartidoDto crearPartido(PartidoDto partidoCrearDto) {

        UsuarioEntity usuarioEntity= iUsuarioRepository.findByUsername(partidoCrearDto.getUsername());

        EquipoEntity equipoEntityLocal= iEquipoRepository.findById(partidoCrearDto.getEquipoLocal());

        EquipoEntity equipoEntityVisitante= iEquipoRepository.findById(partidoCrearDto.getEquipoVisitante());

        PartidosEntity partidosEntity= new PartidosEntity();
        partidosEntity.setIdPartido(UUID.randomUUID().toString());
        partidosEntity.setFecha(partidoCrearDto.getFecha());
        partidosEntity.setGolesLocal("0");
        partidosEntity.setGolesVisitante("0");
        partidosEntity.setUsuarioEntity(usuarioEntity);
        partidosEntity.setEquipoEntityLocal(equipoEntityLocal);
        partidosEntity.setEquipoEntityVisitante(equipoEntityVisitante);

        PartidosEntity partidosEntityCreado= iPartidoRepository.save(partidosEntity);

        PartidoDto partidoDto= modelMapper.map(partidosEntityCreado, PartidoDto.class);

        return partidoDto;
    }

    @Override
    public List<PartidoDto> partidosCreados() {

        List<PartidosEntity> partidosEntityList= iPartidoRepository.partidosCreados();

        List<PartidoDto> partidoDtoList= new ArrayList<>();

        for (PartidosEntity partidosEntity : partidosEntityList) {
            PartidoDto partidoDto= modelMapper.map(partidosEntity, PartidoDto.class);
            partidoDtoList.add(partidoDto);
        }

        return partidoDtoList;
    }

    @Override
    public PartidoDto detallePartido(String id) {

        PartidosEntity partidosEntity= iPartidoRepository.findByIdPartido(id); //obtener una consulta que se va hacer al repositorio, en este caso bucar el partido por el id

        PartidoDto partidoDto= modelMapper.map(partidosEntity, PartidoDto.class); //mapeamos "partidosEntity" a "partidoDto"

        return partidoDto;
    }

    @Override
    public PartidoDto actualizarPartido(String idPartido, PartidoDto partidoActualizarDto) {

        PartidosEntity partidosEntity= iPartidoRepository.findByIdPartido(idPartido); //bucar la informacon del partido donde esta parado

        UsuarioEntity usuarioEntity= iUsuarioRepository.findByUsername(partidoActualizarDto.getUsername()); //bucar la informacion del usuario

        if (partidosEntity.getUsuarioEntity().getId() != usuarioEntity.getId()){ //validar si el usuario que esta logeado es el mismo usuario que creo el partido
            throw new RuntimeException("No se puede realizar esta accion");
        }

        partidosEntity.setGolesLocal(partidoActualizarDto.getGolesLocal());
        partidosEntity.setGolesVisitante(partidoActualizarDto.getGolesVisitante());

        PartidosEntity partidosEntityActulizado= iPartidoRepository.save(partidosEntity);

        PartidoDto partidoDto= modelMapper.map(partidosEntityActulizado, PartidoDto.class);

        return partidoDto;
    }

    @Override
    public void eliminarPartido(String idpartido, long idUsuario) {

        PartidosEntity partidosEntity= iPartidoRepository.findByIdPartido(idpartido);

        if (partidosEntity.getUsuarioEntity().getId() != idUsuario){
                throw new RuntimeException("No se puede realizar esta accion");
        }

        iPartidoRepository.delete(partidosEntity);

    }


}
