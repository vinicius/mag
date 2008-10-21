<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>

<h:panelGrid columns="2" align="center">
	<h:outputLink value="#{facesContext.externalContext.requestContextPath}/pages/common/credits.jsf">
		<h:outputText value="#{msg['about.credits']}"/>
	</h:outputLink>
	<h:outputLink value="http://eclipse.ime.usp.br/wiki/index.php/ConversorDeV%C3%ADdeo">
		<h:outputText value="#{msg['about.wiki']}"/>
	</h:outputLink>
</h:panelGrid>