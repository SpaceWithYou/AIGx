package ru.mephi.restriction;

import ru.mephi.scheme.Graph;
import ru.mephi.scheme.GraphNode;
import ru.mephi.scheme.component.ElementType;

import java.util.HashSet;
import java.util.Set;

import static ru.mephi.scheme.component.ElementType.FALSE;
import static ru.mephi.scheme.component.ElementType.OUTPUT;
import static ru.mephi.scheme.component.ElementType.TRUE;


public class SimpleRestrictionProcessor implements RestrictionProcessor {
    private final Set<GraphNode> possibleNodesWithNoParents = new HashSet<>();

    @Override
    public void process(Graph graph) {
        var inputs = graph.getInputs();
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

    private void processTrueCase(GraphNode node) {
        ElementType type;
        var nextElements = node.getNextElements();
        //Обрабатываем обычные ноды
        for (int i = 0; i < nextElements.size(); i++) {
            var nextNode = nextElements.get(i);
            type = nextNode.getType();
            switch (type) {
                case AND -> {
                    nextElements.remove(i);
                    possibleNodesWithNoParents.add(node);
                    possibleNodesWithNoParents.add(nextNode);
                }
                case XOR -> {
                    //XOR
                }
                case OUTPUT -> {
                    setOutputValue(nextNode, TRUE, FALSE);
                }
                default -> {
                    processNode(nextNode);
                }
            }
        }

        //Обрабатываем ноды с отрицанием
        var nextInvertedElements = node.getNextElementsWithInversion();
        //Обрабатываем ноды с отрицанием
        for (var nextNode : nextInvertedElements) {
            if(nextNode.getType() == OUTPUT) {
                setOutputValue(nextNode, TRUE, FALSE);
            } else {
                processFalseCase(nextNode);
            }
        }
    }

    private void processFalseCase(GraphNode node) {
        ElementType type;
        var nextElements = node.getNextElements();
        //Обрабатываем обычные ноды
        for (int i = 0; i < nextElements.size(); i++) {
            var nextNode = nextElements.get(i);
            type = nextNode.getType();
            switch (type) {
                case AND -> {
                    nextNode.setType(FALSE);
                    processFalseCase(nextNode);
                }
                case XOR -> {
                    nextElements.remove(i);
                    possibleNodesWithNoParents.add(node);
                    possibleNodesWithNoParents.add(nextNode);
                }
                case OUTPUT -> {
                    setOutputValue(nextNode, FALSE, TRUE);
                }
                default -> {
                    processNode(nextNode);
                }
            }
        }

        var nextInvertedElements = node.getNextElementsWithInversion();
        //Обрабатываем ноды с отрицанием
        for (var nextNode : nextInvertedElements) {
            if(nextNode.getType() == OUTPUT) {
                setOutputValue(nextNode, FALSE, TRUE);
            } else {
                processTrueCase(nextNode);
            }
        }
    }

    private void setOutputValue(GraphNode output, ElementType value, ElementType orElse) {
        if(output.getNumber() > 0) {
            output.setType(value);
        } else {
            output.setType(orElse);
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
