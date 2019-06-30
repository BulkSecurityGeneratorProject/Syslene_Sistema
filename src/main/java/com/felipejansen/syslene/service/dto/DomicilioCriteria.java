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
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.felipejansen.syslene.domain.Domicilio} entity. This class is used
 * in {@link com.felipejansen.syslene.web.rest.DomicilioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /domicilios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DomicilioCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nomeMorador;

    private StringFilter endereco;

    private StringFilter latitude;

    private StringFilter longitude;

    private BigDecimalFilter numeroHabitantes;

    private BooleanFilter ligacaoDomiciliarAgua;

    private BooleanFilter poco;

    private BooleanFilter cisterna;

    private BooleanFilter reservatorioElevado;

    private BooleanFilter reservatorioSemiElevado;

    private BooleanFilter conjuntoSanitario;

    private BooleanFilter piaCozinha;

    private BooleanFilter tanqueLavarRoupa;

    private BooleanFilter filtroDomestico;

    private BooleanFilter tanqueSeptico;

    private BooleanFilter sumidouro;

    private BooleanFilter valaInfiltracao;

    private BooleanFilter sistemaReuso;

    private BooleanFilter ligacaoDomiciliarEsgoto;

    private BooleanFilter recipenteResiduosSolidos;

    private LocalDateFilter dataCadastro;

    private BooleanFilter levantamentoConcluido;

    private LocalDateFilter dataAlteracao;

    private LongFilter userId;

    private LongFilter localidadeId;

    public DomicilioCriteria(){
    }

    public DomicilioCriteria(DomicilioCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nomeMorador = other.nomeMorador == null ? null : other.nomeMorador.copy();
        this.endereco = other.endereco == null ? null : other.endereco.copy();
        this.latitude = other.latitude == null ? null : other.latitude.copy();
        this.longitude = other.longitude == null ? null : other.longitude.copy();
        this.numeroHabitantes = other.numeroHabitantes == null ? null : other.numeroHabitantes.copy();
        this.ligacaoDomiciliarAgua = other.ligacaoDomiciliarAgua == null ? null : other.ligacaoDomiciliarAgua.copy();
        this.poco = other.poco == null ? null : other.poco.copy();
        this.cisterna = other.cisterna == null ? null : other.cisterna.copy();
        this.reservatorioElevado = other.reservatorioElevado == null ? null : other.reservatorioElevado.copy();
        this.reservatorioSemiElevado = other.reservatorioSemiElevado == null ? null : other.reservatorioSemiElevado.copy();
        this.conjuntoSanitario = other.conjuntoSanitario == null ? null : other.conjuntoSanitario.copy();
        this.piaCozinha = other.piaCozinha == null ? null : other.piaCozinha.copy();
        this.tanqueLavarRoupa = other.tanqueLavarRoupa == null ? null : other.tanqueLavarRoupa.copy();
        this.filtroDomestico = other.filtroDomestico == null ? null : other.filtroDomestico.copy();
        this.tanqueSeptico = other.tanqueSeptico == null ? null : other.tanqueSeptico.copy();
        this.sumidouro = other.sumidouro == null ? null : other.sumidouro.copy();
        this.valaInfiltracao = other.valaInfiltracao == null ? null : other.valaInfiltracao.copy();
        this.sistemaReuso = other.sistemaReuso == null ? null : other.sistemaReuso.copy();
        this.ligacaoDomiciliarEsgoto = other.ligacaoDomiciliarEsgoto == null ? null : other.ligacaoDomiciliarEsgoto.copy();
        this.recipenteResiduosSolidos = other.recipenteResiduosSolidos == null ? null : other.recipenteResiduosSolidos.copy();
        this.dataCadastro = other.dataCadastro == null ? null : other.dataCadastro.copy();
        this.levantamentoConcluido = other.levantamentoConcluido == null ? null : other.levantamentoConcluido.copy();
        this.dataAlteracao = other.dataAlteracao == null ? null : other.dataAlteracao.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.localidadeId = other.localidadeId == null ? null : other.localidadeId.copy();
    }

    @Override
    public DomicilioCriteria copy() {
        return new DomicilioCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNomeMorador() {
        return nomeMorador;
    }

    public void setNomeMorador(StringFilter nomeMorador) {
        this.nomeMorador = nomeMorador;
    }

    public StringFilter getEndereco() {
        return endereco;
    }

    public void setEndereco(StringFilter endereco) {
        this.endereco = endereco;
    }

    public StringFilter getLatitude() {
        return latitude;
    }

    public void setLatitude(StringFilter latitude) {
        this.latitude = latitude;
    }

    public StringFilter getLongitude() {
        return longitude;
    }

    public void setLongitude(StringFilter longitude) {
        this.longitude = longitude;
    }

    public BigDecimalFilter getNumeroHabitantes() {
        return numeroHabitantes;
    }

    public void setNumeroHabitantes(BigDecimalFilter numeroHabitantes) {
        this.numeroHabitantes = numeroHabitantes;
    }

    public BooleanFilter getLigacaoDomiciliarAgua() {
        return ligacaoDomiciliarAgua;
    }

    public void setLigacaoDomiciliarAgua(BooleanFilter ligacaoDomiciliarAgua) {
        this.ligacaoDomiciliarAgua = ligacaoDomiciliarAgua;
    }

    public BooleanFilter getPoco() {
        return poco;
    }

    public void setPoco(BooleanFilter poco) {
        this.poco = poco;
    }

    public BooleanFilter getCisterna() {
        return cisterna;
    }

    public void setCisterna(BooleanFilter cisterna) {
        this.cisterna = cisterna;
    }

    public BooleanFilter getReservatorioElevado() {
        return reservatorioElevado;
    }

    public void setReservatorioElevado(BooleanFilter reservatorioElevado) {
        this.reservatorioElevado = reservatorioElevado;
    }

    public BooleanFilter getReservatorioSemiElevado() {
        return reservatorioSemiElevado;
    }

    public void setReservatorioSemiElevado(BooleanFilter reservatorioSemiElevado) {
        this.reservatorioSemiElevado = reservatorioSemiElevado;
    }

    public BooleanFilter getConjuntoSanitario() {
        return conjuntoSanitario;
    }

    public void setConjuntoSanitario(BooleanFilter conjuntoSanitario) {
        this.conjuntoSanitario = conjuntoSanitario;
    }

    public BooleanFilter getPiaCozinha() {
        return piaCozinha;
    }

    public void setPiaCozinha(BooleanFilter piaCozinha) {
        this.piaCozinha = piaCozinha;
    }

    public BooleanFilter getTanqueLavarRoupa() {
        return tanqueLavarRoupa;
    }

    public void setTanqueLavarRoupa(BooleanFilter tanqueLavarRoupa) {
        this.tanqueLavarRoupa = tanqueLavarRoupa;
    }

    public BooleanFilter getFiltroDomestico() {
        return filtroDomestico;
    }

    public void setFiltroDomestico(BooleanFilter filtroDomestico) {
        this.filtroDomestico = filtroDomestico;
    }

    public BooleanFilter getTanqueSeptico() {
        return tanqueSeptico;
    }

    public void setTanqueSeptico(BooleanFilter tanqueSeptico) {
        this.tanqueSeptico = tanqueSeptico;
    }

    public BooleanFilter getSumidouro() {
        return sumidouro;
    }

    public void setSumidouro(BooleanFilter sumidouro) {
        this.sumidouro = sumidouro;
    }

    public BooleanFilter getValaInfiltracao() {
        return valaInfiltracao;
    }

    public void setValaInfiltracao(BooleanFilter valaInfiltracao) {
        this.valaInfiltracao = valaInfiltracao;
    }

    public BooleanFilter getSistemaReuso() {
        return sistemaReuso;
    }

    public void setSistemaReuso(BooleanFilter sistemaReuso) {
        this.sistemaReuso = sistemaReuso;
    }

    public BooleanFilter getLigacaoDomiciliarEsgoto() {
        return ligacaoDomiciliarEsgoto;
    }

    public void setLigacaoDomiciliarEsgoto(BooleanFilter ligacaoDomiciliarEsgoto) {
        this.ligacaoDomiciliarEsgoto = ligacaoDomiciliarEsgoto;
    }

    public BooleanFilter getRecipenteResiduosSolidos() {
        return recipenteResiduosSolidos;
    }

    public void setRecipenteResiduosSolidos(BooleanFilter recipenteResiduosSolidos) {
        this.recipenteResiduosSolidos = recipenteResiduosSolidos;
    }

    public LocalDateFilter getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateFilter dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public BooleanFilter getLevantamentoConcluido() {
        return levantamentoConcluido;
    }

    public void setLevantamentoConcluido(BooleanFilter levantamentoConcluido) {
        this.levantamentoConcluido = levantamentoConcluido;
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

    public LongFilter getLocalidadeId() {
        return localidadeId;
    }

    public void setLocalidadeId(LongFilter localidadeId) {
        this.localidadeId = localidadeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DomicilioCriteria that = (DomicilioCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nomeMorador, that.nomeMorador) &&
            Objects.equals(endereco, that.endereco) &&
            Objects.equals(latitude, that.latitude) &&
            Objects.equals(longitude, that.longitude) &&
            Objects.equals(numeroHabitantes, that.numeroHabitantes) &&
            Objects.equals(ligacaoDomiciliarAgua, that.ligacaoDomiciliarAgua) &&
            Objects.equals(poco, that.poco) &&
            Objects.equals(cisterna, that.cisterna) &&
            Objects.equals(reservatorioElevado, that.reservatorioElevado) &&
            Objects.equals(reservatorioSemiElevado, that.reservatorioSemiElevado) &&
            Objects.equals(conjuntoSanitario, that.conjuntoSanitario) &&
            Objects.equals(piaCozinha, that.piaCozinha) &&
            Objects.equals(tanqueLavarRoupa, that.tanqueLavarRoupa) &&
            Objects.equals(filtroDomestico, that.filtroDomestico) &&
            Objects.equals(tanqueSeptico, that.tanqueSeptico) &&
            Objects.equals(sumidouro, that.sumidouro) &&
            Objects.equals(valaInfiltracao, that.valaInfiltracao) &&
            Objects.equals(sistemaReuso, that.sistemaReuso) &&
            Objects.equals(ligacaoDomiciliarEsgoto, that.ligacaoDomiciliarEsgoto) &&
            Objects.equals(recipenteResiduosSolidos, that.recipenteResiduosSolidos) &&
            Objects.equals(dataCadastro, that.dataCadastro) &&
            Objects.equals(levantamentoConcluido, that.levantamentoConcluido) &&
            Objects.equals(dataAlteracao, that.dataAlteracao) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(localidadeId, that.localidadeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nomeMorador,
        endereco,
        latitude,
        longitude,
        numeroHabitantes,
        ligacaoDomiciliarAgua,
        poco,
        cisterna,
        reservatorioElevado,
        reservatorioSemiElevado,
        conjuntoSanitario,
        piaCozinha,
        tanqueLavarRoupa,
        filtroDomestico,
        tanqueSeptico,
        sumidouro,
        valaInfiltracao,
        sistemaReuso,
        ligacaoDomiciliarEsgoto,
        recipenteResiduosSolidos,
        dataCadastro,
        levantamentoConcluido,
        dataAlteracao,
        userId,
        localidadeId
        );
    }

    @Override
    public String toString() {
        return "DomicilioCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nomeMorador != null ? "nomeMorador=" + nomeMorador + ", " : "") +
                (endereco != null ? "endereco=" + endereco + ", " : "") +
                (latitude != null ? "latitude=" + latitude + ", " : "") +
                (longitude != null ? "longitude=" + longitude + ", " : "") +
                (numeroHabitantes != null ? "numeroHabitantes=" + numeroHabitantes + ", " : "") +
                (ligacaoDomiciliarAgua != null ? "ligacaoDomiciliarAgua=" + ligacaoDomiciliarAgua + ", " : "") +
                (poco != null ? "poco=" + poco + ", " : "") +
                (cisterna != null ? "cisterna=" + cisterna + ", " : "") +
                (reservatorioElevado != null ? "reservatorioElevado=" + reservatorioElevado + ", " : "") +
                (reservatorioSemiElevado != null ? "reservatorioSemiElevado=" + reservatorioSemiElevado + ", " : "") +
                (conjuntoSanitario != null ? "conjuntoSanitario=" + conjuntoSanitario + ", " : "") +
                (piaCozinha != null ? "piaCozinha=" + piaCozinha + ", " : "") +
                (tanqueLavarRoupa != null ? "tanqueLavarRoupa=" + tanqueLavarRoupa + ", " : "") +
                (filtroDomestico != null ? "filtroDomestico=" + filtroDomestico + ", " : "") +
                (tanqueSeptico != null ? "tanqueSeptico=" + tanqueSeptico + ", " : "") +
                (sumidouro != null ? "sumidouro=" + sumidouro + ", " : "") +
                (valaInfiltracao != null ? "valaInfiltracao=" + valaInfiltracao + ", " : "") +
                (sistemaReuso != null ? "sistemaReuso=" + sistemaReuso + ", " : "") +
                (ligacaoDomiciliarEsgoto != null ? "ligacaoDomiciliarEsgoto=" + ligacaoDomiciliarEsgoto + ", " : "") +
                (recipenteResiduosSolidos != null ? "recipenteResiduosSolidos=" + recipenteResiduosSolidos + ", " : "") +
                (dataCadastro != null ? "dataCadastro=" + dataCadastro + ", " : "") +
                (levantamentoConcluido != null ? "levantamentoConcluido=" + levantamentoConcluido + ", " : "") +
                (dataAlteracao != null ? "dataAlteracao=" + dataAlteracao + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (localidadeId != null ? "localidadeId=" + localidadeId + ", " : "") +
            "}";
    }

}
