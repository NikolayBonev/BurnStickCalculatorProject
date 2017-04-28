package calculator;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import domain.*;
import graph.Graph;

public class BurnStickThread implements Callable<BurnResult> {
	private BurnResult result = null;
	private List<Point> points;
	private List<Stick> sticks;
	private boolean[] isPassedPoints;

	private Graph graph;
	private int currentPointIndex;
	private Double[] distances;

	public BurnStickThread(List<Point> points, List<Stick> sticks, Graph graph, int currentIndex) {
		this.points = new ArrayList<Point>(points);
		this.sticks = new ArrayList<Stick>(sticks);
		this.graph = graph;

		this.isPassedPoints = new boolean[points.size()];
		this.distances = new Double[points.size()];
		Arrays.fill(distances, 0.0);
		this.currentPointIndex = currentIndex;
		this.result = new BurnResult(points.get(currentIndex), this.distances);
	}

	/*
	 * Returns the neighbors of the current point.
	 */
	private List<Integer> getAllNeighborsOfPoint(int pointIndex) {
		List<Integer> neighbors = new ArrayList<Integer>();

		IntStream.range(0, points.size() - 1).filter(neighborIndex -> graph.getMatrix()[pointIndex][neighborIndex] != 0)
				.forEach(neighborIndex -> neighbors.add(neighborIndex));

		return neighbors;
	}

	/*
	 * Updated distances to neighbors of current point.
	 */
	private void updateNeighborDistances(int currentPointIndex) {
		List<Integer> neighbors = getAllNeighborsOfPoint(currentPointIndex);

		double currentPointDistance = this.distances[currentPointIndex];
		double newNeighborDistance = 0.0;

		for (int neighborIndex : neighbors) {
			if (isPassedPoints[neighborIndex]) {
				continue;
			}

			newNeighborDistance = currentPointDistance + getNeighborDistance(currentPointIndex, neighborIndex);

			if (this.distances[neighborIndex] == 0.0 || newNeighborDistance < this.distances[neighborIndex]) {
				this.distances[neighborIndex] = newNeighborDistance;
			}
		}
	}

	/*
	 * Returns distance between two points
	 */
	private Double getNeighborDistance(int begin, int end) {
		return graph.getMatrix()[begin][end];
	}

	/*
	 * Returns the index of a point with minimum distance that is not used yet.
	 */
	private Integer getNotUsedPointWithMinimumDistance() {
		int index = -1;

		double currentDistance = 0.0;
		double minimalDistance = 0.0;

		for (int currentPointIndex = 0; currentPointIndex < points.size(); currentPointIndex++) {
			currentDistance = this.distances[currentPointIndex];

			if (isPassedPoints[currentPointIndex] || currentDistance == 0.0) {
				continue;
			}

			if (minimalDistance == 0.0 || (minimalDistance != 0.0 && minimalDistance > currentDistance)) {
				minimalDistance = currentDistance;
				index = currentPointIndex;
			}
		}

		return index;
	}

	/*
	 * Calculates the minimal burn time from the start point
	 */
	private void calculateOptimimBurnTime() {
		int index = -1;
		int endPoint1 = -1;
		int endPoint2 = -1;
		double timeOfInnition = 0;
		double realBurnTime = 0;
		double minimumBurnTime = 0;

		for (Stick stick : sticks) {
			endPoint1 = points.indexOf(stick.getEnd1());
			endPoint2 = points.indexOf(stick.getEnd2());

			if (endPoint1 == -1 || endPoint2 == -1) {
				continue;
			}

			if (result.getResultDistances()[endPoint1] >= result.getResultDistances()[endPoint2]) {
				timeOfInnition = result.getResultDistances()[endPoint1] - result.getResultDistances()[endPoint2];
				index = endPoint1;
			} else {
				timeOfInnition = result.getResultDistances()[endPoint2] - result.getResultDistances()[endPoint1];
				index = endPoint2;
			}

			realBurnTime = result.getResultDistances()[index] + ((stick.getTime() - timeOfInnition) / 2);

			if (realBurnTime > minimumBurnTime) {
				minimumBurnTime = realBurnTime;
			}
		}

		result.setBurnTime(minimumBurnTime);

	}

	@Override
	public BurnResult call() throws Exception {
		while (true) {
			isPassedPoints[currentPointIndex] = true;

			updateNeighborDistances(currentPointIndex);

			currentPointIndex = getNotUsedPointWithMinimumDistance();

			if (currentPointIndex == -1) {
				break;
			}
		}

		calculateOptimimBurnTime();
		
		return result;
	}
}
