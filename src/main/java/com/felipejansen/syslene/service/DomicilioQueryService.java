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

import com.felipejansen.syslene.domain.Domicilio;
import com.felipejansen.syslene.domain.*; // for static metamodels
import com.felipejansen.syslene.repository.DomicilioRepository;
import com.felipejansen.syslene.service.dto.DomicilioCriteria;
import com.felipejansen.syslene.service.dto.DomicilioDTO;
import com.felipejansen.syslene.service.mapper.DomicilioMapper;

/**
 * Service for executing complex queries for {@link Domicilio} entities in the database.
 * The main input is a {@link DomicilioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DomicilioDTO} or a {@link Page} of {@link DomicilioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DomicilioQueryService extends QueryService<Domicilio> {

    private final Logger log = LoggerFactory.getLogger(DomicilioQueryService.class);

    private final DomicilioRepository domicilioRepository;

    private final DomicilioMapper domicilioMapper;

    public DomicilioQueryService(DomicilioRepository domicilioRepository, DomicilioMapper domicilioMapper) {
        this.domicilioRepository = domicilioRepository;
        this.domicilioMapper = domicilioMapper;
    }

    /**
     * Return a {@link List} of {@link DomicilioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DomicilioDTO> findByCriteria(DomicilioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Domicilio> specification = createSpecification(criteria);
        return domicilioMapper.toDto(domicilioRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DomicilioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DomicilioDTO> findByCriteria(DomicilioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Domicilio> specification = createSpecification(criteria);
        return domicilioRepository.findAll(specification, page)
            .map(domicilioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DomicilioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Domicilio> specification = createSpecification(criteria);
        return domicilioRepository.count(specification);
    }

    /**
     * Function to convert DomicilioCriteria to a {@link Specification}.
     */
    private Specification<Domicilio> createSpecification(DomicilioCriteria criteria) {
        Specification<Domicilio> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Domicilio_.id));
            }
            if (criteria.getNomeMorador() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomeMorador(), Domicilio_.nomeMorador));
            }
            if (criteria.getEndereco() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEndereco(), Domicilio_.endereco));
            }
            if (criteria.getLatitude() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLatitude(), Domicilio_.latitude));
            }
            if (criteria.getLongitude() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLongitude(), Domicilio_.longitude));
            }
            if (criteria.getNumeroHabitantes() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumeroHabitantes(), Domicilio_.numeroHabitantes));
            }
            if (criteria.getLigacaoDomiciliarAgua() != null) {
                specification = specification.and(buildSpecification(criteria.getLigacaoDomiciliarAgua(), Domicilio_.ligacaoDomiciliarAgua));
            }
            if (criteria.getPoco() != null) {
                specification = specification.and(buildSpecification(criteria.getPoco(), Domicilio_.poco));
            }
            if (criteria.getCisterna() != null) {
                specification = specification.and(buildSpecification(criteria.getCisterna(), Domicilio_.cisterna));
            }
            if (criteria.getReservatorioElevado() != null) {
                specification = specification.and(buildSpecification(criteria.getReservatorioElevado(), Domicilio_.reservatorioElevado));
            }
            if (criteria.getReservatorioSemiElevado() != null) {
                specification = specification.and(buildSpecification(criteria.getReservatorioSemiElevado(), Domicilio_.reservatorioSemiElevado));
            }
            if (criteria.getConjuntoSanitario() != null) {
                specification = specification.and(buildSpecification(criteria.getConjuntoSanitario(), Domicilio_.conjuntoSanitario));
            }
            if (criteria.getPiaCozinha() != null) {
                specification = specification.and(buildSpecification(criteria.getPiaCozinha(), Domicilio_.piaCozinha));
            }
            if (criteria.getTanqueLavarRoupa() != null) {
                specification = specification.and(buildSpecification(criteria.getTanqueLavarRoupa(), Domicilio_.tanqueLavarRoupa));
            }
            if (criteria.getFiltroDomestico() != null) {
                specification = specification.and(buildSpecification(criteria.getFiltroDomestico(), Domicilio_.filtroDomestico));
            }
            if (criteria.getTanqueSeptico() != null) {
                specification = specification.and(buildSpecification(criteria.getTanqueSeptico(), Domicilio_.tanqueSeptico));
            }
            if (criteria.getSumidouro() != null) {
                specification = specification.and(buildSpecification(criteria.getSumidouro(), Domicilio_.sumidouro));
            }
            if (criteria.getValaInfiltracao() != null) {
                specification = specification.and(buildSpecification(criteria.getValaInfiltracao(), Domicilio_.valaInfiltracao));
            }
            if (criteria.getSistemaReuso() != null) {
                specification = specification.and(buildSpecification(criteria.getSistemaReuso(), Domicilio_.sistemaReuso));
            }
            if (criteria.getLigacaoDomiciliarEsgoto() != null) {
                specification = specification.and(buildSpecification(criteria.getLigacaoDomiciliarEsgoto(), Domicilio_.ligacaoDomiciliarEsgoto));
            }
            if (criteria.getRecipenteResiduosSolidos() != null) {
                specification = specification.and(buildSpecification(criteria.getRecipenteResiduosSolidos(), Domicilio_.recipenteResiduosSolidos));
            }
            if (criteria.getDataCadastro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataCadastro(), Domicilio_.dataCadastro));
            }
            if (criteria.getLevantamentoConcluido() != null) {
                specification = specification.and(buildSpecification(criteria.getLevantamentoConcluido(), Domicilio_.levantamentoConcluido));
            }
            if (criteria.getDataAlteracao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataAlteracao(), Domicilio_.dataAlteracao));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Domicilio_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getLocalidadeId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocalidadeId(),
                    root -> root.join(Domicilio_.localidade, JoinType.LEFT).get(Localidade_.id)));
            }
        }
        return specification;
    }
}
