

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Graph {
	String[] vertices;
	int verticesAnalyzed;
	Map<String, Node> coordinateMap;
	boolean[] visited;
	int maxFrontierSize;
	int searchIterations;
	
	public Graph(int noOfVertices)
	{
		vertices = new String[noOfVertices];
		visited = new boolean[noOfVertices];
		coordinateMap = new HashMap<String, Node>();
		verticesAnalyzed = 0;
		maxFrontierSize=0;
		searchIterations=0;
	}
	
	public void addVertex(int id, String x_y)
	{
		vertices[id] = x_y;
		visited[id] = false;
		Node vertex = new Node(id);
		coordinateMap.put(x_y, vertex);
	}
	
	public void addEdge(int index1, int index2)
	{	
		Node vertex1 = coordinateMap.get(vertices[index1]);
		Node vertex2 = coordinateMap.get(vertices[index2]);
		
		if(vertex1.adjacencyList == null)
		{
			vertex1.adjacencyList = new LinkedList<Node>();
		}
		if(vertex2.adjacencyList == null)
		{
			vertex2.adjacencyList = new LinkedList<Node>();
		}
		
		vertex1.adjacencyList.add(vertex2);
		vertex2.adjacencyList.add(vertex1);
	}
	
	public double getDistanceBetweenNodes(Node n1, Node n2)
	{
		int x1 = Integer.parseInt(vertices[n1.i].substring(0,vertices[n1.i].indexOf("_")));
    	int y1 = Integer.parseInt(vertices[n1.i].substring(vertices[n1.i].indexOf("_")+1));
    	
    	int x2 = Integer.parseInt(vertices[n2.i].substring(0,vertices[n2.i].indexOf("_")));
    	int y2 = Integer.parseInt(vertices[n2.i].substring(vertices[n2.i].indexOf("_")+1));
    	
    	return Math.sqrt(Math.pow(x1-x2, 2)+ Math.pow(y1-y2, 2));
    	
    	
    	
	}
	
	public double getDistanceBetweenNodes(int x1, int y1, int x2, int y2)
	{	
    	return Math.sqrt(Math.pow(x1-x2, 2)+ Math.pow(y1-y2, 2));  	   	
	}
	
	public void updateMaxFrontierSize(int size)
	{
		this.maxFrontierSize = Math.max(size,this.maxFrontierSize);
	}
	
}
