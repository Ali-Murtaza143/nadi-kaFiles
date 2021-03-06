package by.epam.ts.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.command.CommandEnum;
import by.epam.ts.controller.command.util.builder.RedirectBuilder;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.service.UserService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ValidationServiceException;
import by.epam.ts.service.factory.ServiceFactory;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class UpdatePatientDataCommand implements Command {

	private static final Logger log = LogManager.getLogger(UpdatePatientDataCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String surname = request.getParameter(RequestAtribute.SURNAME);
		String name = request.getParameter(RequestAtribute.NAME);
		String newEmail = request.getParameter(RequestAtribute.EMAIL);
		String dateOfBirth = request.getParameter(RequestAtribute.DATE_OF_BIRTH);
		String oldEmail = request.getParameter(RequestAtribute.OLD_EMAIL);
		String patientId = getUserIdFromSession(request);

		if ((patientId == null) || patientId.isEmpty()) {
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.GET_INDEX_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.ACCESS_DENIED).getResultString());

			return;
		}
		ServiceFactory factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();
		try {
			userService.setPatientPersonalData(patientId, surname, name, dateOfBirth, newEmail, oldEmail);
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.GET_UPDATE_PATIENT_DATA_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setPatientId(patientId).getResultString());

		} catch (ValidationServiceException e) {
			log.log(Level.WARN, "Error when calling execute() from UpdatePatientDataCommand. Invalid parameters:", e);
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.GET_UPDATE_PATIENT_DATA_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.ERROR_DATA).setInvalidParameters(e.getMessage())
					.getResultString());

		} catch (ServiceException e) {
			log.log(Level.ERROR,
					"Error when calling execute() from UpdatePatientDataCommand. The patient's personal data wasn't updated.",
					e);
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.TECHNICAL_ERROR).getResultString());
		}

	}

}
