<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
        <br>
             Kurv
    </jsp:attribute>

    <jsp:body>

                <c:if test="${sessionScope.completeOrders == null}">
                    <p>Ingen varer er lagt i kurven</p>
                </c:if>
                <c:if test="${sessionScope.completeOrders != null}">
                    <c:forEach items="${sessionScope.completeOrders}" var="order">
                        <div class="container-fluid mx-auto rounded-3 bg-light" style="width: 60%">
                            <div class="row mb-3">
                                <h3 class="col text-dark mx-auto" style="width: 90%">${order.toString()}</h3>
                            </div>
                        </div>
                    </c:forEach>
                    <h3 class="mx-auto">ialt: ${sessionScope.ialt} kr.</h3>
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/pay" role="button">Betal</a>
                </c:if>
    </jsp:body>
</t:pagetemplate>