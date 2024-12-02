package ru.mephi.parser;

import ru.mephi.parser.data.GraphPair;
import ru.mephi.scheme.component.ElementType;
import ru.mephi.scheme.component.FunctionalElement;
import ru.mephi.scheme.graph.Graph;
import ru.mephi.scheme.graph.GraphNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.stream.Stream;

public class Parser extends AbstractParser {

    public Parser(String path) {
        super(path);
        elementsMapping = new TreeMap<>();
    }

    protected GraphPair getGraphs(Stream<String> linesStream) {
        var result = new GraphPair(
                new Graph(
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>()
                ),
                new Graph(
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>()
                ));

        linesStream
                .filter(line -> !line.startsWith(TYPE_COMMENT))
                .map(componentExtractor::getFunctionalElementFromRow)
                .forEach(element -> processElement(element, result.getGraph()));

        getInvertedGraph(result);
        return result;
    }

    private void processElement(FunctionalElement element, Graph graph) {
        var node = initNode(element.getType(), graph);

        for (int input : element.getInputs()) {
            if(input > 0 && elementsMapping.containsKey(input)) {
                var savedNode = elementsMapping.get(input);
                savedNode.getNextElements().add(node);
            } else if(elementsMapping.containsKey(-input)) {
                var savedNode = elementsMapping.get(-input);
                savedNode.getNextElementsWithInversion().add(node);
            }
        }

        //Сохраняем выходы для последующих элементов
        for (int output : element.getOutputs()) {
            elementsMapping.put(output, node);
        }
    }

    private GraphNode initNode(ElementType type, Graph graph) {
        var node = initNode(type, graph.getNodes().size());
        graph.addNode(node);

        return node;
    }

    private GraphNode initNode(ElementType type, int number) {
        var result = new GraphNode(type);
        result.setNextElements(new ArrayList<>());
        result.setNextElementsWithInversion(new ArrayList<>());
        result.setNumber(number);
        return result;
    }

    private void getInvertedGraph(GraphPair pair) {
        var graph = pair.getGraph();
        var result = pair.getInvertedGraph();
        elementsMapping.clear();

        //BFS
        //Мы предполагаем, что граф ацикличный и однонаправленный
        var nodeDeque = new ArrayDeque<>(graph.getInputs());
        GraphNode node, nodeCopy;
        while (!nodeDeque.isEmpty()) {
            node = nodeDeque.pop();
            nodeCopy = getCopy(node.getType(), node.getNumber(), result);

            for (var nextNode : node.getNextElements()) {
                if(!elementsMapping.containsKey(nextNode.getNumber())) {
                    nodeDeque.push(nextNode);
                }
                processNode(nodeCopy, nextNode, result);
            }
            for (var nextInverted : node.getNextElementsWithInversion()) {
                if(!elementsMapping.containsKey(nextInverted.getNumber())) {
                    nodeDeque.push(nextInverted);
                }
                processInvertedNode(nodeCopy, nextInverted, result);
            }
        }
    }

    private void processNode(GraphNode previousNode, GraphNode node, Graph graph) {
        GraphNode copy = getCopy(node.getType(), node.getNumber(), graph);

        copy.getNextElements().add(previousNode);
    }

    private void processInvertedNode(GraphNode previousNode, GraphNode node, Graph graph) {
        GraphNode copy = getCopy(node.getType(), node.getNumber(), graph);

        copy.getNextElementsWithInversion().add(previousNode);
    }

    private GraphNode getCopy(ElementType type, int number, Graph graph) {
        if(elementsMapping.containsKey(number)) {
            return elementsMapping.get(number);
        } else {
            var copy = initNode(type, number);
            elementsMapping.put(number, copy);
            graph.addNode(copy);
            return copy;
        }
    }
}
