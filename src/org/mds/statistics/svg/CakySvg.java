package org.mds.statistics.svg;

import java.util.List;


public class CakySvg {
	static String[] colors ={ "LimeGreen", "Magenta",
			  "Maroon", "MediumAquaMarine","MediumBlue", "MediumOrchid", "MediumPurple", 
			 "MediumSeaGreen", "MediumSlateBlue", "MediumSpringGreen"};
	public static String initialize(double [] fenshu,List<String> dataInfo){
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
        //调整饼图的位置、大小
        sfile.append("version='1.1'  width='400px' height='300px' viewBox='200 -180 800 800'>");
        sfile.append("\n");
        sfile.append("<defs></defs>");
        sfile.append("\n");
        sfile.append("<g stroke-width='1' stroke='#FFFFFF'  xmlns='http://www.w3.org/2000/svg'>");
        sfile.append("\n");
        //循环创造path标签.
        String path =creatPath(502, 300, 300, fenshu,dataInfo);
        sfile.append(path);
        sfile.append("</g>");
        sfile.append("\n");
        sfile.append("</svg>");
		return sfile.toString();
	}
	
    public static String creatPath(double x0,double y0,double r,double[]fenshu,List<String> dataInfo){
    	StringBuffer sfile =new StringBuffer();
    	double x1=0;
    	double y1=0;
    	double middleX= 0;
    	double middleY=0;
    	double radian =0;
    	double textRadian=0;
    	double k=0;
    	for(int i=0;i<fenshu.length;i++){
    		if(i==0){
    		   radian =getRadian(fenshu[0]);
    		   textRadian=radian/2;
    		   x1 = (x0+getCos(radian)*r);
    		   y1 = (y0-getSin(radian)*r);
    		   middleX=(x0+getCos(textRadian)*r);
    		   middleY=(y0-getSin(textRadian)*r);
     		   double percent = Math.round(fenshu[0]*100)/100.0;
     		   k=Math.abs((middleY-y0)/(middleX-x0));
     		   double sita= Math.atan(k);
     		   double lineLen=50;
   		       double textLen=70;
   		       if(radian<6){
   		    	lineLen=90;
   		    	textLen=110;
   		       }
	   		    if(getRadian(fenshu[0])>Math.PI){
			    	 sfile.append("<path d='M "+x0+" "+y0+" L "+(x0+r)+" "+r+" A "+r+" "+r+" 0 1 0 "+x1+" "+y1+" L "+x0+" "+y0+" z' fill='"+colors[0]+"'/>");
			      }else{
			    	 sfile.append("<path d='M "+x0+" "+y0+" L "+(x0+r)+" "+r+" A "+r+" "+r+" 0 0 0 "+x1+" "+y1+" L "+x0+" "+y0+" z' fill='"+colors[0]+"'/>");
			      }
			    sfile.append("\n");
		    
     		   if((textRadian <= (Math.PI/2))){
     		      sfile.append("<line x1='"+middleX+"' y1='"+middleY+"' x2='"+(middleX+Math.cos(sita)*lineLen)+"' y2='"+(middleY-(Math.sin(sita)*lineLen))+"' stroke='#000000'/>");
     		      sfile.append("\n");
      		      sfile.append("<text x='"+(middleX+Math.cos(sita)*textLen)+"' y='"+(middleY-(Math.sin(sita)*textLen))+"' space='preserve' font-family='Tahoma' font-size='38' fill='black' stroke='black' baseline-shift='baseline' >"+ dataInfo.get(i) + "," +percent+"%</text>");
     		    }else if ((textRadian > (Math.PI/2)&&textRadian <= Math.PI)){
     		      sfile.append("<line x1='"+middleX+"' y1='"+middleY+"' x2='"+(middleX-Math.cos(sita)*lineLen)+"' y2='"+(middleY-(Math.sin(sita)*lineLen))+"' stroke='#000000'/>");
     		      sfile.append("\n");
     		      sfile.append("<text x='"+(middleX-Math.cos(sita)*textLen)+"' y='"+(middleY-(Math.sin(sita)*textLen))+"' space='preserve' font-family='Tahoma' font-size='38' fill='black' stroke='black' baseline-shift='baseline' >"+ dataInfo.get(i) + "," +percent+"%</text>");
     		    }else if ((textRadian > (Math.PI)&&textRadian <= (Math.PI*3/2))){
     		      sfile.append("<line x1='"+middleX+"' y1='"+middleY+"' x2='"+(middleX-Math.cos(sita)*lineLen)+"' y2='"+(middleY+(Math.sin(sita)*lineLen))+"' stroke='#000000'/>");
     		      sfile.append("\n");
     		      sfile.append("<text x='"+(middleX-Math.cos(sita)*textLen)+"' y='"+(middleY+(Math.sin(sita)*textLen))+"' space='preserve' font-family='Tahoma' font-size='38' fill='black' stroke='black' baseline-shift='baseline' >"+ dataInfo.get(i) + "," +percent+"%</text>");
     		    }else if((textRadian > (Math.PI*3/2)&&textRadian <= (Math.PI*2))){
     		      sfile.append("<line x1='"+middleX+"' y1='"+middleY+"' x2='"+(middleX+Math.cos(sita)*lineLen)+"' y2='"+(middleY+Math.sin(sita)*lineLen)+"' stroke='#000000'/>");
     		      sfile.append("\n");
     		      sfile.append("<text x='"+(middleX+Math.cos(sita)*textLen)+"' y='"+(middleY+(Math.sin(sita)*textLen))+"' space='preserve' font-family='Tahoma' font-size='38' fill='black' stroke='black' baseline-shift='baseline' >"+ dataInfo.get(i) + "," +percent+"%</text>");
     		    }
    		    sfile.append("\n");
    		    
    		}else{
    			textRadian = radian+(getRadian(fenshu[i])/2);
    			radian =radian+getRadian(fenshu[i]);//弧度.
    			System.out.println("弧度:"+radian);
    			middleX=(x0+getCos(textRadian)*r);
     		    middleY=(y0-getSin(textRadian)*r);
     		    double percent = Math.round(fenshu[i]*100)/100.0;
     		    k=Math.abs((middleY-y0)/(middleX-x0));
     		    double lineLen=50;
     		    double textLen=70;
     		    if(radian<6){
     		    	lineLen=90;
     		    	textLen=110;
     		    }
     		    // 参数 1 表示 画大于180的弧, 0 表示画小于180的弧
			    if(getRadian(fenshu[i])>Math.PI){
	    		   sfile.append("<path d='M "+x0+" "+y0+" L "+x1+" "+y1+" A "+r+" "+r+" 0 1 0 "+(x1=x0+getCos(radian)*r)+" "+(y1=y0-getSin(radian)*r)+" L "+x0+" "+y0+" z' fill='"+colors[i]+"'/>");
			    }else{
	       		    sfile.append("<path d='M "+x0+" "+y0+" L "+x1+" "+y1+" A "+r+" "+r+" 0 0 0 "+(x1=x0+getCos(radian)*r)+" "+(y1=y0-getSin(radian)*r)+" L "+x0+" "+y0+" z' fill='"+colors[i]+"'/>");
			    }
        		sfile.append("\n");
        		
     		    double sita= Math.atan(k);
     		    if((textRadian <= (Math.PI/2))){
     		      sfile.append("<line x1='"+middleX+"' y1='"+middleY+"' x2='"+(middleX+Math.cos(sita)*lineLen)+"' y2='"+(middleY-(Math.sin(sita)*lineLen))+"' stroke='#000000'/>");
     		      sfile.append("\n");
      		      sfile.append("<text x='"+(middleX+Math.cos(sita)*textLen)+"' y='"+(middleY-(Math.sin(sita)*textLen))+"' space='preserve' font-family='Tahoma' font-size='38' fill='black' stroke='black' baseline-shift='baseline' >"+ dataInfo.get(i) + "," +percent+"%</text>");
     		    }else if ((textRadian > (Math.PI/2)&&textRadian <= Math.PI)){
     		      sfile.append("<line x1='"+middleX+"' y1='"+middleY+"' x2='"+(middleX-Math.cos(sita)*lineLen)+"' y2='"+(middleY-(Math.sin(sita)*lineLen))+"' stroke='#000000'/>");
     		      sfile.append("\n");
     		      sfile.append("<text x='"+(middleX-Math.cos(sita)*textLen)+"' y='"+(middleY-(Math.sin(sita)*textLen))+"' space='preserve' font-family='Tahoma' font-size='38' fill='black' stroke='black' baseline-shift='baseline' >"+ dataInfo.get(i) + "," +percent+"%</text>");
     		    }else if ((textRadian > (Math.PI)&&textRadian <= (Math.PI*3/2))){
     		      sfile.append("<line x1='"+middleX+"' y1='"+middleY+"' x2='"+(middleX-Math.cos(sita)*lineLen)+"' y2='"+(middleY+(Math.sin(sita)*lineLen))+"' stroke='#000000'/>");
     		      sfile.append("\n");
     		      sfile.append("<text x='"+(middleX-Math.cos(sita)*textLen)+"' y='"+(middleY+(Math.sin(sita)*textLen))+"' space='preserve' font-family='Tahoma' font-size='38' fill='black' stroke='black' baseline-shift='baseline' >"+ dataInfo.get(i) + "," +percent+"%</text>");
     		    }else if((textRadian > (Math.PI*3/2)&&textRadian <= (Math.PI*2))){
     		      sfile.append("<line x1='"+middleX+"' y1='"+middleY+"' x2='"+(middleX+Math.cos(sita)*lineLen)+"' y2='"+(middleY+Math.sin(sita)*lineLen)+"' stroke='#000000'/>");
     		      sfile.append("\n");
     		      sfile.append("<text x='"+(middleX+Math.cos(sita)*textLen)+"' y='"+(middleY+(Math.sin(sita)*textLen))+"' space='preserve' font-family='Tahoma' font-size='38' fill='black' stroke='black' baseline-shift='baseline' >"+ dataInfo.get(i) + "," +percent+"%</text>");
     		    }
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
	
//	public static void main(String[] args) {
//		double[] data= {1000,320,410,200,990,430,400,300,340,423,243,200,430,210};
//		create(data);
//	}
//	private static void create(double[] data) {
//		  try {
//			createSVG("f:/a.svg",getPercent(data));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	public static double[] getPercent(double data[]){
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
	public static double[] getPercent(List<Integer> dataList){
		Integer sum=0;
		double percents[] = new double[dataList.size()];
		for(Integer i:dataList)
		{
			sum+=i;
		}
		for(int i=0;i<dataList.size();i++){
			percents[i] =(dataList.get(i)*1.0d/sum)*100;
		}
		return percents;
	}
//	public static void createSVG(String fileRealPath, double[] percents) throws Exception {
//		String sFile = initialize(percents);
//		try {
//			byte[] byteFil = sFile.getBytes("UTF-8");
//			File svgFile = new File(fileRealPath);
//			if (svgFile.exists()) {
//				svgFile.delete();
//			}
//			FileOutputStream fos = new FileOutputStream(svgFile);
//			fos.write(byteFil);
//			fos.close();
//		} catch (Exception ex) {
//			System.out.print(ex.getMessage());
//		}
//	}
}
