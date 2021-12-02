public class WordCount {

    protected String word;
    protected int count;

    public WordCount(String newWord, int newCount) {
        this.word = newWord;
        this.count = newCount;
    }

    /**
    * Gets the word stored by this WordCount
    */
    public String getWord() {
        return this.word;
    }
    
    /** 
    * Gets the count stored by this WordCount
    */
    public int getCount() {
        return this.count;
    }
}