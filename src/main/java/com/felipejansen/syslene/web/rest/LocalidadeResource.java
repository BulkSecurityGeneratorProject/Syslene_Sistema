package com.felipejansen.syslene.web.rest;

import com.felipejansen.syslene.domain.Localidade;
import com.felipejansen.syslene.repository.LocalidadeRepository;
import com.felipejansen.syslene.service.LocalidadeService;
import com.felipejansen.syslene.web.rest.errors.BadRequestAlertException;
import com.felipejansen.syslene.service.dto.LocalidadeDTO;
import com.felipejansen.syslene.service.dto.LocalidadeCriteria;
import com.felipejansen.syslene.service.LocalidadeQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * REST controller for managing {@link com.felipejansen.syslene.domain.Localidade}.
 */
@RestController
@RequestMapping("/api")
public class LocalidadeResource {

    private final Logger log = LoggerFactory.getLogger(LocalidadeResource.class);

    private static final String ENTITY_NAME = "localidade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocalidadeService localidadeService;

    @Autowired
    private LocalidadeRepository localidadeRepository;

    private final LocalidadeQueryService localidadeQueryService;

    public LocalidadeResource(LocalidadeService localidadeService, LocalidadeQueryService localidadeQueryService) {
        this.localidadeService = localidadeService;
        this.localidadeQueryService = localidadeQueryService;
    }

    /**
     * {@code POST  /localidades} : Create a new localidade.
     *
     * @param localidadeDTO the localidadeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new localidadeDTO, or with status {@code 400 (Bad Request)} if the localidade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/localidades")
    public ResponseEntity<LocalidadeDTO> createLocalidade(@Valid @RequestBody LocalidadeDTO localidadeDTO) throws URISyntaxException {
        log.debug("REST request to save Localidade : {}", localidadeDTO);
        if (localidadeDTO.getId() != null) {
            throw new BadRequestAlertException("A new localidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocalidadeDTO result = localidadeService.save(localidadeDTO);
        return ResponseEntity.created(new URI("/api/localidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /localidades} : Updates an existing localidade.
     *
     * @param localidadeDTO the localidadeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated localidadeDTO,
     * or with status {@code 400 (Bad Request)} if the localidadeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the localidadeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/localidades")
    public ResponseEntity<LocalidadeDTO> updateLocalidade(@Valid @RequestBody LocalidadeDTO localidadeDTO) throws URISyntaxException {
        log.debug("REST request to update Localidade : {}", localidadeDTO);
        if (localidadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LocalidadeDTO result = localidadeService.save(localidadeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, localidadeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /localidades} : get all the localidades.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of localidades in body.
     */
    @GetMapping("/localidades")
    public ResponseEntity<List<LocalidadeDTO>> getAllLocalidades(LocalidadeCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Localidades by criteria: {}", criteria);
        Page<LocalidadeDTO> page = localidadeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/localidades-nopage")
    public ResponseEntity<List<LocalidadeDTO>> getAllLocalidadesNoPage(LocalidadeCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Localidades by criteria: {}", criteria);
        List<LocalidadeDTO> list = localidadeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().headers(null).body(list);
    }

    /**
    * {@code GET  /localidades/count} : count all the localidades.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/localidades/count")
    public ResponseEntity<Long> countLocalidades(LocalidadeCriteria criteria) {
        log.debug("REST request to count Localidades by criteria: {}", criteria);
        return ResponseEntity.ok().body(localidadeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /localidades/:id} : get the "id" localidade.
     *
     * @param id the id of the localidadeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the localidadeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/localidades/{id}")
    public ResponseEntity<LocalidadeDTO> getLocalidade(@PathVariable Long id) {
        log.debug("REST request to get Localidade : {}", id);
        Optional<LocalidadeDTO> localidadeDTO = localidadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(localidadeDTO);
    }

    /**
     * {@code DELETE  /localidades/:id} : delete the "id" localidade.
     *
     * @param id the id of the localidadeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/localidades/{id}")
    public ResponseEntity<Void> deleteLocalidade(@PathVariable Long id) {
        log.debug("REST request to delete Localidade : {}", id);
        localidadeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
