package com.municipio.sistemasadm.service.dto;

import com.municipio.sistemasadm.domain.enumeration.Tipo;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.municipio.sistemasadm.domain.Infraestructura} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InfraestructuraDTO implements Serializable {

    private Long id;

    private Tipo tipo;

    @NotNull
    private ZonedDateTime createdAt;

    private Set<PozoDTO> pozos = new HashSet<>();

    private ProveedorDTO idProveedor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<PozoDTO> getPozos() {
        return pozos;
    }

    public void setPozos(Set<PozoDTO> pozos) {
        this.pozos = pozos;
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
        if (!(o instanceof InfraestructuraDTO)) {
            return false;
        }

        InfraestructuraDTO infraestructuraDTO = (InfraestructuraDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, infraestructuraDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InfraestructuraDTO{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", pozos=" + getPozos() +
            ", idProveedor=" + getIdProveedor() +
            "}";
    }
}
