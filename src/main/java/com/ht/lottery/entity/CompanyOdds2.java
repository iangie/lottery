package com.ht.lottery.entity;


public class CompanyOdds2 {
	private String companyName;
	
	private String matchInfoId;
	
	private String matchName;
	
	private int result;
	
	private int concede;
	
	private double oddsWin1;
	
	private double oddsDraw1;
	
	private double oddsLose1;
	
	private double oddsWin2;
	
	private double oddsDraw2;
	
	private double oddsLose2;
	
	private double o1 = 1.8;
	
	private double o2 = 1;
	
	private int flag;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getMatchInfoId() {
		return matchInfoId;
	}

	public void setMatchInfoId(String matchInfoId) {
		this.matchInfoId = matchInfoId;
	}

	public String getMatchName() {
		return matchName;
	}
	
	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}
	
	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getConcede() {
		return concede;
	}

	public void setConcede(int concede) {
		this.concede = concede;
	}

	public double getOddsWin1() {
		return oddsWin1;
	}

	public void setOddsWin1(double oddsWin1) {
		this.oddsWin1 = oddsWin1;
	}

	public double getOddsDraw1() {
		return oddsDraw1;
	}

	public void setOddsDraw1(double oddsDraw1) {
		this.oddsDraw1 = oddsDraw1;
	}

	public double getOddsLose1() {
		return oddsLose1;
	}

	public void setOddsLose1(double oddsLose1) {
		this.oddsLose1 = oddsLose1;
	}

	public double getOddsWin2() {
		return oddsWin2;
	}

	public void setOddsWin2(double oddsWin2) {
		this.oddsWin2 = oddsWin2;
	}

	public double getOddsDraw2() {
		return oddsDraw2;
	}

	public void setOddsDraw2(double oddsDraw2) {
		this.oddsDraw2 = oddsDraw2;
	}

	public double getOddsLose2() {
		return oddsLose2;
	}

	public void setOddsLose2(double oddsLose2) {
		this.oddsLose2 = oddsLose2;
	}
	
	public int getR() {
		double min = Math.min(Math.min(oddsWin1, oddsDraw1), oddsLose1);
		if(flag == 3 && min == oddsWin1 && result == 3 && oddsWin1 <= o1 && oddsWin1 >= o2)
			return 1;
		if(flag == 1 && min == oddsDraw1 && result == 1 && oddsDraw1 <= o1 && oddsDraw1 >= o2)
			return 1;
		if(flag == 0 && min == oddsLose1 && result == 0 && oddsLose1 <= o1 && oddsLose1 >= o2)
			return 1;
		return 0;
	}
	
	public int getR_() {
		double min = Math.min(Math.min(oddsWin1, oddsDraw1), oddsLose1);
		if(flag == 3 && min == oddsWin1 && oddsWin1 <= o1 && oddsWin1 >= o2)
			return 1;
		if(flag == 1 && min == oddsDraw1 && oddsDraw1 <= o1 && oddsDraw1 >= o2)
			return 1;
		if(flag == 0 && min == oddsLose1 && oddsLose1 <= o1 && oddsLose1 >= o2)
			return 1;
		return 0;
	}
	
	public int getR2() {
		double min = Math.min(Math.min(oddsWin2, oddsDraw2), oddsLose2);
		if(flag == 3 && min == oddsWin2 && result == 3 && oddsWin2 <= o1 && oddsWin2 >= o2)
			return 1;
		if(flag == 1 && min == oddsDraw2 && result == 1 && oddsDraw2 <= o1 && oddsDraw2 >= o2)
			return 1;
		if(flag == 0 && min == oddsLose2 && result == 0 && oddsLose2 <= o1 && oddsLose2 >= o2)
			return 1;
		return 0;
	}
	
	public int getR2_() {
		double min = Math.min(Math.min(oddsWin2, oddsDraw2), oddsLose2);
		if(flag == 3 && min == oddsWin2 && oddsWin2 <= o1 && oddsWin2 >= o2)
			return 1;
		if(flag == 1 && min == oddsDraw2 && oddsDraw2 <= o1 && oddsDraw2 >= o2)
			return 1;
		if(flag == 0 && min == oddsLose2 && oddsLose2 <= o1 && oddsLose2 >= o2)
			return 1;
		return 0;
	}
	
	public int getR3() {
		double min = Math.min(Math.min(oddsWin2, oddsDraw2), oddsLose2);
		if(flag == 3 && min == oddsWin2 && result == 3 && oddsWin2 < oddsWin1)
			return 1;
		if(flag == 1 && min == oddsDraw2 && result == 1 && oddsDraw2 < oddsDraw1)
			return 1;
		if(flag == 0 && min == oddsLose2 && result == 0 && oddsLose2 < oddsLose1)
			return 1;
		return 0;
	}
	public int getR3_() {
		double min = Math.min(Math.min(oddsWin2, oddsDraw2), oddsLose2);
		if(flag == 3 && min == oddsWin2 && oddsWin2 < oddsWin1)
			return 1;
		if(flag == 1 && min == oddsDraw2 && oddsDraw2 < oddsDraw1)
			return 1;
		if(flag == 0 && min == oddsLose2 && oddsLose2 < oddsLose1)
			return 1;
		return 0;
	}
	
	public int getR4() {
		double min = Math.min(Math.min(oddsWin2, oddsDraw2), oddsLose2);
		if(flag == 3 && min == oddsWin2 && result == 3 && oddsWin2 > oddsWin1)
			return 1;
		if(flag == 1 && min == oddsDraw2 && result == 1 && oddsDraw2 > oddsDraw1)
			return 1;
		if(flag == 0 && min == oddsLose2 && result == 0 && oddsLose2 > oddsLose1)
			return 1;
		return 0;
	}
	public int getR4_() {
		double min = Math.min(Math.min(oddsWin2, oddsDraw2), oddsLose2);
		if(flag == 3 && min == oddsWin2 && oddsWin2 > oddsWin1)
			return 1;
		if(flag == 1 && min == oddsDraw2 && oddsDraw2 > oddsDraw1)
			return 1;
		if(flag == 0 && min == oddsLose2 && oddsLose2 > oddsLose1)
			return 1;
		return 0;
	}

	public int getR5() {
		double min = Math.min(Math.min(oddsWin2, oddsDraw2), oddsLose2);
		if(flag == 3 && min == oddsWin2 && result == 3 && oddsWin2 < oddsWin1 && oddsWin1 <= o1 && oddsWin2 >= o2)
			return 1;
		if(flag == 1 && min == oddsDraw2 && result == 1 && oddsDraw2 < oddsDraw1 && oddsDraw1 <= o1 && oddsDraw2 >= o2)
			return 1;
		if(flag == 0 && min == oddsLose2 && result == 0 && oddsLose2 < oddsLose1 && oddsLose1 <= o1 && oddsLose2 >= o2)
			return 1;
		return 0;
	}
	public int getR5_() {
		double min = Math.min(Math.min(oddsWin2, oddsDraw2), oddsLose2);
		if(flag == 3 && min == oddsWin2 && oddsWin2 < oddsWin1 && oddsWin1 <= o1 && oddsWin2 >= o2)
			return 1;
		if(flag == 1 && min == oddsDraw2 && oddsDraw2 < oddsDraw1 && oddsDraw1 <= o1 && oddsDraw2 >= o2)
			return 1;
		if(flag == 0 && min == oddsLose2 && oddsLose2 < oddsLose1 && oddsLose1 <= o1 && oddsDraw2 >= o2)
			return 1;
		return 0;
	}
	
	public int getR6() {
		double min = Math.min(Math.min(oddsWin2, oddsDraw2), oddsLose2);
		if(flag == 3 && min == oddsWin2 && result == 3 && oddsWin2 > oddsWin1 && oddsWin2 <= o1 && oddsWin1 >= o2)
			return 1;
		if(flag == 1 && min == oddsDraw2 && result == 1 && oddsDraw2 > oddsDraw1 && oddsDraw2 <= o1 && oddsDraw1 >= o2)
			return 1;
		if(flag == 0 && min == oddsLose2 && result == 0 && oddsLose2 > oddsLose1 && oddsLose2 <= o1 && oddsLose1 >= o2)
			return 1;
		return 0;
	}
	public int getR6_() {
		double min = Math.min(Math.min(oddsWin2, oddsDraw2), oddsLose2);
		if(flag == 3 && min == oddsWin2 && oddsWin2 > oddsWin1 && oddsWin2 <= o1 && oddsWin1 >= o2)
			return 1;
		if(flag == 1 && min == oddsDraw2 && oddsDraw2 > oddsDraw1 && oddsDraw2 <= o1 && oddsDraw1 >= o2)
			return 1;
		if(flag == 0 && min == oddsLose2 && oddsLose2 > oddsLose1 && oddsLose2 <= o1 && oddsLose1 >= o2)
			return 1;
		return 0;
	}
	
	public void setO1(double o1) {
		this.o1 = o1;
	}
	
	public void setO2(double o2) {
		this.o2 = o2;
	}
	
	public void setFlag(int flag) {
		this.flag = flag;
	}
}
