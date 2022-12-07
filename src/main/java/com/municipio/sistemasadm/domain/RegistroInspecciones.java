package com.municipio.sistemasadm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RegistroInspecciones.
 */
@Entity
@Table(name = "registro_inspecciones")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RegistroInspecciones implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "cumple_autorizacion")
    private Boolean cumpleAutorizacion;

    @NotNull
    @Column(name = "numero_autorizacion", nullable = false)
    private Integer numeroAutorizacion;

    @Column(name = "cumple_senaletica")
    private Boolean cumpleSenaletica;

    @Column(name = "cumple_conos_seguridad")
    private Boolean cumpleConosSeguridad;

    @Column(name = "cumple_etiquetado")
    private Boolean cumpleEtiquetado;

    @Column(name = "cumple_arreglo_cables")
    private Boolean cumpleArregloCables;

    @Column(name = "cumplelimpieza_orden_pozo")
    private Boolean cumplelimpiezaOrdenPozo;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "fotoPozos",
            "registroInspecciones",
            "idDespliegueInfraestructuraTroncalDistribucions",
            "idDespliegueinfraestructuradispersions",
        },
        allowSetters = true
    )
    private Pozo idPozo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RegistroInspecciones id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getCumpleAutorizacion() {
        return this.cumpleAutorizacion;
    }

    public RegistroInspecciones cumpleAutorizacion(Boolean cumpleAutorizacion) {
        this.setCumpleAutorizacion(cumpleAutorizacion);
        return this;
    }

    public void setCumpleAutorizacion(Boolean cumpleAutorizacion) {
        this.cumpleAutorizacion = cumpleAutorizacion;
    }

    public Integer getNumeroAutorizacion() {
        return this.numeroAutorizacion;
    }

    public RegistroInspecciones numeroAutorizacion(Integer numeroAutorizacion) {
        this.setNumeroAutorizacion(numeroAutorizacion);
        return this;
    }

    public void setNumeroAutorizacion(Integer numeroAutorizacion) {
        this.numeroAutorizacion = numeroAutorizacion;
    }

    public Boolean getCumpleSenaletica() {
        return this.cumpleSenaletica;
    }

    public RegistroInspecciones cumpleSenaletica(Boolean cumpleSenaletica) {
        this.setCumpleSenaletica(cumpleSenaletica);
        return this;
    }

    public void setCumpleSenaletica(Boolean cumpleSenaletica) {
        this.cumpleSenaletica = cumpleSenaletica;
    }

    public Boolean getCumpleConosSeguridad() {
        return this.cumpleConosSeguridad;
    }

    public RegistroInspecciones cumpleConosSeguridad(Boolean cumpleConosSeguridad) {
        this.setCumpleConosSeguridad(cumpleConosSeguridad);
        return this;
    }

    public void setCumpleConosSeguridad(Boolean cumpleConosSeguridad) {
        this.cumpleConosSeguridad = cumpleConosSeguridad;
    }

    public Boolean getCumpleEtiquetado() {
        return this.cumpleEtiquetado;
    }

    public RegistroInspecciones cumpleEtiquetado(Boolean cumpleEtiquetado) {
        this.setCumpleEtiquetado(cumpleEtiquetado);
        return this;
    }

    public void setCumpleEtiquetado(Boolean cumpleEtiquetado) {
        this.cumpleEtiquetado = cumpleEtiquetado;
    }

    public Boolean getCumpleArregloCables() {
        return this.cumpleArregloCables;
    }

    public RegistroInspecciones cumpleArregloCables(Boolean cumpleArregloCables) {
        this.setCumpleArregloCables(cumpleArregloCables);
        return this;
    }

    public void setCumpleArregloCables(Boolean cumpleArregloCables) {
        this.cumpleArregloCables = cumpleArregloCables;
    }

    public Boolean getCumplelimpiezaOrdenPozo() {
        return this.cumplelimpiezaOrdenPozo;
    }

    public RegistroInspecciones cumplelimpiezaOrdenPozo(Boolean cumplelimpiezaOrdenPozo) {
        this.setCumplelimpiezaOrdenPozo(cumplelimpiezaOrdenPozo);
        return this;
    }

    public void setCumplelimpiezaOrdenPozo(Boolean cumplelimpiezaOrdenPozo) {
        this.cumplelimpiezaOrdenPozo = cumplelimpiezaOrdenPozo;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public RegistroInspecciones createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Pozo getIdPozo() {
        return this.idPozo;
    }

    public void setIdPozo(Pozo pozo) {
        this.idPozo = pozo;
    }

    public RegistroInspecciones idPozo(Pozo pozo) {
        this.setIdPozo(pozo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegistroInspecciones)) {
            return false;
        }
        return id != null && id.equals(((RegistroInspecciones) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RegistroInspecciones{" +
            "id=" + getId() +
            ", cumpleAutorizacion='" + getCumpleAutorizacion() + "'" +
            ", numeroAutorizacion=" + getNumeroAutorizacion() +
            ", cumpleSenaletica='" + getCumpleSenaletica() + "'" +
            ", cumpleConosSeguridad='" + getCumpleConosSeguridad() + "'" +
            ", cumpleEtiquetado='" + getCumpleEtiquetado() + "'" +
            ", cumpleArregloCables='" + getCumpleArregloCables() + "'" +
            ", cumplelimpiezaOrdenPozo='" + getCumplelimpiezaOrdenPozo() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
