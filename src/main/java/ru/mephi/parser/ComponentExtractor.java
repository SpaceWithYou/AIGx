package ru.mephi.parser;

import ru.mephi.scheme.ElementType;
import ru.mephi.scheme.FunctionalElement;

import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

/**Парсит компонент из строки*/
public class ComponentExtractor {
    private StringTokenizer tokenizer;

    private static final String ERROR_MESSAGE = "Wrong row format at [%s]";
    private static final String LINE_PATTERN = "[xioa10] \\d+ \\d+( \\d+)+";
    private static final HashMap<String, ElementType> fileTypesMapping = new HashMap<>(ElementType.values().length);

    static {
        fileTypesMapping.put("i", ElementType.INPUT);
        fileTypesMapping.put("o", ElementType.OUTPUT);
        fileTypesMapping.put("x", ElementType.XOR);
        fileTypesMapping.put("a", ElementType.AND);
        fileTypesMapping.put("1", ElementType.TRUE);
        fileTypesMapping.put("0", ElementType.FALSE);
    }

    //TODO положительные числа
    public FunctionalElement getFunctionalElementFromRow(String row) {
        if(!isCorrect(row)) {
            throw new UnsupportedOperationException(ERROR_MESSAGE.formatted(row));
        }
        return parseRow(row);
    }

    private FunctionalElement parseRow(String thisRow) {
        tokenizer = new StringTokenizer(thisRow, " ");
        var type = fileTypesMapping.get(tokenizer.nextToken());
        //TODO fast parse int
        int inputsNumber = parseInt(tokenizer.nextToken());
        int outputNum = parseInt(tokenizer.nextToken());
        var inputs = new int[inputsNumber];
        for (int i = 0; i < inputsNumber; i++) {
            inputs[i] = parseInt(tokenizer.nextToken());
        }
        return new FunctionalElement(inputs, outputNum, type);
    }

    //TODO проверка уже функциональных элементов, например, для входа и выхода 1 - 1 (один вход и один выход)
    //TODO у AND - два входа, у
    private boolean isCorrect(String fileLine) {
        return Pattern.matches(LINE_PATTERN, fileLine);
    }
}