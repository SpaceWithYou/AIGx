package ru.mephi.parser.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.mephi.scheme.graph.Graph;

/**
 * Необходим для хранения пары из графа и инвертированного графа
 * (граф, у которого ребра обращены)
 * */
@Data
@AllArgsConstructor
public class GraphPair {
    private Graph graph;
    private Graph invertedGraph;
}
