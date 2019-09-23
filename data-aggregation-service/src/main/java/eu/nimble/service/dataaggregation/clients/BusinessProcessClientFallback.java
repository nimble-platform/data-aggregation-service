package eu.nimble.service.dataaggregation.clients;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
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
    public Double getTradingVolumeByStatusForCompany(Status status, Integer partyId, String bearerToken) { return -1.0; }
}
