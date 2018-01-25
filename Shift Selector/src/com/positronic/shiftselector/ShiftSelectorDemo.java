package com.positronic.shiftselector;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.SystemColor;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ShiftSelectorDemo extends JFrame
{
	private static final long serialVersionUID = 3755828243732900220L;

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ShiftSelectorDemo frame = new ShiftSelectorDemo();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	private JPanel contentPane;
	private ShiftSelector shiftSelector;

	public ShiftSelectorDemo()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 400);
		contentPane = new JPanel();
		contentPane.setPreferredSize(new Dimension(20, 314));
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		Rectangle bounds = new Rectangle(new Dimension(750, 300));
		
		LayoutManager layoutManager = new FlowLayout();
		
		String[] leftItems = new String[]{"A","B","C"};
		DefaultListModel<String> leftListModel = new DefaultListModel<String>();
		for (int i = 0; i < leftItems.length; i++)
			leftListModel.addElement(leftItems[i]);
		
		String[] rightItems = new String[]{};
		DefaultListModel<String> rightListModel = new DefaultListModel<String>();
		for (int i = 0; i < rightItems.length; i++)
			rightListModel.addElement(rightItems[i]);
		
		shiftSelector = new ShiftSelector(bounds, layoutManager,
				leftListModel, "<==>",
				rightListModel);
		
		contentPane.add(shiftSelector, BorderLayout.CENTER);
	}
}
