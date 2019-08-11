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

import com.felipejansen.syslene.domain.Cidade;
import com.felipejansen.syslene.domain.*; // for static metamodels
import com.felipejansen.syslene.repository.CidadeRepository;
import com.felipejansen.syslene.service.dto.CidadeCriteria;
import com.felipejansen.syslene.service.dto.CidadeDTO;
import com.felipejansen.syslene.service.mapper.CidadeMapper;

/**
 * Service for executing complex queries for {@link Cidade} entities in the database.
 * The main input is a {@link CidadeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CidadeDTO} or a {@link Page} of {@link CidadeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CidadeQueryService extends QueryService<Cidade> {

    private final Logger log = LoggerFactory.getLogger(CidadeQueryService.class);

    private final CidadeRepository cidadeRepository;

    private final CidadeMapper cidadeMapper;

    public CidadeQueryService(CidadeRepository cidadeRepository, CidadeMapper cidadeMapper) {
        this.cidadeRepository = cidadeRepository;
        this.cidadeMapper = cidadeMapper;
    }

    /**
     * Return a {@link List} of {@link CidadeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CidadeDTO> findByCriteria(CidadeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Cidade> specification = createSpecification(criteria);
        return cidadeMapper.toDto(cidadeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CidadeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CidadeDTO> findByCriteria(CidadeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Cidade> specification = createSpecification(criteria);
        return cidadeRepository.findAll(specification, page)
            .map(cidadeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CidadeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Cidade> specification = createSpecification(criteria);
        return cidadeRepository.count(specification);
    }

    /**
     * Function to convert CidadeCriteria to a {@link Specification}.
     */
    private Specification<Cidade> createSpecification(CidadeCriteria criteria) {
        Specification<Cidade> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Cidade_.id));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Cidade_.descricao));
            }
            if (criteria.getEstado() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstado(), Cidade_.estado));
            }
        }
        return specification;
    }
}
