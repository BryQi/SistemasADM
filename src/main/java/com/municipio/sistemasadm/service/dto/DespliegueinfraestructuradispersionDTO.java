package com.municipio.sistemasadm.service.dto;

import com.municipio.sistemasadm.domain.enumeration.Origen;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.municipio.sistemasadm.domain.Despliegueinfraestructuradispersion} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DespliegueinfraestructuradispersionDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombreCliente;

    @NotNull
    private String direccion;

    @NotNull
    private LocalDate fechaInstalacion;

    private Origen origen;

    @NotNull
    private String destino;

    @NotNull
    private String descripcionDePozosUsadosRuta;

    @NotNull
    private Double metrajeInicial;

    @NotNull
    private Double metrajeFinal;

    @NotNull
    private Double calculoValorPago;

    @NotNull
    private ZonedDateTime createdAt;

    @NotNull
    private Float valorMetro;

    private Set<PozoDTO> pozos = new HashSet<>();

    private DespliegueInfraestructuraTroncalDistribucionDTO idDespliegueInfraestructuraTroncalDistribucion;

    private ProveedorDTO idProveedor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDate getFechaInstalacion() {
        return fechaInstalacion;
    }

    public void setFechaInstalacion(LocalDate fechaInstalacion) {
        this.fechaInstalacion = fechaInstalacion;
    }

    public Origen getOrigen() {
        return origen;
    }

    public void setOrigen(Origen origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getDescripcionDePozosUsadosRuta() {
        return descripcionDePozosUsadosRuta;
    }

    public void setDescripcionDePozosUsadosRuta(String descripcionDePozosUsadosRuta) {
        this.descripcionDePozosUsadosRuta = descripcionDePozosUsadosRuta;
    }

    public Double getMetrajeInicial() {
        return metrajeInicial;
    }

    public void setMetrajeInicial(Double metrajeInicial) {
        this.metrajeInicial = metrajeInicial;
    }

    public Double getMetrajeFinal() {
        return metrajeFinal;
    }

    public void setMetrajeFinal(Double metrajeFinal) {
        this.metrajeFinal = metrajeFinal;
    }

    public Double getCalculoValorPago() {
        return calculoValorPago;
    }

    public void setCalculoValorPago(Double calculoValorPago) {
        this.calculoValorPago = calculoValorPago;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Float getValorMetro() {
        return valorMetro;
    }

    public void setValorMetro(Float valorMetro) {
        this.valorMetro = valorMetro;
    }

    public Set<PozoDTO> getPozos() {
        return pozos;
    }

    public void setPozos(Set<PozoDTO> pozos) {
        this.pozos = pozos;
    }

    public DespliegueInfraestructuraTroncalDistribucionDTO getIdDespliegueInfraestructuraTroncalDistribucion() {
        return idDespliegueInfraestructuraTroncalDistribucion;
    }

    public void setIdDespliegueInfraestructuraTroncalDistribucion(
        DespliegueInfraestructuraTroncalDistribucionDTO idDespliegueInfraestructuraTroncalDistribucion
    ) {
        this.idDespliegueInfraestructuraTroncalDistribucion = idDespliegueInfraestructuraTroncalDistribucion;
    }

    public ProveedorDTO getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(ProveedorDTO idProveedor) {
        this.idProveedor = idProveedor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DespliegueinfraestructuradispersionDTO)) {
            return false;
        }

        DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO = (DespliegueinfraestructuradispersionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, despliegueinfraestructuradispersionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DespliegueinfraestructuradispersionDTO{" +
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
            ", pozos=" + getPozos() +
            ", idDespliegueInfraestructuraTroncalDistribucion=" + getIdDespliegueInfraestructuraTroncalDistribucion() +
            ", idProveedor=" + getIdProveedor() +
            "}";
    }
}
