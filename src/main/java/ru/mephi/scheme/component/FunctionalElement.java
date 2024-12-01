package ru.mephi.scheme.component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class FunctionalElement {
    private final List<Integer> inputs = new ArrayList<>();
    private final List<Integer> outputs = new ArrayList<>();
    private final ElementType type;
}
