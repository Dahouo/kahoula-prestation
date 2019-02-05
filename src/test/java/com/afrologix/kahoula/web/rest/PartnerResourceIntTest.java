package com.afrologix.kahoula.web.rest;

import com.afrologix.kahoula.KahoulaPrestationApp;

import com.afrologix.kahoula.domain.Partner;
import com.afrologix.kahoula.repository.PartnerRepository;
import com.afrologix.kahoula.repository.search.PartnerSearchRepository;
import com.afrologix.kahoula.service.PartnerService;
import com.afrologix.kahoula.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import java.util.Collections;
import java.util.List;


import static com.afrologix.kahoula.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PartnerResource REST controller.
 *
 * @see PartnerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KahoulaPrestationApp.class)
public class PartnerResourceIntTest {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CNI_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_CNI_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_USER_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_USER_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCES = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCES = "BBBBBBBBBB";

    private static final String DEFAULT_QUALIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_QUALIFICATION = "BBBBBBBBBB";

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private PartnerService partnerService;

    /**
     * This repository is mocked in the com.afrologix.kahoula.repository.search test package.
     *
     * @see com.afrologix.kahoula.repository.search.PartnerSearchRepositoryMockConfiguration
     */
    @Autowired
    private PartnerSearchRepository mockPartnerSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restPartnerMockMvc;

    private Partner partner;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartnerResource partnerResource = new PartnerResource(partnerService);
        this.restPartnerMockMvc = MockMvcBuilders.standaloneSetup(partnerResource)
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
    public static Partner createEntity() {
        Partner partner = new Partner()
            .userId(DEFAULT_USER_ID)
            .cniImage(DEFAULT_CNI_IMAGE)
            .userImage(DEFAULT_USER_IMAGE)
            .references(DEFAULT_REFERENCES)
            .qualification(DEFAULT_QUALIFICATION);
        return partner;
    }

    @Before
    public void initTest() {
        partnerRepository.deleteAll();
        partner = createEntity();
    }

    @Test
    public void createPartner() throws Exception {
        int databaseSizeBeforeCreate = partnerRepository.findAll().size();

        // Create the Partner
        restPartnerMockMvc.perform(post("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partner)))
            .andExpect(status().isCreated());

        // Validate the Partner in the database
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeCreate + 1);
        Partner testPartner = partnerList.get(partnerList.size() - 1);
        assertThat(testPartner.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testPartner.getCniImage()).isEqualTo(DEFAULT_CNI_IMAGE);
        assertThat(testPartner.getUserImage()).isEqualTo(DEFAULT_USER_IMAGE);
        assertThat(testPartner.getReferences()).isEqualTo(DEFAULT_REFERENCES);
        assertThat(testPartner.getQualification()).isEqualTo(DEFAULT_QUALIFICATION);

        // Validate the Partner in Elasticsearch
        verify(mockPartnerSearchRepository, times(1)).save(testPartner);
    }

    @Test
    public void createPartnerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partnerRepository.findAll().size();

        // Create the Partner with an existing ID
        partner.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartnerMockMvc.perform(post("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partner)))
            .andExpect(status().isBadRequest());

        // Validate the Partner in the database
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeCreate);

        // Validate the Partner in Elasticsearch
        verify(mockPartnerSearchRepository, times(0)).save(partner);
    }

    @Test
    public void checkCniImageIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setCniImage(null);

        // Create the Partner, which fails.

        restPartnerMockMvc.perform(post("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partner)))
            .andExpect(status().isBadRequest());

        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllPartners() throws Exception {
        // Initialize the database
        partnerRepository.save(partner);

        // Get all the partnerList
        restPartnerMockMvc.perform(get("/api/partners?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partner.getId())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].cniImage").value(hasItem(DEFAULT_CNI_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].userImage").value(hasItem(DEFAULT_USER_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].references").value(hasItem(DEFAULT_REFERENCES.toString())))
            .andExpect(jsonPath("$.[*].qualification").value(hasItem(DEFAULT_QUALIFICATION.toString())));
    }
    
    @Test
    public void getPartner() throws Exception {
        // Initialize the database
        partnerRepository.save(partner);

        // Get the partner
        restPartnerMockMvc.perform(get("/api/partners/{id}", partner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partner.getId()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.cniImage").value(DEFAULT_CNI_IMAGE.toString()))
            .andExpect(jsonPath("$.userImage").value(DEFAULT_USER_IMAGE.toString()))
            .andExpect(jsonPath("$.references").value(DEFAULT_REFERENCES.toString()))
            .andExpect(jsonPath("$.qualification").value(DEFAULT_QUALIFICATION.toString()));
    }

    @Test
    public void getNonExistingPartner() throws Exception {
        // Get the partner
        restPartnerMockMvc.perform(get("/api/partners/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePartner() throws Exception {
        // Initialize the database
        partnerService.save(partner);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockPartnerSearchRepository);

        int databaseSizeBeforeUpdate = partnerRepository.findAll().size();

        // Update the partner
        Partner updatedPartner = partnerRepository.findById(partner.getId()).get();
        updatedPartner
            .userId(UPDATED_USER_ID)
            .cniImage(UPDATED_CNI_IMAGE)
            .userImage(UPDATED_USER_IMAGE)
            .references(UPDATED_REFERENCES)
            .qualification(UPDATED_QUALIFICATION);

        restPartnerMockMvc.perform(put("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartner)))
            .andExpect(status().isOk());

        // Validate the Partner in the database
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeUpdate);
        Partner testPartner = partnerList.get(partnerList.size() - 1);
        assertThat(testPartner.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPartner.getCniImage()).isEqualTo(UPDATED_CNI_IMAGE);
        assertThat(testPartner.getUserImage()).isEqualTo(UPDATED_USER_IMAGE);
        assertThat(testPartner.getReferences()).isEqualTo(UPDATED_REFERENCES);
        assertThat(testPartner.getQualification()).isEqualTo(UPDATED_QUALIFICATION);

        // Validate the Partner in Elasticsearch
        verify(mockPartnerSearchRepository, times(1)).save(testPartner);
    }

    @Test
    public void updateNonExistingPartner() throws Exception {
        int databaseSizeBeforeUpdate = partnerRepository.findAll().size();

        // Create the Partner

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartnerMockMvc.perform(put("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partner)))
            .andExpect(status().isBadRequest());

        // Validate the Partner in the database
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Partner in Elasticsearch
        verify(mockPartnerSearchRepository, times(0)).save(partner);
    }

    @Test
    public void deletePartner() throws Exception {
        // Initialize the database
        partnerService.save(partner);

        int databaseSizeBeforeDelete = partnerRepository.findAll().size();

        // Delete the partner
        restPartnerMockMvc.perform(delete("/api/partners/{id}", partner.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Partner in Elasticsearch
        verify(mockPartnerSearchRepository, times(1)).deleteById(partner.getId());
    }

    @Test
    public void searchPartner() throws Exception {
        // Initialize the database
        partnerService.save(partner);
        when(mockPartnerSearchRepository.search(queryStringQuery("id:" + partner.getId())))
            .thenReturn(Collections.singletonList(partner));
        // Search the partner
        restPartnerMockMvc.perform(get("/api/_search/partners?query=id:" + partner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partner.getId())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].cniImage").value(hasItem(DEFAULT_CNI_IMAGE)))
            .andExpect(jsonPath("$.[*].userImage").value(hasItem(DEFAULT_USER_IMAGE)))
            .andExpect(jsonPath("$.[*].references").value(hasItem(DEFAULT_REFERENCES)))
            .andExpect(jsonPath("$.[*].qualification").value(hasItem(DEFAULT_QUALIFICATION)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Partner.class);
        Partner partner1 = new Partner();
        partner1.setId("id1");
        Partner partner2 = new Partner();
        partner2.setId(partner1.getId());
        assertThat(partner1).isEqualTo(partner2);
        partner2.setId("id2");
        assertThat(partner1).isNotEqualTo(partner2);
        partner1.setId(null);
        assertThat(partner1).isNotEqualTo(partner2);
    }
}
