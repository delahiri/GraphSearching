

import java.util.ArrayList;
import java.util.List;


public class Node 
{	int i; // index in vertices array that is kept in Graphs
	Node parent;
	int d;
	List<Node> adjacencyList;
	double heuristic;
	
	public Node(int i)
	{
		this.i = i;
	}
	
	public Node(int i, Node parent)
	{
		this.i = i;
		this.parent = parent;
	}
	
	
	
	public List<Node> successors()
	{
		return adjacencyList;
	}
	
	public List<Node> traceback()
	{
		List<Node> trace = new ArrayList<Node>();
		Node head = this;
		while(head != null)
		{
			trace.add(head);
			head = head.parent;
		}
		return trace;
	}
}
