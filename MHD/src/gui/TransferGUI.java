package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import utilities.componenty.TextChooser;
import utilities.componenty.TextFieldNumber;
import utilities.pomocneProgramy.VytvareniOken;
import utilities.struct.Trie;

public class TransferGUI {
	private static final TransferGUI jedinacek = new TransferGUI();
	private final Panel VSE = new Panel();
	private Trie TRIE = new Trie();
	
	{
		TRIE.addWord("auto");
		TRIE.addWord("Automobil");
	}
	private final ArrayList<Choice> allChoices = new ArrayList<Choice>();
	private final HashMap<Choice,ArrayList<P_TCH_MINUTES>> b = new HashMap<Choice,ArrayList<P_TCH_MINUTES>>();
	JFrame frame = new JFrame();
	private TransferGUI(){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(stations(),BorderLayout.CENTER);
		frame.setVisible(true);
		
	}
	
	private Component stations(){
		final ScrollPane all = new ScrollPane();
		Panel add_stations = new Panel(new BorderLayout());
		Button addTransfer = new Button("+");
		add_stations.add(addTransfer,BorderLayout.NORTH);
		final Panel stations = new Panel(new GridLayout(0,1));
		stations.add(oneStation());
		addTransfer.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				stations.add(oneStation());
				all.validate();
			}
		});
		add_stations.add(stations,BorderLayout.CENTER);
		all.add(add_stations);
		return all;
	}
	
	private Component oneStation(){
		Button add = new Button("+");
		Choice c = new Choice();
		for(String s:TRIE.allWords())
			c.add(s);
		allChoices.add(c);
		final ArrayList<P_TCH_MINUTES> l = new ArrayList<P_TCH_MINUTES>();
		b.put(c,l);
		TextChooser tch = new TextChooser();
		TextFieldNumber minutes = new TextFieldNumber(0,600);
		l.add(new P_TCH_MINUTES(tch,minutes));
		final ScrollPane sp = new ScrollPane();
		final Panel p = (Panel) VytvareniOken.vlozDoTabulky(4, new int[]{1,2}, new Label("Pocatecni stanice"),new Label("Cílová stanice"),new Label("čas v minutách"),null,c,tch.getAsComponent(),minutes,add);
		add.addActionListener(new ActionListener(){
			int radek = 3;
			@Override
			public void actionPerformed(ActionEvent e) {
				TextChooser tch = new TextChooser();
				TextFieldNumber minutes = new TextFieldNumber(0,600);
				l.add(new P_TCH_MINUTES(tch,minutes));
				VytvareniOken.vlozDoTabulky(p,radek++, 4, new int[]{1,2}, null,tch.getAsComponent(),minutes,null);
				sp.validate();
			}
		});
		sp.setPreferredSize(new Dimension(100,100));
		sp.add(p);
		return sp;
	}
	
	public static void main(String[] args) {
		new TransferGUI();
	}
	
	private static class P_TCH_MINUTES{
		public final TextChooser TCH;
		public final TextFieldNumber MINUTES;
		
		public P_TCH_MINUTES(TextChooser tch,TextFieldNumber minutes) {
			TCH=tch;
			MINUTES=minutes;
		}
		
	}
}
