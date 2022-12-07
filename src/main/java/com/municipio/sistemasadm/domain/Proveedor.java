package com.municipio.sistemasadm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
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

    @OneToMany(mappedBy = "idProveedor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "pagos", "pozos", "idDespliegueInfraestructuraTroncalDistribucion", "idProveedor" },
        allowSetters = true
    )
    private Set<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersions = new HashSet<>();

    @OneToMany(mappedBy = "idProveedor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "registroInspecciones", "idProveedor" }, allowSetters = true)
    private Set<Autorizaciones> autorizaciones = new HashSet<>();

    @OneToMany(mappedBy = "idProveedor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "idProveedor", "pozos" }, allowSetters = true)
    private Set<Infraestructura> infraestructuras = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private User user;

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

    public Set<Despliegueinfraestructuradispersion> getDespliegueinfraestructuradispersions() {
        return this.despliegueinfraestructuradispersions;
    }

    public void setDespliegueinfraestructuradispersions(Set<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersions) {
        if (this.despliegueinfraestructuradispersions != null) {
            this.despliegueinfraestructuradispersions.forEach(i -> i.setIdProveedor(null));
        }
        if (despliegueinfraestructuradispersions != null) {
            despliegueinfraestructuradispersions.forEach(i -> i.setIdProveedor(this));
        }
        this.despliegueinfraestructuradispersions = despliegueinfraestructuradispersions;
    }

    public Proveedor despliegueinfraestructuradispersions(Set<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersions) {
        this.setDespliegueinfraestructuradispersions(despliegueinfraestructuradispersions);
        return this;
    }

    public Proveedor addDespliegueinfraestructuradispersion(Despliegueinfraestructuradispersion despliegueinfraestructuradispersion) {
        this.despliegueinfraestructuradispersions.add(despliegueinfraestructuradispersion);
        despliegueinfraestructuradispersion.setIdProveedor(this);
        return this;
    }

    public Proveedor removeDespliegueinfraestructuradispersion(Despliegueinfraestructuradispersion despliegueinfraestructuradispersion) {
        this.despliegueinfraestructuradispersions.remove(despliegueinfraestructuradispersion);
        despliegueinfraestructuradispersion.setIdProveedor(null);
        return this;
    }

    public Set<Autorizaciones> getAutorizaciones() {
        return this.autorizaciones;
    }

    public void setAutorizaciones(Set<Autorizaciones> autorizaciones) {
        if (this.autorizaciones != null) {
            this.autorizaciones.forEach(i -> i.setIdProveedor(null));
        }
        if (autorizaciones != null) {
            autorizaciones.forEach(i -> i.setIdProveedor(this));
        }
        this.autorizaciones = autorizaciones;
    }

    public Proveedor autorizaciones(Set<Autorizaciones> autorizaciones) {
        this.setAutorizaciones(autorizaciones);
        return this;
    }

    public Proveedor addAutorizaciones(Autorizaciones autorizaciones) {
        this.autorizaciones.add(autorizaciones);
        autorizaciones.setIdProveedor(this);
        return this;
    }

    public Proveedor removeAutorizaciones(Autorizaciones autorizaciones) {
        this.autorizaciones.remove(autorizaciones);
        autorizaciones.setIdProveedor(null);
        return this;
    }

    public Set<Infraestructura> getInfraestructuras() {
        return this.infraestructuras;
    }

    public void setInfraestructuras(Set<Infraestructura> infraestructuras) {
        if (this.infraestructuras != null) {
            this.infraestructuras.forEach(i -> i.setIdProveedor(null));
        }
        if (infraestructuras != null) {
            infraestructuras.forEach(i -> i.setIdProveedor(this));
        }
        this.infraestructuras = infraestructuras;
    }

    public Proveedor infraestructuras(Set<Infraestructura> infraestructuras) {
        this.setInfraestructuras(infraestructuras);
        return this;
    }

    public Proveedor addInfraestructura(Infraestructura infraestructura) {
        this.infraestructuras.add(infraestructura);
        infraestructura.setIdProveedor(this);
        return this;
    }

    public Proveedor removeInfraestructura(Infraestructura infraestructura) {
        this.infraestructuras.remove(infraestructura);
        infraestructura.setIdProveedor(null);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Proveedor user(User user) {
        this.setUser(user);
        return this;
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
