<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
        <br>
        Nuværende ordre
    </jsp:attribute>

  <jsp:attribute name="footer">
            Login
    </jsp:attribute>

  <jsp:body>
      <br>
      <br>
      <c:if test="${sessionScope.completeOrders == null}">
          <p>Der er ingen ordre i systemet</p>
      </c:if>
      <c:if test="${sessionScope.completeOrders != null}">
          <table class="table table-dark table-striped">
              <thead>
              <tr>
                  <th scope="col">Kunde id</th>
                  <th scope="col">Bund</th>
                  <th scope="col">Top</th>
                  <th scope="col">Pris</th>
              </tr>
              </thead>
              <tbody>
                  <c:forEach items="${sessionScope.completeOrders}" var="order">
                      <tr>
                          <td>${Integer.parseInt(order.getIdCustomer())}</td>
                          <td>${order.getCupBot()}</td>
                          <td>${order.getCupTop()}</td>
                          <td>${Integer.parseInt(order.price())}</td>
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