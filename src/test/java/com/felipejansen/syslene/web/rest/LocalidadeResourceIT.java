package com.felipejansen.syslene.web.rest;

import com.felipejansen.syslene.SysleneApp;
import com.felipejansen.syslene.domain.Localidade;
import com.felipejansen.syslene.domain.User;
import com.felipejansen.syslene.domain.Cidade;
import com.felipejansen.syslene.repository.LocalidadeRepository;
import com.felipejansen.syslene.service.LocalidadeService;
import com.felipejansen.syslene.service.dto.LocalidadeDTO;
import com.felipejansen.syslene.service.mapper.LocalidadeMapper;
import com.felipejansen.syslene.web.rest.errors.ExceptionTranslator;
import com.felipejansen.syslene.service.dto.LocalidadeCriteria;
import com.felipejansen.syslene.service.LocalidadeQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.felipejansen.syslene.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link LocalidadeResource} REST controller.
 */
@SpringBootTest(classes = SysleneApp.class)
public class LocalidadeResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ABASTECIMENTO_AGUA = false;
    private static final Boolean UPDATED_ABASTECIMENTO_AGUA = true;

    private static final Boolean DEFAULT_ESGOTO_SANITARIO = false;
    private static final Boolean UPDATED_ESGOTO_SANITARIO = true;

    private static final Boolean DEFAULT_COLETA_RESIDUOS = false;
    private static final Boolean UPDATED_COLETA_RESIDUOS = true;

    private static final LocalDate DEFAULT_DATA_ALTERACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ALTERACAO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private LocalidadeRepository localidadeRepository;

    @Autowired
    private LocalidadeMapper localidadeMapper;

    @Autowired
    private LocalidadeService localidadeService;

    @Autowired
    private LocalidadeQueryService localidadeQueryService;

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

    private MockMvc restLocalidadeMockMvc;

    private Localidade localidade;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocalidadeResource localidadeResource = new LocalidadeResource(localidadeService, localidadeQueryService);
        this.restLocalidadeMockMvc = MockMvcBuilders.standaloneSetup(localidadeResource)
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
    public static Localidade createEntity(EntityManager em) {
        Localidade localidade = new Localidade()
            .descricao(DEFAULT_DESCRICAO)
            .abastecimentoAgua(DEFAULT_ABASTECIMENTO_AGUA)
            .esgotoSanitario(DEFAULT_ESGOTO_SANITARIO)
            .coletaResiduos(DEFAULT_COLETA_RESIDUOS)
            .dataAlteracao(DEFAULT_DATA_ALTERACAO);
        return localidade;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Localidade createUpdatedEntity(EntityManager em) {
        Localidade localidade = new Localidade()
            .descricao(UPDATED_DESCRICAO)
            .abastecimentoAgua(UPDATED_ABASTECIMENTO_AGUA)
            .esgotoSanitario(UPDATED_ESGOTO_SANITARIO)
            .coletaResiduos(UPDATED_COLETA_RESIDUOS)
            .dataAlteracao(UPDATED_DATA_ALTERACAO);
        return localidade;
    }

    @BeforeEach
    public void initTest() {
        localidade = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocalidade() throws Exception {
        int databaseSizeBeforeCreate = localidadeRepository.findAll().size();

        // Create the Localidade
        LocalidadeDTO localidadeDTO = localidadeMapper.toDto(localidade);
        restLocalidadeMockMvc.perform(post("/api/localidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localidadeDTO)))
            .andExpect(status().isCreated());

        // Validate the Localidade in the database
        List<Localidade> localidadeList = localidadeRepository.findAll();
        assertThat(localidadeList).hasSize(databaseSizeBeforeCreate + 1);
        Localidade testLocalidade = localidadeList.get(localidadeList.size() - 1);
        assertThat(testLocalidade.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testLocalidade.isAbastecimentoAgua()).isEqualTo(DEFAULT_ABASTECIMENTO_AGUA);
        assertThat(testLocalidade.isEsgotoSanitario()).isEqualTo(DEFAULT_ESGOTO_SANITARIO);
        assertThat(testLocalidade.isColetaResiduos()).isEqualTo(DEFAULT_COLETA_RESIDUOS);
        assertThat(testLocalidade.getDataAlteracao()).isEqualTo(DEFAULT_DATA_ALTERACAO);
    }

    @Test
    @Transactional
    public void createLocalidadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = localidadeRepository.findAll().size();

        // Create the Localidade with an existing ID
        localidade.setId(1L);
        LocalidadeDTO localidadeDTO = localidadeMapper.toDto(localidade);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocalidadeMockMvc.perform(post("/api/localidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Localidade in the database
        List<Localidade> localidadeList = localidadeRepository.findAll();
        assertThat(localidadeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = localidadeRepository.findAll().size();
        // set the field null
        localidade.setDescricao(null);

        // Create the Localidade, which fails.
        LocalidadeDTO localidadeDTO = localidadeMapper.toDto(localidade);

        restLocalidadeMockMvc.perform(post("/api/localidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localidadeDTO)))
            .andExpect(status().isBadRequest());

        List<Localidade> localidadeList = localidadeRepository.findAll();
        assertThat(localidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAbastecimentoAguaIsRequired() throws Exception {
        int databaseSizeBeforeTest = localidadeRepository.findAll().size();
        // set the field null
        localidade.setAbastecimentoAgua(null);

        // Create the Localidade, which fails.
        LocalidadeDTO localidadeDTO = localidadeMapper.toDto(localidade);

        restLocalidadeMockMvc.perform(post("/api/localidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localidadeDTO)))
            .andExpect(status().isBadRequest());

        List<Localidade> localidadeList = localidadeRepository.findAll();
        assertThat(localidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEsgotoSanitarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = localidadeRepository.findAll().size();
        // set the field null
        localidade.setEsgotoSanitario(null);

        // Create the Localidade, which fails.
        LocalidadeDTO localidadeDTO = localidadeMapper.toDto(localidade);

        restLocalidadeMockMvc.perform(post("/api/localidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localidadeDTO)))
            .andExpect(status().isBadRequest());

        List<Localidade> localidadeList = localidadeRepository.findAll();
        assertThat(localidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkColetaResiduosIsRequired() throws Exception {
        int databaseSizeBeforeTest = localidadeRepository.findAll().size();
        // set the field null
        localidade.setColetaResiduos(null);

        // Create the Localidade, which fails.
        LocalidadeDTO localidadeDTO = localidadeMapper.toDto(localidade);

        restLocalidadeMockMvc.perform(post("/api/localidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localidadeDTO)))
            .andExpect(status().isBadRequest());

        List<Localidade> localidadeList = localidadeRepository.findAll();
        assertThat(localidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLocalidades() throws Exception {
        // Initialize the database
        localidadeRepository.saveAndFlush(localidade);

        // Get all the localidadeList
        restLocalidadeMockMvc.perform(get("/api/localidades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].abastecimentoAgua").value(hasItem(DEFAULT_ABASTECIMENTO_AGUA.booleanValue())))
            .andExpect(jsonPath("$.[*].esgotoSanitario").value(hasItem(DEFAULT_ESGOTO_SANITARIO.booleanValue())))
            .andExpect(jsonPath("$.[*].coletaResiduos").value(hasItem(DEFAULT_COLETA_RESIDUOS.booleanValue())))
            .andExpect(jsonPath("$.[*].dataAlteracao").value(hasItem(DEFAULT_DATA_ALTERACAO.toString())));
    }
    
    @Test
    @Transactional
    public void getLocalidade() throws Exception {
        // Initialize the database
        localidadeRepository.saveAndFlush(localidade);

        // Get the localidade
        restLocalidadeMockMvc.perform(get("/api/localidades/{id}", localidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(localidade.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.abastecimentoAgua").value(DEFAULT_ABASTECIMENTO_AGUA.booleanValue()))
            .andExpect(jsonPath("$.esgotoSanitario").value(DEFAULT_ESGOTO_SANITARIO.booleanValue()))
            .andExpect(jsonPath("$.coletaResiduos").value(DEFAULT_COLETA_RESIDUOS.booleanValue()))
            .andExpect(jsonPath("$.dataAlteracao").value(DEFAULT_DATA_ALTERACAO.toString()));
    }

    @Test
    @Transactional
    public void getAllLocalidadesByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        localidadeRepository.saveAndFlush(localidade);

        // Get all the localidadeList where descricao equals to DEFAULT_DESCRICAO
        defaultLocalidadeShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the localidadeList where descricao equals to UPDATED_DESCRICAO
        defaultLocalidadeShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllLocalidadesByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        localidadeRepository.saveAndFlush(localidade);

        // Get all the localidadeList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultLocalidadeShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the localidadeList where descricao equals to UPDATED_DESCRICAO
        defaultLocalidadeShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllLocalidadesByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        localidadeRepository.saveAndFlush(localidade);

        // Get all the localidadeList where descricao is not null
        defaultLocalidadeShouldBeFound("descricao.specified=true");

        // Get all the localidadeList where descricao is null
        defaultLocalidadeShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocalidadesByAbastecimentoAguaIsEqualToSomething() throws Exception {
        // Initialize the database
        localidadeRepository.saveAndFlush(localidade);

        // Get all the localidadeList where abastecimentoAgua equals to DEFAULT_ABASTECIMENTO_AGUA
        defaultLocalidadeShouldBeFound("abastecimentoAgua.equals=" + DEFAULT_ABASTECIMENTO_AGUA);

        // Get all the localidadeList where abastecimentoAgua equals to UPDATED_ABASTECIMENTO_AGUA
        defaultLocalidadeShouldNotBeFound("abastecimentoAgua.equals=" + UPDATED_ABASTECIMENTO_AGUA);
    }

    @Test
    @Transactional
    public void getAllLocalidadesByAbastecimentoAguaIsInShouldWork() throws Exception {
        // Initialize the database
        localidadeRepository.saveAndFlush(localidade);

        // Get all the localidadeList where abastecimentoAgua in DEFAULT_ABASTECIMENTO_AGUA or UPDATED_ABASTECIMENTO_AGUA
        defaultLocalidadeShouldBeFound("abastecimentoAgua.in=" + DEFAULT_ABASTECIMENTO_AGUA + "," + UPDATED_ABASTECIMENTO_AGUA);

        // Get all the localidadeList where abastecimentoAgua equals to UPDATED_ABASTECIMENTO_AGUA
        defaultLocalidadeShouldNotBeFound("abastecimentoAgua.in=" + UPDATED_ABASTECIMENTO_AGUA);
    }

    @Test
    @Transactional
    public void getAllLocalidadesByAbastecimentoAguaIsNullOrNotNull() throws Exception {
        // Initialize the database
        localidadeRepository.saveAndFlush(localidade);

        // Get all the localidadeList where abastecimentoAgua is not null
        defaultLocalidadeShouldBeFound("abastecimentoAgua.specified=true");

        // Get all the localidadeList where abastecimentoAgua is null
        defaultLocalidadeShouldNotBeFound("abastecimentoAgua.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocalidadesByEsgotoSanitarioIsEqualToSomething() throws Exception {
        // Initialize the database
        localidadeRepository.saveAndFlush(localidade);

        // Get all the localidadeList where esgotoSanitario equals to DEFAULT_ESGOTO_SANITARIO
        defaultLocalidadeShouldBeFound("esgotoSanitario.equals=" + DEFAULT_ESGOTO_SANITARIO);

        // Get all the localidadeList where esgotoSanitario equals to UPDATED_ESGOTO_SANITARIO
        defaultLocalidadeShouldNotBeFound("esgotoSanitario.equals=" + UPDATED_ESGOTO_SANITARIO);
    }

    @Test
    @Transactional
    public void getAllLocalidadesByEsgotoSanitarioIsInShouldWork() throws Exception {
        // Initialize the database
        localidadeRepository.saveAndFlush(localidade);

        // Get all the localidadeList where esgotoSanitario in DEFAULT_ESGOTO_SANITARIO or UPDATED_ESGOTO_SANITARIO
        defaultLocalidadeShouldBeFound("esgotoSanitario.in=" + DEFAULT_ESGOTO_SANITARIO + "," + UPDATED_ESGOTO_SANITARIO);

        // Get all the localidadeList where esgotoSanitario equals to UPDATED_ESGOTO_SANITARIO
        defaultLocalidadeShouldNotBeFound("esgotoSanitario.in=" + UPDATED_ESGOTO_SANITARIO);
    }

    @Test
    @Transactional
    public void getAllLocalidadesByEsgotoSanitarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        localidadeRepository.saveAndFlush(localidade);

        // Get all the localidadeList where esgotoSanitario is not null
        defaultLocalidadeShouldBeFound("esgotoSanitario.specified=true");

        // Get all the localidadeList where esgotoSanitario is null
        defaultLocalidadeShouldNotBeFound("esgotoSanitario.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocalidadesByColetaResiduosIsEqualToSomething() throws Exception {
        // Initialize the database
        localidadeRepository.saveAndFlush(localidade);

        // Get all the localidadeList where coletaResiduos equals to DEFAULT_COLETA_RESIDUOS
        defaultLocalidadeShouldBeFound("coletaResiduos.equals=" + DEFAULT_COLETA_RESIDUOS);

        // Get all the localidadeList where coletaResiduos equals to UPDATED_COLETA_RESIDUOS
        defaultLocalidadeShouldNotBeFound("coletaResiduos.equals=" + UPDATED_COLETA_RESIDUOS);
    }

    @Test
    @Transactional
    public void getAllLocalidadesByColetaResiduosIsInShouldWork() throws Exception {
        // Initialize the database
        localidadeRepository.saveAndFlush(localidade);

        // Get all the localidadeList where coletaResiduos in DEFAULT_COLETA_RESIDUOS or UPDATED_COLETA_RESIDUOS
        defaultLocalidadeShouldBeFound("coletaResiduos.in=" + DEFAULT_COLETA_RESIDUOS + "," + UPDATED_COLETA_RESIDUOS);

        // Get all the localidadeList where coletaResiduos equals to UPDATED_COLETA_RESIDUOS
        defaultLocalidadeShouldNotBeFound("coletaResiduos.in=" + UPDATED_COLETA_RESIDUOS);
    }

    @Test
    @Transactional
    public void getAllLocalidadesByColetaResiduosIsNullOrNotNull() throws Exception {
        // Initialize the database
        localidadeRepository.saveAndFlush(localidade);

        // Get all the localidadeList where coletaResiduos is not null
        defaultLocalidadeShouldBeFound("coletaResiduos.specified=true");

        // Get all the localidadeList where coletaResiduos is null
        defaultLocalidadeShouldNotBeFound("coletaResiduos.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocalidadesByDataAlteracaoIsEqualToSomething() throws Exception {
        // Initialize the database
        localidadeRepository.saveAndFlush(localidade);

        // Get all the localidadeList where dataAlteracao equals to DEFAULT_DATA_ALTERACAO
        defaultLocalidadeShouldBeFound("dataAlteracao.equals=" + DEFAULT_DATA_ALTERACAO);

        // Get all the localidadeList where dataAlteracao equals to UPDATED_DATA_ALTERACAO
        defaultLocalidadeShouldNotBeFound("dataAlteracao.equals=" + UPDATED_DATA_ALTERACAO);
    }

    @Test
    @Transactional
    public void getAllLocalidadesByDataAlteracaoIsInShouldWork() throws Exception {
        // Initialize the database
        localidadeRepository.saveAndFlush(localidade);

        // Get all the localidadeList where dataAlteracao in DEFAULT_DATA_ALTERACAO or UPDATED_DATA_ALTERACAO
        defaultLocalidadeShouldBeFound("dataAlteracao.in=" + DEFAULT_DATA_ALTERACAO + "," + UPDATED_DATA_ALTERACAO);

        // Get all the localidadeList where dataAlteracao equals to UPDATED_DATA_ALTERACAO
        defaultLocalidadeShouldNotBeFound("dataAlteracao.in=" + UPDATED_DATA_ALTERACAO);
    }

    @Test
    @Transactional
    public void getAllLocalidadesByDataAlteracaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        localidadeRepository.saveAndFlush(localidade);

        // Get all the localidadeList where dataAlteracao is not null
        defaultLocalidadeShouldBeFound("dataAlteracao.specified=true");

        // Get all the localidadeList where dataAlteracao is null
        defaultLocalidadeShouldNotBeFound("dataAlteracao.specified=false");
    }

    @Test
    @Transactional
    public void getAllLocalidadesByDataAlteracaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        localidadeRepository.saveAndFlush(localidade);

        // Get all the localidadeList where dataAlteracao greater than or equals to DEFAULT_DATA_ALTERACAO
        defaultLocalidadeShouldBeFound("dataAlteracao.greaterOrEqualThan=" + DEFAULT_DATA_ALTERACAO);

        // Get all the localidadeList where dataAlteracao greater than or equals to UPDATED_DATA_ALTERACAO
        defaultLocalidadeShouldNotBeFound("dataAlteracao.greaterOrEqualThan=" + UPDATED_DATA_ALTERACAO);
    }

    @Test
    @Transactional
    public void getAllLocalidadesByDataAlteracaoIsLessThanSomething() throws Exception {
        // Initialize the database
        localidadeRepository.saveAndFlush(localidade);

        // Get all the localidadeList where dataAlteracao less than or equals to DEFAULT_DATA_ALTERACAO
        defaultLocalidadeShouldNotBeFound("dataAlteracao.lessThan=" + DEFAULT_DATA_ALTERACAO);

        // Get all the localidadeList where dataAlteracao less than or equals to UPDATED_DATA_ALTERACAO
        defaultLocalidadeShouldBeFound("dataAlteracao.lessThan=" + UPDATED_DATA_ALTERACAO);
    }


    @Test
    @Transactional
    public void getAllLocalidadesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        localidade.setUser(user);
        localidadeRepository.saveAndFlush(localidade);
        Long userId = user.getId();

        // Get all the localidadeList where user equals to userId
        defaultLocalidadeShouldBeFound("userId.equals=" + userId);

        // Get all the localidadeList where user equals to userId + 1
        defaultLocalidadeShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllLocalidadesByCidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        Cidade cidade = CidadeResourceIT.createEntity(em);
        em.persist(cidade);
        em.flush();
        localidade.setCidade(cidade);
        localidadeRepository.saveAndFlush(localidade);
        Long cidadeId = cidade.getId();

        // Get all the localidadeList where cidade equals to cidadeId
        defaultLocalidadeShouldBeFound("cidadeId.equals=" + cidadeId);

        // Get all the localidadeList where cidade equals to cidadeId + 1
        defaultLocalidadeShouldNotBeFound("cidadeId.equals=" + (cidadeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLocalidadeShouldBeFound(String filter) throws Exception {
        restLocalidadeMockMvc.perform(get("/api/localidades?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].abastecimentoAgua").value(hasItem(DEFAULT_ABASTECIMENTO_AGUA.booleanValue())))
            .andExpect(jsonPath("$.[*].esgotoSanitario").value(hasItem(DEFAULT_ESGOTO_SANITARIO.booleanValue())))
            .andExpect(jsonPath("$.[*].coletaResiduos").value(hasItem(DEFAULT_COLETA_RESIDUOS.booleanValue())))
            .andExpect(jsonPath("$.[*].dataAlteracao").value(hasItem(DEFAULT_DATA_ALTERACAO.toString())));

        // Check, that the count call also returns 1
        restLocalidadeMockMvc.perform(get("/api/localidades/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLocalidadeShouldNotBeFound(String filter) throws Exception {
        restLocalidadeMockMvc.perform(get("/api/localidades?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLocalidadeMockMvc.perform(get("/api/localidades/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLocalidade() throws Exception {
        // Get the localidade
        restLocalidadeMockMvc.perform(get("/api/localidades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocalidade() throws Exception {
        // Initialize the database
        localidadeRepository.saveAndFlush(localidade);

        int databaseSizeBeforeUpdate = localidadeRepository.findAll().size();

        // Update the localidade
        Localidade updatedLocalidade = localidadeRepository.findById(localidade.getId()).get();
        // Disconnect from session so that the updates on updatedLocalidade are not directly saved in db
        em.detach(updatedLocalidade);
        updatedLocalidade
            .descricao(UPDATED_DESCRICAO)
            .abastecimentoAgua(UPDATED_ABASTECIMENTO_AGUA)
            .esgotoSanitario(UPDATED_ESGOTO_SANITARIO)
            .coletaResiduos(UPDATED_COLETA_RESIDUOS)
            .dataAlteracao(UPDATED_DATA_ALTERACAO);
        LocalidadeDTO localidadeDTO = localidadeMapper.toDto(updatedLocalidade);

        restLocalidadeMockMvc.perform(put("/api/localidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localidadeDTO)))
            .andExpect(status().isOk());

        // Validate the Localidade in the database
        List<Localidade> localidadeList = localidadeRepository.findAll();
        assertThat(localidadeList).hasSize(databaseSizeBeforeUpdate);
        Localidade testLocalidade = localidadeList.get(localidadeList.size() - 1);
        assertThat(testLocalidade.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testLocalidade.isAbastecimentoAgua()).isEqualTo(UPDATED_ABASTECIMENTO_AGUA);
        assertThat(testLocalidade.isEsgotoSanitario()).isEqualTo(UPDATED_ESGOTO_SANITARIO);
        assertThat(testLocalidade.isColetaResiduos()).isEqualTo(UPDATED_COLETA_RESIDUOS);
        assertThat(testLocalidade.getDataAlteracao()).isEqualTo(UPDATED_DATA_ALTERACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingLocalidade() throws Exception {
        int databaseSizeBeforeUpdate = localidadeRepository.findAll().size();

        // Create the Localidade
        LocalidadeDTO localidadeDTO = localidadeMapper.toDto(localidade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocalidadeMockMvc.perform(put("/api/localidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Localidade in the database
        List<Localidade> localidadeList = localidadeRepository.findAll();
        assertThat(localidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLocalidade() throws Exception {
        // Initialize the database
        localidadeRepository.saveAndFlush(localidade);

        int databaseSizeBeforeDelete = localidadeRepository.findAll().size();

        // Delete the localidade
        restLocalidadeMockMvc.perform(delete("/api/localidades/{id}", localidade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Localidade> localidadeList = localidadeRepository.findAll();
        assertThat(localidadeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Localidade.class);
        Localidade localidade1 = new Localidade();
        localidade1.setId(1L);
        Localidade localidade2 = new Localidade();
        localidade2.setId(localidade1.getId());
        assertThat(localidade1).isEqualTo(localidade2);
        localidade2.setId(2L);
        assertThat(localidade1).isNotEqualTo(localidade2);
        localidade1.setId(null);
        assertThat(localidade1).isNotEqualTo(localidade2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocalidadeDTO.class);
        LocalidadeDTO localidadeDTO1 = new LocalidadeDTO();
        localidadeDTO1.setId(1L);
        LocalidadeDTO localidadeDTO2 = new LocalidadeDTO();
        assertThat(localidadeDTO1).isNotEqualTo(localidadeDTO2);
        localidadeDTO2.setId(localidadeDTO1.getId());
        assertThat(localidadeDTO1).isEqualTo(localidadeDTO2);
        localidadeDTO2.setId(2L);
        assertThat(localidadeDTO1).isNotEqualTo(localidadeDTO2);
        localidadeDTO1.setId(null);
        assertThat(localidadeDTO1).isNotEqualTo(localidadeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(localidadeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(localidadeMapper.fromId(null)).isNull();
    }
}
