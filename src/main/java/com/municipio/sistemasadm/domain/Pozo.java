package com.municipio.sistemasadm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.municipio.sistemasadm.domain.enumeration.TipoPozo;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
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
    @Column(name = "latitud", nullable = false)
    private String latitud;

    @NotNull
    @Column(name = "longitud", nullable = false)
    private String longitud;

    @OneToMany(mappedBy = "idPozo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "idPozo" }, allowSetters = true)
    private Set<FotoPozo> fotoPozos = new HashSet<>();

    @OneToMany(mappedBy = "idPozo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "idPozo" }, allowSetters = true)
    private Set<RegistroInspecciones> registroInspecciones = new HashSet<>();

    @ManyToMany(mappedBy = "pozos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "despliegueInfraestructuraDispersions", "pagos", "pozos" }, allowSetters = true)
    private Set<DespliegueInfraestructuraTroncalDistribucion> idDespliegueInfraestructuraTroncalDistribucions = new HashSet<>();

    @ManyToMany(mappedBy = "pozos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "pagos", "pozos", "idDespliegueInfraestructuraTroncalDistribucion", "idProveedor" },
        allowSetters = true
    )
    private Set<Despliegueinfraestructuradispersion> idDespliegueinfraestructuradispersions = new HashSet<>();

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

    public Set<FotoPozo> getFotoPozos() {
        return this.fotoPozos;
    }

    public void setFotoPozos(Set<FotoPozo> fotoPozos) {
        if (this.fotoPozos != null) {
            this.fotoPozos.forEach(i -> i.setIdPozo(null));
        }
        if (fotoPozos != null) {
            fotoPozos.forEach(i -> i.setIdPozo(this));
        }
        this.fotoPozos = fotoPozos;
    }

    public Pozo fotoPozos(Set<FotoPozo> fotoPozos) {
        this.setFotoPozos(fotoPozos);
        return this;
    }

    public Pozo addFotoPozo(FotoPozo fotoPozo) {
        this.fotoPozos.add(fotoPozo);
        fotoPozo.setIdPozo(this);
        return this;
    }

    public Pozo removeFotoPozo(FotoPozo fotoPozo) {
        this.fotoPozos.remove(fotoPozo);
        fotoPozo.setIdPozo(null);
        return this;
    }

    public Set<RegistroInspecciones> getRegistroInspecciones() {
        return this.registroInspecciones;
    }

    public void setRegistroInspecciones(Set<RegistroInspecciones> registroInspecciones) {
        if (this.registroInspecciones != null) {
            this.registroInspecciones.forEach(i -> i.setIdPozo(null));
        }
        if (registroInspecciones != null) {
            registroInspecciones.forEach(i -> i.setIdPozo(this));
        }
        this.registroInspecciones = registroInspecciones;
    }

    public Pozo registroInspecciones(Set<RegistroInspecciones> registroInspecciones) {
        this.setRegistroInspecciones(registroInspecciones);
        return this;
    }

    public Pozo addRegistroInspecciones(RegistroInspecciones registroInspecciones) {
        this.registroInspecciones.add(registroInspecciones);
        registroInspecciones.setIdPozo(this);
        return this;
    }

    public Pozo removeRegistroInspecciones(RegistroInspecciones registroInspecciones) {
        this.registroInspecciones.remove(registroInspecciones);
        registroInspecciones.setIdPozo(null);
        return this;
    }

    public Set<DespliegueInfraestructuraTroncalDistribucion> getIdDespliegueInfraestructuraTroncalDistribucions() {
        return this.idDespliegueInfraestructuraTroncalDistribucions;
    }

    public void setIdDespliegueInfraestructuraTroncalDistribucions(
        Set<DespliegueInfraestructuraTroncalDistribucion> despliegueInfraestructuraTroncalDistribucions
    ) {
        if (this.idDespliegueInfraestructuraTroncalDistribucions != null) {
            this.idDespliegueInfraestructuraTroncalDistribucions.forEach(i -> i.removePozo(this));
        }
        if (despliegueInfraestructuraTroncalDistribucions != null) {
            despliegueInfraestructuraTroncalDistribucions.forEach(i -> i.addPozo(this));
        }
        this.idDespliegueInfraestructuraTroncalDistribucions = despliegueInfraestructuraTroncalDistribucions;
    }

    public Pozo idDespliegueInfraestructuraTroncalDistribucions(
        Set<DespliegueInfraestructuraTroncalDistribucion> despliegueInfraestructuraTroncalDistribucions
    ) {
        this.setIdDespliegueInfraestructuraTroncalDistribucions(despliegueInfraestructuraTroncalDistribucions);
        return this;
    }

    public Pozo addIdDespliegueInfraestructuraTroncalDistribucion(
        DespliegueInfraestructuraTroncalDistribucion despliegueInfraestructuraTroncalDistribucion
    ) {
        this.idDespliegueInfraestructuraTroncalDistribucions.add(despliegueInfraestructuraTroncalDistribucion);
        despliegueInfraestructuraTroncalDistribucion.getPozos().add(this);
        return this;
    }

    public Pozo removeIdDespliegueInfraestructuraTroncalDistribucion(
        DespliegueInfraestructuraTroncalDistribucion despliegueInfraestructuraTroncalDistribucion
    ) {
        this.idDespliegueInfraestructuraTroncalDistribucions.remove(despliegueInfraestructuraTroncalDistribucion);
        despliegueInfraestructuraTroncalDistribucion.getPozos().remove(this);
        return this;
    }

    public Set<Despliegueinfraestructuradispersion> getIdDespliegueinfraestructuradispersions() {
        return this.idDespliegueinfraestructuradispersions;
    }

    public void setIdDespliegueinfraestructuradispersions(Set<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersions) {
        if (this.idDespliegueinfraestructuradispersions != null) {
            this.idDespliegueinfraestructuradispersions.forEach(i -> i.removePozo(this));
        }
        if (despliegueinfraestructuradispersions != null) {
            despliegueinfraestructuradispersions.forEach(i -> i.addPozo(this));
        }
        this.idDespliegueinfraestructuradispersions = despliegueinfraestructuradispersions;
    }

    public Pozo idDespliegueinfraestructuradispersions(Set<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersions) {
        this.setIdDespliegueinfraestructuradispersions(despliegueinfraestructuradispersions);
        return this;
    }

    public Pozo addIdDespliegueinfraestructuradispersion(Despliegueinfraestructuradispersion despliegueinfraestructuradispersion) {
        this.idDespliegueinfraestructuradispersions.add(despliegueinfraestructuradispersion);
        despliegueinfraestructuradispersion.getPozos().add(this);
        return this;
    }

    public Pozo removeIdDespliegueinfraestructuradispersion(Despliegueinfraestructuradispersion despliegueinfraestructuradispersion) {
        this.idDespliegueinfraestructuradispersions.remove(despliegueinfraestructuradispersion);
        despliegueinfraestructuradispersion.getPozos().remove(this);
        return this;
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
            ", latitud='" + getLatitud() + "'" +
            ", longitud='" + getLongitud() + "'" +
            "}";
    }
}
