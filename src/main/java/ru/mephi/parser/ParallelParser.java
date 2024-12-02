package ru.mephi.parser;

import ru.mephi.parser.data.GraphPair;
import ru.mephi.scheme.component.FunctionalElement;
import ru.mephi.scheme.graph.Graph;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

public class ParallelParser extends AbstractParser {
    public ParallelParser(String path) {
        super(path);
        elementsMapping = new ConcurrentSkipListMap<>();
    }

    @Override
    protected GraphPair getGraphs(Stream<String> linesStream) {
        //TODO Пул потоков

        var result = new GraphPair(
                new Graph(
                        new CopyOnWriteArrayList<>(),
                        new CopyOnWriteArrayList<>(),
                        new CopyOnWriteArrayList<>()),
                new Graph(
                        new CopyOnWriteArrayList<>(),
                        new CopyOnWriteArrayList<>(),
                        new CopyOnWriteArrayList<>()
                ));

        linesStream
                .parallel()
                .filter(line -> !line.startsWith(TYPE_COMMENT))
                .map(componentExtractor::getFunctionalElementFromRow)
                .forEach(element -> processElementParallel(element, result));
        return result;
    }

    //TODO Реализовать
    private void processElementParallel(FunctionalElement element, GraphPair graphPair) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }
}
