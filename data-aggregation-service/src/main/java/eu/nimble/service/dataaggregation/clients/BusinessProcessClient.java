package eu.nimble.service.dataaggregation.clients;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * REST Client for communication with the Business-Process Service.
 *
 * @author Johannes Innerbichler
 */
@FeignClient(name = "business-process-service", url = "${nimble.business-process.url}", fallback = BusinessProcessClientFallback.class)
//@FeignClient(name = "business-process-service", url = "${nimble.business-process.url}")
public interface BusinessProcessClient {

    @RequestMapping(method = RequestMethod.GET, value = "/statistics/total-number/business-process")
    Integer getProcessCountByStatusAndRole(@RequestParam(value = "role") Role role,
            @RequestParam(value = "status") Status status,
            @RequestHeader("Authorization") String bearerToken);

    @RequestMapping(method = RequestMethod.GET, value = "/statistics/total-number/business-process")
    Integer getProcessCountByStatusRoleForCompany(@RequestParam(value = "role") Role role,
            @RequestParam(value = "status") Status status,
            @RequestParam(value = "partyId") Integer partyId,@RequestHeader("Authorization") String bearerToken);

    @RequestMapping(method = RequestMethod.GET, value = "/statistics/total-number/business-process")
    Integer getProcessCountByRole(@RequestParam(value = "role") Role role,
            @RequestHeader("Authorization") String bearerToken);

    @RequestMapping(method = RequestMethod.GET, value = "/statistics/total-number/business-process")
    Integer getProcessCountByRoleForCompany(@RequestParam(value = "role") Role role,
            @RequestParam(value = "partyId") Integer partyId,@RequestHeader("Authorization") String bearerToken);

    @RequestMapping(method = RequestMethod.GET, value = "/statistics/total-number/business-process")
    Integer getProcessCountByType(@RequestParam(value = "BusinessProcessType") Type type,
            @RequestHeader("Authorization") String bearerToken);

    @RequestMapping(method = RequestMethod.GET, value = "/statistics/total-number/business-process")
    Integer getProcessCountByTypeForCompany(@RequestParam(value = "BusinessProcessType") Type type,
            @RequestParam(value = "partyId") Integer partyId ,@RequestHeader("Authorization") String bearerToken);

    @RequestMapping(method = RequestMethod.GET, value = "/statistics/trading-volume")
    Double getTradingVolumeByStatus(@RequestParam(value = "status") Status status,
            @RequestHeader("Authorization") String bearerToken);

    @RequestMapping(method = RequestMethod.GET, value = "/statistics/trading-volume")
    Double getTradingVolumeByStatusForCompany(@RequestParam(value = "role") Role role,@RequestParam(value = "status") Status status,
            @RequestParam(value = "partyId") Integer partyId ,@RequestHeader("Authorization") String bearerToken);

    @RequestMapping(method = RequestMethod.GET, value = "/statistics/collaboration-time")
    Double getCollaborationTimeForCompany(@RequestParam(value = "role") Role role,
            @RequestParam(value = "partyId") Integer partyId ,@RequestHeader("Authorization") String bearerToken);

    @RequestMapping(method = RequestMethod.GET, value = "/statistics/collaboration-time")
    Double getCollaborationTimeForPlatform(@RequestParam(value = "role") Role role,
            @RequestHeader("Authorization") String bearerToken);

    @RequestMapping(method = RequestMethod.GET, value = "/statistics/response-time")
    Double geResponseTimeForCompany(@RequestParam(value = "partyId") Integer partyId ,
            @RequestHeader("Authorization") String bearerToken);

    @RequestMapping(method = RequestMethod.GET, value = "/statistics/response-time")
    Double geResponseTimeForPlatform(@RequestHeader("Authorization") String bearerToken);

    @RequestMapping(method = RequestMethod.GET, value = "/statistics/response-time-months")
    Map<Integer,Double> geResponseTimeForCompanyForMonths(@RequestParam(value = "partyId") Integer partyId ,
            @RequestHeader("Authorization") String bearerToken);

    @RequestMapping(method = RequestMethod.GET, value = "/statistics/response-time-months")
    Map<Integer,Double> geResponseTimeForPlatformForMonths(@RequestHeader("Authorization") String bearerToken);


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
