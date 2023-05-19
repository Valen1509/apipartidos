package com.fesc.apipartidos.data.entidades;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "partido")
@EntityListeners(AuditingEntityListener.class) //se utiliza la anotacion "@EntityListeners" para activar la auditoria
@Table(indexes = {
        @Index(columnList = "idPartido", name = "index_idPartido", unique = true)
})
public class PartidosEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String idPartido;

    @Column(nullable = false)
    private Date fecha;

    @Column(nullable = false)
    private String golesLocal;

    @Column(nullable = false)
    private String golesVisitante;

    @CreatedDate //se utiliza para que automaticamente establezca el valor de este campo en la fecha y hora que en que se crearon valores en la entidad
    private Date creado;

    @ManyToOne//se declara la relacion muchos a uno
    @JoinColumn(name = "id_usuario") //se usa para unir la columna de "PartidosEntity" y "UsuarioEntity" llamada "id_usuario"
    private UsuarioEntity usuarioEntity; //el atributo se debe llamar igual al mapeo de la relacion "@OneToMany" declarada en "UsuarioEntity" que en este caso es "usuarioEntity"

    @ManyToOne
    @JoinColumn(name = "id_equipolocal") //se usa para unir la columna de "PartidosEntity" y de "EquipoEntity" llamada "id_equipolocal"
    private EquipoEntity equipoEntityLocal; //el atributo se debe llamar igual al mapeo de la relacion "@OneToMany" declarada en "UsuarioEntity" que en este caso es "equipolocal"

    @ManyToOne
    @JoinColumn(name = "id_equipovisitante") //se usa para unir la columna de "partidosEntity" y de "EquipoEntity" llamada ""id_equipovisitante""
    private EquipoEntity equipoEntityVisitante; //el atributo se debe llamar igual al mapeo de la relacion "@OneToMany" declarada en "UsuarioEntity" que en este caso es "equipovisitante"

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(String idPartido) {
        this.idPartido = idPartido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getGolesLocal() {
        return golesLocal;
    }

    public void setGolesLocal(String golesLocal) {
        this.golesLocal = golesLocal;
    }

    public String getGolesVisitante() {
        return golesVisitante;
    }

    public void setGolesVisitante(String golesVisitante) {
        this.golesVisitante = golesVisitante;
    }

    public Date getCreado() {
        return creado;
    }

    public void setCreado(Date creado) {
        this.creado = creado;
    }

    public UsuarioEntity getUsuarioEntity() {
        return usuarioEntity;
    }

    public void setUsuarioEntity(UsuarioEntity usuarioEntity) {
        this.usuarioEntity = usuarioEntity;
    }

    public EquipoEntity getEquipoEntityLocal() {
        return equipoEntityLocal;
    }

    public void setEquipoEntityLocal(EquipoEntity equipoEntityLocal) {
        this.equipoEntityLocal = equipoEntityLocal;
    }

    public EquipoEntity getEquipoEntityVisitante() {
        return equipoEntityVisitante;
    }

    public void setEquipoEntityVisitante(EquipoEntity equipoEntityVisitante) {
        this.equipoEntityVisitante = equipoEntityVisitante;
    }
}
