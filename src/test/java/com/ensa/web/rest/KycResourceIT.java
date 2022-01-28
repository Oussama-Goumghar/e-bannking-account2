package com.ensa.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ensa.IntegrationTest;
import com.ensa.domain.Kyc;
import com.ensa.repository.KycRepository;
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
 * Integration tests for the {@link KycResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KycResourceIT {

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_IDENTITE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_IDENTITE = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_IDENTITE = "AAAAAAAAAA";
    private static final String UPDATED_NUM_IDENTITE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALIDATE_TIME_IDENTITE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALIDATE_TIME_IDENTITE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PROFESSION = "AAAAAAAAAA";
    private static final String UPDATED_PROFESSION = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITE = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_GSM = "AAAAAAAAAA";
    private static final String UPDATED_GSM = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/kycs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KycRepository kycRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKycMockMvc;

    private Kyc kyc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kyc createEntity(EntityManager em) {
        Kyc kyc = new Kyc()
            .titre(DEFAULT_TITRE)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .typeIdentite(DEFAULT_TYPE_IDENTITE)
            .numIdentite(DEFAULT_NUM_IDENTITE)
            .validateTimeIdentite(DEFAULT_VALIDATE_TIME_IDENTITE)
            .profession(DEFAULT_PROFESSION)
            .nationalite(DEFAULT_NATIONALITE)
            .address(DEFAULT_ADDRESS)
            .gsm(DEFAULT_GSM)
            .email(DEFAULT_EMAIL);
        return kyc;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kyc createUpdatedEntity(EntityManager em) {
        Kyc kyc = new Kyc()
            .titre(UPDATED_TITRE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .typeIdentite(UPDATED_TYPE_IDENTITE)
            .numIdentite(UPDATED_NUM_IDENTITE)
            .validateTimeIdentite(UPDATED_VALIDATE_TIME_IDENTITE)
            .profession(UPDATED_PROFESSION)
            .nationalite(UPDATED_NATIONALITE)
            .address(UPDATED_ADDRESS)
            .gsm(UPDATED_GSM)
            .email(UPDATED_EMAIL);
        return kyc;
    }

    @BeforeEach
    public void initTest() {
        kyc = createEntity(em);
    }

    @Test
    @Transactional
    void createKyc() throws Exception {
        int databaseSizeBeforeCreate = kycRepository.findAll().size();
        // Create the Kyc
        restKycMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kyc)))
            .andExpect(status().isCreated());

        // Validate the Kyc in the database
        List<Kyc> kycList = kycRepository.findAll();
        assertThat(kycList).hasSize(databaseSizeBeforeCreate + 1);
        Kyc testKyc = kycList.get(kycList.size() - 1);
        assertThat(testKyc.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testKyc.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testKyc.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testKyc.getTypeIdentite()).isEqualTo(DEFAULT_TYPE_IDENTITE);
        assertThat(testKyc.getNumIdentite()).isEqualTo(DEFAULT_NUM_IDENTITE);
        assertThat(testKyc.getValidateTimeIdentite()).isEqualTo(DEFAULT_VALIDATE_TIME_IDENTITE);
        assertThat(testKyc.getProfession()).isEqualTo(DEFAULT_PROFESSION);
        assertThat(testKyc.getNationalite()).isEqualTo(DEFAULT_NATIONALITE);
        assertThat(testKyc.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testKyc.getGsm()).isEqualTo(DEFAULT_GSM);
        assertThat(testKyc.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void createKycWithExistingId() throws Exception {
        // Create the Kyc with an existing ID
        kyc.setId(1L);

        int databaseSizeBeforeCreate = kycRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKycMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kyc)))
            .andExpect(status().isBadRequest());

        // Validate the Kyc in the database
        List<Kyc> kycList = kycRepository.findAll();
        assertThat(kycList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKycs() throws Exception {
        // Initialize the database
        kycRepository.saveAndFlush(kyc);

        // Get all the kycList
        restKycMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kyc.getId().intValue())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].typeIdentite").value(hasItem(DEFAULT_TYPE_IDENTITE)))
            .andExpect(jsonPath("$.[*].numIdentite").value(hasItem(DEFAULT_NUM_IDENTITE)))
            .andExpect(jsonPath("$.[*].validateTimeIdentite").value(hasItem(DEFAULT_VALIDATE_TIME_IDENTITE.toString())))
            .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION)))
            .andExpect(jsonPath("$.[*].nationalite").value(hasItem(DEFAULT_NATIONALITE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].gsm").value(hasItem(DEFAULT_GSM)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    void getKyc() throws Exception {
        // Initialize the database
        kycRepository.saveAndFlush(kyc);

        // Get the kyc
        restKycMockMvc
            .perform(get(ENTITY_API_URL_ID, kyc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kyc.getId().intValue()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.typeIdentite").value(DEFAULT_TYPE_IDENTITE))
            .andExpect(jsonPath("$.numIdentite").value(DEFAULT_NUM_IDENTITE))
            .andExpect(jsonPath("$.validateTimeIdentite").value(DEFAULT_VALIDATE_TIME_IDENTITE.toString()))
            .andExpect(jsonPath("$.profession").value(DEFAULT_PROFESSION))
            .andExpect(jsonPath("$.nationalite").value(DEFAULT_NATIONALITE))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.gsm").value(DEFAULT_GSM))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    void getNonExistingKyc() throws Exception {
        // Get the kyc
        restKycMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewKyc() throws Exception {
        // Initialize the database
        kycRepository.saveAndFlush(kyc);

        int databaseSizeBeforeUpdate = kycRepository.findAll().size();

        // Update the kyc
        Kyc updatedKyc = kycRepository.findById(kyc.getId()).get();
        // Disconnect from session so that the updates on updatedKyc are not directly saved in db
        em.detach(updatedKyc);
        updatedKyc
            .titre(UPDATED_TITRE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .typeIdentite(UPDATED_TYPE_IDENTITE)
            .numIdentite(UPDATED_NUM_IDENTITE)
            .validateTimeIdentite(UPDATED_VALIDATE_TIME_IDENTITE)
            .profession(UPDATED_PROFESSION)
            .nationalite(UPDATED_NATIONALITE)
            .address(UPDATED_ADDRESS)
            .gsm(UPDATED_GSM)
            .email(UPDATED_EMAIL);

        restKycMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKyc.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedKyc))
            )
            .andExpect(status().isOk());

        // Validate the Kyc in the database
        List<Kyc> kycList = kycRepository.findAll();
        assertThat(kycList).hasSize(databaseSizeBeforeUpdate);
        Kyc testKyc = kycList.get(kycList.size() - 1);
        assertThat(testKyc.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testKyc.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testKyc.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testKyc.getTypeIdentite()).isEqualTo(UPDATED_TYPE_IDENTITE);
        assertThat(testKyc.getNumIdentite()).isEqualTo(UPDATED_NUM_IDENTITE);
        assertThat(testKyc.getValidateTimeIdentite()).isEqualTo(UPDATED_VALIDATE_TIME_IDENTITE);
        assertThat(testKyc.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testKyc.getNationalite()).isEqualTo(UPDATED_NATIONALITE);
        assertThat(testKyc.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testKyc.getGsm()).isEqualTo(UPDATED_GSM);
        assertThat(testKyc.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void putNonExistingKyc() throws Exception {
        int databaseSizeBeforeUpdate = kycRepository.findAll().size();
        kyc.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKycMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kyc.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kyc))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kyc in the database
        List<Kyc> kycList = kycRepository.findAll();
        assertThat(kycList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKyc() throws Exception {
        int databaseSizeBeforeUpdate = kycRepository.findAll().size();
        kyc.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKycMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kyc))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kyc in the database
        List<Kyc> kycList = kycRepository.findAll();
        assertThat(kycList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKyc() throws Exception {
        int databaseSizeBeforeUpdate = kycRepository.findAll().size();
        kyc.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKycMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kyc)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kyc in the database
        List<Kyc> kycList = kycRepository.findAll();
        assertThat(kycList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKycWithPatch() throws Exception {
        // Initialize the database
        kycRepository.saveAndFlush(kyc);

        int databaseSizeBeforeUpdate = kycRepository.findAll().size();

        // Update the kyc using partial update
        Kyc partialUpdatedKyc = new Kyc();
        partialUpdatedKyc.setId(kyc.getId());

        partialUpdatedKyc
            .titre(UPDATED_TITRE)
            .nom(UPDATED_NOM)
            .typeIdentite(UPDATED_TYPE_IDENTITE)
            .validateTimeIdentite(UPDATED_VALIDATE_TIME_IDENTITE)
            .nationalite(UPDATED_NATIONALITE)
            .address(UPDATED_ADDRESS)
            .gsm(UPDATED_GSM);

        restKycMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKyc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKyc))
            )
            .andExpect(status().isOk());

        // Validate the Kyc in the database
        List<Kyc> kycList = kycRepository.findAll();
        assertThat(kycList).hasSize(databaseSizeBeforeUpdate);
        Kyc testKyc = kycList.get(kycList.size() - 1);
        assertThat(testKyc.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testKyc.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testKyc.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testKyc.getTypeIdentite()).isEqualTo(UPDATED_TYPE_IDENTITE);
        assertThat(testKyc.getNumIdentite()).isEqualTo(DEFAULT_NUM_IDENTITE);
        assertThat(testKyc.getValidateTimeIdentite()).isEqualTo(UPDATED_VALIDATE_TIME_IDENTITE);
        assertThat(testKyc.getProfession()).isEqualTo(DEFAULT_PROFESSION);
        assertThat(testKyc.getNationalite()).isEqualTo(UPDATED_NATIONALITE);
        assertThat(testKyc.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testKyc.getGsm()).isEqualTo(UPDATED_GSM);
        assertThat(testKyc.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void fullUpdateKycWithPatch() throws Exception {
        // Initialize the database
        kycRepository.saveAndFlush(kyc);

        int databaseSizeBeforeUpdate = kycRepository.findAll().size();

        // Update the kyc using partial update
        Kyc partialUpdatedKyc = new Kyc();
        partialUpdatedKyc.setId(kyc.getId());

        partialUpdatedKyc
            .titre(UPDATED_TITRE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .typeIdentite(UPDATED_TYPE_IDENTITE)
            .numIdentite(UPDATED_NUM_IDENTITE)
            .validateTimeIdentite(UPDATED_VALIDATE_TIME_IDENTITE)
            .profession(UPDATED_PROFESSION)
            .nationalite(UPDATED_NATIONALITE)
            .address(UPDATED_ADDRESS)
            .gsm(UPDATED_GSM)
            .email(UPDATED_EMAIL);

        restKycMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKyc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKyc))
            )
            .andExpect(status().isOk());

        // Validate the Kyc in the database
        List<Kyc> kycList = kycRepository.findAll();
        assertThat(kycList).hasSize(databaseSizeBeforeUpdate);
        Kyc testKyc = kycList.get(kycList.size() - 1);
        assertThat(testKyc.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testKyc.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testKyc.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testKyc.getTypeIdentite()).isEqualTo(UPDATED_TYPE_IDENTITE);
        assertThat(testKyc.getNumIdentite()).isEqualTo(UPDATED_NUM_IDENTITE);
        assertThat(testKyc.getValidateTimeIdentite()).isEqualTo(UPDATED_VALIDATE_TIME_IDENTITE);
        assertThat(testKyc.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testKyc.getNationalite()).isEqualTo(UPDATED_NATIONALITE);
        assertThat(testKyc.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testKyc.getGsm()).isEqualTo(UPDATED_GSM);
        assertThat(testKyc.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void patchNonExistingKyc() throws Exception {
        int databaseSizeBeforeUpdate = kycRepository.findAll().size();
        kyc.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKycMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kyc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kyc))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kyc in the database
        List<Kyc> kycList = kycRepository.findAll();
        assertThat(kycList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKyc() throws Exception {
        int databaseSizeBeforeUpdate = kycRepository.findAll().size();
        kyc.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKycMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kyc))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kyc in the database
        List<Kyc> kycList = kycRepository.findAll();
        assertThat(kycList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKyc() throws Exception {
        int databaseSizeBeforeUpdate = kycRepository.findAll().size();
        kyc.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKycMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(kyc)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kyc in the database
        List<Kyc> kycList = kycRepository.findAll();
        assertThat(kycList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKyc() throws Exception {
        // Initialize the database
        kycRepository.saveAndFlush(kyc);

        int databaseSizeBeforeDelete = kycRepository.findAll().size();

        // Delete the kyc
        restKycMockMvc.perform(delete(ENTITY_API_URL_ID, kyc.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Kyc> kycList = kycRepository.findAll();
        assertThat(kycList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
