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

public class ShiftSelector extends JPanel implements Serializable, iObservable
{
	private static final long serialVersionUID = -4468144209547852942L;

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	private DefaultListModel<String> almLeft;
	private DefaultListModel<String> almRight;
	private Rectangle bounds;
	private String buttonText;
	private LayoutManager layoutManager;
	private int leftSelection = -1;
	protected ArrayList<iObserver> observers = new ArrayList<iObserver>();
	protected boolean oFlag;

	private int rightSelection = -1;
	private ShiftSelector thisObject;

	public ShiftSelector()
	{
		this(new Rectangle(750, 300), new FlowLayout(),
				new DefaultListModel<String>(), Messages
						.getString("ShiftSelector.0"), //$NON-NLS-1$
				new DefaultListModel<String>());
	}

	public ShiftSelector(LayoutManager layoutManager, String[] leftItems,
			String buttonText, String[] rightItems)
	{
		this(new Rectangle(750, 300), layoutManager, leftItems, buttonText,
				rightItems);
	}

	public ShiftSelector(Rectangle bounds, LayoutManager layoutManager,
			DefaultListModel<String> almLeft, String buttonText,
			DefaultListModel<String> almRight)
	{
		this.bounds = bounds;
		this.layoutManager = layoutManager;
		this.almLeft = almLeft;
		this.buttonText = buttonText;
		this.almRight = almRight;
		this.thisObject = this;

		super.setBounds(bounds);

		this.setLayout(layoutManager);
		this.setBorder(new BevelBorder(BevelBorder.LOWERED));

		JScrollPane leftScrollPane = new JScrollPane();
		leftScrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED));
		leftScrollPane.setPreferredSize(new Dimension(
				(int) (this.getWidth() * .4), this.getHeight()));

		this.add(leftScrollPane);
		JList<String> leftJList = new JList<String>(almLeft);
		leftJList.addListSelectionListener(new ListSelectionListener()
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
			leftScrollPane.setViewportView(leftJList);

		JButton button = new JButton(buttonText);
		button.setPreferredSize(new Dimension((int) (this.getWidth() * .1),
				(int) (this.getHeight() * .9)));
		JList<String> rightJList = new JList<String>(almRight);

		DefaultListModel<String> dlmLeft = (DefaultListModel<String>) leftJList
				.getModel();
		DefaultListModel<String> dlmRight = (DefaultListModel<String>) rightJList
				.getModel();

		button.addMouseListener(new MouseAdapter()
		{
			public void mouseReleased(MouseEvent arg0)
			{
				if (leftSelection != -1)
				{
					String o = dlmLeft.getElementAt(leftSelection);
					dlmLeft.removeElement(o);
					dlmRight.add(dlmRight.getSize(), o);
				}
				if (rightSelection != -1)
				{
					String o = dlmRight.getElementAt(rightSelection);
					dlmRight.removeElement(o);
					dlmLeft.add(dlmLeft.getSize(), o);
				}

				thisObject.setChanged();
				thisObject.notifyObservers(dlmRight);
			}
		});
		this.add(button);

		JScrollPane rightScrollPane = new JScrollPane();
		rightScrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED));
		rightScrollPane.setPreferredSize(new Dimension(
				(int) (this.getWidth() * .4), this.getHeight()));

		this.add(rightScrollPane);

		rightJList.addListSelectionListener(new ListSelectionListener()
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
			rightScrollPane.setViewportView(rightJList);
	}

	public ShiftSelector(Rectangle bounds, LayoutManager layoutManager,
			String[] leftItems, String buttonText, String[] rightItems)
	{
		this(bounds, layoutManager, new DefaultListModel<String>(), buttonText,
				new DefaultListModel<String>());

		for (int i = 0; i < leftItems.length; i++)
			this.almLeft.addElement(leftItems[i]);

		for (int i = 0; i < rightItems.length; i++)
			this.almRight.addElement(rightItems[i]);
	}

	public ShiftSelector(String[] leftItems, String buttonText,
			String[] rightItems)
	{
		this(new FlowLayout(), leftItems, buttonText, rightItems);
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

	// Clears the internal flag that indicates this observable has changed
	// state.
	public void clearChanged()
	{
		this.oFlag = false;
	}

	public int countObservers()
	{
		return this.observers.size();
	}

	public synchronized void deleteObserver(iObserver o)
	{
		observers.remove(o);
	}

	public synchronized void deleteObservers()
	{
		observers.clear();
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

	public String getButtonText()
	{
		return buttonText;
	}

	public LayoutManager getLayoutManager()
	{
		return layoutManager;
	}

	// Returns the boolean value true if this observable has changed state.
	public boolean hasChanged()
	{
		return this.oFlag;
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

	// Checks the internal flag to see if the observable has changed state and
	// notifies all observers.
	public void notifyObservers()
	{
		notifyObservers(null);
	}

	// Checks the internal flag to see if the observable has changed state and
	// notifies all observers. Passes the object specified in the parameter list
	// to the notify() method of the observer.
	public void notifyObservers(Object arg)
	{
		/*
		 * a temporary array buffer, used as a snapshot of the state of current
		 * Observers.
		 */
		Object[] arrLocal;

		synchronized (this)
		{
			/*
			 * We don't want the Observer doing callbacks into arbitrary code
			 * while holding its own Monitor. The code where we extract each
			 * Observable from the Vector and store the state of the Observer
			 * needs synchronization, but notifying observers does not (should
			 * not). The worst result of any potential race-condition here is
			 * that: 1) a newly-added Observer will miss a notification in
			 * progress 2) a recently unregistered Observer will be wrongly
			 * notified when it doesn't care
			 */
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

	public void setButtonText(String buttonText)
	{
		this.buttonText = buttonText;
	}

	// Sets the internal flag that indicates this observable has changed state.
	public synchronized void setChanged()
	{
		this.oFlag = true;
	}

	public void setLayoutManager(LayoutManager layoutManager)
	{
		this.layoutManager = layoutManager;
	}

	public String toString()
	{
		return "ShiftSelector [almLeft=" + almLeft + ", almRight=" + almRight //$NON-NLS-1$ //$NON-NLS-2$
				+ ", bounds=" + bounds + ", buttonText=" + buttonText //$NON-NLS-1$ //$NON-NLS-2$
				+ ", layoutManager=" + layoutManager + "]"; //$NON-NLS-1$ //$NON-NLS-2$
	}
}
