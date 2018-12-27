package homework2;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.LinkedList;

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
	
	@Test
	public void TestIterators() throws NullPointerException, GraphNodeException, GraphEdgeException
	{
		WeightedNodesGraph graph = new WeightedNodesGraph();
		WeightedNode A = new WeightedNode("A", 2);
		WeightedNode B = new WeightedNode("B", 3);
		WeightedNode D = new WeightedNode("D", 1);
		WeightedNode G = new WeightedNode("G", 3);
		graph.addNode(A);
		graph.addNode(B);
		graph.addNode(D);
		graph.addNode(G);
		
		graph.addEdge(A, B);
		graph.addEdge(A, D);
		graph.addEdge(A, G);
		graph.addEdge(B, A);
		graph.addEdge(G, D);
		
		var children = graph.getChildren(A);
		
		assert(children.hasNext());
		assertEquals(B ,children.next());
		assert(children.hasNext());
		assertEquals(D ,children.next());
		assert(children.hasNext());
		assertEquals(G ,children.next());
		assert(!children.hasNext());
	}
	
	@Test
	public void TestDFSSearch() throws NullPointerException, GraphNodeException, GraphEdgeException
	{
		WeightedNodesGraph graph = new WeightedNodesGraph();
		WeightedNode A = new WeightedNode("A", 2);
		WeightedNode B = new WeightedNode("B", 3);
		WeightedNode E = new WeightedNode("E", 2);
		WeightedNode D = new WeightedNode("D", 1);
		WeightedNode G = new WeightedNode("G", 3);
		WeightedNode R = new WeightedNode("R", 4);
		graph.addNode(A);
		graph.addNode(B);
		graph.addNode(E);
		graph.addNode(D);
		graph.addNode(G);
		graph.addNode(R);
		
		graph.addEdge(A, B);
		graph.addEdge(A, D);
		graph.addEdge(A, G);
		
		graph.addEdge(B, A);
		graph.addEdge(B, E);
		
		graph.addEdge(G, D);
		graph.addEdge(G, R);
		
		WeightedNodeDfsAlgorithm dfs = new WeightedNodeDfsAlgorithm();
		
		LinkedList<WeightedNode> visited = dfs.DFS(graph, A, D);
		assertEquals(4, visited.size());
		assertEquals(A, visited.get(0));
		assertEquals(G, visited.get(1));
		assertEquals(R, visited.get(2));
		assertEquals(D, visited.get(3));
		
		visited = dfs.DFS(graph, B);
		assertEquals(6, visited.size());
		assertEquals(B, visited.get(0));
		assertEquals(E, visited.get(1));
		assertEquals(A, visited.get(2));
		assertEquals(G, visited.get(3));
		assertEquals(R, visited.get(4));
		assertEquals(D, visited.get(5));
	}
	
	@Test
	public void TestPathFinder() throws NullPointerException, GraphNodeException, GraphEdgeException
	{
		WeightedNodesGraph graph = new WeightedNodesGraph();
		WeightedNode A = new WeightedNode("A", 2);
		WeightedNode B = new WeightedNode("B", 3);
		WeightedNode C = new WeightedNode("C", 2);
		WeightedNode D = new WeightedNode("D", 2);
		graph.addNode(A);
		graph.addNode(B);
		graph.addNode(C);
		
		graph.addEdge(A, B);
		graph.addEdge(B, C);
		graph.addEdge(C, A);
		
		WeightedNodeDfsAlgorithm dfs = new WeightedNodeDfsAlgorithm();
		
		ArrayList<NodeCountingPath> startPaths = new ArrayList<>();
		NodeCountingPath startPath = new NodeCountingPath(A);
		startPaths.add(startPath);
		
		ArrayList<WeightedNode> destNodes = new ArrayList<>();
		destNodes.add(C);
		NodeCountingPath shortestPath = PathFinder.getShortestPath(graph, startPaths, destNodes, dfs);
		assertEquals(4.0, shortestPath.getCost(), 0);
		
		destNodes = new ArrayList<>();
		destNodes.add(B);
		shortestPath = PathFinder.getShortestPath(graph, startPaths, destNodes, dfs);
		assertEquals(2.0, shortestPath.getCost(), 0);
		
		graph = new WeightedNodesGraph();
		graph.addNode(A);
		graph.addNode(B);
		graph.addNode(C);
		graph.addNode(D);
		
		graph.addEdge(A, B);
		graph.addEdge(A, C);
		
		destNodes = new ArrayList<>();
		destNodes.add(C);
		shortestPath = PathFinder.getShortestPath(graph, startPaths, destNodes, dfs);
		assertEquals(3.0, shortestPath.getCost(), 0);
		
		destNodes = new ArrayList<>();
		destNodes.add(B);
		shortestPath = PathFinder.getShortestPath(graph, startPaths, destNodes, dfs);
		assertEquals(2.0, shortestPath.getCost(), 0);
		
		destNodes = new ArrayList<>();
		destNodes.add(D);
		shortestPath = PathFinder.getShortestPath(graph, startPaths, destNodes, dfs);
		assertEquals(null, shortestPath);
	}
}
