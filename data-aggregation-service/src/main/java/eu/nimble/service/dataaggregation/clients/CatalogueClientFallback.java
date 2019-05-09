package eu.nimble.service.dataaggregation.clients;

import eu.nimble.service.dataaggregation.domain.CatalogueStatistics;
import org.springframework.stereotype.Component;

@Component
public class CatalogueClientFallback implements CatalogueClient {
    @Override
    public CatalogueStatistics getTotalProductsAndServices(String bearerToken) {
        return new CatalogueStatistics(-1,-1);
    }
}
