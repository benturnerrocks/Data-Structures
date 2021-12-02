import java.util.*;
import java.io.*;

public class PathFinder {

    private MysteryUnweightedGraphImplementation wikiGraph;
    private Hashtable<String,Integer> wikiDict;
    private Hashtable<Integer,String> flippedWikiDict;
    private List<String> finalPath;
    /**
    * Constructs a PathFinder that represents the graph with nodes (vertices) specified as in
    * nodeFile and edges specified as in edgeFile.
    * @param nodeFile name of the file with the node names
    * @param edgeFile name of the file with the edge names
    */
    public PathFinder(String nodeFile, String edgeFile){
        File myNodeFile = new File(nodeFile);
        File myEdgeFile = new File(edgeFile);
        //tests to make sure there is a file, else it will catch to no file error and print a statement
        Scanner scannerNode = null;
        Scanner scannerEdge = null;
        try {
            scannerNode = new Scanner(myNodeFile);
            scannerEdge = new Scanner(myEdgeFile);
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }
        //creating the directed graph to store the articles and their links as vertices and edges respectively
        wikiGraph = new MysteryUnweightedGraphImplementation();
        //creating a dictionary to use as a way to identify the vertex of a particular article
        wikiDict = new Hashtable<String,Integer>();
        //create a dictionary that is the same as wikiDict but flipping keys and values
        flippedWikiDict = new Hashtable<Integer,String>();

        Integer vertexCounter;
        
        while (scannerNode.hasNextLine()) {
            String newLine = scannerNode.nextLine();

            if (newLine.length() == 0 || newLine.charAt(0)== '#') {
                ;                
            } else{
                vertexCounter = wikiGraph.addVertex();
                wikiDict.put(newLine, vertexCounter);
                flippedWikiDict.put(vertexCounter, newLine);
            }
        }

        while (scannerEdge.hasNextLine()) {
            String newLine = scannerEdge.nextLine();

            if (newLine.length() == 0 || newLine.charAt(0)== '#') {
                ;
            } else {
                String[] nodes = newLine.split(" ");
                String startNode = nodes[0];
                String endNode = nodes[1];

                wikiGraph.addEdge(wikiDict.get(startNode), wikiDict.get(endNode));
            } 
        }
    }
    
    /**
    * Returns a shortest path from node1 to node2, represented as list that has node1 at
    * position 0, node2 in the final position, and the names of each node on the path
    * (in order) in between. If the two nodes are the same, then the "path" is just a
    * single node. If no path exists, returns an empty list.
    * @param node1 name of the starting article node
    * @param node2 name of the ending article node
    * @return list of the names of nodes on the shortest path
    */
    public List<String> getShortestPath(String node1, String node2) {
        //create a list that contains the final path of articles
        finalPath = new ArrayList<String>();
        //get the node locations of what we are looking for
        int startVertex = wikiDict.get(node1);
        int endVertex = wikiDict.get(node2);
        //creates list to keep track of visited vertex
        List<Integer> visitedList = new ArrayList<Integer>();
        //creates a queue to keep track of where we are visiting next
        Queue<Integer> vertexQueue = new ArrayDeque<Integer>();
        //creates a dictionary to keep track of where we have come from so we can backtrack
        Hashtable<Integer,Integer> trackerDict = new Hashtable<Integer,Integer>();
        //adds the origin vertex to visited list and queue
        visitedList.add(startVertex);
        vertexQueue.add(startVertex);
        //create a boolean statement to control the while loop effectively
        boolean end = !vertexQueue.isEmpty();
        //loops through until we reach the end vertex or (realistically not possible with wikipedia) we do not find a path between them
        while (end) {
            //identify the vertex we are looking from
	        int frontVertex = vertexQueue.poll();
            //iterate through each neighbor for the front vertex and if we have not visited them, mark them as visited, add them to queue so we can find their neighbors, and add it to the trackerList so we can backtrack
	        for (int eachNeighbor : wikiGraph.getNeighbors(frontVertex)) {
    	        if (!visitedList.contains(eachNeighbor)) {
                    //mark each as visited
                    visitedList.add(eachNeighbor);
                    //add to queue so we can check them later
                    vertexQueue.add(eachNeighbor);
                    //add key/value pair of vertex we are at and vertex we are coming from
                    trackerDict.put(eachNeighbor, frontVertex);
                }
	        }
            //we want to reevaluate the conditions for the while loop so that we end the loop at the right time
            if (vertexQueue.isEmpty()){
                end = false;
            } else if (frontVertex==endVertex) {
                end = false;
            } else {
                end = true;
            }
            //update prevVertex
            //prevVertex = frontVertex;
        }
        //test to make sure we have a solution by checking if endVertex has been visited
        if (!visitedList.contains(endVertex)) {
            return finalPath;
        }
        //we have found final vertex, so backtrack while keeping track of the path, and return that path with the vertices as their original names
        //keep track of what vertex we are at so we can add it to the list
        int curVertex = endVertex;
        //go through everything in while loop once to get the end vertex so we can have the update line be the first line in the loop so we dont miss the starting vertex
        //create a string to keep track of the article name that we want to add
        String curArticle;
        //do the same as above in loop for each part of path until we have reached the starting vertex
        while (curVertex != startVertex) {
            curArticle = flippedWikiDict.get(curVertex);
            //add curArticle to list
            finalPath.add(curArticle);
            curVertex = trackerDict.get(curVertex);
            
        } 
        if (curVertex==startVertex) {
            //this is in case the endVertex is the startVertex and also to complete the path since the while loop does not add the start 
            curArticle = flippedWikiDict.get(curVertex);
            finalPath.add(curArticle);
        }
        //since we add the end first, we want to flip the order of the elements in our list
        Collections.reverse(finalPath);
        
        return finalPath;
    }
    
    /**
    * Returns the length of the shortest path from node1 to node2. If no path exists,
    * returns -1. If the two nodes are the same, the path length is 0.
    * @param node1 name of the starting article node
    * @param node2 name of the ending article node
    * @return length of shortest path
    */
    public int getShortestPathLength(String node1, String node2) {
        return this.getShortestPath(node1, node2).size() - 1;
    }
    
    
    /**
    * Returns a shortest path from node1 to node2 that includes the node intermediateNode.
    * This may not be the absolute shortest path between node1 and node2, but should be 
    * a shortest path given the constraint that intermediateNodeAppears in the path. If all
    * three nodes are the same, the "path" is just a single node.  If no path exists, returns
    * an empty list.
    * @param node1 name of the starting article node
    * @param node2 name of the ending article node
    * @return list that has node1 at position 0, node2 in the final position, and the names of each node 
    *      on the path (in order) in between. 
    */
    public List<String> getShortestPath(String node1, String intermediateNode, String node2){
        //creating a list to keep track of the entire path, which we can find by running getShortestPath from node1 to intermediateNode and then from intermediateNode to node2, which we can then splice together
        List<String> completePath;
        //get the first half
        completePath = this.getShortestPath(node1, intermediateNode);
        //remove the intermediateNode from the first half since otherwise we will double count it
        completePath.remove(completePath.size()-1);
        //add the second half
        completePath.addAll(this.getShortestPath(intermediateNode, node2));
        //this line is just so the solution is always stored in the instance variable finalPath so we can just use that in the print method
        this.finalPath = completePath;
        return this.finalPath;
    }

    public void print(String intermediateNode) {
        String printPath = "";
        if (finalPath.size()==0) {
            System.out.println("No path between the two articles.");
        } else {
            printPath += finalPath.get(0);
            for (int i = 1; i < finalPath.size(); i++) {
            printPath += " --> " + finalPath.get(i);
            }
        String startArticle = this.finalPath.get(0);
        String endArticle = this.finalPath.get(finalPath.size()-1);
        System.out.println("Path from " + startArticle + " through " + intermediateNode + " to " + endArticle + ", length = " + (this.finalPath.size()-1));
        System.out.println(printPath);
        }
    }

    public static void main(String[] args) {        
        //assign variable names to command line arguments that will be used every time
        String vertexFile = args[0];
        String edgeFile = args[1];
        String startVertex = args[2];
        //create new instance of PathFinder based on user input
        PathFinder wikiPath = new PathFinder(vertexFile, edgeFile);

        //calls appropriate method depending on number of arguments
        if (args.length == 4) {
            String endVertex = args[3];
            wikiPath.getShortestPath(startVertex, endVertex);
            //prints path according to print method w/o intermediateNode
            wikiPath.print("no intermediate node");
            System.out.println(wikiPath.getShortestPathLength(startVertex, endVertex));
        } else if (args.length == 5) {
            String intermediateVertex = args[3];
            String endVertex = args[4];
            wikiPath.getShortestPath(startVertex, intermediateVertex, endVertex);
            //prints path according to print method with intermediateNode
            wikiPath.print(intermediateVertex);
        }
    }
}