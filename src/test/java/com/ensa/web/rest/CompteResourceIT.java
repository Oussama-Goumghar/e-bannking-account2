package com.ensa.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ensa.IntegrationTest;
import com.ensa.domain.Compte;
import com.ensa.repository.CompteRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link CompteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompteResourceIT {

//    private static final Double DEFAULT_SOLDE = 1D;
//    private static final Double UPDATED_SOLDE = 2D;
//
//    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
//    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());
//
//    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
//    private static final String UPDATED_STATUS = "BBBBBBBBBB";
//
//    private static final String DEFAULT_RIB = "AAAAAAAAAA";
//    private static final String UPDATED_RIB = "BBBBBBBBBB";
//
//    private static final String ENTITY_API_URL = "/api/comptes";
//    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
//
//    private static Random random = new Random();
//    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    @Autowired
//    private CompteRepository compteRepository;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restCompteMockMvc;
//
//    private Compte compte;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Compte createEntity(EntityManager em) {
//        Compte compte = new Compte().solde(DEFAULT_SOLDE).dateCreation(DEFAULT_DATE_CREATION).status(DEFAULT_STATUS).rib(DEFAULT_RIB);
//        return compte;
//    }
//
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Compte createUpdatedEntity(EntityManager em) {
//        Compte compte = new Compte().solde(UPDATED_SOLDE).dateCreation(UPDATED_DATE_CREATION).status(UPDATED_STATUS).rib(UPDATED_RIB);
//        return compte;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        compte = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    void createCompte() throws Exception {
//        int databaseSizeBeforeCreate = compteRepository.findAll().size();
//        // Create the Compte
//        restCompteMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(compte)))
//            .andExpect(status().isCreated());
//
//        // Validate the Compte in the database
//        List<Compte> compteList = compteRepository.findAll();
//        assertThat(compteList).hasSize(databaseSizeBeforeCreate + 1);
//        Compte testCompte = compteList.get(compteList.size() - 1);
//        assertThat(testCompte.getSolde()).isEqualTo(DEFAULT_SOLDE);
//        assertThat(testCompte.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
//        assertThat(testCompte.getStatus()).isEqualTo(DEFAULT_STATUS);
//        assertThat(testCompte.getRib()).isEqualTo(DEFAULT_RIB);
//    }
//
//    @Test
//    @Transactional
//    void createCompteWithExistingId() throws Exception {
//        // Create the Compte with an existing ID
//        compte.setId(1L);
//
//        int databaseSizeBeforeCreate = compteRepository.findAll().size();
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restCompteMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(compte)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Compte in the database
//        List<Compte> compteList = compteRepository.findAll();
//        assertThat(compteList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    void getAllComptes() throws Exception {
//        // Initialize the database
//        compteRepository.saveAndFlush(compte);
//
//        // Get all the compteList
//        restCompteMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(compte.getId().intValue())))
//            .andExpect(jsonPath("$.[*].solde").value(hasItem(DEFAULT_SOLDE.doubleValue())))
//            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
//            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
//            .andExpect(jsonPath("$.[*].rib").value(hasItem(DEFAULT_RIB)));
//    }
//
//    @Test
//    @Transactional
//    void getCompte() throws Exception {
//        // Initialize the database
//        compteRepository.saveAndFlush(compte);
//
//        // Get the compte
//        restCompteMockMvc
//            .perform(get(ENTITY_API_URL_ID, compte.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(compte.getId().intValue()))
//            .andExpect(jsonPath("$.solde").value(DEFAULT_SOLDE.doubleValue()))
//            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
//            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
//            .andExpect(jsonPath("$.rib").value(DEFAULT_RIB));
//    }
//
//    @Test
//    @Transactional
//    void getNonExistingCompte() throws Exception {
//        // Get the compte
//        restCompteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    void putNewCompte() throws Exception {
//        // Initialize the database
//        compteRepository.saveAndFlush(compte);
//
//        int databaseSizeBeforeUpdate = compteRepository.findAll().size();
//
//        // Update the compte
//        Compte updatedCompte = compteRepository.findById(compte.getId()).get();
//        // Disconnect from session so that the updates on updatedCompte are not directly saved in db
//        em.detach(updatedCompte);
//        updatedCompte.solde(UPDATED_SOLDE).dateCreation(UPDATED_DATE_CREATION).status(UPDATED_STATUS).rib(UPDATED_RIB);
//
//        restCompteMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, updatedCompte.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(updatedCompte))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Compte in the database
//        List<Compte> compteList = compteRepository.findAll();
//        assertThat(compteList).hasSize(databaseSizeBeforeUpdate);
//        Compte testCompte = compteList.get(compteList.size() - 1);
//        assertThat(testCompte.getSolde()).isEqualTo(UPDATED_SOLDE);
//        assertThat(testCompte.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
//        assertThat(testCompte.getStatus()).isEqualTo(UPDATED_STATUS);
//        assertThat(testCompte.getRib()).isEqualTo(UPDATED_RIB);
//    }
//
//    @Test
//    @Transactional
//    void putNonExistingCompte() throws Exception {
//        int databaseSizeBeforeUpdate = compteRepository.findAll().size();
//        compte.setId(count.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restCompteMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, compte.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(compte))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Compte in the database
//        List<Compte> compteList = compteRepository.findAll();
//        assertThat(compteList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithIdMismatchCompte() throws Exception {
//        int databaseSizeBeforeUpdate = compteRepository.findAll().size();
//        compte.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restCompteMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(compte))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Compte in the database
//        List<Compte> compteList = compteRepository.findAll();
//        assertThat(compteList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithMissingIdPathParamCompte() throws Exception {
//        int databaseSizeBeforeUpdate = compteRepository.findAll().size();
//        compte.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restCompteMockMvc
//            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(compte)))
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the Compte in the database
//        List<Compte> compteList = compteRepository.findAll();
//        assertThat(compteList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void partialUpdateCompteWithPatch() throws Exception {
//        // Initialize the database
//        compteRepository.saveAndFlush(compte);
//
//        int databaseSizeBeforeUpdate = compteRepository.findAll().size();
//
//        // Update the compte using partial update
//        Compte partialUpdatedCompte = new Compte();
//        partialUpdatedCompte.setId(compte.getId());
//
//        partialUpdatedCompte.solde(UPDATED_SOLDE).rib(UPDATED_RIB);
//
//        restCompteMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedCompte.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompte))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Compte in the database
//        List<Compte> compteList = compteRepository.findAll();
//        assertThat(compteList).hasSize(databaseSizeBeforeUpdate);
//        Compte testCompte = compteList.get(compteList.size() - 1);
//        assertThat(testCompte.getSolde()).isEqualTo(UPDATED_SOLDE);
//        assertThat(testCompte.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
//        assertThat(testCompte.getStatus()).isEqualTo(DEFAULT_STATUS);
//        assertThat(testCompte.getRib()).isEqualTo(UPDATED_RIB);
//    }
//
//    @Test
//    @Transactional
//    void fullUpdateCompteWithPatch() throws Exception {
//        // Initialize the database
//        compteRepository.saveAndFlush(compte);
//
//        int databaseSizeBeforeUpdate = compteRepository.findAll().size();
//
//        // Update the compte using partial update
//        Compte partialUpdatedCompte = new Compte();
//        partialUpdatedCompte.setId(compte.getId());
//
//        partialUpdatedCompte.solde(UPDATED_SOLDE).dateCreation(UPDATED_DATE_CREATION).status(UPDATED_STATUS).rib(UPDATED_RIB);
//
//        restCompteMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedCompte.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompte))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Compte in the database
//        List<Compte> compteList = compteRepository.findAll();
//        assertThat(compteList).hasSize(databaseSizeBeforeUpdate);
//        Compte testCompte = compteList.get(compteList.size() - 1);
//        assertThat(testCompte.getSolde()).isEqualTo(UPDATED_SOLDE);
//        assertThat(testCompte.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
//        assertThat(testCompte.getStatus()).isEqualTo(UPDATED_STATUS);
//        assertThat(testCompte.getRib()).isEqualTo(UPDATED_RIB);
//    }
//
//    @Test
//    @Transactional
//    void patchNonExistingCompte() throws Exception {
//        int databaseSizeBeforeUpdate = compteRepository.findAll().size();
//        compte.setId(count.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restCompteMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, compte.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(compte))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Compte in the database
//        List<Compte> compteList = compteRepository.findAll();
//        assertThat(compteList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithIdMismatchCompte() throws Exception {
//        int databaseSizeBeforeUpdate = compteRepository.findAll().size();
//        compte.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restCompteMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(compte))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Compte in the database
//        List<Compte> compteList = compteRepository.findAll();
//        assertThat(compteList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithMissingIdPathParamCompte() throws Exception {
//        int databaseSizeBeforeUpdate = compteRepository.findAll().size();
//        compte.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restCompteMockMvc
//            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(compte)))
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the Compte in the database
//        List<Compte> compteList = compteRepository.findAll();
//        assertThat(compteList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void deleteCompte() throws Exception {
//        // Initialize the database
//        compteRepository.saveAndFlush(compte);
//
//        int databaseSizeBeforeDelete = compteRepository.findAll().size();
//
//        // Delete the compte
//        restCompteMockMvc
//            .perform(delete(ENTITY_API_URL_ID, compte.getId()).accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Compte> compteList = compteRepository.findAll();
//        assertThat(compteList).hasSize(databaseSizeBeforeDelete - 1);
//    }
}
