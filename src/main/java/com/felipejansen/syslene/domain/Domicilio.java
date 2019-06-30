package com.felipejansen.syslene.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Domicilio.
 */
@Entity
@Table(name = "domicilio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Domicilio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 80)
    @Column(name = "nome_morador", length = 80, nullable = false)
    private String nomeMorador;

    @NotNull
    @Size(max = 80)
    @Column(name = "endereco", length = 80, nullable = false)
    private String endereco;

    @NotNull
    @Column(name = "latitude", nullable = false)
    private String latitude;

    @NotNull
    @Column(name = "longitude", nullable = false)
    private String longitude;

    @Column(name = "numero_habitantes", precision = 21, scale = 2)
    private BigDecimal numeroHabitantes;

    @Column(name = "ligacao_domiciliar_agua")
    private Boolean ligacaoDomiciliarAgua;

    @Column(name = "poco")
    private Boolean poco;

    @Column(name = "cisterna")
    private Boolean cisterna;

    @Column(name = "reservatorio_elevado")
    private Boolean reservatorioElevado;

    @Column(name = "reservatorio_semi_elevado")
    private Boolean reservatorioSemiElevado;

    @Column(name = "conjunto_sanitario")
    private Boolean conjuntoSanitario;

    @Column(name = "pia_cozinha")
    private Boolean piaCozinha;

    @Column(name = "tanque_lavar_roupa")
    private Boolean tanqueLavarRoupa;

    @Column(name = "filtro_domestico")
    private Boolean filtroDomestico;

    @Column(name = "tanque_septico")
    private Boolean tanqueSeptico;

    @Column(name = "sumidouro")
    private Boolean sumidouro;

    @Column(name = "vala_infiltracao")
    private Boolean valaInfiltracao;

    @Column(name = "sistema_reuso")
    private Boolean sistemaReuso;

    @Column(name = "ligacao_domiciliar_esgoto")
    private Boolean ligacaoDomiciliarEsgoto;

    @Column(name = "recipente_residuos_solidos")
    private Boolean recipenteResiduosSolidos;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @Column(name = "levantamento_concluido")
    private Boolean levantamentoConcluido;

    @Column(name = "data_alteracao")
    private LocalDate dataAlteracao;

    @ManyToOne
    @JsonIgnoreProperties("domicilios")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("domicilios")
    private Localidade localidade;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeMorador() {
        return nomeMorador;
    }

    public Domicilio nomeMorador(String nomeMorador) {
        this.nomeMorador = nomeMorador;
        return this;
    }

    public void setNomeMorador(String nomeMorador) {
        this.nomeMorador = nomeMorador;
    }

    public String getEndereco() {
        return endereco;
    }

    public Domicilio endereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getLatitude() {
        return latitude;
    }

    public Domicilio latitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public Domicilio longitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getNumeroHabitantes() {
        return numeroHabitantes;
    }

    public Domicilio numeroHabitantes(BigDecimal numeroHabitantes) {
        this.numeroHabitantes = numeroHabitantes;
        return this;
    }

    public void setNumeroHabitantes(BigDecimal numeroHabitantes) {
        this.numeroHabitantes = numeroHabitantes;
    }

    public Boolean isLigacaoDomiciliarAgua() {
        return ligacaoDomiciliarAgua;
    }

    public Domicilio ligacaoDomiciliarAgua(Boolean ligacaoDomiciliarAgua) {
        this.ligacaoDomiciliarAgua = ligacaoDomiciliarAgua;
        return this;
    }

    public void setLigacaoDomiciliarAgua(Boolean ligacaoDomiciliarAgua) {
        this.ligacaoDomiciliarAgua = ligacaoDomiciliarAgua;
    }

    public Boolean isPoco() {
        return poco;
    }

    public Domicilio poco(Boolean poco) {
        this.poco = poco;
        return this;
    }

    public void setPoco(Boolean poco) {
        this.poco = poco;
    }

    public Boolean isCisterna() {
        return cisterna;
    }

    public Domicilio cisterna(Boolean cisterna) {
        this.cisterna = cisterna;
        return this;
    }

    public void setCisterna(Boolean cisterna) {
        this.cisterna = cisterna;
    }

    public Boolean isReservatorioElevado() {
        return reservatorioElevado;
    }

    public Domicilio reservatorioElevado(Boolean reservatorioElevado) {
        this.reservatorioElevado = reservatorioElevado;
        return this;
    }

    public void setReservatorioElevado(Boolean reservatorioElevado) {
        this.reservatorioElevado = reservatorioElevado;
    }

    public Boolean isReservatorioSemiElevado() {
        return reservatorioSemiElevado;
    }

    public Domicilio reservatorioSemiElevado(Boolean reservatorioSemiElevado) {
        this.reservatorioSemiElevado = reservatorioSemiElevado;
        return this;
    }

    public void setReservatorioSemiElevado(Boolean reservatorioSemiElevado) {
        this.reservatorioSemiElevado = reservatorioSemiElevado;
    }

    public Boolean isConjuntoSanitario() {
        return conjuntoSanitario;
    }

    public Domicilio conjuntoSanitario(Boolean conjuntoSanitario) {
        this.conjuntoSanitario = conjuntoSanitario;
        return this;
    }

    public void setConjuntoSanitario(Boolean conjuntoSanitario) {
        this.conjuntoSanitario = conjuntoSanitario;
    }

    public Boolean isPiaCozinha() {
        return piaCozinha;
    }

    public Domicilio piaCozinha(Boolean piaCozinha) {
        this.piaCozinha = piaCozinha;
        return this;
    }

    public void setPiaCozinha(Boolean piaCozinha) {
        this.piaCozinha = piaCozinha;
    }

    public Boolean isTanqueLavarRoupa() {
        return tanqueLavarRoupa;
    }

    public Domicilio tanqueLavarRoupa(Boolean tanqueLavarRoupa) {
        this.tanqueLavarRoupa = tanqueLavarRoupa;
        return this;
    }

    public void setTanqueLavarRoupa(Boolean tanqueLavarRoupa) {
        this.tanqueLavarRoupa = tanqueLavarRoupa;
    }

    public Boolean isFiltroDomestico() {
        return filtroDomestico;
    }

    public Domicilio filtroDomestico(Boolean filtroDomestico) {
        this.filtroDomestico = filtroDomestico;
        return this;
    }

    public void setFiltroDomestico(Boolean filtroDomestico) {
        this.filtroDomestico = filtroDomestico;
    }

    public Boolean isTanqueSeptico() {
        return tanqueSeptico;
    }

    public Domicilio tanqueSeptico(Boolean tanqueSeptico) {
        this.tanqueSeptico = tanqueSeptico;
        return this;
    }

    public void setTanqueSeptico(Boolean tanqueSeptico) {
        this.tanqueSeptico = tanqueSeptico;
    }

    public Boolean isSumidouro() {
        return sumidouro;
    }

    public Domicilio sumidouro(Boolean sumidouro) {
        this.sumidouro = sumidouro;
        return this;
    }

    public void setSumidouro(Boolean sumidouro) {
        this.sumidouro = sumidouro;
    }

    public Boolean isValaInfiltracao() {
        return valaInfiltracao;
    }

    public Domicilio valaInfiltracao(Boolean valaInfiltracao) {
        this.valaInfiltracao = valaInfiltracao;
        return this;
    }

    public void setValaInfiltracao(Boolean valaInfiltracao) {
        this.valaInfiltracao = valaInfiltracao;
    }

    public Boolean isSistemaReuso() {
        return sistemaReuso;
    }

    public Domicilio sistemaReuso(Boolean sistemaReuso) {
        this.sistemaReuso = sistemaReuso;
        return this;
    }

    public void setSistemaReuso(Boolean sistemaReuso) {
        this.sistemaReuso = sistemaReuso;
    }

    public Boolean isLigacaoDomiciliarEsgoto() {
        return ligacaoDomiciliarEsgoto;
    }

    public Domicilio ligacaoDomiciliarEsgoto(Boolean ligacaoDomiciliarEsgoto) {
        this.ligacaoDomiciliarEsgoto = ligacaoDomiciliarEsgoto;
        return this;
    }

    public void setLigacaoDomiciliarEsgoto(Boolean ligacaoDomiciliarEsgoto) {
        this.ligacaoDomiciliarEsgoto = ligacaoDomiciliarEsgoto;
    }

    public Boolean isRecipenteResiduosSolidos() {
        return recipenteResiduosSolidos;
    }

    public Domicilio recipenteResiduosSolidos(Boolean recipenteResiduosSolidos) {
        this.recipenteResiduosSolidos = recipenteResiduosSolidos;
        return this;
    }

    public void setRecipenteResiduosSolidos(Boolean recipenteResiduosSolidos) {
        this.recipenteResiduosSolidos = recipenteResiduosSolidos;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public Domicilio dataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
        return this;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Boolean isLevantamentoConcluido() {
        return levantamentoConcluido;
    }

    public Domicilio levantamentoConcluido(Boolean levantamentoConcluido) {
        this.levantamentoConcluido = levantamentoConcluido;
        return this;
    }

    public void setLevantamentoConcluido(Boolean levantamentoConcluido) {
        this.levantamentoConcluido = levantamentoConcluido;
    }

    public LocalDate getDataAlteracao() {
        return dataAlteracao;
    }

    public Domicilio dataAlteracao(LocalDate dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
        return this;
    }

    public void setDataAlteracao(LocalDate dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public User getUser() {
        return user;
    }

    public Domicilio user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Localidade getLocalidade() {
        return localidade;
    }

    public Domicilio localidade(Localidade localidade) {
        this.localidade = localidade;
        return this;
    }

    public void setLocalidade(Localidade localidade) {
        this.localidade = localidade;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Domicilio)) {
            return false;
        }
        return id != null && id.equals(((Domicilio) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Domicilio{" +
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
            "}";
    }
}
