package example;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Math;
import java.util.*;

public class CompEx {
	public static void main(String args[]) {
		TreeSet<Sticks> box1 = new TreeSet<>();

		Sticks obj1 = new Sticks(19);
		Sticks obj2 = new Sticks(9);
		Sticks obj3 = new Sticks(53);

		box1.add(obj1);
		box1.add(obj2);
		box1.add(obj3);

		for (Sticks i : box1) {
			System.out.println(i);
		}

	}
}

class Sticks implements Comparable<Sticks> {
	int size;

	public Sticks(int size) {
		this.size = size;
	}

	@Override
	public int compareTo(Sticks sticks_object) {
		if (this.size == sticks_object.size) {
			return 0;
		} else if (this.size < sticks_object.size) {
			return -1;
		} else {
			return 1;
		}
	}
}

// interface Comparable<T> {
// public int compareTo(T o);
// }
