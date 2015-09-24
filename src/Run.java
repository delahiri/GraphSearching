

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Run {

	private static final String DFS_NAV = "DFS_nav";
	private static final String GBFS_NAV = "GBFS_nav";
	private static final String BFS_NAV = "BFS_nav";

	public static void main(String[] args) 
	{
		if(args.length < 6)
		{
			System.out.println("Incomplete Arguments Enter: Search Algorithm,File Path and Coordinates");
		}
		else
		{
		boolean debug = args.length == 7 && "d".equalsIgnoreCase(args[6]) ? true:false; 
		String technique = args[0];
		String fileName = args[1];
		BufferedReader br= null;
		int xs = Integer.parseInt(args[2]);
		int ys = Integer.parseInt(args[3]);
		int xf = Integer.parseInt(args[4]);
		int yf = Integer.parseInt(args[5]);
		try
		{
			br = new BufferedReader(new FileReader(fileName));

		    String line = br.readLine();
		    
		    if(line.toLowerCase().startsWith("vertices: "))
		    {
		    	int noOfVertices = Integer.parseInt(line.substring(10));
		    	Graph g = new Graph(noOfVertices);
		    	line = br.readLine();
		    	
			    while (line != null && !line.toLowerCase().startsWith("edges: ")) 
			    {
			       String[] sp = line.split(" ");
			       int index = Integer.parseInt(sp[0]);
			       int x = Integer.parseInt(sp[1]);
			       int y = Integer.parseInt(sp[2]);
			       
			       g.addVertex(index, x+"_"+y);
			       line = br.readLine();
			    }
			    int edges = Integer.parseInt(line.substring(7));
			    line = br.readLine();
			    while(line != null)
			    {
			    	String[] sp = line.split(" ");
			    	int index1 = Integer.parseInt(sp[1]);
			    	int index2 = Integer.parseInt(sp[2]);	
			    	g.addEdge(index1, index2);
			    	line = br.readLine();
			    }
			    if(debug)
			    {
			    	System.out.println("vertices="+noOfVertices+", edges="+edges);
			    	System.out.println("start=("+xs+","+ys+"), goal=("+xf+","+yf+"),"+" vertices: "+g.coordinateMap.get(xs+"_"+ys).i+" and "+g.coordinateMap.get(xf+"_"+yf).i);
			    }
			    
			    Node goal = null;
			    if(BFS_NAV.equalsIgnoreCase(technique))
			    	 goal = GraphSearches.performBFS(g, xs, ys, xf, yf,debug);
			    else if(GBFS_NAV.equalsIgnoreCase(technique))
			    	 goal = GraphSearches.performGBFS(g, xs, ys, xf, yf,debug);
			    else if(DFS_NAV.equalsIgnoreCase(technique))
			    	 goal = GraphSearches.performDFS(g, xs, ys, xf, yf,debug);
			    else
			    	System.out.println("No Such Search Algorithm, Please enter in BFS_nav, GBFS_nav, DFS_nav");
			    if(goal != null)
			    {
				    List<Node> pathInReverse = goal.traceback();
				    for(int i= pathInReverse.size()-1; i>=0;i--)
				    {
				    	Node n = pathInReverse.get(i);
				    	String address = g.vertices[n.i];
				    	int x = Integer.parseInt(address.substring(0,address.indexOf("_")));
				    	int y = Integer.parseInt(address.substring(address.indexOf("_")+1));
				    //	System.out.println("Vertex "+n.i+" ("+x+","+y+")");	 
				    	System.out.println(x+" "+y);
				    }
			    	System.out.println("================================");
			    	System.out.println("Search Algorithm "+technique.substring(0,technique.indexOf("_nav")));
			    	System.out.println("Total iterations "+g.searchIterations);
			    	System.out.println("Maximum Frontier size: "+g.maxFrontierSize);
			    	System.out.println("Vertices visited: "+g.verticesAnalyzed+"/"+g.vertices.length);
				    System.out.println("Path Length: "+(pathInReverse.size()-1));
				    System.out.println("================================");
			    }
			    else
			    {
			    	System.out.println("No Path Found, Please check source, destination vertices and search algorithm entered");
			    }
			    
		    }
		    else
		    {
		    	System.out.println("Input File format not correct");
		    }
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File Not Found : "+ args[0]);
		}
		catch(IOException e)
		{
			System.out.println("Error while reading file");
		}
		finally 
		{
		    try {
		    		if(br != null)
					br.close();
				} 
		    catch (IOException e) 
		    {
				System.out.println("Error while closing File");
			}
		}
		
		
		}
	}

}
