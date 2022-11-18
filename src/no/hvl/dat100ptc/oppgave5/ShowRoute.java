package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;
	
	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		showRouteMap(MARGIN + MAPYSIZE);
		
		showStatistics();
	}

	// antall x-pixels per lengdegrad
	public double xstep() {

		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));

		double xstep = MAPXSIZE / (Math.abs(maxlon - minlon));
		
		return xstep;
	}

	// antall y-pixels per breddegrad
	public double ystep() {
	
		double ystep;
		
		// TODO - START
		
		double maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		
		ystep = MAPYSIZE / (Math.abs(maxlat - minlat));
		
		return ystep;

		// TODO - SLUTT
	}

	public void showRouteMap(int ybase) {

		// TODO - START
		
		double x = MARGIN + gpspoints[0].getLongitude();
		
		double y = ybase/4 - gpspoints[0].getLatitude();
		
		double x2,y2;
		
		for (int i = 1; i < gpspoints.length; i++) {
			
			x2 = x + xstep()*(gpspoints[i].getLongitude()-gpspoints[i-1].getLongitude());
			
			y2 = y - ystep()*(gpspoints[i].getLatitude()-gpspoints[i-1].getLatitude());
			
			setColor(0,255,0);
			drawCircle((int) x, (int) y, 2);
			drawLine((int) x, (int) y, (int) x2, (int) y2);

			x = x2;
			y = y2;
		}
		
		setColor(0,0,255);
		fillCircle((int) x, (int) y, 4);
		
		// TODO - SLUTT
	}

	public void showStatistics() {

		int TEXTDISTANCE = 20;

		setColor(0,0,0);
		setFont("Courier",12);
		
		// TODO - START
		
		String[] stats = {
				String.format("Total time     :%11s", GPSUtils.formatTime(gpscomputer.totalTime())),
				String.format("Total distance :%11.2f km", gpscomputer.totalDistance()/1000),
				String.format("Total elevation:%11.2f m", gpscomputer.totalElevation()),
				String.format("Max speed      :%11.2f km/t", gpscomputer.maxSpeed()),
				String.format("Average speed  :%11.2f km/t", gpscomputer.averageSpeed()),
				String.format("Energy         :%11.2f kcal", gpscomputer.totalKcal(80))
		};
		
		int yVal = TEXTDISTANCE;
		for (int i = 0; i < stats.length; i++) {
			drawString(stats[i], MARGIN, yVal);
			yVal += TEXTDISTANCE;
		}
		
		// TODO - SLUTT;
	}
}