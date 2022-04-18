package com.fotografcim.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.fotografcim.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class AlbumTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Album.class);
        Album album1 = new Album();
        album1.setId(UUID.randomUUID());
        Album album2 = new Album();
        album2.setId(album1.getId());
        assertThat(album1).isEqualTo(album2);
        album2.setId(UUID.randomUUID());
        assertThat(album1).isNotEqualTo(album2);
        album1.setId(null);
        assertThat(album1).isNotEqualTo(album2);
    }
}
