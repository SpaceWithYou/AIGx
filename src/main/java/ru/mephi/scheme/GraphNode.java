package ru.mephi.scheme;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.mephi.scheme.component.ElementType;

import java.util.List;

@Getter
@EqualsAndHashCode
public class GraphNode {
    private final List<GraphNode> nextElements;
    private final List<GraphNode> nextElementsWithInversion;
    @Setter
    private ElementType type;
    /**
     * Номер элемента, используется для input и output, для остальных типов значение равно 0,
     * по факту индекс, увеличенный на 1, в списке графа, необходим для более быстрого доступа
     * */
    @Setter
    private int number = 0;

    public GraphNode(ElementType type, List<GraphNode> nextElements, List<GraphNode> elementsWithInversion) {
        this.type = type;
        this.nextElements = nextElements;
        this.nextElementsWithInversion = elementsWithInversion;
    }
}