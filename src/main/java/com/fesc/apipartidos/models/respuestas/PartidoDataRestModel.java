package com.fesc.apipartidos.models.respuestas;

import java.util.Date;

public class PartidoDataRestModel {

    private String idPartido;
    private Date fecha;
    private String golesLocal;
    private String golesVisitante;
    private Date creado;
    private boolean jugado;
    private UsuarioDataRestModel usuarioEntity;
    private EquipoDataRestModel equipoEntityLocal;
    private EquipoDataRestModel equipoEntityVisitante;

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

    public boolean isJugado() {
        return this.jugado;
    }

    public boolean getJugado() {
        return this.jugado;
    }

    public void setJugado(boolean jugado) {
        this.jugado = jugado;
    }

    public UsuarioDataRestModel getUsuarioEntity() {
        return usuarioEntity;
    }

    public void setUsuarioEntity(UsuarioDataRestModel usuarioEntity) {
        this.usuarioEntity = usuarioEntity;
    }

    public EquipoDataRestModel getEquipoEntityLocal() {
        return equipoEntityLocal;
    }

    public void setEquipoEntityLocal(EquipoDataRestModel equipoEntityLocal) {
        this.equipoEntityLocal = equipoEntityLocal;
    }

    public EquipoDataRestModel getEquipoEntityVisitante() {
        return equipoEntityVisitante;
    }

    public void setEquipoEntityVisitante(EquipoDataRestModel equipoEntityVisitante) {
        this.equipoEntityVisitante = equipoEntityVisitante;
    }
}
