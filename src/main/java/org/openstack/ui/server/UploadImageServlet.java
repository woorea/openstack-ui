package org.openstack.ui.server;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.openstack.client.OpenStackClient;
import org.openstack.model.images.glance.GlanceImage;

public class UploadImageServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("upload performed");
		// process only multipart requests
		if (ServletFileUpload.isMultipartContent(req)) {

			// Create a factory for disk-based file items
			FileItemFactory factory = new DiskFileItemFactory();

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// Parse the request
			try {
				List<FileItem> items = upload.parseRequest(req);
				
				OpenStackSession session = (OpenStackSession) req.getSession().getAttribute(Constants.OPENSTACK_SESSION);
				OpenStackClient client = OpenStackClient.authenticate(session.getProperties(), session.getAccess());
				
				GlanceImage image = new GlanceImage();
				
				for (FileItem item : items) {
					// process only file upload - discard other form item types
					if (item.isFormField()) {
						setImageAttribute(image, item.getFieldName(), item.getString());
					} else {
						client.getImagesEndpoint().post(item.getInputStream(), item.getSize(), image);
						resp.setStatus(HttpServletResponse.SC_CREATED);
						resp.getWriter().print("The file was created successfully.");
						resp.flushBuffer();
					}
				}
			} catch (Exception e) {
				resp.sendError(
						HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"An error occurred while creating the file : "
								+ e.getMessage());
			}

		} else {
			resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
					"Request contents type is not supported by the servlet.");
		}
	}

	private void setImageAttribute(GlanceImage image, String name, String value) {
		if("name".equals(name)) {
			image.setName(value);
		} else if ("diskFormat".equals(name)) {
			image.setDiskFormat(value);
		} else if ("containerFormat".equals(name)) {
			image.setContainerFormat(value);
		}
	}

}
