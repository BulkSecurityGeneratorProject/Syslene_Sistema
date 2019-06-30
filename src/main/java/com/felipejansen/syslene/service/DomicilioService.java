package com.felipejansen.syslene.service;

import com.felipejansen.syslene.service.dto.DomicilioDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.felipejansen.syslene.domain.Domicilio}.
 */
public interface DomicilioService {

    /**
     * Save a domicilio.
     *
     * @param domicilioDTO the entity to save.
     * @return the persisted entity.
     */
    DomicilioDTO save(DomicilioDTO domicilioDTO);

    /**
     * Get all the domicilios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DomicilioDTO> findAll(Pageable pageable);


    /**
     * Get the "id" domicilio.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DomicilioDTO> findOne(Long id);

    /**
     * Delete the "id" domicilio.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
