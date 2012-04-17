package org.openstack.ui.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetupServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/setup.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession(false);
		if(session != null) {
			session.invalidate();
		}
		
		Properties properties = SetupServlet.loadProperties();
		
		properties.setProperty("verbose", "true");
		
		properties.setProperty("identity.endpoint.publicURL", req.getParameter("identity.endpoint.publicURL"));
		properties.setProperty("identity.endpoint.internalURL", req.getParameter("identity.endpoint.internalURL"));
		properties.setProperty("identity.endpoint.adminURL", req.getParameter("identity.endpoint.adminURL"));
		properties.setProperty("identity.admin.token", req.getParameter("identity.admin.token"));
		
		properties.store(new FileOutputStream(findOrCreateConfigFile()), "last updated : " + new Date());
		
		resp.sendRedirect(String.format("%s/login", req.getContextPath()));
		
	}
	
	public static Properties loadProperties() throws IOException {
		
		File configFile = findOrCreateConfigFile();
		
		Properties properties = new Properties();
		properties.load(new FileInputStream(configFile));
		
		return properties;
	}
	
	private static File findOrCreateConfigFile() throws IOException {
		File configDir = new File(System.getProperty("user.home"),".openstack");
		if(!configDir.exists()) {
			configDir.mkdirs();
		}
		File configFile = new File(configDir, "openstack.properties");
		if(!configFile.exists()) {
			configFile.createNewFile();
		}
		return configFile;
	}

}
