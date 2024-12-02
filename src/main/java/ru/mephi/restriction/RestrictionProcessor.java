package ru.mephi.restriction;

import ru.mephi.parser.data.GraphPair;

/**
 * Процессор для распространения ограничений
 * */
public interface RestrictionProcessor {
    /**
     * Распространяет уже <strong>загруженные</strong> ограничения
     * */
    void process(GraphPair graphs);
}
