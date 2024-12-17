package ru.mephi.restriction;

import lombok.AllArgsConstructor;
import ru.mephi.scheme.graph.Graph;
import ru.mephi.scheme.component.ElementType;
import ru.mephi.scheme.graph.GraphNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Алгоритм распространения ограничений
 * */
@AllArgsConstructor
public class RestrictionAlgo {
    private RestrictionProcessor processor;

    /**
     * @param restrictions Номера входных переменных и их значения
     * */
    public Graph execute(Map<Integer, Boolean> restrictions, Graph graph) {
        var result = createCopy(graph);
        restrictions.forEach((num, value) -> setValueToNode(num, value, result));

        processor.process(result);
        return result;
    }

    private Graph createCopy(Graph graph) {
        var copy = new Graph(
            new ArrayList<>(),
            new ArrayList<>(),
            new HashMap<>()
        );

        graph.getNodes().forEach(((num, graphNode) -> {
            var nodeCopy = new GraphNode(graphNode, new HashMap<>());
            copy.addNode(nodeCopy);
        }));
        return copy;
    }

    private void setValueToNode(int nodeNumber, boolean value, Graph graph) {
        var inputs = graph.getInputs();
        int index = nodeNumber - 1;
        if(index < inputs.size() && index >= 0) {
            inputs.get(index).setType(getTypeByValue(value));
        }
    }

    private ElementType getTypeByValue(boolean value) {
        return value ? ElementType.TRUE : ElementType.FALSE;
    }
}
