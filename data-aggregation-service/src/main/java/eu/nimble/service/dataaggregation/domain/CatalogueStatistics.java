package eu.nimble.service.dataaggregation.domain;

public class CatalogueStatistics {

	private int totalServices;

	private int totalProducts;

	private CatalogueStatistics() {
	}

	public CatalogueStatistics(int totalServices, int totalProducts) {
		this.totalServices = totalServices;
		this.totalProducts = totalProducts;
	}

	public int getTotalServices() {
		return totalServices;
	}

	public int getTotalProducts() {
		return totalProducts;
	}
}
