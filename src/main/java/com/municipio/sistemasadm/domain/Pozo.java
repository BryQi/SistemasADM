package com.municipio.sistemasadm.domain;

import com.municipio.sistemasadm.domain.enumeration.TipoPozo;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Pozo.
 */
@Entity
@Table(name = "pozo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Pozo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "numeropozo", nullable = false)
    private String numeropozo;

    @NotNull
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipopozo")
    private TipoPozo tipopozo;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @NotNull
    @Column(name = "longitud", nullable = false)
    private String longitud;

    @NotNull
    @Column(name = "latitud", nullable = false)
    private String latitud;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pozo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeropozo() {
        return this.numeropozo;
    }

    public Pozo numeropozo(String numeropozo) {
        this.setNumeropozo(numeropozo);
        return this;
    }

    public void setNumeropozo(String numeropozo) {
        this.numeropozo = numeropozo;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Pozo direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public TipoPozo getTipopozo() {
        return this.tipopozo;
    }

    public Pozo tipopozo(TipoPozo tipopozo) {
        this.setTipopozo(tipopozo);
        return this;
    }

    public void setTipopozo(TipoPozo tipopozo) {
        this.tipopozo = tipopozo;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Pozo createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getLongitud() {
        return this.longitud;
    }

    public Pozo longitud(String longitud) {
        this.setLongitud(longitud);
        return this;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return this.latitud;
    }

    public Pozo latitud(String latitud) {
        this.setLatitud(latitud);
        return this;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pozo)) {
            return false;
        }
        return id != null && id.equals(((Pozo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pozo{" +
            "id=" + getId() +
            ", numeropozo='" + getNumeropozo() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", tipopozo='" + getTipopozo() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", longitud='" + getLongitud() + "'" +
            ", latitud='" + getLatitud() + "'" +
            "}";
    }
}
