package lesson5;


import org.jetbrains.annotations.NotNull;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class OpenAddressingSet<T> extends AbstractSet<T> {

    private final int bits;

    private final int capacity;

    private final Object[] storage;

    private int size = 0;
    private final Object deleted = new Object();

    private int startingIndex(Object element) {
        return element.hashCode() & (0x7FFFFFFF >> (31 - bits));
    }

    public OpenAddressingSet(int bits) {
        if (bits < 2 || bits > 31) {
            throw new IllegalArgumentException();
        }
        this.bits = bits;
        capacity = 1 << bits;
        storage = new Object[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Проверка, входит ли данный элемент в таблицу
     */
    @Override
    public boolean contains(Object o) {
        int index = startingIndex(o);
        Object current = storage[index];
        while (current != null) {
            if (current.equals(o) && storage[index] != deleted) {
                return true;
            }
            index = (index + 1) % capacity;
            current = storage[index];
        }
        return false;
    }

    /**
     * Добавление элемента в таблицу.
     * <p>
     * Не делает ничего и возвращает false, если такой же элемент уже есть в таблице.
     * В противном случае вставляет элемент в таблицу и возвращает true.
     * <p>
     * Бросает исключение (IllegalStateException) в случае переполнения таблицы.
     * Обычно Set не предполагает ограничения на размер и подобных контрактов,
     * но в данном случае это было введено для упрощения кода.
     */
    @Override
    public boolean add(T t) {
        int startingIndex = startingIndex(t);
        int index = startingIndex;
        Object current = storage[index];
        while (current != null && storage[index] != deleted) {
            if (current.equals(t)) {
                return false;
            }
            index = (index + 1) % capacity;
            if (index == startingIndex) {
                throw new IllegalStateException("Table is full");
            }
            current = storage[index];
        }
        storage[index] = t;
        size++;
        return true;
    }

    /**
     * Удаление элемента из таблицы
     * <p>
     * Если элемент есть в таблица, функция удаляет его из дерева и возвращает true.
     * В ином случае функция оставляет множество нетронутым и возвращает false.
     * Высота дерева не должна увеличиться в результате удаления.
     * <p>
     * Спецификация: {@link Set#remove(Object)} (Ctrl+Click по remove)
     * <p>
     * Средняя
     * <p>
     * Трудоемкость O(n)
     * Ресурсоемкость O(1)
     */
    @Override
    public boolean remove(Object o) {
        if (contains(o)) {
            int targetIndex = startingIndex(o);
            if (storage[targetIndex].equals(o)) {
                storage[targetIndex] = deleted;
                size--;
                return true;
            }
            int nextIndex = (targetIndex + 1) % capacity;
            while (nextIndex != targetIndex) {
                if (storage[nextIndex].equals(o)) {
                    storage[nextIndex] = deleted;
                    size--;
                    return true;
                }
                nextIndex = (nextIndex + 1) % capacity;
            }
        }
        return false;
    }

    /**
     * Создание итератора для обхода таблицы
     * <p>
     * Не забываем, что итератор должен поддерживать функции next(), hasNext(),
     * и опционально функцию remove()
     * <p>
     * Спецификация: {@link Iterator} (Ctrl+Click по Iterator)
     * <p>
     * Средняя (сложная, если поддержан и remove тоже)
     */
    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new OpenAddressingSetIterator();
    }

    public class OpenAddressingSetIterator implements Iterator<T> {
        int index = 0;
        int objIndex = 0;
        T object;
        /** Трудоемкость O(1)
         * Ресурсоемкость O(1)
         */
        @Override
        public boolean hasNext() {
            return objIndex < size;
        }
        /** Трудоемкость O(n)
        * Ресурсоемкость O(1)
        */
        @Override
        public T next() {
            if (hasNext()) {
                while (storage[index] == null || storage[index].equals(deleted)) index++;
                object = (T) storage[index];
                index++;
                objIndex++;
                return object;
            }
            throw new NoSuchElementException();
        }
        /** Трудоемкость O(1)
         * Ресурсоемкость O(1)
         */
        @Override
        public void remove() {
            if (object != null) {
                object = null;
                objIndex--;
                storage[index - 1] = deleted;
                size--;
            } else throw new IllegalStateException();
        }
    }
}
