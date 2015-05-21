package com.mylab;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class skippytest {
	public static final float DEG2RAD = (float) (Math.PI / 180.0);
	public static final float RAD2DEG = (float) (180.0 / Math.PI);
	public static final float PI = (float) Math.PI;
	public static final float PI_2 = PI / 2.0f;
	public static final float PI_4 = PI / 4.0f;

	public static void main(String[] args) {
		String[] steden = new String[10];
		steden[9] = "Nijmegen";
		steden[8] = "Breda";
		steden[7] = "Groningen";
		steden[6] = "Almere";
		steden[5] = "Tilburg";
		steden[4] = "Eindhoven";
		steden[3] = "Utrecht";
		steden[2] = "Den_Haag";
		steden[1] = "Rotterdam";
		steden[0] = "Amsterdam";
		String pattern = "##,######";
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		double[][] coordinates = new double[10][10];
		coordinates[0][0] = 52.366667; // Amsterdam
		coordinates[0][1] = 4.9;
		coordinates[1][0] = 51.916667; // Rotterdam
		coordinates[1][1] = 4.5;
		coordinates[2][0] = 52.095556; // Den Haag
		coordinates[2][1] = 4.316389;
		coordinates[3][0] = 52.088889; // Utrecht
		coordinates[3][1] = 5.115556;
		coordinates[4][0] = 51.435556; // Eindhoven
		coordinates[4][1] = 5.480556;
		coordinates[5][0] = 51.833333; // Tilburg
		coordinates[5][1] = 5.091111;
		coordinates[6][0] = 52.375; // Almere
		coordinates[6][1] = 5.217778;
		coordinates[7][0] = 53.216667; // Groningen
		coordinates[7][1] = 6.566667;
		coordinates[8][0] = 51.5875; // Breda
		coordinates[8][1] = 4.775;
		coordinates[9][0] = 51.833333; // Nijmegen
		coordinates[9][1] = 5.866667;
		int zoom, len;
		double lat;
		double lon;
		double lat_min = Double.MAX_VALUE;
		double lon_min = Double.MAX_VALUE;
		double lat_max = 0.0;
		double lon_max = 0.0;
		long[] points_long = new long[100];
		String points_string[] = new String[100];
		URL url = null;
		String nodes, tags;
		/*
		 * for (int i = 0; i < 10; i++) { lat = coordinates[i][0]; lon =
		 * coordinates[i][1]; for (zoom = 8; zoom < 19; zoom++) { try { url =
		 * new URL("http://localhost/" + getTileNumber(lat, lon, zoom) +
		 * ".png"); URLConnection connection = null; connection =
		 * url.openConnection(); InputStream in = null; in =
		 * connection.getInputStream(); FileOutputStream fos = null; File tile =
		 * File.createTempFile(steden[i] + "_" + zoom + "_", ".png");
		 * tile.deleteOnExit(); fos = new FileOutputStream(tile); byte[] buf =
		 * new byte[512]; while (true) { len = in.read(buf); if (len == -1)
		 * break; fos.write(buf, 0, len); try { Thread.sleep(100); } catch
		 * (InterruptedException e) { e.printStackTrace(); } } in.close();
		 * fos.flush(); fos.close(); } catch (IOException e) {
		 * e.printStackTrace(); } } } lat = 52.681599; lon = 5.150296; for (zoom
		 * = 8; zoom < 19; zoom++) { try { url = new URL("http://localhost/" +
		 * getTileNumber(lat, lon, zoom) + ".png"); URLConnection connection =
		 * null; connection = url.openConnection(); InputStream in = null; in =
		 * connection.getInputStream(); FileOutputStream fos = null; File tile =
		 * File.createTempFile("la_normande" + "_" + zoom + "_", ".png"); fos =
		 * new FileOutputStream(tile); byte[] buf = new byte[512]; while (true)
		 * { len = in.read(buf); if (len == -1) break; fos.write(buf, 0, len);
		 * try { Thread.sleep(100); } catch (InterruptedException e) {
		 * e.printStackTrace(); } } in.close(); fos.flush(); fos.close(); }
		 * catch (IOException e) { e.printStackTrace(); } } BoundingBox bb1 =
		 * BoundingBox.tile2boundingBox(131, 83, 8); System.out.println(" " +
		 * bb1.east + " " + bb1.west + " " + bb1.north + " " + bb1.south);
		 * double bblon = BoundingBox.tile2lon(131, 8); double bblat =
		 * BoundingBox.tile2lat(83, 8); System.out.println(" " + bblon + " " +
		 * bblat); BoundingBox bb2 = BoundingBox.degrees2boundingBox(52.48678,
		 * 4.73876, 52.49172, 4.74880);
		 */
		Connection connection = PostgresConnection.openGIS();
		try {
			/*
			 * PreparedStatement pstmt1 = connection.prepareStatement(
			 * "SELECT * FROM planet_osm_point WHERE ST_Y(ST_Transform(way, 4326)) < '52.4897' AND ST_Y(ST_Transform(way, 4326)) > '52.4895' AND ST_X(ST_Transform(way, 4326)) < '4.7424' AND ST_X(ST_Transform(way, 4326)) > '4.7423';"
			 * ); ResultSet rs1 = pstmt1.executeQuery(); while (rs1.next()) {
			 * String housenumber = rs1.getString("addr:housenumber");
			 * System.out.println("housenumber: " + housenumber); }
			 * PreparedStatement pstmt2 = connection.prepareStatement(
			 * "SELECT * FROM planet_osm_point WHERE ST_Y(ST_Transform(way, 4326)) < '52.6820' AND ST_Y(ST_Transform(way, 4326)) > '52.6810' AND ST_X(ST_Transform(way, 4326)) < '5.1505' AND ST_X(ST_Transform(way, 4326)) > '5.1500';"
			 * ); ResultSet rs2 = pstmt2.executeQuery(); while (rs2.next()) {
			 * String housenumber = rs2.getString("addr:housenumber");
			 * System.out.println("housenumber: " + housenumber); }
			 */
			PreparedStatement pstmt3 = connection
					.prepareStatement("SELECT * FROM planet_osm_ways WHERE id=24303321;");
			ResultSet rs3 = pstmt3.executeQuery();
			while (rs3.next()) {
				nodes = rs3.getString("nodes");
				tags = rs3.getString("tags");
				points_string = nodes.split(",");
				String first = points_string[0];
				int length = points_string.length;
				String last = points_string[length - 1];
				points_string[0] = first.substring(1);
				points_string[length - 1] = last
						.substring(0, last.length() - 1);
				System.out.println("nodes: " + nodes);
				System.out.println("tags : " + tags);
				for (int i = 0; i < length; i++) {
					points_long[i] = Long.parseLong(points_string[i]);
					PreparedStatement pstmt4 = connection
							.prepareStatement("SELECT * FROM planet_osm_nodes WHERE id=?;");
					pstmt4.setLong(1, points_long[i]);
					ResultSet rs4 = pstmt4.executeQuery();
					while (rs4.next()) {
						int llat = rs4.getInt("lat");
						int llon = rs4.getInt("lon");
						System.out.println(i + ": lat: " + llat + " lon: "
								+ llon);
					}
				}
			}
			PreparedStatement pstmt5 = connection
					.prepareStatement("SELECT * FROM planet_osm_nodes WHERE id=?;");
			pstmt5.setLong(1, 2890992926L);
			ResultSet rs5 = pstmt5.executeQuery();
			while (rs5.next()) {
				int llat = rs5.getInt("lat");
				int llon = rs5.getInt("lon");
				System.out.println("BoterCartoons: lat: " + llat + " lon: "
						+ llon);
			}
			pstmt5.setLong(1, 2797843267L);
			rs5 = pstmt5.executeQuery();
			while (rs5.next()) {
				int llat = rs5.getInt("lat");
				int llon = rs5.getInt("lon");
				System.out.println("La Normande 66: lat: " + llat + " lon: "
						+ llon);
			}
			pstmt5.setLong(1, 2797843973L);
			rs5 = pstmt5.executeQuery();
			while (rs5.next()) {
				int llat = rs5.getInt("lat");
				int llon = rs5.getInt("lon");
				System.out.println("La Normande 68: lat: " + llat + " lon: "
						+ llon);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		PostgresConnection.closeGIS();
		connection = PostgresConnection.openNominatim();
		try {
			/*
			 * PreparedStatement pstmt1 = connection.prepareStatement(
			 * "SELECT * FROM planet_osm_point WHERE ST_Y(ST_Transform(way, 4326)) < '52.4897' AND ST_Y(ST_Transform(way, 4326)) > '52.4895' AND ST_X(ST_Transform(way, 4326)) < '4.7424' AND ST_X(ST_Transform(way, 4326)) > '4.7423';"
			 * ); ResultSet rs1 = pstmt1.executeQuery(); while (rs1.next()) {
			 * String housenumber = rs1.getString("addr:housenumber");
			 * System.out.println("housenumber: " + housenumber); }
			 * PreparedStatement pstmt2 = connection.prepareStatement(
			 * "SELECT * FROM planet_osm_point WHERE ST_Y(ST_Transform(way, 4326)) < '52.6820' AND ST_Y(ST_Transform(way, 4326)) > '52.6810' AND ST_X(ST_Transform(way, 4326)) < '5.1505' AND ST_X(ST_Transform(way, 4326)) > '5.1500';"
			 * ); ResultSet rs2 = pstmt2.executeQuery(); while (rs2.next()) {
			 * String housenumber = rs2.getString("addr:housenumber");
			 * System.out.println("housenumber: " + housenumber); }
			 */
			PreparedStatement pstmt3 = connection
					.prepareStatement("SELECT * FROM planet_osm_ways WHERE id=24303321;");
			ResultSet rs3 = pstmt3.executeQuery();
			while (rs3.next()) {
				nodes = rs3.getString("nodes");
				tags = rs3.getString("tags");
				points_string = nodes.split(",");
				String first = points_string[0];
				int length = points_string.length;
				String last = points_string[length - 1];
				points_string[0] = first.substring(1);
				points_string[length - 1] = last
						.substring(0, last.length() - 1);
				System.out.println("nodes: " + nodes);
				System.out.println("tags : " + tags);
				for (int i = 0; i < length; i++) {
					points_long[i] = Long.parseLong(points_string[i]);
					PreparedStatement pstmt4 = connection
							.prepareStatement("SELECT * FROM planet_osm_nodes WHERE id=?;");
					pstmt4.setLong(1, points_long[i]);
					ResultSet rs4 = pstmt4.executeQuery();
					while (rs4.next()) {
						int llat = rs4.getInt("lat");
						int llon = rs4.getInt("lon");
						System.out.println(i + ": lat: " + llat + " lon: "
								+ llon);
					}
				}
			}
			PreparedStatement pstmt5 = connection
					.prepareStatement("SELECT * FROM planet_osm_nodes WHERE id=?;");
			pstmt5.setLong(1, 2890992926L);
			ResultSet rs5 = pstmt5.executeQuery();
			while (rs5.next()) {
				int llat = rs5.getInt("lat");
				int llon = rs5.getInt("lon");
				System.out.println("BoterCartoons: lat: " + llat + " lon: "
						+ llon);
			}
			pstmt5.setLong(1, 2797843267L);
			rs5 = pstmt5.executeQuery();
			while (rs5.next()) {
				int llat = rs5.getInt("lat");
				int llon = rs5.getInt("lon");
				System.out.println("La Normande 66: lat: " + llat + " lon: "
						+ llon);
			}
			pstmt5.setLong(1, 2797843973L);
			rs5 = pstmt5.executeQuery();
			while (rs5.next()) {
				int llat = rs5.getInt("lat");
				int llon = rs5.getInt("lon");
				System.out.println("La Normande 68: lat: " + llat + " lon: "
						+ llon);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		PostgresConnection.closeNominatim();
		pattern = "##.##";
		decimalFormat = new DecimalFormat(pattern);
		System.out.println("Distance BoterCartoons + La Normande: "
				+ decimalFormat.format(OsmMercator.getDistance(52.4897, 4.7424,
						52.6815, 5.1502) / 1000) + " km");
	}

	public static String getTileNumber(final double lat, final double lon,
			final int zoom) {
		String host_tiles = "osm_tiles/";
		int xtile = (int) Math.floor((lon + 180) / 360 * (1 << zoom));
		int ytile = (int) Math
				.floor((1 - Math.log(Math.tan(Math.toRadians(lat)) + 1
						/ Math.cos(Math.toRadians(lat)))
						/ Math.PI)
						/ 2 * (1 << zoom));
		if (xtile < 0)
			xtile = 0;
		if (xtile >= (1 << zoom))
			xtile = ((1 << zoom) - 1);
		if (ytile < 0)
			ytile = 0;
		if (ytile >= (1 << zoom))
			ytile = ((1 << zoom) - 1);
		return (host_tiles + zoom + "/" + xtile + "/" + ytile);
	}
}