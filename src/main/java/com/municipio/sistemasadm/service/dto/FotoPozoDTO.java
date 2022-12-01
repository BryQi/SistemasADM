package com.municipio.sistemasadm.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.municipio.sistemasadm.domain.FotoPozo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FotoPozoDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] foto;

    private String fotoContentType;

    @NotNull
    private String descripcion;

    @NotNull
    private ZonedDateTime createdAt;

    private PozoDTO idPozo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public PozoDTO getIdPozo() {
        return idPozo;
    }

    public void setIdPozo(PozoDTO idPozo) {
        this.idPozo = idPozo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FotoPozoDTO)) {
            return false;
        }

        FotoPozoDTO fotoPozoDTO = (FotoPozoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fotoPozoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FotoPozoDTO{" +
            "id=" + getId() +
            ", foto='" + getFoto() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", idPozo=" + getIdPozo() +
            "}";
    }
}
