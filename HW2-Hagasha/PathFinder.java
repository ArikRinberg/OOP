package homework2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class PathFinder<N> {
		
	/**
	 * finds the shortest path between 2 nodes from a set of start nodes and a set of end nodes
	 * @requires g!=null, srcNodesPs!=null, destNodes!=null, dfsAlgorithm!=null
	 * @modifies g
	 * @return the shortest path
	 * @throws NullPointerException - node==null || color==null
	 */
	public static <N, P extends Path<N, P>> P getShortestPath(Graph<N> g, ArrayList<P> srcNodesPs,
			ArrayList<N> destNodes, DfsAlgorithm<N> dfsAlgorithm)
			throws GraphNodeException, NullPointerException
	{
		P shortestPath = null;
		for	(P path : srcNodesPs)
		{
			for (N destNode : destNodes)
			{
				P currentDfsPath = getDfsPath(g, path, destNode, dfsAlgorithm);
				if (currentDfsPath == null)
				{
					continue;
				}
				if (shortestPath == null || shortestPath.getCost() > currentDfsPath.getCost())
				{
					shortestPath = currentDfsPath;
				}
			}
		}
		return shortestPath;
	}
	
	private static <N, P extends Path<N, P>> P getDfsPath(Graph<N> g, P srcNodeP,
			N destNode, DfsAlgorithm<N> dfsAlgorithm) 
					throws NullPointerException, GraphNodeException
	{
		N srcNode = srcNodeP.getEnd();		
		LinkedList<N> visitedNodes = dfsAlgorithm.DFS(g, srcNode, destNode);
		if (!visitedNodes.contains(destNode))
		{
			return null;
		}
		
		for (N node : visitedNodes)
		{
			if (node.equals(srcNode))
			{
				continue;
			}
			srcNodeP = srcNodeP.extend(node);
		}
		
		return srcNodeP;
	}
}
