package graph;
import java.util.*;

import domain.*;

/*
 * This class creates a Graph class and sets the lists of points and sticks needed.
 * Returns a Graph object.
 */

public class GraphCreator {
	public static Graph create(List<Point> points, List<Stick> sticks){
		Graph graph = new Graph(points.size());
		
		graph.setPoints(points);
		graph.addSticksWeights(sticks);
		
		return graph;
	}
}
