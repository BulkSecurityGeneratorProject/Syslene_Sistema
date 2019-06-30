package com.felipejansen.syslene.web.rest;

import com.felipejansen.syslene.SysleneApp;
import com.felipejansen.syslene.domain.Domicilio;
import com.felipejansen.syslene.domain.User;
import com.felipejansen.syslene.domain.Localidade;
import com.felipejansen.syslene.repository.DomicilioRepository;
import com.felipejansen.syslene.service.DomicilioService;
import com.felipejansen.syslene.service.dto.DomicilioDTO;
import com.felipejansen.syslene.service.mapper.DomicilioMapper;
import com.felipejansen.syslene.web.rest.errors.ExceptionTranslator;
import com.felipejansen.syslene.service.dto.DomicilioCriteria;
import com.felipejansen.syslene.service.DomicilioQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.felipejansen.syslene.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link DomicilioResource} REST controller.
 */
@SpringBootTest(classes = SysleneApp.class)
public class DomicilioResourceIT {

    private static final String DEFAULT_NOME_MORADOR = "AAAAAAAAAA";
    private static final String UPDATED_NOME_MORADOR = "BBBBBBBBBB";

    private static final String DEFAULT_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO = "BBBBBBBBBB";

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_NUMERO_HABITANTES = new BigDecimal(1);
    private static final BigDecimal UPDATED_NUMERO_HABITANTES = new BigDecimal(2);

    private static final Boolean DEFAULT_LIGACAO_DOMICILIAR_AGUA = false;
    private static final Boolean UPDATED_LIGACAO_DOMICILIAR_AGUA = true;

    private static final Boolean DEFAULT_POCO = false;
    private static final Boolean UPDATED_POCO = true;

    private static final Boolean DEFAULT_CISTERNA = false;
    private static final Boolean UPDATED_CISTERNA = true;

    private static final Boolean DEFAULT_RESERVATORIO_ELEVADO = false;
    private static final Boolean UPDATED_RESERVATORIO_ELEVADO = true;

    private static final Boolean DEFAULT_RESERVATORIO_SEMI_ELEVADO = false;
    private static final Boolean UPDATED_RESERVATORIO_SEMI_ELEVADO = true;

    private static final Boolean DEFAULT_CONJUNTO_SANITARIO = false;
    private static final Boolean UPDATED_CONJUNTO_SANITARIO = true;

    private static final Boolean DEFAULT_PIA_COZINHA = false;
    private static final Boolean UPDATED_PIA_COZINHA = true;

    private static final Boolean DEFAULT_TANQUE_LAVAR_ROUPA = false;
    private static final Boolean UPDATED_TANQUE_LAVAR_ROUPA = true;

    private static final Boolean DEFAULT_FILTRO_DOMESTICO = false;
    private static final Boolean UPDATED_FILTRO_DOMESTICO = true;

    private static final Boolean DEFAULT_TANQUE_SEPTICO = false;
    private static final Boolean UPDATED_TANQUE_SEPTICO = true;

    private static final Boolean DEFAULT_SUMIDOURO = false;
    private static final Boolean UPDATED_SUMIDOURO = true;

    private static final Boolean DEFAULT_VALA_INFILTRACAO = false;
    private static final Boolean UPDATED_VALA_INFILTRACAO = true;

    private static final Boolean DEFAULT_SISTEMA_REUSO = false;
    private static final Boolean UPDATED_SISTEMA_REUSO = true;

    private static final Boolean DEFAULT_LIGACAO_DOMICILIAR_ESGOTO = false;
    private static final Boolean UPDATED_LIGACAO_DOMICILIAR_ESGOTO = true;

    private static final Boolean DEFAULT_RECIPENTE_RESIDUOS_SOLIDOS = false;
    private static final Boolean UPDATED_RECIPENTE_RESIDUOS_SOLIDOS = true;

    private static final LocalDate DEFAULT_DATA_CADASTRO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_CADASTRO = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_LEVANTAMENTO_CONCLUIDO = false;
    private static final Boolean UPDATED_LEVANTAMENTO_CONCLUIDO = true;

    private static final LocalDate DEFAULT_DATA_ALTERACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ALTERACAO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DomicilioRepository domicilioRepository;

    @Autowired
    private DomicilioMapper domicilioMapper;

    @Autowired
    private DomicilioService domicilioService;

    @Autowired
    private DomicilioQueryService domicilioQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restDomicilioMockMvc;

    private Domicilio domicilio;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DomicilioResource domicilioResource = new DomicilioResource(domicilioService, domicilioQueryService);
        this.restDomicilioMockMvc = MockMvcBuilders.standaloneSetup(domicilioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Domicilio createEntity(EntityManager em) {
        Domicilio domicilio = new Domicilio()
            .nomeMorador(DEFAULT_NOME_MORADOR)
            .endereco(DEFAULT_ENDERECO)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .numeroHabitantes(DEFAULT_NUMERO_HABITANTES)
            .ligacaoDomiciliarAgua(DEFAULT_LIGACAO_DOMICILIAR_AGUA)
            .poco(DEFAULT_POCO)
            .cisterna(DEFAULT_CISTERNA)
            .reservatorioElevado(DEFAULT_RESERVATORIO_ELEVADO)
            .reservatorioSemiElevado(DEFAULT_RESERVATORIO_SEMI_ELEVADO)
            .conjuntoSanitario(DEFAULT_CONJUNTO_SANITARIO)
            .piaCozinha(DEFAULT_PIA_COZINHA)
            .tanqueLavarRoupa(DEFAULT_TANQUE_LAVAR_ROUPA)
            .filtroDomestico(DEFAULT_FILTRO_DOMESTICO)
            .tanqueSeptico(DEFAULT_TANQUE_SEPTICO)
            .sumidouro(DEFAULT_SUMIDOURO)
            .valaInfiltracao(DEFAULT_VALA_INFILTRACAO)
            .sistemaReuso(DEFAULT_SISTEMA_REUSO)
            .ligacaoDomiciliarEsgoto(DEFAULT_LIGACAO_DOMICILIAR_ESGOTO)
            .recipenteResiduosSolidos(DEFAULT_RECIPENTE_RESIDUOS_SOLIDOS)
            .dataCadastro(DEFAULT_DATA_CADASTRO)
            .levantamentoConcluido(DEFAULT_LEVANTAMENTO_CONCLUIDO)
            .dataAlteracao(DEFAULT_DATA_ALTERACAO);
        return domicilio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Domicilio createUpdatedEntity(EntityManager em) {
        Domicilio domicilio = new Domicilio()
            .nomeMorador(UPDATED_NOME_MORADOR)
            .endereco(UPDATED_ENDERECO)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .numeroHabitantes(UPDATED_NUMERO_HABITANTES)
            .ligacaoDomiciliarAgua(UPDATED_LIGACAO_DOMICILIAR_AGUA)
            .poco(UPDATED_POCO)
            .cisterna(UPDATED_CISTERNA)
            .reservatorioElevado(UPDATED_RESERVATORIO_ELEVADO)
            .reservatorioSemiElevado(UPDATED_RESERVATORIO_SEMI_ELEVADO)
            .conjuntoSanitario(UPDATED_CONJUNTO_SANITARIO)
            .piaCozinha(UPDATED_PIA_COZINHA)
            .tanqueLavarRoupa(UPDATED_TANQUE_LAVAR_ROUPA)
            .filtroDomestico(UPDATED_FILTRO_DOMESTICO)
            .tanqueSeptico(UPDATED_TANQUE_SEPTICO)
            .sumidouro(UPDATED_SUMIDOURO)
            .valaInfiltracao(UPDATED_VALA_INFILTRACAO)
            .sistemaReuso(UPDATED_SISTEMA_REUSO)
            .ligacaoDomiciliarEsgoto(UPDATED_LIGACAO_DOMICILIAR_ESGOTO)
            .recipenteResiduosSolidos(UPDATED_RECIPENTE_RESIDUOS_SOLIDOS)
            .dataCadastro(UPDATED_DATA_CADASTRO)
            .levantamentoConcluido(UPDATED_LEVANTAMENTO_CONCLUIDO)
            .dataAlteracao(UPDATED_DATA_ALTERACAO);
        return domicilio;
    }

    @BeforeEach
    public void initTest() {
        domicilio = createEntity(em);
    }

    @Test
    @Transactional
    public void createDomicilio() throws Exception {
        int databaseSizeBeforeCreate = domicilioRepository.findAll().size();

        // Create the Domicilio
        DomicilioDTO domicilioDTO = domicilioMapper.toDto(domicilio);
        restDomicilioMockMvc.perform(post("/api/domicilios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(domicilioDTO)))
            .andExpect(status().isCreated());

        // Validate the Domicilio in the database
        List<Domicilio> domicilioList = domicilioRepository.findAll();
        assertThat(domicilioList).hasSize(databaseSizeBeforeCreate + 1);
        Domicilio testDomicilio = domicilioList.get(domicilioList.size() - 1);
        assertThat(testDomicilio.getNomeMorador()).isEqualTo(DEFAULT_NOME_MORADOR);
        assertThat(testDomicilio.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testDomicilio.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testDomicilio.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testDomicilio.getNumeroHabitantes()).isEqualTo(DEFAULT_NUMERO_HABITANTES);
        assertThat(testDomicilio.isLigacaoDomiciliarAgua()).isEqualTo(DEFAULT_LIGACAO_DOMICILIAR_AGUA);
        assertThat(testDomicilio.isPoco()).isEqualTo(DEFAULT_POCO);
        assertThat(testDomicilio.isCisterna()).isEqualTo(DEFAULT_CISTERNA);
        assertThat(testDomicilio.isReservatorioElevado()).isEqualTo(DEFAULT_RESERVATORIO_ELEVADO);
        assertThat(testDomicilio.isReservatorioSemiElevado()).isEqualTo(DEFAULT_RESERVATORIO_SEMI_ELEVADO);
        assertThat(testDomicilio.isConjuntoSanitario()).isEqualTo(DEFAULT_CONJUNTO_SANITARIO);
        assertThat(testDomicilio.isPiaCozinha()).isEqualTo(DEFAULT_PIA_COZINHA);
        assertThat(testDomicilio.isTanqueLavarRoupa()).isEqualTo(DEFAULT_TANQUE_LAVAR_ROUPA);
        assertThat(testDomicilio.isFiltroDomestico()).isEqualTo(DEFAULT_FILTRO_DOMESTICO);
        assertThat(testDomicilio.isTanqueSeptico()).isEqualTo(DEFAULT_TANQUE_SEPTICO);
        assertThat(testDomicilio.isSumidouro()).isEqualTo(DEFAULT_SUMIDOURO);
        assertThat(testDomicilio.isValaInfiltracao()).isEqualTo(DEFAULT_VALA_INFILTRACAO);
        assertThat(testDomicilio.isSistemaReuso()).isEqualTo(DEFAULT_SISTEMA_REUSO);
        assertThat(testDomicilio.isLigacaoDomiciliarEsgoto()).isEqualTo(DEFAULT_LIGACAO_DOMICILIAR_ESGOTO);
        assertThat(testDomicilio.isRecipenteResiduosSolidos()).isEqualTo(DEFAULT_RECIPENTE_RESIDUOS_SOLIDOS);
        assertThat(testDomicilio.getDataCadastro()).isEqualTo(DEFAULT_DATA_CADASTRO);
        assertThat(testDomicilio.isLevantamentoConcluido()).isEqualTo(DEFAULT_LEVANTAMENTO_CONCLUIDO);
        assertThat(testDomicilio.getDataAlteracao()).isEqualTo(DEFAULT_DATA_ALTERACAO);
    }

    @Test
    @Transactional
    public void createDomicilioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = domicilioRepository.findAll().size();

        // Create the Domicilio with an existing ID
        domicilio.setId(1L);
        DomicilioDTO domicilioDTO = domicilioMapper.toDto(domicilio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDomicilioMockMvc.perform(post("/api/domicilios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(domicilioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Domicilio in the database
        List<Domicilio> domicilioList = domicilioRepository.findAll();
        assertThat(domicilioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeMoradorIsRequired() throws Exception {
        int databaseSizeBeforeTest = domicilioRepository.findAll().size();
        // set the field null
        domicilio.setNomeMorador(null);

        // Create the Domicilio, which fails.
        DomicilioDTO domicilioDTO = domicilioMapper.toDto(domicilio);

        restDomicilioMockMvc.perform(post("/api/domicilios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(domicilioDTO)))
            .andExpect(status().isBadRequest());

        List<Domicilio> domicilioList = domicilioRepository.findAll();
        assertThat(domicilioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnderecoIsRequired() throws Exception {
        int databaseSizeBeforeTest = domicilioRepository.findAll().size();
        // set the field null
        domicilio.setEndereco(null);

        // Create the Domicilio, which fails.
        DomicilioDTO domicilioDTO = domicilioMapper.toDto(domicilio);

        restDomicilioMockMvc.perform(post("/api/domicilios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(domicilioDTO)))
            .andExpect(status().isBadRequest());

        List<Domicilio> domicilioList = domicilioRepository.findAll();
        assertThat(domicilioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = domicilioRepository.findAll().size();
        // set the field null
        domicilio.setLatitude(null);

        // Create the Domicilio, which fails.
        DomicilioDTO domicilioDTO = domicilioMapper.toDto(domicilio);

        restDomicilioMockMvc.perform(post("/api/domicilios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(domicilioDTO)))
            .andExpect(status().isBadRequest());

        List<Domicilio> domicilioList = domicilioRepository.findAll();
        assertThat(domicilioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = domicilioRepository.findAll().size();
        // set the field null
        domicilio.setLongitude(null);

        // Create the Domicilio, which fails.
        DomicilioDTO domicilioDTO = domicilioMapper.toDto(domicilio);

        restDomicilioMockMvc.perform(post("/api/domicilios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(domicilioDTO)))
            .andExpect(status().isBadRequest());

        List<Domicilio> domicilioList = domicilioRepository.findAll();
        assertThat(domicilioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDomicilios() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList
        restDomicilioMockMvc.perform(get("/api/domicilios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(domicilio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeMorador").value(hasItem(DEFAULT_NOME_MORADOR.toString())))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.toString())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.toString())))
            .andExpect(jsonPath("$.[*].numeroHabitantes").value(hasItem(DEFAULT_NUMERO_HABITANTES.intValue())))
            .andExpect(jsonPath("$.[*].ligacaoDomiciliarAgua").value(hasItem(DEFAULT_LIGACAO_DOMICILIAR_AGUA.booleanValue())))
            .andExpect(jsonPath("$.[*].poco").value(hasItem(DEFAULT_POCO.booleanValue())))
            .andExpect(jsonPath("$.[*].cisterna").value(hasItem(DEFAULT_CISTERNA.booleanValue())))
            .andExpect(jsonPath("$.[*].reservatorioElevado").value(hasItem(DEFAULT_RESERVATORIO_ELEVADO.booleanValue())))
            .andExpect(jsonPath("$.[*].reservatorioSemiElevado").value(hasItem(DEFAULT_RESERVATORIO_SEMI_ELEVADO.booleanValue())))
            .andExpect(jsonPath("$.[*].conjuntoSanitario").value(hasItem(DEFAULT_CONJUNTO_SANITARIO.booleanValue())))
            .andExpect(jsonPath("$.[*].piaCozinha").value(hasItem(DEFAULT_PIA_COZINHA.booleanValue())))
            .andExpect(jsonPath("$.[*].tanqueLavarRoupa").value(hasItem(DEFAULT_TANQUE_LAVAR_ROUPA.booleanValue())))
            .andExpect(jsonPath("$.[*].filtroDomestico").value(hasItem(DEFAULT_FILTRO_DOMESTICO.booleanValue())))
            .andExpect(jsonPath("$.[*].tanqueSeptico").value(hasItem(DEFAULT_TANQUE_SEPTICO.booleanValue())))
            .andExpect(jsonPath("$.[*].sumidouro").value(hasItem(DEFAULT_SUMIDOURO.booleanValue())))
            .andExpect(jsonPath("$.[*].valaInfiltracao").value(hasItem(DEFAULT_VALA_INFILTRACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].sistemaReuso").value(hasItem(DEFAULT_SISTEMA_REUSO.booleanValue())))
            .andExpect(jsonPath("$.[*].ligacaoDomiciliarEsgoto").value(hasItem(DEFAULT_LIGACAO_DOMICILIAR_ESGOTO.booleanValue())))
            .andExpect(jsonPath("$.[*].recipenteResiduosSolidos").value(hasItem(DEFAULT_RECIPENTE_RESIDUOS_SOLIDOS.booleanValue())))
            .andExpect(jsonPath("$.[*].dataCadastro").value(hasItem(DEFAULT_DATA_CADASTRO.toString())))
            .andExpect(jsonPath("$.[*].levantamentoConcluido").value(hasItem(DEFAULT_LEVANTAMENTO_CONCLUIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].dataAlteracao").value(hasItem(DEFAULT_DATA_ALTERACAO.toString())));
    }
    
    @Test
    @Transactional
    public void getDomicilio() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get the domicilio
        restDomicilioMockMvc.perform(get("/api/domicilios/{id}", domicilio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(domicilio.getId().intValue()))
            .andExpect(jsonPath("$.nomeMorador").value(DEFAULT_NOME_MORADOR.toString()))
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.toString()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.toString()))
            .andExpect(jsonPath("$.numeroHabitantes").value(DEFAULT_NUMERO_HABITANTES.intValue()))
            .andExpect(jsonPath("$.ligacaoDomiciliarAgua").value(DEFAULT_LIGACAO_DOMICILIAR_AGUA.booleanValue()))
            .andExpect(jsonPath("$.poco").value(DEFAULT_POCO.booleanValue()))
            .andExpect(jsonPath("$.cisterna").value(DEFAULT_CISTERNA.booleanValue()))
            .andExpect(jsonPath("$.reservatorioElevado").value(DEFAULT_RESERVATORIO_ELEVADO.booleanValue()))
            .andExpect(jsonPath("$.reservatorioSemiElevado").value(DEFAULT_RESERVATORIO_SEMI_ELEVADO.booleanValue()))
            .andExpect(jsonPath("$.conjuntoSanitario").value(DEFAULT_CONJUNTO_SANITARIO.booleanValue()))
            .andExpect(jsonPath("$.piaCozinha").value(DEFAULT_PIA_COZINHA.booleanValue()))
            .andExpect(jsonPath("$.tanqueLavarRoupa").value(DEFAULT_TANQUE_LAVAR_ROUPA.booleanValue()))
            .andExpect(jsonPath("$.filtroDomestico").value(DEFAULT_FILTRO_DOMESTICO.booleanValue()))
            .andExpect(jsonPath("$.tanqueSeptico").value(DEFAULT_TANQUE_SEPTICO.booleanValue()))
            .andExpect(jsonPath("$.sumidouro").value(DEFAULT_SUMIDOURO.booleanValue()))
            .andExpect(jsonPath("$.valaInfiltracao").value(DEFAULT_VALA_INFILTRACAO.booleanValue()))
            .andExpect(jsonPath("$.sistemaReuso").value(DEFAULT_SISTEMA_REUSO.booleanValue()))
            .andExpect(jsonPath("$.ligacaoDomiciliarEsgoto").value(DEFAULT_LIGACAO_DOMICILIAR_ESGOTO.booleanValue()))
            .andExpect(jsonPath("$.recipenteResiduosSolidos").value(DEFAULT_RECIPENTE_RESIDUOS_SOLIDOS.booleanValue()))
            .andExpect(jsonPath("$.dataCadastro").value(DEFAULT_DATA_CADASTRO.toString()))
            .andExpect(jsonPath("$.levantamentoConcluido").value(DEFAULT_LEVANTAMENTO_CONCLUIDO.booleanValue()))
            .andExpect(jsonPath("$.dataAlteracao").value(DEFAULT_DATA_ALTERACAO.toString()));
    }

    @Test
    @Transactional
    public void getAllDomiciliosByNomeMoradorIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where nomeMorador equals to DEFAULT_NOME_MORADOR
        defaultDomicilioShouldBeFound("nomeMorador.equals=" + DEFAULT_NOME_MORADOR);

        // Get all the domicilioList where nomeMorador equals to UPDATED_NOME_MORADOR
        defaultDomicilioShouldNotBeFound("nomeMorador.equals=" + UPDATED_NOME_MORADOR);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByNomeMoradorIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where nomeMorador in DEFAULT_NOME_MORADOR or UPDATED_NOME_MORADOR
        defaultDomicilioShouldBeFound("nomeMorador.in=" + DEFAULT_NOME_MORADOR + "," + UPDATED_NOME_MORADOR);

        // Get all the domicilioList where nomeMorador equals to UPDATED_NOME_MORADOR
        defaultDomicilioShouldNotBeFound("nomeMorador.in=" + UPDATED_NOME_MORADOR);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByNomeMoradorIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where nomeMorador is not null
        defaultDomicilioShouldBeFound("nomeMorador.specified=true");

        // Get all the domicilioList where nomeMorador is null
        defaultDomicilioShouldNotBeFound("nomeMorador.specified=false");
    }

    @Test
    @Transactional
    public void getAllDomiciliosByEnderecoIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where endereco equals to DEFAULT_ENDERECO
        defaultDomicilioShouldBeFound("endereco.equals=" + DEFAULT_ENDERECO);

        // Get all the domicilioList where endereco equals to UPDATED_ENDERECO
        defaultDomicilioShouldNotBeFound("endereco.equals=" + UPDATED_ENDERECO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByEnderecoIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where endereco in DEFAULT_ENDERECO or UPDATED_ENDERECO
        defaultDomicilioShouldBeFound("endereco.in=" + DEFAULT_ENDERECO + "," + UPDATED_ENDERECO);

        // Get all the domicilioList where endereco equals to UPDATED_ENDERECO
        defaultDomicilioShouldNotBeFound("endereco.in=" + UPDATED_ENDERECO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByEnderecoIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where endereco is not null
        defaultDomicilioShouldBeFound("endereco.specified=true");

        // Get all the domicilioList where endereco is null
        defaultDomicilioShouldNotBeFound("endereco.specified=false");
    }

    @Test
    @Transactional
    public void getAllDomiciliosByLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where latitude equals to DEFAULT_LATITUDE
        defaultDomicilioShouldBeFound("latitude.equals=" + DEFAULT_LATITUDE);

        // Get all the domicilioList where latitude equals to UPDATED_LATITUDE
        defaultDomicilioShouldNotBeFound("latitude.equals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where latitude in DEFAULT_LATITUDE or UPDATED_LATITUDE
        defaultDomicilioShouldBeFound("latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE);

        // Get all the domicilioList where latitude equals to UPDATED_LATITUDE
        defaultDomicilioShouldNotBeFound("latitude.in=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where latitude is not null
        defaultDomicilioShouldBeFound("latitude.specified=true");

        // Get all the domicilioList where latitude is null
        defaultDomicilioShouldNotBeFound("latitude.specified=false");
    }

    @Test
    @Transactional
    public void getAllDomiciliosByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where longitude equals to DEFAULT_LONGITUDE
        defaultDomicilioShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the domicilioList where longitude equals to UPDATED_LONGITUDE
        defaultDomicilioShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultDomicilioShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the domicilioList where longitude equals to UPDATED_LONGITUDE
        defaultDomicilioShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where longitude is not null
        defaultDomicilioShouldBeFound("longitude.specified=true");

        // Get all the domicilioList where longitude is null
        defaultDomicilioShouldNotBeFound("longitude.specified=false");
    }

    @Test
    @Transactional
    public void getAllDomiciliosByNumeroHabitantesIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where numeroHabitantes equals to DEFAULT_NUMERO_HABITANTES
        defaultDomicilioShouldBeFound("numeroHabitantes.equals=" + DEFAULT_NUMERO_HABITANTES);

        // Get all the domicilioList where numeroHabitantes equals to UPDATED_NUMERO_HABITANTES
        defaultDomicilioShouldNotBeFound("numeroHabitantes.equals=" + UPDATED_NUMERO_HABITANTES);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByNumeroHabitantesIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where numeroHabitantes in DEFAULT_NUMERO_HABITANTES or UPDATED_NUMERO_HABITANTES
        defaultDomicilioShouldBeFound("numeroHabitantes.in=" + DEFAULT_NUMERO_HABITANTES + "," + UPDATED_NUMERO_HABITANTES);

        // Get all the domicilioList where numeroHabitantes equals to UPDATED_NUMERO_HABITANTES
        defaultDomicilioShouldNotBeFound("numeroHabitantes.in=" + UPDATED_NUMERO_HABITANTES);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByNumeroHabitantesIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where numeroHabitantes is not null
        defaultDomicilioShouldBeFound("numeroHabitantes.specified=true");

        // Get all the domicilioList where numeroHabitantes is null
        defaultDomicilioShouldNotBeFound("numeroHabitantes.specified=false");
    }

    @Test
    @Transactional
    public void getAllDomiciliosByLigacaoDomiciliarAguaIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where ligacaoDomiciliarAgua equals to DEFAULT_LIGACAO_DOMICILIAR_AGUA
        defaultDomicilioShouldBeFound("ligacaoDomiciliarAgua.equals=" + DEFAULT_LIGACAO_DOMICILIAR_AGUA);

        // Get all the domicilioList where ligacaoDomiciliarAgua equals to UPDATED_LIGACAO_DOMICILIAR_AGUA
        defaultDomicilioShouldNotBeFound("ligacaoDomiciliarAgua.equals=" + UPDATED_LIGACAO_DOMICILIAR_AGUA);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByLigacaoDomiciliarAguaIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where ligacaoDomiciliarAgua in DEFAULT_LIGACAO_DOMICILIAR_AGUA or UPDATED_LIGACAO_DOMICILIAR_AGUA
        defaultDomicilioShouldBeFound("ligacaoDomiciliarAgua.in=" + DEFAULT_LIGACAO_DOMICILIAR_AGUA + "," + UPDATED_LIGACAO_DOMICILIAR_AGUA);

        // Get all the domicilioList where ligacaoDomiciliarAgua equals to UPDATED_LIGACAO_DOMICILIAR_AGUA
        defaultDomicilioShouldNotBeFound("ligacaoDomiciliarAgua.in=" + UPDATED_LIGACAO_DOMICILIAR_AGUA);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByLigacaoDomiciliarAguaIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where ligacaoDomiciliarAgua is not null
        defaultDomicilioShouldBeFound("ligacaoDomiciliarAgua.specified=true");

        // Get all the domicilioList where ligacaoDomiciliarAgua is null
        defaultDomicilioShouldNotBeFound("ligacaoDomiciliarAgua.specified=false");
    }

    @Test
    @Transactional
    public void getAllDomiciliosByPocoIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where poco equals to DEFAULT_POCO
        defaultDomicilioShouldBeFound("poco.equals=" + DEFAULT_POCO);

        // Get all the domicilioList where poco equals to UPDATED_POCO
        defaultDomicilioShouldNotBeFound("poco.equals=" + UPDATED_POCO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByPocoIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where poco in DEFAULT_POCO or UPDATED_POCO
        defaultDomicilioShouldBeFound("poco.in=" + DEFAULT_POCO + "," + UPDATED_POCO);

        // Get all the domicilioList where poco equals to UPDATED_POCO
        defaultDomicilioShouldNotBeFound("poco.in=" + UPDATED_POCO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByPocoIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where poco is not null
        defaultDomicilioShouldBeFound("poco.specified=true");

        // Get all the domicilioList where poco is null
        defaultDomicilioShouldNotBeFound("poco.specified=false");
    }

    @Test
    @Transactional
    public void getAllDomiciliosByCisternaIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where cisterna equals to DEFAULT_CISTERNA
        defaultDomicilioShouldBeFound("cisterna.equals=" + DEFAULT_CISTERNA);

        // Get all the domicilioList where cisterna equals to UPDATED_CISTERNA
        defaultDomicilioShouldNotBeFound("cisterna.equals=" + UPDATED_CISTERNA);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByCisternaIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where cisterna in DEFAULT_CISTERNA or UPDATED_CISTERNA
        defaultDomicilioShouldBeFound("cisterna.in=" + DEFAULT_CISTERNA + "," + UPDATED_CISTERNA);

        // Get all the domicilioList where cisterna equals to UPDATED_CISTERNA
        defaultDomicilioShouldNotBeFound("cisterna.in=" + UPDATED_CISTERNA);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByCisternaIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where cisterna is not null
        defaultDomicilioShouldBeFound("cisterna.specified=true");

        // Get all the domicilioList where cisterna is null
        defaultDomicilioShouldNotBeFound("cisterna.specified=false");
    }

    @Test
    @Transactional
    public void getAllDomiciliosByReservatorioElevadoIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where reservatorioElevado equals to DEFAULT_RESERVATORIO_ELEVADO
        defaultDomicilioShouldBeFound("reservatorioElevado.equals=" + DEFAULT_RESERVATORIO_ELEVADO);

        // Get all the domicilioList where reservatorioElevado equals to UPDATED_RESERVATORIO_ELEVADO
        defaultDomicilioShouldNotBeFound("reservatorioElevado.equals=" + UPDATED_RESERVATORIO_ELEVADO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByReservatorioElevadoIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where reservatorioElevado in DEFAULT_RESERVATORIO_ELEVADO or UPDATED_RESERVATORIO_ELEVADO
        defaultDomicilioShouldBeFound("reservatorioElevado.in=" + DEFAULT_RESERVATORIO_ELEVADO + "," + UPDATED_RESERVATORIO_ELEVADO);

        // Get all the domicilioList where reservatorioElevado equals to UPDATED_RESERVATORIO_ELEVADO
        defaultDomicilioShouldNotBeFound("reservatorioElevado.in=" + UPDATED_RESERVATORIO_ELEVADO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByReservatorioElevadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where reservatorioElevado is not null
        defaultDomicilioShouldBeFound("reservatorioElevado.specified=true");

        // Get all the domicilioList where reservatorioElevado is null
        defaultDomicilioShouldNotBeFound("reservatorioElevado.specified=false");
    }

    @Test
    @Transactional
    public void getAllDomiciliosByReservatorioSemiElevadoIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where reservatorioSemiElevado equals to DEFAULT_RESERVATORIO_SEMI_ELEVADO
        defaultDomicilioShouldBeFound("reservatorioSemiElevado.equals=" + DEFAULT_RESERVATORIO_SEMI_ELEVADO);

        // Get all the domicilioList where reservatorioSemiElevado equals to UPDATED_RESERVATORIO_SEMI_ELEVADO
        defaultDomicilioShouldNotBeFound("reservatorioSemiElevado.equals=" + UPDATED_RESERVATORIO_SEMI_ELEVADO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByReservatorioSemiElevadoIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where reservatorioSemiElevado in DEFAULT_RESERVATORIO_SEMI_ELEVADO or UPDATED_RESERVATORIO_SEMI_ELEVADO
        defaultDomicilioShouldBeFound("reservatorioSemiElevado.in=" + DEFAULT_RESERVATORIO_SEMI_ELEVADO + "," + UPDATED_RESERVATORIO_SEMI_ELEVADO);

        // Get all the domicilioList where reservatorioSemiElevado equals to UPDATED_RESERVATORIO_SEMI_ELEVADO
        defaultDomicilioShouldNotBeFound("reservatorioSemiElevado.in=" + UPDATED_RESERVATORIO_SEMI_ELEVADO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByReservatorioSemiElevadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where reservatorioSemiElevado is not null
        defaultDomicilioShouldBeFound("reservatorioSemiElevado.specified=true");

        // Get all the domicilioList where reservatorioSemiElevado is null
        defaultDomicilioShouldNotBeFound("reservatorioSemiElevado.specified=false");
    }

    @Test
    @Transactional
    public void getAllDomiciliosByConjuntoSanitarioIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where conjuntoSanitario equals to DEFAULT_CONJUNTO_SANITARIO
        defaultDomicilioShouldBeFound("conjuntoSanitario.equals=" + DEFAULT_CONJUNTO_SANITARIO);

        // Get all the domicilioList where conjuntoSanitario equals to UPDATED_CONJUNTO_SANITARIO
        defaultDomicilioShouldNotBeFound("conjuntoSanitario.equals=" + UPDATED_CONJUNTO_SANITARIO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByConjuntoSanitarioIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where conjuntoSanitario in DEFAULT_CONJUNTO_SANITARIO or UPDATED_CONJUNTO_SANITARIO
        defaultDomicilioShouldBeFound("conjuntoSanitario.in=" + DEFAULT_CONJUNTO_SANITARIO + "," + UPDATED_CONJUNTO_SANITARIO);

        // Get all the domicilioList where conjuntoSanitario equals to UPDATED_CONJUNTO_SANITARIO
        defaultDomicilioShouldNotBeFound("conjuntoSanitario.in=" + UPDATED_CONJUNTO_SANITARIO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByConjuntoSanitarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where conjuntoSanitario is not null
        defaultDomicilioShouldBeFound("conjuntoSanitario.specified=true");

        // Get all the domicilioList where conjuntoSanitario is null
        defaultDomicilioShouldNotBeFound("conjuntoSanitario.specified=false");
    }

    @Test
    @Transactional
    public void getAllDomiciliosByPiaCozinhaIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where piaCozinha equals to DEFAULT_PIA_COZINHA
        defaultDomicilioShouldBeFound("piaCozinha.equals=" + DEFAULT_PIA_COZINHA);

        // Get all the domicilioList where piaCozinha equals to UPDATED_PIA_COZINHA
        defaultDomicilioShouldNotBeFound("piaCozinha.equals=" + UPDATED_PIA_COZINHA);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByPiaCozinhaIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where piaCozinha in DEFAULT_PIA_COZINHA or UPDATED_PIA_COZINHA
        defaultDomicilioShouldBeFound("piaCozinha.in=" + DEFAULT_PIA_COZINHA + "," + UPDATED_PIA_COZINHA);

        // Get all the domicilioList where piaCozinha equals to UPDATED_PIA_COZINHA
        defaultDomicilioShouldNotBeFound("piaCozinha.in=" + UPDATED_PIA_COZINHA);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByPiaCozinhaIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where piaCozinha is not null
        defaultDomicilioShouldBeFound("piaCozinha.specified=true");

        // Get all the domicilioList where piaCozinha is null
        defaultDomicilioShouldNotBeFound("piaCozinha.specified=false");
    }

    @Test
    @Transactional
    public void getAllDomiciliosByTanqueLavarRoupaIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where tanqueLavarRoupa equals to DEFAULT_TANQUE_LAVAR_ROUPA
        defaultDomicilioShouldBeFound("tanqueLavarRoupa.equals=" + DEFAULT_TANQUE_LAVAR_ROUPA);

        // Get all the domicilioList where tanqueLavarRoupa equals to UPDATED_TANQUE_LAVAR_ROUPA
        defaultDomicilioShouldNotBeFound("tanqueLavarRoupa.equals=" + UPDATED_TANQUE_LAVAR_ROUPA);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByTanqueLavarRoupaIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where tanqueLavarRoupa in DEFAULT_TANQUE_LAVAR_ROUPA or UPDATED_TANQUE_LAVAR_ROUPA
        defaultDomicilioShouldBeFound("tanqueLavarRoupa.in=" + DEFAULT_TANQUE_LAVAR_ROUPA + "," + UPDATED_TANQUE_LAVAR_ROUPA);

        // Get all the domicilioList where tanqueLavarRoupa equals to UPDATED_TANQUE_LAVAR_ROUPA
        defaultDomicilioShouldNotBeFound("tanqueLavarRoupa.in=" + UPDATED_TANQUE_LAVAR_ROUPA);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByTanqueLavarRoupaIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where tanqueLavarRoupa is not null
        defaultDomicilioShouldBeFound("tanqueLavarRoupa.specified=true");

        // Get all the domicilioList where tanqueLavarRoupa is null
        defaultDomicilioShouldNotBeFound("tanqueLavarRoupa.specified=false");
    }

    @Test
    @Transactional
    public void getAllDomiciliosByFiltroDomesticoIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where filtroDomestico equals to DEFAULT_FILTRO_DOMESTICO
        defaultDomicilioShouldBeFound("filtroDomestico.equals=" + DEFAULT_FILTRO_DOMESTICO);

        // Get all the domicilioList where filtroDomestico equals to UPDATED_FILTRO_DOMESTICO
        defaultDomicilioShouldNotBeFound("filtroDomestico.equals=" + UPDATED_FILTRO_DOMESTICO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByFiltroDomesticoIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where filtroDomestico in DEFAULT_FILTRO_DOMESTICO or UPDATED_FILTRO_DOMESTICO
        defaultDomicilioShouldBeFound("filtroDomestico.in=" + DEFAULT_FILTRO_DOMESTICO + "," + UPDATED_FILTRO_DOMESTICO);

        // Get all the domicilioList where filtroDomestico equals to UPDATED_FILTRO_DOMESTICO
        defaultDomicilioShouldNotBeFound("filtroDomestico.in=" + UPDATED_FILTRO_DOMESTICO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByFiltroDomesticoIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where filtroDomestico is not null
        defaultDomicilioShouldBeFound("filtroDomestico.specified=true");

        // Get all the domicilioList where filtroDomestico is null
        defaultDomicilioShouldNotBeFound("filtroDomestico.specified=false");
    }

    @Test
    @Transactional
    public void getAllDomiciliosByTanqueSepticoIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where tanqueSeptico equals to DEFAULT_TANQUE_SEPTICO
        defaultDomicilioShouldBeFound("tanqueSeptico.equals=" + DEFAULT_TANQUE_SEPTICO);

        // Get all the domicilioList where tanqueSeptico equals to UPDATED_TANQUE_SEPTICO
        defaultDomicilioShouldNotBeFound("tanqueSeptico.equals=" + UPDATED_TANQUE_SEPTICO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByTanqueSepticoIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where tanqueSeptico in DEFAULT_TANQUE_SEPTICO or UPDATED_TANQUE_SEPTICO
        defaultDomicilioShouldBeFound("tanqueSeptico.in=" + DEFAULT_TANQUE_SEPTICO + "," + UPDATED_TANQUE_SEPTICO);

        // Get all the domicilioList where tanqueSeptico equals to UPDATED_TANQUE_SEPTICO
        defaultDomicilioShouldNotBeFound("tanqueSeptico.in=" + UPDATED_TANQUE_SEPTICO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByTanqueSepticoIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where tanqueSeptico is not null
        defaultDomicilioShouldBeFound("tanqueSeptico.specified=true");

        // Get all the domicilioList where tanqueSeptico is null
        defaultDomicilioShouldNotBeFound("tanqueSeptico.specified=false");
    }

    @Test
    @Transactional
    public void getAllDomiciliosBySumidouroIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where sumidouro equals to DEFAULT_SUMIDOURO
        defaultDomicilioShouldBeFound("sumidouro.equals=" + DEFAULT_SUMIDOURO);

        // Get all the domicilioList where sumidouro equals to UPDATED_SUMIDOURO
        defaultDomicilioShouldNotBeFound("sumidouro.equals=" + UPDATED_SUMIDOURO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosBySumidouroIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where sumidouro in DEFAULT_SUMIDOURO or UPDATED_SUMIDOURO
        defaultDomicilioShouldBeFound("sumidouro.in=" + DEFAULT_SUMIDOURO + "," + UPDATED_SUMIDOURO);

        // Get all the domicilioList where sumidouro equals to UPDATED_SUMIDOURO
        defaultDomicilioShouldNotBeFound("sumidouro.in=" + UPDATED_SUMIDOURO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosBySumidouroIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where sumidouro is not null
        defaultDomicilioShouldBeFound("sumidouro.specified=true");

        // Get all the domicilioList where sumidouro is null
        defaultDomicilioShouldNotBeFound("sumidouro.specified=false");
    }

    @Test
    @Transactional
    public void getAllDomiciliosByValaInfiltracaoIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where valaInfiltracao equals to DEFAULT_VALA_INFILTRACAO
        defaultDomicilioShouldBeFound("valaInfiltracao.equals=" + DEFAULT_VALA_INFILTRACAO);

        // Get all the domicilioList where valaInfiltracao equals to UPDATED_VALA_INFILTRACAO
        defaultDomicilioShouldNotBeFound("valaInfiltracao.equals=" + UPDATED_VALA_INFILTRACAO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByValaInfiltracaoIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where valaInfiltracao in DEFAULT_VALA_INFILTRACAO or UPDATED_VALA_INFILTRACAO
        defaultDomicilioShouldBeFound("valaInfiltracao.in=" + DEFAULT_VALA_INFILTRACAO + "," + UPDATED_VALA_INFILTRACAO);

        // Get all the domicilioList where valaInfiltracao equals to UPDATED_VALA_INFILTRACAO
        defaultDomicilioShouldNotBeFound("valaInfiltracao.in=" + UPDATED_VALA_INFILTRACAO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByValaInfiltracaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where valaInfiltracao is not null
        defaultDomicilioShouldBeFound("valaInfiltracao.specified=true");

        // Get all the domicilioList where valaInfiltracao is null
        defaultDomicilioShouldNotBeFound("valaInfiltracao.specified=false");
    }

    @Test
    @Transactional
    public void getAllDomiciliosBySistemaReusoIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where sistemaReuso equals to DEFAULT_SISTEMA_REUSO
        defaultDomicilioShouldBeFound("sistemaReuso.equals=" + DEFAULT_SISTEMA_REUSO);

        // Get all the domicilioList where sistemaReuso equals to UPDATED_SISTEMA_REUSO
        defaultDomicilioShouldNotBeFound("sistemaReuso.equals=" + UPDATED_SISTEMA_REUSO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosBySistemaReusoIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where sistemaReuso in DEFAULT_SISTEMA_REUSO or UPDATED_SISTEMA_REUSO
        defaultDomicilioShouldBeFound("sistemaReuso.in=" + DEFAULT_SISTEMA_REUSO + "," + UPDATED_SISTEMA_REUSO);

        // Get all the domicilioList where sistemaReuso equals to UPDATED_SISTEMA_REUSO
        defaultDomicilioShouldNotBeFound("sistemaReuso.in=" + UPDATED_SISTEMA_REUSO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosBySistemaReusoIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where sistemaReuso is not null
        defaultDomicilioShouldBeFound("sistemaReuso.specified=true");

        // Get all the domicilioList where sistemaReuso is null
        defaultDomicilioShouldNotBeFound("sistemaReuso.specified=false");
    }

    @Test
    @Transactional
    public void getAllDomiciliosByLigacaoDomiciliarEsgotoIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where ligacaoDomiciliarEsgoto equals to DEFAULT_LIGACAO_DOMICILIAR_ESGOTO
        defaultDomicilioShouldBeFound("ligacaoDomiciliarEsgoto.equals=" + DEFAULT_LIGACAO_DOMICILIAR_ESGOTO);

        // Get all the domicilioList where ligacaoDomiciliarEsgoto equals to UPDATED_LIGACAO_DOMICILIAR_ESGOTO
        defaultDomicilioShouldNotBeFound("ligacaoDomiciliarEsgoto.equals=" + UPDATED_LIGACAO_DOMICILIAR_ESGOTO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByLigacaoDomiciliarEsgotoIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where ligacaoDomiciliarEsgoto in DEFAULT_LIGACAO_DOMICILIAR_ESGOTO or UPDATED_LIGACAO_DOMICILIAR_ESGOTO
        defaultDomicilioShouldBeFound("ligacaoDomiciliarEsgoto.in=" + DEFAULT_LIGACAO_DOMICILIAR_ESGOTO + "," + UPDATED_LIGACAO_DOMICILIAR_ESGOTO);

        // Get all the domicilioList where ligacaoDomiciliarEsgoto equals to UPDATED_LIGACAO_DOMICILIAR_ESGOTO
        defaultDomicilioShouldNotBeFound("ligacaoDomiciliarEsgoto.in=" + UPDATED_LIGACAO_DOMICILIAR_ESGOTO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByLigacaoDomiciliarEsgotoIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where ligacaoDomiciliarEsgoto is not null
        defaultDomicilioShouldBeFound("ligacaoDomiciliarEsgoto.specified=true");

        // Get all the domicilioList where ligacaoDomiciliarEsgoto is null
        defaultDomicilioShouldNotBeFound("ligacaoDomiciliarEsgoto.specified=false");
    }

    @Test
    @Transactional
    public void getAllDomiciliosByRecipenteResiduosSolidosIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where recipenteResiduosSolidos equals to DEFAULT_RECIPENTE_RESIDUOS_SOLIDOS
        defaultDomicilioShouldBeFound("recipenteResiduosSolidos.equals=" + DEFAULT_RECIPENTE_RESIDUOS_SOLIDOS);

        // Get all the domicilioList where recipenteResiduosSolidos equals to UPDATED_RECIPENTE_RESIDUOS_SOLIDOS
        defaultDomicilioShouldNotBeFound("recipenteResiduosSolidos.equals=" + UPDATED_RECIPENTE_RESIDUOS_SOLIDOS);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByRecipenteResiduosSolidosIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where recipenteResiduosSolidos in DEFAULT_RECIPENTE_RESIDUOS_SOLIDOS or UPDATED_RECIPENTE_RESIDUOS_SOLIDOS
        defaultDomicilioShouldBeFound("recipenteResiduosSolidos.in=" + DEFAULT_RECIPENTE_RESIDUOS_SOLIDOS + "," + UPDATED_RECIPENTE_RESIDUOS_SOLIDOS);

        // Get all the domicilioList where recipenteResiduosSolidos equals to UPDATED_RECIPENTE_RESIDUOS_SOLIDOS
        defaultDomicilioShouldNotBeFound("recipenteResiduosSolidos.in=" + UPDATED_RECIPENTE_RESIDUOS_SOLIDOS);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByRecipenteResiduosSolidosIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where recipenteResiduosSolidos is not null
        defaultDomicilioShouldBeFound("recipenteResiduosSolidos.specified=true");

        // Get all the domicilioList where recipenteResiduosSolidos is null
        defaultDomicilioShouldNotBeFound("recipenteResiduosSolidos.specified=false");
    }

    @Test
    @Transactional
    public void getAllDomiciliosByDataCadastroIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where dataCadastro equals to DEFAULT_DATA_CADASTRO
        defaultDomicilioShouldBeFound("dataCadastro.equals=" + DEFAULT_DATA_CADASTRO);

        // Get all the domicilioList where dataCadastro equals to UPDATED_DATA_CADASTRO
        defaultDomicilioShouldNotBeFound("dataCadastro.equals=" + UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByDataCadastroIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where dataCadastro in DEFAULT_DATA_CADASTRO or UPDATED_DATA_CADASTRO
        defaultDomicilioShouldBeFound("dataCadastro.in=" + DEFAULT_DATA_CADASTRO + "," + UPDATED_DATA_CADASTRO);

        // Get all the domicilioList where dataCadastro equals to UPDATED_DATA_CADASTRO
        defaultDomicilioShouldNotBeFound("dataCadastro.in=" + UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByDataCadastroIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where dataCadastro is not null
        defaultDomicilioShouldBeFound("dataCadastro.specified=true");

        // Get all the domicilioList where dataCadastro is null
        defaultDomicilioShouldNotBeFound("dataCadastro.specified=false");
    }

    @Test
    @Transactional
    public void getAllDomiciliosByDataCadastroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where dataCadastro greater than or equals to DEFAULT_DATA_CADASTRO
        defaultDomicilioShouldBeFound("dataCadastro.greaterOrEqualThan=" + DEFAULT_DATA_CADASTRO);

        // Get all the domicilioList where dataCadastro greater than or equals to UPDATED_DATA_CADASTRO
        defaultDomicilioShouldNotBeFound("dataCadastro.greaterOrEqualThan=" + UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByDataCadastroIsLessThanSomething() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where dataCadastro less than or equals to DEFAULT_DATA_CADASTRO
        defaultDomicilioShouldNotBeFound("dataCadastro.lessThan=" + DEFAULT_DATA_CADASTRO);

        // Get all the domicilioList where dataCadastro less than or equals to UPDATED_DATA_CADASTRO
        defaultDomicilioShouldBeFound("dataCadastro.lessThan=" + UPDATED_DATA_CADASTRO);
    }


    @Test
    @Transactional
    public void getAllDomiciliosByLevantamentoConcluidoIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where levantamentoConcluido equals to DEFAULT_LEVANTAMENTO_CONCLUIDO
        defaultDomicilioShouldBeFound("levantamentoConcluido.equals=" + DEFAULT_LEVANTAMENTO_CONCLUIDO);

        // Get all the domicilioList where levantamentoConcluido equals to UPDATED_LEVANTAMENTO_CONCLUIDO
        defaultDomicilioShouldNotBeFound("levantamentoConcluido.equals=" + UPDATED_LEVANTAMENTO_CONCLUIDO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByLevantamentoConcluidoIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where levantamentoConcluido in DEFAULT_LEVANTAMENTO_CONCLUIDO or UPDATED_LEVANTAMENTO_CONCLUIDO
        defaultDomicilioShouldBeFound("levantamentoConcluido.in=" + DEFAULT_LEVANTAMENTO_CONCLUIDO + "," + UPDATED_LEVANTAMENTO_CONCLUIDO);

        // Get all the domicilioList where levantamentoConcluido equals to UPDATED_LEVANTAMENTO_CONCLUIDO
        defaultDomicilioShouldNotBeFound("levantamentoConcluido.in=" + UPDATED_LEVANTAMENTO_CONCLUIDO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByLevantamentoConcluidoIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where levantamentoConcluido is not null
        defaultDomicilioShouldBeFound("levantamentoConcluido.specified=true");

        // Get all the domicilioList where levantamentoConcluido is null
        defaultDomicilioShouldNotBeFound("levantamentoConcluido.specified=false");
    }

    @Test
    @Transactional
    public void getAllDomiciliosByDataAlteracaoIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where dataAlteracao equals to DEFAULT_DATA_ALTERACAO
        defaultDomicilioShouldBeFound("dataAlteracao.equals=" + DEFAULT_DATA_ALTERACAO);

        // Get all the domicilioList where dataAlteracao equals to UPDATED_DATA_ALTERACAO
        defaultDomicilioShouldNotBeFound("dataAlteracao.equals=" + UPDATED_DATA_ALTERACAO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByDataAlteracaoIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where dataAlteracao in DEFAULT_DATA_ALTERACAO or UPDATED_DATA_ALTERACAO
        defaultDomicilioShouldBeFound("dataAlteracao.in=" + DEFAULT_DATA_ALTERACAO + "," + UPDATED_DATA_ALTERACAO);

        // Get all the domicilioList where dataAlteracao equals to UPDATED_DATA_ALTERACAO
        defaultDomicilioShouldNotBeFound("dataAlteracao.in=" + UPDATED_DATA_ALTERACAO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByDataAlteracaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where dataAlteracao is not null
        defaultDomicilioShouldBeFound("dataAlteracao.specified=true");

        // Get all the domicilioList where dataAlteracao is null
        defaultDomicilioShouldNotBeFound("dataAlteracao.specified=false");
    }

    @Test
    @Transactional
    public void getAllDomiciliosByDataAlteracaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where dataAlteracao greater than or equals to DEFAULT_DATA_ALTERACAO
        defaultDomicilioShouldBeFound("dataAlteracao.greaterOrEqualThan=" + DEFAULT_DATA_ALTERACAO);

        // Get all the domicilioList where dataAlteracao greater than or equals to UPDATED_DATA_ALTERACAO
        defaultDomicilioShouldNotBeFound("dataAlteracao.greaterOrEqualThan=" + UPDATED_DATA_ALTERACAO);
    }

    @Test
    @Transactional
    public void getAllDomiciliosByDataAlteracaoIsLessThanSomething() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList where dataAlteracao less than or equals to DEFAULT_DATA_ALTERACAO
        defaultDomicilioShouldNotBeFound("dataAlteracao.lessThan=" + DEFAULT_DATA_ALTERACAO);

        // Get all the domicilioList where dataAlteracao less than or equals to UPDATED_DATA_ALTERACAO
        defaultDomicilioShouldBeFound("dataAlteracao.lessThan=" + UPDATED_DATA_ALTERACAO);
    }


    @Test
    @Transactional
    public void getAllDomiciliosByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        domicilio.setUser(user);
        domicilioRepository.saveAndFlush(domicilio);
        Long userId = user.getId();

        // Get all the domicilioList where user equals to userId
        defaultDomicilioShouldBeFound("userId.equals=" + userId);

        // Get all the domicilioList where user equals to userId + 1
        defaultDomicilioShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllDomiciliosByLocalidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        Localidade localidade = LocalidadeResourceIT.createEntity(em);
        em.persist(localidade);
        em.flush();
        domicilio.setLocalidade(localidade);
        domicilioRepository.saveAndFlush(domicilio);
        Long localidadeId = localidade.getId();

        // Get all the domicilioList where localidade equals to localidadeId
        defaultDomicilioShouldBeFound("localidadeId.equals=" + localidadeId);

        // Get all the domicilioList where localidade equals to localidadeId + 1
        defaultDomicilioShouldNotBeFound("localidadeId.equals=" + (localidadeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDomicilioShouldBeFound(String filter) throws Exception {
        restDomicilioMockMvc.perform(get("/api/domicilios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(domicilio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeMorador").value(hasItem(DEFAULT_NOME_MORADOR)))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].numeroHabitantes").value(hasItem(DEFAULT_NUMERO_HABITANTES.intValue())))
            .andExpect(jsonPath("$.[*].ligacaoDomiciliarAgua").value(hasItem(DEFAULT_LIGACAO_DOMICILIAR_AGUA.booleanValue())))
            .andExpect(jsonPath("$.[*].poco").value(hasItem(DEFAULT_POCO.booleanValue())))
            .andExpect(jsonPath("$.[*].cisterna").value(hasItem(DEFAULT_CISTERNA.booleanValue())))
            .andExpect(jsonPath("$.[*].reservatorioElevado").value(hasItem(DEFAULT_RESERVATORIO_ELEVADO.booleanValue())))
            .andExpect(jsonPath("$.[*].reservatorioSemiElevado").value(hasItem(DEFAULT_RESERVATORIO_SEMI_ELEVADO.booleanValue())))
            .andExpect(jsonPath("$.[*].conjuntoSanitario").value(hasItem(DEFAULT_CONJUNTO_SANITARIO.booleanValue())))
            .andExpect(jsonPath("$.[*].piaCozinha").value(hasItem(DEFAULT_PIA_COZINHA.booleanValue())))
            .andExpect(jsonPath("$.[*].tanqueLavarRoupa").value(hasItem(DEFAULT_TANQUE_LAVAR_ROUPA.booleanValue())))
            .andExpect(jsonPath("$.[*].filtroDomestico").value(hasItem(DEFAULT_FILTRO_DOMESTICO.booleanValue())))
            .andExpect(jsonPath("$.[*].tanqueSeptico").value(hasItem(DEFAULT_TANQUE_SEPTICO.booleanValue())))
            .andExpect(jsonPath("$.[*].sumidouro").value(hasItem(DEFAULT_SUMIDOURO.booleanValue())))
            .andExpect(jsonPath("$.[*].valaInfiltracao").value(hasItem(DEFAULT_VALA_INFILTRACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].sistemaReuso").value(hasItem(DEFAULT_SISTEMA_REUSO.booleanValue())))
            .andExpect(jsonPath("$.[*].ligacaoDomiciliarEsgoto").value(hasItem(DEFAULT_LIGACAO_DOMICILIAR_ESGOTO.booleanValue())))
            .andExpect(jsonPath("$.[*].recipenteResiduosSolidos").value(hasItem(DEFAULT_RECIPENTE_RESIDUOS_SOLIDOS.booleanValue())))
            .andExpect(jsonPath("$.[*].dataCadastro").value(hasItem(DEFAULT_DATA_CADASTRO.toString())))
            .andExpect(jsonPath("$.[*].levantamentoConcluido").value(hasItem(DEFAULT_LEVANTAMENTO_CONCLUIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].dataAlteracao").value(hasItem(DEFAULT_DATA_ALTERACAO.toString())));

        // Check, that the count call also returns 1
        restDomicilioMockMvc.perform(get("/api/domicilios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDomicilioShouldNotBeFound(String filter) throws Exception {
        restDomicilioMockMvc.perform(get("/api/domicilios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDomicilioMockMvc.perform(get("/api/domicilios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDomicilio() throws Exception {
        // Get the domicilio
        restDomicilioMockMvc.perform(get("/api/domicilios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDomicilio() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        int databaseSizeBeforeUpdate = domicilioRepository.findAll().size();

        // Update the domicilio
        Domicilio updatedDomicilio = domicilioRepository.findById(domicilio.getId()).get();
        // Disconnect from session so that the updates on updatedDomicilio are not directly saved in db
        em.detach(updatedDomicilio);
        updatedDomicilio
            .nomeMorador(UPDATED_NOME_MORADOR)
            .endereco(UPDATED_ENDERECO)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .numeroHabitantes(UPDATED_NUMERO_HABITANTES)
            .ligacaoDomiciliarAgua(UPDATED_LIGACAO_DOMICILIAR_AGUA)
            .poco(UPDATED_POCO)
            .cisterna(UPDATED_CISTERNA)
            .reservatorioElevado(UPDATED_RESERVATORIO_ELEVADO)
            .reservatorioSemiElevado(UPDATED_RESERVATORIO_SEMI_ELEVADO)
            .conjuntoSanitario(UPDATED_CONJUNTO_SANITARIO)
            .piaCozinha(UPDATED_PIA_COZINHA)
            .tanqueLavarRoupa(UPDATED_TANQUE_LAVAR_ROUPA)
            .filtroDomestico(UPDATED_FILTRO_DOMESTICO)
            .tanqueSeptico(UPDATED_TANQUE_SEPTICO)
            .sumidouro(UPDATED_SUMIDOURO)
            .valaInfiltracao(UPDATED_VALA_INFILTRACAO)
            .sistemaReuso(UPDATED_SISTEMA_REUSO)
            .ligacaoDomiciliarEsgoto(UPDATED_LIGACAO_DOMICILIAR_ESGOTO)
            .recipenteResiduosSolidos(UPDATED_RECIPENTE_RESIDUOS_SOLIDOS)
            .dataCadastro(UPDATED_DATA_CADASTRO)
            .levantamentoConcluido(UPDATED_LEVANTAMENTO_CONCLUIDO)
            .dataAlteracao(UPDATED_DATA_ALTERACAO);
        DomicilioDTO domicilioDTO = domicilioMapper.toDto(updatedDomicilio);

        restDomicilioMockMvc.perform(put("/api/domicilios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(domicilioDTO)))
            .andExpect(status().isOk());

        // Validate the Domicilio in the database
        List<Domicilio> domicilioList = domicilioRepository.findAll();
        assertThat(domicilioList).hasSize(databaseSizeBeforeUpdate);
        Domicilio testDomicilio = domicilioList.get(domicilioList.size() - 1);
        assertThat(testDomicilio.getNomeMorador()).isEqualTo(UPDATED_NOME_MORADOR);
        assertThat(testDomicilio.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testDomicilio.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testDomicilio.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testDomicilio.getNumeroHabitantes()).isEqualTo(UPDATED_NUMERO_HABITANTES);
        assertThat(testDomicilio.isLigacaoDomiciliarAgua()).isEqualTo(UPDATED_LIGACAO_DOMICILIAR_AGUA);
        assertThat(testDomicilio.isPoco()).isEqualTo(UPDATED_POCO);
        assertThat(testDomicilio.isCisterna()).isEqualTo(UPDATED_CISTERNA);
        assertThat(testDomicilio.isReservatorioElevado()).isEqualTo(UPDATED_RESERVATORIO_ELEVADO);
        assertThat(testDomicilio.isReservatorioSemiElevado()).isEqualTo(UPDATED_RESERVATORIO_SEMI_ELEVADO);
        assertThat(testDomicilio.isConjuntoSanitario()).isEqualTo(UPDATED_CONJUNTO_SANITARIO);
        assertThat(testDomicilio.isPiaCozinha()).isEqualTo(UPDATED_PIA_COZINHA);
        assertThat(testDomicilio.isTanqueLavarRoupa()).isEqualTo(UPDATED_TANQUE_LAVAR_ROUPA);
        assertThat(testDomicilio.isFiltroDomestico()).isEqualTo(UPDATED_FILTRO_DOMESTICO);
        assertThat(testDomicilio.isTanqueSeptico()).isEqualTo(UPDATED_TANQUE_SEPTICO);
        assertThat(testDomicilio.isSumidouro()).isEqualTo(UPDATED_SUMIDOURO);
        assertThat(testDomicilio.isValaInfiltracao()).isEqualTo(UPDATED_VALA_INFILTRACAO);
        assertThat(testDomicilio.isSistemaReuso()).isEqualTo(UPDATED_SISTEMA_REUSO);
        assertThat(testDomicilio.isLigacaoDomiciliarEsgoto()).isEqualTo(UPDATED_LIGACAO_DOMICILIAR_ESGOTO);
        assertThat(testDomicilio.isRecipenteResiduosSolidos()).isEqualTo(UPDATED_RECIPENTE_RESIDUOS_SOLIDOS);
        assertThat(testDomicilio.getDataCadastro()).isEqualTo(UPDATED_DATA_CADASTRO);
        assertThat(testDomicilio.isLevantamentoConcluido()).isEqualTo(UPDATED_LEVANTAMENTO_CONCLUIDO);
        assertThat(testDomicilio.getDataAlteracao()).isEqualTo(UPDATED_DATA_ALTERACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingDomicilio() throws Exception {
        int databaseSizeBeforeUpdate = domicilioRepository.findAll().size();

        // Create the Domicilio
        DomicilioDTO domicilioDTO = domicilioMapper.toDto(domicilio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDomicilioMockMvc.perform(put("/api/domicilios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(domicilioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Domicilio in the database
        List<Domicilio> domicilioList = domicilioRepository.findAll();
        assertThat(domicilioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDomicilio() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        int databaseSizeBeforeDelete = domicilioRepository.findAll().size();

        // Delete the domicilio
        restDomicilioMockMvc.perform(delete("/api/domicilios/{id}", domicilio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Domicilio> domicilioList = domicilioRepository.findAll();
        assertThat(domicilioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Domicilio.class);
        Domicilio domicilio1 = new Domicilio();
        domicilio1.setId(1L);
        Domicilio domicilio2 = new Domicilio();
        domicilio2.setId(domicilio1.getId());
        assertThat(domicilio1).isEqualTo(domicilio2);
        domicilio2.setId(2L);
        assertThat(domicilio1).isNotEqualTo(domicilio2);
        domicilio1.setId(null);
        assertThat(domicilio1).isNotEqualTo(domicilio2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DomicilioDTO.class);
        DomicilioDTO domicilioDTO1 = new DomicilioDTO();
        domicilioDTO1.setId(1L);
        DomicilioDTO domicilioDTO2 = new DomicilioDTO();
        assertThat(domicilioDTO1).isNotEqualTo(domicilioDTO2);
        domicilioDTO2.setId(domicilioDTO1.getId());
        assertThat(domicilioDTO1).isEqualTo(domicilioDTO2);
        domicilioDTO2.setId(2L);
        assertThat(domicilioDTO1).isNotEqualTo(domicilioDTO2);
        domicilioDTO1.setId(null);
        assertThat(domicilioDTO1).isNotEqualTo(domicilioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(domicilioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(domicilioMapper.fromId(null)).isNull();
    }
}
