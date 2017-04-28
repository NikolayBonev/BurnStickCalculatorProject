package calculator;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

import domain.*;
import graph.Graph;

public class BurnStickCalculator {
	private Graph graph;
	private List<Point> points;
	private List<Stick> sticks;

	private List<BurnResult> burnResults = new ArrayList<BurnResult>();

	public BurnStickCalculator(Graph graph, List<Point> points, List<Stick> sticks) {
		this.graph = graph;
		this.points = points;
		this.sticks = sticks;
	}

	/*
	 * Calculates the optimal burn times.
	 */
	public void calculate() {
		calculateBurnTimes();
		findOptimalBurnResults();
	}
	
	/*
	 * Creates and executes a Callable object to find the burn time from every start point with integer coordinates.
	 */
	private void calculateBurnTimes() {
		ExecutorService workers = Executors.newCachedThreadPool();
		List<Callable<BurnResult>> tasks = new ArrayList<Callable<BurnResult>>();
		
		IntStream.range(0, points.size() -1)
		.filter(pointIndex -> points.get(pointIndex).getCoordinateX() % 1 == 0)
		.forEach(pointIndex -> tasks.add(new BurnStickThread(points, sticks, graph, pointIndex)));
				
		List<Future<BurnResult>> resutlDistances = null;
		try {
			resutlDistances = workers.invokeAll(tasks);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		resutlDistances.stream().forEach(resultDistance -> {
			try {
				burnResults.add(resultDistance.get());
			} catch (InterruptedException | ExecutionException e1) {
				e1.printStackTrace();
			}
		});
		
		workers.shutdown();
	}

	/*
	 * Removes all results that aren't with minimal time.
	 */
	private void findOptimalBurnResults(){
		
		BurnResult optimalResult = burnResults.get(0);
		List<BurnResult> notOptimalResults = new ArrayList<BurnResult>();
		
		for(BurnResult currentResult : burnResults) {
			if(currentResult.getBurnTime() < optimalResult.getBurnTime()) {
				optimalResult = currentResult;
				notOptimalResults.add(optimalResult);
			}else if(currentResult.getBurnTime() > optimalResult.getBurnTime()) {
				notOptimalResults.add(currentResult);
			}
		}
		
		burnResults.removeAll(notOptimalResults);
	}

	/*
	 * Prints all start point coordinates where the time minimum.
	 */
	public void printResults() {

		if (burnResults.size() == 1) {
			System.out.println("There is one point with optimum position: ");
		} else if (burnResults.size() > 1) {
			System.out.println("There are " + burnResults.size() + " points with optimum positions: ");
		}

		burnResults.forEach((item)->System.out.printf("Coordinate X : %f Coordinate Y : %f  Time : %f\n",
					item.getStartBurnPoint().getCoordinateX(), item.getStartBurnPoint().getCoordinateY(),
					item.getBurnTime()));
	}
}
