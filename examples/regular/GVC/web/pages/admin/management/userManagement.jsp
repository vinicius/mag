<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles"%>
<%@ taglib uri="http://ajaxanywhere.sourceforge.net/" prefix="aa" %>

<%@ page import="org.ajaxanywhere.AAUtils"%>
<% 
    if (AAUtils.isAjaxRequest(request)) {
        AAUtils.addZonesToRefresh(request, "userData");
        AAUtils.addZonesToRefresh(request, "userList");
    }
%>

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
<script>
	ajaxAnywhere.getZonesToReload = function(url, submitButton) {
	    if (typeof submitButton != 'undefined') {
	        if (submitButton.id.search('userManagementForm:removeUser') != -1 ||
				submitButton.id.search('userManagementForm:addUser') != -1) {
					return 'userList';
				}
		}
		return "userData";
	}
	ajaxAnywhere.formName = "selectUserForm";
</script>
</f:verbatim>

<h:panelGrid columns="1" styleClass="content" rowClasses="contentTitle,contentBody" cellspacing="1" cellpadding="1">
	<h:outputText value="#{msg['userManagement.title']}" styleClass="pageTitle" />
	<h:panelGroup>
		<h:panelGrid columns="1">
			<h:panelGroup>
				<h:form id="userManagementForm">
					<h:panelGrid columns="1" style="border: 1px solid black; width: 300px;" align="center">
						<aa:zoneJSF id="userData">			
							<h:panelGrid columns="2" align="center" columnClasses="label,input">
								<h:outputLabel for="email" value="#{msg['login.userID']}" styleClass="loginText"/>
								<h:inputText id="email" value="#{userManagementBean.email}" required="true" rendered="true" styleClass="loginText"/>
							</h:panelGrid>
							
							<h:panelGrid columns="2" rendered="true" align="center" columnClasses="label,input">
								<h:outputText value="#{msg['userManagement.password']}" styleClass="loginText"/>
								<h:inputSecret id="password" value="#{userManagementBean.password}" required="true" rendered="true" styleClass="loginText"/>
							</h:panelGrid>
							
							<h:panelGrid columns="2" align="center" rendered="#{loginBean.admin}" columnClasses="label,input">
								<h:outputLabel for="userGroup" value="#{msg['userManagement.group']}" styleClass="loginText"/>
								<h:selectOneMenu id="userGroup" rendered="true" required="true" value="#{userManagementBean.group}">
									<f:selectItem itemValue="Admin" itemLabel="Admin"/>
									<f:selectItem itemValue="User" itemLabel="User"/>
								</h:selectOneMenu>
							</h:panelGrid>
							<h:panelGrid columns="3" align="center">
								<h:commandButton value="#{msg['userManagement.removeUser']}" id="removeUser" action="#{userManagementBean.removeUser}" styleClass="" onclick="ajaxAnywhere.submitAJAX();"/>
								<h:commandButton value="#{msg['userManagement.addUser']}" id="addUser" action="#{userManagementBean.addUser}" styleClass="" onclick="ajaxAnywhere.submitAJAX();"/>
								<h:commandButton value="#{msg['userManagement.updateUser']}" id="updateUser" action="#{userManagementBean.updateUser}" styleClass="" onclick="ajaxAnywhere.submitAJAX();"/>
							</h:panelGrid>
							<h:panelGrid columns="1" align="center">
								<h:messages globalOnly="true" layout="table" warnClass="warning" errorClass="error" infoClass="information" tooltip="true"/>
								<h:message for="email" warnClass="warning" errorClass="error" infoClass="information" tooltip="true"/>
								<h:message for="password" warnClass="warning" errorClass="error" infoClass="information" tooltip="true"/>
							</h:panelGrid>
						</aa:zoneJSF>
					</h:panelGrid>
				</h:form>
			</h:panelGroup>

			<aa:zoneJSF id="userList">			
				<h:form id="selectUserForm">
					<h:panelGrid columns="2" rendered="#{loginBean.admin}">
						<h:outputLabel for="users" value="#{msg['userManagement.pickAnUser']}"/>
						<h:selectOneMenu id="users" onchange="ajaxAnywhere.submitAJAX();" valueChangeListener="#{userManagementBean.selectUserValueChangeListener}">
							<f:selectItems value="#{userManagementBean.userList}"/>
						</h:selectOneMenu>
					</h:panelGrid>
				</h:form>
			</aa:zoneJSF>
		</h:panelGrid>
	</h:panelGroup>
</h:panelGrid>