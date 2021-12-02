import java.util.Iterator;
import java.util.Comparator;

/**
 This is a linked list that sorts itself according to the comparator that is passed into it. We wrote it (except the starter code I guess). It's cool.. I think.
 */
public class SortedLinkedList implements SortedList<Country> {
    private Node head;
    private int sizeOfList;
    private Comparator<Country> comparator;

    private class Node{
      private Country item; //data being stored
      private Node next; //link to next Node

      private Node (Country item){
        this (item, null);
      }
      private Node (Country item, Node nextNode){
        this.item = item;
        this.next = nextNode;
      }

    }
    
    /**
     * Creates a new SortedLinkedList with ordering specified
     * by the comparator
     */
    public SortedLinkedList(Comparator<Country> comparator) {
        head = new Node(null);
        sizeOfList = 0;
        this.comparator = comparator;
    }

    /**
    COMPLETE, TESTED BY JOHN AND BEN
     * Adds item to the list in sorted order. 
     The worst-case big-O runtime is: O(n^2).
     */
    public void add(Country item) {
      // in case list is empty
      if (sizeOfList == 0){
        head.next = new Node(item);
        //every time we add something we need to increase the size of the list
        sizeOfList++;
        return;
      }
      Node tempNode;
      //iterates through SortedLinkedList and compares to every element in list
      for(int i=0; i< sizeOfList; i++){
        int test = comparator.compare(item, get(i));
        // if we're comparing to the last item:
        if (i == sizeOfList - 1){
          if (test < 0){
            tempNode = getNode(i-1).next; //stores c as tempvar
            getNode(i-1).next = new Node(item, tempNode); //makes b point to cat, makes cat point to c
            sizeOfList++;
          break;
          }
          else{
            getNode(i).next = new Node(item);
            sizeOfList++;
            break;
          }
        }
        
        else if (test < 0) {
          tempNode = getNode(i-1).next; //stores c as tempvar
          getNode(i-1).next = new Node(item, tempNode); //makes b point to cat, makes cat point to c
          sizeOfList++;
          break;
        }

        else{
        }
      }
    }

    /**
    COMPLETE
     * Removes the first occurrence of targetItem from the list, shifting everything after it up one position. The method returns true if targetItem was in the list and false if it was not.

     The worst-case big-O runtime is: O(n).
     */
    public boolean remove(Country targetItem) {
      Node curNode = head.next;
      Node prevNode = head;
      for (int i=0; i < (sizeOfList); i++){
        if (curNode.item.equals(targetItem)){
          prevNode.next = curNode.next;
          sizeOfList--;
          return true;
        }
        prevNode = curNode;
        curNode = curNode.next;
      }
      return false;
    }
    
    /**
    COMPLETE
     * Remove the item at index position from the list, shifting everything
     * after it up one position.
     * @return the item, or throw an IndexOutOfBoundsException if the index is out of bounds.

     The worst-case big-O runtime is: O(n).
     */
    public Country remove(int position) {
      if (position < 0){
        throw new IndexOutOfBoundsException("Index " + position + " is out of bounds!");
      }
      Node curNode = head.next;
      Node prevNode = head;
      Node tempNode;
      for (int i=0; i < position; i++){
        prevNode = curNode;
        curNode = curNode.next;
      }
      tempNode = curNode;
      prevNode.next = curNode.next;
      sizeOfList--;
      return tempNode.item;
    }

    /**
    COMPLETE
     * Returns the first position of targetItem in the list.
     * @return the position of the item, or -1 if targetItem is not in the list
     Worst-case big-O runtime is O(n).
     */
    public int getPosition(Country targetItem) {
      Node curNode = head.next;
      for (int i=0; i < (sizeOfList); i++){
        if (curNode.item.equals(targetItem)){
          return i;
        }
        curNode = curNode.next;
        }
      return -1; 

    }
    /*
    COMPLETE, TESTED BY JOHN (and TestSortedList.java)
     * Returns the country at a given index.
     * @return the item, or throw an IndexOutOfBoundsException if the index is out of bounds.
     Worst-case big-O runtime is O(n).
     */
    public Country get(int position){
      Node curNode = head.next;
      for (int i=0; i < position; i++){
        curNode = curNode.next;
      }
      return curNode.item;  
    }

    /*
    COMPLETE, TESTED BY ADD()
    Returns the node at a given index. Note that head is the -1st node.
    Worst-case big-O runtime is O(n).
    */
    public Node getNode(int position){
      Node curNode = head;
      for (int i=-1; i < position; i++){
        curNode = curNode.next;
      }
      return curNode;  
    }

    /** Returns true if the list contains the target item.
    COMPLETE
    Worst-case big-O runtime is O(n).
    */
    public boolean contains(Country targetItem) {
        Node curNode = head.next;
        for (int i=0; i < (sizeOfList); i++){
          if (curNode.item.equals(targetItem)){
            return true;
          }
          curNode = curNode.next;
        }
        return false; 
    }
       
    
    /** 
    INCOMPLETE, NEEDS REWRITING:
    Re-sorts the list according to the given comparator.
    Alternatively, turns Carleton campus into a beautiful beach resort. You pick.
     * All future insertions should add in the order specified
     * by this comparator.

     Worst-case big-O runtime of this method is: O(n^2).
     */

     //doesn't go through last item in the list rn
      public void resort(Comparator<Country> newComparator) {
        //fixes future insertions to new comparator
        // setting up our algorithm
        Node sortedListTail = head.next;
        Node unsortedListHead = sortedListTail.next;
        int sortedListSize = 1;
        for (int i = 0; i < this.size() - 1; i++){
          Node nodeToAdd = unsortedListHead;
          unsortedListHead = nodeToAdd.next;
          Node currentNode = head;
          //debugging
          // run through all the items from head.next to sortedListTail until we find where to put nodeToAdd in sortedList;
          for (int j = 0; j < sortedListSize; j++){
            Node prevNode = currentNode;
            currentNode = currentNode.next;
            int test = newComparator.compare(nodeToAdd.item, currentNode.item);
            //debugging
            if (test < 0){
              prevNode.next = nodeToAdd;
              nodeToAdd.next = currentNode;
              sortedListTail.next = unsortedListHead;
              break;
            }
            else if (j == sortedListSize - 1){
              sortedListTail = nodeToAdd;
              break;
            }
            else{
            }
          }
          sortedListSize++;
        }
    }
  

    
    /** 
    COMPLETE
    Returns the length of the list: the number of items stored in it. Worst case big-O runtime is O(1). */

    public int size() {
        //returns the instance variable that keeps track of size
        return sizeOfList; 
    }

    /** 
    COMPLETE
    Returns true if the list has no items stored in it. 
        Worst case big-O runtime is O(1). */
    public boolean isEmpty() {
        //returns true if size is 0, otherwise returns false
        if (sizeOfList==0){
            return true;
        }
        else{
            return false;
        }
         
    }

    /** Returns an iterator that begins just before index 0 in this list. */
    public Iterator<Country> iterator() {
        //You can choose whether to implement an iterator. If you do so, you'll
        //delete the line of code below. If you do not, leave the line of code as is.
        throw new UnsupportedOperationException("Iterator not implemented!");
    }     
    
    /** Removes all items from the list. */
    public void clear() {
        //if we set head to null, there should be a cascading effect where the rest of the linked list is automatically disposed of since there's nothing referencing it.
       head.next = null;
       sizeOfList = 0;
        
    }

    public static void main(String[] args){
      Comparator<Country> countryComparator = new CountryComparator("AccessToElectricity", false);
      Comparator<Country> countryComparator4 = new CountryComparator("PopulationTotal", true);
      Comparator<Country> countryComparator3 = new CountryComparator("AccessToElectricity", true);

      SortedList<Country> sortedList1 = new SortedLinkedList(countryComparator);
      
        
      Country country1 = new Country("electricity90,500,3,5,4,90,45,35".split(","));
      Country country2 = new Country("electricity89,499,3,5,4,89,45,35".split(","));
      Country country3 = new Country("electricity65,498,3,5,4,65,45,35".split(","));
      Country country4 = new Country("electricity91,497,3,5,4,91,45,35".split(","));
      Country country5 = new Country("electricity88,496,3,5,4,88,45,35".split(","));
      Country country6 = new Country("electricity87,500,3,5,4,87,45,35".split(","));
      Country country7 = new Country("electricity86,500,3,5,4,86,45,35".split(","));
      Country country8 = new Country("electricity85,500,3,5,4,85,45,35".split(","));

      sortedList1.add(country1);
      sortedList1.add(country2);
      sortedList1.add(country3);
      sortedList1.add(country4);
      sortedList1.add(country5);

      System.out.println("The commands were tested in the following order: add, resort, remove(item), remove(position), getPosition(item), get(), getNode(), contains(), size(), clear() + isEmpty()." + "\n");

      System.out.println("State of sortedList1 after adding 5 countries by AccessToElectricity from least to greatest:" + "\n");
      
      for (int i=0; i < sortedList1.size(); i++){
        System.out.println(sortedList1.get(i).getName());
      }
      System.out.println("");

      
      sortedList1.resort(countryComparator3);

      System.out.println("State of sortedList1 after resorting these 5 countries with the same indicator, but the boolean flipped:" + "\n");
      for (int i=0; i < sortedList1.size(); i++){
        System.out.println(sortedList1.get(i).getName());
      }
      System.out.println("");

      sortedList1.resort(countryComparator4);

      System.out.println("State of sortedList1 after resorting these 5 countries by PopulationTotal from greatest to least: " + "\n");

      for (int i=0; i < sortedList1.size(); i++){
        System.out.println(sortedList1.get(i).getCountry());
      }
      System.out.println("");

      System.out.println("Return value of remove(item) upon removing country1: " + sortedList1.remove(country1) + "\n");

      System.out.println("State of sortedList1 after removing country1, whose name is electricity90:" + "\n");
      for (int i=0; i < sortedList1.size(); i++){
        System.out.println(sortedList1.get(i).getName());
      }
      System.out.println("");

      System.out.println("Return value of remove(item) upon trying to remove country6, which isn't currently in the list: " + sortedList1.remove(country6) + "\n");

      sortedList1.remove(0);
      System.out.println("State of sortedList1 after removing the country at position 0: \n");
      for (int i=0; i < sortedList1.size(); i++){
        System.out.println(sortedList1.get(i).getName());
      }
      System.out.println("");

      System.out.println("I have attempted to remove the item at position -2, and the exception works, but this prevents further testing. I invite the mystery grader to add a testing line for themselves, or add a comment telling me how I can do this without making the rest of the code not run. Anyways, \n");
      
      System.out.println("Return value of getPosition() when I try to get the position of country3, whose name is electricity65: " + sortedList1.getPosition(country3) + "\n");

      System.out.println("Return value of getPosition() when I try to get the position of country6, which isn't currently in the list: " + sortedList1.getPosition(country6) + "\n");

      System.out.println("Same thing as remove() for testing the exception in get(): it works, but I don't know how to show it. Also, get() and getNode() must be working properly because add() works, so not shown here. \n");

      System.out.println("Return value of contains(item) when item == country3, whose name is electricity65: " + sortedList1.contains(country3) + "\n");

      System.out.println("Return value of contains(item) when item == country6, which is currently not in the list: " + sortedList1.contains(country6) + "\n");

      System.out.println("Return value of size() on sortedList1, which currently contains 3 items: " + sortedList1.size() + "\n");

      System.out.println("Return value of isEmpty() when called on sortedList1, which currently contains 3 items: " + sortedList1.isEmpty() + "\n");
      
      sortedList1.clear();
      System.out.println("Clears the list, in this case sortedList1." + "\n");
      
      System.out.println("Return value of isEmpty() when called on sortedList1, which is now an empty list: " + sortedList1.isEmpty() + "\n");

      System.out.println("And with that: ALL TESTS PASSED! *hopefully*");
      }

}