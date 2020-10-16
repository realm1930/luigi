package be.vdab.luigi.restclients;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@PropertySource("application.properties")
@Import(FixerKoersClient.class)
public class FixerKoersClientTest {
    public FixerKoersClientTest(FixerKoersClient client) {
        this.client = client;
    }

    private final FixerKoersClient client;

    @Test
    void deKoersIsPositief(){
        assertThat(client.getDollarKoers()).isPositive();
    }
}
