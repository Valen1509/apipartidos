package com.fesc.apipartidos.controllers;

import com.fesc.apipartidos.models.peticiones.PartidoActualizarRequestModel;
import com.fesc.apipartidos.models.peticiones.PartidoCrearRequestModel;
import com.fesc.apipartidos.models.respuestas.MensajeRestModel;
import com.fesc.apipartidos.models.respuestas.PartidoDataRestModel;
import com.fesc.apipartidos.services.IPartidoService;
import com.fesc.apipartidos.services.IUsuarioService;
import com.fesc.apipartidos.shared.PartidoDto;
import com.fesc.apipartidos.shared.UsuarioDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/partido")
public class PartidoController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IPartidoService iPartidoService;

    @Autowired
    IUsuarioService iUsuarioService;

    @PostMapping
    public PartidoDataRestModel crearPartido(@RequestBody PartidoCrearRequestModel partidoCrearRequestModel) {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();//obtenemos la autenticacion que esta actualmente en la plataforma

        String username= authentication.getPrincipal().toString(); //trae el username

        PartidoDto partidoCrearDto= modelMapper.map(partidoCrearRequestModel, PartidoDto.class);
        partidoCrearDto.setUsername(username);

        PartidoDto partidoDto= iPartidoService.crearPartido(partidoCrearDto);

        PartidoDataRestModel partidoDataRestModel=modelMapper.map(partidoDto, PartidoDataRestModel.class);

        return partidoDataRestModel;
    }

    @GetMapping
    public List<PartidoDataRestModel> partidosCreados(){

        List<PartidoDto> partidoDtoList= iPartidoService.partidosCreados();

        List<PartidoDataRestModel> partidoDataRestModelList= new ArrayList<>();

        for (PartidoDto partidoDto : partidoDtoList) {
            PartidoDataRestModel partidoDataRestModel= modelMapper.map(partidoDto, PartidoDataRestModel.class);
            partidoDataRestModelList.add(partidoDataRestModel);
        }

        return partidoDataRestModelList;
    }

    @GetMapping(path = "/{id}") //parametro path especifica la ruta de la solicitud que se debe manejar y se pone en llaves para especificar que se va a enviar un indicador
    public  PartidoDataRestModel detallePartido(@PathVariable String id){ //se envia el identificador del partido y "@PathVariable" lo recibe y lo guarda en "String id"

        PartidoDto partidoDto= iPartidoService.detallePartido(id); //se llama al metodo "detallePartido" de "iPartidoService", se le ingresa el id y devuelve un "partidoDto"

        PartidoDataRestModel partidoDataRestModel= modelMapper.map(partidoDto, PartidoDataRestModel.class);

        return partidoDataRestModel;

    }

    @PutMapping(path = "/{id}")
    public  PartidoDataRestModel actualizarPartido(@PathVariable String id,
                                                   @RequestBody PartidoActualizarRequestModel partidoActualizarRequestModel) {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();//obtenemos la autenticacion que esta actualmente en la plataforma

        String username= authentication.getPrincipal().toString();

        PartidoDto partidoActualizarDto= modelMapper.map(partidoActualizarRequestModel, PartidoDto.class);
        partidoActualizarDto.setUsername(username);

        PartidoDto partidoDto= iPartidoService.actualizarPartido(id, partidoActualizarDto);

        PartidoDataRestModel partidoDataRestModel= modelMapper.map(partidoDto, PartidoDataRestModel.class);

        return partidoDataRestModel;
    }

    @DeleteMapping(path = "/{id}")
    public MensajeRestModel eliminarPartido(@PathVariable String id) {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();//obtenemos la autenticacion que esta actualmente en la plataforma

        String username= authentication.getPrincipal().toString();

        UsuarioDto usuarioDto= iUsuarioService.leerUsuario(username);

        MensajeRestModel mensajeRestModel= new MensajeRestModel();
        mensajeRestModel.setNombre("Eliminar partido");

        iPartidoService.eliminarPartido(id, usuarioDto.getId());

        mensajeRestModel.setResulatado("Partido eliminado con exito");

        return mensajeRestModel;
    }

}
