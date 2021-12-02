import java.util.*;
import java.time.*;

public class FlashcardPriorityQueue implements PriorityQueue<Flashcard> {

    //keeps track of number of items in array
    int numItems;
    //the array we store the heap in
    Flashcard[] minHeap;

    /**
    * Creates an empty priority queue.
    */
    public FlashcardPriorityQueue() {
        //create an array with 
        this.minHeap = new Flashcard[21];
        this.numItems = 0;
    }
      /** Adds the given item to the queue. */
    public void add(Flashcard item) {
        //check if the list is long enough to add another item
        if (this.minHeap.length - 1 == numItems) {
            //if it is not long enough, resize
            this.resize();
        }
        //then we add the item onto the end (if we had numItems, the next open index will be at numItems before we adjust it)
        this.minHeap[numItems] = item;

        //increment number of items
        this.numItems++;

        //then we adjust the list so it also has the right order
        this.bubbleUp();
    }

    //a method that will always bubble up the last item in the array to its correct spot based off of dueDate to get correct order in heap
    private void bubbleUp() {
        //curNode is the index of the item we just added
        int curNode = this.numItems-1;
        int parent = (curNode-1)/4;
        //create a loop to move the new item up the list until we get to the spot the item should be at or it has reach the top of the heap
        Flashcard tempCard;
        while (curNode != 0 && this.minHeap[parent].compareTo(this.minHeap[curNode])>0) {
            tempCard = this.minHeap[parent];
            //swap the parent and child
            this.minHeap[parent] = this.minHeap[curNode];
            this.minHeap[curNode] = tempCard;
            curNode = parent;
            parent = (curNode-1)/4;
        }    
    }

    //a method that will always bubble down the first item in the array (root of heap) to its correct spot based off of dueDate to get correct order in heap
    private void bubbleDown() {
        //establish the indices of the node we are at and its children
        int curNode= 0;
        int firstChild = 4*curNode +1;
        int secondChild = 4*curNode +2;
        int thirdChild = 4*curNode +3;
        int fourthChild = 4*curNode +4;
        //test if we have fixed the order or the item being moved is now at a leaf
        int minCardIndex = this.dueDateMinIndex(firstChild, secondChild, thirdChild, fourthChild);
        Flashcard minCard = this.minHeap[minCardIndex];
        //code testing
        /*
        System.out.println("TEST minCard:" + minCard.getFrontText());
        */
        Flashcard tempCard = null;
        //while we have children and they require a swap with the current node
        while (this.hasChildren(curNode) && minCard.compareTo(this.minHeap[curNode]) < 0) {
            //so we know since we are in the loop that we need to swap the min child with the flashcard we are moving 
            tempCard = this.minHeap[curNode];
            this.minHeap[curNode] = minCard;
            this.minHeap[minCardIndex] = tempCard;
            //update all the variables
            curNode = minCardIndex;
            firstChild = 4*curNode+1;
            secondChild = 4*curNode+2;
            thirdChild = 4*curNode+3;
            fourthChild = 4*curNode+4;
            minCardIndex = this.dueDateMinIndex(firstChild, secondChild, thirdChild, fourthChild);
            minCard = this.minHeap[minCardIndex];
            //code testing
            /* if (minCard != null && tempCard != null) {
            System.out.println("TEST minCard:" + minCard.getFrontText());
            System.out.println("TEST toSwap card:" + tempCard.getFrontText());
            } */
        }           
    }

    //checks if the index/node we are at has children
    private boolean hasChildren(int indexItem) {
        //establish what the indices of the children should be
        //we know that the following children should be greater than this index so if we have the first child, we have at least one child if not all
        int firstChild = 4*indexItem+1;
        return firstChild < numItems-1 ;
    }

    //resizes the array if need be
    private void resize() {
        //create a temporary array that has the new capacity we want
        Flashcard[] tempArray = new Flashcard[(numItems+1)*2];
        //transfer data from old array into new array
        for (int i=0; i<numItems; i++) {
            tempArray[i] = this.minHeap[i];
        }
        //set our instance variable to the new array which now has extra space
        this.minHeap = tempArray;
    }
    /** Removes the first item according to compareTo from the queue, and returns it.
     * Throws a NoSuchElementException if the queue is empty.
     */
    public Flashcard poll() {
        //test if we actually have a heap
        if (this.isEmpty()) {
            //if we don't, throw NoSuchElementException
            throw new NoSuchElementException();
        }
        //get and store the root
        Flashcard cardToPoll = this.minHeap[0];
        //remove the last leaf so we can maintain proper shape
        Flashcard newRoot = this.minHeap[this.numItems-1];
        this.minHeap[this.numItems-1] = null;
        //set the root to be the last leaf
        this.minHeap[0] = newRoot;
        //code testing
        /* 
        System.out.println("TEST TOP:" + this.minHeap[0].getFrontText());
        */
        //we have officially gotten rid of the item we are trying to poll so decrement number of items
        this.numItems--;

        //now we "bubble down" the heap until we fix the order
        this.bubbleDown();
        //return the card we want
        return cardToPoll;
    }

    public int getNumItems() {
        return this.numItems;
    }

    //a minimum method that returns the lowest of four flashcards
    public int dueDateMinIndex(int index1, int index2, int index3, int index4) {
        //we will us this in adjusting our heap in the poll method
        //keep track of which index Flashcard is our minimum
        // make the assumption here that a will always be the first child and that we know there is at least one child (a)
        Flashcard a = this.minHeap[index1];
        Flashcard b = this.minHeap[index2];
        Flashcard c = this.minHeap[index3];
        Flashcard d = this.minHeap[index4];
        int min = index1;
        if (b != null && b.compareTo(a) < 0) {
            //if b is less than a, comparison will be negative
            min = index2;
        }

        if (c != null && c.compareTo(this.minHeap[min]) < 0) {
            min = index3;
        }

        if (d != null && d.compareTo(this.minHeap[min]) < 0) {
            min = index4;
        }
            
        return min;
    }

    public Flashcard[] getHeap() {
        return this.minHeap;
    }
    
    /** Returns the first item according to compareTo in the queue, without removing it.
     * Throws a NoSuchElementException if the queue is empty.
     */
    public Flashcard peek() {
        //test if we actually have a heap
        if (this.isEmpty()) {
            //if we don't, throw NoSuchElementException
            throw new NoSuchElementException();
        }
        return this.minHeap[0];
    }
    
    /** Returns true if the queue is empty. */
    public boolean isEmpty() {
        //checks if first element is null
        return this.minHeap[0] == null;
    }
    
    /** Removes all items from the queue. */
    public void clear() {
        //creates new empty instance variables
        this.minHeap = new Flashcard[21];
        this.numItems = 0;
    }
    public static void main(String[] args) {

        FlashcardPriorityQueue testPriorityQueue = new FlashcardPriorityQueue();

        //System.out.println("test.isEmpty() should return true here: " + test.isEmpty());
        System.out.println("should print true! : " + testPriorityQueue.isEmpty());

        Flashcard test1 = new Flashcard("2000-11-12T13:03", "1st", "Amigo");
        Flashcard test2 = new Flashcard("2001-12-12T13:03", "2nd", "bye");
        Flashcard test3 = new Flashcard("2002-12-12T13:03", "3rd", "bye");
        Flashcard test4 = new Flashcard("2004-12-12T13:03", "4th", "uyg3fr");
        Flashcard test5 = new Flashcard("2005-12-12T13:03", "5th", "llwf");
        Flashcard test6 = new Flashcard("2006-12-12T13:03", "6th", "alo");
        Flashcard test7 = new Flashcard("2007-12-12T13:03", "7th", "igylf");
        Flashcard test8 = new Flashcard("2008-12-12T13:03", "8th", "w0Y8HO");
        Flashcard test9 = new Flashcard("2009-12-12T13:03", "9th", "we;gfiu");
        Flashcard test10 = new Flashcard("2010-12-12T13:03", "10th", "igweiou");
        Flashcard test11 = new Flashcard("2011-12-12T13:03", "11th", "cars");
        Flashcard test12 = new Flashcard("2012-12-12T13:03", "12th", "jeep");
        Flashcard test13 = new Flashcard("2013-12-12T13:03", "13th", "popppp");
        Flashcard test14 = new Flashcard("2014-12-12T13:03", "14th", "salamander");
        Flashcard test15 = new Flashcard("2015-12-12T13:03", "15th", "isnake");
        Flashcard test16 = new Flashcard("2016-12-12T13:03", "16th", "ierhe");
        Flashcard test17 = new Flashcard("2017-12-12T13:03", "17th", "igywes");
        Flashcard test18 = new Flashcard("2018-12-12T13:03", "18th", "fuowbkjf");
        Flashcard test19 = new Flashcard("2019-12-12T13:03", "19th", "wlgiuw");
        Flashcard test20 = new Flashcard("2020-12-12T13:03", "20th", "8774y");
        Flashcard test21 = new Flashcard("2021-12-12T13:03", "21st", "aifug");
        Flashcard test22 = new Flashcard("2022-12-12T13:03", "22nd", "woefghu");
        Flashcard test23 = new Flashcard("2023-12-12T13:03", "23rd", "giluerg");


        System.out.println("the initial array length should be 21:  " + testPriorityQueue.getHeap().length);
        testPriorityQueue.add(test23);

        System.out.println("should print false! : " + testPriorityQueue.isEmpty());

        testPriorityQueue.add(test22);
        testPriorityQueue.add(test21);
        testPriorityQueue.add(test20);
        testPriorityQueue.add(test19);
        testPriorityQueue.add(test18);
        testPriorityQueue.add(test17);
        testPriorityQueue.add(test16);
        testPriorityQueue.add(test15);
        testPriorityQueue.add(test14);
        testPriorityQueue.add(test13);
        testPriorityQueue.add(test12);
        testPriorityQueue.add(test11);
        testPriorityQueue.add(test10);
        testPriorityQueue.add(test9);
        testPriorityQueue.add(test8);
        testPriorityQueue.add(test7);
        testPriorityQueue.add(test6);
        testPriorityQueue.add(test5);
        testPriorityQueue.add(test4);
        testPriorityQueue.add(test3);
        testPriorityQueue.add(test2);
        testPriorityQueue.add(test1);
        System.out.println("the array length now should be 42:  " + testPriorityQueue.getHeap().length);
        System.out.println("the peek value should be 1:  " + testPriorityQueue.peek().getFrontText());


        Flashcard[] flashcards = testPriorityQueue.getHeap();
        System.out.println("the following print series should come out in order of:  1,3,2,12,10,7,4,15,17,16,21,14,13,23,18,11,20,9,8,19,6,5,22");
        for (int i = 0; i < 23; i++) {
            System.out.println(flashcards[i].getFrontText());
        }
        System.out.println("-----------------------------");

        Flashcard a = testPriorityQueue.poll();
        System.out.println("flashcard a should be 1 and 'Amigo':  " + a.getFrontText() + "   " + a.getBackText() );
        System.out.println("the following print series should come out in order of:  2,3,15,12,10,7,4,22,17,16,21,14,13,23,18,11,20,9,8,19,6,5");
        flashcards = testPriorityQueue.getHeap();
        for (int i = 0; i < 22; i++) {
            System.out.println(flashcards[i].getFrontText());
        }

        System.out.println("-----------------------------");
        Flashcard b = testPriorityQueue.poll();
        System.out.println("the following print series should come out in order of:  3,4,15,12,10,7,5,22,17,16,21,14,13,23,18,11,20,9,8,19,6");
        System.out.println("flashcard b should be 2:  " + b.getFrontText());
        flashcards = testPriorityQueue.getHeap();
        for (int i = 0; i < 21; i++) {
            System.out.println(flashcards[i].getFrontText());
        }

        System.out.println("-----------------------------");
        Flashcard c = testPriorityQueue.poll();
        System.out.println("flashcard c should be 3:  " + c.getFrontText());
        flashcards = testPriorityQueue.getHeap();
        for (int i = 0; i < 20; i++) {
            System.out.println(flashcards[i].getFrontText());
        }

        System.out.println("-----------------------------");
        Flashcard d = testPriorityQueue.poll();
        System.out.println("flashcard d should be 4:  " + d.getFrontText());
        flashcards = testPriorityQueue.getHeap();
        for (int i = 0; i < 19; i++) {
            System.out.println(flashcards[i].getFrontText());
        }

        System.out.println("-----------------------------");
        Flashcard f = testPriorityQueue.poll();
        System.out.println("flashcard e should be 5:  " + f.getFrontText());
        flashcards = testPriorityQueue.getHeap();
        for (int i = 0; i < 18; i++) {
            System.out.println(flashcards[i].getFrontText());
        }
        System.out.println("-----------------------------");


        testPriorityQueue.clear();
        System.out.println("should print true! : " + testPriorityQueue.isEmpty());

        try {
        Flashcard emptyPoll = testPriorityQueue.poll();
        System.out.println("Shouldn't print this");
        } catch(NoSuchElementException e) {
        System.out.println("poll() successfully threw a NoSuchElementException.");
        }

        try {
        Flashcard emptyPeek = testPriorityQueue.poll();
        System.out.println("Shouldn't print this");
        } catch(NoSuchElementException e) {
        System.out.println("poll() successfully threw a NoSuchElementException.");
        }

    }
}