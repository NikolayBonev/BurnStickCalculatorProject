package domain;

public class BurnResult {
	private Point startBurnPoint;
	private Double[] resultDistances;
	private Double burnTime;
	
	public BurnResult(Point startBurnPoint, Double[] resultDistances) {
		this.startBurnPoint = startBurnPoint;
		this.resultDistances = resultDistances;
	}
	
	public Point getStartBurnPoint() {
		return startBurnPoint;
	}

	public Double[] getResultDistances() {
		return resultDistances;
	}

	public Double getBurnTime() {
		return burnTime;
	}

	public void setBurnTime(Double burnTime) {
		this.burnTime = burnTime;
	}
}
