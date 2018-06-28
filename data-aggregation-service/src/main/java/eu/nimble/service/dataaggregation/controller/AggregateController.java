package eu.nimble.service.dataaggregation.controller;

import eu.nimble.service.dataaggregation.clients.BusinessProcessClient;
import eu.nimble.service.dataaggregation.clients.IdentityClient;
import eu.nimble.service.dataaggregation.domain.BusinessProcessStatistics;
import eu.nimble.service.dataaggregation.domain.PlatformStats;
import eu.nimble.service.dataaggregation.domain.IdentityStatistics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;

import static eu.nimble.service.dataaggregation.clients.BusinessProcessClient.Role.*;
import static eu.nimble.service.dataaggregation.clients.BusinessProcessClient.Status.*;
import static eu.nimble.service.dataaggregation.clients.BusinessProcessClient.Type.*;


/**
 * REST Controller for managing data channels.
 *
 * @author Johannes Innerbichler
 */
@Controller
@RequestMapping(path = "/")
@Api("Data Aggregation API")
@SuppressWarnings("unused")
public class AgggregateController {

    private static Logger logger = LoggerFactory.getLogger(AggregateController.class);

    @Autowired
    private IdentityClient identityClient;

    @Autowired
    private BusinessProcessClient businessProcessClient;

    @Autowired
    private Environment environment;

    @PostConstruct
    public void init() {
        logger.info("Using the following URLs: {}, {}", environment.getProperty("nimble.identity.url"), environment.getProperty("nimble.business-process.url"));
    }

    @ApiOperation(value = "Aggregate statistics of platform.", nickname = "getPlatformStats", response = PlatformStats.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Aggregated statistics of platform"),
            @ApiResponse(code = 400, message = "Error while aggregating statistics.")})
    @RequestMapping(value = "/", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<?> getPlatformStatistics() {

        logger.info("Start aggregating platform statistics...");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // collect statistics from Identity service
        IdentityStatistics identityStats = identityClient.getIdentityStatistics();

        // statistics from Business-Process service
        Integer totalBusinessProcesses = businessProcessClient.getTotalCountOfProcesses();
        Integer totalBusinessProcessesWaiting = businessProcessClient.getProcessCountByStatus(WAITINGRESPONSE);
        Integer totalBusinessProcessesApproved = businessProcessClient.getProcessCountByStatus(APPROVED);
        Integer totalBusinessProcessesDenied = businessProcessClient.getProcessCountByStatus(DENIED);
        Integer totalBusinessProcessesBuyer = businessProcessClient.getProcessCountByRole(BUYER);
        Integer totalBusinessProcessesSeller = businessProcessClient.getProcessCountByRole(SELLER);
        Integer totalBusinessProcessesInformationRequest = businessProcessClient.getProcessCountByType(ITEM_INFORMATION_REQUEST);
        Integer totalBusinessProcessesNegotiations = businessProcessClient.getProcessCountByType(NEGOTIATION);
        Integer totalBusinessProcessesOrder = businessProcessClient.getProcessCountByType(ORDER);
        BusinessProcessStatistics businessProcessStatistics = new BusinessProcessStatistics(totalBusinessProcesses, totalBusinessProcessesWaiting,
                totalBusinessProcessesApproved, totalBusinessProcessesDenied, totalBusinessProcessesBuyer, totalBusinessProcessesSeller,
                totalBusinessProcessesInformationRequest, totalBusinessProcessesNegotiations, totalBusinessProcessesOrder);

        // aggregate statistics
        PlatformStats platformStats = new PlatformStats();
        platformStats.setIdentity(identityStats);
        platformStats.setBusinessProcessCount(businessProcessStatistics);

        stopWatch.stop();
        logger.info("Finished aggregation of platform statistics in {} ms", stopWatch.getLastTaskTimeMillis());

        return ResponseEntity.ok(platformStats);
    }
}
