package com.positronic.shiftselector;

import java.awt.AWTEvent;

import com.positronic.shiftselector.ShiftSelector;

public class ShiftSelectorEvent extends AWTEvent
{
	private static final long serialVersionUID = 2316957944185370435L;

	public ShiftSelectorEvent(ShiftSelector source, int id)
	{
		super(source, id);
	}
}
