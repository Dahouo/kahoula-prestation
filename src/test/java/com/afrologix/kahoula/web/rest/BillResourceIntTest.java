package com.afrologix.kahoula.web.rest;

import com.afrologix.kahoula.KahoulaPrestationApp;

import com.afrologix.kahoula.repository.BillRepository;
import com.afrologix.kahoula.repository.search.BillSearchRepository;
import com.afrologix.kahoula.resources.Bill.*;
import com.afrologix.kahoula.resources.Bill.BillService;
import com.afrologix.kahoula.resources.JobBid.JobBid;
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

import com.afrologix.kahoula.resources.enumeration.BillStatus;
/**
 * Test class for the BillResource REST controller.
 *
 * @see BillResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KahoulaPrestationApp.class)
public class BillResourceIntTest {

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;

    private static final Double DEFAULT_UNIT_PRICE = 1D;
    private static final Double UPDATED_UNIT_PRICE = 2D;

    private static final String DEFAULT_JOBID = "AAAAAAAAAA";
    private static final String UPDATED_JOBID = "BBBBBBBBBB";

    private static final BillStatus DEFAULT_STATUS = BillStatus.NEW;
    private static final BillStatus UPDATED_STATUS = BillStatus.VALIDATION;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private BillService billService;

    /**
     * This repository is mocked in the com.afrologix.kahoula.repository.search test package.
     *
     * @see com.afrologix.kahoula.repository.search.BillSearchRepositoryMockConfiguration
     */
    @Autowired
    private BillSearchRepository mockBillSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restBillMockMvc;

    private Bill bill;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BillResource billResource = new BillResource(billService);
        this.restBillMockMvc = MockMvcBuilders.standaloneSetup(billResource)
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
    public static Bill createEntity() {
        Bill bill = new Bill()
            .designation(DEFAULT_DESIGNATION)
            .quantity(DEFAULT_QUANTITY)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .jobid(new JobBid())
            .status(DEFAULT_STATUS);
        return bill;
    }

    @Before
    public void initTest() {
        billRepository.deleteAll();
        bill = createEntity();
    }

    @Test
    public void createBill() throws Exception {
        int databaseSizeBeforeCreate = billRepository.findAll().size();

        // Create the Bill
        BillDTO billDTO = billMapper.toDto(bill);
        restBillMockMvc.perform(post("/api/bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billDTO)))
            .andExpect(status().isCreated());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeCreate + 1);
        Bill testBill = billList.get(billList.size() - 1);
        assertThat(testBill.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testBill.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testBill.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testBill.getJobid()).isEqualTo(DEFAULT_JOBID);
        assertThat(testBill.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the Bill in Elasticsearch
        verify(mockBillSearchRepository, times(1)).save(testBill);
    }

    @Test
    public void createBillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = billRepository.findAll().size();

        // Create the Bill with an existing ID
        bill.setId("existing_id");
        BillDTO billDTO = billMapper.toDto(bill);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillMockMvc.perform(post("/api/bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeCreate);

        // Validate the Bill in Elasticsearch
        verify(mockBillSearchRepository, times(0)).save(bill);
    }

    @Test
    public void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = billRepository.findAll().size();
        // set the field null
        bill.setDesignation(null);

        // Create the Bill, which fails.
        BillDTO billDTO = billMapper.toDto(bill);

        restBillMockMvc.perform(post("/api/bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billDTO)))
            .andExpect(status().isBadRequest());

        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = billRepository.findAll().size();
        // set the field null
        bill.setQuantity(null);

        // Create the Bill, which fails.
        BillDTO billDTO = billMapper.toDto(bill);

        restBillMockMvc.perform(post("/api/bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billDTO)))
            .andExpect(status().isBadRequest());

        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkJobidIsRequired() throws Exception {
        int databaseSizeBeforeTest = billRepository.findAll().size();
        // set the field null
        bill.setJobid(null);

        // Create the Bill, which fails.
        BillDTO billDTO = billMapper.toDto(bill);

        restBillMockMvc.perform(post("/api/bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billDTO)))
            .andExpect(status().isBadRequest());

        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllBills() throws Exception {
        // Initialize the database
        billRepository.save(bill);

        // Get all the billList
        restBillMockMvc.perform(get("/api/bills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bill.getId())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].jobid").value(hasItem(DEFAULT_JOBID.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    public void getBill() throws Exception {
        // Initialize the database
        billRepository.save(bill);

        // Get the bill
        restBillMockMvc.perform(get("/api/bills/{id}", bill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bill.getId()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.jobid").value(DEFAULT_JOBID.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    public void getNonExistingBill() throws Exception {
        // Get the bill
        restBillMockMvc.perform(get("/api/bills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateBill() throws Exception {
        // Initialize the database
        billRepository.save(bill);

        int databaseSizeBeforeUpdate = billRepository.findAll().size();

        // Update the bill
        Bill updatedBill = billRepository.findById(bill.getId()).get();
        updatedBill
            .designation(UPDATED_DESIGNATION)
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .jobid(new JobBid())
            .status(UPDATED_STATUS);
        BillDTO billDTO = billMapper.toDto(updatedBill);

        restBillMockMvc.perform(put("/api/bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billDTO)))
            .andExpect(status().isOk());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeUpdate);
        Bill testBill = billList.get(billList.size() - 1);
        assertThat(testBill.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testBill.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testBill.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testBill.getJobid()).isEqualTo(UPDATED_JOBID);
        assertThat(testBill.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the Bill in Elasticsearch
        verify(mockBillSearchRepository, times(1)).save(testBill);
    }

    @Test
    public void updateNonExistingBill() throws Exception {
        int databaseSizeBeforeUpdate = billRepository.findAll().size();

        // Create the Bill
        BillDTO billDTO = billMapper.toDto(bill);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillMockMvc.perform(put("/api/bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Bill in Elasticsearch
        verify(mockBillSearchRepository, times(0)).save(bill);
    }

    @Test
    public void deleteBill() throws Exception {
        // Initialize the database
        billRepository.save(bill);

        int databaseSizeBeforeDelete = billRepository.findAll().size();

        // Delete the bill
        restBillMockMvc.perform(delete("/api/bills/{id}", bill.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Bill in Elasticsearch
        verify(mockBillSearchRepository, times(1)).deleteById(bill.getId());
    }

    @Test
    public void searchBill() throws Exception {
        // Initialize the database
        billRepository.save(bill);
        when(mockBillSearchRepository.search(queryStringQuery("id:" + bill.getId())))
            .thenReturn(Collections.singletonList(bill));
        // Search the bill
        restBillMockMvc.perform(get("/api/_search/bills?query=id:" + bill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bill.getId())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].jobid").value(hasItem(DEFAULT_JOBID)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bill.class);
        Bill bill1 = new Bill();
        bill1.setId("id1");
        Bill bill2 = new Bill();
        bill2.setId(bill1.getId());
        assertThat(bill1).isEqualTo(bill2);
        bill2.setId("id2");
        assertThat(bill1).isNotEqualTo(bill2);
        bill1.setId(null);
        assertThat(bill1).isNotEqualTo(bill2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillDTO.class);
        BillDTO billDTO1 = new BillDTO();
        billDTO1.setId("id1");
        BillDTO billDTO2 = new BillDTO();
        assertThat(billDTO1).isNotEqualTo(billDTO2);
        billDTO2.setId(billDTO1.getId());
        assertThat(billDTO1).isEqualTo(billDTO2);
        billDTO2.setId("id2");
        assertThat(billDTO1).isNotEqualTo(billDTO2);
        billDTO1.setId(null);
        assertThat(billDTO1).isNotEqualTo(billDTO2);
    }
}
