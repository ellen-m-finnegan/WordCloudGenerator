package ie.gmit.dip;

public class Word {
	private final String word;
    private final int frequency;

    public Word(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    public String getWord() {
        return word;
    }

    public int getFrequency() {
        return frequency;
    }
    
    public String toString() {
        return frequency + "  " + word;
    }
    
    public int compareTo(Word word) {
        return -Integer.compare(frequency, word.frequency);
    }
}
