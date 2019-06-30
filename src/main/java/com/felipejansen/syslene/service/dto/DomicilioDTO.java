package com.felipejansen.syslene.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.felipejansen.syslene.domain.Domicilio} entity.
 */
public class DomicilioDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 80)
    private String nomeMorador;

    @NotNull
    @Size(max = 80)
    private String endereco;

    @NotNull
    private String latitude;

    @NotNull
    private String longitude;

    private BigDecimal numeroHabitantes;

    private Boolean ligacaoDomiciliarAgua;

    private Boolean poco;

    private Boolean cisterna;

    private Boolean reservatorioElevado;

    private Boolean reservatorioSemiElevado;

    private Boolean conjuntoSanitario;

    private Boolean piaCozinha;

    private Boolean tanqueLavarRoupa;

    private Boolean filtroDomestico;

    private Boolean tanqueSeptico;

    private Boolean sumidouro;

    private Boolean valaInfiltracao;

    private Boolean sistemaReuso;

    private Boolean ligacaoDomiciliarEsgoto;

    private Boolean recipenteResiduosSolidos;

    private LocalDate dataCadastro;

    private Boolean levantamentoConcluido;

    private LocalDate dataAlteracao;


    private Long userId;

    private String userFirstName;

    private Long localidadeId;

    private String localidadeDescricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeMorador() {
        return nomeMorador;
    }

    public void setNomeMorador(String nomeMorador) {
        this.nomeMorador = nomeMorador;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getNumeroHabitantes() {
        return numeroHabitantes;
    }

    public void setNumeroHabitantes(BigDecimal numeroHabitantes) {
        this.numeroHabitantes = numeroHabitantes;
    }

    public Boolean isLigacaoDomiciliarAgua() {
        return ligacaoDomiciliarAgua;
    }

    public void setLigacaoDomiciliarAgua(Boolean ligacaoDomiciliarAgua) {
        this.ligacaoDomiciliarAgua = ligacaoDomiciliarAgua;
    }

    public Boolean isPoco() {
        return poco;
    }

    public void setPoco(Boolean poco) {
        this.poco = poco;
    }

    public Boolean isCisterna() {
        return cisterna;
    }

    public void setCisterna(Boolean cisterna) {
        this.cisterna = cisterna;
    }

    public Boolean isReservatorioElevado() {
        return reservatorioElevado;
    }

    public void setReservatorioElevado(Boolean reservatorioElevado) {
        this.reservatorioElevado = reservatorioElevado;
    }

    public Boolean isReservatorioSemiElevado() {
        return reservatorioSemiElevado;
    }

    public void setReservatorioSemiElevado(Boolean reservatorioSemiElevado) {
        this.reservatorioSemiElevado = reservatorioSemiElevado;
    }

    public Boolean isConjuntoSanitario() {
        return conjuntoSanitario;
    }

    public void setConjuntoSanitario(Boolean conjuntoSanitario) {
        this.conjuntoSanitario = conjuntoSanitario;
    }

    public Boolean isPiaCozinha() {
        return piaCozinha;
    }

    public void setPiaCozinha(Boolean piaCozinha) {
        this.piaCozinha = piaCozinha;
    }

    public Boolean isTanqueLavarRoupa() {
        return tanqueLavarRoupa;
    }

    public void setTanqueLavarRoupa(Boolean tanqueLavarRoupa) {
        this.tanqueLavarRoupa = tanqueLavarRoupa;
    }

    public Boolean isFiltroDomestico() {
        return filtroDomestico;
    }

    public void setFiltroDomestico(Boolean filtroDomestico) {
        this.filtroDomestico = filtroDomestico;
    }

    public Boolean isTanqueSeptico() {
        return tanqueSeptico;
    }

    public void setTanqueSeptico(Boolean tanqueSeptico) {
        this.tanqueSeptico = tanqueSeptico;
    }

    public Boolean isSumidouro() {
        return sumidouro;
    }

    public void setSumidouro(Boolean sumidouro) {
        this.sumidouro = sumidouro;
    }

    public Boolean isValaInfiltracao() {
        return valaInfiltracao;
    }

    public void setValaInfiltracao(Boolean valaInfiltracao) {
        this.valaInfiltracao = valaInfiltracao;
    }

    public Boolean isSistemaReuso() {
        return sistemaReuso;
    }

    public void setSistemaReuso(Boolean sistemaReuso) {
        this.sistemaReuso = sistemaReuso;
    }

    public Boolean isLigacaoDomiciliarEsgoto() {
        return ligacaoDomiciliarEsgoto;
    }

    public void setLigacaoDomiciliarEsgoto(Boolean ligacaoDomiciliarEsgoto) {
        this.ligacaoDomiciliarEsgoto = ligacaoDomiciliarEsgoto;
    }

    public Boolean isRecipenteResiduosSolidos() {
        return recipenteResiduosSolidos;
    }

    public void setRecipenteResiduosSolidos(Boolean recipenteResiduosSolidos) {
        this.recipenteResiduosSolidos = recipenteResiduosSolidos;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Boolean isLevantamentoConcluido() {
        return levantamentoConcluido;
    }

    public void setLevantamentoConcluido(Boolean levantamentoConcluido) {
        this.levantamentoConcluido = levantamentoConcluido;
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

    public Long getLocalidadeId() {
        return localidadeId;
    }

    public void setLocalidadeId(Long localidadeId) {
        this.localidadeId = localidadeId;
    }

    public String getLocalidadeDescricao() {
        return localidadeDescricao;
    }

    public void setLocalidadeDescricao(String localidadeDescricao) {
        this.localidadeDescricao = localidadeDescricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DomicilioDTO domicilioDTO = (DomicilioDTO) o;
        if (domicilioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), domicilioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DomicilioDTO{" +
            "id=" + getId() +
            ", nomeMorador='" + getNomeMorador() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", numeroHabitantes=" + getNumeroHabitantes() +
            ", ligacaoDomiciliarAgua='" + isLigacaoDomiciliarAgua() + "'" +
            ", poco='" + isPoco() + "'" +
            ", cisterna='" + isCisterna() + "'" +
            ", reservatorioElevado='" + isReservatorioElevado() + "'" +
            ", reservatorioSemiElevado='" + isReservatorioSemiElevado() + "'" +
            ", conjuntoSanitario='" + isConjuntoSanitario() + "'" +
            ", piaCozinha='" + isPiaCozinha() + "'" +
            ", tanqueLavarRoupa='" + isTanqueLavarRoupa() + "'" +
            ", filtroDomestico='" + isFiltroDomestico() + "'" +
            ", tanqueSeptico='" + isTanqueSeptico() + "'" +
            ", sumidouro='" + isSumidouro() + "'" +
            ", valaInfiltracao='" + isValaInfiltracao() + "'" +
            ", sistemaReuso='" + isSistemaReuso() + "'" +
            ", ligacaoDomiciliarEsgoto='" + isLigacaoDomiciliarEsgoto() + "'" +
            ", recipenteResiduosSolidos='" + isRecipenteResiduosSolidos() + "'" +
            ", dataCadastro='" + getDataCadastro() + "'" +
            ", levantamentoConcluido='" + isLevantamentoConcluido() + "'" +
            ", dataAlteracao='" + getDataAlteracao() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserFirstName() + "'" +
            ", localidade=" + getLocalidadeId() +
            ", localidade='" + getLocalidadeDescricao() + "'" +
            "}";
    }
}
