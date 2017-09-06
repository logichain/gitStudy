package org.mds.statistics.bean;

import java.text.NumberFormat;

public class StatisticsData {
	private String title;
	private Integer count;
	private double percent;
	
	public StatisticsData(String title,Integer count)
	{
		this.title = title;
		this.count = count;
	}
	
	public StatisticsData(String title,Integer count,double percent)
	{
		this.title = title;
		this.count = count;
		this.percent = percent;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
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
}
