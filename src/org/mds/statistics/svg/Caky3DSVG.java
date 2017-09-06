package org.mds.statistics.svg;

import java.io.File;
import java.io.FileOutputStream;

public class Caky3DSVG {
	static String[] colors ={"#d6a97b",
		 					 "#22FF22", "#aaffaa", "#799AE1",
		 					 "#9aabEe", "#3e941b", "#f2e692",  
		 					 "#66cc00", "#297110", "#d6a97b", 
		 					 "#82522b", "#aaaaff", "#1111aa", 
		 					 "#ff2222", "#ffaaaa", "#aa1111" 
		 					 };
	public static void main(String[] args) {
		double data[] = {20,20,50};
		 try {
			createSVG("f:/d.svg",getPercent(data));
		    } catch (Exception e) {
				e.printStackTrace();
		 }
	}
	static String initialize(double [] percent){
		double percents[] = {10,15,5,20,40,10}; 
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
        sfile.append("version='1.1'  width='100%' height='100%' viewBox='0 0 1024 600'>");
        sfile.append("\n");
        sfile.append("<defs></defs>");
        sfile.append("\n");
        String path = createPath(502, 300,300, 150, percents);
        sfile.append(path);
        sfile.append("</g>");
        sfile.append("\n");
        sfile.append("</svg>");
        return sfile.toString();
	}
	/**
	 * 
	 * @param x0 原点 X 
	 * @param y0 原点 Y
	 * @param langR
	 * @param shortR
	 * @param fenshu
	 * @return
	 */
	static String createPath(double x0,double y0,double langR,double shortR ,double percents[]){ 
		StringBuffer sfile = new StringBuffer();
		double xBottom =0;
		double yBottom =0;
		double xBottom1=0;
		double yBottom1=0;
		double radian =0;
		sfile.append("<g stroke-width='1' stroke='#000000' transform='matrix(1,0,0,1,1.638,-9.83)' xmlns='http://www.w3.org/2000/svg'>");
		sfile.append("\n");
		for(int i=0;i<percents.length;i++){ 
			System.out.println("i:"+i);
		    radian =radian+getRadian(percents[i]);//第i个扇形到 第一个扇形,弧度的总和.
            System.out.println("弧度2:"+radian);
			 if (i==0){
				System.out.println("弧度1:"+radian);
				if(radian==Math.PI/2){
					xBottom = x0;//底面的x坐标
					yBottom = y0-shortR;//底面的y坐标
				}else if(radian==Math.PI*3/2){
					xBottom = x0;//底面的x坐标
					yBottom = y0+shortR;//底面的y坐标
				} else{
					double tanRadian = Math.abs(Math.tan(radian));
					double sqValue=shortR*shortR+tanRadian*tanRadian*langR*langR;
					if(radian<Math.PI/2){
						System.out.println("if1:"+radian);
						xBottom = x0+(langR*shortR)/Math.sqrt(sqValue);//底面的x坐标
						yBottom = y0-(tanRadian*langR*shortR)/Math.sqrt(sqValue);//底面的y坐标
					}
					else if (radian>Math.PI/2&&radian<=Math.PI){
						System.out.println("if2:"+radian);
						xBottom =x0-(langR*shortR)/Math.sqrt(sqValue);
						yBottom =y0-(tanRadian*langR*shortR)/Math.sqrt(sqValue);
					}else if (radian>Math.PI&&radian<Math.PI*3/2){
						System.out.println("if3:"+radian);
						xBottom =x0-(langR*shortR)/Math.sqrt(sqValue);
						yBottom =y0+(tanRadian*langR*shortR)/Math.sqrt(sqValue);
					}else if (radian>Math.PI*3/2&&radian<Math.PI*2){
						System.out.println("if4:"+radian);
						xBottom = x0+(langR*shortR)/Math.sqrt(sqValue);
						yBottom = y0+(tanRadian*langR*shortR)/Math.sqrt(sqValue);
					}
				}
				if(getRadian(percents[0])>Math.PI){//大于 PI 弧度,即百分比超过50%
					sfile.append("<g fill='"+colors[i]+"' >");
					sfile.append("\n"); 
					sfile.append("<path d='M "+x0+" "+y0+" L "+(x0+langR)+" "+y0+" A "+langR+" "+shortR+" 0 1 0 "+xBottom+" "+yBottom+" z' />");
					sfile.append("\n"); 
					sfile.append("<path d='M "+(x0+langR)+" "+(y0-50)+" A "+langR+" "+shortR+" 0 1 0 "+xBottom+" "+(yBottom-50)+" L "+xBottom+" "+yBottom+" A "+langR+" "+shortR+" 0 1 1 "+(x0+langR)+" "+y0+" z' />");
					sfile.append("\n"); 
					sfile.append("<path d='M "+x0+" "+(y0-50)+"  L "+(x0+langR)+" "+(y0-50)+" A "+langR+" "+shortR+" 0 1 0 "+xBottom+"  "+(yBottom-50)+" z' />");
					sfile.append("\n"); 
					sfile.append("</g>"); 
				    sfile.append("\n");  
				}else{ 
					sfile.append("<g fill='"+colors[i]+"' >");
					sfile.append("\n");
					sfile.append("<path d='M "+x0+" "+y0+" L "+(x0+langR)+" "+y0+" A "+langR+" "+shortR+" 0 0 0 "+xBottom+" "+yBottom+" z' />");
					sfile.append("\n");
					sfile.append("<path d='M "+(x0+langR)+" "+(y0-50)+" A "+langR+" "+shortR+" 0 0 0 "+xBottom+" "+(yBottom-50)+" L "+xBottom+" "+yBottom+" A "+langR+" "+shortR+" 0 0 1 "+(x0+langR)+" "+y0+" z' />");
					sfile.append("\n");
					sfile.append("<path d='M "+x0+" "+(y0-50)+"  L "+(x0+langR)+" "+(y0-50)+" A "+langR+" "+shortR+" 0 0 0 "+xBottom+"  "+(yBottom-50)+" z' />");
					sfile.append("\n");
					sfile.append("</g>");
				    sfile.append("\n");
				}
			 }else{
                if(radian==Math.PI/2){
					xBottom1= x0;//底面的x坐标
					yBottom1= y0-shortR;//底面的y坐标
				}else if(radian==Math.PI*3/2){
					xBottom1 = x0;//底面的x坐标 
					yBottom1 = y0+shortR;//底面的y坐标
				} else{
					double tanRadian = Math.abs(Math.tan(radian));
					double sqValue=shortR*shortR+tanRadian*tanRadian*langR*langR;
					if(radian<Math.PI/2){
						System.out.println("if1:"+radian);
						xBottom1 = x0+(langR*shortR)/Math.sqrt(sqValue);//底面的x坐标
						yBottom1 = y0-(tanRadian*langR*shortR)/Math.sqrt(sqValue);//底面的y坐标
					}
					else if (radian>Math.PI/2&&radian<=Math.PI){
						System.out.println("if2:"+radian);
						xBottom1 =x0-(langR*shortR)/Math.sqrt(sqValue);
						yBottom1 =y0-(tanRadian*langR*shortR)/Math.sqrt(sqValue);
					}else if (radian>Math.PI&&radian<Math.PI*3/2){
						System.out.println("if3:"+radian);
						xBottom1 =x0-(langR*shortR)/Math.sqrt(sqValue);
						yBottom1 =y0+(tanRadian*langR*shortR)/Math.sqrt(sqValue);
					}else if (radian>Math.PI*3/2){
						System.out.println("if4:"+radian);
						xBottom1 = x0+(langR*shortR)/Math.sqrt(sqValue);
						yBottom1 = y0+(tanRadian*langR*shortR)/Math.sqrt(sqValue);
					}
				}
				if(getRadian(percents[i])>Math.PI){//大于 PI 弧度,即百分比超过50%
					System.out.println("大于pi");
					sfile.append("<g fill='"+colors[i]+"' >");
				    sfile.append("\n");
				    sfile.append("<path d='M "+x0+" "+y0+" L "+xBottom+" "+yBottom+" A "+langR+" "+shortR+" 0 1 0 "+xBottom1+" "+yBottom1+" z' />");
				    sfile.append("\n");
				    sfile.append("<path d='M "+(xBottom)+" "+(yBottom-50)+" A "+langR+" "+shortR+" 0 1 0 "+xBottom1+" "+(yBottom1-50)+" L "+xBottom1+" "+yBottom1+" A "+langR+" "+shortR+" 0 1 1 "+xBottom+" "+yBottom+" z' />");
				    sfile.append("\n");
				    sfile.append("<path d='M "+x0+" "+(y0-50)+"  L "+(xBottom)+" "+(yBottom-50)+" A "+langR+" "+shortR+" 0 1 0 "+xBottom1+" "+(yBottom1-50)+"  z' />");
				    sfile.append("\n");
				    sfile.append("</g>");
				    sfile.append("\n");
				}else{
					System.out.println("小于pi");
					sfile.append("<g fill='"+colors[i]+"' >");
				    sfile.append("\n");
				    sfile.append("<path d='M "+x0+" "+y0+" L "+xBottom+" "+yBottom+" A "+langR+" "+shortR+" 0 0 0 "+xBottom1+" "+yBottom1+" z' />");
				    sfile.append("\n");
				    sfile.append("<path d='M "+(xBottom)+" "+(yBottom-50)+" A "+langR+" "+shortR+" 0 0 0 "+xBottom1+" "+(yBottom1-50)+" L "+xBottom1+" "+yBottom1+" A "+langR+" "+shortR+" 0 0 1 "+xBottom+" "+yBottom+" z' />");
				    sfile.append("\n");
				    sfile.append("<path d='M "+x0+" "+(y0-50)+"  L "+(xBottom)+" "+(yBottom-50)+" A "+langR+" "+shortR+" 0 0 0 "+xBottom1+" "+(yBottom1-50)+"  z' />");
				    sfile.append("\n");
				    sfile.append("</g>");
				    sfile.append("\n");
				}
			    xBottom=xBottom1; 
			    yBottom=yBottom1; 
			 }
		}
		return sfile.toString();
	}
	  //返回弧度
    public static double getRadian(double percent){
    	return (percent*Math.PI)/50;
    }
    //返回正弦
	public static double getSin(double radian){
		 return Math.sin(radian);
	}
	
	//返回余弦
	public static double getCos(double radian){
		return Math.cos(radian);
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
	public static void createSVG(String fileRealPath, double[] percents) throws Exception {
		String sFile = initialize(percents);
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
