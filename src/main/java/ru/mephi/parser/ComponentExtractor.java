package ru.mephi.parser;

import ru.mephi.scheme.component.ElementType;
import ru.mephi.scheme.component.FunctionalElement;

import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static ru.mephi.scheme.component.ElementType.AND;
import static ru.mephi.scheme.component.ElementType.INPUT;
import static ru.mephi.scheme.component.ElementType.OUTPUT;
import static ru.mephi.scheme.component.ElementType.XOR;
import static ru.mephi.scheme.component.ElementType.values;

/**Парсит компонент из строки*/
public class ComponentExtractor {

    private static final String ERROR_MESSAGE = "Wrong row format at [%s]";
    private static final String XOR_AND_PATTERN = "[xa]( -?[1-9]+)+ 0( [1-9]+)+";
    private static final String INPUT_PATTERN = "i ([1-9]+)";
    private static final String OUTPUT_PATTERN = "o (-?[1-9]+)";
    //private static final String NOT_PATTERN = "n( [1-9]+)+ 0( [1-9]+)+";

    private static final HashMap<String, ElementType> fileTypesMapping = new HashMap<>(values().length);

    static {
        fileTypesMapping.put("i", INPUT);
        fileTypesMapping.put("o", OUTPUT);
        fileTypesMapping.put("x", XOR);
        fileTypesMapping.put("a", AND);
        //fileTypesMapping.put("n", NOT);
    }

    public FunctionalElement getFunctionalElementFromRow(String row) {
        var tokenizer = new StringTokenizer(row, " ");
        var type = fileTypesMapping.get(tokenizer.nextToken());
        if(type == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE.formatted(row));
        }

        //Для удобства, будем считать, что выход Input'a - его номер
        //так же как и вход для Ouput'a - его номер
        var result = new FunctionalElement(type);
        switch (type) {
            case INPUT -> {
                checkForCorrectnessOrThrow(row, INPUT_PATTERN);

                var inputNumber = parseInt(tokenizer.nextToken());
                result.getOutputs().add(inputNumber);
            }
            case OUTPUT -> {
                checkForCorrectnessOrThrow(row, OUTPUT_PATTERN);

                var outputNumber = parseInt(tokenizer.nextToken());
                result.getInputs().add(outputNumber);
            }
            case XOR, AND -> {
                checkForCorrectnessOrThrow(row, XOR_AND_PATTERN);

                processElementsWithInputsAndOutputs(result, tokenizer);
            }
//            case NOT -> {
//                checkForCorrectnessOrThrow(row, NOT_PATTERN);
//
//                processElementsWithInputsAndOutputs(result, tokenizer);
//            }
        }
        return result;
    }

    private void processElementsWithInputsAndOutputs(FunctionalElement element, StringTokenizer tokenizer) {
        int number;
        while (tokenizer.hasMoreTokens()) {
            number = parseInt(tokenizer.nextToken());
            if(number == 0) {
                break;
            }
            element.getInputs().add(number);
        }

        while (tokenizer.hasMoreTokens()) {
            number = parseInt(tokenizer.nextToken());
            element.getOutputs().add(number);
        }
    }

    private void checkForCorrectnessOrThrow(String row, String pattern) {
        if(!Pattern.matches(pattern, row)) {
            throw new IllegalArgumentException(ERROR_MESSAGE.formatted(row));
        }
    }
}