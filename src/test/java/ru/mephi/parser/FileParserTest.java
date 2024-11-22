package ru.mephi.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.mephi.util.TestClass;

class FileParserTest extends TestClass {

    @ParameterizedTest(name = "Scheme: {arguments} iteration")
    @MethodSource("getResourcesFileNames")
    public void parseFileTest(String fileName) {
        var fileParser = new FileParser(fileName, false);

        var graph = fileParser.parseFile();

        System.out.println(graph);
    }
}