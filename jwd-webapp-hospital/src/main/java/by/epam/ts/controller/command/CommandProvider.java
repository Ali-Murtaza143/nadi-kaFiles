package by.epam.ts.controller.command;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import by.epam.ts.controller.command.impl.AddNewDiagnosisCommand;
import by.epam.ts.controller.command.impl.AddNewPatientCommand;
import by.epam.ts.controller.command.impl.AddNewStaffCommand;
import by.epam.ts.controller.command.impl.AddNewTreatmentCommand;
import by.epam.ts.controller.command.impl.AddPatientDiagnosisCommand;
import by.epam.ts.controller.command.impl.ChangeLanguageCommand;
import by.epam.ts.controller.command.impl.GetCurrentPatientPageCommand;
import by.epam.ts.controller.command.impl.GetDiagnosisPageCommand;
import by.epam.ts.controller.command.impl.GetPatientDataPageCommand;
import by.epam.ts.controller.command.impl.GetPrescriptionsPageCommand;
import by.epam.ts.controller.command.impl.GetStaffDataPageCommand;
import by.epam.ts.controller.command.impl.GiveConsentCommand;
import by.epam.ts.controller.command.impl.LoginCommand;
import by.epam.ts.controller.command.impl.LogoutCommand;
import by.epam.ts.controller.command.impl.SearchPatientCommand;
import by.epam.ts.controller.command.impl.ShowErrorPageCommand;
import by.epam.ts.controller.command.impl.ShowIndexPageCommand;
import by.epam.ts.controller.command.impl.ShowPatientMainPageCommand;
import by.epam.ts.controller.command.impl.ShowSignUpPageCommand;
import by.epam.ts.controller.command.impl.GetStaffMainPageCommand;
import by.epam.ts.controller.command.impl.ShowTreatmentCommand;
import by.epam.ts.controller.command.impl.SignUpCommand;
import by.epam.ts.controller.command.impl.WrongRequestCommand;
import by.epam.ts.controller.constant_attribute.RequestAtribute;

public final class CommandProvider {
	
	private final static CommandProvider instance = new CommandProvider();	
	private final Map<CommandEnum, Command> repository = new HashMap<>();
	
	private CommandProvider() {
		repository.put(CommandEnum.LOGIN, new LoginCommand());
		repository.put(CommandEnum.LOGOUT, new LogoutCommand());
		repository.put(CommandEnum.SHOW_TREATMENT, new ShowTreatmentCommand());
		repository.put(CommandEnum.SIGN_UP, new SignUpCommand());
		repository.put(CommandEnum.SHOW_PATIENT_MAIN_PAGE, new ShowPatientMainPageCommand());
		repository.put(CommandEnum.GET_STAFF_MAIN_PAGE, new GetStaffMainPageCommand());
		repository.put(CommandEnum.SHOW_INDEX_PAGE, new ShowIndexPageCommand());
		repository.put(CommandEnum.SHOW_SIGNUP_PAGE, new ShowSignUpPageCommand());
		repository.put(CommandEnum.CHANGE_LANGUAGE, new ChangeLanguageCommand());
		repository.put(CommandEnum.SHOW_ERROR_PAGE, new ShowErrorPageCommand());
		repository.put(CommandEnum.GIVE_CONSENT, new GiveConsentCommand());
		repository.put(CommandEnum.SEARCH_PATIENT, new SearchPatientCommand());
		repository.put(CommandEnum.ADD_NEW_PATIENT, new AddNewPatientCommand());
		repository.put(CommandEnum.GET_PATIENT_DATA_PAGE, new GetPatientDataPageCommand());
		repository.put(CommandEnum.GET_CURRENT_PATIENT_PAGE, new GetCurrentPatientPageCommand());
		repository.put(CommandEnum.ADD_PATIENT_DIAGNOSIS, new AddPatientDiagnosisCommand());
		repository.put(CommandEnum.GET_DIAGNOSIS_PAGE, new GetDiagnosisPageCommand());
		repository.put(CommandEnum.ADD_NEW_DIAGNOSIS, new AddNewDiagnosisCommand());
		repository.put(CommandEnum.ADD_NEW_TREATMENT, new AddNewTreatmentCommand());
		repository.put(CommandEnum.GET_PRESCRIPTIONS_PAGE, new GetPrescriptionsPageCommand());
		repository.put(CommandEnum.ADD_NEW_STAFF, new AddNewStaffCommand());
		repository.put(CommandEnum.GET_STAFF_DATA_PAGE, new GetStaffDataPageCommand());
		repository.put(CommandEnum.WRONG_REQUEST, new WrongRequestCommand());
	}
	
	public static CommandProvider getInstance() {
		return instance;
	}
	
	public Command defineCommand(HttpServletRequest request) {
		
		Command current;
		String action = request.getParameter(RequestAtribute.COMMAND);
		if (action == null || action.isEmpty()) {
			current = repository.get(CommandEnum.WRONG_REQUEST);
			return current;
		}
		
		CommandEnum command = CommandEnum.valueOf(action.toUpperCase());
		current = repository.get(command);
		
		if (current == null) {
			current = repository.get(CommandEnum.WRONG_REQUEST);
		}
		
		return current;
	}

}
