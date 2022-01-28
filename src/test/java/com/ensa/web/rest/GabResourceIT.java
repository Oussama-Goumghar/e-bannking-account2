package com.ensa.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ensa.IntegrationTest;
import com.ensa.domain.Gab;
import com.ensa.repository.GabRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GabResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GabResourceIT {

    private static final Double DEFAULT_FOND = 1D;
    private static final Double UPDATED_FOND = 2D;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/gabs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GabRepository gabRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGabMockMvc;

    private Gab gab;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gab createEntity(EntityManager em) {
        Gab gab = new Gab().fond(DEFAULT_FOND).address(DEFAULT_ADDRESS);
        return gab;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gab createUpdatedEntity(EntityManager em) {
        Gab gab = new Gab().fond(UPDATED_FOND).address(UPDATED_ADDRESS);
        return gab;
    }

    @BeforeEach
    public void initTest() {
        gab = createEntity(em);
    }

    @Test
    @Transactional
    void createGab() throws Exception {
        int databaseSizeBeforeCreate = gabRepository.findAll().size();
        // Create the Gab
        restGabMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gab)))
            .andExpect(status().isCreated());

        // Validate the Gab in the database
        List<Gab> gabList = gabRepository.findAll();
        assertThat(gabList).hasSize(databaseSizeBeforeCreate + 1);
        Gab testGab = gabList.get(gabList.size() - 1);
        assertThat(testGab.getFond()).isEqualTo(DEFAULT_FOND);
        assertThat(testGab.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    void createGabWithExistingId() throws Exception {
        // Create the Gab with an existing ID
        gab.setId(1L);

        int databaseSizeBeforeCreate = gabRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGabMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gab)))
            .andExpect(status().isBadRequest());

        // Validate the Gab in the database
        List<Gab> gabList = gabRepository.findAll();
        assertThat(gabList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGabs() throws Exception {
        // Initialize the database
        gabRepository.saveAndFlush(gab);

        // Get all the gabList
        restGabMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gab.getId().intValue())))
            .andExpect(jsonPath("$.[*].fond").value(hasItem(DEFAULT_FOND.doubleValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }

    @Test
    @Transactional
    void getGab() throws Exception {
        // Initialize the database
        gabRepository.saveAndFlush(gab);

        // Get the gab
        restGabMockMvc
            .perform(get(ENTITY_API_URL_ID, gab.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gab.getId().intValue()))
            .andExpect(jsonPath("$.fond").value(DEFAULT_FOND.doubleValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }

    @Test
    @Transactional
    void getNonExistingGab() throws Exception {
        // Get the gab
        restGabMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGab() throws Exception {
        // Initialize the database
        gabRepository.saveAndFlush(gab);

        int databaseSizeBeforeUpdate = gabRepository.findAll().size();

        // Update the gab
        Gab updatedGab = gabRepository.findById(gab.getId()).get();
        // Disconnect from session so that the updates on updatedGab are not directly saved in db
        em.detach(updatedGab);
        updatedGab.fond(UPDATED_FOND).address(UPDATED_ADDRESS);

        restGabMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGab.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGab))
            )
            .andExpect(status().isOk());

        // Validate the Gab in the database
        List<Gab> gabList = gabRepository.findAll();
        assertThat(gabList).hasSize(databaseSizeBeforeUpdate);
        Gab testGab = gabList.get(gabList.size() - 1);
        assertThat(testGab.getFond()).isEqualTo(UPDATED_FOND);
        assertThat(testGab.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void putNonExistingGab() throws Exception {
        int databaseSizeBeforeUpdate = gabRepository.findAll().size();
        gab.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGabMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gab.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gab))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gab in the database
        List<Gab> gabList = gabRepository.findAll();
        assertThat(gabList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGab() throws Exception {
        int databaseSizeBeforeUpdate = gabRepository.findAll().size();
        gab.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGabMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gab))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gab in the database
        List<Gab> gabList = gabRepository.findAll();
        assertThat(gabList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGab() throws Exception {
        int databaseSizeBeforeUpdate = gabRepository.findAll().size();
        gab.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGabMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gab)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gab in the database
        List<Gab> gabList = gabRepository.findAll();
        assertThat(gabList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGabWithPatch() throws Exception {
        // Initialize the database
        gabRepository.saveAndFlush(gab);

        int databaseSizeBeforeUpdate = gabRepository.findAll().size();

        // Update the gab using partial update
        Gab partialUpdatedGab = new Gab();
        partialUpdatedGab.setId(gab.getId());

        restGabMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGab.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGab))
            )
            .andExpect(status().isOk());

        // Validate the Gab in the database
        List<Gab> gabList = gabRepository.findAll();
        assertThat(gabList).hasSize(databaseSizeBeforeUpdate);
        Gab testGab = gabList.get(gabList.size() - 1);
        assertThat(testGab.getFond()).isEqualTo(DEFAULT_FOND);
        assertThat(testGab.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    void fullUpdateGabWithPatch() throws Exception {
        // Initialize the database
        gabRepository.saveAndFlush(gab);

        int databaseSizeBeforeUpdate = gabRepository.findAll().size();

        // Update the gab using partial update
        Gab partialUpdatedGab = new Gab();
        partialUpdatedGab.setId(gab.getId());

        partialUpdatedGab.fond(UPDATED_FOND).address(UPDATED_ADDRESS);

        restGabMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGab.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGab))
            )
            .andExpect(status().isOk());

        // Validate the Gab in the database
        List<Gab> gabList = gabRepository.findAll();
        assertThat(gabList).hasSize(databaseSizeBeforeUpdate);
        Gab testGab = gabList.get(gabList.size() - 1);
        assertThat(testGab.getFond()).isEqualTo(UPDATED_FOND);
        assertThat(testGab.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void patchNonExistingGab() throws Exception {
        int databaseSizeBeforeUpdate = gabRepository.findAll().size();
        gab.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGabMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gab.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gab))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gab in the database
        List<Gab> gabList = gabRepository.findAll();
        assertThat(gabList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGab() throws Exception {
        int databaseSizeBeforeUpdate = gabRepository.findAll().size();
        gab.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGabMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gab))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gab in the database
        List<Gab> gabList = gabRepository.findAll();
        assertThat(gabList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGab() throws Exception {
        int databaseSizeBeforeUpdate = gabRepository.findAll().size();
        gab.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGabMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(gab)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gab in the database
        List<Gab> gabList = gabRepository.findAll();
        assertThat(gabList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGab() throws Exception {
        // Initialize the database
        gabRepository.saveAndFlush(gab);

        int databaseSizeBeforeDelete = gabRepository.findAll().size();

        // Delete the gab
        restGabMockMvc.perform(delete(ENTITY_API_URL_ID, gab.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Gab> gabList = gabRepository.findAll();
        assertThat(gabList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
