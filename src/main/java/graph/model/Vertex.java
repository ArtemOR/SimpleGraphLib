package main.java.graph.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vertex<T> {

    private T vertex;

    private List<Edge> edges = new ArrayList<>();

    public Vertex(T vertex) {
        this.vertex = vertex;
    }

    public T getVertex() {
        return vertex;
    }

    public List<Edge> getEdges() {
        return edges;
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
            Vertex vertex = (Vertex) obj;
            equal = new EqualsBuilder().append(getVertex(), vertex.getVertex())
                    .isEquals();
        }
        return equal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertex);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
