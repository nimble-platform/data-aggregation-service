package eu.nimble.service.dataaggregation.domain;

import java.util.Map;

public class CollaborationTime {

	private Double averageCollabTime;
	private Double averageCollabTimePurchases;
	private Double averageCollabTimeSales;
	private Map<Integer,Double> averageCollabTimeForMonths;
	private Map<Integer,Double> averageCollabTimePurchasesForMonths;
	private Map<Integer,Double> averageCollabTimeSalesForMonths;

	public CollaborationTime(Double averageCollabTime, Double averageCollabTimePurchases, Double averageCollabTimeSales, Map<Integer, Double> averageCollabTimeForMonths, Map<Integer, Double> averageCollabTimePurchasesForMonths, Map<Integer, Double> averageCollabTimeSalesForMonths) {
		this.averageCollabTime = averageCollabTime;
		this.averageCollabTimePurchases = averageCollabTimePurchases;
		this.averageCollabTimeSales = averageCollabTimeSales;
		this.averageCollabTimeForMonths = averageCollabTimeForMonths;
		this.averageCollabTimePurchasesForMonths = averageCollabTimePurchasesForMonths;
		this.averageCollabTimeSalesForMonths = averageCollabTimeSalesForMonths;
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

	public Map<Integer, Double> getAverageCollabTimeForMonths() {
		return averageCollabTimeForMonths;
	}

	public Map<Integer, Double> getAverageCollabTimePurchasesForMonths() {
		return averageCollabTimePurchasesForMonths;
	}

	public Map<Integer, Double> getAverageCollabTimeSalesForMonths() {
		return averageCollabTimeSalesForMonths;
	}
}
