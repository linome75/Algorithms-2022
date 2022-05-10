package lesson7;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     * <p>
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * <p>
     * Трудоемкость O(firstLength*secondLength)
     * РЕсурсоемкость O(firstLength*secondLength)
     */
    public static String longestCommonSubSequence(String first, String second) {
        int firstLength = first.length();
        int secondLength = second.length();
        int[][] lcsTable = new int[firstLength + 1][secondLength + 1];
        for (int i = 1; i <= firstLength; i++) {
            for (int k = 1; k <= secondLength; k++) {
                if (first.charAt(i - 1) == second.charAt(k - 1)) lcsTable[i][k] = lcsTable[i - 1][k - 1] + 1;
                else lcsTable[i][k] = Math.max(lcsTable[i - 1][k], lcsTable[i][k - 1]);
            }
        }
        StringBuilder result = new StringBuilder();
        int i = firstLength;
        int k = secondLength;
        while (i > 0 && k > 0) {
            if (first.charAt(i - 1) == second.charAt(k - 1)) {
                result.append(second.charAt(k - 1));
                i--;
                k--;
            } else if (lcsTable[i - 1][k] > lcsTable[i][k - 1]) i--;
            else k--;
        }
        return result.reverse().toString();
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     * <p>
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     *
     * https://stackoverflow.com/questions/54885750/first-longest-increasing-subsequence
     * Максимальная трудоемкость O(list.size*list.size)
     * Рeсурсоемкость O(list.size)
     *
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        if (list.size() < 2) return list;
        Lis input = new Lis(list);
        return input.dfsd();
    }

    public static class Lis {
        int size;
        int[] seq;

        public Lis(List<Integer> list) {
            size = list.size();
            seq = list.stream().filter(Objects::nonNull).mapToInt(Integer::intValue).toArray();
        }

        public List<Integer> dfsd() {
            int[][] lisTable = new int[size][2];
            int length = 0;
            for (int i = 0; i < size; i++) {
                lisTable[i][0] = -1;
                lisTable[i][1] = 1;
                for (int j = i - 1; j >= 0; j--) {
                    if (seq[i] > seq[j]) {
                        if (lisTable[i][1] <= lisTable[j][1] + 1) {
                            lisTable[i][1] = lisTable[j][1] + 1;
                            lisTable[i][0] = j;
                        }
                    }
                }
                length = Math.max(length,lisTable[i][1]);
            }
            List<Integer> result = new ArrayList<>();
            for (int i = 0; i < size; i++){
                if (lisTable[i][1] == length) {
                    int j = i;
                    while (j != -1) {
                        result.add(seq[j]);
                        j = lisTable[j][0];
                    }
                    break;
                }
            }
            Collections.reverse(result);
            return result;
        }
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     * <p>
     * В файле с именем inputName задано прямоугольное поле:
     * <p>
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     * <p>
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     * <p>
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     * Трудоемкость O(height*width)
     * РЕсурсоемкость O(height*width)
     */
    public static int shortestPathOnField(String inputName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(inputName));
        int height = lines.size();
        int width = lines.get(0).split(" ").length;
        int[][] table = new int[height][width];
        String[] splitLine;
        for (int i = 0; i < height; i++) {
            splitLine = lines.get(i).split(" ");
            for (int j = 0; j < width; j++)
                table[i][j] = Integer.parseInt(splitLine[j]);
        }

        int[][] sum = new int[height][width];
        sum[0][0] = table[0][0];
        for (int i = 1; i < height; i++) sum[i][0] = sum[i - 1][0] + table[i][0];
        for (int j = 1; j < width; j++) sum[0][j] = sum[0][j - 1] + table[0][j];
        for (int i = 1; i < height; i++)
            for (int j = 1; j < width; j++)
                sum[i][j] = Math.min(sum[i - 1][j], Math.min(sum[i - 1][j - 1], sum[i][j - 1])) + table[i][j];
        return sum[height - 1][width - 1];

    }
}
