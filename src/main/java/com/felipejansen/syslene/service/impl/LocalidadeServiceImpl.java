package com.felipejansen.syslene.service.impl;

import com.felipejansen.syslene.service.LocalidadeService;
import com.felipejansen.syslene.domain.Localidade;
import com.felipejansen.syslene.repository.LocalidadeRepository;
import com.felipejansen.syslene.service.dto.LocalidadeDTO;
import com.felipejansen.syslene.service.mapper.LocalidadeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.swagger2.mappers.ModelMapper;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Localidade}.
 */
@Service
@Transactional
public class LocalidadeServiceImpl implements LocalidadeService {

    private final Logger log = LoggerFactory.getLogger(LocalidadeServiceImpl.class);

    private final LocalidadeRepository localidadeRepository;

    private final LocalidadeMapper localidadeMapper;

    public LocalidadeServiceImpl(LocalidadeRepository localidadeRepository, LocalidadeMapper localidadeMapper) {
        this.localidadeRepository = localidadeRepository;
        this.localidadeMapper = localidadeMapper;
    }

    /**
     * Save a localidade.
     *
     * @param localidadeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LocalidadeDTO save(LocalidadeDTO localidadeDTO) {
        log.debug("Request to save Localidade : {}", localidadeDTO);
        Localidade localidade = localidadeMapper.toEntity(localidadeDTO);
        localidade = localidadeRepository.save(localidade);
        return localidadeMapper.toDto(localidade);
    }

    /**
     * Get all the localidades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LocalidadeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Localidades");
        return localidadeRepository.findAll(pageable)
            .map(localidadeMapper::toDto);
    }

    /**
     * Get one localidade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LocalidadeDTO> findOne(Long id) {
        log.debug("Request to get Localidade : {}", id);
        return localidadeRepository.findById(id)
            .map(localidadeMapper::toDto);
    }

    /**
     * Delete the localidade by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Localidade : {}", id);
        localidadeRepository.deleteById(id);
    }
}
