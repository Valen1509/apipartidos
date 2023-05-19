package com.fesc.apipartidos.data.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name="usuario") //define que es una tabla y le da el nombre que en este caso es "usuario"
@Table(indexes = {
        @Index(columnList = "idUsuario", name = "index_idUsuario", unique = true), //ubicamos un idex en la colomna "idUsuario" y le ponemos un nombre "index_idUsuario" y declaramos que va ser unico
        @Index(columnList = "email", name = "index_email", unique = true),
        @Index(columnList = "username", name = "index_username", unique = true)
})
public class UsuarioEntity implements Serializable {

    private static final long serialVersionUID= 1L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String idUsuario;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 10)
    private String username;

    @Column(nullable = false)
    private String passwordEncripatda;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioEntity") //se declara la relacion "@OneToMany" que es de uno a muchos y se indica que mediante "cascade = CascadeType.ALL" cualquier operacion realizada en "usuarioEntity" se propagara a "partidosEntity"
    private List<PartidosEntity> partidosEntityList= new ArrayList<>(); //el atibuto lista indica que se van almacenar muchos partidos de "PartidosEntity" y se van a almacenar en el atributo "PartidosEntity" y se instancia el arraylist

    public long getId() {
        return id;
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

    public String getPasswordEncripatda() {
        return passwordEncripatda;
    }

    public void setPasswordEncripatda(String passwordEncripatda) {
        this.passwordEncripatda = passwordEncripatda;
    }

    public List<PartidosEntity> getPartidosEntityList() {
        return partidosEntityList;
    }

    public void setPartidosEntityList(List<PartidosEntity> partidosEntityList) {
        this.partidosEntityList = partidosEntityList;
    }
}
