import java.util.*;

/**
 * Implements a tree that stores words. Each TreeNode contains a
 * single character as its item and a number as its count (the
 * number of times that each word occurs.
 *
 * Can increment the count of a tree node when a word is added and
 * decrement the count of a tree node when a word is removed (also
 * deletes unnecessary nodes when a word is removed).
 *
 * Can return the number of times that a word occurs, the number of
 * nodes in the tree, and whether or not the tree contains a given
 * word.
 *
 * Finally, can traverse the tree and create a WordCount instance
 * for each word and its count.
 */
public class WordCountMap {
    
    private TreeNode root;
    
    /**
     * Constructs an empty WordCountMap.
     */
    public WordCountMap() {
        //intialize the root
        this.root = new TreeNode();
        this.root.count = 0;
    }
    
    /**
     * Adds 1 to the existing count for word, or adds word to the WordCountMap
     * with a count of 1 if it was not already present.
     * Implementation must be recursive, not iterative.
    */
    public void incrementCount(String word) {
        incrementCountHelper(word, this.root);
    } 
    //a helper class to allow us to traverse the tree
    public void incrementCountHelper(String word, TreeNode curNode) {
        //if word has no letters, do nothing
        if (word.length() <= 0) {
            return;
        }

        //pull the first character of the word
        char curChar = word.charAt(0);

        //base case: if we are at the last letter in the word or it is just a letter, we increment count
        if (word.length() == 1) {
            //if curNode has no children
            if (curNode.children.size() == 0) {
                //create a new child with curChar as the item
                TreeNode newNode = new TreeNode(curChar);
                curNode.children.add(newNode);
                newNode.count++;
            } else { //otherwise look through children for next letter
                boolean contains = false;
                //for each child
                for (TreeNode child : curNode.children) {
                    //if node we are looking for is a child of curNode
                    if (child.item == curChar) {
                        contains = true;
                        //increment count
                        child.count++;
                    }
                }

                //if node we are looking for is not a child of curNode
                if (!contains) {
                    //create new node and increment count
                    TreeNode newNode = new TreeNode(curChar);
                    curNode.children.add(newNode);
                    newNode.count++;
                }
            }
        } else { //if word has more than one letter, call recursively
            //if curNode has no children
            if (curNode.children.size() == 0) {
                //create new node with next letter
                TreeNode newNode = new TreeNode(curChar);
                curNode.children.add(newNode);
                //call recursively
                incrementCountHelper(word.substring(1), newNode);
            } else { //otherwise look through children for next letter
                boolean contains = false;
                //for each child
                for (TreeNode child : curNode.children) {
                    //if node are looking for is a child of curNode
                    if (child.item == curChar) {
                        contains = true;
                        //increment count
                        incrementCountHelper(word.substring(1), child);
                    }
                }

                //if node we are looking for is not a child of curNode
                if (!contains) {
                    //create new node and increment count
                    TreeNode newNode = new TreeNode(curChar);
                    curNode.children.add(newNode);
                    incrementCountHelper(word.substring(1), newNode);
                }
            }
        }
    }

    /**
     * Returns the count of word, or -1 if word does not have a count
     * greater than zero in the map.
     * Implementation must be recursive, not iterative.
    */
    public int getCount(String word) {
        //if word is not in tree, count is 0
        if (!contains(word)) {
            return 0;
        }

        return getCountHelper(word, this.root);
    }

    public int getCountHelper(String word, TreeNode curNode) {
        //base case
        //if we are at the last letter of the word, we are at the place where we could find the count
        if (word.length() == 0) {
            return curNode.count;
        } else { //otherwise call recursively on children of curNode
            //iterate through children
            for (TreeNode child : curNode.children) {
                //if we have found the correct child node, return recursive call
                if (child.item == word.charAt(0)) {
                    return getCountHelper(word.substring(1), child);
                }
            }
        }
        return 0;
    }
    
    /**
     * Returns true if word is stored in this WordCountMap with
     * a count greater than 0, and false otherwise.
     * Implementation must be recursive, not iterative.
    */
    public boolean contains(String word) {
        return containsHelper(word, this.root);
    }

    public boolean containsHelper(String word, TreeNode curNode) {
        //base case: if we are at the end of the word, does it have a count 
        if (word.length() == 0) {
            if (curNode.count > 0) {
                return true;
            } else {
                return false;
            }
        } else if (curNode.children.size() == 0) {
            //if the path to the word does not exist then the word cannot exist
            return false;
        } else {
            //recursively progress down each child 
            for (TreeNode child : curNode.children) {
                if (child.item == word.charAt(0)) {
                    return containsHelper(word.substring(1), child);
                }
            }
        }
        return false;
    }
    
    /**
     * Remove 1 to the existing count for word. If word is not present, does
     * nothing. If word is present and this decreases its count to 0, removes
     * any nodes in the tree that are no longer necessary to represent the
     * remaining words.
    */
    public void decrementCount(String word) {
        //if tree does not contain word, there is no count to decrement
        if (!contains(word)) {
            return;
        } else {
            decrementCountHelper(word, this.root);
        }
    }

    public void decrementCountHelper(String word, TreeNode curNode) {
        //if we are at the right node/letter, then decrement count
        if (word.length() == 0) {
            //decrement count; if count is 0, do nothing
            if (curNode.count >= 1) {
                curNode.count--;
            }
        } else {
            TreeNode nodeToDelete = null;
            //recursively get to the last letter of the word
            for (TreeNode child : curNode.children) {
                if (child.item == word.charAt(0)) {
                    decrementCountHelper(word.substring(1), child);

                    //if node has count 0 and no children, we want to delete it
                    if (child.count == 0 && child.children.size() == 0) {
                        nodeToDelete = child;
                    }
                }
            }

            //if we have set nodeToDelete to a TreeNode, we want to delete it
            if (nodeToDelete != null) {
                curNode.children.remove(nodeToDelete);
            }
        }
    }

    
    /**
     * Returns a list of WordCount objects, one per word stored in this
     * WordCountMap, sorted in decreasing order by count.
    */


    public List<WordCount> getWordCountsByCount() {
        //create a list to store all of the word counts in
        List<WordCount> listWordCount = new ArrayList<WordCount>();
        //initialize a string that we will add and delete letters to 
        String curLetters = "";
        return getWordCountsByCountHelper(this.root, listWordCount, curLetters);
    }

    public List<WordCount> getWordCountsByCountHelper(TreeNode curNode, List<WordCount> listWordCount, String curLetters) {
        if (curNode.children.size() == 0) {
            //we have reached a leaf
            //WordCount for this node already exists so we do nothing
            return listWordCount;
        } else {
            //for each child of curNode
            for (TreeNode child : curNode.children) {
                //add char stored in child to curLetters
                char letterToAdd = child.item;
                curLetters += letterToAdd;
                //if child has a count, create WordCount instance
                if (child.count > 0) {
                    listWordCount.add(new WordCount(curLetters, child.count));
                    //sort list
                    Collections.sort(listWordCount, new SortByCount());
                }
                //otherwise call recursively on child
                getWordCountsByCountHelper(child, listWordCount, curLetters);
                //remove last letter from curLetters when moving back up the tree
                curLetters = curLetters.substring(0, curLetters.length() - 1);
            }
            return listWordCount;
        }
    }

    /**
     * Private class to aid in sorting unsortedList in
     * getWordCountsbyCountHelper method. Takes in a list of
     * WordCounts and returns a list of WordCounts sorted by count
     * in descending order.
     */
    private class SortByCount implements Comparator<WordCount> {
        public int compare(WordCount a, WordCount b) {
            return b.count - a.count;
        }
    }

    /**
     * Returns a count of the total number of nodes in the tree.
     * A tree with only a root is a tree with one node; it is an acceptable
     * implementation to have a tree that represents no words have either
     * 1 node (the root) or 0 nodes.
     * Implementation must be recursive, not iterative.
    */
    public int getNodeCount() {
        //test to make sure we have a tree
        if (this.root == null) {
            return 0;
        } else {
            //if we have a tree, start at the root
            return getNodeCountHelper(this.root);
        }
    }
    
    public int getNodeCountHelper(TreeNode curNode) {
        //base case: we are at a leaf
        if (curNode.children.size()==0) {
            //if it has no children, return 1 to count curNode
            return 1;
        } else {
            //initialize integer to contain sum of recursive calls on children
            int eachChildCount = 0;
            //for each child
            for (TreeNode child : curNode.children) {
                //add node count of subtree to eachChildCount
                eachChildCount += getNodeCountHelper(child);
            }
            //return sum of calls on children + 1 for curNode
            return eachChildCount + 1;
        }
    }

    public void clear() {
        //create a new root for the tree and thus everything below it will cease to exist
        root = new TreeNode();
    }

    /**
     * Private class used to store information at each nod of the
     * tree. Stores a single character, an integer, and a list of
     * all child nodes.
     */
    private class TreeNode {
        char item;
        int count;
        List<TreeNode> children = new ArrayList<TreeNode>();

        public TreeNode() {
            this.item = ' ';
            this.count = 0;
        }
        public TreeNode(char letter) {
            this.item = letter;
        }
    }

    public static void main(String[] args) {
        WordCountMap wordCountMap = new WordCountMap();
        
        for (int i = 0; i<50; i++) {
            wordCountMap.incrementCount("the");
        }
        for (int i = 0; i<20; i++) {
            wordCountMap.incrementCount("that");
        }
        for (int i = 0; i<120; i++) {
            wordCountMap.incrementCount("cat");
        }
        for (int i = 0; i<3; i++) {
            wordCountMap.incrementCount("chomp");
        }
        for (int i = 0; i<7; i++) {
            wordCountMap.incrementCount("then");
        }
        System.out.println("The count for 'the' should be 50. getCount() returns : " + wordCountMap.getCount("the"));

        System.out.println("The count for 'that' should be 20. getCount() returns : " + wordCountMap.getCount("that"));

        System.out.println("The count for 'cat' should be 120. getCount() returns : " + wordCountMap.getCount("cat"));

        System.out.println("The count for 'chomp' should be 3. getCount() returns : " + wordCountMap.getCount("chomp"));

        System.out.println("The count for 'then' should be 7. getCount() returns : " + wordCountMap.getCount("then"));

        wordCountMap.clear();

        System.out.println("TESTS FOR incrementCount(): ");
        //test if testIncrementCount() properly adds to empty list
        wordCountMap.incrementCount("test");
        System.out.println("The count for 'test' should be 1. getCount() returns : " + wordCountMap.getCount("test"));

        //test if clear() properly clears
        wordCountMap.clear();
        System.out.println("The count for 'test' should be 0. getCount() returns : " + wordCountMap.getCount("test"));

        //test if testIncrementCount() properly adds multiple counts to same word
        wordCountMap.incrementCount("test");
        wordCountMap.incrementCount("test");
        wordCountMap.incrementCount("test");
        System.out.println("The count for 'test' should be 3. getCount() returns : " + wordCountMap.getCount("test"));

        //tests if "test" count remains correct after adding the following words
        wordCountMap.incrementCount("tes");
        wordCountMap.incrementCount("tests");
        wordCountMap.incrementCount("t");
        wordCountMap.incrementCount("ps");
        wordCountMap.incrementCount("tps");
        wordCountMap.incrementCount("tps");
        System.out.println("The count for 'test' should still be 3. getCount() returns : " + wordCountMap.getCount("test"));
        System.out.println("The count for 'tests' should be 1. getCount() returns : " + wordCountMap.getCount("tests"));
        System.out.println("The count for 'tes' should be 1. getCount() returns : " + wordCountMap.getCount("tes"));
        System.out.println("The count for 't' should be 1. getCount() returns : " + wordCountMap.getCount("t"));
        System.out.println("The count for 'ps' should be 1. getCount() returns : " + wordCountMap.getCount("ps"));
        System.out.println("The count for 'tps' should be 2. getCount() returns : " + wordCountMap.getCount("tps"));
        wordCountMap.clear();

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("TESTS FOR decrementCount(): ");
        //test if decrementCount() properly throws an exception for decrementing from empty list.
        try{
            wordCountMap.decrementCount("test");
            System.out.println("decrementCount() failed to throw an exception here.");
        }
        catch(NullPointerException e){
            System.out.println("decrementCount() properly threw an exception here.");
        }

        //test if decrementCount() properly decrements and removes nodes
        wordCountMap.incrementCount("test");
        wordCountMap.incrementCount("test");
        wordCountMap.incrementCount("test");
        wordCountMap.incrementCount("test");
        wordCountMap.incrementCount("test");

        System.out.println("Before decrementing, 'test' should have a count of 5: " + wordCountMap.getCount("test"));
        System.out.println("Before decrementing, getNodeCount() should return 5: " + wordCountMap.getNodeCount());
        wordCountMap.decrementCount("test");
        wordCountMap.decrementCount("test");
        wordCountMap.decrementCount("test");
        wordCountMap.decrementCount("test");
        wordCountMap.decrementCount("test");
        System.out.println("After decrementing, 'test' should have a count of 0: " + wordCountMap.getCount("test"));
        System.out.println("After decrementing, getNodeCount() should return 1: " + wordCountMap.getNodeCount());

        //tests if proper exception is thrown if word is not found
        try{
            wordCountMap.decrementCount("tes");
            System.out.println("decrementCount() failed to thrown an exception.");
        }
        catch(NullPointerException e){
            System.out.println("decrementCount() successfully threw the exception.");
        }
        wordCountMap.clear();

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("TESTS FOR contains(): ");
        //test if testContains() properly returns false for an empty WordCountMap
        System.out.println("Should return false here. contains() returns : " + wordCountMap.contains("test"));

        //test if testContains() properly returns true for a word with a count of 1
        wordCountMap.incrementCount("test");
        System.out.println("Should return true here. contains() returns : " + wordCountMap.contains("test"));
        System.out.println("Should return false here. contains() returns : " + wordCountMap.contains("t"));
        System.out.println("Should return false here. contains() returns : " + wordCountMap.contains("st"));
        System.out.println("Should return false here. contains() returns : " + wordCountMap.contains("est"));

        //tests if "test" remains in map after adding the following
        wordCountMap.incrementCount("tes");
        wordCountMap.incrementCount("tests");
        wordCountMap.incrementCount("t");
        System.out.println("Should return true here. contains() returns : " + wordCountMap.contains("test"));
        wordCountMap.clear();

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("TESTS FOR getCount(): ");
        //test if getCount() properly returns 0 for empty list
        System.out.println("The count for 'test' should be 0. getCount() returns : " + wordCountMap.getCount("test"));

        //test if getCount() properly return 3 after adding the following
        wordCountMap.incrementCount("test");
        wordCountMap.incrementCount("test");
        wordCountMap.incrementCount("test");
        System.out.println("The count for 'test' should be 3. getCount() returns : " + wordCountMap.getCount("test"));

        //tests if "test" count remains correct after adding the following words
        wordCountMap.incrementCount("tes");
        wordCountMap.incrementCount("tests");
        wordCountMap.incrementCount("t");
        System.out.println("The count for 'test' should still be 3. getCount() returns : " + wordCountMap.getCount("test"));
        System.out.println("The count for 'tests' should be 1. getCount() returns : " + wordCountMap.getCount("tests"));
        System.out.println("The count for 'tes' should be 1. getCount() returns : " + wordCountMap.getCount("tes"));
        System.out.println("The count for 't' should be 1. getCount() returns : " + wordCountMap.getCount("t"));
        wordCountMap.clear();

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("TESTS FOR getNodeCount(): ");
        //checks if getNodeCount() properly returns 1 for empty list.
        System.out.println("getNodeCount() should return 1 here. It returns: " + wordCountMap.getNodeCount());

        //checks if getNodeCount() properly returns 4 after adding one node
        wordCountMap.incrementCount("test");
        System.out.println("getNodeCount() should return 5 here. It returns: " + wordCountMap.getNodeCount());

        //checks if getNodeCount() properly returns () after adding several nodes
        wordCountMap.clear();
        System.out.println("getNodeCount() should return 1 here. It returns: " + wordCountMap.getNodeCount());
        wordCountMap.incrementCount("test");
        wordCountMap.incrementCount("tests");
        System.out.println("getNodeCount() should return 6 here. It returns: " + wordCountMap.getNodeCount());
        wordCountMap.incrementCount("testss");
        wordCountMap.incrementCount("testsss");
        wordCountMap.incrementCount("dog");
        wordCountMap.decrementCount("test");
        System.out.println("getNodeCount() should return 11 here. It returns: " + wordCountMap.getNodeCount());
        wordCountMap.clear();

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("TESTS FOR getWordCountsByCount(): ");
        wordCountMap.incrementCount("dog");

        wordCountMap.incrementCount("test");
        wordCountMap.incrementCount("test");

        wordCountMap.incrementCount("tests");
        wordCountMap.incrementCount("tests");
        wordCountMap.incrementCount("tests");

        wordCountMap.incrementCount("testss");
        wordCountMap.incrementCount("testss");
        wordCountMap.incrementCount("testss");
        wordCountMap.incrementCount("testss");

        wordCountMap.incrementCount("testsss");
        wordCountMap.incrementCount("testsss");
        wordCountMap.incrementCount("testsss");
        wordCountMap.incrementCount("testsss");
        wordCountMap.incrementCount("testsss");

        System.out.println("order should be testsss, testss, tests, test, and dog: ");

        List<WordCount> list = wordCountMap.getWordCountsByCount();
        for(int i = 0; i < list.size(); i++){
            System.out.print(list.get(i).getWord() + ":");
            System.out.println(list.get(i).getCount());
        }

    }

}