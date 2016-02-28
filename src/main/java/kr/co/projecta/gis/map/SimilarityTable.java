package kr.co.projecta.gis.map;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import kr.co.projecta.algorithm.Dijkstra;
import kr.co.projecta.matching.log.Plogger;

abstract public class SimilarityTable {
	transient static Plogger log = Plogger.getLogger(SimilarityTable.class);
	public static int NA = Dijkstra.MAX;
	
	// 지역
	static class Node {
		int nominal;
		String name;
		
		public Node(int nominal, String name) {
			this.nominal = nominal;
			this.name = name.trim();
		}
		@Override
		public String toString() {
			return name+":"+nominal;
		}
	};
	
	Node [] nodes;
	int [][] nodeMatrix;
	Dijkstra dijkstra;
	int maxDistanceValue = -1;
	int startNominal = 0;
	
	public SimilarityTable(int startNominal, String []datas) 
			throws IllegalAccessException 
	{
		this(datas);
		this.startNominal = startNominal;
	}
	
	public SimilarityTable(String []datas) 
			throws IllegalAccessException 
	{
		nodes = makeNode(datas);
		nodeMatrix = makeNodeMatrix(nodes.length);
		dijkstra = new Dijkstra(nodeMatrix);
		
		initilize();
		autoCalcurateMatrix();
		validateMatrix();
	}
	
	public SimilarityTable(String data, String delim) 
			throws IllegalAccessException 
	{
		nodes = makeNode(data, delim);
		nodeMatrix = makeNodeMatrix(nodes.length);
		dijkstra = new Dijkstra(nodeMatrix);
		
		initilize();
		autoCalcurateMatrix();
		validateMatrix();
	}
	
	private static Node [] makeNode(String []datas) {
		int nominal = 0;
		int length = datas.length;
		
		Node []nodes = new Node[length];
		
		// create node (for description)
		for (String data : datas) {
			nodes[nominal] = new Node(nominal, data);
			nominal++;
		}
		return nodes;
	}
	
	private static Node [] makeNode(String data, String delim) {
		int nominal = 0;
		StringTokenizer toker = new StringTokenizer(data, delim);
		int length = toker.countTokens();
		
		Node []nodes = new Node[length];
		
		// create node (for description)
		while (toker.hasMoreElements()) {
			nodes[nominal] = new Node(nominal, toker.nextToken());
			nominal++;
		}
		return nodes;
	}
	
	private static int [][] makeNodeMatrix(int length) {
		// create matrix (for distance)
		int [][] matrix = new int[length][length];
		for (int a=0; a<length; a++) {
			for (int b=0; b<length; b++) {
				if (a == b) {
					matrix[a][b] = 0;	
				} else {
					matrix[a][b] = NA;
				}
			}
		}
		return matrix;
	}
	
	public String getNodeName(int nominal) {
		return nodes[nominal-startNominal].name;
	}
	
	public void showNodes() {
		if (!log.isDebug()) return;
		
		for (int i=0; i<nodes.length; i++) {
			log.d("["+i+"] "+nodes[i]);
		}
	}
	
	public void showMatrix() {
		if (!log.isDebug()) return;
		
		for (int a=0; a<nodes.length; a++) {
			StringBuffer sb = new StringBuffer();
			for (int b=0; b<nodes.length; b++) {
				if (nodeMatrix[a][b] == NA) {
					sb.append("- ");
				} else {
					sb.append(nodeMatrix[a][b]+" ");
				}
			}
			log.d(sb.toString());
		}		
	}
	
	public void exportCSV(String filename) throws IOException {
		BufferedWriter bw = null;
		StringBuffer sb;
		try {
			bw = new BufferedWriter(new FileWriter(filename));
			sb = new StringBuffer("#,");
			for (int i=0; i<nodes.length; i++) {
				if (i != 0)
					sb.append(",");
				sb.append(nodes[i].name);
			}
			bw.write(sb.toString());
			bw.newLine();
			
			for (int a=0; a<nodes.length; a++) {
				sb = new StringBuffer(nodes[a].name).append(",");
				for (int b=0; b<nodes.length; b++) {
					if (b != 0)
						sb.append(",");
					if (nodeMatrix[a][b] == NA) {
						sb.append("-");
					} else {
						sb.append(nodeMatrix[a][b]);
					}
				}
				bw.write(sb.toString());
				bw.newLine();
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (bw != null)
				try { bw.close(); } catch (IOException e) {}
		}
	}
	
	private int getNominalIndex(String name) {
		for (Node n : nodes) {
			if (name.equals(n.name)) {
				return n.nominal;
			}
		}
		throw new RuntimeException("Unkown "+name);
	}
	
	public void inputDistance(String from, String to, int distance) {		
		int a = getNominalIndex(from.trim());
		int b = getNominalIndex(to.trim());
		
		if (nodeMatrix[a][b] != NA &&
			nodeMatrix[b][a] != NA) {
			throw new RuntimeException(
				String.format(
					"Already input distance %s <-(%d)-> %s", 
					from, distance, to));
		}
		nodeMatrix[a][b] = distance;
//		nodeMatrix[b][a] = distance;
	}
	
	public void validateMatrix() 
			throws IllegalAccessException 
	{
		for (int a=0; a<nodes.length; a++) {
			for (int b=0; b<nodes.length; b++) {
				if (nodeMatrix[a][b] != nodeMatrix[b][a]) {
					throw new IllegalAccessException(
						String.format(
							"invalid matrix [%s][%s], [%d][%d]", 
							nodes[a].name, nodes[b].name, a, b));
				}
			}
		}
	}
	
	public void autoCalcurateMatrix() {
		for (int a=0; a<nodes.length; a++) {
			for (int b=0; b<nodes.length; b++) {
				if (nodeMatrix[a][b] == NA) {
					nodeMatrix[a][b] = dijkstra.getDistance(a, b);
					if (nodeMatrix[a][b] > maxDistanceValue) {
						maxDistanceValue = nodeMatrix[a][b];
					}
				}
			}
		}
		// 순위가 아닌 max값 순서로 값을 재할당
		for (int a=0; a<nodes.length; a++) {
			for (int b=0; b<nodes.length; b++) {
				nodeMatrix[a][b] = maxDistanceValue - nodeMatrix[a][b];
			}
		}		
	}
	
	public int getMaxDistanceValue() {
		return maxDistanceValue;
	}
	
	public int getDistance(int fromNominal, int toNominal) {
		try {
			int a = fromNominal-startNominal;
			int b = toNominal-startNominal;
			return nodeMatrix[a][b];
		} catch (ArrayIndexOutOfBoundsException e) {
			return 0;
		}
	}
	
	public int getDistance(String fromName, String toName) {
		int a = getNominalIndex(fromName);
		int b = getNominalIndex(toName);
		return getDistance(a, b);
	}
	
	public double getDistancePercent(int fromNominal, int toNominal) {
		try {
			int a = fromNominal-startNominal;
			int b = toNominal-startNominal;
			return (((double)nodeMatrix[a][b] / (double)maxDistanceValue)) * 100;
		} catch (ArrayIndexOutOfBoundsException e) {
			return 0;
		}
	}
	
	public double getDistancePercent(String fromName, String toName) {
		int a = getNominalIndex(fromName);
		int b = getNominalIndex(toName);
		return getDistancePercent(a, b);
	}
	
	abstract void initilize();
}
