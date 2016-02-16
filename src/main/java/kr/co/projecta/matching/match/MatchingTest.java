package kr.co.projecta.matching.match;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kr.co.projecta.matching.dao.DAO;
import kr.co.projecta.matching.user.Matcher;
import kr.co.projecta.matching.user.Requirement;
import kr.co.projecta.matching.user.Seeker;

public class MatchingTest {
/*
	public static void main(String[] args) {
		
		DAO<Matcher> seekerDAO = new DAO<Matcher>() {
			int size = 5;
			int i = 0;
			@Override
			public List<Matcher> selectList(Map<String, Object> params) {
				List<Matcher> list = new ArrayList<>();
				for (int i=0; i<size; i++) {
					Seeker seeker = new Seeker();
					seeker.setId(i+"");
					seeker.setName("S"+i);
					list.add(seeker);
				}
				list.get(i++).setFactor(10, 44, 90);
				list.get(i++).setFactor(50, 20, 70);
				list.get(i++).setFactor(30, 21, 100);
				list.get(i++).setFactor(10, 70, 20);
				list.get(i++).setFactor(55, 24, 67);
				
				return list;
			}
			@Override
			public Set<Matcher> selectSet(Map<String, Object> params) {
				Set<Matcher> set = new HashSet<>();
				for (int i=0; i<size; i++) {
					Seeker seeker = new Seeker();
					seeker.setId(i+"");
					seeker.setName("S"+i);					
					switch (i) {
						case 0: seeker.setFactor(10, 44, 90); break;
						case 1: seeker.setFactor(50, 20, 70); break;
						case 2: seeker.setFactor(30, 21, 100); break;
						case 3: seeker.setFactor(10, 70, 20); break;
						case 4: seeker.setFactor(55, 24, 67); break;
					}
					set.add(seeker);
				}				
				return set;
			}
		};
		DAO<Matcher> requirementDAO = new DAO<Matcher>() {
			int size = 5;
			int i = 0;
			@Override
			public List<Matcher> selectList(Map<String, Object> params) {
				List<Matcher> list = new ArrayList<>();
				for (int i=0; i<size; i++) {
					Requirement requirement = new Requirement();
					requirement.setId(i+"");
					requirement.setName("R"+i);
					list.add(requirement);
				}
				list.get(i++).setFactor(10, 30, 100);
				list.get(i++).setFactor(60, 20, 30);
				list.get(i++).setFactor(34, 67, 22);
				list.get(i++).setFactor(83, 13, 5);
				list.get(i++).setFactor(34, 35, 21);
				return list;
			}
			@Override
			public Set<Matcher> selectSet(Map<String, Object> params) {
				Set<Matcher> set = new HashSet<>();
				for (int i=0; i<size; i++) {
					Requirement requirement = new Requirement();
					requirement.setId(i+"");
					requirement.setName("R"+i);
					switch (i) {
					case 0: requirement.setFactor(10, 30, 100); break;
					case 1: requirement.setFactor(60, 20, 30); break;
					case 2: requirement.setFactor(34, 67, 22); break;
					case 3: requirement.setFactor(83, 13, 5); break;
					case 4: requirement.setFactor(34, 35, 21); break;
					}
					set.add(requirement);
				}
				return set;
			}
		};
		
		MatchService matchService = new MatchService();
		matchService.setDoubleMatching(true);
		matchService.setRequirementDAO(requirementDAO);
		matchService.setSeekerDAO(seekerDAO);
		matchService.setMultiMatching(true);
		matchService.setTopLimit(5);
		matchService.autoMatchService();
		System.out.println(matchService.getTopLimit());
		
		
	}
*/	
}
