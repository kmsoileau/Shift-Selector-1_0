package com.positronic.shiftselector;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class ShiftSelectorStatus
{
	private DefaultListModel<String> almLeft;
	private DefaultListModel<String> almRight;
	private JList<String> leftJList;
	private JScrollPane leftScrollPane;
	private int leftSelection;
	protected ArrayList<iObserver> observers;
	protected boolean oFlag;
	private JList<String> rightJList;
	private JScrollPane rightScrollPane;
	private int rightSelection;

	public ShiftSelectorStatus(DefaultListModel<String> almLeft,
			DefaultListModel<String> almRight, JList<String> leftJList,
			JScrollPane leftScrollPane, int leftSelection,
			ArrayList<iObserver> observers, boolean oFlag,
			JList<String> rightJList, JScrollPane rightScrollPane,
			int rightSelection)
	{
		super();
		this.almLeft = almLeft;
		this.almRight = almRight;
		this.leftJList = leftJList;
		this.leftScrollPane = leftScrollPane;
		this.leftSelection = leftSelection;
		this.observers = observers;
		this.oFlag = oFlag;
		this.rightJList = rightJList;
		this.rightScrollPane = rightScrollPane;
		this.rightSelection = rightSelection;
	}

	public DefaultListModel<String> getAlmLeft()
	{
		return almLeft;
	}

	public DefaultListModel<String> getAlmRight()
	{
		return almRight;
	}

	public JList<String> getLeftJList()
	{
		return leftJList;
	}

	public JScrollPane getLeftScrollPane()
	{
		return leftScrollPane;
	}

	public int getLeftSelection()
	{
		return leftSelection;
	}

	public ArrayList<iObserver> getObservers()
	{
		return observers;
	}

	public JList<String> getRightJList()
	{
		return rightJList;
	}

	public JScrollPane getRightScrollPane()
	{
		return rightScrollPane;
	}

	public int getRightSelection()
	{
		return rightSelection;
	}

	public boolean isoFlag()
	{
		return oFlag;
	}

	public void setAlmLeft(DefaultListModel<String> almLeft)
	{
		this.almLeft = almLeft;
	}

	public void setAlmRight(DefaultListModel<String> almRight)
	{
		this.almRight = almRight;
	}

	public void setLeftJList(JList<String> leftJList)
	{
		this.leftJList = leftJList;
	}

	public void setLeftScrollPane(JScrollPane leftScrollPane)
	{
		this.leftScrollPane = leftScrollPane;
	}

	public void setLeftSelection(int leftSelection)
	{
		this.leftSelection = leftSelection;
	}

	public void setObservers(ArrayList<iObserver> observers)
	{
		this.observers = observers;
	}

	public void setoFlag(boolean oFlag)
	{
		this.oFlag = oFlag;
	}

	public void setRightJList(JList<String> rightJList)
	{
		this.rightJList = rightJList;
	}

	public void setRightScrollPane(JScrollPane rightScrollPane)
	{
		this.rightScrollPane = rightScrollPane;
	}

	public void setRightSelection(int rightSelection)
	{
		this.rightSelection = rightSelection;
	}
}
