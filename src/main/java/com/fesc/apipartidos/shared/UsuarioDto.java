package com.fesc.apipartidos.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDto implements Serializable { //recibe la informacion del usuario mapeandola, envia la iformacion al usuario mapeandola, envia informacion a la BD mapeandola

    private static final long serialVersionUID=1L; //constante que se necesita para hacer el proceso de serializacion

    private long id;
    private String idUsuario;
    private String nombre;
    private String email;
    private String username;
    private String password;
    private String passwordEncripatda;
    private List<PartidoDto> partidoDtoList =new ArrayList<>();

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordEncripatda() {
        return passwordEncripatda;
    }

    public void setPasswordEncripatda(String passwordEncripatda) {
        this.passwordEncripatda = passwordEncripatda;
    }

    public List<PartidoDto> getPartidoDtoList() {
        return partidoDtoList;
    }

    public void setPartidoDtoList(List<PartidoDto> partidoDtoList) {
        this.partidoDtoList = partidoDtoList;
    }
}
