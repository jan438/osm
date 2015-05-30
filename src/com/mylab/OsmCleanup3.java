package com.mylab;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OsmCleanup3 {
	public static void main(String[] args) {
		String line = "", nodestr = "", waystr = "", relstr = "";
		int pos1, pos2, count_nodes = 0, count_ways = 0, count_rels = 0; 
		long node;
		long way;
		long rels;
		Connection connection = PostgresConnection.openGIS();
		try {
				String city = "Monaco2.osm";
				BufferedReader br = new BufferedReader(new FileReader(city));
				PreparedStatement pstmt_rels = connection
						.prepareStatement("DELETE FROM planet_osm_rels WHERE id=?;");
				line = "";
				count_rels = 0;
				while (line != null) {
					line = br.readLine();
					if ((line != null) && (line.contains("relation id="))) {
						pos1 = line.indexOf("relation id=");
						pos2 = line.indexOf("visible=");
						if ((pos1 > 0) && (pos2 > pos1)) {
							relstr = line.substring(pos1 + 13, pos2 - 2);
							rels = Long.valueOf(relstr).longValue();
							pstmt_rels.setLong(1, rels);
							pstmt_rels.executeQuery();
							count_rels++;
						}
					}
				}
				br.close();
				br = new BufferedReader(new FileReader(city));
				PreparedStatement pstmt_way = connection
							.prepareStatement("DELETE FROM planet_osm_ways WHERE id=?;");
				line = "";
				count_ways = 0;
				while (line != null) {
					line = br.readLine();
					if ((line != null) && (line.contains("way id="))) {
						pos1 = line.indexOf("way id=");
						pos2 = line.indexOf("visible=");
						if ((pos1 > 0) && (pos2 > pos1)) {
							waystr = line.substring(pos1 + 8, pos2 - 2);
							way = Long.valueOf(waystr).longValue();
							pstmt_way.setLong(1, way);
							pstmt_way.executeQuery();
							count_ways++;
						}
					}
				}
				br.close();
				br = new BufferedReader(new FileReader(city));
				PreparedStatement pstmt_node = connection
								.prepareStatement("DELETE FROM planet_osm_nodes WHERE id=?;");
				line = "";
				count_nodes = 0;
				while (line != null) {
					line = br.readLine();
					if ((line != null) && (line.contains("node id="))) {
						pos1 = line.indexOf("node id=");
						pos2 = line.indexOf("visible=");
						if ((pos1 > 0) && (pos2 > pos1)) {
								nodestr = line.substring(pos1 + 9, pos2 - 2);
								node = Long.valueOf(nodestr)
											.longValue();
								pstmt_node.setLong(1, node);
								pstmt_node.executeQuery();
								count_nodes++;
						}
					}
				}
				br.close();
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
		System.out.println("Deleted rels count: " + count_rels + " deleted ways count: " + count_ways + " deleted nodes count: " + count_nodes);
	}
}