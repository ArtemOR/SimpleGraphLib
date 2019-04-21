package test.java.graph;

import main.java.graph.Graph;
import main.java.graph.exception.ExceptionConstants;
import main.java.graph.exception.GraphLibException;
import main.java.graph.model.Edge;
import main.java.graph.model.Vertex;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GraphTest {

    @Test
    public void newGraph_whenDefaultConstructor_thenIsDirectedIsFalse() {
        boolean defaultDirected = false;

        Graph graph = new Graph();

        assertEquals(graph.isDirected(), defaultDirected);
    }

    @Test
    public void newGraph_whenCreatingDirectedGraph_thenIsDirectedIsTrue() {
        boolean isDirected = true;

        Graph graph = new Graph(isDirected);

        assertEquals(graph.isDirected(), isDirected);
    }

    @Test
    public void addVertices_whenAddingTwoVertices_thenVerticesSizeIsTwo() {
        Graph graph = new Graph();
        List<Vertex> vertices = new ArrayList<>();
        Vertex vertex1 = new Vertex("first");
        Vertex vertex2 = new Vertex("second");
        vertices.add(vertex1);
        vertices.add(vertex2);
        graph.addVertices(vertices);

        int size = graph.getVertices().size();

        assertSame(vertices.size(), size);
    }

    @Test(expected = GraphLibException.class)
    public void addVertices_whenAddingTwoSameVertices_thenThrowsException() {
        Graph graph = new Graph();
        List<Vertex> vertices = new ArrayList<>();
        Vertex vertex1 = new Vertex(123);
        Vertex vertex2 = new Vertex(123);
        vertices.add(vertex1);
        vertices.add(vertex2);
        graph.addVertices(vertices);
    }

    @Test(expected = GraphLibException.class)
    public void addVertex_whenAddingTwoSameVertices_thenThrowsException() {
        Graph graph = new Graph();
        Vertex vertex1 = new Vertex(123);
        Vertex vertex2 = new Vertex(123);
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
    }

    public void addVertex_whenAddingTwoVertices_thenVerticesSizeIsTwo() {
        Graph graph = new Graph();
        List<Vertex> vertices = new ArrayList<>();
        Vertex vertex1 = new Vertex(new BigInteger("123"));
        Vertex vertex2 = new Vertex(new BigInteger("1231"));
        vertices.add(vertex1);
        vertices.add(vertex2);
        graph.addVertices(vertices);

        assertTrue(vertices.size() == 2);
    }

    @Test(expected = GraphLibException.class)
    public void addEdge_whenOneVerticeIsNull_thenThrowsException() {
        Graph graph = new Graph();
        Vertex vertex1 = new Vertex(1);
        Vertex vertex2 = new Vertex(2);
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addEdge(vertex1, null);
    }

    @Test(expected = GraphLibException.class)
    public void addEdge_whenVerticesIsSame_thenThrowsException() {
        Graph graph = new Graph();
        Vertex vertex1 = new Vertex(1);
        Vertex vertex2 = new Vertex(2);
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addEdge(vertex1, vertex1);
    }

    @Test
    public void addEdge_whenGraphDoesNotContainsVertex_thenThrowsException() {
        Graph graph = new Graph();
        Vertex vertex1 = new Vertex(1);
        Vertex vertex2 = new Vertex(2);
        Vertex vertex3 = new Vertex(3);

        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        try {
            graph.addEdge(vertex1, vertex3);
        } catch (GraphLibException e) {
            assertSame(e.getErrorCode(), ExceptionConstants.NO_SUCH_VERTEX_ERROR_CODE);
            assertSame(e.getMessage(), ExceptionConstants.NO_SUCH_VERTEX_ERROR_MESSAGE);
        }
    }

    @Test
    public void addEdge_whenGraphIsDirected_thenOnlyOneVertexHasEdge() {
        Graph graph = new Graph(true);
        Vertex vertex1 = new Vertex(1);
        Vertex vertex2 = new Vertex(2);
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addEdge(vertex1, vertex2);

        assertFalse(vertex1.getEdges().isEmpty());
        assertTrue(vertex2.getEdges().isEmpty());
        assertSame(((Edge) vertex1.getEdges().get(0)).getVertexTo(), vertex2);
    }

    @Test
    public void addEdge_whenGraphIsUndirected_thenVerticesHasEdgesWithEachOnterVetexTo() {
        Graph graph = new Graph(false);
        Vertex vertex1 = new Vertex("one");
        Vertex vertex2 = new Vertex("two");
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addEdge(vertex1, vertex2);

        assertFalse(vertex1.getEdges().isEmpty());
        assertFalse(vertex2.getEdges().isEmpty());
        assertSame(((Edge) vertex1.getEdges().get(0)).getVertexTo(), vertex2);
        assertSame(((Edge) vertex2.getEdges().get(0)).getVertexTo(), vertex1);
    }

    @Test
    public void getPath_whenVerticesIsSame_thenPathIsEmpty() {
        Graph graph = new Graph(false);
        Vertex vertex1 = new Vertex("one");
        graph.addVertex(vertex1);
        List<Edge> path = graph.getPath(vertex1, vertex1);

        assertTrue(path.isEmpty());
    }

    @Test
    public void getPath_whenOnlyTwoVerticesWithEdge_thenPathHasOnlyOneEdge() {
        Graph graph = new Graph(false);
        Vertex vertex1 = new Vertex("one");
        Vertex vertex2 = new Vertex("two");
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addEdge(vertex1, vertex2);
        List<Edge> path = graph.getPath(vertex1, vertex2);

        assertTrue(path.size() == 1);
    }

    @Test
    public void getPath_whenVerticesWithOutEdge_thenPathIsEmpty() {
        Graph graph = new Graph(true);
        Vertex vertex1 = new Vertex(1);
        Vertex vertex2 = new Vertex(2);
        graph.addVertices(vertex1, vertex2);

        List<Edge> path = graph.getPath(vertex1, vertex2);

        assertTrue(path.isEmpty());

    }

    @Test
    public void getPath_whenThreeVerticesWithTwoEdgesInDirectedGraph_thenPathHasTwoEdges() {
        Graph graph = new Graph(true);
        Vertex vertex1 = new Vertex(1);
        Vertex vertex2 = new Vertex("2");
        Vertex vertex3 = new Vertex(new BigInteger("3"));
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addVertex(vertex3);
        graph.addEdge(vertex1, vertex2);
        graph.addEdge(vertex2, vertex3);
        // 1->"2"->BI(3)
        List<Edge> path = graph.getPath(vertex1, vertex3);
        assertEquals(path.size(), 2);

        //if direction is changed, then path is empty
        List<Edge> path2 = graph.getPath(vertex3, vertex1);
        assertTrue(path2.isEmpty());

    }

    @Test
    public void getPath_whenThreeVerticesWithTwoEdgesInUndirectedGraph_thenPathHasOnlyTwoEdgesNoDifferenceOfDirections() {
        Graph graph = new Graph(false);
        Vertex vertex1 = new Vertex(1);
        Vertex vertex2 = new Vertex(2);
        Vertex vertex3 = new Vertex(3);
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addVertex(vertex3);
        graph.addEdge(vertex1, vertex2);
        graph.addEdge(vertex2, vertex3);
        // 1<-->2<-->3
        List<Edge> path = graph.getPath(vertex1, vertex3);
        List<Edge> path2 = graph.getPath(vertex3, vertex1);
        assertTrue(path.size() == 2);
        assertTrue(path2.size() == 2);
    }

    @Test
    public void getPath_testComplicatedGraph() {
        Graph graph = new Graph(false);
        Vertex vertex1 = new Vertex(1);
        Vertex vertex2 = new Vertex(2);
        Vertex vertex3 = new Vertex(3);
        Vertex vertex4 = new Vertex(4);
        Vertex vertex5 = new Vertex(5);
        Vertex vertex6 = new Vertex(6);
        Vertex vertex7 = new Vertex(7);
        Vertex vertex8 = new Vertex(8);
        Vertex vertex9 = new Vertex(9);
        graph.addVertices(vertex1, vertex2, vertex3, vertex4, vertex5, vertex6, vertex7, vertex8, vertex9);
        graph.addEdge(vertex1, vertex5);
        graph.addEdge(vertex1, vertex4);
        graph.addEdge(vertex2, vertex3);
        graph.addEdge(vertex3, vertex6);
        graph.addEdge(vertex4, vertex5);
        graph.addEdge(vertex5, vertex6);
        graph.addEdge(vertex5, vertex7);
        graph.addEdge(vertex5, vertex8);
        graph.addEdge(vertex6, vertex9);
        graph.addEdge(vertex8, vertex9);
        //      1   2 - 3
        //      | \     |
        //      4 - 5 - 6
        //        / |   |
        //      7   8 - 9
        List<Edge> path = graph.getPath(vertex1, vertex3);
        assertTrue(path.size() >= 3);

        List<Edge> path2 = graph.getPath(vertex3, vertex1);
        assertTrue(path2.size() >= 3);

        List<Edge> path3 = graph.getPath(vertex7, vertex2);
        assertTrue(path3.size() >= 4);

        List<Edge> path4 = graph.getPath(vertex1, vertex4);
        assertTrue(path4.size() >= 2);
    }

    @Test
    public void getPath_whenGraphHasGap_thenPathIsEmpty() {
        Graph graph = new Graph(false);
        Vertex vertex1 = new Vertex(1);
        Vertex vertex2 = new Vertex(2);
        Vertex vertex3 = new Vertex(3);
        Vertex vertex4 = new Vertex(4);
        Vertex vertex5 = new Vertex(5);

        graph.addVertices(vertex1, vertex2, vertex3, vertex4, vertex5);
        graph.addEdge(vertex1, vertex2);
        graph.addEdge(vertex2, vertex3);
        graph.addEdge(vertex4, vertex5);
        // 1--2--3  4--5

        List<Edge> path = graph.getPath(vertex1, vertex5);
        assertTrue(path.isEmpty());

        graph.addEdge(vertex3, vertex4);
        // 1--2--3--4--5

        path = graph.getPath(vertex1, vertex5);
        assertTrue(path.size() == 4);
    }

    @Test
    public void getPath_whenVertexIsNotExist_thenThrowsException() {
        Graph graph = new Graph(false);
        Vertex vertex1 = new Vertex(1);
        Vertex vertex2 = new Vertex(2);
        graph.addVertices(vertex1, vertex2);
        graph.addEdge(vertex1, vertex2);

        Vertex vertex3 = new Vertex(3);
        try {
            List<Edge> path = graph.getPath(vertex1, vertex3);
        } catch (GraphLibException e) {
            assertEquals(e.getErrorCode(), ExceptionConstants.NO_SUCH_VERTEX_ERROR_CODE);
            assertEquals(e.getMessage(), ExceptionConstants.NO_SUCH_VERTEX_ERROR_MESSAGE);
        }
    }

    @Test
    public void getPath_whenVertexIsNotNull_thenThrowsException() {
        Graph graph = new Graph(false);
        Vertex vertex1 = new Vertex(1);
        Vertex vertex2 = new Vertex(2);
        graph.addVertices(vertex1, vertex2);
        graph.addEdge(vertex1, vertex2);

        vertex2 = null;
        try {
            List<Edge> path = graph.getPath(vertex1, vertex2);
        } catch (GraphLibException e) {
            assertEquals(e.getErrorCode(), ExceptionConstants.INPUT_PARAMETER_IS_NULL_ERROR_CODE);
            assertEquals(e.getMessage(), ExceptionConstants.INPUT_PARAMETER_IS_NULL_MESSAGE);
        }
    }

    @Test
    public void setDirected_whenGraphIsEmpty_thenIsDirectedCanBeChanged() {
        Graph graph = new Graph();
        assertTrue(graph.isDirected() == false);
        boolean isDirected = true;

        graph.setDirected(isDirected);

        assertSame(graph.isDirected(), isDirected);
    }

    @Test
    public void setDirected_whenGraphHasVerticesWithoutEdges_thenIsDirectedCanBeChanged() {
        Graph graph = new Graph();
        Vertex vertex1 = new Vertex(1);
        Vertex vertex2 = new Vertex(2);
        graph.addVertices(vertex1, vertex2);
        assertTrue(graph.isDirected() == false);

        boolean isDirected = true;

        graph.setDirected(isDirected);

        assertSame(graph.isDirected(), isDirected);
    }


    @Test
    public void getPath_whenGraphHasVerticesWithEdges_thenThrowsException() {
        Graph graph = new Graph(true);
        Vertex vertex1 = new Vertex(1);
        Vertex vertex2 = new Vertex(2);
        graph.addVertices(vertex1, vertex2);
        graph.addEdge(vertex1, vertex2);
        assertTrue(graph.isDirected() == true);

        boolean isDirected = false;
        try {
            graph.setDirected(isDirected);
        } catch (GraphLibException e) {
            assertEquals(e.getErrorCode(), ExceptionConstants.GRAPH_CONTAINS_EDGES_IT_CANNOT_CHANGE_IS_DIRECTED_ERROR_CODE);
            assertEquals(e.getMessage(), ExceptionConstants.GRAPH_CONTAINS_EDGES_IT_CANNOT_CHANGE_IS_DIRECTed_MESSAGE);
        }
    }

}
