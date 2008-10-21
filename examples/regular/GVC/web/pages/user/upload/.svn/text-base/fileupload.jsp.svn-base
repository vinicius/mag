<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<f:verbatim>
<script>
	function showUploading() {
		messageLine.innerHTML = "Uploading file, please wait...";
	}
</script>
</f:verbatim>

<h:panelGrid columns="1" styleClass="content" rowClasses="contentTitle,contentBody" cellspacing="1" cellpadding="1">
	<h:outputText value="#{msg['fileupload.title']}" styleClass="pageTitle" />
	<h:panelGroup>
		<h:panelGroup rendered="#{fileUploadBean.wrong}">
			<h:outputText value="- #{msg['fileUploadBean.errorStatus']}" styleClass="warning" />
		</h:panelGroup>
		
		<h:messages id="messageList" globalOnly="true" warnClass="warning" errorClass="error" infoClass="information" tooltip="true"/>
		
		<h:form id="uploadForm" enctype="multipart/form-data" >
			<h:panelGrid columns="1">
				<h:panelGroup>
					<h:outputLabel for="fileupload" value="#{msg['fileupload.file']} "/>
					<t:inputFileUpload id="fileupload"
				                           value="#{fileUploadBean.upFile}"
				                           storage="file"
				                           styleClass="fileUploadInput"
				                           style="button"
				                           required="true"/>
				</h:panelGroup>
				<h:message for="fileupload" styleClass="error"/>
				<h:commandButton id="uploadButton" value="#{msg['fileupload.buttonSubmit']}"
					styleClass="button" action="#{fileUploadBean.upload}" onclick="showUploading()"/>
			</h:panelGrid>
			<f:verbatim><span id=messageLine></span></f:verbatim>
		</h:form>
	</h:panelGroup>
</h:panelGrid>