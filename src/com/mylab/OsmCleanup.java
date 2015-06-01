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
		int pos1, pos2, i, j, count_nodes = 0, count_ways = 0, count_rels = 0;
		long[] count_way = new long[4];
		long[] count_rel = new long[4];
		long[] count_node = new long[4];
		long[][] node = new long[4][20000];
		long[][] way = new long[4][10000];
		long[][] rels = new long[4][10000];
		long[] max_lon = new long[4];
		long[] min_lon = new long[4];
		long[] max_lat = new long[4];
		long[] min_lat = new long[4];
		Connection connection = PostgresConnection.openGIS();
		for (int c = 0; c < args.length; c++) {
			count_node[c] = 0;
			count_way[c] = 0;
			count_rel[c] = 0;
		}
		try {
			for (int c = 0; c < args.length; c++) {
				String camping = args[c];
				max_lon[c] = 0;
				max_lat[c] = 0;
				min_lon[c] = 9999999999L;
				min_lat[c] = 9999999999L;
				BufferedReader br = new BufferedReader(new FileReader(camping));
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
							node[c][count_nodes] = Long.valueOf(nodestr)
									.longValue();
							pstmt_node.setLong(1, node[c][count_nodes]);
							rs = pstmt_node.executeQuery();
							i = 0;
							while (rs.next()) {
								int llat = rs.getInt("lat");
								int llon = rs.getInt("lon");
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
							way[c][count_ways] = Long.valueOf(waystr)
									.longValue();
							pstmt_way.setLong(1, way[c][count_ways]);
							rs = pstmt_way.executeQuery();
							i = 0;
							while (rs.next()) {
								count_ways++;
							}
						}
					}
					if ((line != null) && (line.contains("relation id="))) {
						pos1 = line.indexOf("relation id=");
						pos2 = line.indexOf("visible=");
						if ((pos1 > 0) && (pos2 > pos1)) {
							relstr = line.substring(pos1 + 13, pos2 - 2);
							rels[c][count_rels] = Long.valueOf(relstr)
									.longValue();
							pstmt_rels.setLong(1, rels[c][count_rels]);
							rs = pstmt_rels.executeQuery();
							i = 0;
							while (rs.next()) {
								count_rels++;
							}
						}
					}
				}
				br.close();
				count_node[c] = count_nodes;
				count_way[c] = count_ways;
				count_rel[c] = count_rels;
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
		/*
		 * connection = PostgresConnection.openGIS(); try { PreparedStatement
		 * pstmt_node = connection .prepareStatement(
		 * "SELECT * FROM planet_osm_nodes WHERE lat>? AND lon>? AND lat<? AND lon<?;"
		 * ); pstmt_node.setLong(1, max_lat[0]); pstmt_node.setLong(2,
		 * max_lon[0]); pstmt_node.setLong(3, min_lat[2]); pstmt_node.setLong(4,
		 * min_lon[2]); System.out.println(" max lat: " + max_lat[0] +
		 * " max lon: " + max_lon[0]); System.out.println(" min lat: " +
		 * min_lat[0] + " min lon: " + min_lon[0]);
		 * 
		 * ResultSet rs = null; rs = pstmt_node.executeQuery();
		 * node_result_count = 0; while (rs.next()) { int llat =
		 * rs.getInt("lat"); int llon = rs.getInt("lon");
		 * System.out.println(" lat: " + llat + " lon: " + llon);
		 * node_result_count++; } PreparedStatement pstmt_way = connection
		 * .prepareStatement("SELECT * FROM planet_osm_ways WHERE id=?;");
		 * way_result_count = 0; for (int l = 0; l < way_count; l++) {
		 * pstmt_way.setLong(1, way[0][l]); rs = pstmt_way.executeQuery(); while
		 * (rs.next()) { way_result_count++; }
		 * 
		 * } PreparedStatement pstmt_rel = connection
		 * .prepareStatement("SELECT * FROM planet_osm_rels WHERE id=?;");
		 * rel_result_count = 0; for (int l = 0; l < rel_count; l++) {
		 * pstmt_rel.setLong(1, rels[1][l]); rs = pstmt_rel.executeQuery();
		 * while (rs.next()) { rel_result_count++; } } } catch (SQLException e)
		 * { e.printStackTrace(); } try { connection.close(); } catch
		 * (SQLException e) { e.printStackTrace(); }
		 */
		for (int c = 0; c < args.length; c++) {
			System.out.println(args[c] + " node count: " + count_node[c] + " way count: "
					+ count_way[c] + " rel count: " + count_rel[c]);
		}
		System.out.println("Possible conflicting relation: " + rels[2][0]);
/*		962076 */
	}
}
