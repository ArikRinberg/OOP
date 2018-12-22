package homework2;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;


/**
 * This class contains a set of test cases that can be used to test the graph
 * and shortest path finding algorithm implementations of homework assignment
 * #2.
 */
public class GraphTests extends ScriptFileTests 
{

	// black-box test are inherited from super
	public GraphTests(java.nio.file.Path testFile) 
	{
		super(testFile);
	}

	@Test
	public void TestAddExistingNode() throws NullPointerException, GraphNodeException
	{
		WeightedNodesGraph graph = new WeightedNodesGraph();
		WeightedNode n1 = new WeightedNode("n1", 0);
		WeightedNode n1DifferentCost = new WeightedNode("n1", 10);
		
		graph.addNode(n1);
		assertThrows(GraphNodeException.class,() -> { graph.addNode(n1); });
		assertThrows(GraphNodeException.class,() -> { graph.addNode(n1DifferentCost); });
	}
	
	@Test
	public void TestAddNullNode() throws NullPointerException, GraphNodeException
	{
		WeightedNodesGraph graph = new WeightedNodesGraph();
		
		assertThrows(NullPointerException.class,() -> { graph.addNode(null); });
	}
	
	@Test
	public void TestAddEdgeWithNulls() throws NullPointerException, GraphNodeException, GraphEdgeException
	{
		WeightedNodesGraph graph = new WeightedNodesGraph();
		WeightedNode n = new WeightedNode("n", 0);

		assertThrows(NullPointerException.class,() -> { graph.addEdge(null, null); });
		assertThrows(NullPointerException.class,() -> { graph.addEdge(n, null); });
		assertThrows(NullPointerException.class,() -> { graph.addEdge(null, n); });
	}
	
	@Test
	public void TestAddExistingEdge() throws NullPointerException, GraphNodeException, GraphEdgeException
	{
		WeightedNodesGraph graph = new WeightedNodesGraph();
		WeightedNode n1 = new WeightedNode("n1", 0);
		WeightedNode n2 = new WeightedNode("n2", 0);
		graph.addNode(n1);
		graph.addNode(n2);
		
		graph.addEdge(n1, n2);
		assertThrows(GraphEdgeException.class,() -> { graph.addEdge(n1, n2); });
	}
	
	@Test
	public void TestAddEdgeWithoutNodes() throws NullPointerException, GraphNodeException, GraphEdgeException
	{
		WeightedNodesGraph graph = new WeightedNodesGraph();
		WeightedNode n1 = new WeightedNode("n1", 0);
		WeightedNode n2 = new WeightedNode("n2", 0);
		graph.addNode(n2);
		
		assertThrows(GraphNodeException.class,() -> { graph.addEdge(n1, n2); });
		assertThrows(GraphNodeException.class,() -> { graph.addEdge(n2, n1); });
	}
	
	//TODO: Add tests to check iterators
}
