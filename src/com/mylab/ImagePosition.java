package com.mylab;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class ImagePosition {

	public static void main(String[] args) {
		double[] location = new double[2];
		double[] southwest = new double[2];
		double[] northeast = new double[2];
		double[] temp1 = new double[2];
		double[] temp2 = new double[2];
		Locale locale  = new Locale("en", "UK");
		String pattern = "###.########";
		DecimalFormat decimalFormat = (DecimalFormat)
		        NumberFormat.getNumberInstance(locale);
		decimalFormat.applyPattern(pattern);
		double delta_x = 0.000001;
		double delta_y = 0.000001;
		location[0] = 19.404167;
		location[1] = -99.088889;
		System.out.println("" + decimalFormat.format(location[0]) + "  "
				+ decimalFormat.format(location[1]));
		int loc_x = getTileX(location[1], 17);
		int loc_y = getTileY(location[0], 17);
		temp1 = location;
		int ne_x = loc_x;
		int ne_y = loc_y;
		while (ne_x == loc_x) {
			temp1[1] = temp1[1] + delta_x;
			ne_x = getTileX(temp1[1], 17);
		}
		northeast[1] = temp1[1];
		while (ne_y == loc_y) {
			temp1[0] = temp1[0] + delta_y;
			ne_y = getTileY(temp1[0], 17);
		}
		northeast[0] = temp1[0];
		temp2 = location;
		ne_x = loc_x;
		ne_y = loc_y;
		while (ne_x == loc_x) {
			temp2[1] = temp2[1] - delta_x;
			ne_x = getTileX(temp2[1], 17);
		}
		southwest[1] = temp2[1];
		while (ne_y == loc_y) {
			temp2[0] = temp2[0] - delta_y;
			ne_y = getTileY(temp2[0], 17);
		}
		southwest[0] = temp2[0];
		System.out.println("ne " + decimalFormat.format(northeast[0]) + "  "
				+ decimalFormat.format(northeast[1]));
		System.out.println("sw " + decimalFormat.format(southwest[0]) + "  "
				+ decimalFormat.format(southwest[1]));
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
