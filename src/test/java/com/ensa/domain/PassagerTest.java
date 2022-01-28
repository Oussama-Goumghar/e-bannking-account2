package com.ensa.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ensa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PassagerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Passager.class);
        Passager passager1 = new Passager();
        passager1.setId(1L);
        Passager passager2 = new Passager();
        passager2.setId(passager1.getId());
        assertThat(passager1).isEqualTo(passager2);
        passager2.setId(2L);
        assertThat(passager1).isNotEqualTo(passager2);
        passager1.setId(null);
        assertThat(passager1).isNotEqualTo(passager2);
    }
}
