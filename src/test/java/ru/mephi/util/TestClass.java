package ru.mephi.util;

import java.util.List;

public class TestClass {
    private static final String RESOURCE_PREFIX = "src/test/resources/";

    private static final List<String> resourcesFileNames = List.of(
            "InputOutput",
            "Not",
            "And",
            "Xor",
            "ComplexScheme"
    );

    public static String[] getResourcesFileNames() {
        return resourcesFileNames.stream()
                .map(TestClass::createPath)
                .toArray(String[]::new);
    }

    private static String createPath(String fileName) {
        return RESOURCE_PREFIX + fileName;
    }
}
