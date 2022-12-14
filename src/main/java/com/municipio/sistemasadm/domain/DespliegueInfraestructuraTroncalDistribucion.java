package com.municipio.sistemasadm.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DespliegueInfraestructuraTroncalDistribucion.
 */
@Entity
@Table(name = "despliegue_infraestructura_troncal_distribucion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DespliegueInfraestructuraTroncalDistribucion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre_ruta", nullable = false)
    private String nombreRuta;

    @NotNull
    @Column(name = "descripcion_ruta", nullable = false)
    private String descripcionRuta;

    @NotNull
    @Column(name = "metraje_inicial", nullable = false)
    private Double metrajeInicial;

    @NotNull
    @Column(name = "metraje_final", nullable = false)
    private Double metrajeFinal;

    @NotNull
    @Column(name = "calculo_valor_pago", nullable = false)
    private Double calculoValorPago;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @NotNull
    @Column(name = "valor_metro", nullable = false)
    private Float valorMetro;

    @ManyToMany
    @NotNull
    @JoinTable(
        name = "rel_despliegue_infraestructura_troncal_distribucion__pozo",
        joinColumns = @JoinColumn(name = "despliegue_infraestructura_troncal_distribucion_id"),
        inverseJoinColumns = @JoinColumn(name = "pozo_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Pozo> pozos = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private Proveedor razonSocial;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DespliegueInfraestructuraTroncalDistribucion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreRuta() {
        return this.nombreRuta;
    }

    public DespliegueInfraestructuraTroncalDistribucion nombreRuta(String nombreRuta) {
        this.setNombreRuta(nombreRuta);
        return this;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public String getDescripcionRuta() {
        return this.descripcionRuta;
    }

    public DespliegueInfraestructuraTroncalDistribucion descripcionRuta(String descripcionRuta) {
        this.setDescripcionRuta(descripcionRuta);
        return this;
    }

    public void setDescripcionRuta(String descripcionRuta) {
        this.descripcionRuta = descripcionRuta;
    }

    public Double getMetrajeInicial() {
        return this.metrajeInicial;
    }

    public DespliegueInfraestructuraTroncalDistribucion metrajeInicial(Double metrajeInicial) {
        this.setMetrajeInicial(metrajeInicial);
        return this;
    }

    public void setMetrajeInicial(Double metrajeInicial) {
        this.metrajeInicial = metrajeInicial;
    }

    public Double getMetrajeFinal() {
        return this.metrajeFinal;
    }

    public DespliegueInfraestructuraTroncalDistribucion metrajeFinal(Double metrajeFinal) {
        this.setMetrajeFinal(metrajeFinal);
        return this;
    }

    public void setMetrajeFinal(Double metrajeFinal) {
        this.metrajeFinal = metrajeFinal;
    }

    public Double getCalculoValorPago() {
        return this.calculoValorPago;
    }

    public DespliegueInfraestructuraTroncalDistribucion calculoValorPago(Double calculoValorPago) {
        this.setCalculoValorPago(calculoValorPago);
        return this;
    }

    public void setCalculoValorPago(Double calculoValorPago) {
        this.calculoValorPago = calculoValorPago;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public DespliegueInfraestructuraTroncalDistribucion createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Float getValorMetro() {
        return this.valorMetro;
    }

    public DespliegueInfraestructuraTroncalDistribucion valorMetro(Float valorMetro) {
        this.setValorMetro(valorMetro);
        return this;
    }

    public void setValorMetro(Float valorMetro) {
        this.valorMetro = valorMetro;
    }

    public Set<Pozo> getPozos() {
        return this.pozos;
    }

    public void setPozos(Set<Pozo> pozos) {
        this.pozos = pozos;
    }

    public DespliegueInfraestructuraTroncalDistribucion pozos(Set<Pozo> pozos) {
        this.setPozos(pozos);
        return this;
    }

    public DespliegueInfraestructuraTroncalDistribucion addPozo(Pozo pozo) {
        this.pozos.add(pozo);
        return this;
    }

    public DespliegueInfraestructuraTroncalDistribucion removePozo(Pozo pozo) {
        this.pozos.remove(pozo);
        return this;
    }

    public Proveedor getRazonSocial() {
        return this.razonSocial;
    }

    public void setRazonSocial(Proveedor proveedor) {
        this.razonSocial = proveedor;
    }

    public DespliegueInfraestructuraTroncalDistribucion razonSocial(Proveedor proveedor) {
        this.setRazonSocial(proveedor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DespliegueInfraestructuraTroncalDistribucion)) {
            return false;
        }
        return id != null && id.equals(((DespliegueInfraestructuraTroncalDistribucion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DespliegueInfraestructuraTroncalDistribucion{" +
            "id=" + getId() +
            ", nombreRuta='" + getNombreRuta() + "'" +
            ", descripcionRuta='" + getDescripcionRuta() + "'" +
            ", metrajeInicial=" + getMetrajeInicial() +
            ", metrajeFinal=" + getMetrajeFinal() +
            ", calculoValorPago=" + getCalculoValorPago() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", valorMetro=" + getValorMetro() +
            "}";
    }
}
