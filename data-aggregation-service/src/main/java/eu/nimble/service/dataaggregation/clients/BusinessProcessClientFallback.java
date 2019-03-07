package eu.nimble.service.dataaggregation.clients;

import org.springframework.stereotype.Component;

@Component
public class BusinessProcessClientFallback implements BusinessProcessClient {
    @Override
    public Integer getTotalCountOfProcesses(String bearerToken) {
        return -1;
    }

    @Override
    public Integer getProcessCountByStatus(Status status, String bearerToken) {
        return -1;
    }

    @Override
    public Integer getProcessCountByRole(Role role, String bearerToken) {
        return -1;
    }

    @Override
    public Integer getProcessCountByType(Type type, String bearerToken) {
        return -1;
    }

    @Override
    public Double getTradingVolumeByStatus(Status status, String bearerToken) {
        return -1.0;
    }
}
