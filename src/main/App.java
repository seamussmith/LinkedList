package main;

public class App
{
    public static void main(String[] args) 
    {
        var ll = new LinkedList<Integer>();
        ll.add(1);
        ll.add(2);
        ll.add(3);
        ll.add(4);
        ll.add(5);
        ll.add(1, 999);
        System.out.println(ll);
    }
}
