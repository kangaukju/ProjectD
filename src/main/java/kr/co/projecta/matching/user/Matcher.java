package kr.co.projecta.matching.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.json.simple.JSONObject;

import kr.co.projecta.gis.map.SimilarityTable;
import kr.co.projecta.matching.context.ContextDatabase;
import kr.co.projecta.matching.exception.NeedNotMatchException;
import kr.co.projecta.matching.log.Plogger;
import kr.co.projecta.matching.match.MatchResult;
import kr.co.projecta.matching.match.MatchResultScore;
import kr.co.projecta.matching.util.Strings;
import kr.co.projecta.matching.util.Times;

public abstract class Matcher {
	Plogger log = Plogger.getLogger(this.getClass());
	
	// Matching Factors
	MatchStatus matchStatus = MatchStatus.INCOMPLETION;
	Gender gender; // 성별
	WorkQtime workQtime; // 업무가능 시간
	WorkMday workMday; // 업무가능 요일
	Date workDate; // 업무날짜
	Nation nation; // 국적
	WorkAbility workAbility; // 가능업무
	List<Region> regions = new ArrayList<Region>(3); // 업무가능 지역
	Date birth; // 생년월일
	int workTime; // 근무 시간
	Date registerDate;
	Region location; // 업주 주소
	
	abstract public int hashCode();
	
	abstract public boolean equals(Object obj);
	
	abstract protected JSONObject makeJSON();
	
	public JSONObject toJSON() {
		JSONObject o = makeJSON();
		if (o == null) {
			o = new JSONObject();
		}
		Strings.setJSON(o, "matchStatus", matchStatus);
		Strings.setJSON(o, "gender", gender);
		Strings.setJSON(o, "workQtime", workQtime);
		Strings.setJSON(o, "workMday", workMday);
		Strings.setJSONDateYYYYMMDDHH(o, "workDate", workDate);
		Strings.setJSON(o, "nation", nation);
		Strings.setJSON(o, "workAbility", workAbility);
		Strings.setJSON(o, "regions", regions);
		Strings.setJSONDateYYYYMMDD(o, "birth", birth);
		Strings.setJSON(o, "workTime", workTime);
		Strings.setJSONDateYYYYMMDD(o, "registerDate", registerDate);
		Strings.setJSON(o, "location", location);
		return o;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(
				this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	/*
	@Override
	public boolean equals(Object obj) {
		Matcher matcher = (Matcher) obj;
		return new EqualsBuilder().
				appendSuper(super.equals(obj)).
				append(factor1, matcher.factor1).
				append(factor2, matcher.factor2).
				append(factor3, matcher.factor3).
				isEquals();
	}
	*/

	static final double MAX_MATCH_SCORE = 100;
	/////////////////////////////////////////////////////////////////////////////////
	//
	// Match 알고리즘 함수 구현
	//
	/////////////////////////////////////////////////////////////////////////////////
	public double scoreAllOrNothing(boolean match) {
		return (match ? MAX_MATCH_SCORE : 0);
	}
	public double scoreWorkQtime(Matcher matcher) throws NeedNotMatchException {
		if (workDate != null && workTime != 0 && matcher.workQtime != null) {
			WorkQtime myWorkQtime = WorkQtime.valueOf(workDate, workTime);
			return matcher.workQtime.howMatchPercent(myWorkQtime);
		}
		else
		if (workQtime != null && matcher.workDate != null && matcher.workTime != 0) {
			WorkQtime youWorkQtime = WorkQtime.valueOf(matcher.workDate, matcher.workTime);
			return workQtime.howMatchPercent(youWorkQtime);
		}
		throw new NeedNotMatchException();
	}
	public double scoreWorkMday(Matcher matcher) throws NeedNotMatchException {
		if (workDate != null && matcher.workMday != null) {
			MdayBit mBit = Times.getMday(workDate);
			WorkMday mday = new WorkMday(mBit.intValue());
			return mday.howMatchPercent(matcher.workMday);
		}
		else
		if (workMday != null && matcher.workDate != null) {
			MdayBit mBit = Times.getMday(matcher.workDate);
			WorkMday mday = new WorkMday(mBit.intValue());
			return mday.howMatchPercent(workMday);
		}
		throw new NeedNotMatchException();
	}
	public double scoreRegionAndLocation(Matcher matcher) throws NeedNotMatchException {
		ContextDatabase contextDatabase = ContextDatabase.getInstance();
		SimilarityTable similarityTable = contextDatabase.getSimilarityTableSeoul();
		double max = 0;
		
		if (regions.size() > 0 && matcher.location != null) {
			for (Region region : regions) {
				int from = region.getSigunguId();
				int to = matcher.location.getSigunguId();
				double percent = similarityTable.getDistancePercent(from, to);
				if (percent > max) {
					max = percent;
				}
			}
			return max;
		}
		else
		if (location != null && matcher.regions.size() > 0) {
			for (Region region : matcher.regions) {
				int from = region.getSigunguId();
				int to = location.getSigunguId();
				double percent = similarityTable.getDistancePercent(from, to);
				if (percent > max) {
					max = percent;
				}
			}
			return max;
		}
		throw new NeedNotMatchException();
		
		/*
		if(regions != null && matcher.regions != null) {
			ContextDatabase contextDatabase = ContextDatabase.getInstance();
			SimilarityTable similarityTable = contextDatabase.getSimilarityTableSeoul();
			double percentTotal = 0;
			for (Region region1 : regions) {
				double max = 0;
				for (Region region2 : matcher.regions) {
					int from = region1.getSigunguId();
					int to   = region2.getSigunguId();
					double percent = similarityTable.getDistancePercent(from, to);
					
					if (percent > max) max = percent;
//					log.d(region1+" <-> "+region2+" = "+percent);
				}
//				log.d(region1+": max = "+max);
				percentTotal += max;
			}
			double regionPercent = (percentTotal/(regions.size()*100)) * 100;
			result.addScore("regions", regionPercent);
		}
		 */
	}
	public MatchResult matchResult(Matcher matcher) {
		MatchResultScore result = new MatchResultScore(MAX_MATCH_SCORE);
		double score = 0;
		
		// TODO: 연령대 - 조건에 넣을지 아직 미지수
		if (birth != null && matcher.birth != null) {
			
		}
		
		// 성별
		if (gender != null && matcher.gender != null) {
			score = (gender == matcher.gender) ? MAX_MATCH_SCORE : 0;
			result.addScore("gender", score);
		}
		
		// XXX: 업무시간 - 업무시간과 근무시간이 구직자가 선택한 업무시간과의 매칭률 계산
		try {
			score = scoreWorkQtime(matcher);
			result.addScore("workQtime", score);
		} catch (NeedNotMatchException e) { }
		
		// XXX: 업무요일 - 구직자가 설정한 요일(들)과 배정일이 매칭되어야한다.
		try {
			score = scoreWorkMday(matcher);
			result.addScore("workMday", score);
		} catch (NeedNotMatchException e) { }
		
		// 국적
		if (nation != null && matcher.nation != null) {
			score = (nation == matcher.nation) ? MAX_MATCH_SCORE : 0;
			result.addScore("nation", score);
		}
		
		// 업무
		if (workAbility != null && matcher.workAbility != null) {
			score = (workAbility == matcher.workAbility) ? MAX_MATCH_SCORE : 0;
			result.addScore("workAbility", score);
		}
		
		// XXX: 업무지역
		try {
			score = scoreRegionAndLocation(matcher);
			result.addScore("regions", score);
		} catch (NeedNotMatchException e) { }
		
		return result;
	}
	
	public Matcher() { }
	
	public Date getWorkDate() {
		return workDate;
	}
	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public WorkQtime getWorkQtime() {
		return workQtime;
	}
	public void setWorkQtime(String value) {
		this.workQtime = WorkQtime.valueOf(value);
	}
	public WorkMday getWorkMday() {
		return workMday;
	}
	public void setWorkMday(String value) {
		this.workMday = WorkMday.valueOf(value);
	}
	public Nation getNation() {
		return nation;
	}
	public void setNation(Nation nation) {
		this.nation = nation;
	}
	public WorkAbility getWorkAbility() {
		return workAbility;
	}
	public void setWorkAbility(WorkAbility workAbility) {
		this.workAbility = workAbility;
	}
	public Region getRegion1() {
		return regions.get(0);
	}
	public Region getRegion2() {
		return regions.get(1);
	}
	public Region getRegion3() {
		return regions.get(2);
	}
	public void setRegion(int index, int regionId) {
		ContextDatabase contextDatabase = ContextDatabase.getInstance();
		Region region = contextDatabase.getJusoSeoulMap().get(regionId);
		if (region != null) {
			regions.add(index, new Region(region));
		} else {
			region = contextDatabase.getJusoAllMap().get(regionId);
			if (region != null) {
				regions.add(index, new Region(region));
			} else {
				region = new Region();
				region.setId(regionId);
				regions.add(index, region);
			}
		}
	}
	public void setRegion1(int regionId) {
		setRegion(0, regionId);
	}
	public void setRegion2(int regionId) {
		setRegion(1, regionId);
	}
	public void setRegion3(int regionId) {
		setRegion(2, regionId);
	}
	public List<Region> getRegions() {
		return regions;
	}
	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public MatchStatus getMatchStatus() {
		return matchStatus;
	}
	public void setMatchStatus(MatchStatus matchStatus) {
		this.matchStatus = matchStatus;
	}
	public Region getLocation() {
		return location;
	}
	public void setLocation(int regionId) {
		ContextDatabase contextDatabase = ContextDatabase.getInstance();
		Region region = contextDatabase.getJusoSeoulMap().get(regionId);
		if (region != null) {
			location = new Region(region);
		} else {
			region = contextDatabase.getJusoAllMap().get(regionId);
			if (region != null) {
				location = new Region(region);
			} else {
				location = new Region();
				location.setId(regionId);
			}
		}
	}
	public int getWorkTime() {
		return workTime;
	}
	public void setWorkTime(int workTime) {
		this.workTime = workTime;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
}
