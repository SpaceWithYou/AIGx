package ru.mephi.restriction;

import lombok.AllArgsConstructor;
import ru.mephi.scheme.Graph;
import ru.mephi.scheme.component.ElementType;

import java.util.Map;

/**
 * Алгоритм распространения ограничений
 * */
@AllArgsConstructor
public class RestrictionAlgo {
    private Graph graph;
    private RestrictionProcessor processor;

    /**
     * @param restrictions Номера входных переменных и их значения
     * */
    public Graph execute(Map<Integer, Boolean> restrictions) {
        restrictions.forEach(this::setValueToNode);
        processor.process(graph);
        return graph;
    }

    private void setValueToNode(int nodeNumber, boolean value) {
        var inputs = graph.getInputs();
        if (inputs.containsKey(nodeNumber)) {
            inputs.get(nodeNumber).setType(getTypeByValue(value));
        }
    }

    private ElementType getTypeByValue(boolean value) {
        return value ? ElementType.INPUT : ElementType.FALSE;
    }
}
