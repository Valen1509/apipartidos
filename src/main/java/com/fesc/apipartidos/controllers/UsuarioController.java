package com.fesc.apipartidos.controllers;

import com.fesc.apipartidos.models.peticiones.UsuarioCrearRequestModel;
import com.fesc.apipartidos.models.respuestas.PartidoDataRestModel;
import com.fesc.apipartidos.models.respuestas.UsuarioDataRestModel;
import com.fesc.apipartidos.services.IUsuarioService;
import com.fesc.apipartidos.shared.PartidoDto;
import com.fesc.apipartidos.shared.UsuarioDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/usuario")//"usuario" va ser una endpoint para que se hagan peticiones //Para que el cliente (la vista) acceda
public class UsuarioController {

    @Autowired //enlazar los beans tanto de springboot y los que se hayan creado lo que permite que se pueden usar sin hacer una instancia
    ModelMapper modelMapper; //llama al metodo modelMapper

    @Autowired //enlazar los beans tanto de springboot y los que se hayan creado
    IUsuarioService iUsuarioService; //llama la interfaz

       @GetMapping//Obtener informacion
        public UsuarioDataRestModel leerUsuario(){ //crear el metodo "leerUusario"

           Authentication authentication= SecurityContextHolder.getContext().getAuthentication();//obtenemos la autenticacion que esta actualmente en la plataforma

           String username= authentication.getPrincipal().toString();

           UsuarioDto usuarioDto= iUsuarioService.leerUsuario(username);

           UsuarioDataRestModel usuarioDataRestModel= modelMapper.map(usuarioDto, UsuarioDataRestModel.class);

           return usuarioDataRestModel; //se retorna un string, en este caso la informacion del usuario
       }

       @PostMapping //Crear usuario
        public UsuarioDataRestModel crearUsuario(@RequestBody UsuarioCrearRequestModel usuarioCrearRequestModel) { //crea el metodo "crearUsuario" y mediante la anotacion @Requestbody indicamos que vamos a recibir la informacion mediante el cuerpo de la peticion en este caso "UsuarioCrearRequestModel"

            UsuarioDto usuarioCrearDto = modelMapper.map(usuarioCrearRequestModel, UsuarioDto.class); //mapear el objeto "usuarioCrearRequestModel" y a convertir en "UsuarioDto"y lo almacena en el objeto "UsuarioCrearDto"

            UsuarioDto usuarioDto= iUsuarioService.crearUsuario(usuarioCrearDto);//llama al servicio "iUsuarioService" y se usa el metodo "crearUsuario" y se envia un usuario Dto(usuarioCrearDto) //registrar la informacion

           UsuarioDataRestModel usuarioDataRestModel= modelMapper.map(usuarioDto, UsuarioDataRestModel.class); //mapear el "usuarioDto" en la clase "UsuarioDataRestModel" // buscar los getter de "usuarioDto y lo mete en los setter de "UsuarioDataRestModel"

           return usuarioDataRestModel; //se envia la respuesta
       }

       @GetMapping(path = "/mispartidos")
        public List<PartidoDataRestModel> leerMisPartidos() {

           Authentication authentication= SecurityContextHolder.getContext().getAuthentication();//obtenemos la autenticacion que esta actualmente en la plataforma

           String username= authentication.getPrincipal().toString();

           List<PartidoDto> partidoDtoList= iUsuarioService.leerMisPartidos(username); //consulta al servicios

           List<PartidoDataRestModel> partidoDataRestModelList= new ArrayList<>(); //usamos el padre "List" e instanciamos al hijo "ArrayList"

           for (PartidoDto partidoDto : partidoDtoList) {
               PartidoDataRestModel partidoDataRestModel= modelMapper.map(partidoDto, PartidoDataRestModel.class);

               if (partidoDataRestModel.getFecha().compareTo(new Date(System.currentTimeMillis())) < 0){ //creamos un condicional if que compare la fecha del partidos con la fecha actual y si la comparacion es menos que 0, ya se jugo o sea ya paso la fecha
                   partidoDataRestModel.setJugado(true); //si se cumple el if lo pne en jugado
               }
               partidoDataRestModelList.add(partidoDataRestModel);
           }

           return partidoDataRestModelList;
       }


}