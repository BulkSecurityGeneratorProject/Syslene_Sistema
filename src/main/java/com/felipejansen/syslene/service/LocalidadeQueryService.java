package com.felipejansen.syslene.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.felipejansen.syslene.domain.Localidade;
import com.felipejansen.syslene.domain.*; // for static metamodels
import com.felipejansen.syslene.repository.LocalidadeRepository;
import com.felipejansen.syslene.service.dto.LocalidadeCriteria;
import com.felipejansen.syslene.service.dto.LocalidadeDTO;
import com.felipejansen.syslene.service.mapper.LocalidadeMapper;

/**
 * Service for executing complex queries for {@link Localidade} entities in the database.
 * The main input is a {@link LocalidadeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LocalidadeDTO} or a {@link Page} of {@link LocalidadeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LocalidadeQueryService extends QueryService<Localidade> {

    private final Logger log = LoggerFactory.getLogger(LocalidadeQueryService.class);

    private final LocalidadeRepository localidadeRepository;

    private final LocalidadeMapper localidadeMapper;

    public LocalidadeQueryService(LocalidadeRepository localidadeRepository, LocalidadeMapper localidadeMapper) {
        this.localidadeRepository = localidadeRepository;
        this.localidadeMapper = localidadeMapper;
    }

    /**
     * Return a {@link List} of {@link LocalidadeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LocalidadeDTO> findByCriteria(LocalidadeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Localidade> specification = createSpecification(criteria);
        return localidadeMapper.toDto(localidadeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LocalidadeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LocalidadeDTO> findByCriteria(LocalidadeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Localidade> specification = createSpecification(criteria);
        return localidadeRepository.findAll(specification, page)
            .map(localidadeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LocalidadeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Localidade> specification = createSpecification(criteria);
        return localidadeRepository.count(specification);
    }

    /**
     * Function to convert LocalidadeCriteria to a {@link Specification}.
     */
    private Specification<Localidade> createSpecification(LocalidadeCriteria criteria) {
        Specification<Localidade> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Localidade_.id));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Localidade_.descricao));
            }
            if (criteria.getAbastecimentoAgua() != null) {
                specification = specification.and(buildSpecification(criteria.getAbastecimentoAgua(), Localidade_.abastecimentoAgua));
            }
            if (criteria.getEsgotoSanitario() != null) {
                specification = specification.and(buildSpecification(criteria.getEsgotoSanitario(), Localidade_.esgotoSanitario));
            }
            if (criteria.getColetaResiduos() != null) {
                specification = specification.and(buildSpecification(criteria.getColetaResiduos(), Localidade_.coletaResiduos));
            }
            if (criteria.getDataAlteracao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataAlteracao(), Localidade_.dataAlteracao));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Localidade_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
