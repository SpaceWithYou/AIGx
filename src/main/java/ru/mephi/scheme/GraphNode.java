package ru.mephi.scheme;

import lombok.Getter;
import ru.mephi.scheme.component.ElementType;

import java.util.List;

@Getter
public class GraphNode {
    private final ElementType type;
    private final List<GraphNode> nextElements;
    private final List<GraphNode> nextElementsWithInversion;

    public GraphNode(ElementType type, List<GraphNode> nextElements, List<GraphNode> elementsWithInversion) {
        this.type = type;
        this.nextElements = nextElements;
        this.nextElementsWithInversion = elementsWithInversion;
    }
}