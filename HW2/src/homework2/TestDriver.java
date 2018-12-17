package homework2;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


/**
 * This class implements a testing driver which reads test scripts
 * from files for testing Graph and PathFinder.
 */
public class TestDriver {

	// String -> Graph: maps the names of graphs to the actual graph
	// TODO: Parameterize the next line correctly.
  	private final Map<String, WeightedNodesGraph> graphs = new HashMap<>();
  	// String -> WeightedNode: maps the names of nodes to the actual node
  	private final Map<String,WeightedNode> nodes = new HashMap<>();
	private final BufferedReader input;
  	private final PrintWriter output;


  	/**
  	 * Creates a new TestDriver.
     * @requires r != null && w != null
     * @effects Creates a new TestDriver which reads command from
     * <tt>r</tt> and writes results to <tt>w</tt>.
     */
  	public TestDriver(Reader r, Writer w) {
    	input = new BufferedReader(r);
    	output = new PrintWriter(w);
  	}


  	/**
  	 * Executes the commands read from the input and writes results to the
  	 * output.
     * @effects Executes the commands read from the input and writes
     * 		    results to the output.
     * @throws IOException - if the input or output sources encounter an
     * 		   IOException.
     */
  	public void runTests() throws IOException {

    	String inputLine;
		while ((inputLine = input.readLine()) != null) {
			// echo blank and comment lines
      		if (inputLine.trim().length() == 0 ||
      		    inputLine.charAt(0) == '#') {
        		output.println(inputLine);
        		continue;
      		}

      		// separate the input line on white space
      		StringTokenizer st = new StringTokenizer(inputLine);
      		if (st.hasMoreTokens()) {
        		String command = st.nextToken();

        		List<String> arguments = new ArrayList<>();
        		while (st.hasMoreTokens())
          			arguments.add(st.nextToken());

        		executeCommand(command, arguments);
      		}
    	}

    	output.flush();
  	}


  	private void executeCommand(String command, List<String> arguments) {

    	try {
      		if (command.equals("CreateGraph")) {
        		createGraph(arguments);
      		} else if (command.equals("CreateNode")) {
        		createNode(arguments);
      		} else if (command.equals("AddNode")) {
        		addNode(arguments);
      		} else if (command.equals("AddEdge")) {
        		addEdge(arguments);
      		} else if (command.equals("ListNodes")) {
        		listNodes(arguments);
      		} else if (command.equals("ListChildren")) {
        		listChildren(arguments);
      		} else if (command.equals("FindPath")) {
        		findPath(arguments);
      		} else if (command.equals("DfsAlgorithm")){
				dfsAlgorithm(arguments);
			}else {
        		output.println("Unrecognized command: " + command);
      		}
    	} catch (Exception e) {
      		output.println("Exception: " + e.toString());
    	}
  	}


	private void createGraph(List<String> arguments) {

    	if (arguments.size() != 1)
      		throw new CommandException(
				"Bad arguments to CreateGraph: " + arguments);

    	String graphName = arguments.get(0);
    	createGraph(graphName);
  	}


  	private void createGraph(String graphName) {
  		
  		graphs.put(graphName, new WeightedNodesGraph());
  		
  		output.println("created graph "+ graphName);
  		//TODO: Insert your code here.
  		
  		// graphs.put(graphName, ___);
  		// output.println(...);

  	}
 
  	
  	private void createNode(List<String> arguments) {

    	if (arguments.size() != 2)
      		throw new CommandException(
				"Bad arguments to createNode: " + arguments);

    	String nodeName = arguments.get(0);
    	String cost = arguments.get(1);
    	createNode(nodeName, cost);
  	}


 	private void createNode(String nodeName, String cost) {

 		// TODO: Insert your code here.
 		
 		// nodes.put(nodeName, ___);
 		// output.println(...);

 		WeightedNode node = new WeightedNode(nodeName, Integer.parseInt(cost));
 		nodes.put(nodeName, node);
 		output.println("created node "+ nodeName+ " with cost " + cost);
 		
  	}
	

  	private void addNode(List<String> arguments) {

    	if (arguments.size() != 2)
      		throw new CommandException(
				"Bad arguments to addNode: " + arguments);

    	String graphName = arguments.get(0);
    	String nodeName = arguments.get(1);
    	addNode(graphName, nodeName);
  	}


  	private void addNode(String graphName, String nodeName) {
  		
  		WeightedNodesGraph graph = graphs.get(graphName);
  		WeightedNode node = nodes.get(nodeName);
  		
  		try {
			graph.addNode(node);
			output.println("added node "+ nodeName+ " to " + graphName);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GraphNodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  		// TODO: Insert your code here.
  		 
  		// ___ = graphs.get(graphName);
  		// ___ = nodes.get(nodeName);
  		// output.println(...);
  		
  	}


  	private void addEdge(List<String> arguments) {

    	if (arguments.size() != 3)
      		throw new CommandException(
				"Bad arguments to addEdge: " + arguments);

    	String graphName = arguments.get(0);
    	String parentName = arguments.get(1);
    	String childName = arguments.get(2);
    	addEdge(graphName, parentName, childName);
  	}


	private void addEdge(String graphName, String parentName, String childName) {
		
		WeightedNodesGraph graph = graphs.get(graphName);
  		WeightedNode start = nodes.get(parentName);
  		WeightedNode end = nodes.get(childName);
  		try {
			graph.addEdge(start, end);
			output.println("added edge from " + parentName+ " to " + childName + " in " + graphName);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GraphEdgeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GraphNodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO: Insert your code here.
		  
		// ___ = graphs.get(graphName);
		// ___ = nodes.get(parentName);
		// ___ = nodes.get(childName);
		// output.println(...);

  	}


  	private void listNodes(List<String> arguments) {

    	if (arguments.size() != 1)
      		throw new CommandException(
				"Bad arguments to listNodes: " + arguments);

    	String graphName = arguments.get(0);
    	listNodes(graphName);
  	}


  	private void listNodes(String graphName) {
  		WeightedNodesGraph graph = graphs.get(graphName);
  		Iterator<WeightedNode> iterator = graph.getNodes();
  		
  		output.print(graphName + " contains:");
  		while(iterator.hasNext())
  		{
  			WeightedNode node = (WeightedNode)iterator.next();
  			output.print(" " + node.getName());	
  		}
  		
		output.println();

  		// TODO: Insert your code here.
  		   
  		// ___ = graphs.get(graphName);
  		// output.println(...);

  	}


  	private void listChildren(List<String> arguments) {

    	if (arguments.size() != 2)
      		throw new CommandException(
				"Bad arguments to listChildren: " + arguments);

    	String graphName = arguments.get(0);
    	String parentName = arguments.get(1);
    	listChildren(graphName, parentName);
  	}


  	private void listChildren(String graphName, String parentName) {
  		WeightedNodesGraph graph = graphs.get(graphName);
  		WeightedNode parent = nodes.get(parentName);
  		Iterator<WeightedNode> iterator;
		try {
			iterator = graph.getChildren(parent);
	  		output.print("the children of " + parentName + " in " + graphName + " are:");
	  		while(iterator.hasNext())
	  		{
	  			WeightedNode child = (WeightedNode)iterator.next();
	  			output.print(" " + child.getName());
	  		}
	  		
			output.println();
			
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GraphNodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  		
  		// TODO: Insert your code here.
  		    
  		// ___ = graphs.get(graphName);
  		// ___ = nodes.get(parentName);
  		// output.println(...);
  		
  	}


  	private void findPath(List<String> arguments) {

    	String graphName;
    	List<String> sourceArgs = new ArrayList<>();
    	List<String> destArgs = new ArrayList<>();

    	if (arguments.size() < 1)
      		throw new CommandException(
				"Bad arguments to FindPath: " + arguments);

    	Iterator<String> iter = arguments.iterator();
    	graphName = iter.next();

		// extract source arguments
    	while (iter.hasNext()) {
      		String s = iter.next();
      		if (s.equals("->"))
        		break;
      		sourceArgs.add(s);
    	}

		// extract destination arguments
    	while (iter.hasNext())
      		destArgs.add(iter.next());

    	if (sourceArgs.size() < 1)
      		throw new CommandException(
				"Too few source args for FindPath");

    	if (destArgs.size() < 1)
      		throw new CommandException(
				"Too few dest args for FindPath");

    	findPath(graphName, sourceArgs, destArgs);
  	}


  	private void findPath(String graphName, List<String> sourceArgs,
  						  List<String> destArgs) {
  		
  		// TODO: Insert your code here.
  		   
  		// ___ = graphs.get(graphName);
  		// ___ = nodes.get(sourceArgs.get(i));
  		// ___ = nodes.get(destArgs.get(i));
  		// output.println(...);
		
  	}
	
  	private void dfsAlgorithm(List<String> arguments)
  	{
  		if (arguments.size() == 2)
  		{
  			String graphName = arguments.get(0);
  	    	String sourceArg = arguments.get(1);
  	    	dfsAlgorithm(graphName, sourceArg);
  		}
  		else if (arguments.size() == 3)
  		{
  			String graphName = arguments.get(0);
  	    	String sourceArg = arguments.get(1);
  	    	String destArg = arguments.get(2);
  	    	dfsAlgorithm(graphName, sourceArg, destArg);
  		}
  		else
  		{
  			throw new CommandException(
  					"Wrong number of source args for DfsAlgorithm");
  		}
  	}
  	
	private void dfsAlgorithm(String graphName, String sourceArg,
							  String destArg) {
	// TODO: Insert you code here.			
	// ___ = graphs.get(graphName);
  	// ___ = nodes.get(sourceArgs);
  	// ___ = nodes.get(destArgs);
  	// output.println(...);
	
		WeightedNodesGraph graph = graphs.get(graphName);
		WeightedNode startNode =  nodes.get(sourceArg);
		WeightedNode endNode =  nodes.get(destArg);
		
		DfsAlgorithm<WeightedNode> dfs = new WeightedNodeDfsAlgorithm();
		
		try {
			boolean result = dfs.DFS(graph, startNode, endNode);
			output.print("dfs algorithm output " + graphName + " " + sourceArg + " -> " + destArg + ":");
			if (!result)
			{
				output.println(" no path was found");
			}
			else
			{
				for (WeightedNode node : dfs.visited)
				{
					output.print(" " + node.getName());
				}
				
				output.println();
			}
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GraphNodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void dfsAlgorithm(String graphName, String sourceArg) {
	// TODO: Insert you code here.	
	// ___ = graphs.get(graphName);
  	// ___ = nodes.get(sourceArgs);
  	// output.println(...);	
		WeightedNodesGraph graph = graphs.get(graphName);
		WeightedNode startNode =  nodes.get(sourceArg);
		
		DfsAlgorithm<WeightedNode> dfs = new WeightedNodeDfsAlgorithm();
		
		try {
			dfs.DFS(graph, startNode);
			output.print("dfs algorithm output " + graphName + " " + sourceArg+ ":");
			for (WeightedNode node : dfs.visited)
			{
				output.print(" " + node.getName());
			}
			
			output.println();

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GraphNodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	private static void printUsage() {
		System.err.println("Usage:");
		System.err.println("to read from a file: java TestDriver <name of input script>");
		System.err.println("to read from standard input: java TestDriver");
	}


	public static void main(String args[]) {

		try {
			// check for correct number of arguments
			if (args.length > 1) {
				printUsage();
				return;
			}

			TestDriver td;
			if (args.length == 0)
				// no arguments - read from standard input
				td = new TestDriver(new InputStreamReader(System.in),
								    new OutputStreamWriter(System.out));
			else {
				// one argument - read from file
				java.nio.file.Path testsFile = Paths.get(args[0]);
				if (Files.exists(testsFile) && Files.isReadable(testsFile)) {
					td = new TestDriver(
							Files.newBufferedReader(testsFile, Charset.forName("US-ASCII")),
							new OutputStreamWriter(System.out));
				} else {
					System.err.println("Cannot read from " + testsFile.toString());
					printUsage();
					return;
				}
			}

			td.runTests();

		} catch (IOException e) {
			System.err.println(e.toString());
			e.printStackTrace(System.err);
		}
	}
}


/**
 * This exception results when the input file cannot be parsed properly.
 */
class CommandException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CommandException() {
		super();
  	}

  	public CommandException(String s) {
		super(s);
  	}
}