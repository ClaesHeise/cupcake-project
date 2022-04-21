<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
        <br>
         Byg din egen cupcake
    </jsp:attribute>

    <jsp:body>

        <h2>Vælg Bund:</h2>
        <form action="addorder" method="post" id="cupcakeForm">
            <div class="container">
                <div class="row justify-content-start">
                    <div class="col" style="width: 20%">
                        <label>
                            <input type="radio" name="bottom" value="1" checked>
                            <img src="${pageContext.request.contextPath}/images/Chokolade2BT.jpg" width="100%">
                            <p>Chokolade</p>
                            <p>5 kr.</p>
                        </label>
                    </div>
                    <div class="col" style="width: 20%">
                        <label>
                            <input type="radio" name="bottom" value="2">
                            <img src="${pageContext.request.contextPath}/images/Vanila2BT.jpg" width="100%">
                            <p>Vanilje</p>
                            <p>5 kr.</p>
                        </label>
                    </div>
                    <div class="col" style="width: 20%">
                        <label>
                            <input type="radio" name="bottom" value="3">
                            <img src="${pageContext.request.contextPath}/images/Nutmeg2BT.jpg" width="100%">
                            <p>Muskatnød</p>
                            <p>5 kr.</p>
                        </label>
                    </div>
                    <div class="w-100"></div>
                    <div class="col" style="width: 20%">
                        <label>
                            <input type="radio" name="bottom" value="4">
                            <img src="${pageContext.request.contextPath}/images/Pistachio2BT.jpg" width="100%">
                            <p>Pistacie</p>
                            <p>6 kr.</p>
                        </label>
                    </div>
                    <div class="col" style="width: 20%">
                        <label>
                            <input type="radio" name="bottom" value="5">
                            <img src="${pageContext.request.contextPath}/images/Almond2BT.jpg" width="100%">
                            <p>Mandel</p>
                            <p>7 kr.</p>
                        </label>
                    </div>
                </div>
            </div>
            <br>
            <br>
            <h2>Vælg topping:</h2>
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col" style="width: 20%">
                        <label>
                            <input type="radio" name="topping" value="1" checked>
                            <img src="${pageContext.request.contextPath}/images/Chokolade2TP.jpg" width="100%">
                            <p>Chokolade</p>
                            <p>5 kr.</p>
                        </label>
                    </div>
                    <div class="col" style="width: 20%">
                        <label>
                            <input type="radio" name="topping" value="2">
                            <img src="${pageContext.request.contextPath}/images/Blueberry2TP.jpg" width="100%">
                            <p>Blåbær</p>
                            <p>5 kr.</p>
                        </label>
                    </div>
                    <div class="col" style="width: 20%">
                        <label>
                            <input type="radio" name="topping" value="3">
                            <img src="${pageContext.request.contextPath}/images/Raspberry2TP.jpg" width="100%">
                            <p>Hindbær</p>
                            <p>5 kr.</p>
                        </label>
                    </div>
                    <div class="w-100"></div>
                    <div class="col" style="width: 20%">
                        <label>
                            <input type="radio" name="topping" value="4">
                            <img src="${pageContext.request.contextPath}/images/Crispy2TP.jpg" width="100%">
                            <p>Crispy</p>
                            <p>6 kr.</p>
                        </label>
                    </div>

                    <div class="col" style="width: 20%">
                        <label>
                            <input type="radio" name="topping" value="5">
                            <img src="${pageContext.request.contextPath}/images/Strawberry2TP.jpg" width="100%">
                            <p>Jordbær</p>
                            <p>6 kr.</p>
                        </label>
                    </div>
                    <div class="col" style="width: 20%">
                        <label>
                            <input type="radio" name="topping" value="6">
                            <img src="${pageContext.request.contextPath}/images/RumRaisin2TP.jpg" width="100%">
                            <p>Rom og rosin</p>
                            <p>7 kr.</p>
                        </label>
                    </div>
                    <div class="w-100"></div>
                    <div class="col" style="width: 20%">
                        <label>
                            <input type="radio" name="topping" value="7">
                            <img src="${pageContext.request.contextPath}/images/Orange2TP.jpg" width="100%">
                            <p>Appelsin</p>
                            <p>8 kr.</p>
                        </label>
                    </div>
                    <div class="col" style="width: 20%">
                        <label>
                            <input type="radio" name="topping" value="8">
                            <img src="${pageContext.request.contextPath}/images/Lemon2TP.jpg" width="100%">
                            <p>Citron</p>
                            <p>8 kr.</p>
                        </label>
                    </div>
                    <div class="col" style="width: 20%">
                        <label>
                            <input type="radio" name="topping" value="9">
                            <img src="${pageContext.request.contextPath}/images/BlueCheese2TP.jpg" width="100%">
                            <p>Blå ost</p>
                            <p>9 kr.</p>
                        </label>
                    </div>
                </div>
            </div>
            <br>
            <br>
            <div class="container">
                <div class="row">
                    <div class="col w-40 align-self-start" style="width: 40%">
                        <h2>Vælg antal:</h2>
                    </div>
                    <div class="col w-15 align-self-start" style="width: 15%">
                        <input type="number" name="amount" id="amountbutton">
                    </div>
                    <div class="col w-45 align-self-start" style="width: 45%">
                        <h3>stks.</h3>
                    </div>
                </div>
            </div>
            <div>
                <button type="submit">Tilføj</button>
            </div>
        </form>
        <br>

    </jsp:body>

</t:pagetemplate>