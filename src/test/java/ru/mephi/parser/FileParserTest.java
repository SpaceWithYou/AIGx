package ru.mephi.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FileParserTest {

    private FileParser fileParser;

    private static final String RESOURCE_PREFIX = "src/test/resources/";

    @ParameterizedTest(name = "Scheme {arguments} iteration")
    @ValueSource(strings = {"InputOutput", "Not", "And", "Xor", "ComplexScheme"})
    public void parseFileTest(String fileName) {
        var mapper = setUpJsonMapper();
        fileParser = new FileParser(createPath(fileName));

        var graph = fileParser.parseFile();

        try {
            System.out.println(mapper.writeValueAsString(graph.getNodes()));
        } catch (JsonProcessingException e) {
            System.err.println("Couldn't serialize graph!");
        }
    }

    private ObjectMapper setUpJsonMapper() {
        //TODO нормальный вывод в JSON/XML - (BFS?)
        var simpleFilterProvider = new SimpleFilterProvider();
        simpleFilterProvider.addFilter("nextNodesFilter", SimpleBeanPropertyFilter.serializeAllExcept("nextElements", "outputNumber"));
        var mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.setFilterProvider(simpleFilterProvider);
        return mapper;
    }

    private String createPath(String fileName) {
        return RESOURCE_PREFIX + fileName;
    }
}