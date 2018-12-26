package ua.nure.pihnastyi.practice2;


import java.util.Iterator;

class MyListImpl implements MyList, ListIterable {


    private Object[] myElements;
    private int size;

    MyListImpl() {
        size = 0;
        myElements = new Object[0];

    }

    public Iterator<Object> iterator() {
        return new IteratorImpl();
    }

    @Override
    public void add(Object e) {
        myElements = java.util.Arrays.copyOf(myElements, myElements.length + 1);
        myElements[size++] = e;
    }

    @Override
    public void clear() {
        myElements = new Object[0];
        size = 0;
    }

    @Override
    public boolean remove(Object o) {

        if (o != null) {
            for (int i = 0; i < myElements.length; i++) {
                if (o.equals(myElements[i])) {
                    deleteElementById(i);
                    return true;
                }
            }
        } else {
            for (int i = 0; i < myElements.length; i++) {
                if (o == myElements[i]) {
                    deleteElementById(i);
                    return true;
                }
            }
        }
        return false;
    }

    private void deleteElementById(int indexOfDelete) {
        --size;
        for (int j = indexOfDelete; j < size; ++j) {///проверить мб тут ошибка
            myElements[j] = myElements[j + 1];
        }
        myElements = java.util.Arrays.copyOf(myElements, myElements.length - 1);
    }

    @Override
    public Object[] toArray() {
        return myElements;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(Object o) {
        if (o != null) {
            for (int i = 0; i < myElements.length; i++) {
                if (o.equals(myElements[i])) {
                    return true;
                }
            }
        } else {
            for (int i = 0; i < myElements.length; i++) {
                if (o == myElements[i]) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(MyList c) {
        Object[] myC = c.toArray();
        int countMatch = 0;

        for (int i = 0; i < myC.length; i++) {
            for (int j = 0; j < myElements.length; j++) {
                if (myC[i] != null) {
                    if (myC[i].equals(myElements[j])) {
                        countMatch++;
                        break;
                    }
                } else {
                    if (myC[i] == (myElements[j])) {
                        countMatch++;
                        break;
                    }
                }
            }
        }
        if (countMatch == myC.length) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[");
        for (int i = 0; i < myElements.length; i++) {
            if (i == myElements.length - 1) {
                result.append(myElements[i].toString());
            } else {
                result.append(myElements[i]);
                result.append(", ");
            }
        }
        result.append("]");
        return result.toString();
    }

    public ListIterator listIterator() {
        return new ListIteratorImpl();
    }

    private class IteratorImpl implements Iterator<Object> {

        protected int index;
        boolean wasRemove;
        boolean wasNext;
        boolean wasSet;
        boolean wasPrevious;

        IteratorImpl() {
            index = 0;
            wasNext = false;
            wasRemove = false;


        }

        @Override
        public boolean hasNext() {

            return index < myElements.length;
        }

        @Override
        public Object next() {
            wasNext = true;
            wasRemove = false;
            wasPrevious = false;
            return myElements[index++];
        }

        @Override
        public void remove() {
            if (!wasNext) {
                throw new IllegalStateException();
            }
            if (wasRemove) {
                throw new IllegalStateException();
            } else {
                wasRemove = true;
                index--;
                deleteElementById(index);


            }

        }
    }

    private class ListIteratorImpl extends IteratorImpl implements ListIterator {

        ListIteratorImpl() {
            wasSet = false;
            wasPrevious = false;

        }

        @Override
        public boolean hasPrevious() {
            return (index <= 0) ? false : true;
        }

        @Override
        public Object previous() {
            wasPrevious = true;
            wasNext = false;
            wasSet = false;
            return myElements[--index];
        }

        @Override
        public void set(Object e) {

            if (!wasPrevious) {
                throw new IllegalStateException();
            }
            if (wasSet) {
                throw new IllegalStateException();
            } else {
                wasSet = true;
                if (wasNext || wasPrevious) {
                    myElements[index] = e;
                }
            }

        }

    }
}


