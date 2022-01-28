package com.ensa.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ensa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BenificiaireTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Benificiaire.class);
        Benificiaire benificiaire1 = new Benificiaire();
        benificiaire1.setId(1L);
        Benificiaire benificiaire2 = new Benificiaire();
        benificiaire2.setId(benificiaire1.getId());
        assertThat(benificiaire1).isEqualTo(benificiaire2);
        benificiaire2.setId(2L);
        assertThat(benificiaire1).isNotEqualTo(benificiaire2);
        benificiaire1.setId(null);
        assertThat(benificiaire1).isNotEqualTo(benificiaire2);
    }
}
