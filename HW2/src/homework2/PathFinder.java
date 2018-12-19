package homework2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class PathFinder<N> {
		
	public static <N, P extends Path<N, P>> P getShortestPath(Graph<N> g, ArrayList<P> srcNodesPs,
			ArrayList<N> destNodes, DfsAlgorithm<N> dfsAlgorithm)
			throws GraphNodeException, NullPointerException
	{
		P shortestPath = null;
		for	(P path : srcNodesPs)
		{
			HashSet<N> visitedNodes = new HashSet<>();
			
			// Run DFS to set up the backwards edges
			dfsAlgorithm.DFS(g, path.getEnd());
			visitedNodes.add(path.getEnd());
			
			// Setup for findShortest
			ArrayList<P> openPaths = new ArrayList<>();
			openPaths.add(path);
			
			P shortestFromStart = findShortest(g, openPaths, destNodes, visitedNodes);

			if (shortestPath == null || shortestFromStart.getCost() < shortestPath.getCost())
			{
				shortestPath = shortestFromStart;
			}
		}
		return shortestPath;
	}
	
	private static <N, P extends Path<N, P>> P findShortest(Graph<N> g, ArrayList<P> openPaths,
			ArrayList<N> destNodes, HashSet<N> visitedNodes) throws NullPointerException, GraphNodeException
	{
		P currentPath = getLowestCost(openPaths);
		if (currentPath == null)
		{
			return null;
		}
		
		openPaths.remove(currentPath);
		if (destNodes.contains(currentPath.getEnd()))
		{
			return currentPath;
		}
		
		Iterator<N> chidlrenIterator = g.getChildren(currentPath.getEnd());
		while (chidlrenIterator.hasNext())
		{
			N child = chidlrenIterator.next();
			if (!visitedNodes.contains(child))
			{
				visitedNodes.add(child);
				P pathWithChild = currentPath.extend(child);
				openPaths.add(pathWithChild);
			}
			// else we've got to the child from a shorter path
		}
		
		return findShortest(g, openPaths, destNodes, visitedNodes);
	}
	
	private static <N, P extends Path<N, P>> P getLowestCost(ArrayList<P> paths)
	{
		P shortestPath = null;
		for (P path : paths)
		{
			if (shortestPath == null || path.getCost() < shortestPath.getCost())
			{
				shortestPath = path;
			}
		}
		return shortestPath;
	}
}
