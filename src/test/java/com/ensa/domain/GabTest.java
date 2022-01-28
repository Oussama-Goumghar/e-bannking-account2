package com.ensa.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ensa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GabTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gab.class);
        Gab gab1 = new Gab();
        gab1.setId(1L);
        Gab gab2 = new Gab();
        gab2.setId(gab1.getId());
        assertThat(gab1).isEqualTo(gab2);
        gab2.setId(2L);
        assertThat(gab1).isNotEqualTo(gab2);
        gab1.setId(null);
        assertThat(gab1).isNotEqualTo(gab2);
    }
}
