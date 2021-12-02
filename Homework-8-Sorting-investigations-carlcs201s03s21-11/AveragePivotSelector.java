import java.lang.Math.*;

public class AveragePivotSelector implements PivotSelector {
    /**
     * Returns the index of the index (either first, middle or last, that is closest to the average of the two)
     */
    public int choosePivotIndex(int[] array, int first, int last) {
        int middle = last / 2;
        int average = (array[first] + array[middle] + array[last]) / 3;
        int comparison1 = Math.abs(array[first] - average);
        int comparison2 = Math.abs(array[middle] - average);
        int comparison3 = Math.abs(array[last] - average);
        
        if (Math.min(comparison1, comparison2) == comparison1 && Math.min(comparison1, comparison3) == comparison1) {
            return first;
        } else if (Math.min(comparison1, comparison2) == comparison2 && Math.min(comparison2, comparison3) == comparison2) {
            return middle;
        } else {
            return last;
        }
    }
}