package org.cloudsherpa.ui.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class KeyPairDownloadServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		OpenStackSession session = (OpenStackSession) req.getSession().getAttribute(Constants.OPENSTACK_SESSION);
		OpenStackClient client = OpenStackClient.authenticate(session.getProperties(), session.getAccess());
		
		KeyPair keyPair = client.getComputeEndpoint().keyPairs().post(new NovaKeyPair(req.getParameter("keyPairName")));

		ServletOutputStream out = resp.getOutputStream();
		// resp.setContentLength(responseBytes.length);
		resp.setHeader("Content-disposition",
				"attachment; filename=" + keyPair.getName() + ".pem");
		resp.setContentType("text/plain; charset=UTF-8");
		resp.setStatus(HttpServletResponse.SC_CREATED);
		IOUtils.write(keyPair.getPrivateKey(), out, "UTF-8");
		out.flush();
		IOUtils.closeQuietly(out);
		System.out.println("WRITED");
		*/
	}

}