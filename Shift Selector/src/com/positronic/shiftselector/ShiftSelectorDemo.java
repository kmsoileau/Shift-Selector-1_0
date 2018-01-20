/*
 * Copyright (c) 2017-8. Positronic Software
 */

package com.positronic.shiftselector;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;

public class ShiftSelectorDemo
{
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					new ShiftSelectorDemo();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	private JFrame frame;

	public ShiftSelectorDemo()
	{
		initialize();
	}

	private void initialize()
	{
		frame = new JFrame("Shift Selector Demo");
		frame.setIconImage(Toolkit
				.getDefaultToolkit()
				.getImage(
						ShiftSelectorDemo.class
								.getResource("/com/positronic/shiftselector/ShiftSelectorIcon.png")));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(new Rectangle(new Point(80, 80),
				new Dimension(470, 350)));

		Rectangle bounds = new Rectangle(new Point(1200, 120), new Dimension(
				450, 300));
		LayoutManager layoutManager = new FlowLayout();

		/*
		 * String[] leftItems = new String[] { "A", "B", "C", "D", "E", "F",
		 * "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
		 * "Quaternionic Hyperspace Radiation" };
		 */
		String[] leftItems = randomStrings(20, 20);

		DefaultListModel<String> leftListModel = new DefaultListModel<String>();
		for (int i = 0; i < leftItems.length; i++)
			leftListModel.addElement(leftItems[i]);

		/*
		 * String[] rightItems = new String[] { "R", "S", "T", "U", "V", "W",
		 * "X", "Y", "Z" };
		 */

		String[] rightItems = randomStrings(20, 20);
		DefaultListModel<String> rightListModel = new DefaultListModel<String>();
		for (int i = 0; i < rightItems.length; i++)
			rightListModel.addElement(rightItems[i]);

		ShiftSelector panel = new ShiftSelector(bounds, layoutManager,
				leftListModel, " ", rightListModel);
		frame.getContentPane().add(panel);

		frame.setVisible(true);
	}

	private String randomLetter()
	{
		String[] alphabet = new String[]
		{ "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
				"O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
				" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
				" ", " " };
		return alphabet[(int) (Math.random() * alphabet.length)];
	}

	private String randomString(int length)
	{
		if (length < 1)
			return "";

		return randomLetter() + randomString(length - 1);
	}

	private String[] randomStrings(int n, int length)
	{
		if (n < 0)
			return null;
		String[] ret = new String[n];
		for (int i = 0; i < n; i++)
			ret[i] = randomString(length);
		return ret;
	}
}
