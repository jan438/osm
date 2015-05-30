package com.mylab;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OsmCleanup {
	public static void main(String[] args) {
		String line = "", nodestr = "", waystr = "", relstr = "";
		int pos1, pos2, i, j, count_nodes, count_ways, count_rels, node_count = 0, result_count = 0;
		long[] node = new long[20000];
		long[] way = new long[10000];
		long[] rels = new long[10000];
		long[] max_lon = new long[3];
		long[] min_lon = new long[3];
		long[] max_lat = new long[3];
		long[] min_lat = new long[3];
		Connection connection = PostgresConnection.openGIS();
		try {
			for (int c = 0; c < args.length; c++) {
				String city = args[c];
				max_lon[c] = 0;
				max_lat[c] = 0;
				min_lon[c] = 9999999999L;
				min_lat[c] = 9999999999L;
				BufferedReader br = new BufferedReader(new FileReader(city));
				PreparedStatement pstmt_node = connection
						.prepareStatement("SELECT * FROM planet_osm_nodes WHERE id=?;");
				PreparedStatement pstmt_way = connection
						.prepareStatement("SELECT * FROM planet_osm_ways WHERE id=?;");
				PreparedStatement pstmt_rels = connection
						.prepareStatement("SELECT * FROM planet_osm_rels WHERE id=?;");
				ResultSet rs = null;
				line = "";
				count_nodes = 0;
				count_ways = 0;
				count_rels = 0;
				while (line != null) {
					line = br.readLine();
					if ((line != null) && (line.contains("node id="))) {
						pos1 = line.indexOf("node id=");
						pos2 = line.indexOf("visible=");
						if ((pos1 > 0) && (pos2 > pos1)) {
							nodestr = line.substring(pos1 + 9, pos2 - 2);
							node[count_nodes] = Long.valueOf(nodestr)
									.longValue();
							pstmt_node.setLong(1, node[count_nodes]);
							rs = pstmt_node.executeQuery();
							i = 0;
							while (rs.next()) {
								int llat = rs.getInt("lat");
								int llon = rs.getInt("lon");
/*								System.out.println(city + " " + count_nodes
										+ " node: " + node[count_nodes]
										+ " lat: " + llat + " lon: " + llon); */
								if (llat > max_lat[c])
									max_lat[c] = llat;
								if (llon > max_lon[c])
									max_lon[c] = llon;
								if (llat < min_lat[c])
									min_lat[c] = llat;
								if (llon < min_lon[c])
									min_lon[c] = llon;
								count_nodes++;
							}
						}
					}
					if ((line != null) && (line.contains("way id="))) {
						pos1 = line.indexOf("way id=");
						pos2 = line.indexOf("visible=");
						if ((pos1 > 0) && (pos2 > pos1)) {
							waystr = line.substring(pos1 + 8, pos2 - 2);
							way[count_ways] = Long.valueOf(waystr).longValue();
							pstmt_way.setLong(1, way[count_ways]);
							rs = pstmt_way.executeQuery();
							i = 0;
							while (rs.next()) {
/*								System.out.println(city + " " + count_ways
										+ " way: " + way[count_ways]); */
								count_ways++;
							}
						}
					}
					if ((line != null) && (line.contains("relation id="))) {
						pos1 = line.indexOf("relation id=");
						pos2 = line.indexOf("visible=");
						if ((pos1 > 0) && (pos2 > pos1)) {
							relstr = line.substring(pos1 + 13, pos2 - 2);
							rels[count_rels] = Long.valueOf(relstr).longValue();
							pstmt_rels.setLong(1, rels[count_rels]);
							rs = pstmt_rels.executeQuery();
							i = 0;
							while (rs.next()) {
/*								System.out.println(city + " " + count_rels
										+ " relation: " + rels[count_rels]); */
								count_rels++;
							}
						}
					}
				}
				br.close();
				if (c == 1) {
					node_count = count_nodes;
				}
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (int c = 0; c < args.length; c++) {
			System.out.println(args[c] + " " + " max lat: " + max_lat[c]
					+ " max lon: " + max_lon[c] + " min lat: " + min_lat[c]
					+ " min lon: " + min_lon[c]);
		}
		connection = PostgresConnection.openGIS();
		try {
				PreparedStatement pstmt_node = connection
						.prepareStatement("SELECT * FROM planet_osm_nodes WHERE lat>? AND lon>? AND lat<? AND lon<?;");
				pstmt_node.setLong(1, max_lat[0]);
				pstmt_node.setLong(2, max_lon[0]);
				pstmt_node.setLong(3, min_lat[2]);
				pstmt_node.setLong(4, min_lon[2]);
				ResultSet rs = null;
				rs = pstmt_node.executeQuery();
				result_count = 0;
				while (rs.next()) {
					int llat = rs.getInt("lat");
					int llon = rs.getInt("lon");
/*					System.out.println(result_count + " lat: " + llat + " lon: " + llon); */
					result_count++;
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		System.out.println("node count: " + node_count + " result count: " + result_count);
	}
}
