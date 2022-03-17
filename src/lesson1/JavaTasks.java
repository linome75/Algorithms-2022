package lesson1;

import kotlin.NotImplementedError;


import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     * <p>
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     * <p>
     * Пример:
     * <p>
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     * <p>
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     * <p>
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     * <p>
     * Ресурсоемкость O(N);
     * Трудоемкость O(N*log(N));
     */
    static public void sortTimes(String inputName, String outputName) throws Exception {

        FileReader inputReader = new FileReader(inputName);
        Scanner inputScanner = new Scanner(inputReader);
        ArrayList<Integer> timeList = new ArrayList<>();
        while (inputScanner.hasNextLine()) {
            String line = inputScanner.nextLine();
            timeList.add(lineToInt(line));
        }
        inputReader.close();
        Collections.sort(timeList);

        FileWriter outputWriter = new FileWriter(outputName);
        for (int time : timeList) {
            boolean pm = false;
            if (time >= 2000000) pm = true;
            time %= 1000000;
            if (time < 10000) time += 120000;
            outputWriter.write(String.format("%02d:%02d:%02d", time / 10000, time % 10000 / 100, time % 100));
            if (pm) outputWriter.write(" PM\n");
            else outputWriter.write(" AM\n");

        }
        outputWriter.close();
    }

    static public Integer lineToInt(String line) throws Exception {
        int time = 1000000;
        if (!line.matches("^(0[1-9]|1[0-2])(:[0-5]\\d){2} [P|A]M$")) {
            throw new Exception("Wrong data in file");
        } else {
            if (line.charAt(9) == 'P') time += 1000000;
            if (!line.startsWith("12")) time += Integer.parseInt(line.substring(0, 2)) * 10000;
            time += Integer.parseInt(line.substring(3, 5)) * 100;
            time += Integer.parseInt(line.substring(6, 8));
        }
        return time;
    }

    /**
     * Сортировка адресов
     * <p>
     * Средняя
     * <p>
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     * <p>
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     * <p>
     * Людей в городе может быть до миллиона.
     * <p>
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     * <p>
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка температур
     * <p>
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     * <p>
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     * <p>
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     * <p>
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     * <p>
     * Ресурсоемкость O(N);
     * Трудоемкость O(N+K);
     */
    static public void sortTemperatures(String inputName, String outputName) throws Exception {
        FileReader inputReader = new FileReader(inputName);
        Scanner inputScanner = new Scanner(inputReader);
        int[] count = new int[7731];
        Arrays.fill(count, 1, 7731, 0);

        while (inputScanner.hasNextLine()) {
            String line = inputScanner.nextLine();
            if (line.matches("([1-4]?\\d{1,2}.\\d)|(500.0)|(-[1]?\\d{1,2}.\\d)|(-2[0-6]\\d.\\d)|(-27[0-2].\\d)|(-273.0)")) {
                int temperature = (int) Double.parseDouble(line) * 10 + 2730;
                count[temperature]++;
            } else throw new Exception("Wrong data in file");
        }

        inputReader.close();

        FileWriter outputWriter = new FileWriter(outputName);

        for (int i = 0; i < 7731; i++)
            if (count[i] != 0)
                outputWriter.write((((double) (i - 2730)) / 10 + "\n").repeat(count[i]));

        outputWriter.close();
    }

    /**
     * Сортировка последовательности
     * <p>
     * Средняя
     * (Задача взята с сайта acmp.ru)
     * <p>
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     * <p>
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     * <p>
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     * <p>
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Соединить два отсортированных массива в один
     * <p>
     * Простая
     * <p>
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     * <p>
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     * <p>
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }
}
