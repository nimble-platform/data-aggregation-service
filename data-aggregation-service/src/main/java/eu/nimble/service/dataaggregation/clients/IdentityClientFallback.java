package eu.nimble.service.dataaggregation.clients;

import eu.nimble.service.dataaggregation.domain.IdentityStatistics;
import org.springframework.stereotype.Component;

@Component
public class IdentityClientFallback implements IdentityClient {
    @Override
    public IdentityStatistics getIdentityStatistics() {
        return new IdentityStatistics(-1L, -1L);
    }
}
