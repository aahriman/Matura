package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import utilities.componenty.TextChooser;
import utilities.pomocneProgramy.VytvareniOken;

public class Vyber {

	private final Panel VYBER = new Panel();
	private final Choice TOWN=new Choice();
	private final ArrayList<TextChooser> FROM=new ArrayList<TextChooser>();
	private final ArrayList<TextChooser> TO=new ArrayList<TextChooser>();
	
	JFrame frame = new JFrame();
	
	public Vyber() {
	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		TOWN.add("Pardubice");
		if(TOWN.getItemCount() <= 1)
			TOWN.setEnabled(false);
		
		FROM.add(new TextChooser());
		TO.add(new TextChooser());
		frame.add(jizda(),BorderLayout.NORTH);
		frame.setVisible(true);
	}
	
	public Component getAsComponent(){
		return VYBER;
	}
	
	
	private Component jizda(){
		final Button addFROM = new Button("+");
		final Button addTO = new Button("+");
		final Panel fromPanel = new Panel(new GridBagLayout());
		final Panel toPanel = new Panel(new GridBagLayout());
		final ScrollPane fromSP = new ScrollPane(); 			fromSP.add(fromPanel);
		final ScrollPane toSP = new ScrollPane(); 			toSP.add(toPanel);
		final GridBagConstraints gbcFROM = new GridBagConstraints();
		final GridBagConstraints gbcTO = new GridBagConstraints();
		final Button cout = new Button("počítej");
		final JPanel all =(JPanel) VytvareniOken.vlozDoTabulkyNew(1, new int[]{1}, 
				VytvareniOken.vlozeniDoPomocnehoPaneluSPopiskem("Město:", TOWN),
				fromSP,
				toSP,
				cout);
		
		
		
		VytvareniOken.vytvorDalsiRadek(fromPanel, gbcFROM, new int[]{1}, 
				VytvareniOken.vlozeniDoPomocnehoPaneluSPopiskemPopisekNad("Zastavky odkud:", FROM.get(0).getAsComponent())
						,addFROM);
		VytvareniOken.vytvorDalsiRadek(toPanel, gbcTO, new int[]{1}, 
				VytvareniOken.vlozeniDoPomocnehoPaneluSPopiskemPopisekNad("Zastavky kam:", TO.get(0).getAsComponent())
						,addTO);
		
		addFROM.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FROM.add(new TextChooser());
				VytvareniOken.vytvorDalsiRadek(fromPanel, gbcFROM, new int[]{0},FROM.get(FROM.size()-1).getAsComponent());
				all.validate();
			}
		});
		
		addTO.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TO.add(new TextChooser());
				VytvareniOken.vytvorDalsiRadek(toPanel, gbcTO, new int[]{0},TO.get(TO.size()-1).getAsComponent());
				all.validate();
			}
		});
		return 	all; 
	}
	public static void main(String[] args) {
		new Vyber();
	}
	
	
}
