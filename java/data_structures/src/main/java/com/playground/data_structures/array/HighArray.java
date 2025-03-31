package com.playground.data_structures.array;

public class HighArray {
	private final long[] a; // ref to array a

	private int nElems; // number of data items

	public HighArray(final int max) {
		a = new long[max]; // create the array
		nElems = 0;
	}

	public int size() {
		return nElems;
	}

	public boolean find(final long searchKey) {
		int j;
		for (j = 0; j < nElems; j++) {
			if (a[j] == searchKey) {
				return true;
			}
		}
		return false;
	}

	public void insert(final long value) // put element into array
	{
		a[nElems] = value; // insert it
		nElems++; // increment size
	}

	public boolean delete(final long value) {
		int j, k;
		for (j = 0; j < nElems; j++) {
			if (a[j] == value) {
				break;
			}
		}
		if (j == nElems) {
			return false;
		}

		for (k = j; k < nElems - 1; k++) {
			a[k] = a[k + 1];
		}
		nElems--;

		return true;
	}

	public long getMax() {
		int j;
		long max = -1;
		for (j = 0; j < nElems; j++) {
			if (a[j] > max) {
				max = a[j];
			}
		}
		return max;
	}

	public long removeMax() {
		int j;
		long max = -1;
		for (j = 0; j < nElems; j++) {
			if (a[j] > max) {
				max = a[j];
			}
		}
		delete(max);
		return max;
	}

	public void noDuplicates() {
		int j;

		if (nElems <= 1) {
			return;
		}


		for (j = 1; j < nElems - 1; j++) {
			if (a[j] == a[nElems - 1]) {
				break;
			}
			while (a[j - 1] == a[j]) {
				for (int k = j; k < nElems - 1; k++) {
					a[k] = a[k + 1];
				}
			}

		}
		nElems = a[j - 1] != a[j] ? j + 1 : j;

	}

	public void display()
	{
		for (int j = 0; j < nElems; j++) {
			System.out.print(a[j] + " ");
		}

		System.out.println("");
	}
}
