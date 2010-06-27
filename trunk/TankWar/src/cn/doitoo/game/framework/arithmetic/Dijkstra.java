package cn.doitoo.game.framework.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Dijkstra extends PathSolver{
	/**
	 * Dijkstra算法可以描述如下，其中输入的带权有向图是G = (V, E), V = {0, 1, .., n-1}。顶点V是源。
	 * a是一个二维数组，a[i][j]表示边(i, j)的权。当(i, j)不属于E，a[i][j]是一个大数。dist[i]表示当前从
	 * 源到顶点i的最短特殊路径长度。
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
		for (int i = 0; i < n; i++) { // toBeChecked = 所有顶点;
			dist[i] = a[v][i];
			toBeChecked[i] = false;
		}
		dist[v] = 0;

		for (int i = 0; i < n; i++) {
			int temp = Integer.MAX_VALUE;
			int u = v;
			for (int j = 0; j < n; j++) { // u = toBeChecked 中 currDist(u)
											// 最小的一个顶点;
				if (!toBeChecked[j] && dist[j] < temp) {
					u = j;
					temp = dist[j];
				}
			}
			toBeChecked[u] = true; // 从 toBeChecked 中删除 u;
			for (int j = 0; j < n; j++) {
				if (!toBeChecked[j] && j != u && a[u][j] < Integer.MAX_VALUE) { // toBeChecked
																				// 中所有和
																				// u
																				// 邻接的顶点
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
