package domain;

public class Stick {
	private Point end1;
	private Point end2;
	private double time;
	
	public Stick(Point end1, Point end2, double time) {
		setEnd1(end1);
		setEnd2(end2);
		setTime(time);
	}

	public Point getEnd1() {
		return end1;
	}
	public void setEnd1(Point end1) {
		this.end1 = end1;
	}
	public Point getEnd2() {
		return end2;
	}
	public void setEnd2(Point end2) {
		this.end2 = end2;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	
	public Point calculateMiddle(){
		double coordinateX = 0;
		double coordinateY = 0;
		
		if(end1.getCoordinateX().equals(end2.getCoordinateX()) || end1.getCoordinateY().equals(end2.getCoordinateY())){
			return null;
		}else{
			if(end1.getCoordinateX() > end2.getCoordinateX()){
				coordinateX = end2.getCoordinateX() + ((end1.getCoordinateX() - end2.getCoordinateX())/2);
			}else{
				coordinateX = end1.getCoordinateX() + ((end2.getCoordinateX() - end1.getCoordinateX())/2);
			}
			
			if(end1.getCoordinateY() > end2.getCoordinateY()){
				coordinateY = end2.getCoordinateY() + ((end1.getCoordinateY() - end2.getCoordinateY())/2);
			}else{
				coordinateY = end1.getCoordinateY() + ((end2.getCoordinateY() - end1.getCoordinateY())/2);
			}
		}
		
		return new Point(coordinateX, coordinateY);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(!(obj instanceof Stick)){
			return false;
		}
		
		Stick stick = (Stick) obj;
		
		if(stick.getEnd1().equals(end1) && stick.getEnd2().equals(end2)){
			return true;
		}else if(stick.getEnd1().equals(end2) && stick.getEnd2().equals(end1)){
			return true;
		}
		
		return false;
	}
}
