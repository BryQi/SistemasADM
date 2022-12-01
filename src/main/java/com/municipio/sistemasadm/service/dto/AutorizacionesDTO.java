package com.municipio.sistemasadm.service.dto;

import com.municipio.sistemasadm.domain.enumeration.ContactoTecnico;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.municipio.sistemasadm.domain.Autorizaciones} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AutorizacionesDTO implements Serializable {

    private Long id;

    @NotNull
    private String cliente;

    @NotNull
    private String direccionOrigen;

    @NotNull
    private Double direccionDestino;

    @NotNull
    private LocalDate fechaOperacion;

    @NotNull
    private String ventanaTrabajo;

    private ContactoTecnico contactoTecnico;

    @NotNull
    private String tipoTrabajo;

    @NotNull
    private String observaciones;

    @NotNull
    private ZonedDateTime createdAt;

    private RegistroInspeccionesDTO registroInspecciones;

    private ProveedorDTO idProveedor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDireccionOrigen() {
        return direccionOrigen;
    }

    public void setDireccionOrigen(String direccionOrigen) {
        this.direccionOrigen = direccionOrigen;
    }

    public Double getDireccionDestino() {
        return direccionDestino;
    }

    public void setDireccionDestino(Double direccionDestino) {
        this.direccionDestino = direccionDestino;
    }

    public LocalDate getFechaOperacion() {
        return fechaOperacion;
    }

    public void setFechaOperacion(LocalDate fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public String getVentanaTrabajo() {
        return ventanaTrabajo;
    }

    public void setVentanaTrabajo(String ventanaTrabajo) {
        this.ventanaTrabajo = ventanaTrabajo;
    }

    public ContactoTecnico getContactoTecnico() {
        return contactoTecnico;
    }

    public void setContactoTecnico(ContactoTecnico contactoTecnico) {
        this.contactoTecnico = contactoTecnico;
    }

    public String getTipoTrabajo() {
        return tipoTrabajo;
    }

    public void setTipoTrabajo(String tipoTrabajo) {
        this.tipoTrabajo = tipoTrabajo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public RegistroInspeccionesDTO getRegistroInspecciones() {
        return registroInspecciones;
    }

    public void setRegistroInspecciones(RegistroInspeccionesDTO registroInspecciones) {
        this.registroInspecciones = registroInspecciones;
    }

    public ProveedorDTO getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(ProveedorDTO idProveedor) {
        this.idProveedor = idProveedor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AutorizacionesDTO)) {
            return false;
        }

        AutorizacionesDTO autorizacionesDTO = (AutorizacionesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, autorizacionesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AutorizacionesDTO{" +
            "id=" + getId() +
            ", cliente='" + getCliente() + "'" +
            ", direccionOrigen='" + getDireccionOrigen() + "'" +
            ", direccionDestino=" + getDireccionDestino() +
            ", fechaOperacion='" + getFechaOperacion() + "'" +
            ", ventanaTrabajo='" + getVentanaTrabajo() + "'" +
            ", contactoTecnico='" + getContactoTecnico() + "'" +
            ", tipoTrabajo='" + getTipoTrabajo() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", registroInspecciones=" + getRegistroInspecciones() +
            ", idProveedor=" + getIdProveedor() +
            "}";
    }
}
