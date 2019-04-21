package main.java.graph;

import main.java.graph.model.Edge;
import main.java.graph.model.Vertex;
import main.java.graph.exception.ExceptionConstants;
import main.java.graph.exception.GraphLibException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.*;
import java.util.stream.Collectors;


public class Graph {

    private List<Vertex> vertices = new ArrayList<>();

    private boolean isDirected = false;

    public Graph() {
    }

    public Graph(boolean isDirected) {
        this.isDirected = isDirected;
    }

    public boolean isDirected() {
        return isDirected;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public void setDirected(boolean isDirected) {
        assertGraphCanChangeIsDirected();
        this.isDirected = isDirected;
    }

    public void addVertex(Vertex vertex) {
        assertGraphContainsVertex(vertex);
        vertices.add(vertex);
    }

    public void addVertices(List<Vertex> vertices) {
        vertices.forEach(v -> {
            assertGraphContainsVertex(v);
            this.vertices.add(v);
        });
    }

    public void addVertices(Vertex... vertices) {
        List verticesList = new ArrayList<>(Arrays.asList(vertices));
        verticesList.forEach(v -> {
            Vertex vertex = (Vertex) v;
            assertGraphContainsVertex(vertex);
            this.vertices.add(vertex);
        });
    }

    public void addEdge(Vertex fromVertex, Vertex toVertex) {
        assertNotNull(fromVertex);
        assertNotNull(toVertex);
        assertNotSame(fromVertex, toVertex);
        assertIsPresent(fromVertex);
        assertIsPresent(toVertex);

        List<Edge> edgesTo = fromVertex.getEdges();
        edgesTo.add(new Edge(fromVertex, toVertex));

        if (!isDirected()) {
            List<Edge> edgesFrom = toVertex.getEdges();
            edgesFrom.add(new Edge(toVertex, fromVertex));
        }
    }

    public List<Edge> getPath(Vertex from, Vertex to) {
        assertNotNull(from);
        assertIsPresent(from);
        assertNotNull(to);
        assertIsPresent(to);
        List<Edge> oldPath = new ArrayList<>();
        return getPath(from, to, oldPath);
    }

    private List<Edge> getPath(Vertex from, Vertex to, List<Edge> oldPath) {
        Collection<Edge> edges = from.getEdges();
        for (Edge edge : edges) {
            Vertex vertexTo = edge.getVertexTo();
            if (vertexTo.equals(to)) {
                oldPath.add(edge);
                return oldPath;
            } else if (!isContain(oldPath, vertexTo)) {
                List<Edge> newPath = new ArrayList<>(oldPath);
                newPath.add(edge);
                List<Edge> path = getPath(vertexTo, to, newPath);
                if (!path.isEmpty()) {
                    return path;
                }
            }
        }
        return new ArrayList<>();
    }

    private boolean isContain(List<Edge> oldPath, Vertex vertexTo) {

        return oldPath.stream().filter(e -> e.getVertexTo().equals(vertexTo)).collect(Collectors.toList()).size() > 0;
    }


    private void assertNotNull(Vertex value) {
        if (value == null) {
            GraphLibException ex = new GraphLibException(ExceptionConstants.INPUT_PARAMETER_IS_NULL_MESSAGE, (Throwable) null);
            ex.setErrorCode(ExceptionConstants.INPUT_PARAMETER_IS_NULL_ERROR_CODE);
            throw ex;
        }
    }

    private void assertNotSame(Vertex value1, Vertex value2) {
        if (value1.equals(value2)) {
            GraphLibException ex = new GraphLibException(ExceptionConstants.CANNOT_CREATE_PATH_BETWEEN_TWO_SAME_VERTEX_MESSAGE, (Throwable) null);
            ex.setErrorCode(ExceptionConstants.CANNOT_CREATE_PATH_BETWEEN_TWO_SAME_VERTEX_ERROR_CODE);
            throw ex;
        }
    }

    private void assertIsPresent(Vertex value) {
        if (!vertices.contains(value)) {
            GraphLibException ex = new GraphLibException(ExceptionConstants.NO_SUCH_VERTEX_ERROR_MESSAGE, (Throwable) null);
            ex.setErrorCode(ExceptionConstants.NO_SUCH_VERTEX_ERROR_CODE);
            throw ex;
        }
    }

    private void assertGraphContainsVertex(Vertex value) {
        if (vertices.contains(value)) {
            GraphLibException ex = new GraphLibException(ExceptionConstants.VERTEX_ALREADY_EXIST_MESSAGE, (Throwable) null);
            ex.setErrorCode(ExceptionConstants.VERTEX_ALREADY_EXIST_ERROR_CODE);
            throw ex;
        }
    }

    private void assertGraphCanChangeIsDirected() {
        vertices.forEach(v -> {
                    if (!v.getEdges().isEmpty()) {
                        GraphLibException ex = new GraphLibException(ExceptionConstants.GRAPH_CONTAINS_EDGES_IT_CANNOT_CHANGE_IS_DIRECTed_MESSAGE, (Throwable) null);
                        ex.setErrorCode(ExceptionConstants.GRAPH_CONTAINS_EDGES_IT_CANNOT_CHANGE_IS_DIRECTED_ERROR_CODE);
                        throw ex;
                    }
                }
        );
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal;
        if (obj == null) {
            equal = false;
        } else if (obj == this) {
            equal = true;
        } else if (obj.getClass() != getClass()) {
            equal = false;
        } else {
            Graph graph = (Graph) obj;
            equal = new EqualsBuilder().append(getVertices(), graph.getVertices())
                    .append(isDirected(), graph.isDirected())
                    .isEquals();
        }
        return equal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertices, isDirected);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}