package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;


import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.sqrt;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     *
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     *
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     *
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) {
        throw new NotImplementedError();
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     *
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     *
     * 1 2 3
     * 8   4
     * 7 6 5
     *
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     *
     * 1 2 3
     * 8   4
     * 7 6 х
     *
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     *
     * 1 х 3
     * 8   4
     * 7 6 Х
     *
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     *
     * 1 Х 3
     * х   4
     * 7 6 Х
     *
     * 1 Х 3
     * Х   4
     * х 6 Х
     *
     * х Х 3
     * Х   4
     * Х 6 Х
     *
     * Х Х 3
     * Х   х
     * Х 6 Х
     *
     * Х Х 3
     * Х   Х
     * Х х Х
     *
     * Общий комментарий: решение из Википедии для этой задачи принимается,
     * но приветствуется попытка решить её самостоятельно.
     *
     * Решение из википедии
     * Ресурсоемкость = O(1);
     * Трудоемкость = O(N);
     */
    static public int josephTask(int menNumber, int choiceInterval) throws Exception {
        if (menNumber <=0 & choiceInterval <=0) throw new Exception("Wrong data");
        else {
            int result = 1;
            if (menNumber > 1)
            for (int i = 2; i <= menNumber; i++) {
                result = (result + choiceInterval - 1) % i + 1;
            }
            return result;
        }
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     *
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     *
     * Решение с "построением матрицы"
     * Ресурсоемкость O(firstLength*secondLength)
     * Трудоемкость O(firstLength*secondLength)
     */
    static public String longestCommonSubstring(String first, String second) throws Exception {
        if (first.isEmpty()||second.isEmpty()) throw new Exception("Wrong Data");
        int maxLength = 0;
        int coordinate = 0;
        int firstLength = first.length();
        int secondLength = second.length();
        int [][] tableOfCount = new int[firstLength][secondLength];
        for (int i = 0; i < firstLength-1; i++) {
            for (int j = 0; j <secondLength-1; j++){
                if (first.charAt(i) == second.charAt(j)) {
                    if (i > 0 & j > 0) tableOfCount[i][j] = tableOfCount[i - 1][j - 1] + 1;
                    else tableOfCount[i][j] = 1;
                    if (tableOfCount[i][j] > maxLength) {
                        maxLength = tableOfCount[i][j];
                        coordinate = j;
                }
            }
        }
    }
    return second.substring(coordinate-maxLength+1, coordinate+1);
    }

    /**
     * Число простых чисел в интервале
     * Простая
     *
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     *
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     *
     * Решето Эратосфена
     * Ресурсоемкость O(N)
     * Трудоемкость O(N*log(log(N)));
     */
    static public int calcPrimesNumber(int limit) {
        int res = 0;
        if (limit == 2) return res;
        if (limit > 2)  {
        boolean[] flags = new boolean[(int) sqrt(limit)];
        Arrays.fill(flags, true);
        flags[1] = false;
        for (int i = 2; i*i < limit; i++)
            if (flags[i])
                for (int j = i*i; j < limit; j+=i) {
                    flags[i] = false;
                    res++; } }
        return res;
    }
}
