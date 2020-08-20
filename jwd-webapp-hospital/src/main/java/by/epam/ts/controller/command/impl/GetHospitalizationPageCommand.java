package by.epam.ts.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.Hospitalization;
import by.epam.ts.bean.Patient;
import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.command.CommandEnum;
import by.epam.ts.controller.command.util.builder.RedirectBuilder;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.controller.manager.NavigationManager;
import by.epam.ts.service.HospitalizationService;
import by.epam.ts.service.UserService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.factory.ServiceFactory;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class GetHospitalizationPageCommand implements Command {

	private static final String PATH = "path.page.staff.hospitalization";
	private static final Logger log = LogManager.getLogger(GetHospitalizationPageCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String patientId = request.getParameter(RequestAtribute.PATIENT_ID);

		ServiceFactory factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();
		HospitalizationService hospitalizationService = factory.getHospitalizationService();
		
		try {
			Patient patient = userService.getPatientById(patientId);
			Hospitalization lastHospitalization = hospitalizationService.getLastHospitalizationById(patientId);
			request.setAttribute(RequestAtribute.PATIENT, patient);
			request.setAttribute(RequestAtribute.HOSPITALIZATION, lastHospitalization);
			String page = NavigationManager.getProperty(PATH);
			goForward(request, response, page);
		} catch (ServiceException e) {
			log.log(Level.ERROR,
					"Error when calling userService.getLastHospitalizationById() from GetHospitalizationPageCommand",
					e);
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.TECHNICAL_ERROR).getResultString());
		}

	}

}
