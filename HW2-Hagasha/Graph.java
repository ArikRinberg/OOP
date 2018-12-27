package homework2;

import java.lang.NullPointerException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/*
 * A Graph class is a record type which nodes of type T and edges connecting nodes.
 */
public abstract class Graph<T> 
{
	
  	// Abstraction Function:
  	// A graph G is NaN if:
  	// 1. There exists an edge (N1,N2) in G where N1 is not in G or N2 is not in G
	// 2. There exists a node N that appears more than once in G
	// Otherwise G represents a graph that is build of nodes and edges, where every node is unique
	//  and the edges connect the nodes.

	//
	// Representation invariant
	// For every node in the graph:
	//  1. For every child of node:
	//       1. child is in the graph
	//       2. an edge (a,b) is in the graph where a=node and b=child
	//  2. node appears only once in the graph 
	
	private HashMap<String, T> _nodes;
	private HashMap<T, HashSet<T>> _children;
	
	/**
  	 * Constructor for Graph.
     * @requires none
   	 * @effects creates an empty graph.
   	 **/
	public Graph()
	{
		_nodes =  new HashMap<String, T>();
		_children = new HashMap<T, HashSet<T>>();
		
		checkRep();
	}
	
	/**
	 * Add a node with no edges to the graph.
	 * @requires none
	 * @modifies this
	 * @return nothing.
	 * @throws
	 * 	NullPointerException - if node is null
	 *  GraphNodeException - if the node is already in the graph
	 */
	public void addNode(T node) throws NullPointerException, GraphNodeException
	{
		if(node == null)
		{
			throw new NullPointerException();
		}
		
		String nodeName = getNodeName(node);
		if(_nodes.containsKey(getNodeName(node)))
		{
			throw new GraphNodeException();
		}
		
		_nodes.put(nodeName, node);
		_children.put(node, new HashSet<T>());
		
		checkRep();
	}
		
	/**
	 * Adds a directed edge from start node to end node.
	 * @requires none
	 * @modifies this
	 * @return nothing.
	 * @throws
	 *  NullPointerException - if start or end are null
	 *  GraphEdgeException - if edge is already in the graph
	 *  GraphNodeException - if at least one of the nodes isn't in the graph
	 */
	public void addEdge(T start, T end) 
			throws NullPointerException, GraphEdgeException, GraphNodeException
	{
		if(start == null || end == null)
		{
			throw new NullPointerException();
		}
		
		String startName = getNodeName(start);
		String endName = getNodeName(end);
		
		if(!_nodes.containsKey(startName) || !_nodes.containsKey(endName))
		{
			throw new GraphNodeException();
		}
			
		T startNode = _nodes.get(startName);
		HashSet<T> childrenOfStart = _children.get(start);
		
		for (T child : childrenOfStart)
		{
			if (endName.equals(getNodeName(child)))
			{
				throw new GraphEdgeException();
			}
		}
		
		T endNode = _nodes.get(endName);
		childrenOfStart.add(endNode);
		
		checkRep();
	}
	
		/**
	 * Iterate over the nodes in the graph.
	 * @requires none
	 * @modifies none
	 * @return iterator to the nodes in the graph. 
	 */
	public Iterator<T> getNodes()
	{
		List<T> nodes = new ArrayList<T> (_children.keySet());
		return nodes.iterator();
	}
	
	/**
	 * Returns an iterator to the children of a node .
	 * @requires none
	 * @modifies none
	 * @return iterator to the nodes in the graph.
	 * @throws
	 *  NullPointerException - parent is null
	 *  GraphNodeException - parent isn't in the graph
	 */
	public Iterator<T> getChildren(T parent)
			throws NullPointerException, GraphNodeException
	{
		if (parent == null)
		{
			throw new NullPointerException();
		}
		
		String parentName = getNodeName(parent);
		if (!_nodes.containsKey(parentName))
		{
			throw new GraphNodeException();
		}
		
		T parentNode = _nodes.get(parentName);
		List<T> children = new ArrayList<T> (_children.get(parentNode));
		return children.iterator();
	}
	
	protected abstract String getNodeName(T node);
	
	private void checkRep()
	{
		for (T node : _children.keySet())
		{
			for (T child : _children.get(node))
			{
				assert _children.containsKey(child);
			}
		}
	}
}
