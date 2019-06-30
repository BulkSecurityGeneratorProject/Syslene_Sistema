package com.felipejansen.syslene.web.rest;

import com.felipejansen.syslene.service.DomicilioService;
import com.felipejansen.syslene.web.rest.errors.BadRequestAlertException;
import com.felipejansen.syslene.service.dto.DomicilioDTO;
import com.felipejansen.syslene.service.dto.DomicilioCriteria;
import com.felipejansen.syslene.service.DomicilioQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.felipejansen.syslene.domain.Domicilio}.
 */
@RestController
@RequestMapping("/api")
public class DomicilioResource {

    private final Logger log = LoggerFactory.getLogger(DomicilioResource.class);

    private static final String ENTITY_NAME = "domicilio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DomicilioService domicilioService;

    private final DomicilioQueryService domicilioQueryService;

    public DomicilioResource(DomicilioService domicilioService, DomicilioQueryService domicilioQueryService) {
        this.domicilioService = domicilioService;
        this.domicilioQueryService = domicilioQueryService;
    }

    /**
     * {@code POST  /domicilios} : Create a new domicilio.
     *
     * @param domicilioDTO the domicilioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new domicilioDTO, or with status {@code 400 (Bad Request)} if the domicilio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/domicilios")
    public ResponseEntity<DomicilioDTO> createDomicilio(@Valid @RequestBody DomicilioDTO domicilioDTO) throws URISyntaxException {
        log.debug("REST request to save Domicilio : {}", domicilioDTO);
        if (domicilioDTO.getId() != null) {
            throw new BadRequestAlertException("A new domicilio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DomicilioDTO result = domicilioService.save(domicilioDTO);
        return ResponseEntity.created(new URI("/api/domicilios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /domicilios} : Updates an existing domicilio.
     *
     * @param domicilioDTO the domicilioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated domicilioDTO,
     * or with status {@code 400 (Bad Request)} if the domicilioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the domicilioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/domicilios")
    public ResponseEntity<DomicilioDTO> updateDomicilio(@Valid @RequestBody DomicilioDTO domicilioDTO) throws URISyntaxException {
        log.debug("REST request to update Domicilio : {}", domicilioDTO);
        if (domicilioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DomicilioDTO result = domicilioService.save(domicilioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, domicilioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /domicilios} : get all the domicilios.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of domicilios in body.
     */
    @GetMapping("/domicilios")
    public ResponseEntity<List<DomicilioDTO>> getAllDomicilios(DomicilioCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Domicilios by criteria: {}", criteria);
        Page<DomicilioDTO> page = domicilioQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /domicilios/count} : count all the domicilios.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/domicilios/count")
    public ResponseEntity<Long> countDomicilios(DomicilioCriteria criteria) {
        log.debug("REST request to count Domicilios by criteria: {}", criteria);
        return ResponseEntity.ok().body(domicilioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /domicilios/:id} : get the "id" domicilio.
     *
     * @param id the id of the domicilioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the domicilioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/domicilios/{id}")
    public ResponseEntity<DomicilioDTO> getDomicilio(@PathVariable Long id) {
        log.debug("REST request to get Domicilio : {}", id);
        Optional<DomicilioDTO> domicilioDTO = domicilioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(domicilioDTO);
    }

    /**
     * {@code DELETE  /domicilios/:id} : delete the "id" domicilio.
     *
     * @param id the id of the domicilioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/domicilios/{id}")
    public ResponseEntity<Void> deleteDomicilio(@PathVariable Long id) {
        log.debug("REST request to delete Domicilio : {}", id);
        domicilioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
