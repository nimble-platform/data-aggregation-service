package eu.nimble.service.dataaggregation.domain;

public class TradingVolume {
    private Double waiting;
    private Double approved;
    private Double denied;

    private TradingVolume() {
    }

    public TradingVolume(Double waiting, Double approved, Double denied) {
        this.waiting = waiting;
        this.approved = approved;
        this.denied = denied;
    }

    public Double getWaiting() {
        return waiting;
    }

    public Double getApproved() {
        return approved;
    }

    public Double getDenied() {
        return denied;
    }
}
