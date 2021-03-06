package by.epam.ts.controller.command.impl;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.Hospitalization;
import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.command.CommandEnum;
import by.epam.ts.controller.command.util.builder.RedirectBuilder;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.controller.manager.NavigationManager;
import by.epam.ts.service.DiagnosisService;
import by.epam.ts.service.HospitalizationService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.factory.ServiceFactory;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class GetHospitalizationPlanCommand implements Command {

	private static final String PATH = "path.page.hospitalization_plan";
	private static final Logger log = LogManager.getLogger(GetHospitalizationPlanCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String patientId = getUserIdFromSession(request);
		if ((patientId == null) || patientId.isEmpty()) {
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.GET_INDEX_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.ACCESS_DENIED).getResultString());

			return;
		}

		ServiceFactory factory = ServiceFactoryImpl.getInstance();
		HospitalizationService hospitalizationService = factory.getHospitalizationService();
		DiagnosisService diagnosisService = factory.getDiagnosisService();
		Hospitalization lastHospitalization;
		int hospitalizationLength;

		try {
			// attempt to get the last hospitalization;
			lastHospitalization = hospitalizationService.getLastHospitalizationById(patientId);
			if (lastHospitalization != null) {
				// the patient has already been hospitalized;
				// get the last entry date and attempt to get the last discharge date;
				LocalDate entryDate = lastHospitalization.getEntryDate();
				LocalDate dischargeDate = lastHospitalization.getDischargeDate();
				String page = NavigationManager.getProperty(PATH);
				if (dischargeDate != null) {
					// the patient has been already discharged, hospitalization was closed;
					request.setAttribute(RequestAtribute.HOSPITALIZATION, lastHospitalization);
					request.setAttribute(RequestAtribute.MESSAGE, RequestMessage.ALREADY_DISCHARGED);
					request.setAttribute(RequestAtribute.DATE_FINISHING, dischargeDate.toString());
					goForward(request, response, page);

				} else {
					// attempt to get average amount of bed days by primary diagnosis;
					hospitalizationLength = diagnosisService.getAverageHospitalizationLength(patientId, entryDate);
					if (hospitalizationLength == 0) {
						// the primary diagnosis is absent in current moment;
						request.setAttribute(RequestAtribute.HOSPITALIZATION, lastHospitalization);
						request.setAttribute(RequestAtribute.MESSAGE, RequestMessage.DIAGNOSIS_ABSENT);
						goForward(request, response, page);
						return;
					}
					LocalDate expectedDischargeDate = entryDate.plusDays(hospitalizationLength);
					request.setAttribute(RequestAtribute.HOSPITALIZATION, lastHospitalization);
					request.setAttribute(RequestAtribute.BED_DAYS, hospitalizationLength);
					request.setAttribute(RequestAtribute.DATE_FINISHING, expectedDischargeDate);
					goForward(request, response, page);
				}
			} else {
				// the patient hasn't been hospitalized yet;
				RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
						CommandEnum.GET_PATIENT_MAIN_PAGE.toString().toLowerCase());
				response.sendRedirect(builder.setMessage(RequestMessage.NO_CURRENT_HOSPITALIZATION).getResultString());

			}

		} catch (ServiceException e) {
			log.log(Level.ERROR, "Error when calling execute() from GetHospitalizationPlanCommand", e);
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.TECHNICAL_ERROR).getResultString());
		}
	}
}
