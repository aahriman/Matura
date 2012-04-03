package utilities.componenty;

import java.awt.Component;
import java.awt.List;
import java.awt.Point;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

import javax.swing.JWindow;

import utilities.componenty.pomocne.OknoProTesty;
import utilities.struct.Trie;

public class TextChooser {
	
	private static final int MIN_CHARACTERS_TO_SHOW_ADVICES = 2;
	private final TextField  TEXT= new TextField();
	private final JWindow ADVICE = new JWindow();
	private final List ADVICES = new List();
	
	private static Trie TRIE = new Trie();
	static{
		TRIE.addWord("auto");
		TRIE.addWord("kolo");
		TRIE.addWord("Autosalon");
		TRIE.addWord("koloopravna");
	}
	
	
	private boolean selected = false;
	
	
	public TextChooser() {
		TEXT.addTextListener(new TextListener(){
			int numberOfChangeIfSelected = 0;
			@Override
			public void textValueChanged(TextEvent e) {
				if(TEXT.getText().length() == 0)
					ADVICE.setVisible(false);
				
				if (selected) // ošetření při výběru z nabýdky ADVICES
					numberOfChangeIfSelected = ++numberOfChangeIfSelected % 2;

				if (numberOfChangeIfSelected == 0 && TEXT.getText().length() >= MIN_CHARACTERS_TO_SHOW_ADVICES) {
					selected = false;
					String[] allAdvice = TRIE.allWithPrefix((TEXT.getText()));
					ADVICES.removeAll();
					for (int i = 0; i < allAdvice.length; i++) {
						ADVICES.add(allAdvice[i]);
					}

					if (allAdvice.length > 0) {
						Point point = TEXT.getLocationOnScreen();
						point.y += TEXT.getHeight();
						ADVICE.setLocation(point);
						ADVICE.setSize(TEXT.getWidth(),
										ADVICE.getSize().height);
						ADVICE.setVisible(true);
					}
				}
			}
		});
		ADVICES.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				selected = true;
				String selectedText = ADVICES.getSelectedItem(); 
				TEXT.setText(selectedText);
				TEXT.setSelectionStart(selectedText.length());
				TEXT.setSelectionEnd  (selectedText.length());
				ADVICE.setVisible(false);
			}
		});
		ADVICE.add(ADVICES);
		ADVICE.setSize(100, 100);
	}
	
	public Component getAsComponent(){
		return TEXT;
	}

	public static void addWord(String word){
		TRIE.addWord(word);
	}
	
	public static void main(String[] args) {
		TextChooser t = new TextChooser();
		OknoProTesty o = new OknoProTesty();
		o.add(t.getAsComponent());
		o.zviditelni(200,100);
	
	}
}

