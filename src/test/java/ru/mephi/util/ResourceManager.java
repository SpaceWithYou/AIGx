package ru.mephi.util;

import java.util.List;

public class ResourceManager {
    private static final String RESOURCE_PREFIX = "src/test/resources/";

    private static final List<String> resourcesFileNames = List.of(
            "InputOutput",
            "Not",
            "And",
            "Xor",
            "ComplexScheme"
    );

    public String[] getResourcesFileNames() {
        return resourcesFileNames.stream()
                .map(this::createPath)
                .toArray(String[]::new);
    }

    private String createPath(String fileName) {
        return RESOURCE_PREFIX + fileName;
    }
}
