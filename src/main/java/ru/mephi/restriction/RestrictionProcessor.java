package ru.mephi.restriction;

import ru.mephi.scheme.graph.Graph;

/**
 * Процессор для распространения ограничений
 * */
public interface RestrictionProcessor {

    /**
     * Распространяет уже <strong>загруженные</strong> ограничения
     * */
    void process(Graph graph);
}
