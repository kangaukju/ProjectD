package kr.co.projecta.matching.dao;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import kr.co.projecta.matching.log.Plogger;

@Component("MatchResultDAO")
public class MatchResultDAO implements DAO<File> {
	Plogger log = Plogger.getLogger(this.getClass());
	
	@Deprecated
	public Map selectMap(Map<String, Object> params) {
		return null;
	}

	public List<File> selectList(Map<String, Object> params) {
		String database = (String) params.get("database");
		String filter = (String) params.get("filter");
		Long lstart = (Long) params.get("start");		
		Long lend = (Long) params.get("end");
		int start = lstart.intValue();
		int end = lend.intValue();
		FilenameFilter filenameFilter = null;
		File [] resultFiles = null;
		File [] pages = null;
		
		if (database == null) {
			return new ArrayList<File>();
		}
		
		System.out.println(database);
		
		File resource = new File(database);
		
		// 파일 검색(필터) 처리
		if (filter != null) {
			filenameFilter = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return Pattern.matches(filter, name);
				}
			};
		}
		// 서브 파일 목록 조회
		if (filenameFilter != null) {			
			resultFiles = resource.listFiles(filenameFilter);
		} else {
			resultFiles = resource.listFiles();
		}
		if (resultFiles == null || resultFiles.length == 0) {
			return new ArrayList<File>();
		}
		
		Arrays.sort(resultFiles, new Comparator<File>() {
			public int compare(File f1, File f2) {
				return f2.compareTo(f1);
			}
		});		
		pages = resultFiles;
/*		
		System.out.println(start+" ~ "+end);
		for (File f : pages) {
			System.out.println("--> "+f.getName());
		}
*/		
		// paging 처리
		int size = end - start + 1;
		if (pages.length < size) {
			size = pages.length;
		}
//			System.out.println("size: "+size);
		pages = new File[size];
		System.arraycopy(resultFiles, start, pages, 0, size);
		
		List<File> result = new ArrayList<File>(Arrays.asList(pages));
		return result;
		
	}

	@Deprecated
	public File selectOne(String key, String value) {
		return null;
	}

	public long selectCount(Map<String, Object> params) {
		return this.selectList(params).size();
	}
	
	public static void main(String [] args) {
		MatchResultDAO dao = new MatchResultDAO();
		String dir = "/media/kinow/1TB_D/workspace/pa/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/pa/matching/kinow/";
		Map<String, Object> params = new HashMap<>();
		params.put("database", dir);
		params.put("start", "2");
		params.put("end", "5");
		params.put("filter", ".*31.*");
		
		List<File> files = dao.selectList(params);
		
		System.out.println(files.size());
		for (File f : files) {
			System.out.println(f.getName());
		}
	}
	
}
