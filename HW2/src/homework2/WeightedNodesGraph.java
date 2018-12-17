package homework2;

public class WeightedNodesGraph extends Graph<WeightedNode> {
	@Override
	public void addNode(WeightedNode node) throws NullPointerException, GraphNodeException 
	{
		if(node == null)
		{
			throw new NullPointerException();
		}
		
		super.addNode(node);
	}
}
