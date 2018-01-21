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

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ShiftSelector extends JPanel implements java.io.Serializable
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
	private int rightSelection = -1;
	
	private ShiftSelector thisObject;

	/*
	 * Optionally, toNotify can be set to any JComponent (usually one containing
	 * the ShiftSelector. Whenever the shift button is pressed, an event is
	 * triggered on the JComponent.
	 */
	private JComponent toNotify = null;

	public ShiftSelector()
	{
		this(new Rectangle(750, 300), new FlowLayout(),
				new DefaultListModel<String>(), Messages
						.getString("ShiftSelector.0"), //$NON-NLS-1$
				new DefaultListModel<String>());
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
		
		this.thisObject=this;

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
				
				if (toNotify != null)
				{
					toNotify.dispatchEvent(new ShiftSelectorEvent(thisObject,0));
				}
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

	public JComponent getToNotify()
	{
		return toNotify;
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

	public void setLayoutManager(LayoutManager layoutManager)
	{
		this.layoutManager = layoutManager;
	}

	public void setToNotify(JComponent toNotify)
	{
		this.toNotify = toNotify;
	}

	public String toString()
	{
		return "ShiftSelector [almLeft=" + almLeft + ", almRight=" + almRight //$NON-NLS-1$ //$NON-NLS-2$
				+ ", bounds=" + bounds + ", buttonText=" + buttonText //$NON-NLS-1$ //$NON-NLS-2$
				+ ", layoutManager=" + layoutManager + "]"; //$NON-NLS-1$ //$NON-NLS-2$
	}
}
