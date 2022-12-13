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
    @Column(name = "calculo_valor_pago", nullable = false)
    private Double calculoValorPago;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @NotNull
    @Column(name = "valor_metro", nullable = false)
    private Float valorMetro;

    @OneToMany(mappedBy = "idDespliegueinfraestructuradispersion")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "idDespliegueInfraestructuraTroncalDistribucion", "idDespliegueinfraestructuradispersion" },
        allowSetters = true
    )
    private Set<Pago> pagos = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_despliegueinfraestructuradispersion__pozo",
        joinColumns = @JoinColumn(name = "despliegueinfraestructuradispersion_id"),
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

    @ManyToOne
    @JsonIgnoreProperties(value = { "despliegueInfraestructuraDispersions", "pagos", "pozos", "infraestructura" }, allowSetters = true)
    private DespliegueInfraestructuraTroncalDistribucion idDespliegueInfraestructuraTroncalDistribucion;

    @ManyToOne
    @JsonIgnoreProperties(value = { "despliegueinfraestructuradispersions", "autorizaciones", "infraestructuras" }, allowSetters = true)
    private Proveedor idProveedor;

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

    public Double getCalculoValorPago() {
        return this.calculoValorPago;
    }

    public Despliegueinfraestructuradispersion calculoValorPago(Double calculoValorPago) {
        this.setCalculoValorPago(calculoValorPago);
        return this;
    }

    public void setCalculoValorPago(Double calculoValorPago) {
        this.calculoValorPago = calculoValorPago;
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

    public Set<Pago> getPagos() {
        return this.pagos;
    }

    public void setPagos(Set<Pago> pagos) {
        if (this.pagos != null) {
            this.pagos.forEach(i -> i.setIdDespliegueinfraestructuradispersion(null));
        }
        if (pagos != null) {
            pagos.forEach(i -> i.setIdDespliegueinfraestructuradispersion(this));
        }
        this.pagos = pagos;
    }

    public Despliegueinfraestructuradispersion pagos(Set<Pago> pagos) {
        this.setPagos(pagos);
        return this;
    }

    public Despliegueinfraestructuradispersion addPago(Pago pago) {
        this.pagos.add(pago);
        pago.setIdDespliegueinfraestructuradispersion(this);
        return this;
    }

    public Despliegueinfraestructuradispersion removePago(Pago pago) {
        this.pagos.remove(pago);
        pago.setIdDespliegueinfraestructuradispersion(null);
        return this;
    }

    public Set<Pozo> getPozos() {
        return this.pozos;
    }

    public void setPozos(Set<Pozo> pozos) {
        this.pozos = pozos;
    }

    public Despliegueinfraestructuradispersion pozos(Set<Pozo> pozos) {
        this.setPozos(pozos);
        return this;
    }

    public Despliegueinfraestructuradispersion addPozo(Pozo pozo) {
        this.pozos.add(pozo);
        pozo.getIdDespliegueinfraestructuradispersions().add(this);
        return this;
    }

    public Despliegueinfraestructuradispersion removePozo(Pozo pozo) {
        this.pozos.remove(pozo);
        pozo.getIdDespliegueinfraestructuradispersions().remove(this);
        return this;
    }

    public DespliegueInfraestructuraTroncalDistribucion getIdDespliegueInfraestructuraTroncalDistribucion() {
        return this.idDespliegueInfraestructuraTroncalDistribucion;
    }

    public void setIdDespliegueInfraestructuraTroncalDistribucion(
        DespliegueInfraestructuraTroncalDistribucion despliegueInfraestructuraTroncalDistribucion
    ) {
        this.idDespliegueInfraestructuraTroncalDistribucion = despliegueInfraestructuraTroncalDistribucion;
    }

    public Despliegueinfraestructuradispersion idDespliegueInfraestructuraTroncalDistribucion(
        DespliegueInfraestructuraTroncalDistribucion despliegueInfraestructuraTroncalDistribucion
    ) {
        this.setIdDespliegueInfraestructuraTroncalDistribucion(despliegueInfraestructuraTroncalDistribucion);
        return this;
    }

    public Proveedor getIdProveedor() {
        return this.idProveedor;
    }

    public void setIdProveedor(Proveedor proveedor) {
        this.idProveedor = proveedor;
    }

    public Despliegueinfraestructuradispersion idProveedor(Proveedor proveedor) {
        this.setIdProveedor(proveedor);
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
            ", calculoValorPago=" + getCalculoValorPago() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", valorMetro=" + getValorMetro() +
            "}";
    }
}
