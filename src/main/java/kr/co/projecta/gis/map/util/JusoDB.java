package kr.co.projecta.gis.map.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kr.co.projecta.matching.log.Plogger;

public class JusoDB {
	
	boolean ignoreCity = true;
	
	Plogger log = Plogger.getLogger(this.getClass());
	
	static private final String JUSO_TEMPFILE = ".complete.juso";
	static private final String DOWNLOAD_SHELLSCRIPT = "download.sh";
	static private final String EXTRACT_CITY_SHELLSCRIPT = "extract_city.sh";
	static private final String EXTRACT_TOWN_SHELLSCRIPT = "extract_town.sh";
	static private final String SIDONAME_SHELLSCRIPT = "sidoname.sh";
	static private final String JUSO_ZIP_FILENAME = "juso.zip";
	static private final String JUSO_FILENAME_PREFIX = "jibun_";
	static private final String JUSO_OUTPUT_DIR = "output";
	
	Map<String, List<Juso>> jusoMap = new HashMap<String, List<Juso>>();
	
	
	/*
	public static String getClassPath(Class<?> clazz) {
		URL resource = clazz.getClassLoader().getResource("");
		Package pack = clazz.getPackage();
		return resource.getPath()+pack.getName().replace('.', '/');
	}
	*/
	
	String downloadpath;
	String scriptpath;
	
	public JusoDB(String downloadpath, String scriptpath) {
		this.downloadpath = downloadpath;
		this.scriptpath = scriptpath;
	}
	
	public static boolean rm(String pathname) {
		File path = new File(pathname);
		if (!path.exists()) {
			return false;
		}

		File[] files = path.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					rm(file.getAbsolutePath());
				} else {
					file.delete();
				}
			}
		}
		return path.delete();
	}
	
	public static boolean mkdir(String dirpath) {
		File dir = new File(dirpath);
		if (dir.exists()) {
			return true;
		}
		return dir.mkdirs();
	}
	
	public void download() 
			throws Exception 
	{
		String jusoZipFile = downloadpath+"/"+JUSO_ZIP_FILENAME;
		log.i("juso zip file: "+jusoZipFile);
		
		if (!mkdir(downloadpath)) {
			throw new IOException(
					"Could not create directory - "+downloadpath);
		}
				
		// download juso.zip
		log.i("run: "+String.format(
				"%s/%s %s",
				scriptpath,
				DOWNLOAD_SHELLSCRIPT,
				downloadpath));
		log.i("Start download");
		UnixCommand.executeWithPrint(String.format(
				"%s/%s %s",
				scriptpath,
				DOWNLOAD_SHELLSCRIPT,
				downloadpath));
		
		if (!new File(jusoZipFile).exists()) {
			throw new IOException(
					"Could not find "+JUSO_ZIP_FILENAME+" - "+jusoZipFile);
		}
		
		// unzip juso.zip
		UnixCommand.executeWithPrint(String.format(
				"unzip %s -d %s",
				jusoZipFile, downloadpath));
	}
	
	public boolean isCity(String file) 
			throws Exception 
	{
		if (ignoreCity) return true;
		
		String sidoName = UnixCommand.execute(
				String.format(
						"%s/%s %s", 
						scriptpath,
						SIDONAME_SHELLSCRIPT,
						file));
		return sidoName.replace("\n", "").endsWith("시");
	}
	
	public void write(String outputfile, List<Juso> jusoList)
            throws IOException 
    {
		File out = new File(outputfile);

		if (jusoList == null || jusoList.size() == 0) {
			throw new IOException("Empty juso list");
		}

		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(out));
			for (Juso juso : jusoList) {
				bw.write(juso.toExcel(',') + "\n");
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (bw != null)
				try { bw.close(); } catch (IOException e) { }
		}
    }
	
	public void showJuso() {
		int total = 0;
		Set<String> keys = jusoMap.keySet();
		List<Juso> jusolist;
		
		for (String key : keys) {
			jusolist = jusoMap.get(key);
			if (jusolist != null) {
				for (Juso juso : jusolist) {
					total++;
					log.i(juso);
				}
			}
		}
		log.i("show juso: total juso is " + total);
	}
	
	public void extract() throws Exception {
		
		int total = 0;
		String outputdir = downloadpath+"/"+JUSO_OUTPUT_DIR;
				
		if (!mkdir(downloadpath)) {
			throw new IOException(
					"Could not create directory - "+downloadpath);
		}
		
		File outputDir = new File(outputdir);
		if (outputDir.isDirectory()) {
			rm(outputDir.getAbsolutePath());
			log.i("Delete old output directory - "+outputDir.getAbsolutePath());
		}
		mkdir(outputDir.getAbsolutePath());
	
		File downloadDir = new File(downloadpath);
		for (File file : downloadDir.listFiles()) {
			if (!file.getName().startsWith(JUSO_FILENAME_PREFIX)) {
				continue;
			}
			
			boolean isCity = isCity(file.getAbsolutePath());
			
			UnixCommand.execute(String.format(
					"%s/%s %s %s",
					scriptpath,
					isCity ? EXTRACT_CITY_SHELLSCRIPT : EXTRACT_TOWN_SHELLSCRIPT, 
					file.getAbsolutePath(), 
					JUSO_TEMPFILE));
			
			
			if (!new File(JUSO_TEMPFILE).exists()) {
				throw new IOException(
						"Failed to make file - "+file.getAbsolutePath());
			}
			
			BufferedReader br = null;
			try {
				Juso juso;
				String line;
				List<Juso> jusoList = new ArrayList<Juso>();
				br = new BufferedReader(new FileReader(
						new File(JUSO_TEMPFILE)));
				
				while ((line = br.readLine()) != null) {
					if (isCity) {
						juso = JusoBase.jusoFactory(line, ',');
					} else {
						juso = JusoTown.jusoFactory(line, ',');
					}
					jusoList.add(juso);
				}
				
				if (jusoList.size() > 0) {
					String sidoName = jusoList.get(0).getSidoName();
					
					total += jusoList.size();
					
					jusoMap.put(sidoName, jusoList);
				
					write(outputdir+"/"+sidoName+".txt", jusoList);
				
					log.i("extract: "+sidoName+", count: "+jusoList.size());
				}
			} catch (IOException e) {
				throw e;
			} finally {
				rm(JUSO_TEMPFILE);
				if (br != null) 
					try { br.close(); } catch (IOException e) { };
			}
		}
		log.i("extract all, count: "+total);
	}
	
	private Connection getConnection(
			String host,
			String database, 
			String user, 
			String password) throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://"+host+":3306/"+database+
				"?useUnicode=true&characterEncoding=UTF8", 
				user, password);
		return connection;
	}
	
	public void importDB(
			String host,
			String database, 
			String user, 
			String password,
			String table)
	{
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = getConnection(host, database, user, password);
			
			// drop old table
			ps = connection.prepareStatement(
					"DROP TABLE IF EXISTS " + table);
			ps.executeUpdate();
			ps.close();
			log.i("drop table "+table);
			
			// create new table
			ps = connection.prepareStatement(
					ignoreCity ? 
							JusoBase.createTableSQL(table) : 
							JusoTown.createTableSQL(table));
			ps.executeUpdate();
			ps.close();
			log.i("create table "+table);
			log.i(ignoreCity ?
					JusoBase.createTableSQL(table) :
					JusoTown.createTableSQL(table));
			
			Set<String> keys = jusoMap.keySet();
			for (String key : keys) {
				List<Juso> jusoList = jusoMap.get(key);
				if (jusoList != null) {
					for (Juso juso : jusoList) {
						ps = connection.prepareStatement(juso.insertSQL(table));
						juso.insertPreparedStatement(ps);
						ps.executeUpdate();
					}
				}
				log.i("import DB: insert "+key+", count: "+jusoList.size());
			}
			
		} catch (Exception e) {
			log.e(e.getMessage());
		} finally {
			if (ps != null)
				try { ps.close(); } catch (SQLException e) {}
			if (connection != null)
				try { connection.close(); } catch (SQLException e) {}
		}
	}
}
