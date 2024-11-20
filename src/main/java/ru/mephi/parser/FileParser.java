package ru.mephi.parser;

import ru.mephi.scheme.Graph;
import ru.mephi.scheme.GraphNode;
import ru.mephi.scheme.component.ElementType;
import ru.mephi.scheme.component.FunctionalElement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @version 1.9
 * Формат файла:<br>
 * Каждая строка имеет вид f_k'_i1'_i2'_i3'_...in'_0_o1'_o2'_..om' , где
 * f - обозначение функционального элемента, i1',..., in' - номера входа причем,
 * отрицательные значения соответствуют литералам с отрицанием,
 * o1',..., om' - номера выходов, они положительные, как и для входов, 0 - разделитель.<br>
 * Символ f может принимать следующие значения:
 * -x - XOR
 *     <li>a - AND</li>
 *     <li><s>n - NOT</s></li>
 *     <li>i - INPUT</li>
 *     <li>o - OUTPUT</li>
 *     <li>c - COMMENT</li>
 * </ul>
 * Частные случаи:
 * <ul>
 *     <li>Вход: i_n, где n - номер входа, n > 0</li>
 *     <li>Выход: o_n, где n - номер выхода, n может быть отрицательным,
 *     в случае если мы хотим вывести отрицание литерала</li>
 *     <li>Комментарий: c_text, где text - текст комментария</li>
 * </ul>
 * Строки записываются по уровням схемы!
 * */

public class FileParser {

    private BufferedReader reader;
    private ComponentExtractor componentExtractor;
    private boolean isParallel = false;
    private static final int BUFFER_SIZE = 1 << 16;
    private static final String TYPE_COMMENT = "c ";
    //Ключи - номер выхода (литерал)
    //Все номера выходов считаем различными
    private static Map<Integer, GraphNode> elementsMapping;

    public FileParser(String path, boolean parallelFlag) {
        try {
            reader = new BufferedReader(new FileReader(path), BUFFER_SIZE);
            componentExtractor = new ComponentExtractor();
            if(parallelFlag) {
                this.isParallel = true;
                elementsMapping = new ConcurrentSkipListMap<>();
            } else {
                elementsMapping = new TreeMap<>();
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found!");
        }
    }

    public Graph parseFile() {
        var result = new Graph();
        try {
            var linesStream = reader.lines();
            if(isParallel) {
                linesStream
                        .parallel()
                        .filter(line -> !line.startsWith(TYPE_COMMENT))
                        .map(componentExtractor::getFunctionalElementFromRow)
                        .forEach(element -> processElement(element, result));
            } else {
                linesStream
                        .filter(line -> !line.startsWith(TYPE_COMMENT))
                        .map(componentExtractor::getFunctionalElementFromRow)
                        .forEach(element -> processElement(element, result));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    private void processElement(FunctionalElement element, Graph graph) {
        var type = element.getType();
        var node = new GraphNode(type, new ArrayList<>(), new ArrayList<>());
        if(type == ElementType.INPUT) {
            graph.addInput(node);
        } else if (type == ElementType.OUTPUT) {
            graph.addOutput(node);
        } else {
            graph.addNode(node);
        }

        //Проверяем не делали мы таких элементов раньше
        //сохраняем положительные записи в Map
        for (int input : element.getInputs()) {
            if(input > 0 && elementsMapping.containsKey(input)) {
                elementsMapping.get(input).getNextElements().add(node);
            } else if(elementsMapping.containsKey(-input)) {
                elementsMapping.get(-input).getNextElementsWithInversion().add(node);
            }
        }

        //Сохраняем выходы для последующих элементов
        for (int output : element.getOutputs()) {
            elementsMapping.put(output, node);
        }
    }
}
