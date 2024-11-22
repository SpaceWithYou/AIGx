package ru.mephi.scheme;

import lombok.Getter;
import lombok.Setter;
import ru.mephi.scheme.component.ElementType;

import java.util.List;

@Getter
public class GraphNode {
    @Setter
    private ElementType type;
    private final List<GraphNode> nextElements;
    private final List<GraphNode> nextElementsWithInversion;

    public GraphNode(ElementType type, List<GraphNode> nextElements, List<GraphNode> elementsWithInversion) {
        this.type = type;
        this.nextElements = nextElements;
        this.nextElementsWithInversion = elementsWithInversion;
    }
}