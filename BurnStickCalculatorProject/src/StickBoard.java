import java.util.*;

import calculator.*;
import domain.*;
import graph.*;
import readers.*;

public class StickBoard {

	public static void main(String[] args) {
		ParametersReader reader = ParametersReaderFactory.createReader("ParametersTextFileReader");
		
		System.out.println("Enter path to test:");
		
		Scanner sc = new Scanner(System.in);
		String url = sc.nextLine();
		sc.close();
		
		List<Point> points= new ArrayList<>();
		List<Stick> sticks = new ArrayList<>();
		
		reader.read(url, points, sticks);
				
		System.out.println("Total count of points : " + points.size());
		System.out.println("Total count of sticks : " + sticks.size());
		
		Graph graph = GraphCreator.create(points, sticks);
		BurnStickCalculator calulator = new BurnStickCalculator(graph, points, sticks);
		
		calulator.calculate();
		
		System.out.println();
		
		calulator.printResults();
	}
}
