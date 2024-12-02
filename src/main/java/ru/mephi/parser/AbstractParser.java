package ru.mephi.parser;

import ru.mephi.parser.data.GraphPair;
import ru.mephi.scheme.graph.GraphNode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @version 2.0.1
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
 *     <li>
 *         Вход: i_n, где n - номер входа, n > 0. Причем все значения n должны быть последовательными натуральными числами,
 *     начиная с 1!
 *     </li>
 *     <li>
 *         Выход: o_n, где n - номер выхода, n может быть отрицательным,
 *     в случае если мы хотим вывести отрицание литерала. Значения n должны быть по абсолютной величине последовательными
 *     натуральными числами, начиная с 1!
 *     </li>
 *     <li>Комментарий: c_text, где text - текст комментария</li>
 * </ul>
 * Строки записываются по уровням схемы (слева направо)!!!
 * */

public abstract class AbstractParser {
    protected static final int BUFFER_SIZE = 1 << 16;
    protected static final String TYPE_COMMENT = "c ";
    protected BufferedReader reader;
    protected ComponentExtractor componentExtractor;
    //Ключи - номер выхода (литерал)
    //Все номера выходов считаем различными
    protected static Map<Integer, GraphNode> elementsMapping;

    public AbstractParser(String path) {
        try {
            reader = new BufferedReader(new FileReader(path), BUFFER_SIZE);
            componentExtractor = new ComponentExtractor();
        } catch (FileNotFoundException e) {
            System.err.println("File not found!");
        }
    }

    public GraphPair parseFile() {
        try {
            var linesStream = reader.lines();
            return getGraphs(linesStream);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    protected abstract GraphPair getGraphs(Stream<String> linesStream);
}
