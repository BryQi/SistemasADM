package com.municipio.sistemasadm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.municipio.sistemasadm.domain.enumeration.Tipo;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Infraestructura.
 */
@Entity
@Table(name = "infraestructura")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Infraestructura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private Tipo tipo;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "despliegueinfraestructuradispersions", "autorizaciones", "infraestructuras", "user" },
        allowSetters = true
    )
    private Proveedor idProveedor;

    @ManyToMany
    @JoinTable(
        name = "rel_infraestructura__pozo",
        joinColumns = @JoinColumn(name = "infraestructura_id"),
        inverseJoinColumns = @JoinColumn(name = "pozo_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "fotoPozos",
            "registroInspecciones",
            "idDespliegueInfraestructuraTroncalDistribucions",
            "idDespliegueinfraestructuradispersions",
        },
        allowSetters = true
    )
    private Set<Pozo> pozos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Infraestructura id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tipo getTipo() {
        return this.tipo;
    }

    public Infraestructura tipo(Tipo tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Infraestructura createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Proveedor getIdProveedor() {
        return this.idProveedor;
    }

    public void setIdProveedor(Proveedor proveedor) {
        this.idProveedor = proveedor;
    }

    public Infraestructura idProveedor(Proveedor proveedor) {
        this.setIdProveedor(proveedor);
        return this;
    }

    public Set<Pozo> getPozos() {
        return this.pozos;
    }

    public void setPozos(Set<Pozo> pozos) {
        this.pozos = pozos;
    }

    public Infraestructura pozos(Set<Pozo> pozos) {
        this.setPozos(pozos);
        return this;
    }

    public Infraestructura addPozo(Pozo pozo) {
        this.pozos.add(pozo);
        return this;
    }

    public Infraestructura removePozo(Pozo pozo) {
        this.pozos.remove(pozo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Infraestructura)) {
            return false;
        }
        return id != null && id.equals(((Infraestructura) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Infraestructura{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
