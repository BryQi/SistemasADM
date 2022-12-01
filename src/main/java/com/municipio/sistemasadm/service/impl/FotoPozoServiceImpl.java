package com.municipio.sistemasadm.service.impl;

import com.municipio.sistemasadm.domain.FotoPozo;
import com.municipio.sistemasadm.repository.FotoPozoRepository;
import com.municipio.sistemasadm.service.FotoPozoService;
import com.municipio.sistemasadm.service.dto.FotoPozoDTO;
import com.municipio.sistemasadm.service.mapper.FotoPozoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FotoPozo}.
 */
@Service
@Transactional
public class FotoPozoServiceImpl implements FotoPozoService {

    private final Logger log = LoggerFactory.getLogger(FotoPozoServiceImpl.class);

    private final FotoPozoRepository fotoPozoRepository;

    private final FotoPozoMapper fotoPozoMapper;

    public FotoPozoServiceImpl(FotoPozoRepository fotoPozoRepository, FotoPozoMapper fotoPozoMapper) {
        this.fotoPozoRepository = fotoPozoRepository;
        this.fotoPozoMapper = fotoPozoMapper;
    }

    @Override
    public FotoPozoDTO save(FotoPozoDTO fotoPozoDTO) {
        log.debug("Request to save FotoPozo : {}", fotoPozoDTO);
        FotoPozo fotoPozo = fotoPozoMapper.toEntity(fotoPozoDTO);
        fotoPozo = fotoPozoRepository.save(fotoPozo);
        return fotoPozoMapper.toDto(fotoPozo);
    }

    @Override
    public FotoPozoDTO update(FotoPozoDTO fotoPozoDTO) {
        log.debug("Request to update FotoPozo : {}", fotoPozoDTO);
        FotoPozo fotoPozo = fotoPozoMapper.toEntity(fotoPozoDTO);
        fotoPozo = fotoPozoRepository.save(fotoPozo);
        return fotoPozoMapper.toDto(fotoPozo);
    }

    @Override
    public Optional<FotoPozoDTO> partialUpdate(FotoPozoDTO fotoPozoDTO) {
        log.debug("Request to partially update FotoPozo : {}", fotoPozoDTO);

        return fotoPozoRepository
            .findById(fotoPozoDTO.getId())
            .map(existingFotoPozo -> {
                fotoPozoMapper.partialUpdate(existingFotoPozo, fotoPozoDTO);

                return existingFotoPozo;
            })
            .map(fotoPozoRepository::save)
            .map(fotoPozoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FotoPozoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FotoPozos");
        return fotoPozoRepository.findAll(pageable).map(fotoPozoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FotoPozoDTO> findOne(Long id) {
        log.debug("Request to get FotoPozo : {}", id);
        return fotoPozoRepository.findById(id).map(fotoPozoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FotoPozo : {}", id);
        fotoPozoRepository.deleteById(id);
    }
}
