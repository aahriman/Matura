package utilities.struct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

public class Trie{
	TreeSet<String> allWords = new TreeSet<String>();
	
	static class Level{
		
		public static final int MAX_INDEX = 10 + ('z'-'a')*2+4;
		
		boolean[] isWorld = new boolean[MAX_INDEX];
		Level[] nextLevel = new Level[MAX_INDEX];
		
		public static int getIndex(char c){
			
			switch(c){
				case ',':
					return MAX_INDEX - 3;
				case ' ':
					return MAX_INDEX - 2;
				case '.':
					return MAX_INDEX - 1;
				default:
					if(c <= '9')
						return c - '1'; 
					if(c < 'Z')
						return 10 + c- 'A';
					else
						return 10 + ('Z'-'A')+c - 'a';
						
			}
				
		}
		
		public static char getChar(int index){
			switch(index){
				case MAX_INDEX - 3:
					return ',';
				case MAX_INDEX - 2:
					return ' ';
				case MAX_INDEX - 1:
					return '.';
				default:
					if(index < 10){
						return  (char)(index+'0');
					}else if(index < 10 + 'z'-'a') {
						return (char)(index - 10 + 'A');
					}else
						return (char)(index - 10 - ('Z'-'A') + 'a');
						
			}
		}
	}
	
	private Level firstLevel = new Level();
	
	public boolean includedSeekingWord(String seekingWord){
		Level actualLevel = firstLevel;
		for(int i = 0; i < seekingWord.length();i++){
			int actualLevelIndex = Level.getIndex(seekingWord.charAt(i));
			
			if(i == seekingWord.length() - 1)
				return actualLevel.isWorld[actualLevelIndex];
			else if(actualLevel.nextLevel[actualLevelIndex] == null)
				return false;
			
			actualLevel = actualLevel.nextLevel[actualLevelIndex];
		}
		
		return false;
	}
	
	public boolean removeWord(String word){
		allWords.remove(word);
		Level actualLevel = firstLevel;
		for(int i = 0; i < word.length();i++){
			int actualLevelIndex = Level.getIndex(word.charAt(i));
			
			if(i == word.length() - 1){
				actualLevel.isWorld[actualLevelIndex] = false;
				return true;
			}else if(actualLevel.nextLevel[actualLevelIndex] == null)
				return false;
			
			actualLevel = actualLevel.nextLevel[actualLevelIndex];
		}
		
		return false;
	}
	
	public String[] allWithPrefix(String prefix){
		ArrayList<String> s = new ArrayList<String>();
		Level actualLevel = firstLevel;
		for(int i = 0; i < prefix.length() ;i++){
			if(actualLevel == null)
				return new String[0];
			
			int actualLevelIndex = Level.getIndex(prefix.charAt(i));
			if(i == prefix.length() - 1 && actualLevel.isWorld[actualLevelIndex])
				s.add(prefix);
				
			actualLevel = actualLevel.nextLevel[actualLevelIndex];
		}

		s.addAll(dfs(actualLevel,prefix));
		
		return s.toArray(new String[0]);
	}
	public void addWord(String word){
		allWords.add(word);
		Level actualLevel = firstLevel;
		for(int i = 0; i < word.length();i++){
			int actualLevelIndex = Level.getIndex(word.charAt(i));
			
			if(i == word.length() - 1){
				actualLevel.isWorld[actualLevelIndex] = true;
			}else{
				if(actualLevel.nextLevel[actualLevelIndex] == null){
					actualLevel.nextLevel[actualLevelIndex] = new Level();
				}
				actualLevel = actualLevel.nextLevel[actualLevelIndex];
			}
		}
	}
	
	public String[] allWords(){
		return allWords.toArray(new String[0]);
	}
	@Override
	/**
	 * this method return string with all words in trie,
	 * single words are separate by two characters = ', '
	 */
	public String toString() {
		String s = "";
		for(String l:allWords){
			s += l+", ";
		}
		return s;
	}
	
	private ArrayList<String> dfs(Level level,String prefix){
		ArrayList<String> strings = new ArrayList<String>();
		
		if(level == null)
			return strings;
		
		for(int i = 0; i < Level.MAX_INDEX;i++){
			String actualPrefix = prefix + Level.getChar(i);
			if(level.isWorld[i]){
				strings.add(actualPrefix);
			}
			if(level.nextLevel[i] != null){
				strings.addAll(dfs(level.nextLevel[i],actualPrefix));
			}
		}
		return strings;
	}
	
	/**
	 * only testing metod
	 * @param args
	 */
	public static void main(String[] args) {
		Trie trie = new Trie();
		trie.addWord("A a");
		trie.addWord("ahoj"); // pozdeji mazu
		trie.addWord("aho");
		trie.addWord("blbA");
		trie.addWord("blab");
		System.out.println(trie.includedSeekingWord("ahoj"));
		trie.removeWord("ahoj");
		trie.removeWord("blba");
		System.out.println(trie.includedSeekingWord("ahoj"));
		System.out.println(trie.includedSeekingWord("aho"));
		System.out.println(trie.includedSeekingWord("blbe"));
		System.out.println(trie.includedSeekingWord("blabol"));
		System.out.println(Arrays.toString(trie.allWithPrefix("a a")));
		System.out.println(Arrays.toString(trie.allWithPrefix("")));
		System.out.println(trie);
		System.out.println(trie.allWords);
	}
}
