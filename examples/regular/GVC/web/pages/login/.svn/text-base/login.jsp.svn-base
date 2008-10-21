<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<f:verbatim>
<style type="text/css">
	.goButton {
		font-weight: bold;
		background-color: LightSteelBlue;
		color: #294747;
		width: 30px;
		height: 20px;
	}	
	a.loginLink {
		color: black;
		font-size: x-small;
	}	
	a:visited.loginLink {
		color: black;
	}	
	a:hover.loginLink {
		color: blue;
		font-weight: bold;
	}	
	a:active.loginLink {
		color: #294747;
	}	
	a.selected.loginLink {
		color:#294747;
	}
	.loginTable {
		text-align: right;
		vertical-align: top;
	}
	.loginError {
		font-family : verdana, Geneva, Arial, Helvetica, sans-serif;
		color: #FF0000;
		font-size: x-small;
	}
	.loginText {
		font-size: x-small;
	}
</style>
</f:verbatim>

<h:form id="loginForm">
	<h:panelGrid columns="1" rendered="#{!loginBean.authenticated}" cellspacing="0" cellpadding="0" styleClass="loginTable">
		<h:panelGrid columns="2" align="right">
			<h:outputLabel for="email" value="#{msg['login.userID']} " styleClass="loginText"/>
			<h:inputText id="email" value="#{loginBean.email}" required="true" maxlength="20" size="20"/>
		</h:panelGrid>

		<h:panelGrid columns="2" align="right">
			<h:outputLabel for="password" value="#{msg['login.password']} " styleClass="loginText"/>
			<h:inputSecret id="password" value="#{loginBean.password}" required="true" maxlength="20" size="20"/>
		</h:panelGrid>

		<%-- Rendered = False -> Not implemented yet --%>
		<h:panelGrid columns="1" align="right">
			<h:commandButton id="loginButton" action="#{loginBean.login}" value="Go" styleClass="goButton"/>
<%--		<h:outputLink rendered="false" value="" styleClass="loginLink">
				<h:outputText value="#{msg['login.register']}"/>
			</h:outputLink>
			<h:outputLink rendered="false" value="" styleClass="loginLink">
				<h:outputText value="#{msg['login.forgetPassword']}"/>
			</h:outputLink> --%>
		</h:panelGrid>
		<h:message for="email" styleClass="loginError"/>
		<h:message for="password" styleClass="loginError"/>
	</h:panelGrid>
	
	<h:panelGrid columns="1" rendered="#{loginBean.authenticated}" cellspacing="2" cellpadding="2">
		<h:outputText value="#{msg['login.userID']}: #{loginBean.user.email}" styleClass="loginText"/>
		<h:outputText value="#{msg['login.group']}: (#{loginBean.user.group})" styleClass="loginText"/>
		<h:outputText rendered="false" value="#{loginBean.user.password}" styleClass="loginText"/>
		<h:commandLink id="logoutButton" value="#{msg['login.logout']}" action="#{loginBean.logout}"/>
	</h:panelGrid>
</h:form>