package eu.nimble.service.dataaggregation.clients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * REST Client for communication with the Business-Process Service.
 *
 * @author Johannes Innerbichler
 */
@FeignClient(name = "business-process-service", url = "${nimble.business-process.url}", fallback = BusinessProcessClientFallback.class)
public interface BusinessProcessClient {
    @RequestMapping(method = RequestMethod.GET, value = "/statistics/total-number/business-process")
    Integer getTotalCountOfProcesses();

    @RequestMapping(method = RequestMethod.GET, value = "/statistics/total-number/business-process")
    Integer getProcessCountByStatus(@RequestParam(value = "status") Status status);

    @RequestMapping(method = RequestMethod.GET, value = "/statistics/total-number/business-process")
    Integer getProcessCountByRole(@RequestParam(value = "role") Role role);

    @RequestMapping(method = RequestMethod.GET, value = "/statistics/total-number/business-process")
    Integer getProcessCountByType(@RequestParam(value = "BusinessProcessType") Type type);

    enum Status {
        APPROVED, WAITINGRESPONSE, DENIED
    }

    enum Role {
        BUYER, SELLER
    }

    enum Type {
        CATALOGUE, NEGOTIATION, ORDER, REMITTANCEADVICE, INVOICE, TRACKING, FULFILMENT,
        PRODUCTCONFIGURATION, TRANSPORT_EXECUTION_PLAN, ITEM_INFORMATION_REQUEST, OTHER
    }
}