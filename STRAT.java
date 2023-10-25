package aoap1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class STRAT {
	
	public static List<Integer> strat1(int n, int m, int[][] houses) {
		int i=0, j=0;
		List<Integer> schedule = new ArrayList<>();
		for (i=0; i<n; i++) {
			while (j < m) {
				if (houses[j][0] <= i+1 && houses[j][1] >= i+1) {
					schedule.add(j+1);
					j++;
					break;
				}
				else if (houses[j][0] > i+1) {
					break;
				}
				else j++;
			}
		}
		return schedule;	
	}
	
	public static List<Integer> strat2(int n, int m, int[][] houses) {
		int i=0, j=0;
		List<Integer> schedule = new ArrayList<>();
		int[][] new_houses = new int[m][3];
		for (int k=0; k<m; k++) {
			new_houses[k][0] = houses[k][0];
			new_houses[k][1] = houses[k][1];
			new_houses[k][2] = k + 1;
		}
		// Add houses to priority queue according to largest start time 
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[0] - a[0]);
		for (i=0; i<n; i++) {
			while (j < m && new_houses[j][0] == i+1) {
				pq.add(new_houses[j++]);
			}
			while (pq.peek() != null) {
				int[] temp = pq.poll();
				if (temp[0] <= i+1 && temp[1] >= i+1) {
					schedule.add(temp[2]);
					break;
				}
				else continue;
			}
		}
		return schedule;	
	}
	
	public static List<Integer> strat3(int n, int m, int[][] houses) {
		int i=0, j=0;
		List<Integer> schedule = new ArrayList<>();
		int[][] new_houses = new int[m][4];
		for (int k=0; k<m; k++) {
			new_houses[k][0] = houses[k][0];
			new_houses[k][1] = houses[k][1];
			new_houses[k][2] = k + 1;
			new_houses[k][3] = houses[k][1] - houses[k][0];
		}
		// Add houses to priority queue according to smallest duration time 
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[3] - b[3]);
		for (i=0; i<n; i++) {
			while (j < m && new_houses[j][0] == i+1) {
				pq.add(new_houses[j++]);
			}
			while (pq.peek() != null) {
				int[] temp = pq.poll();
				if (temp[0] <= i+1 && temp[1] >= i+1) {
					schedule.add(temp[2]);
					break;
				}
				else continue;
			}
		}	
		return schedule;			
	}
	
	public static List<Integer> strat4(int n, int m, int[][] houses) {
		int i=0, j=0;
		List<Integer> schedule = new ArrayList<>();
		int[][] new_houses = new int[m][3];
		for (int k=0; k<m; k++) {
			new_houses[k][0] = houses[k][0];
			new_houses[k][1] = houses[k][1];
			new_houses[k][2] = k + 1;
		}
		// Add houses to priority queue according to earliest finish time 
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
		for (i=0; i<n; i++) {
			while (j < m && new_houses[j][0] == i+1) {
				pq.add(new_houses[j++]);
			}
			while (pq.peek() != null) {
				int[] temp = pq.poll();
				if (temp[0] <= i+1 && temp[1] >= i+1) {
					schedule.add(temp[2]);
					break;
				}
				else continue;
			}
		}
		return schedule;	
	}
	
	public static List<Integer> strat5(int n, int m, int[][] houses) {
		int j=0;
		List<Integer> schedule = new ArrayList<>();
		int[][] new_houses = new int[m][3];
		int[] start_day = new int[m];
		for (int k=0; k<m; k++) {
			new_houses[k][0] = houses[k][0];
			new_houses[k][1] = houses[k][1];
			new_houses[k][2] = k + 1;
			start_day[k] = houses[k][0];
		}
		// Add houses to priority queue according to earliest finish time 
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
		int curr_day = start_day[0];
		for (int day : start_day) {
			if (day > n) break;
			curr_day = Math.max(day, curr_day);
			while (j < m && new_houses[j][0] <= curr_day) {
				pq.add(new_houses[j++]);
			}
			while (pq.peek() != null) {
				int[] temp = pq.poll();
				if (temp[1] >= curr_day) {
					curr_day = Math.max(temp[0], curr_day + 1);
					schedule.add(temp[2]);
					break;
				}
				else continue;
			}			
		}
		return schedule;	
	}	
	
	public static int[][] Houses(int m) {
		  // randomly generate houses
		  int[][] houses = new int[m][2];
		  for (int i = 0; i < m; i++) {
		    houses[i][0] = (int) (Math.random() * m / 2); // set the multiplier to control the range
		    houses[i][1] = houses[i][0] + (int) (Math.random() * m / 2);
		    if (houses[i][0] == 0) houses[i][0]++;
		  }
		  // sort houses
		  // Arrays.sort(houses, (a, b) -> Integer.compare(a[0],b[0])); //increasing order
		  Arrays.sort(
		          houses,
		          (a, b) -> {
		            if (a[0] != b[0]) {
		              return a[0] - b[0];
		            } else if (a[0] == b[0]) {
		              return a[1] - b[1];
		            }
		            return 0;
		          }); // increasing order
		  return houses;
		}
	
	public static void main(String[] args) {
		int m = 1000;
		List<Long> runningTime4 = new ArrayList<>();
		List<Long> runningTime5 = new ArrayList<>();
		for (int i = 100 * m; i < 1000 * m; i = i + 100 * m) {
			int[][] houses = Houses(m);
			
			long stime4 = System.nanoTime();
			strat4(i, m, houses);
			long etime4 = System.nanoTime();
			runningTime4.add(etime4 - stime4);
			
			long stime5 = System.nanoTime();
			strat5(i, m, houses);
			long etime5 = System.nanoTime();
			runningTime5.add(etime5 - stime5);
		}
		System.out.println(runningTime4);
		System.out.println(runningTime5);
	}
}
		
//	  int n = 10000;
//	  int m = 10000;
//	  int[][] houses = Houses(m);

//	  for (int i = 0; i < houses.length; i++) {
//	    for (int j = 0; j < houses[i].length; j++) {
//	      System.out.print(houses[i][j] + " ");
//	    }
//	    System.out.println();
//	  }
//
//	  List<Integer> ans1 = strat1(n, m, houses);
//	  System.out.println("STRAT1 " + ans1.toArray().length);
//	  List<Integer> ans2 = strat2(n, m, houses);
//	  System.out.println("STRAT2 " + ans2.toArray().length);
//	  List<Integer> ans3 = strat3(n, m, houses);
//	  System.out.println("STRAT3 " + ans3.toArray().length);
//	  List<Integer> ans4 = strat4(n, m, houses);
//	  System.out.println("STRAT4 " + ans4.toArray().length);
//	  List<Integer> ans5 = strat5(n, m, houses);
//	  System.out.println("STRAT5 " + ans5.toArray().length);
//	}
//}
	
//	public static void main(String[] args) {
////		STRAT1 test1
////		int n11 = 10, m11 = 4;
////		int[][] houses11 = {{1,1}, {1,2}, {2,5}, {4,5}};
//		int n11 = 10, m11 = 4;
//		int[][] houses11 = {{1,1}, {1,1}, {3,4}, {3,4}};
//		List<Integer> ans11 = strat1(n11, m11, houses11);
//		System.out.println("STRAT1 test1:" + ans11);
//		
////		STRAT1 test2
//		int n12 = 10, m12 = 5;
//		int[][] houses12 = {{1,2}, {1,2}, {1,5}, {1,5}, {3,3}};
//		List<Integer> ans12 = strat1(n12, m12, houses12);
//		System.out.println("STRAT1 test2:" + ans12);
//
////		STRAT2 test1
//		int n21 = 10, m21 = 4;
//		int[][] houses21 = {{1,1}, {1,2}, {2,5}, {4,5}};
//		List<Integer> ans21 = strat2(n21, m21, houses21);
//		System.out.println("STRAT2 test1:" + ans21);
//		
////		STRAT2 test2
//		int n22 = 10, m22 = 5;
//		int[][] houses22 = {{1,2}, {1,2}, {1,5}, {1,5}, {3,3}};
//		List<Integer> ans22 = strat2(n22, m22, houses22);
//		System.out.println("STRAT2 test2:" + ans22);
//			
////		STRAT3 test1
//		int n31 = 10, m31 = 4;
//		int[][] houses31 = {{1,1}, {1,2}, {2,5}, {4,5}};
//		List<Integer> ans31 = strat3(n31, m31, houses31);
//		System.out.println("STRAT3 test1:" + ans31);	
//		
////		STRAT3 test2
//		int n32 = 10, m32 = 5;
//		int[][] houses32 = {{1,2}, {1,2}, {1,5}, {1,5}, {3,3}};
//		List<Integer> ans32 = strat3(n32, m32, houses32);	
//		System.out.println("STRAT3 test2:" + ans32);		
//
////		STRAT4 test1
//		int n41 = 10, m41 = 4;
//		int[][] houses41 = {{1,1}, {1,2}, {2,5}, {4,5}};
//		List<Integer> ans41 = strat4(n41, m41, houses41);
//		System.out.println("STRAT4 test1:" + ans41);
//		
////		STRAT4 test2
//		int n42 = 10, m42 = 5;
////		int[][] houses42 = {{1,2}, {1,2}, {1,5}, {1,5}, {3,3}};
//		int[][] houses42 = {{1,5}, {2,2}, {2,3}, {3,5}, {4,4}};
//		List<Integer> ans42 = strat4(n42, m42, houses42);	
//		System.out.println("STRAT4 test2:" + ans42);	
//	}
//}


















