/*
 * Copyright (c) 2017-8. Positronic Software
 */

package com.positronic.shiftselector;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/*
 * Copyright (c) 2017-8. Positronic Software
 */

public class ShiftSelector extends JPanel implements Serializable, iObservable
{
	private static final long serialVersionUID = -4468144209547852942L;

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	private DefaultListModel<String> almLeft;
	private DefaultListModel<String> almRight;
	protected Rectangle bounds;
	private JButton button;
	protected String buttonText;
	protected LayoutManager layoutManager;
	private JList<String> leftJList;
	private JScrollPane leftScrollPane;
	private int leftSelection = -1;
	protected ArrayList<iObserver> observers = new ArrayList<iObserver>();
	protected boolean oFlag;
	private JList<String> rightJList;
	private JScrollPane rightScrollPane;
	private int rightSelection = -1;
	private ShiftSelector thisObject;

	public ShiftSelector()
	{
		this.setBorder(new BevelBorder(BevelBorder.LOWERED));
		this.leftScrollPane = new JScrollPane();
		this.leftScrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED));
		this.rightScrollPane = new JScrollPane();
		this.rightScrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED));
	}

	public ShiftSelector(Rectangle bounds, LayoutManager layoutManager,
			DefaultListModel<String> almLeft, String buttonText,
			DefaultListModel<String> almRight)
	{
		this();
		this.bounds = bounds;
		this.layoutManager = layoutManager;
		this.almLeft = almLeft;
		this.buttonText = buttonText;
		this.almRight = almRight;
		this.thisObject = this;

		super.setBounds(bounds);

		this.setLayout(layoutManager);

		this.getLeftScrollPane().setPreferredSize(
				new Dimension((int) (this.getWidth() * .4), this.getHeight()));

		this.add(doLeftJScrollPane(almLeft));

		this.setButton(new JButton(buttonText));
		this.getButton().setPreferredSize(
				new Dimension((int) (this.getWidth() * .1), (int) (this
						.getHeight() * .9)));

		JList<String> rightJList = new JList<String>(almRight);

		doButtonListener((DefaultListModel<String>) this.getLeftJList()
				.getModel(), (DefaultListModel<String>) rightJList.getModel());
		this.add(this.getButton());

		this.getRightScrollPane().setPreferredSize(
				new Dimension((int) (this.getWidth() * .4), this.getHeight()));

		this.add(doRightJScrollPane(almRight));
	}

	public ShiftSelector(Rectangle bounds, String[] leftItems,
			String buttonText, String[] rightItems)
	{
		this(bounds, new FlowLayout(), new DefaultListModel<String>(),
				buttonText, new DefaultListModel<String>());

		for (int i = 0; i < leftItems.length; i++)
			this.almLeft.addElement(leftItems[i]);

		for (int i = 0; i < rightItems.length; i++)
			this.almRight.addElement(rightItems[i]);
	}

	public synchronized void addObserver(iObserver o)
	{
		if (o == null)
			throw new NullPointerException();
		if (!observers.contains(o))
		{
			observers.add(o);
		}
	}

	public void clearChanged()
	{
		this.oFlag = false;
	}

	void doButtonListener(DefaultListModel<String> dlmLeft,
			DefaultListModel<String> dlmRight)
	{
		button.addMouseListener(new MouseAdapter()
		{
			public void mouseReleased(MouseEvent arg0)
			{
				if (leftSelection != -1)
				{
					doSelectItem(dlmLeft, dlmRight);
				}
				if (rightSelection != -1)
				{
					doDeselectItem(dlmLeft, dlmRight);
				}

				thisObject.setChanged();
				ShiftSelectorStatus status = new ShiftSelectorStatus(almLeft, almRight, leftJList,
						leftScrollPane, leftSelection, observers, oFlag,
						rightJList, rightScrollPane, rightSelection);
				thisObject.notifyObservers(status);
			}
		});
	}

	void doDeselectItem(DefaultListModel<String> dlmLeft,
			DefaultListModel<String> dlmRight)
	{
		String o = dlmRight.getElementAt(rightSelection);
		dlmRight.removeElement(o);
		dlmLeft.add(dlmLeft.getSize(), o);
	}

	JScrollPane doLeftJScrollPane(DefaultListModel<String> almLeft)
	{
		this.setLeftJList(new JList<String>(almLeft));
		this.getLeftJList().addListSelectionListener(
				new ListSelectionListener()
				{
					public void valueChanged(ListSelectionEvent arg0)
					{
						if (!arg0.getValueIsAdjusting())
						{
							leftSelection = leftJList.getSelectedIndex();
						}
					}
				});
		if (almLeft != null)
			leftScrollPane.setViewportView(this.getLeftJList());
		return leftScrollPane;
	}

	JScrollPane doRightJScrollPane(DefaultListModel<String> almRight)
	{
		this.setRightJList(new JList<String>(almRight));
		this.getRightJList().addListSelectionListener(
				new ListSelectionListener()
				{
					public void valueChanged(ListSelectionEvent arg0)
					{
						if (!arg0.getValueIsAdjusting())
						{
							rightSelection = rightJList.getSelectedIndex();
						}
					}
				});
		if (almRight != null)
			rightScrollPane.setViewportView(this.getRightJList());
		return rightScrollPane;
	}

	void doSelectItem(DefaultListModel<String> dlmLeft,
			DefaultListModel<String> dlmRight)
	{
		String o = dlmLeft.getElementAt(leftSelection);
		dlmLeft.removeElement(o);
		dlmRight.add(dlmRight.getSize(), o);
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ShiftSelector))
			return false;
		ShiftSelector other = (ShiftSelector) obj;
		if (almLeft == null)
		{
			if (other.almLeft != null)
				return false;
		}
		else if (!almLeft.equals(other.almLeft))
			return false;
		if (almRight == null)
		{
			if (other.almRight != null)
				return false;
		}
		else if (!almRight.equals(other.almRight))
			return false;
		if (bounds == null)
		{
			if (other.bounds != null)
				return false;
		}
		else if (!bounds.equals(other.bounds))
			return false;
		if (buttonText == null)
		{
			if (other.buttonText != null)
				return false;
		}
		else if (!buttonText.equals(other.buttonText))
			return false;
		if (layoutManager == null)
		{
			if (other.layoutManager != null)
				return false;
		}
		else if (!layoutManager.equals(other.layoutManager))
			return false;
		return true;
	}

	public DefaultListModel<String> getAlmLeft()
	{
		return almLeft;
	}

	public DefaultListModel<String> getAlmRight()
	{
		return almRight;
	}

	public Rectangle getBounds()
	{
		return bounds;
	}

	public JButton getButton()
	{
		return button;
	}

	public String getButtonText()
	{
		return buttonText;
	}

	public LayoutManager getLayoutManager()
	{
		return layoutManager;
	}

	public JList<String> getLeftJList()
	{
		return leftJList;
	}

	public JScrollPane getLeftScrollPane()
	{
		return leftScrollPane;
	}

	public JList<String> getRightJList()
	{
		return rightJList;
	}

	public JScrollPane getRightScrollPane()
	{
		return rightScrollPane;
	}

	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((almLeft == null) ? 0 : almLeft.hashCode());
		result = prime * result
				+ ((almRight == null) ? 0 : almRight.hashCode());
		result = prime * result + ((bounds == null) ? 0 : bounds.hashCode());
		result = prime * result
				+ ((buttonText == null) ? 0 : buttonText.hashCode());
		result = prime * result
				+ ((layoutManager == null) ? 0 : layoutManager.hashCode());
		return result;
	}

	public void notifyObservers()
	{
		notifyObservers(null);
	}

	public void notifyObservers(Object arg)
	{
		Object[] arrLocal;

		synchronized (this)
		{
			if (!this.oFlag)
				return;
			arrLocal = observers.toArray();
			clearChanged();
		}

		for (int i = arrLocal.length - 1; i >= 0; i--)
			((iObserver) arrLocal[i]).update(this, arg);
	}

	public void setAlmLeft(DefaultListModel<String> almLeft)
	{
		this.almLeft = almLeft;
	}

	public void setAlmRight(DefaultListModel<String> almRight)
	{
		this.almRight = almRight;
	}

	public void setBounds(Rectangle bounds)
	{
		this.bounds = bounds;
	}

	public void setButton(JButton button)
	{
		this.button = button;
	}

	public void setButtonText(String buttonText)
	{
		this.buttonText = buttonText;
	}

	public synchronized void setChanged()
	{
		this.oFlag = true;
	}

	public void setLayoutManager(LayoutManager layoutManager)
	{
		this.layoutManager = layoutManager;
	}

	public void setLeftJList(JList<String> leftJList)
	{
		this.leftJList = leftJList;
	}

	public void setLeftScrollPane(JScrollPane leftScrollPane)
	{
		this.leftScrollPane = leftScrollPane;
	}

	public void setRightJList(JList<String> rightJList)
	{
		this.rightJList = rightJList;
	}

	public void setRightScrollPane(JScrollPane rightScrollPane)
	{
		this.rightScrollPane = rightScrollPane;
	}

	public String toString()
	{
		return "ShiftSelector [almLeft=" + almLeft + ", almRight=" + almRight //$NON-NLS-1$ //$NON-NLS-2$
				+ ", bounds=" + bounds + ", buttonText=" + buttonText //$NON-NLS-1$ //$NON-NLS-2$
				+ ", layoutManager=" + layoutManager + "]"; //$NON-NLS-1$ //$NON-NLS-2$
	}
}