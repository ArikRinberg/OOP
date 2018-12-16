package homework2;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class WeightedNodesGraph implements Graph<WeightedNode> {

	
	
  	// Abstraction Function:
  	// A graph G is NaN if:
  	// 1. There exists a node N in G where N.cost < 0
  	// 2. There exists an edge (N1,N2) in G where N1 is not in G or N2 is not in G
	// 3. There exists an edge (N1,N2) in G where N1 = N2
	// 4. There exists a node N that appears more than once in G
	// Otherwise G represents a graph that is build of nodes and edges, where every node is unique
	//  and has a weight, and the edges connect the nodes.

	//
	// Representation invariant
	// For every node in the graph:
	// 	1. node.cost >= 0
	//  2. For every child of node:
	//       1. child is in the graph
	//       2. an edge (a,b) is in the graph where a=node and b=child
	//  3. node appears only once in the graph 

	private HashMap<WeightedNode, HashSet<WeightedNode>> _weightedGraph;

	/**
  	 * Constructor for WeightedNodesGraph.
     * @requires none
   	 * @effects creates an empty graph.
   	 **/
	public WeightedNodesGraph()
	{
		_weightedGraph = new HashMap<WeightedNode, HashSet<WeightedNode>>();
	}
	
	@Override
	public void addNode(WeightedNode node) throws NullPointerException, GraphNodeException {
		if(node == null) throw new NullPointerException();
		if(_weightedGraph.containsKey(node)) throw new GraphNodeException();
		if(node.getCost() < 0) throw new GraphNodeException();
		_weightedGraph.put(node, new HashSet<WeightedNode>());
		
	}

	@Override
	public void addEdge(WeightedNode start, WeightedNode end)
			throws NullPointerException, GraphEdgeException, GraphNodeException {
		if(start == null || end == null) throw new NullPointerException();
		if(!_weightedGraph.containsKey(start) || !_weightedGraph.containsKey(end))
		{
			throw new GraphNodeException();
		}
		
		if (start.equals(end))
		{
			throw new GraphEdgeException();
		}
		HashSet<WeightedNode> childrenOfStart = _weightedGraph.get(start);
		if (childrenOfStart.contains(end))
		{
			throw new GraphEdgeException();
		}
		childrenOfStart.add(end);
	}

	@Override
	public Iterator<WeightedNode> getNodes() {
		
		//TODO: Alter for deep copy
		List<WeightedNode> nodes = new ArrayList<WeightedNode> (_weightedGraph.keySet());
		return nodes.iterator();
	}

	@Override
	public Iterator<WeightedNode> getChildren(WeightedNode parent) throws NullPointerException, GraphNodeException {
		if (parent == null)
		{
			throw new NullPointerException();
		}
		if (!_weightedGraph.containsKey(parent))
		{
			throw new GraphNodeException();
		}
		
		//TODO: Alter for deep copy
		List<WeightedNode> children = new ArrayList<WeightedNode> (_weightedGraph.get(parent));
		return children.iterator();
	}

}
