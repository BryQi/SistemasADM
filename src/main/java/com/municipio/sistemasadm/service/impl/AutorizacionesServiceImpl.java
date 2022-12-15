package com.municipio.sistemasadm.service.impl;

import com.municipio.sistemasadm.domain.Autorizaciones;
import com.municipio.sistemasadm.repository.AutorizacionesRepository;
import com.municipio.sistemasadm.service.AutorizacionesService;
import com.municipio.sistemasadm.service.dto.AutorizacionesDTO;
import com.municipio.sistemasadm.service.mapper.AutorizacionesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Autorizaciones}.
 */
@Service
@Transactional
public class AutorizacionesServiceImpl implements AutorizacionesService {

    private final Logger log = LoggerFactory.getLogger(AutorizacionesServiceImpl.class);

    private final AutorizacionesRepository autorizacionesRepository;

    private final AutorizacionesMapper autorizacionesMapper;

    public AutorizacionesServiceImpl(AutorizacionesRepository autorizacionesRepository, AutorizacionesMapper autorizacionesMapper) {
        this.autorizacionesRepository = autorizacionesRepository;
        this.autorizacionesMapper = autorizacionesMapper;
    }

    @Override
    public AutorizacionesDTO save(AutorizacionesDTO autorizacionesDTO) {
        log.debug("Request to save Autorizaciones : {}", autorizacionesDTO);
        Autorizaciones autorizaciones = autorizacionesMapper.toEntity(autorizacionesDTO);
        autorizaciones = autorizacionesRepository.save(autorizaciones);
        return autorizacionesMapper.toDto(autorizaciones);
    }

    @Override
    public AutorizacionesDTO update(AutorizacionesDTO autorizacionesDTO) {
        log.debug("Request to update Autorizaciones : {}", autorizacionesDTO);
        Autorizaciones autorizaciones = autorizacionesMapper.toEntity(autorizacionesDTO);
        autorizaciones = autorizacionesRepository.save(autorizaciones);
        return autorizacionesMapper.toDto(autorizaciones);
    }

    @Override
    public Optional<AutorizacionesDTO> partialUpdate(AutorizacionesDTO autorizacionesDTO) {
        log.debug("Request to partially update Autorizaciones : {}", autorizacionesDTO);

        return autorizacionesRepository
            .findById(autorizacionesDTO.getId())
            .map(existingAutorizaciones -> {
                autorizacionesMapper.partialUpdate(existingAutorizaciones, autorizacionesDTO);

                return existingAutorizaciones;
            })
            .map(autorizacionesRepository::save)
            .map(autorizacionesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AutorizacionesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Autorizaciones");
        return autorizacionesRepository.findAll(pageable).map(autorizacionesMapper::toDto);
    }

    public Page<AutorizacionesDTO> findAllWithEagerRelationships(Pageable pageable) {
        return autorizacionesRepository.findAllWithEagerRelationships(pageable).map(autorizacionesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AutorizacionesDTO> findOne(Long id) {
        log.debug("Request to get Autorizaciones : {}", id);
        return autorizacionesRepository.findOneWithEagerRelationships(id).map(autorizacionesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Autorizaciones : {}", id);
        autorizacionesRepository.deleteById(id);
    }
}
