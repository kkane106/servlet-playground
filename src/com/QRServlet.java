package com;

import java.io.IOException;
import java.net.InetAddress;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

public class QRServlet extends HttpServlet {
	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		String name = req.getParameter("name");
		
		if (name == null) {
			name = "test";
		}
		System.out.println(name);
		InetAddress inetAddress = InetAddress.getLocalHost();
		String ipAddress = inetAddress.getHostAddress();
		System.out.println(ipAddress);
		
//		Set content type to display QR code as image (instead of gobbledy gook)
		res.setContentType("image/png");
		
//		Get the output stream from the response
		ServletOutputStream os = res.getOutputStream();
		
//		Feed data to the QRCode library, write it to the Servlet's OutputStream
		QRCode.from(name).to(ImageType.PNG).writeTo(os);
		
//		Clean up
		os.close();
	}
}
