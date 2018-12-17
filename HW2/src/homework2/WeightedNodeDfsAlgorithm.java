package homework2;

public class WeightedNodeDfsAlgorithm extends  DfsAlgorithm<WeightedNode>
{

	@Override
	public String getNodeColor(WeightedNode node) {
		if (node == null)
		{
			throw new NullPointerException();
		}
		return node.getColor();
	}

	@Override
	public void setNodeColor(WeightedNode node, String color) {
		if (node == null)
		{
			throw new NullPointerException();
		}
		node.setColor(color);
		
	}

	@Override
	public void setNodeHasBackwardsEdge(WeightedNode node, boolean hasBackwardsEdge) {
		if (node == null)
		{
			throw new NullPointerException();
		}
		node.setHasBackwardsEdge(hasBackwardsEdge);		
	}

}
