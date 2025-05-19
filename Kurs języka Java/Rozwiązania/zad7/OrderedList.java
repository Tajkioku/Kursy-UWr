import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Jednokierunkowa lista uporządkowana bez powtórzeń.
 */
public class OrderedList<T extends Comparable<T>> implements OrderedSequence<T>, Iterable<T> {

    private Node head;

    /**
     * Węzeł listy jednokierunkowej.
     */
    private class Node {
        private T data;
        private Node next;

        public Node(T data) {
            this.data = data;
        }

        /**
         * Wstawia element do listy zaczynającej się od tego węzła.
         */
        public Node insert(T el) {
            int cmp = el.compareTo(this.data);
            if (cmp == 0) {
                // Ignoruj duplikaty
                return this;
            } else if (cmp < 0) {
                Node newNode = new Node(el);
                newNode.next = this;
                return newNode;
            } else {
                if (this.next == null) {
                    this.next = new Node(el);
                } else {
                    this.next = this.next.insert(el);
                }
                return this;
            }
        }

        /**
         * Usuwa element z listy zaczynającej się od tego węzła.
         */
        public Node remove(T el) {
            int cmp = el.compareTo(this.data);
            if (cmp == 0) {
                return this.next;
            } else if (cmp < 0) {
                return this;
            } else {
                if (this.next != null) {
                    this.next = this.next.remove(el);
                }
                return this;
            }
        }

        public boolean search(T el) {
            int cmp = el.compareTo(this.data);
            if (cmp == 0) {
                return true;
            } else if (cmp < 0) {
                return false;
            } else {
                return this.next != null && this.next.search(el);
            }
        }

        public T min() {
            return this.data;
        }

        public T max() {
            return (this.next == null) ? this.data : this.next.max();
        }

        public T at(int pos) {
            if (pos == 0) {
                return this.data;
            } else if (this.next == null) {
                throw new IndexOutOfBoundsException("Indeks poza zakresem.");
            } else {
                return this.next.at(pos - 1);
            }
        }

        public int index(T el, int currentIndex) {
            int cmp = el.compareTo(this.data);
            if (cmp == 0) {
                return currentIndex;
            } else if (cmp < 0) {
                return -1;
            } else {
                return (this.next == null) ? -1 : this.next.index(el, currentIndex + 1);
            }
        }

        public int size() {
            return 1 + ((this.next == null) ? 0 : this.next.size());
        }

        @Override
        public String toString() {
            return (this.next == null) ? this.data.toString() : this.data + ", " + this.next.toString();
        }
    }

    // Implementacja interfejsu OrderedSequence

    @Override
    public void insert(T el) {
        if (el == null) {
            throw new NullPointerException("Nie można wstawić wartości null.");
        }
        head = (head == null) ? new Node(el) : head.insert(el);
    }

    @Override
    public void remove(T el) {
        if (el == null) {
            throw new NullPointerException("Nie można usunąć wartości null.");
        }
        if (head != null) {
            head = head.remove(el);
        }
    }

    @Override
    public T min() {
        return (head == null) ? null : head.min();
    }

    @Override
    public T max() {
        return (head == null) ? null : head.max();
    }

    @Override
    public boolean search(T el) {
        if (el == null) {
            throw new NullPointerException("Nie można wyszukiwać wartości null.");
        }
        return head != null && head.search(el);
    }

    @Override
    public T at(int pos) {
        if (pos < 0 || pos >= size()) {
            throw new IndexOutOfBoundsException("Nieprawidłowa pozycja: " + pos);
        }
        return head.at(pos);
    }

    @Override
    public int index(T el) {
        if (el == null) {
            throw new NullPointerException("Nie można znaleźć indeksu wartości null.");
        }
        return (head == null) ? -1 : head.index(el, 0);
    }

    @Override
    public int size() {
        return (head == null) ? 0 : head.size();
    }

    // Implementacja interfejsu Iterable

    @Override
    public Iterator<T> iterator() {
        return new OrderedListIterator();
    }

    private class OrderedListIterator implements Iterator<T> {

        private Node current = head;
        private Node lastReturned = null;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            lastReturned = current;
            T data = current.data;
            current = current.next;
            return data;
        }

        @Override
        public void remove() {
            if (lastReturned == null) {
                throw new IllegalStateException("remove() może być wywołane tylko po next().");
            }
            OrderedList.this.remove(lastReturned.data);
            lastReturned = null;
        }
    }

    @Override
    public String toString() {
        return "[" + ((head == null) ? "" : head.toString()) + "]";
    }
}
