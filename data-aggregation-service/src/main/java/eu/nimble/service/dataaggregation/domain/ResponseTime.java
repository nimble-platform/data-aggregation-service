package eu.nimble.service.dataaggregation.domain;

import java.util.Map;

public class ResponseTime {
	private Double averageTime;
	private Map<Integer,Double> averageTimeForMonths;

	private ResponseTime() {
	}

	public ResponseTime(Double averageTime, Map<Integer,Double> averageTimeForMonths) {
		this.averageTime = averageTime;
		this.averageTimeForMonths = averageTimeForMonths;
	}

	public Double getAverageTime() {
		return averageTime;
	}

	public Map<Integer, Double> getAverageTimeForMonths() {
		return averageTimeForMonths;
	}
}
