package eu.nimble.service.dataaggregation.clients;

import eu.nimble.service.dataaggregation.domain.IdentityStatistics;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * REST Client for communication with the Identity Service.
 *
 * @author Johannes Innerbichler
 */
@FeignClient(name = "identity-service", url = "${nimble.identity.url}", fallback = IdentityClientFallback.class)
public interface IdentityClient {
    @RequestMapping(method = RequestMethod.GET, value = "/statistics/", produces = "application/json")
    IdentityStatistics getIdentityStatistics();
}
