package com.felipejansen.syslene.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.felipejansen.syslene.domain.Localidade} entity. This class is used
 * in {@link com.felipejansen.syslene.web.rest.LocalidadeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /localidades?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LocalidadeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter descricao;

    private BooleanFilter abastecimentoAgua;

    private BooleanFilter esgotoSanitario;

    private BooleanFilter coletaResiduos;

    private LocalDateFilter dataAlteracao;

    private LongFilter userId;

    public LocalidadeCriteria(){
    }

    public LocalidadeCriteria(LocalidadeCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.abastecimentoAgua = other.abastecimentoAgua == null ? null : other.abastecimentoAgua.copy();
        this.esgotoSanitario = other.esgotoSanitario == null ? null : other.esgotoSanitario.copy();
        this.coletaResiduos = other.coletaResiduos == null ? null : other.coletaResiduos.copy();
        this.dataAlteracao = other.dataAlteracao == null ? null : other.dataAlteracao.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public LocalidadeCriteria copy() {
        return new LocalidadeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescricao() {
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
    }

    public BooleanFilter getAbastecimentoAgua() {
        return abastecimentoAgua;
    }

    public void setAbastecimentoAgua(BooleanFilter abastecimentoAgua) {
        this.abastecimentoAgua = abastecimentoAgua;
    }

    public BooleanFilter getEsgotoSanitario() {
        return esgotoSanitario;
    }

    public void setEsgotoSanitario(BooleanFilter esgotoSanitario) {
        this.esgotoSanitario = esgotoSanitario;
    }

    public BooleanFilter getColetaResiduos() {
        return coletaResiduos;
    }

    public void setColetaResiduos(BooleanFilter coletaResiduos) {
        this.coletaResiduos = coletaResiduos;
    }

    public LocalDateFilter getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateFilter dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LocalidadeCriteria that = (LocalidadeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(abastecimentoAgua, that.abastecimentoAgua) &&
            Objects.equals(esgotoSanitario, that.esgotoSanitario) &&
            Objects.equals(coletaResiduos, that.coletaResiduos) &&
            Objects.equals(dataAlteracao, that.dataAlteracao) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        descricao,
        abastecimentoAgua,
        esgotoSanitario,
        coletaResiduos,
        dataAlteracao,
        userId
        );
    }

    @Override
    public String toString() {
        return "LocalidadeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (abastecimentoAgua != null ? "abastecimentoAgua=" + abastecimentoAgua + ", " : "") +
                (esgotoSanitario != null ? "esgotoSanitario=" + esgotoSanitario + ", " : "") +
                (coletaResiduos != null ? "coletaResiduos=" + coletaResiduos + ", " : "") +
                (dataAlteracao != null ? "dataAlteracao=" + dataAlteracao + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
