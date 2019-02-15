//package com.afrologix.kahoula.web.rest;
//
//import com.afrologix.kahoula.KahoulaPrestationApp;
//
//import com.afrologix.kahoula.resources.Location.*;
//import com.afrologix.kahoula.web.rest.errors.ExceptionTranslator;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.validation.Validator;
//
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
///**
// * Test class for the LocationResource REST controller.
// *
// * @see LocationResource
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = KahoulaPrestationApp.class)
//public class LocationResourceIntTest {
//
//    private static final String DEFAULT_REGION_NAME = "AAAAAAAAAA";
//    private static final String UPDATED_REGION_NAME = "BBBBBBBBBB";
//
//    private static final String DEFAULT_CITY_NAME = "AAAAAAAAAA";
//    private static final String UPDATED_CITY_NAME = "BBBBBBBBBB";
//
//    @Autowired
//    private LocationRepository locationRepository;
//
//    @Autowired
//    private LocationMapper locationMapper;
//
//    @Autowired
//    private LocationService locationService;
//
//    /**
//     * This repository is mocked in the com.afrologix.kahoula.repository.search test package.
//     *
//     * @see com.afrologix.kahoula.repository.search.LocationSearchRepositoryMockConfiguration
//     */
//    @Autowired
//    private LocationSearchRepository mockLocationSearchRepository;
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
//    private MockMvc restLocationMockMvc;
//
//    private Location location;
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        final LocationResource locationResource = new LocationResource(locationService);
//        this.restLocationMockMvc = MockMvcBuilders.standaloneSetup(locationResource)
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
//    public static Location createEntity() {
//        Location location = new Location()
//            .regionName(DEFAULT_REGION_NAME)
//            .cityName(DEFAULT_CITY_NAME);
//        return location;
//    }
//
//    @Before
//    public void initTest() {
//        locationRepository.deleteAll();
//        location = createEntity();
//    }
//
//    @Test
//    public void createLocation() throws Exception {
//        int databaseSizeBeforeCreate = locationRepository.findAll().size();
//
//        // Create the Location
//        LocationDTO locationDTO = locationMapper.toDto(location);
//        restLocationMockMvc.perform(post("/api/locations")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(locationDTO)))
//            .andExpect(status().isCreated());
//
//        // Validate the Location in the database
//        List<Location> locationList = locationRepository.findAll();
//        assertThat(locationList).hasSize(databaseSizeBeforeCreate + 1);
//        Location testLocation = locationList.get(locationList.size() - 1);
//        assertThat(testLocation.getRegionName()).isEqualTo(DEFAULT_REGION_NAME);
//        assertThat(testLocation.getCityName()).isEqualTo(DEFAULT_CITY_NAME);
//
//        // Validate the Location in Elasticsearch
//        verify(mockLocationSearchRepository, times(1)).save(testLocation);
//    }
//
//    @Test
//    public void createLocationWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = locationRepository.findAll().size();
//
//        // Create the Location with an existing ID
//        location.setId("existing_id");
//        LocationDTO locationDTO = locationMapper.toDto(location);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restLocationMockMvc.perform(post("/api/locations")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(locationDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Location in the database
//        List<Location> locationList = locationRepository.findAll();
//        assertThat(locationList).hasSize(databaseSizeBeforeCreate);
//
//        // Validate the Location in Elasticsearch
//        verify(mockLocationSearchRepository, times(0)).save(location);
//    }
//
//    @Test
//    public void checkRegionNameIsRequired() throws Exception {
//        int databaseSizeBeforeTest = locationRepository.findAll().size();
//        // set the field null
//        location.setRegionName(null);
//
//        // Create the Location, which fails.
//        LocationDTO locationDTO = locationMapper.toDto(location);
//
//        restLocationMockMvc.perform(post("/api/locations")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(locationDTO)))
//            .andExpect(status().isBadRequest());
//
//        List<Location> locationList = locationRepository.findAll();
//        assertThat(locationList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    public void checkCityNameIsRequired() throws Exception {
//        int databaseSizeBeforeTest = locationRepository.findAll().size();
//        // set the field null
//        location.setCityName(null);
//
//        // Create the Location, which fails.
//        LocationDTO locationDTO = locationMapper.toDto(location);
//
//        restLocationMockMvc.perform(post("/api/locations")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(locationDTO)))
//            .andExpect(status().isBadRequest());
//
//        List<Location> locationList = locationRepository.findAll();
//        assertThat(locationList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    public void getAllLocations() throws Exception {
//        // Initialize the database
//        locationRepository.save(location);
//
//        // Get all the locationList
//        restLocationMockMvc.perform(get("/api/locations?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(location.getId())))
//            .andExpect(jsonPath("$.[*].regionName").value(hasItem(DEFAULT_REGION_NAME.toString())))
//            .andExpect(jsonPath("$.[*].cityName").value(hasItem(DEFAULT_CITY_NAME.toString())));
//    }
//
//    @Test
//    public void getLocation() throws Exception {
//        // Initialize the database
//        locationRepository.save(location);
//
//        // Get the location
//        restLocationMockMvc.perform(get("/api/locations/{id}", location.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(location.getId()))
//            .andExpect(jsonPath("$.regionName").value(DEFAULT_REGION_NAME.toString()))
//            .andExpect(jsonPath("$.cityName").value(DEFAULT_CITY_NAME.toString()));
//    }
//
//    @Test
//    public void getNonExistingLocation() throws Exception {
//        // Get the location
//        restLocationMockMvc.perform(get("/api/locations/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void updateLocation() throws Exception {
//        // Initialize the database
//        locationRepository.save(location);
//
//        int databaseSizeBeforeUpdate = locationRepository.findAll().size();
//
//        // Update the location
//        Location updatedLocation = locationRepository.findById(location.getId()).get();
//        updatedLocation
//            .regionName(UPDATED_REGION_NAME)
//            .cityName(UPDATED_CITY_NAME);
//        LocationDTO locationDTO = locationMapper.toDto(updatedLocation);
//
//        restLocationMockMvc.perform(put("/api/locations")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(locationDTO)))
//            .andExpect(status().isOk());
//
//        // Validate the Location in the database
//        List<Location> locationList = locationRepository.findAll();
//        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
//        Location testLocation = locationList.get(locationList.size() - 1);
//        assertThat(testLocation.getRegionName()).isEqualTo(UPDATED_REGION_NAME);
//        assertThat(testLocation.getCityName()).isEqualTo(UPDATED_CITY_NAME);
//
//        // Validate the Location in Elasticsearch
//        verify(mockLocationSearchRepository, times(1)).save(testLocation);
//    }
//
//    @Test
//    public void updateNonExistingLocation() throws Exception {
//        int databaseSizeBeforeUpdate = locationRepository.findAll().size();
//
//        // Create the Location
//        LocationDTO locationDTO = locationMapper.toDto(location);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restLocationMockMvc.perform(put("/api/locations")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(locationDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Location in the database
//        List<Location> locationList = locationRepository.findAll();
//        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
//
//        // Validate the Location in Elasticsearch
//        verify(mockLocationSearchRepository, times(0)).save(location);
//    }
//
//    @Test
//    public void deleteLocation() throws Exception {
//        // Initialize the database
//        locationRepository.save(location);
//
//        int databaseSizeBeforeDelete = locationRepository.findAll().size();
//
//        // Delete the location
//        restLocationMockMvc.perform(delete("/api/locations/{id}", location.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isOk());
//
//        // Validate the database is empty
//        List<Location> locationList = locationRepository.findAll();
//        assertThat(locationList).hasSize(databaseSizeBeforeDelete - 1);
//
//        // Validate the Location in Elasticsearch
//        verify(mockLocationSearchRepository, times(1)).deleteById(location.getId());
//    }
//
//    @Test
//    public void searchLocation() throws Exception {
//        // Initialize the database
//        locationRepository.save(location);
//        when(mockLocationSearchRepository.search(queryStringQuery("id:" + location.getId())))
//            .thenReturn(Collections.singletonList(location));
//        // Search the location
//        restLocationMockMvc.perform(get("/api/_search/locations?query=id:" + location.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(location.getId())))
//            .andExpect(jsonPath("$.[*].regionName").value(hasItem(DEFAULT_REGION_NAME)))
//            .andExpect(jsonPath("$.[*].cityName").value(hasItem(DEFAULT_CITY_NAME)));
//    }
//
//    @Test
//    public void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(Location.class);
//        Location location1 = new Location();
//        location1.setId("id1");
//        Location location2 = new Location();
//        location2.setId(location1.getId());
//        assertThat(location1).isEqualTo(location2);
//        location2.setId("id2");
//        assertThat(location1).isNotEqualTo(location2);
//        location1.setId(null);
//        assertThat(location1).isNotEqualTo(location2);
//    }
//
//    @Test
//    public void dtoEqualsVerifier() throws Exception {
//        TestUtil.equalsVerifier(LocationDTO.class);
//        LocationDTO locationDTO1 = new LocationDTO();
//        locationDTO1.setId("id1");
//        LocationDTO locationDTO2 = new LocationDTO();
//        assertThat(locationDTO1).isNotEqualTo(locationDTO2);
//        locationDTO2.setId(locationDTO1.getId());
//        assertThat(locationDTO1).isEqualTo(locationDTO2);
//        locationDTO2.setId("id2");
//        assertThat(locationDTO1).isNotEqualTo(locationDTO2);
//        locationDTO1.setId(null);
//        assertThat(locationDTO1).isNotEqualTo(locationDTO2);
//    }
//}
