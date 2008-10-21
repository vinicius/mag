<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://ajaxanywhere.sourceforge.net/" prefix="aa" %>

<%@ page import="org.ajaxanywhere.AAUtils"%>
<% 
    if (AAUtils.isAjaxRequest(request)) {
        AAUtils.addZonesToRefresh(request, "convertedListArea");
    }
%>

<f:verbatim>
<script>
	ajaxAnywhere.getZonesToReload = function(url, submitButton) {
		return "convertedListArea";
	    if (typeof submitButton != 'undefined') {
	        if (submitButton.id.search('fileManagementForm:removeOriginalButton') != -1 ||
				submitButton.id.search('fileManagementForm:removeConvertedButton') != -1) {
					return 'convertedListArea';
				}
		}
		return "convertedListArea";
	}
	ajaxAnywhere.formName = "fileManagementForm";
</script>
</f:verbatim>

<h:panelGrid columns="1" styleClass="content" rowClasses="contentTitle,contentBody" cellspacing="1" cellpadding="1">
	<h:outputText value="#{msg['fileManagement.title']}" styleClass="pageTitle" />
	<aa:zoneJSF id="convertedListArea">
		<h:panelGroup>
			<h:outputText value="#{msg['emptyFileList']}" rendered="#{fileManagementBean.videoFile == null}"/>
			<h:form id="fileManagementForm">
				<h:panelGrid columns="2" rendered="#{fileManagementBean.videoFile != null}">
					<h:outputText value="Original"/>
					<h:outputText value="Converted"/>				
					
					<h:selectOneListbox id="original" required="true" value="#{fileManagementBean.originalSelected}" valueChangeListener="#{fileManagementBean.selectOriginalVideoChangeListener}" onchange="ajaxAnywhere.submitAJAX();" size="10" style="font-size: x-small; width: 250px; vertical-align: top">
						<f:selectItems value="#{fileManagementBean.originalVideos}"/>
					</h:selectOneListbox>	
					<h:selectManyListbox id="convertedList" value="#{fileManagementBean.convertedSelected}" size="10" style="font-size: x-small; width: 250px; vertical-align: top">
						<f:selectItems value="#{fileManagementBean.convertedVideoList}"/>
					</h:selectManyListbox>
					
					<h:commandButton id="removeOriginalButton" value="Remove Original" action="#{fileManagementBean.removeOriginalVideo}" onclick="ajaxAnywhere.submitAJAX();"/>
					<h:commandButton id="removeConvertedButton" value="Remove Converted" action="#{fileManagementBean.removeConvertedVideos}" onclick="ajaxAnywhere.submitAJAX();"/>
				</h:panelGrid>
				<h:message for="original" warnClass="warning" errorClass="error" infoClass="information" tooltip="true"/>
				<h:message for="convertedList" warnClass="warning" errorClass="error" infoClass="information" tooltip="true"/>
			</h:form>
		</h:panelGroup>
	</aa:zoneJSF>
</h:panelGrid>
