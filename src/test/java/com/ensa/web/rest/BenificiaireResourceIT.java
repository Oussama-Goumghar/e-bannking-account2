package com.ensa.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ensa.IntegrationTest;
import com.ensa.domain.Benificiaire;
import com.ensa.repository.BenificiaireRepository;
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
 * Integration tests for the {@link BenificiaireResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BenificiaireResourceIT {

//    private static final String ENTITY_API_URL = "/api/benificiaires";
//    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
//
//    private static Random random = new Random();
//    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    @Autowired
//    private BenificiaireRepository benificiaireRepository;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restBenificiaireMockMvc;
//
//    private Benificiaire benificiaire;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Benificiaire createEntity(EntityManager em) {
//        Benificiaire benificiaire = new Benificiaire();
//        return benificiaire;
//    }
//
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Benificiaire createUpdatedEntity(EntityManager em) {
//        Benificiaire benificiaire = new Benificiaire();
//        return benificiaire;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        benificiaire = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    void createBenificiaire() throws Exception {
//        int databaseSizeBeforeCreate = benificiaireRepository.findAll().size();
//        // Create the Benificiaire
//        restBenificiaireMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benificiaire)))
//            .andExpect(status().isCreated());
//
//        // Validate the Benificiaire in the database
//        List<Benificiaire> benificiaireList = benificiaireRepository.findAll();
//        assertThat(benificiaireList).hasSize(databaseSizeBeforeCreate + 1);
//        Benificiaire testBenificiaire = benificiaireList.get(benificiaireList.size() - 1);
//    }
//
//    @Test
//    @Transactional
//    void createBenificiaireWithExistingId() throws Exception {
//        // Create the Benificiaire with an existing ID
//        benificiaire.setId(1L);
//
//        int databaseSizeBeforeCreate = benificiaireRepository.findAll().size();
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restBenificiaireMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benificiaire)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Benificiaire in the database
//        List<Benificiaire> benificiaireList = benificiaireRepository.findAll();
//        assertThat(benificiaireList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    void getAllBenificiaires() throws Exception {
//        // Initialize the database
//        benificiaireRepository.saveAndFlush(benificiaire);
//
//        // Get all the benificiaireList
//        restBenificiaireMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(benificiaire.getId().intValue())));
//    }
//
//    @Test
//    @Transactional
//    void getBenificiaire() throws Exception {
//        // Initialize the database
//        benificiaireRepository.saveAndFlush(benificiaire);
//
//        // Get the benificiaire
//        restBenificiaireMockMvc
//            .perform(get(ENTITY_API_URL_ID, benificiaire.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(benificiaire.getId().intValue()));
//    }
//
//    @Test
//    @Transactional
//    void getNonExistingBenificiaire() throws Exception {
//        // Get the benificiaire
//        restBenificiaireMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    void putNewBenificiaire() throws Exception {
//        // Initialize the database
//        benificiaireRepository.saveAndFlush(benificiaire);
//
//        int databaseSizeBeforeUpdate = benificiaireRepository.findAll().size();
//
//        // Update the benificiaire
//        Benificiaire updatedBenificiaire = benificiaireRepository.findById(benificiaire.getId()).get();
//        // Disconnect from session so that the updates on updatedBenificiaire are not directly saved in db
//        em.detach(updatedBenificiaire);
//
//        restBenificiaireMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, updatedBenificiaire.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(updatedBenificiaire))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Benificiaire in the database
//        List<Benificiaire> benificiaireList = benificiaireRepository.findAll();
//        assertThat(benificiaireList).hasSize(databaseSizeBeforeUpdate);
//        Benificiaire testBenificiaire = benificiaireList.get(benificiaireList.size() - 1);
//    }
//
//    @Test
//    @Transactional
//    void putNonExistingBenificiaire() throws Exception {
//        int databaseSizeBeforeUpdate = benificiaireRepository.findAll().size();
//        benificiaire.setId(count.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restBenificiaireMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, benificiaire.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(benificiaire))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Benificiaire in the database
//        List<Benificiaire> benificiaireList = benificiaireRepository.findAll();
//        assertThat(benificiaireList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithIdMismatchBenificiaire() throws Exception {
//        int databaseSizeBeforeUpdate = benificiaireRepository.findAll().size();
//        benificiaire.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restBenificiaireMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(benificiaire))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Benificiaire in the database
//        List<Benificiaire> benificiaireList = benificiaireRepository.findAll();
//        assertThat(benificiaireList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithMissingIdPathParamBenificiaire() throws Exception {
//        int databaseSizeBeforeUpdate = benificiaireRepository.findAll().size();
//        benificiaire.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restBenificiaireMockMvc
//            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benificiaire)))
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the Benificiaire in the database
//        List<Benificiaire> benificiaireList = benificiaireRepository.findAll();
//        assertThat(benificiaireList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void partialUpdateBenificiaireWithPatch() throws Exception {
//        // Initialize the database
//        benificiaireRepository.saveAndFlush(benificiaire);
//
//        int databaseSizeBeforeUpdate = benificiaireRepository.findAll().size();
//
//        // Update the benificiaire using partial update
//        Benificiaire partialUpdatedBenificiaire = new Benificiaire();
//        partialUpdatedBenificiaire.setId(benificiaire.getId());
//
//        restBenificiaireMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedBenificiaire.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBenificiaire))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Benificiaire in the database
//        List<Benificiaire> benificiaireList = benificiaireRepository.findAll();
//        assertThat(benificiaireList).hasSize(databaseSizeBeforeUpdate);
//        Benificiaire testBenificiaire = benificiaireList.get(benificiaireList.size() - 1);
//    }
//
//    @Test
//    @Transactional
//    void fullUpdateBenificiaireWithPatch() throws Exception {
//        // Initialize the database
//        benificiaireRepository.saveAndFlush(benificiaire);
//
//        int databaseSizeBeforeUpdate = benificiaireRepository.findAll().size();
//
//        // Update the benificiaire using partial update
//        Benificiaire partialUpdatedBenificiaire = new Benificiaire();
//        partialUpdatedBenificiaire.setId(benificiaire.getId());
//
//        restBenificiaireMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedBenificiaire.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBenificiaire))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Benificiaire in the database
//        List<Benificiaire> benificiaireList = benificiaireRepository.findAll();
//        assertThat(benificiaireList).hasSize(databaseSizeBeforeUpdate);
//        Benificiaire testBenificiaire = benificiaireList.get(benificiaireList.size() - 1);
//    }
//
//    @Test
//    @Transactional
//    void patchNonExistingBenificiaire() throws Exception {
//        int databaseSizeBeforeUpdate = benificiaireRepository.findAll().size();
//        benificiaire.setId(count.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restBenificiaireMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, benificiaire.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(benificiaire))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Benificiaire in the database
//        List<Benificiaire> benificiaireList = benificiaireRepository.findAll();
//        assertThat(benificiaireList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithIdMismatchBenificiaire() throws Exception {
//        int databaseSizeBeforeUpdate = benificiaireRepository.findAll().size();
//        benificiaire.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restBenificiaireMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(benificiaire))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Benificiaire in the database
//        List<Benificiaire> benificiaireList = benificiaireRepository.findAll();
//        assertThat(benificiaireList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithMissingIdPathParamBenificiaire() throws Exception {
//        int databaseSizeBeforeUpdate = benificiaireRepository.findAll().size();
//        benificiaire.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restBenificiaireMockMvc
//            .perform(
//                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(benificiaire))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the Benificiaire in the database
//        List<Benificiaire> benificiaireList = benificiaireRepository.findAll();
//        assertThat(benificiaireList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void deleteBenificiaire() throws Exception {
//        // Initialize the database
//        benificiaireRepository.saveAndFlush(benificiaire);
//
//        int databaseSizeBeforeDelete = benificiaireRepository.findAll().size();
//
//        // Delete the benificiaire
//        restBenificiaireMockMvc
//            .perform(delete(ENTITY_API_URL_ID, benificiaire.getId()).accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Benificiaire> benificiaireList = benificiaireRepository.findAll();
//        assertThat(benificiaireList).hasSize(databaseSizeBeforeDelete - 1);
//    }
}
