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
		if (graph == null || startNode == null)
		{
			throw new NullPointerException();
		}
		
		// Reset the color and backwards edges
		colorWhite(graph);
		resetBackwardsEdges(graph);
		
		// Run DFS
		DFSHelper(graph, startNode, endNode, visited);
		
		// Reset the color
		colorWhite(graph);
		return visited;
	}
	
	private boolean DFSHelper(Graph<T> graph, T startNode, T endNode, LinkedList<T> visited)
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
		
		// First check for backward edges
		for (T child : children)
		{
			if (getNodeColor(child).equals("Grey"))
			{
				setNodeHasBackwardsEdge(startNode, true);
			}
		}
		
		// Check if we're done
		if (startNode.equals(endNode))
		{
			return true;
		}
		
		// Recursive DFS on each of the children
		for (T child : children)
		{
			if (getNodeColor(child).equals("White") && 
					DFSHelper(graph, child, endNode, visited))
			{
				return true;
			}
		}
		
		// We're done with this node, paint it black
		setNodeColor(startNode, "Black");
		
		// Didn't find the destination :(
		return false;
	}
	
	/**
	 * Get the color of a node
	 * @requires none
	 * @modifies node
	 * @return node color.
	 * @throws NullPointerException - node==null
	 */
	public abstract String getNodeColor(T node) throws NullPointerException;
	
	/**
	 * Set the color of a node
	 * @requires none
	 * @modifies none
	 * @return none.
	 * @throws NullPointerException - node==null || color==null
	 */
	public abstract void setNodeColor(T node, String color) throws NullPointerException;
		
	/**
	 * Set if the node has backwards edges or not
	 * @requires none
	 * @modifies node
	 * @return none.
	 * @throws NullPointerException - node==null
	 */
	public abstract void setNodeHasBackwardsEdge(T node, boolean hasBackwardsEdge)
			throws NullPointerException;

	/**
	 * Sets all node colors to white in graph 
	 * @requires none
	 * @modifies graph
	 * @return none.
	 */
	private void colorWhite(Graph<T> graph)
	{
		Iterator<T> iterator = graph.getNodes();
		while (iterator.hasNext())
		{
			T node = iterator.next();
			setNodeColor(node, "White");
		}
	}
	
	/**
	 * Sets all backwards edges in graph to false
	 * @requires none
	 * @modifies graph
	 * @return none.
	 */
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
