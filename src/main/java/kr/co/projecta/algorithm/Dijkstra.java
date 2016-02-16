package kr.co.projecta.algorithm;

import kr.co.projecta.matching.log.Plogger;

public class Dijkstra {	
	public static int MAX = Integer.MAX_VALUE/2;
	int min;
	int [][]matrix;
	int node;
	int length;
	int[] visit; // node의 탐색여부
	int[] distance; // 가중치
	int[] via; // node의 엣지정보
	
	int _start, _end;
			
	public Dijkstra(int [][]matrix) {
		this.matrix = matrix;
		this.length = matrix[0].length;
		
		visit = new int[length]; 
		distance = new int[length]; 
		via = new int[length];
		
		_start = _end = 0;
	}
	
	public int getDistance(int start, int end) {
		node = 0;
		
		// initialize
		for (int i=0; i<length; i++) {
			visit[i] = 0;
			distance[i] = MAX;			
		}
		
		distance[start] = 0;
		
		for (int i=0; i<length; i++) {
			min = MAX;
			for (int j=0; j<length; j++) {
				if (visit[j] == 0 && distance[j] < min) {
					node = j;
					min = distance[j];
				}
			}
			
			visit[node] = 1;
			if (min == MAX) {
				break;
			}

			for (int j=0; j<length; j++) {
				if (distance[j] > distance[node] + matrix[node][j]) {
					distance[j] = distance[node] + matrix[node][j];
					via[j] = node;
				}
			}
		}
		_start = start;
		_end = end;
		return distance[end];
	}
	
	public int [] getPathNode() {
		int path[] = new int[length];
		int path_cnt = 0;

		node = _end;
		while (true) {
			path[path_cnt++] = node;
			if (node == _start) {
				break;
			}
			node = via[node];
		}

		int i;
		int result_path[] = new int[path_cnt];
		for (i=0; i<path_cnt; i++) {
			result_path[i] = path[path_cnt-1-i];
		}
		return result_path;
	}
	
	public String [] getPathName(Callback callback) {
		int path[] = new int[length];
		int path_cnt = 0;

		node = _end;
		while (true) {
			path[path_cnt++] = node;
			if (node == _start) {
				break;
			}
			node = via[node];
		}

		int i;
		String result_path[] = new String[path_cnt];
		for (i=0; i<path_cnt; i++) {
			result_path[i] = callback.getNodeName(path[path_cnt-1-i]);
		}
		return result_path;
	}	
	
	
	public static void main(String[] arg) throws Exception {
		Plogger log = Plogger.getLogger(Dijkstra.class);
		
		int m = MAX;
		int[][] data = { 
				{ 0,  10, 30,  m,  m,  m }, // Node 0에서의 거리.
				{ 10,  0, 15, 40,  m,  m }, // Node 1에서의 거리.
				{ 30, 15,  0, 10, 20,  m }, // Node 2에서의 거리.
				{  m, 40, 10,  0, 20, 30 }, // Node 3에서의 거리.
				{  m,  m, 20, 20,  0, 10 }, // Node 4에서의 거리.
				{  m,  m,  m, 30, 10,  0 }  // Node 5에서의 거리.
		};
		Dijkstra d = new Dijkstra(data);
		int dist = d.getDistance(1, 5);
		
		String [] path = d.getPathName(new Callback() {
			public String getNodeName(int node) {
				switch (node) {
				case 0: return "A";
				case 1: return "B";
				case 2: return "C";
				case 3: return "D";
				case 4: return "E";
				case 5: return "F";
				}
				return null;
			}
		});
		StringBuffer pathRoot = new StringBuffer();
		for(String s : path) {
			pathRoot.append(s+" -> ");			
		}
		log.d(pathRoot.toString());
	}
}
