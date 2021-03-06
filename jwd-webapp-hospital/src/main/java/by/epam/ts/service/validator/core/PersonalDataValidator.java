package by.epam.ts.service.validator.core;

public class PersonalDataValidator {
	
	 private static final String LOGIN_PATTERN = "^[a-zA-Z0-9_-]{3,20}$";
	 private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9_-]{5,20}$";
	 private static final String EMAIL_PATTERN = "^([a-z0-9_\\.-]+)@([a-z0-9_\\.-]+)\\.([a-z\\.]{2,6})$";
	 private static final String NAME_PATTERN = "^([\\p{L}]|[-]){2,45}$";
	 private static final String SURNAME_PATTERN = "^([\\p{L}]|[-]){2,45}$";
	 private static final String SPECIALTY_PATTERN = "^([\\p{L}]){5,10}$";
	 private static final String DATE_OF_BIRTH_PATTERN = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$";
	 private static final String PATIENT_ID_PATTERN = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}";
	 private static final String POSITIVE_USER_STATUS = "true";
	 private static final String NEGATIVE_USER_STATUS = "false";
	 private static final String USER_ROLE_DOCTOR = "DOCTOR";
	 private static final String USER_ROLE_ADMINISTRATOR = "ADMINISTRATOR";
	 
	
	public boolean validLogin(String login) {
		return (login != null) && (login.matches(LOGIN_PATTERN));	
	}
	
	public boolean validPassword(String password) {
		return (password != null) && (password.matches(PASSWORD_PATTERN));
	}
	
	public boolean validEmail(String email) {
		return (email != null) && (email.matches(EMAIL_PATTERN));
	}
	
	public boolean validName(String name) {
		return (name != null) && (name.matches(NAME_PATTERN));
	}
	
	public boolean validSurname(String surname) {
		return (surname != null) && (surname.matches(SURNAME_PATTERN));
	}
	
	public boolean validDateOfBirth(String date) {
		return (date != null) && (date.matches(DATE_OF_BIRTH_PATTERN));
	}
	
	public boolean validID(String id) {
		return (id != null) && (id.matches(PATIENT_ID_PATTERN));
	}
	
	public boolean validSpesialty(String specialty) {
		return (specialty != null) && (specialty.matches(SPECIALTY_PATTERN));
	}
	
	public boolean validUserStatus(String status) {
		return ((status.equals(POSITIVE_USER_STATUS)) || (status.equals(NEGATIVE_USER_STATUS)));
	}
	
	public boolean validStaffUserRole(String role) {
		return ((role.equals(USER_ROLE_DOCTOR)) || (role.equals(USER_ROLE_ADMINISTRATOR)));
	}

}
