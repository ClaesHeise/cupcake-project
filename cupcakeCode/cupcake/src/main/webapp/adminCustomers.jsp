<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
        <br>
        Kunder i systemet
    </jsp:attribute>

    <jsp:body>

        <br>
        <br>
        <c:if test="${sessionScope.customers == null}">
            <p>Der er ingen kunder i systemet</p>
        </c:if>
        <c:if test="${sessionScope.customers != null}">
            <table class="table table-dark table-striped">
                <thead>
                <tr>
                    <th scope="col">Kunde id</th>
                    <th scope="col">Navn</th>
                    <th scope="col">Mail</th>
                    <th scope="col">Penge</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${sessionScope.customers}" var="customer">
                    <tr>
                        <td>${Integer.parseInt(customer.getIdUser())}</td>
                        <td>${customer.getName()}</td>
                        <td>${customer.getMail()}</td>
                        <td>${Integer.parseInt(customer.getMoney())}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <br>
            <br>
            <br>
        </c:if>


    </jsp:body>
</t:pagetemplate>