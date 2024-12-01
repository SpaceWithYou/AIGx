package ru.mephi.scheme;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.mephi.scheme.component.ElementType;

import java.util.Collection;
import java.util.List;

/**
 * Реализация AIGX (And-Inverted-Graph-eXtended)
 * */
@RequiredArgsConstructor
@Getter
public class Graph {
    //Номера входов
    private final List<GraphNode> inputs;
    //Номера выходов
    private final List<GraphNode> outputs;
    private final List<GraphNode> graphNodes;

    public void addNode(GraphNode node) {
        this.graphNodes.add(node);
    }

    public void addInput(GraphNode node) {
        this.inputs.add(node);
        addNode(node);
    }

    public void addOutput(GraphNode node) {
        this.outputs.add(node);
        addNode(node);
    }

    public void removeNode(GraphNode node) {
        this.graphNodes.remove(node);
        var type = node.getType();
        if(type == ElementType.INPUT) {
            inputs.remove(node.getNumber());
        } else if(type == ElementType.OUTPUT) {
            outputs.remove(node.getNumber());
        }
    }

    public void removeNode(int index) {
        if(index >= this.inputs.size()) {
            throw new IllegalArgumentException("The index should be less than the number of inputs");
        }

        var node = this.inputs.get(index);
        var type = node.getType();

        if(type == ElementType.INPUT) {
            inputs.remove(node.getNumber());
        } else if(type == ElementType.OUTPUT) {
            outputs.remove(index);
        }
        this.graphNodes.remove(node.getNumber());
    }

    public void removeNodes(Collection<? extends  GraphNode> nodes) {
        this.graphNodes.removeAll(nodes);
        for(var node : nodes) {
            var type = node.getType();
            if(type == ElementType.INPUT) {
                inputs.remove(node.getNumber());
            } else if (type == ElementType.OUTPUT) {
                outputs.remove(node.getNumber());
            }
        }
    }
}
