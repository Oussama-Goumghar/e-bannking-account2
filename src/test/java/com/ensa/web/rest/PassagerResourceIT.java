package com.ensa.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ensa.IntegrationTest;
import com.ensa.domain.Passager;
import com.ensa.repository.PassagerRepository;
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
 * Integration tests for the {@link PassagerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PassagerResourceIT {

    private static final String ENTITY_API_URL = "/api/passagers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PassagerRepository passagerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPassagerMockMvc;

    private Passager passager;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Passager createEntity(EntityManager em) {
        Passager passager = new Passager();
        return passager;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Passager createUpdatedEntity(EntityManager em) {
        Passager passager = new Passager();
        return passager;
    }

    @BeforeEach
    public void initTest() {
        passager = createEntity(em);
    }

    @Test
    @Transactional
    void createPassager() throws Exception {
        int databaseSizeBeforeCreate = passagerRepository.findAll().size();
        // Create the Passager
        restPassagerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passager)))
            .andExpect(status().isCreated());

        // Validate the Passager in the database
        List<Passager> passagerList = passagerRepository.findAll();
        assertThat(passagerList).hasSize(databaseSizeBeforeCreate + 1);
        Passager testPassager = passagerList.get(passagerList.size() - 1);
    }

    @Test
    @Transactional
    void createPassagerWithExistingId() throws Exception {
        // Create the Passager with an existing ID
        passager.setId(1L);

        int databaseSizeBeforeCreate = passagerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPassagerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passager)))
            .andExpect(status().isBadRequest());

        // Validate the Passager in the database
        List<Passager> passagerList = passagerRepository.findAll();
        assertThat(passagerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPassagers() throws Exception {
        // Initialize the database
        passagerRepository.saveAndFlush(passager);

        // Get all the passagerList
        restPassagerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(passager.getId().intValue())));
    }

    @Test
    @Transactional
    void getPassager() throws Exception {
        // Initialize the database
        passagerRepository.saveAndFlush(passager);

        // Get the passager
        restPassagerMockMvc
            .perform(get(ENTITY_API_URL_ID, passager.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(passager.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingPassager() throws Exception {
        // Get the passager
        restPassagerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPassager() throws Exception {
        // Initialize the database
        passagerRepository.saveAndFlush(passager);

        int databaseSizeBeforeUpdate = passagerRepository.findAll().size();

        // Update the passager
        Passager updatedPassager = passagerRepository.findById(passager.getId()).get();
        // Disconnect from session so that the updates on updatedPassager are not directly saved in db
        em.detach(updatedPassager);

        restPassagerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPassager.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPassager))
            )
            .andExpect(status().isOk());

        // Validate the Passager in the database
        List<Passager> passagerList = passagerRepository.findAll();
        assertThat(passagerList).hasSize(databaseSizeBeforeUpdate);
        Passager testPassager = passagerList.get(passagerList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingPassager() throws Exception {
        int databaseSizeBeforeUpdate = passagerRepository.findAll().size();
        passager.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPassagerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, passager.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(passager))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passager in the database
        List<Passager> passagerList = passagerRepository.findAll();
        assertThat(passagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPassager() throws Exception {
        int databaseSizeBeforeUpdate = passagerRepository.findAll().size();
        passager.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPassagerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(passager))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passager in the database
        List<Passager> passagerList = passagerRepository.findAll();
        assertThat(passagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPassager() throws Exception {
        int databaseSizeBeforeUpdate = passagerRepository.findAll().size();
        passager.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPassagerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passager)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Passager in the database
        List<Passager> passagerList = passagerRepository.findAll();
        assertThat(passagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePassagerWithPatch() throws Exception {
        // Initialize the database
        passagerRepository.saveAndFlush(passager);

        int databaseSizeBeforeUpdate = passagerRepository.findAll().size();

        // Update the passager using partial update
        Passager partialUpdatedPassager = new Passager();
        partialUpdatedPassager.setId(passager.getId());

        restPassagerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPassager.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPassager))
            )
            .andExpect(status().isOk());

        // Validate the Passager in the database
        List<Passager> passagerList = passagerRepository.findAll();
        assertThat(passagerList).hasSize(databaseSizeBeforeUpdate);
        Passager testPassager = passagerList.get(passagerList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdatePassagerWithPatch() throws Exception {
        // Initialize the database
        passagerRepository.saveAndFlush(passager);

        int databaseSizeBeforeUpdate = passagerRepository.findAll().size();

        // Update the passager using partial update
        Passager partialUpdatedPassager = new Passager();
        partialUpdatedPassager.setId(passager.getId());

        restPassagerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPassager.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPassager))
            )
            .andExpect(status().isOk());

        // Validate the Passager in the database
        List<Passager> passagerList = passagerRepository.findAll();
        assertThat(passagerList).hasSize(databaseSizeBeforeUpdate);
        Passager testPassager = passagerList.get(passagerList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingPassager() throws Exception {
        int databaseSizeBeforeUpdate = passagerRepository.findAll().size();
        passager.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPassagerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, passager.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(passager))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passager in the database
        List<Passager> passagerList = passagerRepository.findAll();
        assertThat(passagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPassager() throws Exception {
        int databaseSizeBeforeUpdate = passagerRepository.findAll().size();
        passager.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPassagerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(passager))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passager in the database
        List<Passager> passagerList = passagerRepository.findAll();
        assertThat(passagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPassager() throws Exception {
        int databaseSizeBeforeUpdate = passagerRepository.findAll().size();
        passager.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPassagerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(passager)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Passager in the database
        List<Passager> passagerList = passagerRepository.findAll();
        assertThat(passagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePassager() throws Exception {
        // Initialize the database
        passagerRepository.saveAndFlush(passager);

        int databaseSizeBeforeDelete = passagerRepository.findAll().size();

        // Delete the passager
        restPassagerMockMvc
            .perform(delete(ENTITY_API_URL_ID, passager.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Passager> passagerList = passagerRepository.findAll();
        assertThat(passagerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
