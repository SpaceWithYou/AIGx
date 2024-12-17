package ru.mephi.parser;

import ru.mephi.scheme.component.ElementType;
import ru.mephi.scheme.component.FunctionalElement;
import ru.mephi.scheme.graph.Graph;
import ru.mephi.scheme.graph.GraphNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.stream.Stream;

public class Parser extends AbstractParser {

    public Parser(String path) {
        super(path);
        elementsMapping = new TreeMap<>();
    }

    protected Graph getGraph(Stream<String> linesStream) {
        var result = new Graph(
                new ArrayList<>(),
                new ArrayList<>(),
                new TreeMap<>()
        );
        linesStream
                .filter(line -> !line.startsWith(TYPE_COMMENT))
                .map(componentExtractor::getFunctionalElementFromRow)
                .forEach(element -> processElement(element, result));

        return result;
    }

    private void processElement(FunctionalElement element, Graph graph) {
        var node = initNode(element.getType(), graph);

        for (int input : element.getInputs()) {
            if(input > 0 && elementsMapping.containsKey(input)) {
                var savedNode = elementsMapping.get(input);
                savedNode.getNextElements().put(node, false);
            } else if(elementsMapping.containsKey(-input)) {
                var savedNode = elementsMapping.get(-input);
                savedNode.getNextElements().put(node, true);
            }
        }

        //Сохраняем выходы для последующих элементов
        for (int output : element.getOutputs()) {
            elementsMapping.put(output, node);
        }
    }

    private GraphNode initNode(ElementType type, Graph graph) {
        var result = new GraphNode(type);
        result.setNextElements(new HashMap<>());
        result.setNumber(graph.getNodes().size());
        graph.addNode(result);

        return result;
    }
}
