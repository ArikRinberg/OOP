package homework2;
import java.lang.NullPointerException;
import java.util.Iterator;

public interface Graph<T> {
		/**
		 * Add a node with no edges to the graph.
		 * @requires none
		 * @modifies this
		 * @return nothing.
		 * @throws
		 * 	NullPointerException - if node is null
		 *  GraphNodeException - if the node is already in the graph or the 
		 *    node fails to meet the requirements from the graph
		 */
		public void addNode(T node) throws NullPointerException, GraphNodeException;
		
		/**
		 * Adds a directed edge from start node to end node.
		 * @requires none
		 * @modifies this
		 * @return nothing.
		 * @throws
		 *  NullPointerException - if start or end are null
		 *  GraphEdgeException - if edge is already in the graph
		 *  GraphNodeException - if at least one of the nodes isn't in the graph or start=end
		 */
		public void addEdge(T start, T end) throws NullPointerException, GraphEdgeException, GraphNodeException;

		/**
		 * Iterate over the nodes in the graph.
		 * @requires none
		 * @modifies none
		 * @return iterator to the nodes in the graph. 
		 */
		public Iterator<T> getNodes();
		
		/**
		 * Returns an iterator to the children of a node .
		 * @requires none
		 * @modifies none
		 * @return iterator to the nodes in the graph.
		 * @throws
		 *  NullPointerException - parent is null
		 *  GraphNodeException - parent isn't in the graph
		 */
		public Iterator<T> getChildren(T parent) throws NullPointerException, GraphNodeException; 
}