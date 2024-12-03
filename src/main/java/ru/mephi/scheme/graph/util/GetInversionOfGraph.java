package ru.mephi.scheme.graph.util;

import ru.mephi.scheme.graph.Graph;
import ru.mephi.scheme.graph.GraphNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class GetInversionOfGraph {

    public static Graph execute(Graph graph) {
        var result = new Graph(
            new ArrayList<>(),
            new ArrayList<>(),
            new TreeMap<>()
        );

        //BFS
        //Создаем новый граф и копируем в него вершины
        var nodeDeque = new ArrayDeque<>(graph.getInputs());
        GraphNode node;
        while(!nodeDeque.isEmpty()) {
            node = nodeDeque.pop();

            var nodeCopy = getNodeCopy(node, result);

            for(var entry : node.getNextElements().entrySet()) {
                var nextNode = entry.getKey();
                var isNegative = entry.getValue();
                var nextNodeCopy = getNodeCopy(nextNode, result);

                nextNodeCopy.getNextElements().put(nodeCopy, isNegative);
                nodeDeque.push(nextNode);
            }
        }

        return result;
    }

    private static GraphNode getNodeCopy(GraphNode node, Graph result) {
        if(result.getNodes().containsKey(node.getNumber())) {
            return result.getNodes().get(node.getNumber());
        } else {
            var nodeCopy = new GraphNode(node.getType(), node.getNumber(), new HashMap<>());
            result.addNode(nodeCopy);
            return nodeCopy;
        }
    }
}
