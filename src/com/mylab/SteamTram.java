package com.mylab;

import java.text.DecimalFormat;

public class SteamTram {

	public static void main(String[] args) {
		double[][] steamtram_hoorn_medemblik = new double[22][2];
		steamtram_hoorn_medemblik[0][0] = 52.64556;
		steamtram_hoorn_medemblik[0][1] = 5.05464;
		steamtram_hoorn_medemblik[1][0] = 52.64764;
		steamtram_hoorn_medemblik[1][1] = 5.07322;
		steamtram_hoorn_medemblik[2][0] = 52.64928;
		steamtram_hoorn_medemblik[2][1] = 5.07434;
		steamtram_hoorn_medemblik[3][0] = 52.65443;
		steamtram_hoorn_medemblik[3][1] = 5.06914;
		steamtram_hoorn_medemblik[4][0] = 52.65961;
		steamtram_hoorn_medemblik[4][1] = 5.06314;
		steamtram_hoorn_medemblik[5][0] = 52.66749;
		steamtram_hoorn_medemblik[5][1] = 5.05885;
		steamtram_hoorn_medemblik[6][0] = 52.66835;
		steamtram_hoorn_medemblik[6][1] = 5.05815;
		steamtram_hoorn_medemblik[7][0] = 52.68132;
		steamtram_hoorn_medemblik[7][1] = 5.03377;
		steamtram_hoorn_medemblik[8][0] = 52.71073;
		steamtram_hoorn_medemblik[8][1] = 5.03177;
		steamtram_hoorn_medemblik[9][0] = 52.71685;
		steamtram_hoorn_medemblik[9][1] = 5.02687;
		steamtram_hoorn_medemblik[10][0] = 52.71812;
		steamtram_hoorn_medemblik[10][1] = 5.02755;
		steamtram_hoorn_medemblik[11][0] = 52.71858;
		steamtram_hoorn_medemblik[11][1] = 5.02865;
		steamtram_hoorn_medemblik[12][0] = 52.72353;
		steamtram_hoorn_medemblik[12][1] = 5.05275;
		steamtram_hoorn_medemblik[13][0] = 52.72779;
		steamtram_hoorn_medemblik[13][1] = 5.07468;
		steamtram_hoorn_medemblik[14][0] = 52.72928;
		steamtram_hoorn_medemblik[14][1] = 5.07625;
		steamtram_hoorn_medemblik[15][0] = 52.75506;
		steamtram_hoorn_medemblik[15][1] = 5.07024;
		steamtram_hoorn_medemblik[16][0] = 52.75959;
		steamtram_hoorn_medemblik[16][1] = 5.07217;
		steamtram_hoorn_medemblik[17][0] = 52.76185;
		steamtram_hoorn_medemblik[17][1] = 5.07703;
		steamtram_hoorn_medemblik[18][0] = 52.76548;
		steamtram_hoorn_medemblik[18][1] = 5.08665;
		steamtram_hoorn_medemblik[19][0] = 52.76723;
		steamtram_hoorn_medemblik[19][1] = 5.09269;
		steamtram_hoorn_medemblik[20][0] = 52.77312;
		steamtram_hoorn_medemblik[20][1] = 5.10091;
		steamtram_hoorn_medemblik[21][0] = 52.77401;
		steamtram_hoorn_medemblik[21][1] = 5.10562;
		for (int i = 0; i < 22; i++) {
			System.out.println("" + i + ": " + steamtram_hoorn_medemblik[i][0]
					+ ", " + steamtram_hoorn_medemblik[i][1]);
		}
		String pattern = "##.##";
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		for (int i = 0; i < 21; i++) {
			System.out.println(""
					+ i
					+ ": "
					+ decimalFormat.format(OsmMercator.getDistance(
							steamtram_hoorn_medemblik[i][0],
							steamtram_hoorn_medemblik[i][1],
							steamtram_hoorn_medemblik[i + 1][0],
							steamtram_hoorn_medemblik[i + 1][1])));
		}
		for (int i = 0; i < 21; i++) {
			System.out.print((int) Math.round(OsmMercator.getDistance(
					steamtram_hoorn_medemblik[i][0],
					steamtram_hoorn_medemblik[i][1],
					steamtram_hoorn_medemblik[i + 1][0],
					steamtram_hoorn_medemblik[i + 1][1])));
			if (i < 20)
				System.out.print(",");
		}
		System.out.println();
		System.out
				.println("==========================================================");
		double[][] train_enkhuizen_hoorn = new double[13][2];
		train_enkhuizen_hoorn[0][0] = 52.69957;
		train_enkhuizen_hoorn[0][1] = 5.28819;
		train_enkhuizen_hoorn[1][0] = 52.69816;
		train_enkhuizen_hoorn[1][1] = 5.27488;
		train_enkhuizen_hoorn[2][0] = 52.69603;
		train_enkhuizen_hoorn[2][1] = 5.25319;
		train_enkhuizen_hoorn[3][0] = 52.69529;
		train_enkhuizen_hoorn[3][1] = 5.24598;
		train_enkhuizen_hoorn[4][0] = 52.69502;
		train_enkhuizen_hoorn[4][1] = 5.23607;
		train_enkhuizen_hoorn[5][0] = 52.69534;
		train_enkhuizen_hoorn[5][1] = 5.21135;
		train_enkhuizen_hoorn[6][0] = 52.69031;
		train_enkhuizen_hoorn[6][1] = 5.18274;
		train_enkhuizen_hoorn[7][0] = 52.68553;
		train_enkhuizen_hoorn[7][1] = 5.15879;
		train_enkhuizen_hoorn[8][0] = 52.66766;
		train_enkhuizen_hoorn[8][1] = 5.11078;
		train_enkhuizen_hoorn[9][0] = 52.65345;
		train_enkhuizen_hoorn[9][1] = 5.08511;
		train_enkhuizen_hoorn[10][0] = 52.64812;
		train_enkhuizen_hoorn[10][1] = 5.07509;
		train_enkhuizen_hoorn[11][0] = 52.64458;
		train_enkhuizen_hoorn[11][1] = 5.05996;
		train_enkhuizen_hoorn[12][0] = 52.64554;
		train_enkhuizen_hoorn[12][1] = 5.05458;
		for (int i = 0; i < 13; i++) {
			System.out.println("" + i + ": " + train_enkhuizen_hoorn[i][0]
					+ ", " + train_enkhuizen_hoorn[i][1]);
		}

		for (int i = 0; i < 12; i++) {
			System.out.println(""
					+ i
					+ ": "
					+ decimalFormat.format(OsmMercator.getDistance(
							train_enkhuizen_hoorn[i][0],
							train_enkhuizen_hoorn[i][1],
							train_enkhuizen_hoorn[i + 1][0],
							train_enkhuizen_hoorn[i + 1][1])));
		}
		for (int i = 0; i < 12; i++) {
			System.out.print((int) Math.round(OsmMercator.getDistance(
					train_enkhuizen_hoorn[i][0], train_enkhuizen_hoorn[i][1],
					train_enkhuizen_hoorn[i + 1][0],
					train_enkhuizen_hoorn[i + 1][1])));
			if (i < 11)
				System.out.print(",");
		}
		System.out.println();
		System.out
				.println("==========================================================");

	}
}
