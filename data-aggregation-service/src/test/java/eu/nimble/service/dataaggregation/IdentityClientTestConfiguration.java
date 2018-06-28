package eu.nimble.service.dataaggregation;

import eu.nimble.service.dataaggregation.clients.IdentityClient;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class IdentityClientTestConfiguration {

    @Bean
    @Primary
    public IdentityClient identityClient() {
        return Mockito.mock(IdentityClient.class, (Answer) invocation -> "444");
    }
}
