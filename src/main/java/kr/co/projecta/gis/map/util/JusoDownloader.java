package kr.co.projecta.gis.map.util;

import gnu.getopt.Getopt;
import kr.co.projecta.matching.log.Plogger;

public class JusoDownloader {

	public static void main(String[] args) {		
		Plogger log = Plogger.getLogger(JusoDB.class);
		
		int c;
		String downloadpath = null;
		String scriptpath = null;
		
		Getopt g = new Getopt("JusoDownloader", args, "d:p:");
		while ((c = g.getopt()) != -1) {
			switch (c) {
			case 'd':
				downloadpath = g.getOptarg();
				break;
			case 'p':
				scriptpath = g.getOptarg();
				break;
			default:
				System.err.println("Usage: JusoDownloader -d download path -p script path");
				System.exit(1);
			}
		}
		
		if (downloadpath == null || scriptpath == null) {
			System.err.println("Usage: JusoDownloader -d download path -p script path");
			System.exit(1);
		}
		
		System.out.println("download path: "+downloadpath);
		System.out.println("script path: "+scriptpath);
		
		JusoDB jusoDB = new JusoDB(downloadpath, scriptpath);
		try {
			jusoDB.download();			
			jusoDB.extract();			
			jusoDB.importDB("localhost", "projecta", "kinow", "830421", "juso");
			
		} catch (Exception e) {
			log.e(e.getMessage());
		}
	}
}
