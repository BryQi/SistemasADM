package com.municipio.sistemasadm.service.impl;

import com.municipio.sistemasadm.domain.Despliegueinfraestructuradispersion;
import com.municipio.sistemasadm.repository.DespliegueinfraestructuradispersionRepository;
import com.municipio.sistemasadm.service.DespliegueinfraestructuradispersionService;
import com.municipio.sistemasadm.service.dto.DespliegueinfraestructuradispersionDTO;
import com.municipio.sistemasadm.service.mapper.DespliegueinfraestructuradispersionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Despliegueinfraestructuradispersion}.
 */
@Service
@Transactional
public class DespliegueinfraestructuradispersionServiceImpl implements DespliegueinfraestructuradispersionService {

    private final Logger log = LoggerFactory.getLogger(DespliegueinfraestructuradispersionServiceImpl.class);

    private final DespliegueinfraestructuradispersionRepository despliegueinfraestructuradispersionRepository;

    private final DespliegueinfraestructuradispersionMapper despliegueinfraestructuradispersionMapper;

    public DespliegueinfraestructuradispersionServiceImpl(
        DespliegueinfraestructuradispersionRepository despliegueinfraestructuradispersionRepository,
        DespliegueinfraestructuradispersionMapper despliegueinfraestructuradispersionMapper
    ) {
        this.despliegueinfraestructuradispersionRepository = despliegueinfraestructuradispersionRepository;
        this.despliegueinfraestructuradispersionMapper = despliegueinfraestructuradispersionMapper;
    }

    @Override
    public DespliegueinfraestructuradispersionDTO save(DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO) {
        log.debug("Request to save Despliegueinfraestructuradispersion : {}", despliegueinfraestructuradispersionDTO);
        Despliegueinfraestructuradispersion despliegueinfraestructuradispersion = despliegueinfraestructuradispersionMapper.toEntity(
            despliegueinfraestructuradispersionDTO
        );
        despliegueinfraestructuradispersion = despliegueinfraestructuradispersionRepository.save(despliegueinfraestructuradispersion);
        return despliegueinfraestructuradispersionMapper.toDto(despliegueinfraestructuradispersion);
    }

    @Override
    public DespliegueinfraestructuradispersionDTO update(DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO) {
        log.debug("Request to update Despliegueinfraestructuradispersion : {}", despliegueinfraestructuradispersionDTO);
        Despliegueinfraestructuradispersion despliegueinfraestructuradispersion = despliegueinfraestructuradispersionMapper.toEntity(
            despliegueinfraestructuradispersionDTO
        );
        despliegueinfraestructuradispersion = despliegueinfraestructuradispersionRepository.save(despliegueinfraestructuradispersion);
        return despliegueinfraestructuradispersionMapper.toDto(despliegueinfraestructuradispersion);
    }

    @Override
    public Optional<DespliegueinfraestructuradispersionDTO> partialUpdate(
        DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO
    ) {
        log.debug("Request to partially update Despliegueinfraestructuradispersion : {}", despliegueinfraestructuradispersionDTO);

        return despliegueinfraestructuradispersionRepository
            .findById(despliegueinfraestructuradispersionDTO.getId())
            .map(existingDespliegueinfraestructuradispersion -> {
                despliegueinfraestructuradispersionMapper.partialUpdate(
                    existingDespliegueinfraestructuradispersion,
                    despliegueinfraestructuradispersionDTO
                );

                return existingDespliegueinfraestructuradispersion;
            })
            .map(despliegueinfraestructuradispersionRepository::save)
            .map(despliegueinfraestructuradispersionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DespliegueinfraestructuradispersionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Despliegueinfraestructuradispersions");
        return despliegueinfraestructuradispersionRepository.findAll(pageable).map(despliegueinfraestructuradispersionMapper::toDto);
    }

    public Page<DespliegueinfraestructuradispersionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return despliegueinfraestructuradispersionRepository
            .findAllWithEagerRelationships(pageable)
            .map(despliegueinfraestructuradispersionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DespliegueinfraestructuradispersionDTO> findOne(Long id) {
        log.debug("Request to get Despliegueinfraestructuradispersion : {}", id);
        return despliegueinfraestructuradispersionRepository
            .findOneWithEagerRelationships(id)
            .map(despliegueinfraestructuradispersionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Despliegueinfraestructuradispersion : {}", id);
        despliegueinfraestructuradispersionRepository.deleteById(id);
    }
}
