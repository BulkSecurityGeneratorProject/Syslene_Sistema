package com.felipejansen.syslene.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Localidade.
 */
@Entity
@Table(name = "localidade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Localidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 80)
    @Column(name = "descricao", length = 80, nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "abastecimento_agua", nullable = false)
    private Boolean abastecimentoAgua;

    @NotNull
    @Column(name = "esgoto_sanitario", nullable = false)
    private Boolean esgotoSanitario;

    @NotNull
    @Column(name = "coleta_residuos", nullable = false)
    private Boolean coletaResiduos;

    @Column(name = "data_alteracao")
    private LocalDate dataAlteracao;

    @ManyToOne
    @JsonIgnoreProperties("localidades")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Localidade descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isAbastecimentoAgua() {
        return abastecimentoAgua;
    }

    public Localidade abastecimentoAgua(Boolean abastecimentoAgua) {
        this.abastecimentoAgua = abastecimentoAgua;
        return this;
    }

    public void setAbastecimentoAgua(Boolean abastecimentoAgua) {
        this.abastecimentoAgua = abastecimentoAgua;
    }

    public Boolean isEsgotoSanitario() {
        return esgotoSanitario;
    }

    public Localidade esgotoSanitario(Boolean esgotoSanitario) {
        this.esgotoSanitario = esgotoSanitario;
        return this;
    }

    public void setEsgotoSanitario(Boolean esgotoSanitario) {
        this.esgotoSanitario = esgotoSanitario;
    }

    public Boolean isColetaResiduos() {
        return coletaResiduos;
    }

    public Localidade coletaResiduos(Boolean coletaResiduos) {
        this.coletaResiduos = coletaResiduos;
        return this;
    }

    public void setColetaResiduos(Boolean coletaResiduos) {
        this.coletaResiduos = coletaResiduos;
    }

    public LocalDate getDataAlteracao() {
        return dataAlteracao;
    }

    public Localidade dataAlteracao(LocalDate dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
        return this;
    }

    public void setDataAlteracao(LocalDate dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public User getUser() {
        return user;
    }

    public Localidade user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Localidade)) {
            return false;
        }
        return id != null && id.equals(((Localidade) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Localidade{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", abastecimentoAgua='" + isAbastecimentoAgua() + "'" +
            ", esgotoSanitario='" + isEsgotoSanitario() + "'" +
            ", coletaResiduos='" + isColetaResiduos() + "'" +
            ", dataAlteracao='" + getDataAlteracao() + "'" +
            "}";
    }
}
