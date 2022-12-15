package com.municipio.sistemasadm.domain;

import com.municipio.sistemasadm.domain.enumeration.ContactoTecnico;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Autorizaciones.
 */
@Entity
@Table(name = "autorizaciones")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Autorizaciones implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "cliente", nullable = false)
    private String cliente;

    @NotNull
    @Column(name = "direccion_origen", nullable = false)
    private String direccionOrigen;

    @NotNull
    @Column(name = "fecha_operacion", nullable = false)
    private LocalDate fechaOperacion;

    @NotNull
    @Column(name = "ventana_trabajo", nullable = false)
    private String ventanaTrabajo;

    @Enumerated(EnumType.STRING)
    @Column(name = "contacto_tecnico")
    private ContactoTecnico contactoTecnico;

    @NotNull
    @Column(name = "tipo_trabajo", nullable = false)
    private String tipoTrabajo;

    @NotNull
    @Column(name = "observaciones", nullable = false)
    private String observaciones;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @NotNull
    @Column(name = "direccion_destino", nullable = false)
    private String direccionDestino;

    @ManyToOne(optional = false)
    @NotNull
    private Proveedor razonSocial;

    @ManyToOne(optional = false)
    @NotNull
    private Pozo numeropozo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Autorizaciones id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCliente() {
        return this.cliente;
    }

    public Autorizaciones cliente(String cliente) {
        this.setCliente(cliente);
        return this;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDireccionOrigen() {
        return this.direccionOrigen;
    }

    public Autorizaciones direccionOrigen(String direccionOrigen) {
        this.setDireccionOrigen(direccionOrigen);
        return this;
    }

    public void setDireccionOrigen(String direccionOrigen) {
        this.direccionOrigen = direccionOrigen;
    }

    public LocalDate getFechaOperacion() {
        return this.fechaOperacion;
    }

    public Autorizaciones fechaOperacion(LocalDate fechaOperacion) {
        this.setFechaOperacion(fechaOperacion);
        return this;
    }

    public void setFechaOperacion(LocalDate fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public String getVentanaTrabajo() {
        return this.ventanaTrabajo;
    }

    public Autorizaciones ventanaTrabajo(String ventanaTrabajo) {
        this.setVentanaTrabajo(ventanaTrabajo);
        return this;
    }

    public void setVentanaTrabajo(String ventanaTrabajo) {
        this.ventanaTrabajo = ventanaTrabajo;
    }

    public ContactoTecnico getContactoTecnico() {
        return this.contactoTecnico;
    }

    public Autorizaciones contactoTecnico(ContactoTecnico contactoTecnico) {
        this.setContactoTecnico(contactoTecnico);
        return this;
    }

    public void setContactoTecnico(ContactoTecnico contactoTecnico) {
        this.contactoTecnico = contactoTecnico;
    }

    public String getTipoTrabajo() {
        return this.tipoTrabajo;
    }

    public Autorizaciones tipoTrabajo(String tipoTrabajo) {
        this.setTipoTrabajo(tipoTrabajo);
        return this;
    }

    public void setTipoTrabajo(String tipoTrabajo) {
        this.tipoTrabajo = tipoTrabajo;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public Autorizaciones observaciones(String observaciones) {
        this.setObservaciones(observaciones);
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Autorizaciones createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDireccionDestino() {
        return this.direccionDestino;
    }

    public Autorizaciones direccionDestino(String direccionDestino) {
        this.setDireccionDestino(direccionDestino);
        return this;
    }

    public void setDireccionDestino(String direccionDestino) {
        this.direccionDestino = direccionDestino;
    }

    public Proveedor getRazonSocial() {
        return this.razonSocial;
    }

    public void setRazonSocial(Proveedor proveedor) {
        this.razonSocial = proveedor;
    }

    public Autorizaciones razonSocial(Proveedor proveedor) {
        this.setRazonSocial(proveedor);
        return this;
    }

    public Pozo getNumeropozo() {
        return this.numeropozo;
    }

    public void setNumeropozo(Pozo pozo) {
        this.numeropozo = pozo;
    }

    public Autorizaciones numeropozo(Pozo pozo) {
        this.setNumeropozo(pozo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Autorizaciones)) {
            return false;
        }
        return id != null && id.equals(((Autorizaciones) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Autorizaciones{" +
            "id=" + getId() +
            ", cliente='" + getCliente() + "'" +
            ", direccionOrigen='" + getDireccionOrigen() + "'" +
            ", fechaOperacion='" + getFechaOperacion() + "'" +
            ", ventanaTrabajo='" + getVentanaTrabajo() + "'" +
            ", contactoTecnico='" + getContactoTecnico() + "'" +
            ", tipoTrabajo='" + getTipoTrabajo() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", direccionDestino='" + getDireccionDestino() + "'" +
            "}";
    }
}
