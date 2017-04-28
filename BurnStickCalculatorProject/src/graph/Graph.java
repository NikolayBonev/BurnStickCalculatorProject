package graph;
import java.util.*;

import domain.*;

public class Graph {
	private List<Point> points;
	private double[][] matrix;
	
	public Graph(int size){
		matrix = new double[size][size];
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}
	
	/*
	 * Add the connection between two points in the matrix.
	 */
	public void addStickWeight(Stick stick){
		int index1 = points.indexOf(stick.getEnd1());
		int index2 = points.indexOf(stick.getEnd2());
		
		if(index1 == -1 || index2 == -1){
			return;
		}
		
		matrix[index1][index2] = stick.getTime();
		matrix[index2][index1] = stick.getTime();
	}
	
	/*
	 * Add connection between many points in the matrix.
	 */
	public void addSticksWeights(List<Stick> sticks){
		sticks.forEach(stick -> addStickWeight(stick));
	}
	
	public double[][] getMatrix() {
		return matrix;
	}
}
