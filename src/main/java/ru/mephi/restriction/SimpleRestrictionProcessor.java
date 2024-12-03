package ru.mephi.restriction;

import ru.mephi.scheme.graph.Graph;
import ru.mephi.scheme.graph.GraphNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


public class SimpleRestrictionProcessor implements RestrictionProcessor {

    /**
     * Возможные базовые оптимизации:
     * <ul>
     *     <li>0 XOR x = x</li>
     *     <li>1 AND x = x</li>
     *     <li>1 XOR x = NOT x</li>
     *     <li>1 XOR 1 = 0</li>
     *     <li>1 XOR 0 = 1</li>
     *     <li>0 AND x = 0</li>
     *     <li>NOT 0 = 1</li>
     *     <li>NOT 1 = 0</li>
     * </ul>
     * */
    @Override
    public void process(Graph graph) {

        var inputs = graph.getInputs();

//        //BFS
//        var nodeDeque = new ArrayDeque<>(inputs);
//        GraphNode node;
//        while (!nodeDeque.isEmpty()) {
//            node = nodeDeque.pop();
//
//            node.getNextElements().forEach(nextNode -> {
//                processElement(nextNode, false);
//                nodeDeque.push(nextNode);
//            });
//            node.getNextElementsWithInversion().forEach(nextNode -> {
//                processElement(nextNode, true);
//                nodeDeque.push(nextNode);
//            });
//        }
    }

    private void processElement(GraphNode node, boolean isInverted) {
        switch (node.getType()) {
            case AND -> processAndElement(node);
            case XOR -> processXorElement(node);
            case OUTPUT -> processOutputElement(node);
            case TRUE -> processTrueElement(node);
            case FALSE -> processFalseElement(node);
        }
    }

    //Один элемент, остальные - константы
    private void Opt1(GraphNode node) {
        //TODO
        return;
    }

    private void processAndElement(GraphNode node) {
        Opt1(node);
        //TODO
        return;
    }

    private void processXorElement(GraphNode node) {
        Opt1(node);
        //TODO
        return;
    }

    private void processOutputElement(GraphNode node) {
        Opt1(node);
        //TODO
        return;
    }

    private void processTrueElement(GraphNode node) {
        //TODO
        return;
    }

    private void processFalseElement(GraphNode node) {
        //TODO
        return;
    }

}
