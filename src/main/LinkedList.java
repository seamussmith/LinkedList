package main;

import java.util.AbstractList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;

public class LinkedList<TElement> extends AbstractList<TElement>
{
    ListNode head;
    LinkedList(ListNode head)
    {
        this.head = head;
    }
    public LinkedList()
    {
        
    }
    @Override
    public TElement get(int index) {
        var iter = iterator();
        TElement res = null;
        for (int i = 0; i != index; ++i)
        {
            if (!iter.hasNext())
                throw new IndexOutOfBoundsException();
            res = iter.next();
        }
        return res;
    }
    ListNode getNode(int index)
    {
        var iter = iterator();
        for (int i = 0; i != index; ++i)
        {
            if (!iter.hasNext())
                throw new IndexOutOfBoundsException();
            iter.next();
        }
        return iter.last;
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
        public LinkedListIterator()
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
                LinkedList.this.head = new ListNode(e, null);
                return;
            }
            var cur = last;
            var nextNode = currentNode;
            var newNode = new ListNode(e, nextNode);
            cur.next = newNode;
        }
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
