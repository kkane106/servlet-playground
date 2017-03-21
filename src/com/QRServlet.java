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
//		Dynamically extract name from param
		String name = req.getParameter("name");
//		Extract lat/long derived from HTML5 with JS
		String lat = req.getParameter("lat");
		String lon = req.getParameter("long");
		
//		...if issue...
		if (name == null) {
			name = "test";
		}
		
//		Extract IP
		InetAddress inetAddress = InetAddress.getLocalHost();
		String ipAddress = inetAddress.getHostAddress();
		
//		Set content type to display QR code as image (instead of gobbledy gook)
		res.setContentType("image/png");
		
//		Get the output stream from the response
		ServletOutputStream os = res.getOutputStream();
		
		System.out.println(generateQRData(name, ipAddress, lat, lon));
//		Feed data to the QRCode library, write it to the Servlet's OutputStream
		QRCode
			.from(generateQRData(name, ipAddress, lat, lon))
			.to(ImageType.PNG)
			.writeTo(os);
		
//		Clean up
		os.close();
	}
	
	public String generateQRData(String name, String ip, String lat, String lon) {
		String data = "";
		data += name + "&";
		data += ip + "&";
		data += lat + ":" + lon;
		return data;
	}
}
