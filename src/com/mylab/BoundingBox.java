package com.mylab;

class BoundingBox {

	double north;
	double south;
	double east;
	double west;

	static BoundingBox tile2boundingBox(final int x, final int y, final int zoom) {
		BoundingBox bb = new BoundingBox();
		bb.north = tile2lat(y, zoom);
		bb.south = tile2lat(y + 1, zoom);
		bb.west = tile2lon(x, zoom);
		bb.east = tile2lon(x + 1, zoom);
		return bb;
	}

	static BoundingBox degrees2boundingBox(double xmin, double ymin,
			double xmax, double ymax) {
		BoundingBox bb = new BoundingBox();
		bb.north = ymax;
		bb.south = ymin;
		bb.west = xmin;
		bb.east = xmax;
		return bb;
	}

	static double tile2lon(int x, int z) {
		return x / Math.pow(2.0, z) * 360.0 - 180;
	}

	static double tile2lat(int y, int z) {
		double n = Math.PI - (2.0 * Math.PI * y) / Math.pow(2.0, z);
		return Math.toDegrees(Math.atan(Math.sinh(n)));
	}
}