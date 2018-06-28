package eu.nimble.service.dataaggregation.clients;

import org.springframework.stereotype.Component;

@Component
public class BusinessProcessClientFallback implements BusinessProcessClient {
    @Override
    public Integer getTotalCountOfProcesses() {
        return -1;
    }

    @Override
    public Integer getProcessCountByStatus(Status status) {
        return -1;
    }

    @Override
    public Integer getProcessCountByRole(Role role) {
        return -1;
    }

    @Override
    public Integer getProcessCountByType(Type type) {
        return -1;
    }
}
