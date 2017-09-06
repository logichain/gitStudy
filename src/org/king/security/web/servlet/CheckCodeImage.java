package org.king.security.web.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * <p> CheckCodeImage.java </p>
 * <p> {功能说明} </p>
 *
 * <p><a href="CheckCodeImage.java.html"><i>查看源代码</i></a></p>  
 *
 * @author <a href="mailto:m_ljf@msn.com">ljf</a>
 * @version 0.1
 * @since 2006-5-16
 * 
 *
 */
public class CheckCodeImage extends HttpServlet {

	private static Log log = LogFactory.getLog(CheckCodeImage.class);
	
	public CheckCodeImage() {
	}
	
	public final void service(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		
       //清空缓冲区
        response.reset();

		response.setContentType("image/jpeg");

      //设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

		
		String checkCode = RandomStringUtils.randomNumeric(4);

	
		BufferedImage image = new BufferedImage(50, 18,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = image.createGraphics();
		Font font = new Font(g2d.getFont().getFontName(), Font.PLAIN, 18);
		g2d.setFont(font);
		g2d.setColor(new Color(254,231,229));
		g2d.fill(new Rectangle(image.getWidth(), image.getHeight()));
		g2d.setColor(new Color(185,46,38));

		for (int i = 0; i < ((int) (Math.random() * 60) + 20); ++i) {
			g2d.fill(new Rectangle((int) (Math.random() * image.getWidth()),
					(int) (Math.random() * image.getHeight()), 1, 1));
		}

		g2d.setPaint(new Color(173,40,32));
		g2d.drawString(checkCode, 2, 15); //(int) (Math.random() * 15) + 5
		g2d.dispose();

		request.getSession(true).setAttribute("chkCode", checkCode);

		try {
			//ImageIO.write(image, "gif", response.getOutputStream());
			OutputStream out = response.getOutputStream();
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			out.flush();
		} catch (Exception ex) {
			log.info(ex);
		}

		
	}
	
}
