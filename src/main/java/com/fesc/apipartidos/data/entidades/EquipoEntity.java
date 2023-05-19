package com.fesc.apipartidos.data.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "equipo")
public class EquipoEntity implements Serializable {

    private static final long serialVersionUID= 1L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 20)
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipoEntityLocal") //se declara la relacion de uno a muchos entre "equipolocal" y "PartidosEntity"
    private List<PartidosEntity> partidosEntityLocalList= new ArrayList<>(); //el atibuto lista indica que se van almacenar muchos partidos de "PartidosEntity" y se van a almacenar en el atributo "PartidosEntity" y se instancia el arraylist

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipoEntityVisitante") //se declara la relacion de uno a muchos entre "equipovisitante" y "PartidosEntity"
    private List<PartidosEntity> partidosEntityVisitanteList= new ArrayList<>(); //el atibuto lista indica que se van almacenar muchos partidos de "PartidosEntity" y se van a almacenar en el atributo "PartidosEntity" y se instancia el arraylist

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<PartidosEntity> getPartidosEntityLocalList() {
        return partidosEntityLocalList;
    }

    public void setPartidosEntityLocalList(List<PartidosEntity> partidosEntityLocalList) {
        this.partidosEntityLocalList = partidosEntityLocalList;
    }

    public List<PartidosEntity> getPartidosEntityVisitanteList() {
        return partidosEntityVisitanteList;
    }

    public void setPartidosEntityVisitanteList(List<PartidosEntity> partidosEntityVisitanteList) {
        this.partidosEntityVisitanteList = partidosEntityVisitanteList;
    }
}
