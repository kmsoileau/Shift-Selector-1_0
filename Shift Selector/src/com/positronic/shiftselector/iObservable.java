package com.positronic.shiftselector;

public interface iObservable
{
	void addObserver(iObserver o);

	void clearChanged();

	int countObservers();

	void deleteObserver(iObserver o);

	void deleteObservers();

	boolean hasChanged();

	void notifyObservers();

	void notifyObservers(Object arg);

	void setChanged();
}