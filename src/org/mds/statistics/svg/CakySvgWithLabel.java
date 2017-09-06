package org.mds.statistics.svg;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 
 * @author 风絮NO.1
 *
 */
public class CakySvgWithLabel {
	//定义不同的颜色
	static String[] colors ={"#f2e692", "#aa1111", 
							  "#799AE1", "#3e941b", 
							  "#66cc00", "#297110", 
							  "#d6a97b", "#82522b", 
							  "#aaaaff", "#1111aa", 
							  "#ff2222", "#ffaaaa"};
	
	static String initialize(double [] percents,String[]names){
		StringBuffer sfile = new StringBuffer();
		sfile.append("<?xml version='1.0' encoding='UTF-8'?>");
		sfile.append("\n");
		sfile.append("<svg xmlns:svg='http://www.w3.org/2000/svg'");
		sfile.append("\n");
        sfile.append("xmlns='http://www.w3.org/2000/svg'");
        sfile.append("\n");
        sfile.append("xmlns:xlink='http://www.w3.org/1999/xlink'");
        sfile.append("\n");
        sfile.append("xml:space='default'");
        sfile.append("\n");
        sfile.append("version='1.1'  width='100%' height='100%' viewBox='0 0 2024 570'>");
        sfile.append("\n");
        sfile.append("<defs></defs>");
        sfile.append("\n");
        sfile.append("<g stroke-width='1' stroke='#FFFFFF' transform='matrix(1,0,0,1,16.384,-9.83)' xmlns='http://www.w3.org/2000/svg'>");
        sfile.append("\n");
        //循环创造path标签.
        String path =creatPath(502, 300, 300, percents,names);//中心点式503,300.
        sfile.append(path);
        sfile.append("</g>");
        sfile.append("\n");
        sfile.append("</svg>");
		return sfile.toString();
	}
	/**
	 * 
	 * @param x0 中心点横坐标
	 * @param y0 中心点纵坐标
	 * @param r  半径
	 * @param percents  百分比数组
	 * @param names 显示颜色代表的名称
	 * @return
	 */ 
    public static String creatPath(double x0,double y0,double r,double[]percents,String[]names){
    	StringBuffer sfile =new StringBuffer();
    	double x1=0; //新扇形的x坐标
    	double y1=0; //新扇形的y坐标
    	double middleX=0;  //文本显示的坐标,包括竖线显示的坐标
    	double middleY=0;
    	double radian =0; //弧度
    	double textRadian=0; //文本显示位置度弧度
    	double k=0;
    	int N=10;
    	for(int i=0;i<percents.length;i++){
    		if(i==0){
    		   radian =getRadian(percents[0]);
    		   textRadian=radian/2;
    		   x1 = (x0+getCos(radian)*r);
    		   y1 = (y0-getSin(radian)*r);
    		   middleX=(x0+getCos(textRadian)*r);
    		   middleY=(y0-getSin(textRadian)*r);
     		   double percent = Math.round(percents[0]*100)/100.0;//获得精确到两位小数点的坐标.
     		   k=Math.abs((middleY-y0)/(middleX-x0));//获得扇形终点的坐标,与中心点连成的直线的斜率.(取正值)
     		   double sita= Math.atan(k);//求斜角
     		   double lineLen=50;
   		       double textLen=70;
   		       if(radian<6){
   		    	 lineLen=90;
   		    	 textLen=110;//控制指示线的长度,与文字的位置
   		       }
     		   if((textRadian<(Math.PI/2))){
     		      sfile.append("<line x1='"+middleX+"' y1='"+middleY+"' x2='"+(middleX+Math.cos(sita)*lineLen)+"' y2='"+(middleY-(Math.sin(sita)*lineLen))+"' stroke='#000000'/>");
     		      sfile.append("\n");
      		      sfile.append("<text x='"+(middleX+Math.cos(sita)*textLen)+"' y='"+(middleY-(Math.sin(sita)*textLen))+"' space='preserve' font-family='Tahoma' font-size='21' fill='red' stroke='red' baseline-shift='baseline' >"+percent+"%</text>");
     		    }else if ((textRadian>(Math.PI/2)&&textRadian<Math.PI)){
     		      sfile.append("<line x1='"+middleX+"' y1='"+middleY+"' x2='"+(middleX-Math.cos(sita)*lineLen)+"' y2='"+(middleY-(Math.sin(sita)*lineLen))+"' stroke='#000000'/>");
     		      sfile.append("\n");
     		      sfile.append("<text x='"+(middleX-Math.cos(sita)*textLen)+"' y='"+(middleY-(Math.sin(sita)*textLen))+"' space='preserve' font-family='Tahoma' font-size='21' fill='red' stroke='red' baseline-shift='baseline' >"+percent+"%</text>");
     		    }else if ((textRadian>(Math.PI)&&textRadian<(Math.PI*3/2))){
     		      sfile.append("<line x1='"+middleX+"' y1='"+middleY+"' x2='"+(middleX-Math.cos(sita)*lineLen)+"' y2='"+(middleY+(Math.sin(sita)*lineLen))+"' stroke='#000000'/>");
     		      sfile.append("\n");
     		      sfile.append("<text x='"+(middleX-Math.cos(sita)*textLen)+"' y='"+(middleY+(Math.sin(sita)*textLen))+"' space='preserve' font-family='Tahoma' font-size='21' fill='red' stroke='red' baseline-shift='baseline' >"+percent+"%</text>");
     		    }else if((textRadian>(Math.PI*3/2)&&textRadian<(Math.PI*2))){
     		      sfile.append("<line x1='"+middleX+"' y1='"+middleY+"' x2='"+(middleX+Math.cos(sita)*lineLen)+"' y2='"+(middleY+Math.sin(sita)*lineLen)+"' stroke='#000000'/>");
     		      sfile.append("\n");
     		      sfile.append("<text x='"+(middleX+Math.cos(sita)*textLen)+"' y='"+(middleY+(Math.sin(sita)*textLen))+"' space='preserve' font-family='Tahoma' font-size='21' fill='red' stroke='red' baseline-shift='baseline' >"+percent+"%</text>");
     		    }
    		    sfile.append("\n"); 
    		    if(getRadian(percents[0])>Math.PI){
    		    	 sfile.append("<path d='M "+x0+" "+y0+" L "+(x0+r)+" "+r+" A "+r+" "+r+" 0 1 0 "+x1+" "+y1+" L "+x0+" "+y0+" z' fill='"+colors[0]+"'/>");
    		      }else{
    		    	 sfile.append("<path d='M "+x0+" "+y0+" L "+(x0+r)+" "+r+" A "+r+" "+r+" 0 0 0 "+x1+" "+y1+" L "+x0+" "+y0+" z' fill='"+colors[0]+"'/>");
    		      }
    		    sfile.append("\n");
    		    sfile.append("<rect x='"+(x0+2*r)+"' y='"+(y0-r/2.0+N)+"' width='60' height='30' fill='"+colors[i]+"' stroke='#FFFFFF' stroke-dasharray='1,1' />");
    		    sfile.append("\n"); 
    		    sfile.append("<text x='"+(x0+2*r+80)+"' y='"+(y0-r/2.0+N+25)+"' space='preserve' font-family='宋体' font-size='28' fill='"+colors[0]+"' stroke='#000000' stroke-dasharray='1,1' baseline-shift='baseline'>"+names[0]+"</text>");
    		    sfile.append("\n");
    		    
    		}else{
    			textRadian = radian+(getRadian(percents[i])/2);//获取指示线与X轴的弧度.
    			radian =radian+getRadian(percents[i]);//第i个扇形前面的弧度的总和
    			middleX=(x0+getCos(textRadian)*r);
     		    middleY=(y0-getSin(textRadian)*r);
     		    double percent = Math.round(percents[i]*100)/100.0;
     		    k=Math.abs((middleY-y0)/(middleX-x0));
     		    double lineLen=50;
     		    double textLen=70;
     		    if(radian<6){
     		    	lineLen=90;
     		    	textLen=110;
     		    }
     		    double sita= Math.atan(k);
     		    if((textRadian<(Math.PI/2))){
     		      sfile.append("<line x1='"+middleX+"' y1='"+middleY+"' x2='"+(middleX+Math.cos(sita)*lineLen)+"' y2='"+(middleY-(Math.sin(sita)*lineLen))+"' stroke='#000000'/>");
     		      sfile.append("\n");
      		      sfile.append("<text x='"+(middleX+Math.cos(sita)*textLen)+"' y='"+(middleY-(Math.sin(sita)*textLen))+"' space='preserve' font-family='Tahoma' font-size='21' fill='red' stroke='red' baseline-shift='baseline' >"+percent+"%</text>");
     		    }else if ((textRadian>(Math.PI/2)&&textRadian<Math.PI)){
     		      sfile.append("<line x1='"+middleX+"' y1='"+middleY+"' x2='"+(middleX-Math.cos(sita)*lineLen)+"' y2='"+(middleY-(Math.sin(sita)*lineLen))+"' stroke='#000000'/>");
     		      sfile.append("\n");
     		      sfile.append("<text x='"+(middleX-Math.cos(sita)*textLen)+"' y='"+(middleY-(Math.sin(sita)*textLen))+"' space='preserve' font-family='Tahoma' font-size='21' fill='red' stroke='red' baseline-shift='baseline' >"+percent+"%</text>");
     		    }else if ((textRadian>(Math.PI)&&textRadian<(Math.PI*3/2))){
     		      sfile.append("<line x1='"+middleX+"' y1='"+middleY+"' x2='"+(middleX-Math.cos(sita)*lineLen)+"' y2='"+(middleY+(Math.sin(sita)*lineLen))+"' stroke='#000000'/>");
     		      sfile.append("\n");
     		      sfile.append("<text x='"+(middleX-Math.cos(sita)*textLen)+"' y='"+(middleY+(Math.sin(sita)*textLen))+"' space='preserve' font-family='Tahoma' font-size='21' fill='red' stroke='red' baseline-shift='baseline' >"+percent+"%</text>");
     		    }else if((textRadian>(Math.PI*3/2)&&textRadian<(Math.PI*2))){
     		      sfile.append("<line x1='"+middleX+"' y1='"+middleY+"' x2='"+(middleX+Math.cos(sita)*lineLen)+"' y2='"+(middleY+Math.sin(sita)*lineLen)+"' stroke='#000000'/>");
     		      sfile.append("\n");
     		      sfile.append("<text x='"+(middleX+Math.cos(sita)*textLen)+"' y='"+(middleY+(Math.sin(sita)*textLen))+"' space='preserve' font-family='Tahoma' font-size='21' fill='red' stroke='red' baseline-shift='baseline' >"+percent+"%</text>");
     		    }
			    sfile.append("\n");
			    // 参数 1 表示 画大于180的弧, 0 表示画小于180的弧 (这个地方比较重要)
			    if(getRadian(percents[i])>Math.PI){
	    		   sfile.append("<path d='M "+x0+" "+y0+" L "+x1+" "+y1+" A "+r+" "+r+" 0 1 0 "+(x1=x0+getCos(radian)*r)+" "+(y1=y0-getSin(radian)*r)+" L "+x0+" "+y0+" z' fill='"+colors[i]+"'/>");
			    }else{
	       		    sfile.append("<path d='M "+x0+" "+y0+" L "+x1+" "+y1+" A "+r+" "+r+" 0 0 0 "+(x1=x0+getCos(radian)*r)+" "+(y1=y0-getSin(radian)*r)+" L "+x0+" "+y0+" z' fill='"+colors[i]+"'/>");
			    }
			    sfile.append("\n");
			    N+=50;
    		    sfile.append("<rect x='"+(x0+2*r)+"' y='"+(y0-r/2.0+N)+"' width='60' height='30' fill='"+colors[i]+"' stroke='#FFFFFF' stroke-dasharray='1,1' />");
    		    sfile.append("\n"); 
    		    sfile.append("<text x='"+(x0+2*r+80)+"' y='"+(y0-r/2.0+N+25)+"' space='preserve' font-family='宋体' font-size='28' fill='"+colors[0]+"' stroke='#000000' stroke-dasharray='1,1' baseline-shift='baseline'>"+names[i]+"</text>");
    		    sfile.append("\n");
    		}
    		
    	}
    	return sfile.toString();
    }
    //返回弧度
    public static double getRadian(double fenshu){
    	return (fenshu*Math.PI)/50;
    }
    //返回正弦
	public static double getSin(double radian){
		 return Math.sin(radian);
	}
	//返回余弦
	public static double getCos(double radian){
		return Math.cos(radian);
	}
	public static void main(String[] args) {
		double[] data= {1000,2000,3000,900,100,2100};
		String[] names={"中国","美国","德国","法国","意大利","巴西"};
		create(data,names);
	}
	
	private static void create(double[] data,String[] names) {
		  try {
			createSVG("f:/c.svg",getPercent(data),names);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static double[] getPercent(double data[]){
		double sum=0;
		double percents[] = new double[data.length];
		for(int i=0;i<data.length;i++){
				sum+=data[i];
		}
		for(int i=0;i<data.length;i++){
			percents[i] =(data[i]/sum)*100;
		}
		return percents;
	}
	
	public static void createSVG(String fileRealPath, double[] percents,String[] names) throws Exception {
		String sFile = initialize(percents,names);
		try {
			byte[] byteFil = sFile.getBytes("UTF-8");
			File svgFile = new File(fileRealPath);
			if (svgFile.exists()) {
				svgFile.delete();
			}
			FileOutputStream fos = new FileOutputStream(svgFile);
			fos.write(byteFil);
			fos.close();
		} catch (Exception ex) {
			System.out.print(ex.getMessage());
		}
	}
}
