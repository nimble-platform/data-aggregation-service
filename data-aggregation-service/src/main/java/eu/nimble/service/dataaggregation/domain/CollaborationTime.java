package eu.nimble.service.dataaggregation.domain;

public class CollaborationTime {

	private Double averageCollabTime;
	private Double averageCollabTimePurchases;
	private Double averageCollabTimeSales;

	private CollaborationTime() {
	}

	public CollaborationTime(Double averageCollabTime, Double averageCollabTimePurchases, Double averageCollabTimeSales) {
		this.averageCollabTime = averageCollabTime;
		this.averageCollabTimePurchases = averageCollabTimePurchases;
		this.averageCollabTimeSales = averageCollabTimeSales;
	}

	public Double getAverageCollabTime() {
		return averageCollabTime;
	}

	public Double getAverageCollabTimePurchases() {
		return averageCollabTimePurchases;
	}

	public Double getAverageCollabTimeSales() {
		return averageCollabTimeSales;
	}
}
