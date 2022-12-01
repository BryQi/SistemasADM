package com.municipio.sistemasadm.service.mapper;

import com.municipio.sistemasadm.domain.Proveedor;
import com.municipio.sistemasadm.domain.User;
import com.municipio.sistemasadm.service.dto.ProveedorDTO;
import com.municipio.sistemasadm.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Proveedor} and its DTO {@link ProveedorDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProveedorMapper extends EntityMapper<ProveedorDTO, Proveedor> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    ProveedorDTO toDto(Proveedor s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
