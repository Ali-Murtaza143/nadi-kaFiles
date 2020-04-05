package by.epam.ts.controller.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.Treatment;
import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.SessionAtribute;
import by.epam.ts.controller.manager.MessageManager;
import by.epam.ts.controller.manager.NavigationManager;
import by.epam.ts.service.UserService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class ShowTreatmentCommand implements Command {
	private static final Logger log = LogManager.getLogger(ShowTreatmentCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = null;

		ServiceFactoryImpl factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute(SessionAtribute.USER_ID);

		if (userId == null) {
			String message = MessageManager.getProperty("local.main.denied");
			request.setAttribute(RequestAtribute.ACCESS_DENIED, message);
			page = NavigationManager.getProperty("path.page.main");
			goForward(request, response, page);

		} else {
			List<Treatment> prescriptions = new ArrayList<Treatment>();
			try {
				prescriptions = userService.getPatientsTreatmentById(userId);
			} catch (ServiceException ex) {
				log.log(Level.ERROR, "Error during calling method getPatientsTreatmentById() from ShowTreatmentCommand",
						ex);
				String message = MessageManager.getProperty("local.technicalerror");
				request.setAttribute("techninalErrorMessage", message);
				response.sendRedirect(request.getContextPath() + "/register?command=show_error_page");
			}

			if (!prescriptions.isEmpty()) {
				request.setAttribute("treatment", prescriptions);
				page = NavigationManager.getProperty("path.page.treatment");
				goForward(request, response, page);

			} else {
				String message = MessageManager.getProperty("local.main.data.unavailable");
				request.setAttribute(RequestAtribute.DATA_UNAVAILABLE, message);
				page = NavigationManager.getProperty("path.page.main");
				goForward(request, response, page);
			}
		}

	}
}