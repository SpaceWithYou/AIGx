package ru.mephi.scheme.graph;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.mephi.scheme.component.ElementType;

import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
public class GraphNode {
    @Setter
    private List<GraphNode> nextElements;
    @Setter
    private List<GraphNode> nextElementsWithInversion;
    @Setter
    private ElementType type;
    /**
     * Номер элемента, необходим для более быстрого доступа и экономии памяти. По факту индекс
     * */
    @Setter
    private int number = 0;

    public GraphNode(ElementType type) {
        this.type = type;
    }

}