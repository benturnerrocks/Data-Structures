import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * This class has some initial tests for your sorted list implementation.
 * You are *strongly* encouraged to modify it and/or to write your own 
 * tests. It's intended to get you started writing tests, but it does 
 * not include all the testing that we will be doing on your homework
 * submissions.
 */
public class TestSortedList {

    /**
     * Returns true if the first expectedOrder.size() items in expectedOrder
     * and actualOrder are the exact same objects. Otherwise, returns false.
     * Crashes if actualOrder has fewer than size items.
     * Assumes a working implementation of get in actualOrder.
     */
    private static boolean equalLists(List<Country> expectedOrder, 
            SortedList<Country> actualOrder) {
        for(int i = 0; i < expectedOrder.size(); i++) {
            if(expectedOrder.get(i) != actualOrder.get(i)) {
                System.err.println("Incorrect object at spot " + i);
                return false;
            }
        }
        return true;
    }

    /**
     * Creates a country based on countryString and adds that country to the 
     * sorted list as well as the expectedOrder list, adding to the expectedOrder
     * list at the index expectedSpot.
     */
    private static void addNewCountry(String countryString, int expectedSpot,   
            List<Country> expectedOrder, SortedList<Country> sortedList) {
        Country newCountry = new Country(countryString.split(","));
        sortedList.add(newCountry);
        expectedOrder.add(expectedSpot, newCountry);
    }

    /** 
     * Some initial tests for the add and get methods.
     * Checks adding to an empty list, adding to the beginning
     * of a non-empty list, and adding in the second spot in a
     * non-empty list. You might add additional tests - e.g.,
     * adding to the end of a non-empty test.
     */
    public static boolean testAddGet() {
        boolean allTestsPassed = true;
        Comparator<Country> countryComparator = new CountryComparator("AccessToElectricity", false);
        SortedList<Country> sortedList = new SortedLinkedList(countryComparator);
        Country electricity90 = new Country("A,500,3,5,4,90,45,35".split(","));
        sortedList.add(electricity90);

        //Using != rather than ! with .equals because we expect to get the same
        //object back.
        if(sortedList.get(0) != electricity90) {
            allTestsPassed = false;
            System.err.println("Added electricity90 but didn't get it back " +
            "at spot 0. Instead, got: " + sortedList.get(0));
        }
      
      
        //Make it easier to store my expected ordering
        List<Country> expectedOrder = new ArrayList<Country>();
        expectedOrder.add(electricity90);

        addNewCountry("B,500,3,5,4,85,45,35", 0, expectedOrder, sortedList);
        if(!equalLists(expectedOrder, sortedList)) {
            allTestsPassed = false;
            System.err.println("Error after adding two countries");
        }

        
        addNewCountry("C,500,3,5,4,87,45,35", 1, expectedOrder, sortedList);
        if(!equalLists(expectedOrder, sortedList)) {
            allTestsPassed = false;
            System.err.println("Error after adding three countries");
        }

        //Add more tests here!

        
        
        return allTestsPassed;

    }
    
    public static void main(String[] args) {
        boolean allTestsPassed = true;
        allTestsPassed = allTestsPassed && testAddGet();

        if(allTestsPassed) {
            System.out.println("All tests passed!");
        }  

    }
}