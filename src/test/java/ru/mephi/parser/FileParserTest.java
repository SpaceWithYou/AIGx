package ru.mephi.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FileParserTest {

    private static final String RESOURCE_PREFIX = "src/test/resources/";

    @ParameterizedTest(name = "Scheme: {arguments} iteration")
    @ValueSource(strings = {"InputOutput", "Not", "And", "Xor", "ComplexScheme"})
    public void parseFileTest(String fileName) {
        var fileParser = new FileParser(createPath(fileName), false);

        var graph = fileParser.parseFile();

        System.out.println(graph);
    }

    private String createPath(String fileName) {
        return RESOURCE_PREFIX + fileName;
    }
}