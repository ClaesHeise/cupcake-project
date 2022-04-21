<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>


    <jsp:body>

        <c:if test="${sessionScope.pay == null}">
            <h2>Der var ikke nok penge på kontoen</h2>
            <br>
            <h3>Spørg admin efter flere midler</h3>
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/index.jsp" role="button">Tilbage til forsiden</a>
        </c:if>
        <c:if test="${sessionScope.pay ==  'paid'}">
            <h2>Varerne er gået igennem og betalt for!</h2>
            <br>
            <h3>Se frem til dine dejlige cupcakes!</h3>
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/index.jsp" role="button">Tilbage til forsiden</a>
        </c:if>

    </jsp:body>
</t:pagetemplate>