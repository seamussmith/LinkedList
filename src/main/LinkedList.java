package main;

import java.util.AbstractList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;

public class LinkedList<TElement> extends AbstractList<TElement>
{
    ListNode head = null;
    LinkedList(ListNode head)
    {
        this.head = head;
    }
    public LinkedList()
    {
        
    }
    public LinkedList(List<TElement> elements)
    {
        elements.forEach(x -> add(x));
    }
    @Override
    public TElement get(int index) {
        var iter = (LinkedListIterator) listIterator(index);
        return iter.currentNode.data;
    }
    ListNode getNode(int index)
    {
        var iter = (LinkedListIterator) listIterator(index);
        return iter.last;
    }
    public boolean add(TElement e)
    {
        var iter = (LinkedListIterator) listIterator(size());
        iter.add(e);
        return true;
    }
    @Override
    public void add(int index, TElement e)
    {
        var iter = (LinkedListIterator) listIterator(index);
        iter.add(e);
    }
    @Override
    public TElement set(int index, TElement element)
    {
        var iter = iterator();
        for (var i = 0; i < index; ++i)
            iter.next();
        iter.set(element);
        return element;
    }
    @Override
    public TElement remove(int index)
    {
        if (index > size())
            throw new IndexOutOfBoundsException("Max Index: " + (size()-1) + " Index: " + index);
        var iter = (LinkedListIterator) listIterator(index);
        iter.next();
        var lastElement = iter.last.data;
        iter.remove();
        return lastElement;
    }
    @Override
    public LinkedListIterator iterator()
    {
        return new LinkedListIterator();
    }
    @Override
    public int size() {
        var s = 0;
        for (var i : this)
            ++s;
        return s;
    }
    public String toString()
    {
        var sb = new StringBuilder();
        var iter = iterator();
        sb.append("[");
        while (iter.hasNext())
        {
            var next = iter.next();
            sb.append(next);
            if (iter.hasNext())
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
    @Override
    public void clear()
    {
        head = null;
    }
    @Override
    public int indexOf(Object o) {
        var i = 0;
        for (var e : this)
        {
            if (e.equals(o))
                return i;
            ++i;
        }
        return -1;
    }
    @Override
    public int lastIndexOf(Object o) {
        var i = 0;
        var obji = -1;
        for (var e : this)
        {
            if (e.equals(o))
                obji = i;
            ++i;
        }
        return obji;
    }
    @Override
    public ListIterator<TElement> listIterator() {
        return new LinkedListIterator();
    }
    @Override
    public ListIterator<TElement> listIterator(int index) {
        var iter = iterator();
        for (int i = 0; i != index; ++i)
        {
            if (!iter.hasNext())
                throw new IndexOutOfBoundsException();
            iter.next();
        }
        return iter;
    }
    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        if (toIndex > size())
            throw new IndexOutOfBoundsException();
        var iter = listIterator(fromIndex);
        for (var i = fromIndex; i < toIndex; ++i)
        {
            iter.remove();
            iter.next();
        }
    }
    public class ListNode
    {
        TElement data = null;
        ListNode next = null;
        ListNode(TElement data)
        {
            this.data = data;
        }
        ListNode(TElement data, ListNode next)
        {
            this.data = data;
            this.next = next;
        }
    }
    public class LinkedListIterator implements ListIterator<TElement>
    {
        ListNode currentNode = LinkedList.this.head;
        ListNode last = null;
        boolean canRemove = false;
        int index = 0;
        private LinkedListIterator()
        {
            
        }

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public TElement next() {
            if (!hasNext())
                throw new NoSuchElementException();
            last = currentNode;
            var getnode = currentNode.data;
            currentNode = currentNode.next;
            canRemove = true;
            ++index;
            return getnode;
        }

        @Override
        public int nextIndex() {
            next();
            return index-1;
        }
        @Override
        public void remove() {
            if (!canRemove)
                throw new IllegalStateException("Cannot remove more than once per next call");
            canRemove = false;
            
            // If we are operating on the head
            if (last == LinkedList.this.head)
            {
                LinkedList.this.head = last.next;
            }
            else
            {
                var behindLast = LinkedList.this.getNode(index-2);
                behindLast.next = last.next;
                last = behindLast;
                currentNode = last.next;
            }
        }

        @Override
        public void set(TElement e) {
            currentNode.data = e;
        }

        @Override
        public void add(TElement e) {
            // If we are operating without a head
            if (currentNode == LinkedList.this.head)
            {
                LinkedList.this.head = new ListNode(e, currentNode);
                return;
            }
            var cur = last;
            var nextNode = currentNode;
            var newNode = new ListNode(e, nextNode);
            cur.next = newNode;
        }
        // Going backwards in a linked list is an impossible task.
        @Override
        public boolean hasPrevious() {
            throw new UnsupportedOperationException("LinkedList does not support moving backwards");
        }
        @Override
        public TElement previous() {
            throw new UnsupportedOperationException("LinkedList does not support moving backwards");
        }
        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException("LinkedList does not support moving backwards");
        }
    }
}
