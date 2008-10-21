<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles"%>

<f:verbatim>
<style type="text/css">
	.label {
		width: 100px;
		text-align: right;
	}	
	.input {
		width: 200px;
		text-align: left;
	}	
</style>
</f:verbatim>

<h:panelGrid columns="1" styleClass="content" rowClasses="contentTitle,contentBody" cellspacing="1" cellpadding="1">
	<h:outputText value="#{msg['userDataManagement.title']}" styleClass="pageTitle" />
	<h:panelGroup>
		<h:panelGrid columns="1">
			<h:panelGroup>
				<h:form id="userManagementForm">
					<h:panelGrid columns="1" style="border: 1px solid black; width: 300px;" align="center">
						<h:panelGrid columns="2" align="center" columnClasses="label,input">
							<h:outputLabel for="email" value="#{msg['login.userID']}" styleClass="loginText"/>
							<h:outputText id="email" value="#{loginBean.user.email}" styleClass="loginText"/>
						</h:panelGrid>
						
						<h:panelGrid columns="2" rendered="true" align="center" columnClasses="label,input">
							<h:outputText value="#{msg['userDataManagement.password']}" styleClass="loginText"/>
							<h:inputText id="password" value="#{userDataManagementBean.password}" required="true" styleClass="loginText"/>
						</h:panelGrid>

						<h:panelGrid columns="1" align="center">
							<h:commandButton value="#{msg['userDataManagement.updateUser']}" id="updateUser" action="#{userDataManagementBean.updateUser}" styleClass="" onclick="ajaxAnywhere.submitAJAX();"/>
						</h:panelGrid>
						
						<h:panelGrid columns="1" align="center">
							<h:messages globalOnly="true" layout="table" warnClass="warning" errorClass="error" infoClass="information" tooltip="true"/>
							<h:message for="email" warnClass="warning" errorClass="error" infoClass="information" tooltip="true"/>
							<h:message for="password" warnClass="warning" errorClass="error" infoClass="information" tooltip="true"/>
						</h:panelGrid>
					</h:panelGrid>
				</h:form>
			</h:panelGroup>
		</h:panelGrid>
	</h:panelGroup>
</h:panelGrid>