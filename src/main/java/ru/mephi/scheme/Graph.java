package ru.mephi.scheme;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация AIGX (And-Inverted-Graph-eXtended)
 * */
@Getter
public class Graph {
    private final List<GraphNode> inputs = new ArrayList<>();
    private final List<GraphNode> outputs = new ArrayList<>();
    //Нужны ли все ноды?
    private final List<GraphNode> GraphNodes = new ArrayList<>();

    public void addNode(GraphNode node) {
        this.GraphNodes.add(node);
    }

    public void addInput(GraphNode node) {
        this.inputs.add(node);
        addNode(node);
    }

    public void addOutput(GraphNode node) {
        this.outputs.add(node);
        addNode(node);
    }
}
