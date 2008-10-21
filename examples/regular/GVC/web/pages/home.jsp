<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<h:panelGrid columns="1" styleClass="content" rowClasses="contentTitle,contentBody" cellspacing="1" cellpadding="1">
	<h:outputText value="#{msg['app.home']}" rendered="#{!loginBean.authenticated}" styleClass="pageTitle" />
	<h:outputText value="#{msg['app.home']}, #{loginBean.user.email}" rendered="#{loginBean.authenticated}" styleClass="pageTitle" />
</h:panelGrid>