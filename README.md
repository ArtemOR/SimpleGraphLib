# SimpleGraphLib
Lib for creating graph. Can add vertices, edges between vertices and can find path between two vertices;
Supports 2 types of graphs - directed and undirected, which user can specify in Graph constructor by adding boolean parameter isDirected. Default value for isDirected is false.

Graph can change isDirected by method setDirected(boolean isDirected) if it does not have edges, else throws GraphLibException(Exception id5)

Graph also supports methods:
addVertex(Vertex vertex) - adding vertex to Collection of vertices. Vertex can be created from any type. It cannot be null (Exception id1), cannot add two same vertices(Exception id3);

Graph also supports some overload methods to add vertices:
addVertices(List<Vertex> vertices) - adding collection of vertices;
addVertices(Vertex... vertices) - adding any quantity of vertices separated by comma.
These methods supports all rules related to addVertex(Vertex vertex)

getVertices() - returns all vertices of graph;

addEdge(Vertex vertexFrom, Vertex vertexTo) - adds edges to vertices in graph. vertexTo and vertexFrom should not be null (Exception id1), should exists in graph(Exception id2) and should not be equals (Exception id4);

getPath(Vertex vertexFrom, Vertex vertexTo) - returns a list of edges between 2 vertices. vertexTo and vertexFrom should be not null (Exception id1) and should exists in graph(Exception id2). If there is no possible path between specified vertices, empty collection will be returned;

Specific Exceptions:
id1 - Input parameter is null
id2 - Vertex is not found
id3 - Vertex is already Exist
id4 - Cannot create path between two same vertex.
id5 - Graph contain edges with specified direction, it can't change isDirected any more.