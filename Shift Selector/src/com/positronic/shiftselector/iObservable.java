/*
 * Copyright (c) 2017-8. Positronic Software
 */

package com.positronic.shiftselector;

public interface iObservable
{
	void addObserver(iObserver o);

	void clearChanged();

	void notifyObservers();

	void notifyObservers(Object arg);

	void setChanged();
}