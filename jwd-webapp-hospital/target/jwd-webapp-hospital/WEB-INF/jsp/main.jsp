<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<html>

<head>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="style/style.css"/>

<title>Main-page</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.locale" var="loc" />
<fmt:message bundle="${loc}" key="local.main.update_patient" var="update_patient_data" />
<fmt:message bundle="${loc}" key="local.main.showtreat" var="show_treat" />
<fmt:message bundle="${loc}" key="local.main.get.consent" var="get_consent" />
<fmt:message bundle="${loc}" key="local.main.calc_hospitalization" var="calc_hospitalization" />
<fmt:message bundle="${loc}" key="local.main.welcome" var="welcome" />
<fmt:message bundle="${loc}" key="local.main.navigate_login" var="navigate_login" />
<fmt:message bundle="${loc}" key="local.main.logout_btn" var="logout_button" />
<fmt:message bundle="${loc}" key="local.main.denied" var="access_denied" />
<fmt:message bundle="${loc}" key="local.main.data.unavailable" var="data_unavailable" />

<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
	
</head>

<body>

	<!-- Logout button -->
		
	<form name="Logout_form" method="POST" action="font" class="float-right">
		<input type="hidden" name="command" value="logout" /> 
		<button type="submit" class="btn btn-link">${logout_button}</button>
	</form>

	 <!-- Change language buttons -->
	
	<form class="ml-2" action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="ru" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/> 
		<button type="submit" class="btn btn-secondary">${ru_button}</button>
	</form>

	<form class="ml-2" action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>
		<button type="submit" class="btn btn-secondary">${en_button}</button>
	</form>
	
	<!-- Navigation menu -->
	
	<!--Main Navigation-->
<header>

  <nav class="navbar fixed-top navbar-expand-lg navbar-dark pink scrolling-navbar">
    <a class="navbar-brand" href="#"><strong>Navbar</strong></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
      aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav mr-auto">
        <li class="nav-item active">
          <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">Features</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">Pricing</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">Opinions</a>
        </li>
      </ul>
      <ul class="navbar-nav nav-flex-icons">
        <li class="nav-item">
          <a class="nav-link"><i class="fab fa-facebook-f"></i></a>
        </li>
        <li class="nav-item">
          <a class="nav-link"><i class="fab fa-twitter"></i></a>
        </li>
        <li class="nav-item">
          <a class="nav-link"><i class="fab fa-instagram"></i></a>
        </li>
      </ul>
    </div>
  </nav>

</header>
<!--Main Navigation-->
	
	
	
	
	
	
	
	
	
	<nav class="navbar navbar-dark navbar-expand-lg bg-company-red">
	
		<form class="form-inline" name="update_patient_data" action="font" method="GET">
  			<input type="hidden" name="command" value="get_update_patient_data_page" />
  			<button type="submit" class="btn btn-sm btn-outline-secondary">${update_patient_data}</button>
        </form>
	
		<form class="form-inline" name="get_treatment" method="GET" action="font">
			<input type="hidden" name="command" value="get_treatment_page" /> 
			<button type="submit" class="btn btn-sm btn-outline-secondary">${show_treat}</button>
		</form>
		
		<form class="form-inline" name="calc_hospitalization" method="GET" action="font">
			<input type="hidden" name="command" value="get_hospitalization_plan" /> 
			<button type="submit" class="btn btn-sm btn-outline-secondary">${calc_hospitalization}</button>
		</form>
		
	</nav>
	
	<div class="ml-4">
  		<h1>${welcome}</h1>
  	</div>
  		
  	<img src="img/background_theme.png" alt="stethoscope_image" class="ml-4">
  		
  	<!-- Alerts -->		

	<c:if test="${not empty requestScope.access_denied}">
		<c:out value="${access_denied}"/>
	</c:if>
	
	<c:if test="${not empty requestScope.data_unavailable}">
		<c:out value="${data_unavailable}"/>
	</c:if>
		
	<hr>
		
	<!-- Footer -->
		
	<div id="footer">
    	<jsp:include page="/WEB-INF/jsp/part/footer.jsp"/>
	</div>

</body>
</html>