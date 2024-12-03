package ru.mephi.restriction;

import ru.mephi.parser.data.GraphPair;
import ru.mephi.scheme.graph.GraphNode;

import static ru.mephi.scheme.component.ElementType.FALSE;
import static ru.mephi.scheme.component.ElementType.TRUE;


public class SimpleRestrictionProcessor implements RestrictionProcessor {
    @Override
    public void process(GraphPair graphs) {

    }

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
     * По факту будет DFS/BFS
     * */
    private void processNode(GraphNode node) {
        var type = node.getType();
        if(type == TRUE) {
            processTrueCase(node);
        } else if(type == FALSE) {
            processFalseCase(node);
        }
    }

    private void processTrueCase(GraphNode node) {

    }

    private void processFalseCase(GraphNode node) {

    }
}
