package cn.doitoo.game.framework.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Dijkstra extends PathSolver{
	/**
	 * Dijkstra�㷨�����������£���������Ĵ�Ȩ����ͼ��G = (V, E), V = {0, 1, .., n-1}������V��Դ��
	 * a��һ����ά���飬a[i][j]��ʾ��(i, j)��Ȩ����(i, j)������E��a[i][j]��һ��������dist[i]��ʾ��ǰ��
	 * Դ������i���������·�����ȡ�
	 * 
	 * @param v
	 *            in the source vertex
	 * @param a
	 *            the weight matrix
	 * @param dist
	 *            the path length
	 * @param prev
	 *            the preverse vertex in the path
	 */
	public static void dijkstra(int v, int[][] a, int[] dist, int[] prev) {
		int n = dist.length;
		if (v < 0 || v >= n) {
			throw new IllegalArgumentException(
					"v should between 0 inclusive and dist.length exclusive.");
		}
		boolean[] toBeChecked = new boolean[n];
		for (int i = 0; i < n; i++) { // toBeChecked = ���ж���;
			dist[i] = a[v][i];
			toBeChecked[i] = false;
		}
		dist[v] = 0;

		for (int i = 0; i < n; i++) {
			int temp = Integer.MAX_VALUE;
			int u = v;
			for (int j = 0; j < n; j++) { // u = toBeChecked �� currDist(u)
											// ��С��һ������;
				if (!toBeChecked[j] && dist[j] < temp) {
					u = j;
					temp = dist[j];
				}
			}
			toBeChecked[u] = true; // �� toBeChecked ��ɾ�� u;
			for (int j = 0; j < n; j++) {
				if (!toBeChecked[j] && j != u && a[u][j] < Integer.MAX_VALUE) { // toBeChecked
																				// �����к�
																				// u
																				// �ڽӵĶ���
																				// j
					int newdist = dist[u] + a[u][j];
					if (newdist < dist[j]) {
						dist[j] = newdist;
						prev[j] = u;
					}
				}
			}
		}
	}

	

	@Override
	public List<Integer> solvePath(int[][] adjVect, int startV, int endV) {
		int[] dist = new int[adjVect.length];
		int[] prev = new int[adjVect.length];
		dijkstra(startV, adjVect, dist, prev);
		Stack<Integer> st = new Stack<Integer>();		
		while(endV!=0){			
			   st.add(endV);
			   endV =prev[endV];
		}
		List<Integer> pathList = new ArrayList<Integer>();
		while(!st.isEmpty()){
			pathList.add(st.pop());
		}
		return pathList;
	}
}
