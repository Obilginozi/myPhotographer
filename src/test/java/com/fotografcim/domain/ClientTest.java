package com.fotografcim.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.fotografcim.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ClientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Client.class);
        Client client1 = new Client();
        client1.setId(UUID.randomUUID());
        Client client2 = new Client();
        client2.setId(client1.getId());
        assertThat(client1).isEqualTo(client2);
        client2.setId(UUID.randomUUID());
        assertThat(client1).isNotEqualTo(client2);
        client1.setId(null);
        assertThat(client1).isNotEqualTo(client2);
    }
}
