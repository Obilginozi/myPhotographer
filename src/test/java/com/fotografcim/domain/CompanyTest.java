package com.fotografcim.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.fotografcim.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CompanyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Company.class);
        Company company1 = new Company();
        company1.setId(UUID.randomUUID());
        Company company2 = new Company();
        company2.setId(company1.getId());
        assertThat(company1).isEqualTo(company2);
        company2.setId(UUID.randomUUID());
        assertThat(company1).isNotEqualTo(company2);
        company1.setId(null);
        assertThat(company1).isNotEqualTo(company2);
    }
}
