package ru.mephi.scheme.graph.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.mephi.parser.Parser;
import ru.mephi.scheme.graph.Graph;
import ru.mephi.util.TestClass;

class GetInversionOfGraphTest extends TestClass {

    Graph setUp(String filename) {
        var parser = new Parser(filename);
        return parser.parseFile();
    }

    @ParameterizedTest(name = "Scheme: {arguments} iteration")
    @MethodSource("getResourcesFileNames")
    void execute(String fileName) {
        var graph = setUp(fileName);

        var invertedGraph = GetInversionOfGraph.execute(graph);
        System.out.println(invertedGraph);
    }
}