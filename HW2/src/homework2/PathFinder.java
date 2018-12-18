package homework2;

import java.util.ArrayList;

public class PathFinder<N> {
	
	public static <N, P extends Path<N, P>> P getShortestPath(Graph<N> g, ArrayList<P> srcNodesPs,
			ArrayList<N> destNodes, DfsAlgorithm<N> dfsAlgorithm)
			throws GraphNodeException, NullPointerException
	{
		P shortestPath = null;
		for	(P path : srcNodesPs)
		{
			// Run dfs to set up the backwards edges
			dfsAlgorithm.DFS(g, path.getEnd());
		}
		return shortestPath;
	}
	
	public static <N, P extends Path<N, P>> P BFS(Graph<N> g, N srcNodesPs, ArrayList<N> destNodes)
	{
		P shortestPath = null;
		
		return shortestPath;
	}
	
	
	public static <T> String getNodeColor(T node) { return null; }
			
	public static <T> boolean getNodeHasBackwardsEdge(T node) { return false; }

}
