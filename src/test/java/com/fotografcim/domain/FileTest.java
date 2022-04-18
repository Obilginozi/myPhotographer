package com.fotografcim.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.fotografcim.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class FileTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(File.class);
        File file1 = new File();
        file1.setId(UUID.randomUUID());
        File file2 = new File();
        file2.setId(file1.getId());
        assertThat(file1).isEqualTo(file2);
        file2.setId(UUID.randomUUID());
        assertThat(file1).isNotEqualTo(file2);
        file1.setId(null);
        assertThat(file1).isNotEqualTo(file2);
    }
}
