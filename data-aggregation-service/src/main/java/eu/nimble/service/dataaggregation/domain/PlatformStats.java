package eu.nimble.service.dataaggregation.domain;

public class PlatformStats {

    private IdentityStatistics identity;
    private BusinessProcessStatistics businessProcessCount;
    private CatalogueStatistics catalogueStatistics;
    private TradingVolume tradingVolume;

    public PlatformStats() {
    }

    public IdentityStatistics getIdentity() {
        return identity;
    }

    public void setIdentity(IdentityStatistics identityStatistics) {
        this.identity = identityStatistics;
    }

    public CatalogueStatistics getCatalogueStatistics() { return catalogueStatistics; }

    public void setCatalogueStatistics(CatalogueStatistics catalogueStatistics) {
        this.catalogueStatistics = catalogueStatistics;
    }

    public BusinessProcessStatistics getBusinessProcessCount() {
        return businessProcessCount;
    }

    public void setBusinessProcessCount(BusinessProcessStatistics businessProcess) {
        this.businessProcessCount = businessProcess;
    }

    public TradingVolume getTradingVolume() {
        return tradingVolume;
    }

    public void setTradingVolume(TradingVolume tradingVolume) {
        this.tradingVolume = tradingVolume;
    }
}
