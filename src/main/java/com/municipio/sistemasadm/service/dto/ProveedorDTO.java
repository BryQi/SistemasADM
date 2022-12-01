package com.municipio.sistemasadm.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.municipio.sistemasadm.domain.Proveedor} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProveedorDTO implements Serializable {

    private Long id;

    @NotNull
    private String razonSocial;

    @NotNull
    private String contactoTecnico;

    @NotNull
    private String correoEmpresa;

    @NotNull
    private String direccion;

    @NotNull
    private Integer celular;

    @NotNull
    private ZonedDateTime createdAt;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getContactoTecnico() {
        return contactoTecnico;
    }

    public void setContactoTecnico(String contactoTecnico) {
        this.contactoTecnico = contactoTecnico;
    }

    public String getCorreoEmpresa() {
        return correoEmpresa;
    }

    public void setCorreoEmpresa(String correoEmpresa) {
        this.correoEmpresa = correoEmpresa;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getCelular() {
        return celular;
    }

    public void setCelular(Integer celular) {
        this.celular = celular;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProveedorDTO)) {
            return false;
        }

        ProveedorDTO proveedorDTO = (ProveedorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, proveedorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProveedorDTO{" +
            "id=" + getId() +
            ", razonSocial='" + getRazonSocial() + "'" +
            ", contactoTecnico='" + getContactoTecnico() + "'" +
            ", correoEmpresa='" + getCorreoEmpresa() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", celular=" + getCelular() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
