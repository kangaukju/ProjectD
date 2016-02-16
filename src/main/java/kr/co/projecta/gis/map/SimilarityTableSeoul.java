package kr.co.projecta.gis.map;

public class SimilarityTableSeoul extends SimilarityTable {

	public SimilarityTableSeoul(String data, String delim) 
			throws IllegalAccessException {
		super(data, delim);
	}
	public SimilarityTableSeoul(int startNominal, String[] datas) 
			throws IllegalAccessException {
		super(startNominal, datas);
	}
	public SimilarityTableSeoul(String[] datas) 
			throws IllegalAccessException {
		super(datas);
	}
	@Override
	void initilize() {
		inputDistance("도봉구", "노원구", 1);
		inputDistance("도봉구", "강북구", 1);
		
		inputDistance("노원구", "도봉구", 1);
		inputDistance("노원구", "강북구", 1);
		inputDistance("노원구", "성북구", 1);
		inputDistance("노원구", "중랑구", 1);
		
		inputDistance("강북구", "도봉구", 1);
		inputDistance("강북구", "노원구", 1);
		inputDistance("강북구", "성북구", 1);
				
		inputDistance("성북구", "중랑구", 1);
		inputDistance("성북구", "노원구", 1);
		inputDistance("성북구", "강북구", 1);
		inputDistance("성북구", "은평구", 1);
		inputDistance("성북구", "종로구", 1);
		inputDistance("성북구", "동대문구", 1);
				
		inputDistance("종로구", "성북구", 1);
		inputDistance("종로구", "은평구", 1);
		inputDistance("종로구", "서대문구", 1);
		inputDistance("종로구", "중구", 1);
		inputDistance("종로구", "동대문구", 1);
		inputDistance("종로구", "성동구", 1);
				
		inputDistance("은평구", "성북구", 1);
		inputDistance("은평구", "종로구", 1);
		inputDistance("은평구", "서대문구", 1);
		inputDistance("은평구", "마포구", 1);
				
		inputDistance("서대문구", "종로구", 1);
		inputDistance("서대문구", "은평구", 1);
		inputDistance("서대문구", "마포구", 1);
		inputDistance("서대문구", "중구", 1);
		
		inputDistance("중구", "성동구", 1);
		inputDistance("중구", "동대문구", 1);
		inputDistance("중구", "종로구", 1);
		inputDistance("중구", "서대문구", 1);
		inputDistance("중구", "마포구", 1);
		inputDistance("중구", "용산구", 1);
				
		inputDistance("동대문구", "중랑구", 1);
		inputDistance("동대문구", "성북구", 1);
		inputDistance("동대문구", "종로구", 1);
		inputDistance("동대문구", "중구", 1);
		inputDistance("동대문구", "성동구", 1);
		inputDistance("동대문구", "광진구", 1);
		
		inputDistance("중랑구", "노원구", 1);
		inputDistance("중랑구", "성북구", 1);
		inputDistance("중랑구", "동대문구", 1);
		inputDistance("중랑구", "광진구", 1);
		
		inputDistance("광진구", "성동구", 1);
		inputDistance("광진구", "동대문구", 1);
		inputDistance("광진구", "중랑구", 1);
		inputDistance("광진구", "강동구", 2);
		inputDistance("광진구", "송파구", 2);
		inputDistance("광진구", "강남구", 2);
				
		inputDistance("성동구", "광진구", 1);
		inputDistance("성동구", "동대문구", 1);
		inputDistance("성동구", "종로구", 1);
		inputDistance("성동구", "중구", 1);
		inputDistance("성동구", "용산구", 1);
		inputDistance("성동구", "서초구", 2);
		inputDistance("성동구", "강남구", 2);
		inputDistance("성동구", "송파구", 2);
		
		inputDistance("용산구", "성동구", 1);
		inputDistance("용산구", "중구", 1);
		inputDistance("용산구", "마포구", 1);
		inputDistance("용산구", "영등포구", 2);
		inputDistance("용산구", "동작구", 2);
		inputDistance("용산구", "서초구", 2);
		inputDistance("용산구", "강남구", 2);
				
		inputDistance("마포구", "용산구", 1);
		inputDistance("마포구", "중구", 1);
		inputDistance("마포구", "은평구", 1);
		inputDistance("마포구", "서대문구", 1);
		inputDistance("마포구", "강서구", 2);
		inputDistance("마포구", "영등포구", 2);
				
		inputDistance("강서구", "양천구", 1);
		inputDistance("강서구", "마포구", 2);
		
		inputDistance("양천구", "강서구", 1);
		inputDistance("양천구", "영등포구", 1);
		inputDistance("양천구", "구로구", 1);
		inputDistance("양천구", "금천구", 1);
		
		inputDistance("구로구", "양천구", 1);
		inputDistance("구로구", "금천구", 1);
		inputDistance("구로구", "영등포구", 1);
		
		inputDistance("영등포구", "동작구", 1);
		inputDistance("영등포구", "관악구", 1);
		inputDistance("영등포구", "금천구", 1);
		inputDistance("영등포구", "구로구", 1);
		inputDistance("영등포구", "양천구", 1);
		inputDistance("영등포구", "마포구", 2);
		inputDistance("영등포구", "용산구", 2);
				
		inputDistance("동작구", "서초구", 1);
		inputDistance("동작구", "관악구", 1);
		inputDistance("동작구", "금천구", 1);
		inputDistance("동작구", "영등포구", 1);
		inputDistance("동작구", "용산구", 2);
				
		inputDistance("서초구", "강남구", 1);
		inputDistance("서초구", "관악구", 1);
		inputDistance("서초구", "동작구", 1);
		inputDistance("서초구", "용산구", 2);
		inputDistance("서초구", "성동구", 2);
		
		inputDistance("강남구", "송파구", 1);
		inputDistance("강남구", "서초구", 1);
		inputDistance("강남구", "용산구", 2);
		inputDistance("강남구", "성동구", 2);
		inputDistance("강남구", "광진구", 2);
		
		inputDistance("송파구", "강동구", 1);
		inputDistance("송파구", "강남구", 1);
		inputDistance("송파구", "성동구", 2);
		inputDistance("송파구", "광진구", 2);
		
		inputDistance("강동구", "송파구", 1);
		inputDistance("강동구", "광진구", 2);
		
		inputDistance("금천구", "구로구", 1);
		inputDistance("금천구", "양천구", 1);
		inputDistance("금천구", "영등포구", 1);
		inputDistance("금천구", "동작구", 1);
		inputDistance("금천구", "관악구", 1);
		
		inputDistance("관악구", "서초구", 1);
		inputDistance("관악구", "동작구", 1);
		inputDistance("관악구", "영등포구", 1);
		inputDistance("관악구", "금천구", 1);
	}

	public static void main(String [] args) throws Exception {
		String data = 
				"도봉구 노원구 강북구 성북구 중랑구 은평구 종로구 서대문구 중구 "+
				"동대문구 마포구 용산구 성동구 광진구 강서구 양천구 구로구 영등포구 "+
				"금천구 동작구 관악구 서초구 강남구 송파구 강동구";
		
		SimilarityTableSeoul t = new SimilarityTableSeoul(data, "\t ");
		t.exportCSV("/home/kinow/ss.csv");
		t.showMatrix();
		
		/*
		 * 도봉구 노원구 관악구
		 * 금천구 중랑구 도봉구
		 */
		String [] r1s = new String[]{"도봉구", "관악구"};
		String [] r2s = new String[]{"도봉구", "노원구", "강북구"};
		
		double matchPercentTotal = 0;
		int count = 0;
		for (String r1 : r1s) {
			double max = 0;
			
			for (String r2 : r2s) {
				int dist = t.getDistance(r1, r2);
				double distPercent = t.getDistancePercent(r1, r2);
				if (distPercent > max) {
					max = distPercent;
				}
				System.out.println(r1 + "<->" + r2 + " = ("+ dist+ ")"+distPercent);
			}
			System.out.println("max : "+max);
			count++;
			matchPercentTotal += max;
		}
		System.out.println("matchPercentTotal: "+matchPercentTotal);
		System.out.println("count: "+count);
		System.out.println("percent: "+(matchPercentTotal/(count*100)) * 100);
	}
}
