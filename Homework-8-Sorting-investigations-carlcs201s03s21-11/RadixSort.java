import java.util.*;
import java.io.*;

public class RadixSort {
    
    /**
     * Adds word to queue based on last letter. If the word is shorter than the others, we create a "blank" space at the end of the word so we can completely go through all the words
     */
    private static void sort(boolean blank, String word, char curLetter, List<Queue<String>> listOfQueues) {
        if (blank) {
            listOfQueues.get(26).add(word);
        } else if (curLetter == 'a') {
            listOfQueues.get(0).add(word);
        } else if (curLetter == 'b') {
            listOfQueues.get(1).add(word);
        } else if (curLetter == 'c') {
            listOfQueues.get(2).add(word);
        } else if (curLetter == 'd') {
            listOfQueues.get(3).add(word);
        } else if (curLetter == 'e') {
            listOfQueues.get(4).add(word);
        } else if (curLetter == 'f') {
            listOfQueues.get(5).add(word);
        } else if (curLetter == 'g') {
            listOfQueues.get(6).add(word);
        } else if (curLetter == 'h') {
            listOfQueues.get(7).add(word);
        } else if (curLetter == 'i') {
            listOfQueues.get(8).add(word);
        } else if (curLetter == 'j') {
            listOfQueues.get(9).add(word);
        } else if (curLetter == 'k') {
            listOfQueues.get(10).add(word);
        } else if (curLetter == 'l') {
            listOfQueues.get(11).add(word);
        } else if (curLetter == 'm') {
            listOfQueues.get(12).add(word);
        } else if (curLetter == 'n') {
            listOfQueues.get(13).add(word);
        } else if (curLetter == 'o') {
            listOfQueues.get(14).add(word);
        } else if (curLetter == 'p') {
            listOfQueues.get(15).add(word);
        } else if (curLetter == 'q') {
            listOfQueues.get(16).add(word);
        } else if (curLetter == 'r') {
            listOfQueues.get(17).add(word);
        } else if (curLetter == 's') {
            listOfQueues.get(18).add(word);
        } else if (curLetter == 't') {
            listOfQueues.get(19).add(word);
        } else if (curLetter == 'u') {
            listOfQueues.get(20).add(word);
        } else if (curLetter == 'v') {
            listOfQueues.get(21).add(word);
        } else if (curLetter == 'w') {
            listOfQueues.get(22).add(word);
        } else if (curLetter == 'x') {
            listOfQueues.get(23).add(word);
        } else if (curLetter == 'y') {
            listOfQueues.get(24).add(word);
        } else if (curLetter == 'z') {
            listOfQueues.get(25).add(word);
        }
    }
    
    /**
     * Uses standard least significant digit radix sort to sort the
     * words.
     *
     * @param A list of words. Each word contains only lower case letters in a-z.
     * @return A list with the same words as the input argument, in sorted order
     */
    public static List<String> radixSort(List<String> words) {
        //create a main queue to hold the stuff we need to sort
        Queue<String> mainQueue = new ArrayDeque<String>();
        //create a longest variable to identify what word is the longest so we only iterate for that long
        int longest = 0;
        for (String word : words) {
            longest = Math.max(longest, word.length());
            //add them to the main queue so we can sort them
            mainQueue.add(word);
        }
        //clear words so we can add back in the sorted words
        words.clear();
        //create a list of queues so we can keep track of all the queues we need
        List<Queue<String>> listOfQueues = new ArrayList<>();
        //create a queue for each letter in the alphabet and one for a blank space
        for (int i = 0; i < 27; i++) {
            Queue<String> queue = new ArrayDeque<String>();
            listOfQueues.add(queue);
        }
        //our actual sorting
        //for loop goes until we have gone through enough times to look at the longest word (aka works through each letter in each word from last letter to first)
        for (int i = 0; i < longest; i++) {
            //go through entire main queue and sort each word into its respective queue
            while (!mainQueue.isEmpty()) {
                // remove word from main queue
                String word = mainQueue.poll();
                char curLetter;
                //test if we should have a blank
                if (i < longest - word.length()) {
                    sort(true, word, 'a', listOfQueues);
                } else {
                    curLetter = word.charAt(longest - i - 1);
                    sort(false, word, curLetter, listOfQueues);
                }
            }

            // put everything back into mainQueue
            for (Queue<String> queue : listOfQueues) {
                while (!queue.isEmpty()) {
                    //add words from each letter queue back into mainQueue
                    mainQueue.add(queue.poll());
                }
            }
        }
        // copy everything from mainQueue to words after we have sorted everything
        while (!mainQueue.isEmpty()) {
            words.add(mainQueue.poll());
        }
        // return newly sorted list of words
        return words;
    }
                
    /**
     * A variation on radix sort that sorts the words into buckets by their initial letter,
     * and then uses standard radix sort to separately sort each of the individual 
     * buckets. Recombines at the end to get a fully sorted list.
     *
     * @param A list of words. Each word contains only lower case letters in a-z.
     * @return A list with the same words as the input argument, in sorted order
     */
    public static List<String> msdRadixSort(List<String> words) {
        //create list of "buckets" (one for each letter)
        List<Queue<String>> listOfQueues = new ArrayList<Queue<String>>();
        //creates buckets to keep words in
        for (int i = 0; i < 26; i++) {
            Queue<String> bucket = new ArrayDeque<String>();
            listOfQueues.add(bucket);
        }
        
        //for each word in input file, sort into bucket by first letter
        for (String word : words) {
            char curLetter = word.charAt(0);
            sort(false, word, curLetter, listOfQueues);
        }
        //clear words list so it can be added to later
        words.clear();

        for (Queue<String> queue : listOfQueues) {
            //sort each queue using radix sort
            List<String> inputList = new ArrayList<String>();
            while (!queue.isEmpty()) {
                inputList.add(queue.poll());
            }
            //radix sort returns List<String> inputList
            radixSort(inputList);
            
            //add each word from input list back to words
            for (String word : inputList) {
                words.add(word);
            }
        }
        //return list of words
        return words;
    }

    public static void main(String[] args) {
        List<String> radixSortList = new ArrayList<String>();
        int numRuns = 10;
        File wordFile = new File("sampleWords.txt");
        Scanner wordScanner = null;
        try {
            wordScanner = new Scanner(wordFile);
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }
        
        while (wordScanner.hasNextLine()) {
            String word = wordScanner.nextLine();
            radixSortList.add(word);
        }

        double total = 0;

        for(int i = 0; i < numRuns + 1; i++) {
            // shuffle
            Collections.shuffle(radixSortList);

            //startTime and endTime enclose only the sorting, not anything else. Make sure to 
            //avoid timing things like shuffling the array or printing (printing to the console
            //takes a very long time!)
            long startTime = System.currentTimeMillis();
            radixSort(radixSortList);
            //msdRadixSort(radixSortList)
            long endTime = System.currentTimeMillis();
            if (i != 0) {//The first time through a particular piece of code may take longer, so we ignore it.
                total += (endTime - startTime);
            }
        }
        System.out.println("radix sort: " + (total / numRuns));
        //System.out.println("msd radix sort: " + (total / numRuns));
    }

}