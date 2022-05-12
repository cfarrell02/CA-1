package Utils;

import java.util.Comparator;
import java.util.Iterator;

public class CoolLinkedList<F> implements Iterable<F> {
    public node<F> head;
    public node<F> tail ;
    private int size;

    public boolean add(F e) { //Add element to tail of list
        node<F> fn = new node<>();
        fn.setContents(e);
        if(tail != null){
        tail.next = fn;
        fn.previous = tail;}
        tail = fn;
        if(size ==0)
            head=fn;
        ++size;
        return true;
    }
    public F set(int oldIndex, F newObject){
        if(oldIndex>=0&&oldIndex<size){
        node<F> oldNode = getNode(oldIndex);
        node<F> newNode = new node<>();
        newNode.setContents(newObject);
        if(size==1){
            head = newNode;
            tail = newNode;
        }else {
            if (oldIndex == 0) {
                newNode.next = head.next;
                head.next.previous = newNode;
                head = newNode;
            } else if (oldIndex == (size - 1)) {
                newNode.previous = newNode;
                tail.previous.next = newNode;
                tail = newNode;
            } else {
                newNode.previous = oldNode.previous;
                oldNode.previous.next = newNode;
                newNode.next = oldNode.next;
                oldNode.next.previous = newNode;
            }
        }
        return oldNode.getContents();
        }
        return null;
    }

    public void swap(int i,int j){
        F swapped = get(j);
        set(j,get(i));
        set(i,swapped);
    }
    public int size(){
        return size;}
    public void clear() { //Empty list
        head = null;
        tail = null;
        size = 0;
    }
    public void shellSort(Comparator<F> c){
       // final int[] gaps={5,3,1}; //Other gaps and ways to calculate them
        CoolLinkedList<Integer> gaps = new CoolLinkedList();
        gaps.add(1);
        for(int i = 0 ; Math.pow(2,i)<size;++i){
            gaps.add(0,(int) Math.pow(2,i) + 1);
        }
        for(Integer g : gaps) {

            for(int e=g;e<size;e++){
                F elem=get(e);
                int i;

                for(i=e;i>=g && c.compare(get(i-g),elem)>0;i-=g)
                    set(i,get(i-g));

                set(i,elem);
            }
        }
    }


    // Function to sort a[0..n-1] using Comb Sort
    void combSort(Comparator<F> c)
    {
        // Initialize gap
        int gap = size;
        int n = gap;

        // Initialize swapped as true to make sure that
        // loop runs
        boolean swapped = true;

        // Keep running while gap is more than 1 and last
        // iteration caused a swap
        while (gap != 1 || swapped)
        {
            // Find next gap
            gap = (gap<1) ? (gap*10)/13 : 1;

            // Initialize swapped as false so that we can
            // check if swap happened or not
            swapped = false;

            // Compare all elements with current gap
            for (int i=0; i<n-gap; i++)
            {
                if (c.compare(get(i),get(i+gap))>0)
                {
                    swap(i,i+gap);
                    swapped = true;
                }
            }
        }
    }
    public void mergeSort(Comparator<F> c) {
        if (size > 1) {
            //Create 2 sublists as evenly sized as possible
            int x = size / 2, y = size - x;
            CoolLinkedList<F> xa = new CoolLinkedList<>();
            CoolLinkedList<F> ya = new CoolLinkedList<>();
            //Copy elements into sub lists
            int i;
            for (i = 0; i < x; i++) xa.add(get(i));
            for (int i2 = 0; i2 < y; i2++, i++) ya.add(get(i));
            //Recursively merge sort the sub lists independently
            xa.mergeSort(c);
            ya.mergeSort(c);
            //Merge the 2 sorted sub lists
            i = 0;
            int xai = 0, yai = 0;
            while (xai < xa.size() && yai < ya.size())
                set(i++,(c.compare(xa.get(xai),ya.get(yai)))<0 ? xa.get(xai++) : ya.get(yai++));
            while (xai < xa.size()) set(i++,xa.get(xai++));
            while (yai < ya.size()) set(i++,ya.get(yai++));
        }
    }
    public boolean isEmpty(){
        return size == 0;
    }
    public F remove(int index){
        node<F> deletedNode = getNode(index);

        if(size ==1)
            clear();
        else {
            if (index == 0) {
                head = head.next;
                head.previous = null;
            } else if (index == size - 1) {
                tail = tail.previous;
                tail.next = null;
            } else {
                deletedNode.next.previous = deletedNode.previous;
                deletedNode.previous.next = deletedNode.next;
            }
        }
        size--;
        return deletedNode.getContents();

    }

    private node<F> getNode(int index) {
            node<F> temp = head;
            for (int i = 0; i <= index && temp != null; ++i) {
                if (i == index)
                    return temp;
                temp = temp.next;
            }
        return null;
    }
    public F get(int index){
        if(getNode(index)!=null)
            return getNode(index).getContents();
        return null;
    }



    public void add(int index, F element) {
        node<F> fn = new node<>();
        fn.setContents(element);
        if(head==null)
            head = fn;
        if(tail==null)
            tail = fn;
        if(index == 0){
            fn.next = head;
            head = fn;
        }
        else if(index == size-1){
            fn.previous = tail;
            tail = fn;
        }else{
            fn.previous=getNode(index-1);
            fn.next=getNode(index);
            fn.previous.next = fn;
            fn.next.previous = fn;
        }

        size ++;
    }



    public int indexOf(Object o) {
        node<F> temp = head;
        for(int i=0;temp!=null;++i){
            temp = temp.next;
            if(temp.getContents().equals(o))
                return i;
        }
        return -1;
    }




    public boolean contains(F object){
        node<F> temp = head;
        while(temp!=null){
            if(temp.getContents().equals(object))
                return true;

            temp = temp.next;
        }
        return false;
    }


    public Iterator<F> iterator() {
        return new CoolIterator<F>(head);
    }



}