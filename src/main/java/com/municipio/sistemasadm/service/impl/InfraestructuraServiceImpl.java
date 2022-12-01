package com.municipio.sistemasadm.service.impl;

import com.municipio.sistemasadm.domain.Infraestructura;
import com.municipio.sistemasadm.repository.InfraestructuraRepository;
import com.municipio.sistemasadm.service.InfraestructuraService;
import com.municipio.sistemasadm.service.dto.InfraestructuraDTO;
import com.municipio.sistemasadm.service.mapper.InfraestructuraMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Infraestructura}.
 */
@Service
@Transactional
public class InfraestructuraServiceImpl implements InfraestructuraService {

    private final Logger log = LoggerFactory.getLogger(InfraestructuraServiceImpl.class);

    private final InfraestructuraRepository infraestructuraRepository;

    private final InfraestructuraMapper infraestructuraMapper;

    public InfraestructuraServiceImpl(InfraestructuraRepository infraestructuraRepository, InfraestructuraMapper infraestructuraMapper) {
        this.infraestructuraRepository = infraestructuraRepository;
        this.infraestructuraMapper = infraestructuraMapper;
    }

    @Override
    public InfraestructuraDTO save(InfraestructuraDTO infraestructuraDTO) {
        log.debug("Request to save Infraestructura : {}", infraestructuraDTO);
        Infraestructura infraestructura = infraestructuraMapper.toEntity(infraestructuraDTO);
        infraestructura = infraestructuraRepository.save(infraestructura);
        return infraestructuraMapper.toDto(infraestructura);
    }

    @Override
    public InfraestructuraDTO update(InfraestructuraDTO infraestructuraDTO) {
        log.debug("Request to update Infraestructura : {}", infraestructuraDTO);
        Infraestructura infraestructura = infraestructuraMapper.toEntity(infraestructuraDTO);
        infraestructura = infraestructuraRepository.save(infraestructura);
        return infraestructuraMapper.toDto(infraestructura);
    }

    @Override
    public Optional<InfraestructuraDTO> partialUpdate(InfraestructuraDTO infraestructuraDTO) {
        log.debug("Request to partially update Infraestructura : {}", infraestructuraDTO);

        return infraestructuraRepository
            .findById(infraestructuraDTO.getId())
            .map(existingInfraestructura -> {
                infraestructuraMapper.partialUpdate(existingInfraestructura, infraestructuraDTO);

                return existingInfraestructura;
            })
            .map(infraestructuraRepository::save)
            .map(infraestructuraMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InfraestructuraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Infraestructuras");
        return infraestructuraRepository.findAll(pageable).map(infraestructuraMapper::toDto);
    }

    public Page<InfraestructuraDTO> findAllWithEagerRelationships(Pageable pageable) {
        return infraestructuraRepository.findAllWithEagerRelationships(pageable).map(infraestructuraMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InfraestructuraDTO> findOne(Long id) {
        log.debug("Request to get Infraestructura : {}", id);
        return infraestructuraRepository.findOneWithEagerRelationships(id).map(infraestructuraMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Infraestructura : {}", id);
        infraestructuraRepository.deleteById(id);
    }
}
