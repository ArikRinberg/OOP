package homework2;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public abstract class DfsAlgorithm<T> {
	
	public LinkedList<T> visited;
	
	public boolean DFS(Graph<T> graph, T startNode)
			throws NullPointerException, GraphNodeException
	{
		return DFS(graph, startNode, null);
	}
	
	public boolean DFS(Graph<T> graph, T startNode, T endNode) 
			throws NullPointerException, GraphNodeException
	{
		visited = new LinkedList<T>();
		if (startNode == null)
		{
			return false;
		}
		
		colorWhite(graph);
		resetBackwardsEdges(graph);
		boolean result = DFSHelper(graph, startNode, endNode);
		colorWhite(graph);
		return result;
	}
	
	private boolean DFSHelper(Graph<T> graph, T startNode, T endNode)
			throws NullPointerException, GraphNodeException
	{
		PriorityQueue<T> children = new PriorityQueue<T>();
		Iterator<T> iterator = graph.getChildren(startNode);
		while (iterator.hasNext())
		{
			T node = iterator.next();
			children.add(node);
		}
		
		visited.add(startNode);
		setNodeColor(startNode, "Grey");
		
		if (startNode.equals(endNode))
		{
			return true;
		}
		
		for (T child : children)
		{
			if (getNodeColor(child).equals("White"))
			{
				if (DFSHelper(graph, child, endNode))
				{
					return true;
				}
			}
			else
			{
				setNodeHasBackwardsEdge(startNode, true);
			}
		}
		
		setNodeColor(startNode, "Black");
		
		return false;
	}
	
	public abstract String getNodeColor(T node);
	
	public abstract void setNodeColor(T node, String color);
		
	public abstract void setNodeHasBackwardsEdge(T node, boolean hasBackwardsEdge);

	
	private void colorWhite(Graph<T> graph)
	{
		Iterator<T> iterator = graph.getNodes();
		while (iterator.hasNext())
		{
			T node = iterator.next();
			setNodeColor(node, "White");
		}
	}
	
	private void resetBackwardsEdges(Graph<T> graph)
	{
		Iterator<T> iterator = graph.getNodes();
		while (iterator.hasNext())
		{
			T node = iterator.next();
			setNodeHasBackwardsEdge(node, false);
		}
	}

	
}
