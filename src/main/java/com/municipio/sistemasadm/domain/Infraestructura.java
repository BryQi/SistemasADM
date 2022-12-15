package com.municipio.sistemasadm.domain;

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

    @ManyToOne(optional = false)
    @NotNull
    private Proveedor razonSocial;

    @ManyToMany
    @NotNull
    @JoinTable(
        name = "rel_infraestructura__numeropozo",
        joinColumns = @JoinColumn(name = "infraestructura_id"),
        inverseJoinColumns = @JoinColumn(name = "numeropozo_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Pozo> numeropozos = new HashSet<>();

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

    public Proveedor getRazonSocial() {
        return this.razonSocial;
    }

    public void setRazonSocial(Proveedor proveedor) {
        this.razonSocial = proveedor;
    }

    public Infraestructura razonSocial(Proveedor proveedor) {
        this.setRazonSocial(proveedor);
        return this;
    }

    public Set<Pozo> getNumeropozos() {
        return this.numeropozos;
    }

    public void setNumeropozos(Set<Pozo> pozos) {
        this.numeropozos = pozos;
    }

    public Infraestructura numeropozos(Set<Pozo> pozos) {
        this.setNumeropozos(pozos);
        return this;
    }

    public Infraestructura addNumeropozo(Pozo pozo) {
        this.numeropozos.add(pozo);
        return this;
    }

    public Infraestructura removeNumeropozo(Pozo pozo) {
        this.numeropozos.remove(pozo);
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
