package com.felipejansen.syslene.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.felipejansen.syslene.domain.Localidade} entity.
 */
public class LocalidadeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 80)
    private String descricao;

    @NotNull
    private Boolean abastecimentoAgua;

    @NotNull
    private Boolean esgotoSanitario;

    @NotNull
    private Boolean coletaResiduos;

    private LocalDate dataAlteracao;


    private Long userId;

    private String userFirstName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isAbastecimentoAgua() {
        return abastecimentoAgua;
    }

    public void setAbastecimentoAgua(Boolean abastecimentoAgua) {
        this.abastecimentoAgua = abastecimentoAgua;
    }

    public Boolean isEsgotoSanitario() {
        return esgotoSanitario;
    }

    public void setEsgotoSanitario(Boolean esgotoSanitario) {
        this.esgotoSanitario = esgotoSanitario;
    }

    public Boolean isColetaResiduos() {
        return coletaResiduos;
    }

    public void setColetaResiduos(Boolean coletaResiduos) {
        this.coletaResiduos = coletaResiduos;
    }

    public LocalDate getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDate dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LocalidadeDTO localidadeDTO = (LocalidadeDTO) o;
        if (localidadeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), localidadeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LocalidadeDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", abastecimentoAgua='" + isAbastecimentoAgua() + "'" +
            ", esgotoSanitario='" + isEsgotoSanitario() + "'" +
            ", coletaResiduos='" + isColetaResiduos() + "'" +
            ", dataAlteracao='" + getDataAlteracao() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserFirstName() + "'" +
            "}";
    }
}
