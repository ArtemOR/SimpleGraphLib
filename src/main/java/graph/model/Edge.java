package main.java.graph.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class Edge {

    private Vertex vertexTo;

    private Vertex vertexFrom;

    public Edge(Vertex vertexFrom, Vertex vertexTo) {
        this.vertexFrom = vertexFrom;
        this.vertexTo = vertexTo;
    }

    public Vertex getVertexTo(){
        return vertexTo;
    }

    public Vertex getVertexFrom(){
        return vertexFrom;
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
           Edge edge = (Edge) obj;
            equal = new EqualsBuilder() .append(getVertexTo(), edge.getVertexTo())
                    .isEquals();
        }
        return equal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertexTo);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}