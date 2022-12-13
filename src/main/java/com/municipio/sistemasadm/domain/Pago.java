package com.municipio.sistemasadm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Pago.
 */
@Entity
@Table(name = "pago")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Pago implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;

    @NotNull
    @Column(name = "pago", nullable = false)
    private Integer pago;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @ManyToOne
    @JsonIgnoreProperties(value = { "despliegueInfraestructuraDispersions", "pagos", "pozos", "infraestructura" }, allowSetters = true)
    private DespliegueInfraestructuraTroncalDistribucion idDespliegueInfraestructuraTroncalDistribucion;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "pagos", "pozos", "idDespliegueInfraestructuraTroncalDistribucion", "idProveedor" },
        allowSetters = true
    )
    private Despliegueinfraestructuradispersion idDespliegueinfraestructuradispersion;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pago id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaPago() {
        return this.fechaPago;
    }

    public Pago fechaPago(LocalDate fechaPago) {
        this.setFechaPago(fechaPago);
        return this;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Integer getPago() {
        return this.pago;
    }

    public Pago pago(Integer pago) {
        this.setPago(pago);
        return this;
    }

    public void setPago(Integer pago) {
        this.pago = pago;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Pago createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public DespliegueInfraestructuraTroncalDistribucion getIdDespliegueInfraestructuraTroncalDistribucion() {
        return this.idDespliegueInfraestructuraTroncalDistribucion;
    }

    public void setIdDespliegueInfraestructuraTroncalDistribucion(
        DespliegueInfraestructuraTroncalDistribucion despliegueInfraestructuraTroncalDistribucion
    ) {
        this.idDespliegueInfraestructuraTroncalDistribucion = despliegueInfraestructuraTroncalDistribucion;
    }

    public Pago idDespliegueInfraestructuraTroncalDistribucion(
        DespliegueInfraestructuraTroncalDistribucion despliegueInfraestructuraTroncalDistribucion
    ) {
        this.setIdDespliegueInfraestructuraTroncalDistribucion(despliegueInfraestructuraTroncalDistribucion);
        return this;
    }

    public Despliegueinfraestructuradispersion getIdDespliegueinfraestructuradispersion() {
        return this.idDespliegueinfraestructuradispersion;
    }

    public void setIdDespliegueinfraestructuradispersion(Despliegueinfraestructuradispersion despliegueinfraestructuradispersion) {
        this.idDespliegueinfraestructuradispersion = despliegueinfraestructuradispersion;
    }

    public Pago idDespliegueinfraestructuradispersion(Despliegueinfraestructuradispersion despliegueinfraestructuradispersion) {
        this.setIdDespliegueinfraestructuradispersion(despliegueinfraestructuradispersion);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pago)) {
            return false;
        }
        return id != null && id.equals(((Pago) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pago{" +
            "id=" + getId() +
            ", fechaPago='" + getFechaPago() + "'" +
            ", pago=" + getPago() +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
