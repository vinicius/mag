<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://www.jenia.org/jsf/popup" prefix="jp" %>

<f:verbatim>
<script type="text/javascript">
	function changeFilename() {
		var filenameArray = document.getElementById("customizeForm:fileOutputText").value.split(".");
		document.getElementById("customizeForm:fileOutputText").value = 
				filenameArray[0] + "." + document.getElementById("customizeForm:formatList").value;
	}

	function onlyNumbers(event) {
		var keynum;
		var keychar;
		var numcheck;
		if(window.event) { // IE
			keynum = event.keyCode;
		}
		else if(event.which) { // Netscape/Firefox/Opera
			keynum = event.which;
		}
		keychar = String.fromCharCode(keynum);
		numcheck = /[^a-zA-Z -.]/;
		return numcheck.test(keychar);
	}

	function mantainProportion(field) {
		if(document.getElementById("customizeForm:proportional").checked == true) {
			var width = parseFloat(document.getElementById("width").value);
			var height = parseFloat(document.getElementById("height").value);
			var proportion = width / height;
			var fieldW = document.getElementById("customizeForm:widthText");
			var fieldH = document.getElementById("customizeForm:heightText");
			if(field == document.getElementById("customizeForm:widthText")) {
				if (field.value != "") {
					fieldH.value = parseInt(parseFloat(fieldW.value) / proportion);
				}
				else {
					document.getElementById("customizeForm:heightText").value = ""
				}
			}
			else if(field == document.getElementById("customizeForm:heightText")) {
				if (field.value != "") {
					fieldW.value = parseInt(parseFloat(fieldH.value) * proportion);
				}
				else {
					document.getElementById("customizeForm:widthText").value = ""
				}
			}
			else {

			}
		}
	}
</script>
</f:verbatim>

<h:panelGrid columns="1" styleClass="content" rowClasses="contentTitle,contentBody" cellspacing="1" cellpadding="1">
	<h:panelGroup>
		<h:outputText value="#{msg['customize.title']}" styleClass="pageTitle"/>
		<h:form id="helpForm">
			<jp:popupFrame id="helpPopup" height="250px" width="350px" actionOpen="#{customizeConvertionBean.help}" center="true">
				<h:graphicImage value="/images/help.jpg" style="border-width: 0px"/>
			</jp:popupFrame>
		</h:form>
	</h:panelGroup>
	<h:panelGroup>
		<h:inputHidden id="width" value="#{customizeConvertionBean.videoIn.videoFormat.width}"/>
		<h:inputHidden id="height" value="#{customizeConvertionBean.videoIn.videoFormat.height}"/>

		<h:panelGrid columns="2">
			<h:outputText value="#{msg['customize.videoName']}" />
			<h:outputText value="#{customizeConvertionBean.videoIn.filename}" />
			<h:outputText value="#{msg['customize.resolution']}" />
			<h:outputText value="#{customizeConvertionBean.videoIn.videoFormat.width}x#{customizeConvertionBean.videoIn.videoFormat.height}" />
			<h:outputText value="#{msg['customize.fps']}" />
			<h:outputText value="#{customizeConvertionBean.videoIn.videoFormat.framesPerSecond}" />
			<h:outputText value="#{msg['customize.format']}" />
			<h:outputText value="#{customizeConvertionBean.videoIn.videoFormat.format}" />
			<h:outputText value="#{msg['customize.videoCodec']}" />
			<h:outputText value="#{customizeConvertionBean.videoIn.videoFormat.codec}" />
			<h:outputText value="#{msg['customize.audioCodec']}" />
			<h:outputText value="#{customizeConvertionBean.videoIn.audioFormat.codec}" />
		</h:panelGrid>
	
		<h:panelGroup><f:verbatim><hr/></f:verbatim></h:panelGroup>	
	
		<h:form id="customizeForm">
			<h:panelGrid columns="2">
				<h:panelGrid columns="2">
					<h:outputText value="#{msg['customize.resolution']}" />
					<h:panelGrid columns="1" align="right">
						<h:outputText value="#{msg['customize.width']}" style="font-size: 12px; text-align: right;"/>
						<h:outputText value="#{msg['customize.height']}" style="font-size: 12px; text-align: right;"/>
					</h:panelGrid>
				</h:panelGrid>
		
				<h:panelGrid columns="3" width="10">
					<h:panelGroup>
						<h:inputText id="widthText" size="6" value="#{customizeConvertionBean.videoOut.videoFormat.width}" required="true" onkeypress="return onlyNumbers(event)" onkeyup="mantainProportion(this)" onkeydown="mantainProportion(this)">
							<t:validateRegExpr pattern="^\d+$"/>
						</h:inputText>
						<h:inputText id="heightText" size="6" value="#{customizeConvertionBean.videoOut.videoFormat.height}" required="true" onkeypress="return onlyNumbers(event)" onkeyup="mantainProportion(this)" onkeydown="mantainProportion(this)">
							<t:validateRegExpr pattern="^\d+$"/>
						</h:inputText>
					</h:panelGroup>
						
					<h:selectBooleanCheckbox id="proportional" value="true" onclick="mantainProportion(this)"/>
					<t:graphicImage value="/images/chain.gif"/>					
				</h:panelGrid>
		
				<h:outputText value="#{msg['customize.fps']}" />
				<h:inputText id="fpsText" size="6" value="#{customizeConvertionBean.videoOut.videoFormat.framesPerSecond}" required="true">
					<t:validateRegExpr pattern="^\d+([.]\d+)?$" message="Frames per second: Invalid value"/>
				</h:inputText>
				
				<h:outputText value="#{msg['customize.format']}" />
				<t:selectOneMenu id="formatList" onchange="changeFilename()" value="#{customizeConvertionBean.videoOut.videoFormat.format}" required="true">
				    <f:selectItem itemValue="mp4" itemLabel="mp4" />
					<f:selectItem itemValue="mpg" itemLabel="mpg" />
					<f:selectItem itemValue="avi" itemLabel="avi" />
					<f:selectItem itemValue="wmv" itemLabel="wmv" />
				</t:selectOneMenu>
				
				 <h:outputText value="#{msg['customize.videoCodec']}" />
				<t:selectOneMenu id="videoCodecList" value="#{customizeConvertionBean.videoOut.videoFormat.codec}" required="true">
					<f:selectItem itemValue="mpeg4" itemLabel="MPEG-4" />
					<f:selectItem itemValue="wmv1" itemLabel="Windows Media Video" />
					<f:selectItem itemValue="mpeg2video" itemLabel="MPEG-2" />
				</t:selectOneMenu>
				
				 <h:outputText value="#{msg['customize.audioCodec']}" />
				<t:selectOneMenu id="audioCodecList" value="#{customizeConvertionBean.videoOut.audioFormat.codec}" required="true">
					<f:selectItem itemValue="aac" itemLabel="Advanced Audio Codec" />
					<f:selectItem itemValue="mp2" itemLabel="MPEG-2" />
				</t:selectOneMenu>
				
				<h:outputText value="#{msg['customize.outputFile']}" />	
				<h:inputText id="fileOutputText" onchange="changeFilename()" size="18" value="#{customizeConvertionBean.videoOut.filename}"/>

				<h:outputText value="#{msg['customize.nodes']}" />
				<h:selectOneMenu id="numberOfNodes" value="#{customizeConvertionBean.numberOfNodes}">
					<f:selectItem itemLabel="1" itemValue="1"/>
					<f:selectItem itemLabel="2" itemValue="2"/>
					<f:selectItem itemLabel="3" itemValue="3"/>
					<f:selectItem itemLabel="4" itemValue="4"/>
					<f:selectItem itemLabel="5" itemValue="5"/>
					<f:selectItem itemLabel="6" itemValue="6"/>
					<f:selectItem itemLabel="7" itemValue="7"/>
					<f:selectItem itemLabel="8" itemValue="8"/>
				</h:selectOneMenu>
			</h:panelGrid>
			
			<h:panelGrid columns="1" rowClasses="error">
				<h:messages layout="table" warnClass="warning" errorClass="error" infoClass="information" tooltip="true"/>
				<h:message for="widthText" warnClass="warning" errorClass="error" infoClass="information" tooltip="true"/>
				<h:message for="heightText" warnClass="warning" errorClass="error" infoClass="information" tooltip="true"/>
				<h:message for="fpsText" warnClass="warning" errorClass="error" infoClass="information" tooltip="true"/>
			</h:panelGrid>
			
			<t:commandButton id="convertButton" value="#{msg['customize.convertButton']}" styleClass="button" action="#{customizeConvertionBean.customConvertRequest}"/>
		</h:form>
	</h:panelGroup>
</h:panelGrid>