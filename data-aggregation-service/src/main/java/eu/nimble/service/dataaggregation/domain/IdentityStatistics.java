package eu.nimble.service.dataaggregation.domain;

public class IdentityStatistics {

    private Long totalUsers;

    private Long totalCompanies;

    private IdentityStatistics() {
    }

    public IdentityStatistics(Long totalUsers, Long totalCompanies) {
        this.totalUsers = totalUsers;
        this.totalCompanies = totalCompanies;
    }

    public Long getTotalUsers() {
        return totalUsers;
    }

    public Long getTotalCompanies() {
        return totalCompanies;
    }
}