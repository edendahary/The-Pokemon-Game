# Ex2-OOP
# Directed Weighted graph 

### This is an object oriented programmin project which his main idea is based on functions.

### This Project Made by Eden Dahary Student in Ariel University.

### This project contains a directional weighted graph with vertexs and edges.

### in this project we implements data structure, algorithms and GUI.

## node_data Class

This Class represents a node with unique key and a string ,tag and geo location that is a 3D point.

### This Class contains serval methods:

| **Methods**      |    **Details**        |
|-----------------|-----------------------|
| `NodeData(geo_location p, int id)` | create a new node with the given id and geo location |
| `getKey()` | Returns the node key |
| `getInfo()` | Returns the node String |
| `setInfo()` | Sets the node String  |
| `getTag()` | Returns the node tag |
| `setTag()` | Sets the node tag |

## EdgeData Class

This Class represents the set of operations applicable on a directional edge(src,dest) in a (directional) weighted graph.

### This Class contains serval methods:

| **Methods**      |    **Details**        |
|-----------------|-----------------------|
| `EdgeData(int srcKey,double weight,int dest)` | create a edge with the given src and dest and the weight |
| `getSrc()` | Returns the edge src |
| `getDest()` | Returns the edge dest |
| `getWeight()` | Returns the edge weight |


## DWGraph_DS Class

This Class represents the set of operations applicable on a node (vertex) in a directional weighted graph.
implement this Graph with a two data structure called Hash-Map(Key,Value), one contains the Nodes and the other one contains the nodes and the edges of the current node.

### This Class contains serval methods:

| **Methods**      |    **Details**        |
|-----------------|-----------------------|
| `DWGraph_DS()` | Default constructor     |
| `getNode()` | Returns a node by the nodeKey |
| `hasEdge()` | Checks is two nodes are connected if so return true else return false|
| `getEdge()` | Returns the weight of an edge between two nodes |
| `addNode()` | Adds a new node(if is not exists) to the graph |
| `connect()` | Connects two nodes in the graph (if they already Connected update the weight on this edge)|
| `getV()` | Returns a collection of the graph nodes |
| `getV(int node_id)` | Returns a collection of the edges of the given node | 
| `removeNode()` | Removed a node from the graph | 
| `removeEdge()` | Remove an edge between two nodes in the graph |
| `nodeSize()` | Returns the number of the nodes in the graph | 
| `edgeSize()` | Returns the number of the edges in the graph |
| `getMC()` | Returns the mode count|

## DWGraph_Algo

This class represents a Directed (positive) Weighted Graph Theory Algorithms including:
 isConnected(); // strongly (all ordered pais connected)
 shortestPathDist(int src, int dest);
 List<node_data> shortestPath(int src, int dest);
 Save(file); // JSON file
 Load(file); // JSON file

implement this Grpah Algorithms with a private class that called DijkstraResult that compute the functions in this class.

| **Method**      |    **Details** |
|-----------------|--------------|
| `init()`         | Initialize the graph |
| `copy()`        | Creates a deep copy of the graph |
| `getGraph()` | Returns a pointer to the initialized graph |
| `isConnected()` | Checks if the graph is connected |
| `shortestPathDist()` | Return the shortest Path Dist if none Retrun -1|
| `shortestPath()` | Return the the shortest path between src to dest - as an ordered List of nodes, if non Return null |
| `save()` | Saves a graph to a file|
| `load()` | Load a graph |






