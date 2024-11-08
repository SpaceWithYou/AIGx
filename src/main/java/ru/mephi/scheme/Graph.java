package ru.mephi.scheme;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация AIGX (And-Inverted-Graph-eXtended)
 * */
@Getter
public class Graph {
    //TODO custom hashcode
    //TODO delete JSON
    @EqualsAndHashCode
    @AllArgsConstructor
    @Getter
    public static class Node {
        private FunctionalElement element;
        @JsonFilter("nextNodesFilter")
        private List<Node> nextElements;

        public int getOutputNumber() {
            return element.getOutput();
        }
    }
    //TODO Здесь можно хранить мапу индексов и входов/выходов
    @JsonIgnore
    private final List<Node> inputs = new ArrayList<>();
    @JsonIgnore
    private final List<Node> outputs = new ArrayList<>();
    private final List<Node> nodes = new ArrayList<>();

    public void addNode(Node node) {
        this.nodes.add(node);
    }

    public void addInput(Node node) {
        this.inputs.add(node);
        addNode(node);
    }

    public void addOutput(Node node) {
        this.outputs.add(node);
        addNode(node);
    }
}
