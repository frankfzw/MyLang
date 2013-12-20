package UI;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.*;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

public class AnalyzerArea extends JPanel{

	private CodeArea resultArea;
	private CodeArea status;
	private JScrollPane resultScroll;
	private JScrollPane statusScroll;
	private JPanel leftSide;
	private JLabel labelR;
	private JLabel labelS;
	public static final int SEM = 0;
	public static final int LEX = 1;
	int type;
	
	public AnalyzerArea(int type) {
		init(type);
	}
	
	public void init(int type) {
		
		labelS = new JLabel("Message:");
		labelR = new JLabel("Result:");
		JPanel empty = new JPanel();
		this.type = type;
		
		//init scorll and text area
		leftSide = new JPanel();
		leftSide.setBackground(Color.WHITE);
		resultArea = new CodeArea();
		status = new CodeArea();
		if (type == LEX) {
			leftSide.add(resultArea);
		}
		
		
		resultScroll = new JScrollPane(leftSide);
		resultScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		resultScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		statusScroll = new JScrollPane(status);
		statusScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		statusScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		//set layout
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		GridBagConstraints s = new GridBagConstraints();
		s.fill = GridBagConstraints.HORIZONTAL;
		
		s.weightx = 1;
		s.weighty = 0;
		s.gridx = 0;
		s.gridy = 0;
		s.anchor = GridBagConstraints.WEST;
		this.add(labelR, s);
		
		s.ipadx = 5;
		s.weightx = 0;
		s.weighty = 0;
		s.gridx = 1;
		s.gridy = 0;
		this.add(empty, s);
		
		s.weightx = 1;
		s.weighty = 0;
		s.gridx = 2;
		s.gridy = 0;
		this.add(labelS, s);
		
		s.fill = GridBagConstraints.BOTH;
		s.gridwidth = 1;
		s.weightx = 0;
		s.weighty = 1;
		s.gridx = 0;
		s.gridy = 1;
		this.add(resultScroll, s);
		
		
		s.gridwidth = 1;
		s.weightx = 0;
		s.weighty = 1;
		s.gridx = 2;
		s.gridy = 1;
		this.add(statusScroll, s);
		
		
	}
	
	public void setResult(String result) {
		resultArea.setText(result);
	}
	
	public void setStatus(String str) {
		status.setText(str);
	}
	
	public void setTree(SemanticTree tree) {
		if (type == SEM) {
			leftSide.add(tree);
		}
	}
	
}
