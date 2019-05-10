package eu.nimble.service.dataaggregation.clients;

import org.springframework.stereotype.Component;

@Component
public class BusinessProcessClientFallback implements BusinessProcessClient {
    @Override
    public Integer getTotalCountOfProcesses(String bearerToken) {
        return -1;
    }

    @Override
    public Integer getTotalCountOfProcessesForCompany(String bearerToken, Integer partyId) { return -1; }

    @Override
    public Integer getProcessCountByStatus(Status status, String bearerToken) {
        return -1;
    }

    @Override
    public Integer getProcessCountByStatusForCompany(Status status, Integer partyId, String bearerToken) { return -1; }

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
