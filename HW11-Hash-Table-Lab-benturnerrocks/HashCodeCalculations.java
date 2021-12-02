import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * In class lab for learning about hash code functions and collisions.
 * @author arafferty
 * @author YOUR NAME HERE
 */
public class HashCodeCalculations {

    /**
     * Always returns 0.
     */
    public static int hashCode0(String s) {
        return 0;
    }
    
    /**
     * This assigns a hashcode based off of the first character of the string. Note that casting a character to an integer returns its ASCII value - there
     * are 128 characters, and each has a distinct number from 0-127. 
     */
    public static int hashCode1(String s) {
        if(s.isEmpty()) {
            return 0;
        } else {
            return (int) s.charAt(0);
        }
    }
    
    /**
     * Assigns a hashcode that is built off of ASCII values of each letter added together
     */
    public static int hashCode2(String s) {
        int hashCode = 0;
        for(int i = 0; i < s.length(); i++) {
            hashCode += (int) s.charAt(i);
        }
        return hashCode;
    }
    
    /**
     * 
     * Since ASCII code has 128 values, it will multiply the hashcode by 129 to in a sense "moves the digit of the code up a digit". Not sure what collisions would occur, definetely if the same word was used twice  Hint: You might think about how you would write an integer as a sum that
     * uses its digits to get a better understanding of what's happening here. 
     * (Note that this isn't quite the same thing, but it's similar. If you can explain
     *  why this would make sense if 129 was 128, you've likely done a good job explaining
     *  the big idea.)
     */
    public static int hashCode3(String s) {
        int hashCode = 0;
        for(int i = 0; i < s.length(); i++) {
            hashCode = 129*hashCode + (int) s.charAt(i);
        }
        return hashCode;
    }
     
     
    
    /**
     * Implement this function so it works the way we talked about in class.
     * Compression function that takes a hash code (positive or negative) and
     * the number of buckets we have to use in our hash table, and compresses
     * the hash code into the range [0, numberOfBuckets).
     */
    public static int compressToSize(int hashCode, int numberOfBuckets) {
        int compressedValue = hashCode % numberOfBuckets;
        if (compressedValue < 0) {
            return compressedValue + numberOfBuckets;
        }
        return compressedValue;
    }
    
    /**
     * Counts the number of buckets that have no words stored at them - i.e.,
     * they have value 0 - and calculates what proportion of the total buckets
     * that is.
     */
    public static double 
    proportionOfBucketsWithNoWords(int[] buckets) {
        int emptyBucketCount = 0;
        for(int i = 0; i < buckets.length; i++) {
            if(buckets[i] == 0) {
                emptyBucketCount++;
            }
        }
        return emptyBucketCount*1.0/buckets.length;
    }
    
    
    /**
     * Returns the maximum value in a single bucket
     */
    public static int getMaxBucketValue(int[] buckets) {
        int max = -1;//Safe starting value since all buckets[i] should be >= 0
        for(int i = 0; i < buckets.length; i++) {
            if(buckets[i] > max) {
                max = buckets[i];
            }
        }
        return max;
    }
    
    /**
     * Returns the average number of words in each non-empty bucket
     */
    public static double getAverageInNonEmptyBuckets(int[] buckets) {
        int totalCount = 0;
        int totalNonEmpty = 0;
        for(int i = 0; i < buckets.length; i++) {
            totalCount += buckets[i];
            if(buckets[i] != 0) {
                totalNonEmpty++;
            }
        }
        return totalCount*1.0/totalNonEmpty;
    }
    
    
    /**
     * Implement this method so that it calculates how many words would be placed
     * in each bucket in the array. 
     * Each individual word should be counted only once (i.e., if "the" occurs 
     * 501 times in the file, you should only hash it once, rather than thinking 
     * of it as causing 500 collisions).
     * @param numBuckets number of spots to include in the array
     * @param file file to read from
     * @param hashCodeFunctionToUse which of the hash functions to use; see lab 
     *                              description for more details
     * @return an array that indicates how many different words are place in index 0, 1, etc.
     */
    public static int[] collisionCounter(int numBuckets, String file, int hashCodeFunctionToUse) {
        //Initialize the variables you'll need to count collisions (an array, a set)
        int[] arrayHashTable = new int[numBuckets];
        //to keep track of if we've seen a word
        HashSet<String> seenWords = new HashSet<String>();

        try {
            Scanner scanner =  new Scanner(new File(file));
            //Write your code for counting collisions here.
            while (scanner.hasNextLine()) {
                String curWord = scanner.nextLine();
                //check if we have seen the word, if not then run the hashcode and add it to the array
                if (!seenWords.contains(curWord)) {
                    int hashcode = 0;
                    //make the hashcode depending on the hashcode method asked for 
                    if (hashCodeFunctionToUse == -1) {
                        hashcode = curWord.hashCode();
                    } else if (hashCodeFunctionToUse == 0) {
                        hashcode = hashCode0(curWord);
                    } else if (hashCodeFunctionToUse == 1) {
                        hashcode = hashCode1(curWord);
                    } else if (hashCodeFunctionToUse == 2) {
                        hashcode = hashCode2(curWord);
                    } else if (hashCodeFunctionToUse == 3) {
                        hashcode = hashCode3(curWord);
                    } else {
                        //provide an error statement if the user asks to use an invalid hashcode
                        System.out.println("Invalid hashCodeFunctionToUse.");
                        System.exit(1);
                    }
                    //add word to set to indicate we have seen it
                    seenWords.add(curWord);
                    //add one to the correct bucket
                    arrayHashTable[compressToSize(hashcode,numBuckets)]++;
                }
            }

            scanner.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        return arrayHashTable;//Change this line to return your count of collisions
    }
    
    public static void main(String[] args) {
        HashCodeCalculations newTable = new HashCodeCalculations();
        String[] arrayWords = new String[]{"ant","tan","mop","tiger"};
        //testing each individual hashcodde
        for (int i=0; i<arrayWords.length; i++) {
            System.out.println(newTable.hashCode0(arrayWords[i]));
            System.out.println(newTable.hashCode1(arrayWords[i]));
            System.out.println(newTable.hashCode2(arrayWords[i]));
            System.out.println(newTable.hashCode3(arrayWords[i]));
            System.out.println("next is:\n");
        }
        //testing collisions for 2039 buckets and words.txt
        System.out.println("\nCollisions for 2039 buckets with words.txt");
        for (int i=-1; i<4; i++) {
            int[] curArray = newTable.collisionCounter(2039, "words.txt", i);
            System.out.println("\nCollisions for hashcode" + i +":");
            System.out.println("Max Bucket Value:" + getMaxBucketValue(curArray));
            System.out.println("Average of Non-Empty Buckets:" + getAverageInNonEmptyBuckets(curArray));
            System.out.println("Proportion Of Buckets With No Words:" + proportionOfBucketsWithNoWords(curArray));
        }
        //testing collisions for 2048 buckets and words.txt
        System.out.println("\nCollisions for 2048 buckets with words.txt");
        for (int i=-1; i<4; i++) {
            int[] curArray = newTable.collisionCounter(2048, "words.txt", i);
            System.out.println("\nCollisions for hashcode" + i +":");
            System.out.println("Max Bucket Value:" + getMaxBucketValue(curArray));
            System.out.println("Average of Non-Empty Buckets:" + getAverageInNonEmptyBuckets(curArray));
            System.out.println("Proportion Of Buckets With No Words:" + proportionOfBucketsWithNoWords(curArray));
        }
        
        //testing collisions for 2039 buckets and HoundOfTheBaskervilles.txt
        System.out.println("\nCollisions for 2039 buckets with HoundOfTheBaskervilles");
        for (int i=-1; i<4; i++) {
            int[] curArray = newTable.collisionCounter(2039, "HoundOfTheBaskervilles.txt", i);
            System.out.println("\nCollisions for hashcode" + i +":");
            System.out.println("Max Bucket Value:" + getMaxBucketValue(curArray));
            System.out.println("Average of Non-Empty Buckets:" + getAverageInNonEmptyBuckets(curArray));
            System.out.println("Proportion Of Buckets With No Words:" + proportionOfBucketsWithNoWords(curArray));
        }

        //testing collisions for 2048 buckets and HoundOfTheBaskervilles.txt
        System.out.println("\nCollisions for 2048 buckets with HoundOfTheBaskervilles");
        for (int i=-1; i<4; i++) {
            int[] curArray = newTable.collisionCounter(2048, "HoundOfTheBaskervilles.txt", i);
            System.out.println("\nCollisions for hashcode" + i +":");
            System.out.println("Max Bucket Value:" + getMaxBucketValue(curArray));
            System.out.println("Average of Non-Empty Buckets:" + getAverageInNonEmptyBuckets(curArray));
            System.out.println("Proportion Of Buckets With No Words:" + proportionOfBucketsWithNoWords(curArray));
        }
        

    }
    
          
}