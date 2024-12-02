package ru.mephi.scheme.graph;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.mephi.scheme.component.ElementType;

import java.util.List;

/**
 * Реализация AIGX (And-Inverted-Graph-eXtended)
 *
 * @param nodes Ключи - номера элементов
 */
@RequiredArgsConstructor
@Getter
public class Graph {
    private final List<GraphNode> inputs;
    private final List<GraphNode> outputs;
    private final List<GraphNode> nodes;

    public void addNode(GraphNode node) {
        nodes.add(node);
        if (node.getType() == ElementType.INPUT) {
            inputs.add(node);
        } else if (node.getType() == ElementType.OUTPUT) {
            outputs.add(node);
        }
    }
}