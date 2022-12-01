package com.municipio.sistemasadm.service.dto;

import com.municipio.sistemasadm.domain.enumeration.TipoPozo;
import com.municipio.sistemasadm.domain.enumeration.Ubicacion;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.municipio.sistemasadm.domain.Pozo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PozoDTO implements Serializable {

    private Long id;

    @NotNull
    private String numeropozo;

    @NotNull
    private String direccion;

    private Ubicacion ubicacion;

    private TipoPozo tipopozo;

    @NotNull
    private ZonedDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeropozo() {
        return numeropozo;
    }

    public void setNumeropozo(String numeropozo) {
        this.numeropozo = numeropozo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public TipoPozo getTipopozo() {
        return tipopozo;
    }

    public void setTipopozo(TipoPozo tipopozo) {
        this.tipopozo = tipopozo;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PozoDTO)) {
            return false;
        }

        PozoDTO pozoDTO = (PozoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pozoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PozoDTO{" +
            "id=" + getId() +
            ", numeropozo='" + getNumeropozo() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", ubicacion='" + getUbicacion() + "'" +
            ", tipopozo='" + getTipopozo() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
