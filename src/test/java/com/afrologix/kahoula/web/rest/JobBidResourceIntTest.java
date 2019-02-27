//package com.afrologix.kahoula.web.rest;
//
//import com.afrologix.kahoula.KahoulaPrestationApp;
//
//import com.afrologix.kahoula.repository.JobBidRepository;
//import com.afrologix.kahoula.resources.JobBid.*;
//import com.afrologix.kahoula.web.rest.errors.ExceptionTranslator;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.validation.Validator;
//
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//
//import static com.afrologix.kahoula.web.rest.TestUtil.createFormattingConversionService;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
//import static org.hamcrest.Matchers.hasItem;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.afrologix.kahoula.resources.enumeration.JobType;
//import com.afrologix.kahoula.resources.enumeration.JobStatus;
///**
// * Test class for the JobBidResource REST controller.
// *
// * @see JobBidResource
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = KahoulaPrestationApp.class)
//public class JobBidResourceIntTest {
//
//    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
//    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";
//
//    private static final JobType DEFAULT_TYPE = JobType.ELECTRICITE;
//    private static final JobType UPDATED_TYPE = JobType.PEINTURE;
//
//    private static final Instant DEFAULT_WISH_DATE = Instant.ofEpochMilli(0L);
//    private static final Instant UPDATED_WISH_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
//
//    private static final String DEFAULT_CUSTOMER_ID = "AAAAAAAAAA";
//    private static final String UPDATED_CUSTOMER_ID = "BBBBBBBBBB";
//
//    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
//    private static final String UPDATED_LOCATION = "BBBBBBBBBB";
//
//    private static final String DEFAULT_PARTNER_ID = "AAAAAAAAAA";
//    private static final String UPDATED_PARTNER_ID = "BBBBBBBBBB";
//
//    private static final Double DEFAULT_AMOUNT = 1D;
//    private static final Double UPDATED_AMOUNT = 2D;
//
//    private static final JobStatus DEFAULT_STATUS = JobStatus.NEW;
//    private static final JobStatus UPDATED_STATUS = JobStatus.INIT;
//
//    @Autowired
//    private JobBidRepository jobBidRepository;
//
//    @Autowired
//    private JobBidMapper jobBidMapper;
//
//    @Autowired
//    private JobBidService jobBidService;
//
//    /**
//     * This repository is mocked in the com.afrologix.kahoula.repository.search test package.
//     *
////     * @see com.afrologix.kahoula.repository.search.JobBidSearchRepositoryMockConfiguration
////     */
////    @Autowired
////    private JobBidSearchRepository mockJobBidSearchRepository;
//
//    @Autowired
//    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
//
//    @Autowired
//    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
//
//    @Autowired
//    private ExceptionTranslator exceptionTranslator;
//
//    @Autowired
//    private Validator validator;
//
//    private MockMvc restJobBidMockMvc;
//
//    private JobBid jobBid;
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        final JobBidResource jobBidResource = new JobBidResource(jobBidService);
//        this.restJobBidMockMvc = MockMvcBuilders.standaloneSetup(jobBidResource)
//            .setCustomArgumentResolvers(pageableArgumentResolver)
//            .setControllerAdvice(exceptionTranslator)
//            .setConversionService(createFormattingConversionService())
//            .setMessageConverters(jacksonMessageConverter)
//            .setValidator(validator).build();
//    }
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static JobBid createEntity() {
//        List<JobType> jobTypeList = Arrays.asList(JobType.ELECTRICITE,JobType.PEINTURE);
//        JobBid jobBid = new JobBid()
//            .description(DEFAULT_DESCRIPTION)
//            .type(jobTypeList)
//            .wishDate(DEFAULT_WISH_DATE)
//            .customerId()
//            .location(DEFAULT_LOCATION)
//            .partnerId(DEFAULT_PARTNER_ID)
//            .amount(DEFAULT_AMOUNT)
//            .status(DEFAULT_STATUS);
//        return jobBid;
//    }
//
//    @Before
//    public void initTest() {
//        jobBidRepository.deleteAll();
//        jobBid = createEntity();
//    }
//
//    @Test
//    public void createJobBid() throws Exception {
//        int databaseSizeBeforeCreate = jobBidRepository.findAll().size();
//
//        // Create the JobBid
//        JobBidDTO jobBidDTO = jobBidMapper.toDto(jobBid);
//        restJobBidMockMvc.perform(post("/api/job-bids")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(jobBidDTO)))
//            .andExpect(status().isCreated());
//
//        // Validate the JobBid in the database
//        List<JobBid> jobBidList = jobBidRepository.findAll();
//        assertThat(jobBidList).hasSize(databaseSizeBeforeCreate + 1);
//        JobBid testJobBid = jobBidList.get(jobBidList.size() - 1);
//        assertThat(testJobBid.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
//        assertThat(testJobBid.getType()).isEqualTo(DEFAULT_TYPE);
//        assertThat(testJobBid.getWishDate()).isEqualTo(DEFAULT_WISH_DATE);
//        assertThat(testJobBid.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
//        assertThat(testJobBid.getLocation()).isEqualTo(DEFAULT_LOCATION);
//        assertThat(testJobBid.getPartnerId()).isEqualTo(DEFAULT_PARTNER_ID);
//        assertThat(testJobBid.getAmount()).isEqualTo(DEFAULT_AMOUNT);
//        assertThat(testJobBid.getStatus()).isEqualTo(DEFAULT_STATUS);
//
//        // Validate the JobBid in Elasticsearch
//        verify(mockJobBidSearchRepository, times(1)).save(testJobBid);
//    }
//
//    @Test
//    public void createJobBidWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = jobBidRepository.findAll().size();
//
//        // Create the JobBid with an existing ID
//        jobBid.setId("existing_id");
//        JobBidDTO jobBidDTO = jobBidMapper.toDto(jobBid);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restJobBidMockMvc.perform(post("/api/job-bids")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(jobBidDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the JobBid in the database
//        List<JobBid> jobBidList = jobBidRepository.findAll();
//        assertThat(jobBidList).hasSize(databaseSizeBeforeCreate);
//
//        // Validate the JobBid in Elasticsearch
//        verify(mockJobBidSearchRepository, times(0)).save(jobBid);
//    }
//
//    @Test
//    public void checkDescriptionIsRequired() throws Exception {
//        int databaseSizeBeforeTest = jobBidRepository.findAll().size();
//        // set the field null
//        jobBid.setDescription(null);
//
//        // Create the JobBid, which fails.
//        JobBidDTO jobBidDTO = jobBidMapper.toDto(jobBid);
//
//        restJobBidMockMvc.perform(post("/api/job-bids")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(jobBidDTO)))
//            .andExpect(status().isBadRequest());
//
//        List<JobBid> jobBidList = jobBidRepository.findAll();
//        assertThat(jobBidList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    public void checkTypeIsRequired() throws Exception {
//        int databaseSizeBeforeTest = jobBidRepository.findAll().size();
//        // set the field null
//        jobBid.setType(null);
//
//        // Create the JobBid, which fails.
//        JobBidDTO jobBidDTO = jobBidMapper.toDto(jobBid);
//
//        restJobBidMockMvc.perform(post("/api/job-bids")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(jobBidDTO)))
//            .andExpect(status().isBadRequest());
//
//        List<JobBid> jobBidList = jobBidRepository.findAll();
//        assertThat(jobBidList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    public void checkCustomerIdIsRequired() throws Exception {
//        int databaseSizeBeforeTest = jobBidRepository.findAll().size();
//        // set the field null
//        jobBid.setCustomerId(null);
//
//        // Create the JobBid, which fails.
//        JobBidDTO jobBidDTO = jobBidMapper.toDto(jobBid);
//
//        restJobBidMockMvc.perform(post("/api/job-bids")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(jobBidDTO)))
//            .andExpect(status().isBadRequest());
//
//        List<JobBid> jobBidList = jobBidRepository.findAll();
//        assertThat(jobBidList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    public void checkLocationIsRequired() throws Exception {
//        int databaseSizeBeforeTest = jobBidRepository.findAll().size();
//        // set the field null
//        jobBid.setLocation(null);
//
//        // Create the JobBid, which fails.
//        JobBidDTO jobBidDTO = jobBidMapper.toDto(jobBid);
//
//        restJobBidMockMvc.perform(post("/api/job-bids")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(jobBidDTO)))
//            .andExpect(status().isBadRequest());
//
//        List<JobBid> jobBidList = jobBidRepository.findAll();
//        assertThat(jobBidList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    public void checkPartnerIdIsRequired() throws Exception {
//        int databaseSizeBeforeTest = jobBidRepository.findAll().size();
//        // set the field null
//        jobBid.setPartnerId(null);
//
//        // Create the JobBid, which fails.
//        JobBidDTO jobBidDTO = jobBidMapper.toDto(jobBid);
//
//        restJobBidMockMvc.perform(post("/api/job-bids")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(jobBidDTO)))
//            .andExpect(status().isBadRequest());
//
//        List<JobBid> jobBidList = jobBidRepository.findAll();
//        assertThat(jobBidList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    public void checkAmountIsRequired() throws Exception {
//        int databaseSizeBeforeTest = jobBidRepository.findAll().size();
//        // set the field null
//        jobBid.setAmount(null);
//
//        // Create the JobBid, which fails.
//        JobBidDTO jobBidDTO = jobBidMapper.toDto(jobBid);
//
//        restJobBidMockMvc.perform(post("/api/job-bids")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(jobBidDTO)))
//            .andExpect(status().isBadRequest());
//
//        List<JobBid> jobBidList = jobBidRepository.findAll();
//        assertThat(jobBidList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    public void getAllJobBids() throws Exception {
//        // Initialize the database
//        jobBidRepository.save(jobBid);
//
//        // Get all the jobBidList
//        restJobBidMockMvc.perform(get("/api/job-bids?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(jobBid.getId())))
//            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
//            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
//            .andExpect(jsonPath("$.[*].wishDate").value(hasItem(DEFAULT_WISH_DATE.toString())))
//            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.toString())))
//            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
//            .andExpect(jsonPath("$.[*].partnerId").value(hasItem(DEFAULT_PARTNER_ID.toString())))
//            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
//            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
//    }
//
//    @Test
//    public void getJobBid() throws Exception {
//        // Initialize the database
//        jobBidRepository.save(jobBid);
//
//        // Get the jobBid
//        restJobBidMockMvc.perform(get("/api/job-bids/{id}", jobBid.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(jobBid.getId()))
//            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
//            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
//            .andExpect(jsonPath("$.wishDate").value(DEFAULT_WISH_DATE.toString()))
//            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.toString()))
//            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
//            .andExpect(jsonPath("$.partnerId").value(DEFAULT_PARTNER_ID.toString()))
//            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
//            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
//    }
//
//    @Test
//    public void getNonExistingJobBid() throws Exception {
//        // Get the jobBid
//        restJobBidMockMvc.perform(get("/api/job-bids/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void updateJobBid() throws Exception {
//        // Initialize the database
//        jobBidRepository.save(jobBid);
//
//        int databaseSizeBeforeUpdate = jobBidRepository.findAll().size();
//
//        // Update the jobBid
//        JobBid updatedJobBid = jobBidRepository.findById(jobBid.getId()).get();
//        updatedJobBid
//            .description(UPDATED_DESCRIPTION)
//            .type(UPDATED_TYPE)
//            .wishDate(UPDATED_WISH_DATE)
//            .customerId(UPDATED_CUSTOMER_ID)
//            .location(UPDATED_LOCATION)
//            .partnerId(UPDATED_PARTNER_ID)
//            .amount(UPDATED_AMOUNT)
//            .status(UPDATED_STATUS);
//        JobBidDTO jobBidDTO = jobBidMapper.toDto(updatedJobBid);
//
//        restJobBidMockMvc.perform(put("/api/job-bids")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(jobBidDTO)))
//            .andExpect(status().isOk());
//
//        // Validate the JobBid in the database
//        List<JobBid> jobBidList = jobBidRepository.findAll();
//        assertThat(jobBidList).hasSize(databaseSizeBeforeUpdate);
//        JobBid testJobBid = jobBidList.get(jobBidList.size() - 1);
//        assertThat(testJobBid.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
//        assertThat(testJobBid.getType()).isEqualTo(UPDATED_TYPE);
//        assertThat(testJobBid.getWishDate()).isEqualTo(UPDATED_WISH_DATE);
//        assertThat(testJobBid.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
//        assertThat(testJobBid.getLocation()).isEqualTo(UPDATED_LOCATION);
//        assertThat(testJobBid.getPartnerId()).isEqualTo(UPDATED_PARTNER_ID);
//        assertThat(testJobBid.getAmount()).isEqualTo(UPDATED_AMOUNT);
//        assertThat(testJobBid.getStatus()).isEqualTo(UPDATED_STATUS);
//
//        // Validate the JobBid in Elasticsearch
//        verify(mockJobBidSearchRepository, times(1)).save(testJobBid);
//    }
//
//    @Test
//    public void updateNonExistingJobBid() throws Exception {
//        int databaseSizeBeforeUpdate = jobBidRepository.findAll().size();
//
//        // Create the JobBid
//        JobBidDTO jobBidDTO = jobBidMapper.toDto(jobBid);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restJobBidMockMvc.perform(put("/api/job-bids")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(jobBidDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the JobBid in the database
//        List<JobBid> jobBidList = jobBidRepository.findAll();
//        assertThat(jobBidList).hasSize(databaseSizeBeforeUpdate);
//
//        // Validate the JobBid in Elasticsearch
//        verify(mockJobBidSearchRepository, times(0)).save(jobBid);
//    }
//
//    @Test
//    public void deleteJobBid() throws Exception {
//        // Initialize the database
//        jobBidRepository.save(jobBid);
//
//        int databaseSizeBeforeDelete = jobBidRepository.findAll().size();
//
//        // Delete the jobBid
//        restJobBidMockMvc.perform(delete("/api/job-bids/{id}", jobBid.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isOk());
//
//        // Validate the database is empty
//        List<JobBid> jobBidList = jobBidRepository.findAll();
//        assertThat(jobBidList).hasSize(databaseSizeBeforeDelete - 1);
//
//        // Validate the JobBid in Elasticsearch
//        verify(mockJobBidSearchRepository, times(1)).deleteById(jobBid.getId());
//    }
//
//    @Test
//    public void searchJobBid() throws Exception {
//        // Initialize the database
//        jobBidRepository.save(jobBid);
//        when(mockJobBidSearchRepository.search(queryStringQuery("id:" + jobBid.getId()), PageRequest.of(0, 20)))
//            .thenReturn(new PageImpl<>(Collections.singletonList(jobBid), PageRequest.of(0, 1), 1));
//        // Search the jobBid
//        restJobBidMockMvc.perform(get("/api/_search/job-bids?query=id:" + jobBid.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(jobBid.getId())))
//            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
//            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
//            .andExpect(jsonPath("$.[*].wishDate").value(hasItem(DEFAULT_WISH_DATE.toString())))
//            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
//            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
//            .andExpect(jsonPath("$.[*].partnerId").value(hasItem(DEFAULT_PARTNER_ID)))
//            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
//            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
//    }
//
//    @Test
//    public void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(JobBid.class);
//        JobBid jobBid1 = new JobBid();
//        jobBid1.setId("id1");
//        JobBid jobBid2 = new JobBid();
//        jobBid2.setId(jobBid1.getId());
//        assertThat(jobBid1).isEqualTo(jobBid2);
//        jobBid2.setId("id2");
//        assertThat(jobBid1).isNotEqualTo(jobBid2);
//        jobBid1.setId(null);
//        assertThat(jobBid1).isNotEqualTo(jobBid2);
//    }
//
//    @Test
//    public void dtoEqualsVerifier() throws Exception {
//        TestUtil.equalsVerifier(JobBidDTO.class);
//        JobBidDTO jobBidDTO1 = new JobBidDTO();
//        jobBidDTO1.setId("id1");
//        JobBidDTO jobBidDTO2 = new JobBidDTO();
//        assertThat(jobBidDTO1).isNotEqualTo(jobBidDTO2);
//        jobBidDTO2.setId(jobBidDTO1.getId());
//        assertThat(jobBidDTO1).isEqualTo(jobBidDTO2);
//        jobBidDTO2.setId("id2");
//        assertThat(jobBidDTO1).isNotEqualTo(jobBidDTO2);
//        jobBidDTO1.setId(null);
//        assertThat(jobBidDTO1).isNotEqualTo(jobBidDTO2);
//    }
//}
