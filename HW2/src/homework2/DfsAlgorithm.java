package homework2;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public abstract class DfsAlgorithm<T> {
	
	public LinkedList<T> DFS(Graph<T> graph, T startNode)
			throws NullPointerException, GraphNodeException
	{
		return DFS(graph, startNode, null);
	}
	
	public LinkedList<T> DFS(Graph<T> graph, T startNode, T endNode) 
			throws NullPointerException, GraphNodeException
	{
		LinkedList<T> visited = new LinkedList<T>();
		if (startNode == null)
		{
			return visited;
		}
		
		HashMap<T, Integer> nodeDepths = new HashMap<>();
		
		colorWhite(graph);
		resetBackwardsEdges(graph);
		DFSHelper(graph, startNode, endNode, visited, nodeDepths, 0);
		colorWhite(graph);
		return visited;
	}
	
	private boolean DFSHelper(Graph<T> graph, T startNode, T endNode, LinkedList<T> visited,
			HashMap<T, Integer> nodeDepths, int currentDepth)
			throws NullPointerException, GraphNodeException
	{
		PriorityQueue<T> children = new PriorityQueue<T>(Collections.reverseOrder());
		Iterator<T> iterator = graph.getChildren(startNode);
		while (iterator.hasNext())
		{
			T node = iterator.next();
			children.add(node);
		}
		
		visited.add(startNode);
		setNodeColor(startNode, "Grey");
		nodeDepths.put(startNode, currentDepth);
		
		if (startNode.equals(endNode))
		{
			return true;
		}
		
		for (T child : children)
		{
			if (getNodeColor(child).equals("White"))
			{
				if (DFSHelper(graph, child, endNode, visited, nodeDepths, currentDepth+1))
				{
					return true;
				}
			}
			else
			{
				if (nodeDepths.get(startNode) > nodeDepths.get(child) || startNode.equals(child))
				{
					setNodeHasBackwardsEdge(startNode, true);
				}
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
