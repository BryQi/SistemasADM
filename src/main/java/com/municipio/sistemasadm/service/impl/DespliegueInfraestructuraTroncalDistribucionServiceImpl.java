package com.municipio.sistemasadm.service.impl;

import com.municipio.sistemasadm.domain.DespliegueInfraestructuraTroncalDistribucion;
import com.municipio.sistemasadm.repository.DespliegueInfraestructuraTroncalDistribucionRepository;
import com.municipio.sistemasadm.service.DespliegueInfraestructuraTroncalDistribucionService;
import com.municipio.sistemasadm.service.dto.DespliegueInfraestructuraTroncalDistribucionDTO;
import com.municipio.sistemasadm.service.mapper.DespliegueInfraestructuraTroncalDistribucionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DespliegueInfraestructuraTroncalDistribucion}.
 */
@Service
@Transactional
public class DespliegueInfraestructuraTroncalDistribucionServiceImpl implements DespliegueInfraestructuraTroncalDistribucionService {

    private final Logger log = LoggerFactory.getLogger(DespliegueInfraestructuraTroncalDistribucionServiceImpl.class);

    private final DespliegueInfraestructuraTroncalDistribucionRepository despliegueInfraestructuraTroncalDistribucionRepository;

    private final DespliegueInfraestructuraTroncalDistribucionMapper despliegueInfraestructuraTroncalDistribucionMapper;

    public DespliegueInfraestructuraTroncalDistribucionServiceImpl(
        DespliegueInfraestructuraTroncalDistribucionRepository despliegueInfraestructuraTroncalDistribucionRepository,
        DespliegueInfraestructuraTroncalDistribucionMapper despliegueInfraestructuraTroncalDistribucionMapper
    ) {
        this.despliegueInfraestructuraTroncalDistribucionRepository = despliegueInfraestructuraTroncalDistribucionRepository;
        this.despliegueInfraestructuraTroncalDistribucionMapper = despliegueInfraestructuraTroncalDistribucionMapper;
    }

    @Override
    public DespliegueInfraestructuraTroncalDistribucionDTO save(
        DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO
    ) {
        log.debug("Request to save DespliegueInfraestructuraTroncalDistribucion : {}", despliegueInfraestructuraTroncalDistribucionDTO);
        DespliegueInfraestructuraTroncalDistribucion despliegueInfraestructuraTroncalDistribucion = despliegueInfraestructuraTroncalDistribucionMapper.toEntity(
            despliegueInfraestructuraTroncalDistribucionDTO
        );
        despliegueInfraestructuraTroncalDistribucion =
            despliegueInfraestructuraTroncalDistribucionRepository.save(despliegueInfraestructuraTroncalDistribucion);
        return despliegueInfraestructuraTroncalDistribucionMapper.toDto(despliegueInfraestructuraTroncalDistribucion);
    }

    @Override
    public DespliegueInfraestructuraTroncalDistribucionDTO update(
        DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO
    ) {
        log.debug("Request to update DespliegueInfraestructuraTroncalDistribucion : {}", despliegueInfraestructuraTroncalDistribucionDTO);
        DespliegueInfraestructuraTroncalDistribucion despliegueInfraestructuraTroncalDistribucion = despliegueInfraestructuraTroncalDistribucionMapper.toEntity(
            despliegueInfraestructuraTroncalDistribucionDTO
        );
        despliegueInfraestructuraTroncalDistribucion =
            despliegueInfraestructuraTroncalDistribucionRepository.save(despliegueInfraestructuraTroncalDistribucion);
        return despliegueInfraestructuraTroncalDistribucionMapper.toDto(despliegueInfraestructuraTroncalDistribucion);
    }

    @Override
    public Optional<DespliegueInfraestructuraTroncalDistribucionDTO> partialUpdate(
        DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO
    ) {
        log.debug(
            "Request to partially update DespliegueInfraestructuraTroncalDistribucion : {}",
            despliegueInfraestructuraTroncalDistribucionDTO
        );

        return despliegueInfraestructuraTroncalDistribucionRepository
            .findById(despliegueInfraestructuraTroncalDistribucionDTO.getId())
            .map(existingDespliegueInfraestructuraTroncalDistribucion -> {
                despliegueInfraestructuraTroncalDistribucionMapper.partialUpdate(
                    existingDespliegueInfraestructuraTroncalDistribucion,
                    despliegueInfraestructuraTroncalDistribucionDTO
                );

                return existingDespliegueInfraestructuraTroncalDistribucion;
            })
            .map(despliegueInfraestructuraTroncalDistribucionRepository::save)
            .map(despliegueInfraestructuraTroncalDistribucionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DespliegueInfraestructuraTroncalDistribucionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DespliegueInfraestructuraTroncalDistribucions");
        return despliegueInfraestructuraTroncalDistribucionRepository
            .findAll(pageable)
            .map(despliegueInfraestructuraTroncalDistribucionMapper::toDto);
    }

    public Page<DespliegueInfraestructuraTroncalDistribucionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return despliegueInfraestructuraTroncalDistribucionRepository
            .findAllWithEagerRelationships(pageable)
            .map(despliegueInfraestructuraTroncalDistribucionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DespliegueInfraestructuraTroncalDistribucionDTO> findOne(Long id) {
        log.debug("Request to get DespliegueInfraestructuraTroncalDistribucion : {}", id);
        return despliegueInfraestructuraTroncalDistribucionRepository
            .findOneWithEagerRelationships(id)
            .map(despliegueInfraestructuraTroncalDistribucionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DespliegueInfraestructuraTroncalDistribucion : {}", id);
        despliegueInfraestructuraTroncalDistribucionRepository.deleteById(id);
    }
}
