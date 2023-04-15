package ie.gmit.dip;

import java.util.*;
import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;

public class Parser {
	// declarations
	private static final String IGNORE_FILE = "./ignorewords.txt";
	private final Set<String> ignoreSet = new HashSet<>();
	private final Map<String, Integer> map = new HashMap<>();
	private final Queue<Word> wordQ = new PriorityQueue<>();

	public Queue<Word> getWordQueue() {
		return wordQ;
	}

	public int numUniqueWordsRead() {
		return map.size();
	}

	public ArrayList<String> list = new ArrayList<>();// these lists are populated by file/url and are passed into
														// imageCloud
	public ArrayList<String> ignoreList = new ArrayList<>();

	// parse ignore file.
	public void fileIgnoreParse() throws IOException {
		long start = System.currentTimeMillis();
		try (BufferedReader ignoreFile = new BufferedReader(new FileReader(IGNORE_FILE))) {
			String next = null;

			while ((next = ignoreFile.readLine()) != null) {
				if (next.isEmpty())
					continue;

				ignoreList.add(next.toLowerCase());
				ignoreSet.add(next.toLowerCase());
			}
		}
		System.out.printf("Parsing %s...\n", IGNORE_FILE);
		System.out.println("Time (ms):" + (System.currentTimeMillis() - start));
		System.out.println("Time Complexity = 0(n) where n = " + ignoreList.size());
	}

	public void outputToFile(String filename, int numWords) throws FileNotFoundException {
		try (PrintWriter pw = new PrintWriter(filename)) {
			int counter = 0;

			// Copies the Queue so the words in wordQ aren't polled prematurely
			// Adds all the elements in wordQ to copyQ
			Queue<Word> copyQ = new PriorityQueue<>(wordQ);

			while (counter < copyQ.size() && counter < numWords) {
				pw.println(copyQ.poll());
				counter++;
			}
		}
	}

	protected void processWords(String[] words) {
		for (String word : words) {
			word = stripPunctuation(word).trim();

			// Only adds words that aren't in ignorewords.txt.
			if (!ignoreSet.contains(word)) {
				if (!word.isEmpty()) {
					updateMap(word);
				}
			}
		}
	}

	/**
	 * Adds all words in the Map to a PriorityQueue. Words are then sorted whenever
	 * a new element is added based on frequency.
	 */
	protected void sortMap() {
		// Iterates over the keys in the Set, instantiates a new Word object, adds it to
		// the PriorityQueue.
		for (String key : map.keySet()) {
			wordQ.offer(new Word(key, map.get(key)));
		}
	}

	private void updateMap(String word) {
		// If the word is already in the Map, its frequency is retrieved and increaseds.
		if (map.containsKey(word)) {
			int freq = map.get(word);
			map.put(word, ++freq);
		} else {
			// First occurrence of the word - sets the frequency to 1
			map.put(word, 1);
		}
	}

	private static String stripPunctuation(String word) {
		return word.replaceAll("(?:(?<!\\S)\\p{Punct}+)|(?:\\p{Punct}+(?!\\S))", "");
	}

	// Parses in the input file
	public void parseFile(InputStream in) throws Exception {
		// inputFile takes in the text file from the menu class
		long start = System.currentTimeMillis();

		BufferedReader inputFile = new BufferedReader(new InputStreamReader(in));
		String next = null;

		while ((next = inputFile.readLine()) != null) {
			String[] words = next.split(" ");

			for (String word : words) {
				//O(n)
				if (!ignoreSet.contains(word)) {// checks ignorefile.txt for words and adds the new ones to ArrayList
					//O(1)
					list.add(word);
				}
			}
		}
		System.out.printf("Parsing %s...\n", Menu.getFile());
		System.out.println("Time (ms):" + (System.currentTimeMillis() - start));
		System.out.println("Time Complexity = 0(n) where n = " + list.size());
	}

	// Parses the URL
	public void parseUrl(String myUrl) throws IOException {
		long start = System.currentTimeMillis();

		URL useUrl = new URL(myUrl);
		BufferedReader inputUrl = new BufferedReader(new InputStreamReader(useUrl.openStream()));
		String next = null;

		while ((next = inputUrl.readLine()) != null) {
			String[] words = next.split(" ");

			for (String word : words) {
				// O(n)
				if (!ignoreList.contains(word)) {// checks ignorefile.txt for words, adds new ones to ArrayList
					// O(1)
					list.add(word);
				}
			}
		}

		System.out.printf("parsing %s...\n", Menu.getURL());
		System.out.println("Time (ms):" + (System.currentTimeMillis() - start));
		System.out.println("Time Complexity = 0(n) where n = " + list.size());
	}

}
