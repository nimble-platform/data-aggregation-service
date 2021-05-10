package eu.nimble.service.dataaggregation.controller;

import eu.nimble.service.dataaggregation.clients.BusinessProcessClient;
import eu.nimble.service.dataaggregation.clients.CatalogueClient;
import eu.nimble.service.dataaggregation.clients.IdentityClient;
import eu.nimble.service.dataaggregation.domain.BusinessProcessStatistics;
import eu.nimble.service.dataaggregation.domain.CatalogueStatistics;
import eu.nimble.service.dataaggregation.domain.CollaborationStats;
import eu.nimble.service.dataaggregation.domain.CollaborationTime;
import eu.nimble.service.dataaggregation.domain.PlatformStats;
import eu.nimble.service.dataaggregation.domain.IdentityStatistics;
import eu.nimble.service.dataaggregation.domain.ResponseTime;
import eu.nimble.service.dataaggregation.domain.TradingVolume;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;

import static eu.nimble.service.dataaggregation.clients.BusinessProcessClient.Role.*;
import static eu.nimble.service.dataaggregation.clients.BusinessProcessClient.Status.*;
import static eu.nimble.service.dataaggregation.clients.BusinessProcessClient.Type.*;

import java.util.HashMap;
import java.util.Map;

/**
 * REST Controller for managing data channels.
 *
 * @author Johannes Innerbichler
 */
@Controller
@RequestMapping(path = "/")
@Api("Data Aggregation API")
@SuppressWarnings("unused")
public class AggregateController {

    private static Logger logger = LoggerFactory.getLogger(AggregateController.class);

    @Autowired
    private IdentityClient identityClient;

    @Autowired
    private BusinessProcessClient businessProcessClient;

    @Autowired
    private CatalogueClient catalogueClient;

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
    public ResponseEntity<?> getPlatformStatistics(@ApiParam(value = "The Bearer token provided by the identity service") @RequestHeader(value = "Authorization", required = true) String bearerToken,
                                                   @ApiParam(value = "companyID (not yet supported") @RequestParam(required = false) String companyID) {

        logger.info("Start aggregating platform statistics...");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // collect statistics from Identity service
        IdentityStatistics identityStats = identityClient.getIdentityStatistics();

        //buyer stats
        Integer totalBusinessProcessesBuyer = businessProcessClient.getProcessCountByRole(BUYER, bearerToken);
        Integer totalBusinessProcessesWaitingBuyer = businessProcessClient.getProcessCountByStatusAndRole(BUYER,WAITINGRESPONSE, bearerToken);
        Integer totalBusinessProcessesApprovedBuyer = businessProcessClient.getProcessCountByStatusAndRole(BUYER,APPROVED,bearerToken);
        Integer totalBusinessProcessesDeniedBuyer = businessProcessClient.getProcessCountByStatusAndRole(BUYER,DENIED,bearerToken);


        // seller statistics
        Integer totalBusinessProcessesSeller = businessProcessClient.getProcessCountByRole(SELLER, bearerToken);
        Integer totalBusinessProcessesWaitingSeller = businessProcessClient.getProcessCountByStatusAndRole(SELLER,WAITINGRESPONSE, bearerToken);
        Integer totalBusinessProcessesApprovedSeller = businessProcessClient.getProcessCountByStatusAndRole(SELLER,APPROVED, bearerToken);
        Integer totalBusinessProcessesDeniedSeller = businessProcessClient.getProcessCountByStatusAndRole(SELLER,DENIED, bearerToken);

        // statistics from Business-Process service
        Integer totalBusinessProcesses = (totalBusinessProcessesSeller + totalBusinessProcessesBuyer)/2;
        Integer totalBusinessProcessesWaiting = (totalBusinessProcessesWaitingSeller + totalBusinessProcessesWaitingBuyer)/2;
        Integer totalBusinessProcessesApproved= (totalBusinessProcessesApprovedSeller + totalBusinessProcessesApprovedBuyer)/2;
        Integer totalBusinessProcessesDenied = (totalBusinessProcessesDeniedSeller + totalBusinessProcessesDeniedBuyer)/2;

        // statistics from Business-Process service
        Integer totalBusinessProcessesInformationRequest = businessProcessClient.getProcessCountByType(ITEM_INFORMATION_REQUEST, bearerToken);
        Integer totalBusinessProcessesNegotiations = businessProcessClient.getProcessCountByType(NEGOTIATION, bearerToken);
        Integer totalBusinessProcessesOrder = businessProcessClient.getProcessCountByType(ORDER, bearerToken);

        BusinessProcessStatistics businessProcessStatistics = new BusinessProcessStatistics(totalBusinessProcesses, totalBusinessProcessesWaiting,
                totalBusinessProcessesApproved, totalBusinessProcessesDenied,totalBusinessProcessesSeller,
                totalBusinessProcessesWaitingSeller,totalBusinessProcessesApprovedSeller,totalBusinessProcessesDeniedSeller,
                totalBusinessProcessesBuyer,totalBusinessProcessesWaitingBuyer,totalBusinessProcessesApprovedBuyer,totalBusinessProcessesDeniedBuyer,
                totalBusinessProcessesInformationRequest, totalBusinessProcessesNegotiations, totalBusinessProcessesOrder);

        // trading volume
        Double volumeWaiting = businessProcessClient.getTradingVolumeByStatus(WAITINGRESPONSE, bearerToken);
        Double volumeApproved = businessProcessClient.getTradingVolumeByStatus(APPROVED, bearerToken);
        Double volumeDenied = businessProcessClient.getTradingVolumeByStatus(DENIED, bearerToken);
        TradingVolume tradingVolume = new TradingVolume(volumeWaiting, volumeApproved, volumeDenied);

        // aggregate statistics
        PlatformStats platformStats = new PlatformStats();
        platformStats.setIdentity(identityStats);
        platformStats.setBusinessProcessCount(businessProcessStatistics);
        platformStats.setTradingVolume(tradingVolume);

        stopWatch.stop();
        logger.info("Finished aggregation of platform statistics in {} ms", stopWatch.getLastTaskTimeMillis());

        return ResponseEntity.ok(platformStats);
    }


    @ApiOperation(value = "Aggregate statistics of platform.", nickname = "getPlatformStats", response = PlatformStats.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Aggregated statistics of platform"),
            @ApiResponse(code = 400, message = "Error while aggregating statistics.")})
    @RequestMapping(value = "/company", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<?> getPlatformStatisticsForComany(@ApiParam(value = "The Bearer token provided by the identity service") @RequestHeader(value = "Authorization", required = true) String bearerToken,
            @ApiParam(value = "companyID (not yet supported") @RequestParam(required = false) String companyID) {

        logger.info("Start aggregating platform statistics...");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // collect statistics from Identity service
        IdentityStatistics identityStats = identityClient.getIdentityStatistics();

        // seller statistics
        Integer totalBusinessProcessesSeller = businessProcessClient.getProcessCountByRoleForCompany(SELLER,Integer.parseInt(companyID), bearerToken);
        Integer totalBusinessProcessesWaitingSeller = businessProcessClient.getProcessCountByStatusRoleForCompany(SELLER,WAITINGRESPONSE,Integer.parseInt(companyID), bearerToken);
        Integer totalBusinessProcessesApprovedSeller = businessProcessClient.getProcessCountByStatusRoleForCompany(SELLER,APPROVED,Integer.parseInt(companyID), bearerToken);
        Integer totalBusinessProcessesDeniedSeller = businessProcessClient.getProcessCountByStatusRoleForCompany(SELLER,DENIED,Integer.parseInt(companyID), bearerToken);

        //buyer statistics
        Integer totalBusinessProcessesBuyer = businessProcessClient.getProcessCountByRoleForCompany(BUYER,Integer.parseInt(companyID), bearerToken);
        Integer totalBusinessProcessesWaitingBuyer = businessProcessClient.getProcessCountByStatusRoleForCompany(BUYER,WAITINGRESPONSE,Integer.parseInt(companyID), bearerToken);
        Integer totalBusinessProcessesApprovedBuyer = businessProcessClient.getProcessCountByStatusRoleForCompany(BUYER,APPROVED,Integer.parseInt(companyID), bearerToken);
        Integer totalBusinessProcessesDeniedBuyer = businessProcessClient.getProcessCountByStatusRoleForCompany(BUYER,DENIED,Integer.parseInt(companyID), bearerToken);

        // statistics from Business-Process service
        Integer totalBusinessProcesses = totalBusinessProcessesSeller + totalBusinessProcessesBuyer;
        Integer totalBusinessProcessesWaiting = totalBusinessProcessesWaitingSeller + totalBusinessProcessesWaitingBuyer;
        Integer totalBusinessProcessesApproved= totalBusinessProcessesApprovedSeller + totalBusinessProcessesApprovedBuyer;
        Integer totalBusinessProcessesDenied = totalBusinessProcessesDeniedSeller + totalBusinessProcessesDeniedBuyer;

        Integer totalBusinessProcessesInformationRequest = businessProcessClient.getProcessCountByTypeForCompany(ITEM_INFORMATION_REQUEST,Integer.parseInt(companyID), bearerToken);
        Integer totalBusinessProcessesNegotiations = businessProcessClient.getProcessCountByTypeForCompany(NEGOTIATION,Integer.parseInt(companyID), bearerToken);
        Integer totalBusinessProcessesOrder = businessProcessClient.getProcessCountByTypeForCompany(ORDER,Integer.parseInt(companyID),bearerToken);

        BusinessProcessStatistics businessProcessStatistics = new BusinessProcessStatistics(totalBusinessProcesses,
                totalBusinessProcessesWaiting,totalBusinessProcessesApproved,totalBusinessProcessesDenied,
                totalBusinessProcessesSeller,totalBusinessProcessesWaitingSeller,totalBusinessProcessesApprovedSeller,totalBusinessProcessesDeniedSeller,
                totalBusinessProcessesBuyer,totalBusinessProcessesWaitingBuyer,totalBusinessProcessesApprovedBuyer,totalBusinessProcessesDeniedBuyer,
                totalBusinessProcessesInformationRequest, totalBusinessProcessesNegotiations, totalBusinessProcessesOrder);

        // aggregate statistics
        PlatformStats platformStats = new PlatformStats();
        platformStats.setIdentity(identityStats);
        platformStats.setBusinessProcessCount(businessProcessStatistics);

        stopWatch.stop();
        logger.info("Finished aggregation of platform statistics in {} ms", stopWatch.getLastTaskTimeMillis());

        return ResponseEntity.ok(platformStats);
    }

    @ApiOperation(value = "Aggregate statistics of company collaboration.", nickname = "getCollabStats", response = CollaborationStats.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Aggregated statistics of company collaboration"),
            @ApiResponse(code = 400, message = "Error while aggregating statistics.")})
    @RequestMapping(value = "/company/collabaration", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<?> getCollabarationStatisticsForComany(@ApiParam(value = "The Bearer token provided by the identity service") @RequestHeader(value = "Authorization", required = true) String bearerToken,
            @ApiParam(value = "companyID (not yet supported") @RequestParam(required = false) String companyID) {

        // trading volume seller
        Double volumeWaitingSeller = businessProcessClient.getTradingVolumeByStatusForCompany(SELLER,WAITINGRESPONSE,Integer.parseInt(companyID),bearerToken);
        Double volumeApprovedSeller = businessProcessClient.getTradingVolumeByStatusForCompany(SELLER,APPROVED,Integer.parseInt(companyID),bearerToken);
        Double volumeDeniedSeller = businessProcessClient.getTradingVolumeByStatusForCompany(SELLER,DENIED,Integer.parseInt(companyID), bearerToken);
        TradingVolume tradingVolumeSeller = new TradingVolume(volumeWaitingSeller, volumeApprovedSeller, volumeDeniedSeller);

        // trading volume buyer
        Double volumeWaitingBuyer = businessProcessClient.getTradingVolumeByStatusForCompany(BUYER,WAITINGRESPONSE,Integer.parseInt(companyID),bearerToken);
        Double volumeApprovedBuyerr = businessProcessClient.getTradingVolumeByStatusForCompany(BUYER,APPROVED,Integer.parseInt(companyID),bearerToken);
        Double volumeDeniedBuyer = businessProcessClient.getTradingVolumeByStatusForCompany(BUYER,DENIED,Integer.parseInt(companyID), bearerToken);
        TradingVolume tradingVolumeBuyer = new TradingVolume(volumeWaitingBuyer, volumeApprovedBuyerr, volumeDeniedBuyer);

        Double averageWaitng = (volumeWaitingSeller + volumeWaitingBuyer)/2;
        Double averageApproved = (volumeApprovedBuyerr + volumeApprovedSeller)/2;
        Double averageDenied = (volumeDeniedSeller + volumeDeniedBuyer)/2;
        TradingVolume tradingVolumeAvg = new TradingVolume(averageWaitng, averageApproved, averageDenied);

        //collab time
        Double averageCollabTimePurchases = businessProcessClient.getCollaborationTimeForCompany(BUYER,Integer.parseInt(companyID),bearerToken);
        Double averageCollabTimeSales = businessProcessClient.getCollaborationTimeForCompany(SELLER,Integer.parseInt(companyID),bearerToken);
        Double averageCollabTime = (averageCollabTimePurchases+averageCollabTimeSales)/2;
        Map<Integer,Double> averageCollabTimePurchasesForMonths = businessProcessClient.getCollaborationTimeForCompanyForMonths(BUYER,Integer.parseInt(companyID),bearerToken);
        Map<Integer,Double> averageCollabTimeSalesForMonths = businessProcessClient.getCollaborationTimeForCompanyForMonths(SELLER,Integer.parseInt(companyID),bearerToken);
        Map<Integer,Double> averageCollabTimeForMonths = new HashMap<>();
        averageCollabTimePurchasesForMonths.forEach((month, value) -> averageCollabTimeForMonths.put(month, (averageCollabTimeSalesForMonths.get(month) + value) / 2));
        CollaborationTime collaborationTime = new CollaborationTime(averageCollabTime, averageCollabTimePurchases, averageCollabTimeSales,averageCollabTimeForMonths,
                averageCollabTimePurchasesForMonths,averageCollabTimeSalesForMonths);

        //response time
        Double averageResponseTime = businessProcessClient.geResponseTimeForCompany(Integer.parseInt(companyID),bearerToken);
        Map<Integer,Double> averagetimeForMonths =
                businessProcessClient.geResponseTimeForCompanyForMonths(Integer.parseInt(companyID),bearerToken);
        ResponseTime resTime = new ResponseTime(averageResponseTime,averagetimeForMonths);

        // aggregate statistics
        CollaborationStats collabStats = new CollaborationStats();
        collabStats.setTradingVolume(tradingVolumeAvg);
        collabStats.setTradingVolumesales(tradingVolumeSeller);
        collabStats.setTradingVolumespurchase(tradingVolumeBuyer);
        collabStats.setCollaborationTime(collaborationTime);
        collabStats.setResponseTime(resTime);

        return ResponseEntity.ok(collabStats);
    }

    @ApiOperation(value = "Aggregate statistics of company collaboration.", nickname = "getCollabStats", response = CollaborationStats.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Aggregated statistics of company collaboration"),
            @ApiResponse(code = 400, message = "Error while aggregating statistics.")})
    @RequestMapping(value = "/platform/collabaration", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<?> getCollabarationStatisticsForPlatform(@ApiParam(value = "The Bearer token provided by the identity service") @RequestHeader(value = "Authorization", required = true) String bearerToken) {


        //collab time
        Double averageCollabTimePurchases = businessProcessClient.getCollaborationTimeForPlatform(BUYER,bearerToken);
        Double averageCollabTimeSales = businessProcessClient.getCollaborationTimeForPlatform(SELLER,bearerToken);
        Double averageCollabTime = (averageCollabTimePurchases+averageCollabTimeSales)/2;
        Map<Integer,Double> averageCollabTimePurchasesForMonths = businessProcessClient.getCollaborationTimeForPlatformForMonths(BUYER,bearerToken);
        Map<Integer,Double> averageCollabTimeSalesForMonths = businessProcessClient.getCollaborationTimeForPlatformForMonths(SELLER,bearerToken);
        Map<Integer,Double> averageCollabTimeForMonths = new HashMap<>();
        averageCollabTimePurchasesForMonths.forEach((month, value) -> averageCollabTimeForMonths.put(month, (averageCollabTimeSalesForMonths.get(month) + value) / 2));
        CollaborationTime collaborationTime = new CollaborationTime(averageCollabTime, averageCollabTimePurchases, averageCollabTimeSales,averageCollabTimeForMonths,
                averageCollabTimePurchasesForMonths,averageCollabTimeSalesForMonths);

        //response time
        Double averageResponseTime = businessProcessClient.geResponseTimeForPlatform(bearerToken);
        Map<Integer,Double> averagetimeForMonths =
                businessProcessClient.geResponseTimeForPlatformForMonths(bearerToken);
        ResponseTime resTime = new ResponseTime(averageResponseTime,averagetimeForMonths);

        // aggregate statistics
        CollaborationStats collabStats = new CollaborationStats();
        collabStats.setCollaborationTime(collaborationTime);
        collabStats.setResponseTime(resTime);
        return ResponseEntity.ok(collabStats);
    }
}
