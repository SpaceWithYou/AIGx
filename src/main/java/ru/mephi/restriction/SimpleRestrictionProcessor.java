package ru.mephi.restriction;

import ru.mephi.scheme.Graph;
import ru.mephi.scheme.GraphNode;
import ru.mephi.scheme.component.ElementType;

import java.util.ArrayList;
import java.util.List;

import static ru.mephi.scheme.component.ElementType.AND;
import static ru.mephi.scheme.component.ElementType.FALSE;
import static ru.mephi.scheme.component.ElementType.TRUE;
import static ru.mephi.scheme.component.ElementType.XOR;


public class SimpleRestrictionProcessor implements RestrictionProcessor {
    private final List<GraphNode> possibleNodesWithNoParents = new ArrayList<>();

    @Override
    public void process(Graph graph) {
        var inputs = graph.getInputs().values();
        inputs.forEach(this::processNode);

        inputs.forEach(this::checkOrphans);
        graph.removeNodes(possibleNodesWithNoParents);
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

    /**
     * В случае ArrayList быстрее итерироваться по индексу!
     * */
    private void processTrueCase(GraphNode node) {
        ElementType type;
        var nextElements = node.getNextElements();
        //Обрабатываем обычные ноды
        for (var nextNode : nextElements) {
            type = nextNode.getType();
            if(type == AND) {
                nextElements.remove(nextNode);
                possibleNodesWithNoParents.add(nextNode);
            } else if(type == XOR) {

            } else {
                processNode(nextNode);
            }
        }

        //Обрабатываем ноды с отрицанием
        var nextInvertedElements = node.getNextElementsWithInversion();
        //Обрабатываем ноды с отрицанием
        for (var nextNode : nextInvertedElements) {
            processFalseCase(nextNode);
        }
    }

    /**
     * В случае ArrayList быстрее итерироваться по индексу!
     * */
    private void processFalseCase(GraphNode node) {
        ElementType type;
        var nextElements = node.getNextElements();
        //Обрабатываем обычные ноды
        for (var nextNode : nextElements) {
            type = nextNode.getType();
            if(type == AND) {
                nextNode.setType(FALSE);
                processFalseCase(nextNode);
            } else if(type == XOR) {
                nextElements.remove(nextNode);
                possibleNodesWithNoParents.add(nextNode);
            } else {
                processNode(nextNode);
            }
        }

        var nextInvertedElements = node.getNextElementsWithInversion();
        //Обрабатываем ноды с отрицанием
        for (var nextNode : nextInvertedElements) {
            processTrueCase(nextNode);
        }
    }

    /**
     * В процессе обработки, сохранялись потенциальные ноды, у которых нет входа и выхода,
     * в этой функции мы их ищем, если по окончании выполнения, коллекция possibleNodesWithNoParents
     * не пуста, то все эти ноды будут удалены из графа
     * */
    private void checkOrphans(GraphNode node) {
        if(possibleNodesWithNoParents.contains(node)) {
            if(node.getNextElements().isEmpty() && node.getNextElementsWithInversion().isEmpty()) {
                return;
            }
            possibleNodesWithNoParents.remove(node);
        }
        node.getNextElements().forEach(this::checkOrphans);
        node.getNextElementsWithInversion().forEach(this::checkOrphans);
    }
}
