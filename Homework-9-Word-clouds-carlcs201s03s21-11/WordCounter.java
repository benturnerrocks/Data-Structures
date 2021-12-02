import java.util.*;
import java.io.*;

public class WordCounter {
    
    //initialize the wordcountmap tree that we will store the words in
    private WordCountMap map = new WordCountMap();
    //initialize a list of word count objects
    private List<WordCount> treeList;

    //we want to load the words from the file into the wordcountmap
    private void load(String textFileName) {
        //load in from the file that we are reading from and our stop words file
        File treeFile = new File(textFileName);
        File stopWordsFile = new File("StopWords.txt");
        Scanner scannerTree = null;
        Scanner scannerStopWords = null;
        try {
            scannerTree = new Scanner(treeFile);
            scannerStopWords = new Scanner(stopWordsFile);
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }
        //create a set to hold all the stop words (use a set since it has a fast contains method)
        HashSet<String> stopWordSet = new HashSet<>();

        //pull out each of the stop words
        while (scannerStopWords.hasNextLine()) {
            String newStopWord = scannerStopWords.nextLine();
            stopWordSet.add(newStopWord);
        }
        //pull out each of the words from the text source
        while (scannerTree.hasNext()) {
            //pulls out next word unformatted based on spaces
            String newWord = scannerTree.next();
            //formats the word to not have punctuation
            newWord = formatWord(newWord);
            //add each word to the tree if it is NOT a stop word
            if (!stopWordSet.contains(newWord)) {
                map.incrementCount(newWord);
            }
        }
        scannerTree.close();
        scannerStopWords.close();
    }

    private void print() { 
        //limit the number of words we print so we can see the important ones
        List<WordCount> newList = getWordCountList();
        if (newList.size()>100) {
            newList = newList.subList(0,100);
        }
        //call getWordCountsByCount and then iterate through and print each
        for (WordCount wordCount : newList) {
                System.out.println(wordCount.getWord() + ":" + wordCount.getCount());
        }
    }

    private List<WordCount> getWordCountList() {
        //method used to get a list of wordcounts
        return map.getWordCountsByCount();
    }

    public static String formatWord(String word) {
        //need to format the words correctly
        //if we have a word that is one character 
        if (word.length() <= 1) {
            //test if it is a letter or digit
            if(Character.isLetterOrDigit(word.charAt(0))){
                return word.toLowerCase();
            } else {
                //if it is anything else, return nothing
                return "";
            }
        }
        //we want to test if the front or back of the word has punctuation
        if (!Character.isLetterOrDigit(word.charAt(0)) || !Character.isLetterOrDigit(word.charAt(word.length() - 1))) {
            //filter to avoid index out of bounds
            if (word.length() <= 1) {
                return "";
            } else {
                if (!Character.isLetterOrDigit(word.charAt(0))) {
                    //test if the first letter is punctuation and if so we delete the punctuation until we reach a letter
                    while (!Character.isLetterOrDigit(word.charAt(0))) {
                        if (word.length() <= 1) {
                            return "";
                        }
                        word = word.substring(1,word.length());
                    }
                }
                if (!Character.isLetterOrDigit(word.charAt(word.length()-1))) {
                    //test if the last letter is punctuation and if so we delete the punctuation until we get a letter
                    while (!Character.isLetterOrDigit(word.charAt(word.length()-1))) {
                        if (word.length() <= 1) {
                            return "";
                        }
                        word = word.substring(0, word.length()-1);
                    }
                }
            }
        }
        //if the word is actually a word, no punctuation then return in lower in lower case
        //this also returns the words that got trimmed of their punctuation
        return word.toLowerCase(); 
        
    }

    public static void main(String[] args) {
        // usage: java WordCounter textFileName
        // or: java WordCounter textFileName numberOfWordsToInclude outFileName
        WordCounter newWordCounter = new WordCounter();
        //if one argument is given (the text file) we want to load it and then print off the top 100 (this number is set in the print method) words by count
        if (args.length == 1) {
            newWordCounter.load(args[0]);
            newWordCounter.print();
        } else if (args.length == 3) {
            //we want to create a word cloud written into an html
            //first we load the text file
            newWordCounter.load(args[0]);
            //creat a WordCloudMaker object that we will use to create a word cloud html
            WordCloudMaker newWordCloud = new WordCloudMaker();
            //create the list of word count objects
            List<WordCount> newList = newWordCounter.getWordCountList();
            //identify the number of words user wants to display in word cloud
            int numWords = Integer.parseInt(args[1]);
            //if list is longer than this, we truncate it
            //otherwise we create a word cloud with what we have
            if (newList.size() > numWords) {
                newList = newList.subList(0,numWords-1);
            }
            //create a new html file
            File htmlFile = new File("C:\\" + args[2]);
            try {
                //try to create a new BufferedWriter and FileWriter to write the html file
                BufferedWriter newBuff = new BufferedWriter(new FileWriter(htmlFile));
                //actually write to the html file using the list of WordCounts and the string created by getWordCloudHTML
                newBuff.write(newWordCloud.getWordCloudHTML("Word Cloud", newList));
                newBuff.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            
        } else {
            System.out.println("Wrong number of command-line arguments. Please input 1 or 3.");
        }
    }

}