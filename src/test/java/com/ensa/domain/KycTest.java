package com.ensa.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ensa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KycTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kyc.class);
        Kyc kyc1 = new Kyc();
        kyc1.setId(1L);
        Kyc kyc2 = new Kyc();
        kyc2.setId(kyc1.getId());
        assertThat(kyc1).isEqualTo(kyc2);
        kyc2.setId(2L);
        assertThat(kyc1).isNotEqualTo(kyc2);
        kyc1.setId(null);
        assertThat(kyc1).isNotEqualTo(kyc2);
    }
}
