package by.epam.ts.controller.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.Patient;
import by.epam.ts.controller.command.AccessManager;
import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.command.CommandEnum;
import by.epam.ts.controller.command.util.builder.RedirectBuilder;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.controller.manager.NavigationManager;
import by.epam.ts.service.UserService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.factory.ServiceFactory;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class SearchPatientCommand implements Command, AccessManager {

	private static final String PATH = "path.page.staff.patient_data";
	private static final Logger log = LogManager.getLogger(SearchPatientCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Checking of the user rights;
		boolean staffRights = checkStaffRights(request);
		if (!staffRights) {
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.ACCESS_DENIED).getResultString());

			return;
		}
		String query = request.getParameter(RequestAtribute.QUERY_SEARCH);
		// If query is incorrect - show current page again with the message;
		if (query == null || query.isEmpty()) {
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.GET_STAFF_MAIN_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.NOT_FOUND).getResultString());

			return;
		}
		ServiceFactory factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();
		try {
			List<Patient> patients = userService.getPatientBySurname(query);
			if (patients.isEmpty()) {
				RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
						CommandEnum.GET_STAFF_MAIN_PAGE.toString().toLowerCase());
				response.sendRedirect(builder.setMessage(RequestMessage.NOT_FOUND).getResultString());

				return;
			}
			request.setAttribute(RequestAtribute.LIST_PATIENTS, patients);
			request.setAttribute(RequestAtribute.MESSAGE, RequestMessage.PATIENTS_FOUND);
			String page = NavigationManager.getProperty(PATH);
			goForward(request, response, page);

		} catch (ServiceException e) {
			log.log(Level.ERROR,
					"Error when calling userService.getPatientBySurname(surname) from SearchPatientCommand", e);
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.TECHNICAL_ERROR).getResultString());
		}

	}

}
