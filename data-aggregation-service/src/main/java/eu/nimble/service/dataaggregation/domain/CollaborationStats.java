package eu.nimble.service.dataaggregation.domain;

public class CollaborationStats {
	private TradingVolume tradingVolumesales;
	private TradingVolume tradingVolumespurchase;
	private TradingVolume tradingVolume;
	private CollaborationTime collaborationTime;
	private ResponseTime responseTime;

	public CollaborationStats() {
	}

	public TradingVolume getTradingVolume() {
		return tradingVolume;
	}

	public void setTradingVolume(TradingVolume tradingVolume) {
		this.tradingVolume = tradingVolume;
	}

	public CollaborationTime getCollaborationTime() { return collaborationTime; }

	public void setCollaborationTime(CollaborationTime collaborationTime) {
		this.collaborationTime = collaborationTime;
	}

	public ResponseTime getResponseTime() { return responseTime;}

	public void setResponseTime(ResponseTime responseTime) {
		this.responseTime = responseTime;
	}

	public TradingVolume getTradingVolumesales() {
		return tradingVolumesales;
	}

	public void setTradingVolumesales(TradingVolume tradingVolumesales) {
		this.tradingVolumesales = tradingVolumesales;
	}

	public TradingVolume getTradingVolumespurchase() {
		return tradingVolumespurchase;
	}

	public void setTradingVolumespurchase(TradingVolume tradingVolumespurchase) {
		this.tradingVolumespurchase = tradingVolumespurchase;
	}
}
