package ru.mephi.parser;

import ru.mephi.scheme.ElementType;
import ru.mephi.scheme.FunctionalElement;
import ru.mephi.scheme.Graph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * @version 0.9
 * @// FIXME: 06.11.2024 Убрать 1-ую часть, она лишняя? Пусть на 1-ой строке будет кол-во входов n_i и кол-во выходов n_o
 * (n_i n_o), а далее первые n_i строк описывают входы, после них n_o входов, которые описывают выходы и оставшиеся
 * строки обозначают обычные гейты
 * Формат файла: <br>
 * <ul>
 *     <li>
 *         Заголовок:
 *         <ol>
 *             <li>
 *                 1-ая строка отвечает за номера битов входа, если бит под номером n четный, то его номер n / 2,
 *                 иначе его номер (n - 1) / 2 и обозначает вход с отрицанием.
 *             </li>
 *             <li>
 *                 2-ая строка отвечает за номера выходов, аналогично 1-ой строке.
 *             </li>
 *         </ol>
 *     </li>
 *     <li>
 *         Основная часть. Состоит из строк вида: <br>
 *         f_k'_i1'_i2'_i3'_...in', где f - обозначение функционального элемента;
 *         <ul>
 *             <li>x - XOR</li>
 *             <li>a - AND</li>
 *             <li>i - INPUT</li>
 *             <li>o - OUTPUT</li>
 *         </ul>
 * @// FIXME: 06.11.2024 Добавь количество входов, перед k'
 *         k' - номер выхода;
 *         i1', i2',..., in' - номера входов, причем, если i1' четно, то берется вход i1 = i1' / 2, если
 *         i1' - нечетно, то вход i1 = (i1' - 1) / 2 берется с отрицанием, аналогично для k', i2', i3',... in'
 *     </li>
 * </ul>
 * @// FIXME: 07.11.2024 f n k i1 i2 ... in
 * @// FIXME: 07.11.2024 Все-таки убрать заголовок)
 * */

public class FileParser {

    private BufferedReader reader;
    private ComponentExtractor componentExtractor;
    private static final int BUFFER_SIZE = 1 << 16;
    private static final String ERROR_MESSAGE = "An error [%s] occurred while parsing the graph!";
    //TODO разделить на несколько диапазонов?
    //TODO библиотека для более быстрых коллекций?
    //Ключи - номера output для функционального элемента
    private static final TreeMap<Integer, Graph.Node> elementsMapping = new TreeMap<>();

    public FileParser(String path) {
        try {
            reader = new BufferedReader(new FileReader(path), BUFFER_SIZE);
            componentExtractor = new ComponentExtractor();
        } catch (FileNotFoundException e) {
            System.err.println("File not found!");
        }
    }

    //TODO multithreading parsing
    public Graph parseFile() {
        var result = new Graph();
        try {
            reader.lines()
                    .map(componentExtractor::getFunctionalElementFromRow)
                    .forEach(element -> processElement(element, result));
        } catch (Exception e) {
            System.err.println(ERROR_MESSAGE.formatted(e.getMessage()));
        }
        return result;
    }

    //TODO для отрицания выход одного гейта и вход другого должны быть нечетными!
    //TODO проверка на наличие точно такого же гейта (чтобы не было петель)
    //!TODO зачем отдельный тип INPUT и OUTPUT?
    //TODO количество входов у AND?
    //Определенный формат, для i - входы = 0, для o - выход = 0
    //В такой реализации можно добавлять элементы в любом порядке
    private void processElement(FunctionalElement element, Graph graph) {
        var node = new Graph.Node(element, new ArrayList<>());
        if(element.getType() == ElementType.INPUT) {
            graph.addInput(node);
        } else if (element.getType() == ElementType.OUTPUT) {
            graph.addOutput(node);
        } else {
            graph.addNode(node);
        }

        //Нашли кого-то в мапе - добавляем ребро
        boolean notElementFlag = element.getOutput() % 2 != 0;
        for (int input : element.getInputs()) {
            if(elementsMapping.containsKey(input)) {
                if(notElementFlag) {
                    createNotElementIfNeeded(node, graph);
                }
                elementsMapping.get(input).getNextElements().add(node);
            }
        }

        elementsMapping.put(element.getOutput(), node);
    }

    //Мы нашли два элемента f1 и f2, такие что, выход f1 = 2k + 1 и один из входов f2 = 2k + 1
    //Делаем NOT для их соединения
    private void createNotElementIfNeeded(Graph.Node node, Graph graph) {
        int outputNum = node.getOutputNumber();
        var elementNOT = new FunctionalElement(new int[] {outputNum}, outputNum, ElementType.NOT);
        var nodeNOT = new Graph.Node(elementNOT, new ArrayList<>());

        node.getNextElements().add(nodeNOT);
        graph.addNode(nodeNOT);
        elementsMapping.put(outputNum, node);
    }
}
