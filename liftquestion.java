package com.sears.lift;

import java.util.*;
/*
 thiru,5,9
paps,8,4
ravi,8,9
jai,9,5
rohan,9,8
 */
class Lift {
	public int start;
	public int end;

	Lift(int start, int end) {
		this.start = start;
		this.end = end;
	}
}

public class liftquestion {

	static int previousFloor = -1111111111;
	private static Scanner sc;

	// mapping startFloor ---> [names]
	public static Map<Integer, ArrayList<String>> generateFloorOnBoardMapping(Map<String, Lift> data) {
		Map<Integer, ArrayList<String>> mapping = new LinkedHashMap<>();
		for (String s : data.keySet()) {
			Lift l = data.get(s);
			if (mapping.containsKey(l.start)) {
				ArrayList<String> arr = mapping.get(l.start);
				arr.add(s); 
				mapping.put(l.start, arr);
			} else {
				ArrayList<String> arr = new ArrayList<>();
				arr.add(s);
				mapping.put(l.start, arr);
			}
		}
		return mapping;

	}

	public static int getNearestFloorFromCurrent(int currFloor, Map<Integer, ArrayList<String>> mapping) {
		int mindiff = Integer.MAX_VALUE;
		int key = currFloor;

		for (Integer k : mapping.keySet()) {
			int diff = Math.abs(k - currFloor);
			if (diff < mindiff) {
				key = k;
				mindiff = diff;
			}
		}

		return key;
	}

	public static String getNearestDestination(int currFLoor, Map<String, Integer> onBoard) {
		int mindiff = Integer.MAX_VALUE;
		String key = "";

		for (String k : onBoard.keySet()) {
			int diff = Math.abs(onBoard.get(k) - currFLoor);
			if (diff < mindiff) {
				key = k;
				mindiff = diff;
			} else if (diff == mindiff
					&& (((onBoard.get(k) - currFLoor) > 0 ? 1 : -1) == ((currFLoor - previousFloor) > 0 ? 1 : -1))) {
				// if currFloor < previousFloor(-ve) mean lift moving downward

				if (onBoard.get(key) == onBoard.get(k)) { // remove if same destination
					onBoard.remove(k);
					continue;
				}
				key = k;
				mindiff = diff;
			}
		}

		return key;
	}

	public static ArrayList<Integer> liftSystem(Map<String, Lift> data) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		Map<String, Integer> onBoard = new HashMap<>();
		Map<Integer, ArrayList<String>> mapping = generateFloorOnBoardMapping(data);
	//	int currFloor = (mapping.entrySet().iterator().next()).getKey();
		if(mapping.isEmpty())
		{
			return res;
		}
	    int currFloor =getNearestFloorFromCurrent(0, mapping);
		res.add(currFloor);
		while (true) {
			if (mapping.containsKey(currFloor)) {
				for (String name : mapping.get(currFloor)) {
					onBoard.put(name, data.get(name).end);
				}
			}

			mapping.remove(currFloor);
			// previousFloor = currFloor;

			if (!onBoard.isEmpty()) {
				String nearestToCurr = getNearestDestination(currFloor, onBoard);
				previousFloor = currFloor;
				currFloor = onBoard.get(nearestToCurr);
				res.add(currFloor);
				onBoard.remove(nearestToCurr);
			} else if (!mapping.isEmpty()) {
				previousFloor = currFloor;
				currFloor = getNearestFloorFromCurrent(currFloor, mapping);
				res.add(currFloor);
			} else {
				break;
			}
		}

		return res;
	}

	public static Map<String, Lift> getTestCase() {

		sc = new Scanner(System.in);
		Map<String, Lift> liftInput = new LinkedHashMap<>();
		int n = 5;
		while (n != 0) {
			String s = sc.next();
			String s1[] = s.split(",");
			int a = Integer.parseInt(s1[1]);
			int b = Integer.parseInt(s1[2]);

			liftInput.put(s1[0], new Lift(a, b));
			n--;
		}
		return liftInput;

	}

	public static void main(String[] args) {
		System.out.println("Enter input");
		ArrayList<Integer> res = liftSystem(getTestCase());
		System.out.println("output is");
		for(int i=0;i<res.size();i++)
		{
			if(i!=res.size()-1)
			{
		System.out.print(res.get(i)+",");
			}
			else
			{
				System.out.print(res.get(i));
            }
	    }
	}
}
