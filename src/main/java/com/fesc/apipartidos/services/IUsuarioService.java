package com.fesc.apipartidos.services;

import com.fesc.apipartidos.shared.PartidoDto;
import com.fesc.apipartidos.shared.UsuarioDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUsuarioService extends UserDetailsService { //la interfaz que tiene la declaracion de los metodos(crear, actualizar etc) y extiende la clase "UserDetails" de la libreria "Spring Security"

    UsuarioDto crearUsuario(UsuarioDto usuarioCrearDto); //se crea el metodo "crearUsuario" de "UsuarioDto" y se especifica que se le va a enviar un "UsuarioDto"

    UsuarioDto leerUsuario(String username);

    List<PartidoDto> leerMisPartidos(String username); //metodo que permite leer los partidos del usuario

};

