

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class GraphSearches 
{
	/**
	 * Performs Breadth First Search
	 * @param g
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static Node performBFS(Graph g, int x1, int y1, int x2, int y2, boolean debug)
	{
		Node start = g.coordinateMap.get(x1+"_"+y1);
		start.parent = null;
		start.d = 0;
		g.visited[start.i] = true;
		g.verticesAnalyzed++;
		
		Queue<Node> q = new LinkedList<Node>();
		q.add(start);
		
		while(q.peek() != null)
		{
			g.searchIterations++;
			Node analyzing = q.poll();
			
			if(debug)
			System.out.println("iter="+g.searchIterations+", frontier="+q.size()+
					", popped="+analyzing.i +" ("+g.vertices[analyzing.i].substring(0,g.vertices[analyzing.i].indexOf("_"))
					+","+g.vertices[analyzing.i].substring(g.vertices[analyzing.i].indexOf("_")+1)+")"+", depth="+analyzing.d
					+", dist2goal="+String.format("%.02f", g.getDistanceBetweenNodes(Integer.parseInt(g.vertices[analyzing.i]
							.substring(0,g.vertices[analyzing.i].indexOf("_"))),Integer.parseInt(g.vertices[analyzing.i]
							.substring(g.vertices[analyzing.i].indexOf("_")+1)),x2,y2 )));
			
			
			if((x2+"_"+y2).equals(g.vertices[analyzing.i]))
			{
				return analyzing;
			}
			List<Node> adjacencyList = analyzing.adjacencyList;
			for(Node n: adjacencyList)
			{
				if(!g.visited[n.i])
				{			
					g.verticesAnalyzed++;
					
					g.visited[n.i] = true;
					n.parent = analyzing;
					n.d = analyzing.d +1;
					q.add(n);
					if(debug)
					System.out.println("pushed "+n.i+" ("+g.vertices[n.i]
							.substring(0,g.vertices[n.i].indexOf("_"))+","
							+g.vertices[n.i].substring(g.vertices[n.i].indexOf("_")+1)+")");
				}
				
				g.updateMaxFrontierSize(q.size());				
			}
			
		}
		return null;		
	}
	
	/**
	 * Performs Greedy Best First Search
	 * @param g
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static Node performGBFS(Graph g, int x1, int y1, int x2, int y2, boolean debug)
	{
		Node start = g.coordinateMap.get(x1+"_"+y1);
		start.parent = null;
		start.d = 0;
		start.heuristic = g.getDistanceBetweenNodes(start, g.coordinateMap.get(x2+"_"+y2));
		g.visited[start.i] = true;
		g.verticesAnalyzed++;
		
		PriorityQueue<Node> q = new PriorityQueue<Node>(11,new HeuristicsComparator());
		q.add(start);
		
		while(q.peek() != null)
		{
			g.searchIterations++;
			Node analyzing = q.poll();
			
			if(debug)
				System.out.println("iter="+g.searchIterations+", frontier="+q.size()+
						", popped="+analyzing.i +" ("+g.vertices[analyzing.i].substring(0,g.vertices[analyzing.i].indexOf("_"))
						+","+g.vertices[analyzing.i].substring(g.vertices[analyzing.i].indexOf("_")+1)+")"+", depth="+analyzing.d
						+", dist2goal="+String.format("%.02f", g.getDistanceBetweenNodes(Integer.parseInt(g.vertices[analyzing.i]
								.substring(0,g.vertices[analyzing.i].indexOf("_"))),Integer.parseInt(g.vertices[analyzing.i]
								.substring(g.vertices[analyzing.i].indexOf("_")+1)),x2,y2 )));
			
			
			if((x2+"_"+y2).equals(g.vertices[analyzing.i]))
			{
				return analyzing;
			}
			List<Node> adjacencyList = analyzing.adjacencyList;
			for(Node n: adjacencyList)
			{
				if(!g.visited[n.i])
				{
					g.verticesAnalyzed++;	
					g.visited[n.i] = true;
					n.parent = analyzing;
					n.d = analyzing.d +1;
					String coordinate = g.vertices[n.i];
					int xn = Integer.parseInt(coordinate.substring(0,coordinate.indexOf("_")));
					int yn = Integer.parseInt(coordinate.substring(coordinate.indexOf("_")+1));
					
					n.heuristic = g.getDistanceBetweenNodes(xn,yn, x2, y2);
					q.add(n);
					if(debug)
						System.out.println("pushed "+n.i+" ("+g.vertices[n.i]
								.substring(0,g.vertices[n.i].indexOf("_"))+","
								+g.vertices[n.i].substring(g.vertices[n.i].indexOf("_")+1)+")");
					
				}
				
			}
			g.updateMaxFrontierSize(q.size());
		}
		return null;		
	}
	
	/**
	 * Performs Depth First Search
	 * @param g
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static Node performDFS(Graph g, int x1, int y1, int x2, int y2, boolean debug)
	{
		Node start = g.coordinateMap.get(x1+"_"+y1);
		start.parent = null;
		start.d = 0;
		g.visited[start.i] = true;
		g.verticesAnalyzed++;
		
		Stack<Node> s = new Stack<Node>();
		s.push(start);
		while(!s.isEmpty())
		{
			g.searchIterations++;
			Node analyzing = s.pop();
			
			if(debug)
				System.out.println("iter="+g.searchIterations+", frontier="+s.size()+
						", popped="+analyzing.i +" ("+g.vertices[analyzing.i].substring(0,g.vertices[analyzing.i].indexOf("_"))
						+","+g.vertices[analyzing.i].substring(g.vertices[analyzing.i].indexOf("_")+1)+")"+", depth="+analyzing.d
						+", dist2goal="+String.format("%.02f",g.getDistanceBetweenNodes(Integer.parseInt(g.vertices[analyzing.i]
								.substring(0,g.vertices[analyzing.i].indexOf("_"))),Integer.parseInt(g.vertices[analyzing.i]
								.substring(g.vertices[analyzing.i].indexOf("_")+1)),x2,y2 )));
			
			
			if((x2+"_"+y2).equals(g.vertices[analyzing.i]))
			{
				return analyzing;
			}
			List<Node> adjacencyList = analyzing.adjacencyList;
			for(Node n: adjacencyList)
			{
				if(!g.visited[n.i])
				{
					g.verticesAnalyzed++;
					g.visited[n.i] = true;
					n.parent = analyzing;
					n.d = analyzing.d +1;
					s.push(n);	
					if(debug)
						System.out.println("pushed "+n.i+" ("+g.vertices[n.i]
								.substring(0,g.vertices[n.i].indexOf("_"))+","
								+g.vertices[n.i].substring(g.vertices[n.i].indexOf("_")+1)+")");
				}
			}
			g.updateMaxFrontierSize(s.size());
		}
		return null;
	}
	
}
