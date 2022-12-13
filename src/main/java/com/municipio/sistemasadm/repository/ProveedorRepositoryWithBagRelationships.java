package com.municipio.sistemasadm.repository;

import com.municipio.sistemasadm.domain.Proveedor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ProveedorRepositoryWithBagRelationships {
    Optional<Proveedor> fetchBagRelationships(Optional<Proveedor> proveedor);

    List<Proveedor> fetchBagRelationships(List<Proveedor> proveedors);

    Page<Proveedor> fetchBagRelationships(Page<Proveedor> proveedors);
}
