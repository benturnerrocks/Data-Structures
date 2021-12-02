/**
* Median of 3 method for selecting a pivot for Quicksort
*/
public class MedianOf3PivotSelector implements PivotSelector {
    /**
     * Returns the median of the first, last, and middle items in the
     * array. I.e., if first = 0 and last = 3, returns median of
     * 0, 1, and 3. Should not modify the contents of array.
     */
    public int choosePivotIndex(int[] array, int first, int last) {
        // use integer division to find index of middle item
        int middle = last / 2;
        if ((array[first] < array[middle]) && (array[middle] < array[last]) || (array[last] < array[middle]) && (array[middle] < array[first])) {
            return middle;
        } else if ((array[middle] < array[first]) && (array[first] < array[last]) || (array[last] < array[first]) && (array[first] < array[middle])) {
            return first;
        } else {
            return last;
        }
    }
}