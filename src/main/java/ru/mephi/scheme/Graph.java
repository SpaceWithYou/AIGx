package ru.mephi.scheme;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.mephi.scheme.component.ElementType;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Реализация AIGX (And-Inverted-Graph-eXtended)
 * */
@RequiredArgsConstructor
@Getter
public class Graph {
    //Номера входов
    private final Map<Integer, GraphNode> inputs;
    //Номера выходов
    private final Map<Integer, GraphNode> outputs;
    private final List<GraphNode> graphNodes;

    public void addNode(GraphNode node) {
        this.graphNodes.add(node);
    }

    public void addInput(GraphNode node, int number) {
        this.inputs.put(number, node);
        addNode(node);
    }

    public void addOutput(GraphNode node, int number) {
        this.outputs.put(number, node);
        addNode(node);
    }

    public void removeNode(GraphNode node) {
        this.graphNodes.remove(node);
        var type = node.getType();
        if(type == ElementType.INPUT) {
            //
        } else if(type == ElementType.OUTPUT) {
            outputs.forEach((key, value) -> {
                if(value == node) {
                    inputs.remove(key);
                }
            });
        }
    }

    public void removeNodes(Collection<? extends  GraphNode> nodes) {
        this.graphNodes.removeAll(nodes);

    }
}
