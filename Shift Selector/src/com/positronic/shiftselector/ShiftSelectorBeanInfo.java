/*
 * Copyright (c) 2017-8. Positronic Software
 */

package com.positronic.shiftselector;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class ShiftSelectorBeanInfo extends SimpleBeanInfo
{
	private final static Class<ShiftSelector> beanClass = ShiftSelector.class;

	public java.awt.Image getIcon(int iconKind)
	{
		if (iconKind == BeanInfo.ICON_MONO_16x16
				|| iconKind == BeanInfo.ICON_COLOR_16x16)
		{
			java.awt.Image img = loadImage("ShiftSelectorIcon16.png");
			return img;
		}
		if (iconKind == BeanInfo.ICON_MONO_32x32
				|| iconKind == BeanInfo.ICON_COLOR_32x32)
		{
			java.awt.Image img = loadImage("ShiftSelectorIcon32.png");
			return img;
		}
		return null;
	}

	public PropertyDescriptor[] getPropertyDescriptors()
	{
		try
		{
			PropertyDescriptor bounds = new PropertyDescriptor("bounds",
					beanClass);
			PropertyDescriptor layoutManager = new PropertyDescriptor(
					"layoutManager", beanClass);
			PropertyDescriptor almLeft = new PropertyDescriptor("almLeft",
					beanClass);
			PropertyDescriptor buttonText = new PropertyDescriptor(
					"buttonText", beanClass);
			PropertyDescriptor almRight = new PropertyDescriptor("almRight",
					beanClass);

			bounds.setBound(true);
			layoutManager.setBound(true);
			almLeft.setBound(true);
			buttonText.setBound(true);
			almRight.setBound(true);

			PropertyDescriptor rv[] =
			{ bounds, layoutManager, almLeft, buttonText, almRight };
			return rv;
		}
		catch (IntrospectionException e)
		{
			throw new Error(e.toString());
		}
	}
}
