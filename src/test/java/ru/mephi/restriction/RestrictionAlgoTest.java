package ru.mephi.restriction;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.mephi.parser.Parser;
import ru.mephi.util.TestClass;

import java.util.Map;

class RestrictionAlgoTest extends TestClass {

    @ParameterizedTest(name = "Scheme: {arguments} iteration")
    @MethodSource("getResourcesFileNames")
    public void parseFileTest(String fileName) {
        var fileParser = new Parser(fileName);
        var graph = fileParser.parseFile();
        var algo = new RestrictionAlgo(new SimpleRestrictionProcessor());
        var restrictions = Map.of(1, true, 2, false);

        algo.execute(restrictions, graph);

        System.out.println(graph);
    }
}