package com.municipio.sistemasadm.service.impl;

import com.municipio.sistemasadm.domain.RegistroInspecciones;
import com.municipio.sistemasadm.repository.RegistroInspeccionesRepository;
import com.municipio.sistemasadm.service.RegistroInspeccionesService;
import com.municipio.sistemasadm.service.dto.RegistroInspeccionesDTO;
import com.municipio.sistemasadm.service.mapper.RegistroInspeccionesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RegistroInspecciones}.
 */
@Service
@Transactional
public class RegistroInspeccionesServiceImpl implements RegistroInspeccionesService {

    private final Logger log = LoggerFactory.getLogger(RegistroInspeccionesServiceImpl.class);

    private final RegistroInspeccionesRepository registroInspeccionesRepository;

    private final RegistroInspeccionesMapper registroInspeccionesMapper;

    public RegistroInspeccionesServiceImpl(
        RegistroInspeccionesRepository registroInspeccionesRepository,
        RegistroInspeccionesMapper registroInspeccionesMapper
    ) {
        this.registroInspeccionesRepository = registroInspeccionesRepository;
        this.registroInspeccionesMapper = registroInspeccionesMapper;
    }

    @Override
    public RegistroInspeccionesDTO save(RegistroInspeccionesDTO registroInspeccionesDTO) {
        log.debug("Request to save RegistroInspecciones : {}", registroInspeccionesDTO);
        RegistroInspecciones registroInspecciones = registroInspeccionesMapper.toEntity(registroInspeccionesDTO);
        registroInspecciones = registroInspeccionesRepository.save(registroInspecciones);
        return registroInspeccionesMapper.toDto(registroInspecciones);
    }

    @Override
    public RegistroInspeccionesDTO update(RegistroInspeccionesDTO registroInspeccionesDTO) {
        log.debug("Request to update RegistroInspecciones : {}", registroInspeccionesDTO);
        RegistroInspecciones registroInspecciones = registroInspeccionesMapper.toEntity(registroInspeccionesDTO);
        registroInspecciones = registroInspeccionesRepository.save(registroInspecciones);
        return registroInspeccionesMapper.toDto(registroInspecciones);
    }

    @Override
    public Optional<RegistroInspeccionesDTO> partialUpdate(RegistroInspeccionesDTO registroInspeccionesDTO) {
        log.debug("Request to partially update RegistroInspecciones : {}", registroInspeccionesDTO);

        return registroInspeccionesRepository
            .findById(registroInspeccionesDTO.getId())
            .map(existingRegistroInspecciones -> {
                registroInspeccionesMapper.partialUpdate(existingRegistroInspecciones, registroInspeccionesDTO);

                return existingRegistroInspecciones;
            })
            .map(registroInspeccionesRepository::save)
            .map(registroInspeccionesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RegistroInspeccionesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RegistroInspecciones");
        return registroInspeccionesRepository.findAll(pageable).map(registroInspeccionesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RegistroInspeccionesDTO> findOne(Long id) {
        log.debug("Request to get RegistroInspecciones : {}", id);
        return registroInspeccionesRepository.findById(id).map(registroInspeccionesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RegistroInspecciones : {}", id);
        registroInspeccionesRepository.deleteById(id);
    }
}
