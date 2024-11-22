package ru.mephi.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.mephi.util.ResourceManager;

class FileParserTest {
    private static ResourceManager resourceManager = new ResourceManager();

    private static String[] getFilenames() {
        return resourceManager.getResourcesFileNames();
    }

    @ParameterizedTest(name = "Scheme: {arguments} iteration")
    @MethodSource("getFilenames")
    public void parseFileTest(String fileName) {
        var fileParser = new FileParser(fileName, false);

        var graph = fileParser.parseFile();

        System.out.println(graph);
    }
}