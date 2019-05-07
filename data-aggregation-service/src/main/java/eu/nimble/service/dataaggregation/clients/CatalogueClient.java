package eu.nimble.service.dataaggregation.clients;

import eu.nimble.service.dataaggregation.domain.CatalogueStatistics;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * REST Client for communication with the Catalogue Service.
 *
 * @author Ayeshmantha Perera
 */
@FeignClient(name = "catalogue-service", url = "${nimble.catalogue.url}", fallback = CatalogueClientFallback.class)
public interface CatalogueClient {
    @RequestMapping(method = RequestMethod.GET, value = "/cataloguelines/statistics")
    CatalogueStatistics getTotalProductsAndServices(@RequestHeader("Authorization") String bearerToken);
}
