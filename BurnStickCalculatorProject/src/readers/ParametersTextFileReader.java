package readers;
import java.io.*;
import java.util.*;

import domain.*;

/*
 * This class reads the needed parameters from a normal text file.
 * Implements the interface ParametersReader.
 */

public class ParametersTextFileReader implements ParametersReader{
	private static String PARAMETER_DELIMITER = " ,";
	
	private List<Point> points = null;
	private List<Stick> sticks = null;
	/*
	 * Receives a URL to the file as a string. If there isn't a existing file with the URL or has occurred a exception
	 * while opening or reading the file return false. 
	 * Reads and fills the lists with Stick and Point objects.
	 * The used delimiter is " ,". If you want to use a different delimiter you have to change the PARAMETER_DELIMITER
	 * constant using the method changeDelimiter.
	 */
	public boolean read(String url, List<Point> points, List<Stick> sticks) {
		this.points = points;
		this.sticks = sticks;
		
		try(BufferedReader reader = new BufferedReader(new FileReader(url))) {
			String line = reader.readLine();
			int countOfSticks = Integer.parseInt(line);
			
			int i = 0;
			
			while(reader.ready() && i < countOfSticks){
				line = reader.readLine();
				
				createStick(line);
								
				i++;
			}
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}
	
	private void createStick(String stickLine) {
		String[] stickParameters = stickLine.split(PARAMETER_DELIMITER);
		
		Point stickEnd1 = createPoint(stickParameters[0], stickParameters[1]);
		Point stickEnd2 = createPoint(stickParameters[2], stickParameters[3]);
		
		addUniquePoint(stickEnd1);
		addUniquePoint(stickEnd2);
		
		double burnTime = Double.parseDouble(stickParameters[4]);
		
		createStick(stickEnd1, stickEnd2, burnTime);
	}
		
	/*
	 * Creates a Stick object from two given points and time.
	 * Checks if the stick is a middle stick. If true creates a third point and creates two sticks.
	 */
	private void createStick(Point stickEnd1, Point stickEnd2, double burnTime){
		
		if(stickEnd1.getCoordinateX().compareTo(stickEnd2.getCoordinateX()) == 0 ||
				stickEnd1.getCoordinateY().compareTo(stickEnd2.getCoordinateY()) == 0){
			
			addUniqueStick(new Stick(stickEnd1, stickEnd2, burnTime));
		}else{
			double middlePointX = calculateAxis(stickEnd1.getCoordinateX(), stickEnd2.getCoordinateX());
			double middlePointY = calculateAxis(stickEnd1.getCoordinateY(), stickEnd2.getCoordinateY());
			
			Point middlePoint = new Point(middlePointX, middlePointY);
			
			addUniquePoint(middlePoint);
			addUniqueStick(new Stick(stickEnd1, middlePoint, burnTime/2));
			addUniqueStick(new Stick(middlePoint, stickEnd2, burnTime/2));
		}
	}
	
	/*
	 * Calculates the X or Y or etc of particular mid point of a stick 
	 */
	private Double calculateAxis(double point1, double point2){
		double result = 0;
		
		
		if(point1 > point2){
			result = point2 + (point1 - point2)/2;
		}else if(point1 < point2){
			result = point1 + (point2 - point1)/2;
		}
		
		return result;
	}
	
	/*
	 * Creating a Point object from two real coordinates.
	 */
	private Point createPoint(String coorX, String coorY){
		Double coordinateX = Double.parseDouble(coorX);
		Double coordinateY = Double.parseDouble(coorY);
		
		Point point = new Point(coordinateX, coordinateY);
		
		return point;
	}
	
	/*
	 * Set the delimiter that will be used.
	 */
	public void changeDelimiter(String delimiter) {
		if(delimiter != null){
			PARAMETER_DELIMITER = delimiter;
		}
	}
	
	private void addUniquePoint(Point point) {
		if(!points.contains(point)) {
			points.add(point);
		}
	}
	
	private void addUniqueStick(Stick stick) {
		if(!sticks.contains(stick)) {
			sticks.add(stick);
		}
	}
	
	/*
	 * Returns the currently used delimiter.
	 */
	public String getDelimiter() {
		return PARAMETER_DELIMITER;
	}
}
