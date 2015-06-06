package com.mylab;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class FlagPosition {

	public static void main(String[] args) {
		double[] temp1 = new double[2];
		double[] temp2 = new double[2];
		int zoom = 14;
		String[] flags = { "australia.jpg", "malaysia.jpg", "china.jpg",
				"bahrein.jpg", "spain.jpg", "monaco.jpg", "canada.jpg",
				"austria.jpg", "united-kingdom.jpg", "hungary.jpg",
				"belgium.jpg", "italy.jpg", "singapore.jpg", "japan.jpg",
				"russia.jpg", "america.jpg", "mexico.jpg", "brazil.jpg",
				"abu-dhabi.jpg" };
		double[][] location = new double[19][2];
		location[0][0] = -37.847281;
		location[0][1] = 144.971186;
		location[1][0] = 2.759722;
		location[1][1] = 101.7375;
		location[2][0] = 31.337403;
		location[2][1] = 121.220786;
		location[3][0] = 26.031389;
		location[3][1] = 50.514444;
		location[4][0] = 41.57;
		location[4][1] = 2.258056;
		location[5][0] = 43.734722;
		location[5][1] = 7.420556;
		location[6][0] = 45.500578;
		location[6][1] = -73.522461;
		location[7][0] = 47.219722;
		location[7][1] = 14.764722;
		location[8][0] = 52.078611;
		location[8][1] = -1.016944;
		location[9][0] = 47.583056;
		location[9][1] = 19.251111;
		location[10][0] = 50.443889;
		location[10][1] = 5.965278;
		location[11][0] = 45.617817;
		location[11][1] = 9.28105;
		location[12][0] = 1.291403;
		location[12][1] = 103.864147;
		location[13][0] = 34.843056;
		location[13][1] = 136.540556;
		location[14][0] = 43.408333;
		location[14][1] = 39.965556;
		location[15][0] = 30.1325;
		location[15][1] = -97.640833;
		location[16][0] = 19.404167;
		location[16][1] = -99.088889;
		location[17][0] = -23.701436;
		location[17][1] = -46.697131;
		location[18][0] = 24.495839;
		location[18][1] = 54.604011;
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.forLanguageTag("en_US"));
		DecimalFormat df = (DecimalFormat)nf;
		String pattern = "##.#####";
		df.applyPattern(pattern);
		double delta_x = 0.000001;
		double delta_y = 0.000001;
		System.out.println("[");		
		for (int count = 0; count < 19; count++) {
			int loc_x = getTileX(location[count][1], zoom);
			int loc_y = getTileY(location[count][0], zoom);
			temp1 = location[count];
			int se_x = loc_x;
			int se_y = loc_y;
			while (se_x == loc_x) {
				temp1[1] = temp1[1] - delta_x;
				se_x = getTileX(temp1[1], zoom);
			}
			temp2[1] = temp1[1];
			while (se_y == loc_y) {
				temp1[0] = temp1[0] + delta_y;
				se_y = getTileY(temp1[0], zoom);
			}
			temp2[0] = temp1[0];
			se_x = getTileX(temp2[1], zoom);
			se_y = getTileY(temp2[0], zoom);
			int nw_x = se_x;
			int nw_y = se_y;
			while (nw_x == se_x) {
				temp2[1] = temp2[1] - delta_x;
				nw_x = getTileX(temp2[1], zoom);
			}
			while (nw_y == se_y) {
				temp2[0] = temp2[0] + delta_y;
				nw_y = getTileY(temp2[0], zoom);
			}
			double hight = temp2[0] - temp1[0];
			hight = hight * 67 / 100;
			temp1[0] = temp2[0] - hight;
			System.out.print("[\"images/f1/" + flags[count] + "\", "
					+ df.format(temp1[0]) + ", "
					+ df.format(temp1[1]) + ", "
					+ df.format(temp2[0]) + ", "
					+ df.format(temp2[1]) + "]");
			if (count < 18) {
				System.out.println(",");
			}
			else {
				System.out.println("");
			}
		}
		System.out.println("];");
	}

	private static int getTileX(double lon, int zoom) {
		return (int) (Math.floor((lon + 180) / 360 * (1 << zoom)));
	}

	private static int getTileY(double lat, int zoom) {
		return (int) (Math.floor((1 - Math.log(Math.tan(Math.toRadians(lat))
				+ 1 / Math.cos(Math.toRadians(lat)))
				/ Math.PI)
				/ 2 * (1 << zoom)));
	}

}
