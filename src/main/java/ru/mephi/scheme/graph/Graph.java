package ru.mephi.scheme.graph;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.mephi.scheme.component.ElementType;

import java.util.List;
import java.util.Map;

/**
 * Реализация AIGX (And-Inverted-Graph-eXtended)
 */
@AllArgsConstructor
@Getter
public class Graph {
    private List<GraphNode> inputs;
    private List<GraphNode> outputs;
    private Map<Integer, GraphNode> nodes;

    public void addNode(GraphNode node) {
        nodes.put(node.getNumber(), node);
        if (node.getType() == ElementType.INPUT) {
            inputs.add(node);
        } else if (node.getType() == ElementType.OUTPUT) {
            outputs.add(node);
        }
    }

}