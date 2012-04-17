package org.openstack.ui.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UploadImageServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("upload performed");
		/*
		// process only multipart requests
		if (ServletFileUpload.isMultipartContent(req)) {

			// Create a factory for disk-based file items
			FileItemFactory factory = new DiskFileItemFactory();

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// Parse the request
			try {
				List<FileItem> items = upload.parseRequest(req);
				for (FileItem item : items) {
					// process only file upload - discard other form item types
					if (item.isFormField())
						continue;

					String fileName = item.getName();
					// get only the file name not whole path
					if (fileName != null) {
						fileName = FilenameUtils.getName(fileName);
					}

					File uploadedFile = new File(UPLOAD_DIRECTORY, fileName);
					if (uploadedFile.createNewFile()) {
						item.write(uploadedFile);
						resp.setStatus(HttpServletResponse.SC_CREATED);
						resp.getWriter().print(
								"The file was created successfully.");
						resp.flushBuffer();
					} else
						throw new IOException(
								"The file already exists in repository.");
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
		*/
	}

}
