package ru.mephi.scheme.graph;

import lombok.Getter;
import lombok.Setter;
import ru.mephi.scheme.component.ElementType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Setter
@Getter
public class GraphNode implements Comparable<GraphNode> {
    /**
     * True если элемент с отрицанием, иначе false
     * */
    private Map<GraphNode, Boolean> nextElements;
    private ElementType type;
    /**
     * Номер элемента, необходим для более быстрого доступа и экономии памяти.
     * */
    private int number = 0;

    public GraphNode(ElementType type) {
        this.type = type;
    }

    public GraphNode(ElementType type, int number, Map<GraphNode, Boolean> nextElements) {
        this.type = type;
        this.number = number;
        this.nextElements = nextElements;
    }

    /**Позволяет выбрать тип мапы*/
    public GraphNode(GraphNode node, Map<GraphNode, Boolean> nextElements) {
        this.type = node.type;
        this.number = node.number;
        this.nextElements = nextElements;
        this.nextElements.putAll(node.nextElements);
    }

    @Override
    public int compareTo(GraphNode o) {
        return number - o.number;
    }

    @Override
    public int hashCode() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GraphNode graphNode)) {
            return false;
        }
        return number == graphNode.number && Objects.equals(nextElements, graphNode.nextElements) && type == graphNode.type;
    }
}