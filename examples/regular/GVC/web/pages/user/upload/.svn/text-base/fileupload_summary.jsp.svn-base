<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<h:panelGrid columns="1" styleClass="content" rowClasses="contentTitle,contentBody" cellspacing="1" cellpadding="1">
	<h:outputText value="#{msg['fileupload.sucess']}" styleClass="pageTitle" />
	<h:panelGroup>
		<h:form id="form2" enctype="multipart/form-data" >
			<h:panelGrid columns="1">
				<h:panelGroup>
			        <h:outputText value="#{msg['fileupload.arqName']} = " />
			        <h:outputText value="#{fileUploadBean.upFile.name}" />
				</h:panelGroup>
				<h:panelGroup>
					<h:outputText value="#{msg['fileupload.contenType']} = " />
			        <h:outputText value="#{fileUploadBean.upFile.contentType}" />
				</h:panelGroup>
				<h:panelGroup>
			        <h:outputText value="#{msg['fileupload.size']} = " />
			        <h:outputText value="#{fileUploadBean.upFile.size} bytes" />
				</h:panelGroup>
			</h:panelGrid>
	   	</h:form>
	</h:panelGroup>
</h:panelGrid>