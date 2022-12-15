package com.municipio.sistemasadm.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Proveedor.
 */
@Entity
@Table(name = "proveedor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Proveedor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "razon_social", nullable = false)
    private String razonSocial;

    @NotNull
    @Column(name = "contacto_tecnico", nullable = false)
    private String contactoTecnico;

    @NotNull
    @Column(name = "correo_empresa", nullable = false)
    private String correoEmpresa;

    @NotNull
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @NotNull
    @Column(name = "celular", nullable = false)
    private Integer celular;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Proveedor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazonSocial() {
        return this.razonSocial;
    }

    public Proveedor razonSocial(String razonSocial) {
        this.setRazonSocial(razonSocial);
        return this;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getContactoTecnico() {
        return this.contactoTecnico;
    }

    public Proveedor contactoTecnico(String contactoTecnico) {
        this.setContactoTecnico(contactoTecnico);
        return this;
    }

    public void setContactoTecnico(String contactoTecnico) {
        this.contactoTecnico = contactoTecnico;
    }

    public String getCorreoEmpresa() {
        return this.correoEmpresa;
    }

    public Proveedor correoEmpresa(String correoEmpresa) {
        this.setCorreoEmpresa(correoEmpresa);
        return this;
    }

    public void setCorreoEmpresa(String correoEmpresa) {
        this.correoEmpresa = correoEmpresa;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Proveedor direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getCelular() {
        return this.celular;
    }

    public Proveedor celular(Integer celular) {
        this.setCelular(celular);
        return this;
    }

    public void setCelular(Integer celular) {
        this.celular = celular;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Proveedor createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Proveedor)) {
            return false;
        }
        return id != null && id.equals(((Proveedor) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Proveedor{" +
            "id=" + getId() +
            ", razonSocial='" + getRazonSocial() + "'" +
            ", contactoTecnico='" + getContactoTecnico() + "'" +
            ", correoEmpresa='" + getCorreoEmpresa() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", celular=" + getCelular() +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
