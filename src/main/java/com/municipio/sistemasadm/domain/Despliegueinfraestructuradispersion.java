package com.municipio.sistemasadm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.municipio.sistemasadm.domain.enumeration.Origen;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Despliegueinfraestructuradispersion.
 */
@Entity
@Table(name = "despliegueinfraestructuradispersion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Despliegueinfraestructuradispersion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre_cliente", nullable = false)
    private String nombreCliente;

    @NotNull
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @NotNull
    @Column(name = "fecha_instalacion", nullable = false)
    private LocalDate fechaInstalacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "origen")
    private Origen origen;

    @NotNull
    @Column(name = "destino", nullable = false)
    private String destino;

    @NotNull
    @Column(name = "descripcion_de_pozos_usados_ruta", nullable = false)
    private String descripcionDePozosUsadosRuta;

    @NotNull
    @Column(name = "metraje_inicial", nullable = false)
    private Double metrajeInicial;

    @NotNull
    @Column(name = "metraje_final", nullable = false)
    private Double metrajeFinal;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @NotNull
    @Column(name = "valor_metro", nullable = false)
    private Float valorMetro;

    @NotNull
    @Column(name = "calculo_valor_pago_d", nullable = false)
    private Float calculoValorPagoD;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "pozos", "razonSocial" }, allowSetters = true)
    private DespliegueInfraestructuraTroncalDistribucion nombreRuta;

    @ManyToOne(optional = false)
    @NotNull
    private Proveedor razonSocial;

    @ManyToMany
    @NotNull
    @JoinTable(
        name = "rel_despliegueinfraestructuradispersion__numeropozo",
        joinColumns = @JoinColumn(name = "despliegueinfraestructuradispersion_id"),
        inverseJoinColumns = @JoinColumn(name = "numeropozo_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Pozo> numeropozos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Despliegueinfraestructuradispersion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCliente() {
        return this.nombreCliente;
    }

    public Despliegueinfraestructuradispersion nombreCliente(String nombreCliente) {
        this.setNombreCliente(nombreCliente);
        return this;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Despliegueinfraestructuradispersion direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDate getFechaInstalacion() {
        return this.fechaInstalacion;
    }

    public Despliegueinfraestructuradispersion fechaInstalacion(LocalDate fechaInstalacion) {
        this.setFechaInstalacion(fechaInstalacion);
        return this;
    }

    public void setFechaInstalacion(LocalDate fechaInstalacion) {
        this.fechaInstalacion = fechaInstalacion;
    }

    public Origen getOrigen() {
        return this.origen;
    }

    public Despliegueinfraestructuradispersion origen(Origen origen) {
        this.setOrigen(origen);
        return this;
    }

    public void setOrigen(Origen origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return this.destino;
    }

    public Despliegueinfraestructuradispersion destino(String destino) {
        this.setDestino(destino);
        return this;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getDescripcionDePozosUsadosRuta() {
        return this.descripcionDePozosUsadosRuta;
    }

    public Despliegueinfraestructuradispersion descripcionDePozosUsadosRuta(String descripcionDePozosUsadosRuta) {
        this.setDescripcionDePozosUsadosRuta(descripcionDePozosUsadosRuta);
        return this;
    }

    public void setDescripcionDePozosUsadosRuta(String descripcionDePozosUsadosRuta) {
        this.descripcionDePozosUsadosRuta = descripcionDePozosUsadosRuta;
    }

    public Double getMetrajeInicial() {
        return this.metrajeInicial;
    }

    public Despliegueinfraestructuradispersion metrajeInicial(Double metrajeInicial) {
        this.setMetrajeInicial(metrajeInicial);
        return this;
    }

    public void setMetrajeInicial(Double metrajeInicial) {
        this.metrajeInicial = metrajeInicial;
    }

    public Double getMetrajeFinal() {
        return this.metrajeFinal;
    }

    public Despliegueinfraestructuradispersion metrajeFinal(Double metrajeFinal) {
        this.setMetrajeFinal(metrajeFinal);
        return this;
    }

    public void setMetrajeFinal(Double metrajeFinal) {
        this.metrajeFinal = metrajeFinal;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Despliegueinfraestructuradispersion createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Float getValorMetro() {
        return this.valorMetro;
    }

    public Despliegueinfraestructuradispersion valorMetro(Float valorMetro) {
        this.setValorMetro(valorMetro);
        return this;
    }

    public void setValorMetro(Float valorMetro) {
        this.valorMetro = valorMetro;
    }

    public Float getCalculoValorPagoD() {
        return this.calculoValorPagoD;
    }

    public Despliegueinfraestructuradispersion calculoValorPagoD(Float calculoValorPagoD) {
        this.setCalculoValorPagoD(calculoValorPagoD);
        return this;
    }

    public void setCalculoValorPagoD(Float calculoValorPagoD) {
        this.calculoValorPagoD = calculoValorPagoD;
    }

    public DespliegueInfraestructuraTroncalDistribucion getNombreRuta() {
        return this.nombreRuta;
    }

    public void setNombreRuta(DespliegueInfraestructuraTroncalDistribucion despliegueInfraestructuraTroncalDistribucion) {
        this.nombreRuta = despliegueInfraestructuraTroncalDistribucion;
    }

    public Despliegueinfraestructuradispersion nombreRuta(
        DespliegueInfraestructuraTroncalDistribucion despliegueInfraestructuraTroncalDistribucion
    ) {
        this.setNombreRuta(despliegueInfraestructuraTroncalDistribucion);
        return this;
    }

    public Proveedor getRazonSocial() {
        return this.razonSocial;
    }

    public void setRazonSocial(Proveedor proveedor) {
        this.razonSocial = proveedor;
    }

    public Despliegueinfraestructuradispersion razonSocial(Proveedor proveedor) {
        this.setRazonSocial(proveedor);
        return this;
    }

    public Set<Pozo> getNumeropozos() {
        return this.numeropozos;
    }

    public void setNumeropozos(Set<Pozo> pozos) {
        this.numeropozos = pozos;
    }

    public Despliegueinfraestructuradispersion numeropozos(Set<Pozo> pozos) {
        this.setNumeropozos(pozos);
        return this;
    }

    public Despliegueinfraestructuradispersion addNumeropozo(Pozo pozo) {
        this.numeropozos.add(pozo);
        return this;
    }

    public Despliegueinfraestructuradispersion removeNumeropozo(Pozo pozo) {
        this.numeropozos.remove(pozo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Despliegueinfraestructuradispersion)) {
            return false;
        }
        return id != null && id.equals(((Despliegueinfraestructuradispersion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Despliegueinfraestructuradispersion{" +
            "id=" + getId() +
            ", nombreCliente='" + getNombreCliente() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", fechaInstalacion='" + getFechaInstalacion() + "'" +
            ", origen='" + getOrigen() + "'" +
            ", destino='" + getDestino() + "'" +
            ", descripcionDePozosUsadosRuta='" + getDescripcionDePozosUsadosRuta() + "'" +
            ", metrajeInicial=" + getMetrajeInicial() +
            ", metrajeFinal=" + getMetrajeFinal() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", valorMetro=" + getValorMetro() +
            ", calculoValorPagoD=" + getCalculoValorPagoD() +
            "}";
    }
}
