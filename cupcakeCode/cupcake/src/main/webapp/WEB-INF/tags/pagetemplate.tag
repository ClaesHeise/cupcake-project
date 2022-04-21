<%@ tag import="dat.cupcake.model.entities.CompleteOrder" %>
<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>

<!DOCTYPE html>
<html lang="da">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><jsp:invoke fragment="header"/></title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<header>
    <div class="container w-95 mt-2 rounded">
        <a class="navbar-brand rounded" href="index.jsp">
            <img src="${pageContext.request.contextPath}/images/headerPic.png" width="100%;" class="img-fluid rounded"/>
        </a>
    </div>
</header>

<nav class="navbar navbar-expand-lg navbar-light">
    <div class="container w-95 h-25 bg-light mt-2 rounded">
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup"
                aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-start" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <c:if test="${sessionScope.user == null }">
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/index.jsp">Byg din cupcake</a>
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/orders.jsp">Ordre</a>
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/login.jsp">Login</a>
                </c:if>
                <c:if test="${sessionScope.user != null && sessionScope.user.getRole().equals('user')}">
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/index.jsp">Byg din cupcake</a>
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/orders.jsp">Ordre</a>
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/">Profil</a>
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/logout">Log ud</a>
                </c:if>
                <c:if test="${sessionScope.user != null && sessionScope.user.getRole().equals('admin')}">
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/adminOrders.jsp">Se ordre</a>
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/adminCustomers.jsp">Se kunder</a>
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/">Profil</a>
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/logout">Log ud</a>
                </c:if>
            </div>
        </div>
        <div class="navbar justify-content-end">
            <c:if test="${sessionScope.user == null }">
                <i class="fa fa-envelope-o"></i>
                <a> something@mail.dk</a>
            </c:if>
            <c:if test="${sessionScope.user != null }">
                <i class="fa fa-envelope-o"></i>
                <a> ${sessionScope.user.getMail()}</a>
            </c:if>
            <div class="dropdown">
                <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                    <i class="fa fa-cart-plus" aria-hidden="true"></i>
                </button>
                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuButton1">
                    <c:if test="${sessionScope.completeOrders.size() < 1}">
                        <li><a class="dropdown-item">Ingen varer er lagt i kurven</a></li>
                    </c:if>
                    <c:if test="${sessionScope.completeOrders.size() > 0}">
<%--                        <% int ialt = 0; %>--%>
                        <%--<li><a class="dropdown-item">I'm not null</a></li>--%>
                        <c:forEach items="${sessionScope.completeOrders}" var="order">
                            <%--<li><a class="dropdown-item">I'm not null</a></li>--%>
                            <li><p class="dropdown-item-text">${order.toString()}</p></li>
                            <%-- Maybe add ialt --%>
                        </c:forEach>
                        <li><p class="dropdown-item-text">ialt: ${sessionScope.ialt} kr.</p> </li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/orders.jsp">Gå til ordre</a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>
</nav>

<div data-bs-spy="scroll" id="body" class="container mt-2 rounded" style="width: 95%;">
    <h1><jsp:invoke fragment="header"/></h1>
    <jsp:doBody/>
</div>

<!-- Footer -->
<div class="container-fluid mt-2 rounded-3" style="width: 95%;" style="height: 5%;">
    <hr/>
    <div class="row mt-4">
        <div class="col border-end border-3">
            <p class="fw-bold">Adresse</p>
            Cupcakevej 66<br/>
            Bornholm
        </div>
        <div class="col border-end border-3">
            <p class="fw-bold">Om os</p>
            Vi er et lille bageri,<br/>
            der bare elsker at lave cupcakes
        </div>
        <div class="col">
            <p class="fw-bold">Kontakt</p>
            <i class="fa fa-mobile" aria-hidden="true"></i> +45 67 43 28 73<br/>
            <i class="fa fa-envelope-o"></i> OlskerCupcakes@gmail.com
        </div>
    </div>

</div>

</div>

<!-- Bootstrap Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>

</body>
</html>