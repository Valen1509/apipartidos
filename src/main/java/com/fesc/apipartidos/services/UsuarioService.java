package com.fesc.apipartidos.services;

import com.fesc.apipartidos.data.entidades.PartidosEntity;
import com.fesc.apipartidos.data.entidades.UsuarioEntity;
import com.fesc.apipartidos.data.repositorios.IPartidoRepository;
import com.fesc.apipartidos.data.repositorios.IUsuarioRepository;
import com.fesc.apipartidos.shared.PartidoDto;
import com.fesc.apipartidos.shared.UsuarioDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IUsuarioRepository iUsuarioRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    IPartidoRepository iPartidoRepository;

    @Override //implementa el metodo "crearUsuario" que fue creado en "IUsuarioservice"
    public UsuarioDto crearUsuario(UsuarioDto usuarioCrearDto) {

        if (iUsuarioRepository.findByEmail(usuarioCrearDto.getEmail()) !=null) { //se crea un condicional if, llamamos a "iUsuarioRepository" que tiene el metodo "findBy" que usamos para buscar el email ingresado en "usuarioCrearDto" mediante "getEmail()" y agregamos "!=null" se utiliza para comprobar si el resultado de la búsqueda anterior es distinto de "null", es decir, si se encontró un usuario con el mismo correo
            throw new RuntimeException("Este correo ya se encuentra registrado"); //es una declaracion que se utiliza para generar una excepcion, en este caso si no se cumple el condicional if se muestra el mensaje
        }

        if (iUsuarioRepository.findByUsername(usuarioCrearDto.getUsername()) !=null) {
            throw new RuntimeException("Este nombre de usuario ya esta en uso"); //es una declaracion que se utiliza para generar una excepcion
        }

        UsuarioEntity usuarioEntityDto= modelMapper.map(usuarioCrearDto, UsuarioEntity.class); //mapeamos la infromacion de "usuarioCrearDto" a "usuarioEntity"
        usuarioEntityDto.setIdUsuario(UUID.randomUUID().toString()); //settea el id, o sea crea el id de usuario mediante UUID
        usuarioEntityDto.setPasswordEncripatda(bCryptPasswordEncoder.encode(usuarioCrearDto.getPassword()));//obtiene la password de "usuarioCrearDto" y la encripta mediante "bCryptPasswordEncoder"

        UsuarioEntity usuarioEntity= iUsuarioRepository.save(usuarioEntityDto); //insercion de datos

        UsuarioDto usuarioDto= modelMapper.map(usuarioEntity, UsuarioDto.class); //se hace un mapeo de "usuarioEntity" a "usuarioDto"
        return usuarioDto; //se retorna un "usuarioDto"
    }

    @Override
    public UsuarioDto leerUsuario(String username) {

        UsuarioEntity usuarioEntity= iUsuarioRepository.findByUsername(username); //obtener una consulta que se va hacer al repositorio en estecaso buscar el usuario por el username

        if (usuarioEntity==null){
            throw new UsernameNotFoundException(username); //creamos una excepcion del username que ya esta definida por la seguridad de spring boot
        }

        UsuarioDto usuarioDto= modelMapper.map(usuarioEntity, UsuarioDto.class);

        return usuarioDto;
    }

    @Override
    public List<PartidoDto> leerMisPartidos(String username) {

        UsuarioEntity usuarioEntity= iUsuarioRepository.findByUsername(username);

        List<PartidosEntity> partidosEntityList= iPartidoRepository.getByUsuarioEntityIdOrderByCreadoDesc(usuarioEntity.getId());

        List<PartidoDto> partidoDtoList= new ArrayList<>();

        for(PartidosEntity partidosEntity : partidosEntityList){
            PartidoDto partidoDto= modelMapper.map(partidosEntity, PartidoDto.class);
            partidoDtoList.add(partidoDto);
        }

        return partidoDtoList;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //metodo del login que viene por defecto en la libreria

        UsuarioEntity usuarioEntity= iUsuarioRepository.findByUsername(username);

        if (usuarioEntity==null) {
            throw new UsernameNotFoundException(username);
        }

        User usuario= new User(usuarioEntity.getUsername(), usuarioEntity.getPasswordEncripatda(), new ArrayList<>()); //se hace una nueva instancia de "user" y se trae al "username", la "passwordEncriptada", y le manda una "Arraylist" para que traiga las autorizaciones del usuario

        return usuario; //este "usuario" solo obtiene el proceso de la obtencion de la informacion y luego tiene que pasar a servalidado
    }
}
