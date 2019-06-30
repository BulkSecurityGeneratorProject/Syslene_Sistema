package com.felipejansen.syslene.service;

import com.felipejansen.syslene.service.dto.LocalidadeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.felipejansen.syslene.domain.Localidade}.
 */
public interface LocalidadeService {

    /**
     * Save a localidade.
     *
     * @param localidadeDTO the entity to save.
     * @return the persisted entity.
     */
    LocalidadeDTO save(LocalidadeDTO localidadeDTO);

    /**
     * Get all the localidades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LocalidadeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" localidade.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LocalidadeDTO> findOne(Long id);

    /**
     * Delete the "id" localidade.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
