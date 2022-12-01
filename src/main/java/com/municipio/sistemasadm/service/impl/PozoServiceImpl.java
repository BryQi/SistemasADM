package com.municipio.sistemasadm.service.impl;

import com.municipio.sistemasadm.domain.Pozo;
import com.municipio.sistemasadm.repository.PozoRepository;
import com.municipio.sistemasadm.service.PozoService;
import com.municipio.sistemasadm.service.dto.PozoDTO;
import com.municipio.sistemasadm.service.mapper.PozoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Pozo}.
 */
@Service
@Transactional
public class PozoServiceImpl implements PozoService {

    private final Logger log = LoggerFactory.getLogger(PozoServiceImpl.class);

    private final PozoRepository pozoRepository;

    private final PozoMapper pozoMapper;

    public PozoServiceImpl(PozoRepository pozoRepository, PozoMapper pozoMapper) {
        this.pozoRepository = pozoRepository;
        this.pozoMapper = pozoMapper;
    }

    @Override
    public PozoDTO save(PozoDTO pozoDTO) {
        log.debug("Request to save Pozo : {}", pozoDTO);
        Pozo pozo = pozoMapper.toEntity(pozoDTO);
        pozo = pozoRepository.save(pozo);
        return pozoMapper.toDto(pozo);
    }

    @Override
    public PozoDTO update(PozoDTO pozoDTO) {
        log.debug("Request to update Pozo : {}", pozoDTO);
        Pozo pozo = pozoMapper.toEntity(pozoDTO);
        pozo = pozoRepository.save(pozo);
        return pozoMapper.toDto(pozo);
    }

    @Override
    public Optional<PozoDTO> partialUpdate(PozoDTO pozoDTO) {
        log.debug("Request to partially update Pozo : {}", pozoDTO);

        return pozoRepository
            .findById(pozoDTO.getId())
            .map(existingPozo -> {
                pozoMapper.partialUpdate(existingPozo, pozoDTO);

                return existingPozo;
            })
            .map(pozoRepository::save)
            .map(pozoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PozoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pozos");
        return pozoRepository.findAll(pageable).map(pozoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PozoDTO> findOne(Long id) {
        log.debug("Request to get Pozo : {}", id);
        return pozoRepository.findById(id).map(pozoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pozo : {}", id);
        pozoRepository.deleteById(id);
    }
}
