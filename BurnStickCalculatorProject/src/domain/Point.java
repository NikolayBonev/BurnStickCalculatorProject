package domain;

public class Point {
	private Double coordinateX;
	private Double coordinateY;
	
	public Point(Double coordinateX, Double coordinateY) {
		setCoordinateX(coordinateX);
		setCoordinateY(coordinateY);
	}
	
	public Double getCoordinateX() {
		return coordinateX;
	}
	
	public void setCoordinateX(Double coordinateX) {
		this.coordinateX = coordinateX;
	}
	
	public Double getCoordinateY() {
		return coordinateY;
	}
	
	public void setCoordinateY(Double coordinateY) {
		this.coordinateY = coordinateY;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(!(obj instanceof Point)){
			return false;
		}
		
		Point pnt = (Point) obj;
		
		if(coordinateX.equals(pnt.coordinateX) && coordinateY.equals(pnt.coordinateY)){
			return true;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
	    long bits = Double.doubleToLongBits(coordinateX);
	    bits ^= Double.doubleToLongBits(coordinateY) * 31;
	    return (((int) bits) ^ ((int) (bits >> 32)));
	}
}
