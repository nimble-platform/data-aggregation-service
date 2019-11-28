package eu.nimble.service.dataaggregation.clients;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class BusinessProcessClientFallback implements BusinessProcessClient {

    @Override
    public Integer getProcessCountByStatusAndRole(Role role,Status status,
            String bearerToken) {
        return -1;
    }

    @Override
    public Integer getProcessCountByStatusRoleForCompany(Role role,Status status,
            Integer partyId,String bearerToken) { return -1; }

    @Override
    public Integer getProcessCountByRole(Role role, String bearerToken) {
        return -1;
    }

    @Override
    public Integer getProcessCountByRoleForCompany(Role role, Integer partyId, String bearerToken) { return -1; }

    @Override
    public Integer getProcessCountByType(Type type, String bearerToken) {
        return -1;
    }

    @Override
    public Integer getProcessCountByTypeForCompany(Type type, Integer partyId, String bearerToken) { return -1; }

    @Override
    public Double getTradingVolumeByStatus(Status status, String bearerToken) {
        return -1.0;
    }

    @Override
    public Double getTradingVolumeByStatusForCompany(Role role,Status status, Integer partyId, String bearerToken) { return -1.0; }

    @Override
    public Double getCollaborationTimeForCompany(Role role, Integer partyId, String bearerToken) { return -1.0; }

    @Override
    public Double geResponseTimeForCompany(Integer partyId, String bearerToken) { return -1.0; }

    @Override
    public Map<Integer,Double> geResponseTimeForCompanyForMonths(Integer partyId, String bearerToken) {
        Map<Integer, Double> map = new HashMap<>();
        return map;
    }

    @Override
    public Double getCollaborationTimeForPlatform(Role role,String bearerToken){ return -1.0; }

    @Override
    public Double geResponseTimeForPlatform(String bearerToken){ return -1.0; }

    @Override
    public Map<Integer,Double> geResponseTimeForPlatformForMonths(String bearerToken){
        Map<Integer, Double> map = new HashMap<>();
        return map;
    }
}
