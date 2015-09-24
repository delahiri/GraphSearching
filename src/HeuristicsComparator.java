

import java.util.Comparator;

public class HeuristicsComparator implements Comparator<Node>
{

	public int compare(Node n1, Node n2) {
		if (n1.heuristic < n2.heuristic)
		{
			return -1;
		}
		else if( n2.heuristic < n1.heuristic)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}

}
