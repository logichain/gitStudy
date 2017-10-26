package org.mds.statistics.bean;

import java.text.NumberFormat;

public class StatisticsData {
	private String title;
	private Integer designCaseCount;
	private Integer testCaseCount;
	private Integer unpassCaseCount;
	private Integer NACaseCount;
	private double percent;
	
	public StatisticsData(String title,Integer designCaseCount,Integer testCaseCount,Integer unpassCaseCount,Integer NACaseCount)
	{
		this.title = title;
		this.designCaseCount = designCaseCount;
		this.testCaseCount = testCaseCount;
		this.unpassCaseCount = unpassCaseCount;
		this.setNACaseCount(NACaseCount);
	}
	
	public StatisticsData(String title,Integer designCaseCount)
	{
		this.title = title;
		this.designCaseCount = designCaseCount;
	}
	
		
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	} 

	public String getPercentStr() {
		NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumFractionDigits(2); //设置小数点保留几位
        
		return nf.format(percent);
	}

	public Integer getDesignCaseCount() {
		return designCaseCount;
	}

	public void setDesignCaseCount(Integer designCaseCount) {
		this.designCaseCount = designCaseCount;
	}

	public Integer getTestCaseCount() {
		return testCaseCount;
	}

	public void setTestCaseCount(Integer testCaseCount) {
		this.testCaseCount = testCaseCount;
	}

	public Integer getUnpassCaseCount() {
		return unpassCaseCount;
	}

	public void setUnpassCaseCount(Integer unpassCaseCount) {
		this.unpassCaseCount = unpassCaseCount;
	}

	public Integer getNACaseCount() {
		return NACaseCount;
	}

	public void setNACaseCount(Integer nACaseCount) {
		NACaseCount = nACaseCount;
	}


}
