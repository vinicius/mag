<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<f:verbatim>
<style type="text/css">
	.column1 {
		text-align: left;
		width: 125px;
	}
	.column2 {
		text-align: center;
		width: 125px;
	}
	.column3 {
		text-align: center;
		width: 125px;
	}
	.column4 {
		text-align: center;
		width: 125px;
	}
</style>
</f:verbatim>

<h:panelGrid columns="1" styleClass="content" rowClasses="contentTitle,contentBody" cellspacing="1" cellpadding="1">
	<h:outputText value="#{msg['convertionList.title']}" styleClass="pageTitle" />
	<h:outputText value="#{msg['convertionList.subtitle']}" styleClass="pageSubTitle" />
	<h:panelGroup>
		<h:messages layout="table" warnClass="warning" errorClass="error" infoClass="information" tooltip="true"/>
	
		<h:outputText value="#{msg['emptyFileList']}" rendered="#{convertionBean.amountOfVideoFiles < 1}" />
		<h:panelGroup>
			<h:form id="convertionForm">
				<h:panelGrid columns="1">
					<t:dataTable id="filesTable"
								var="videoFile"
							renderedIfEmpty="false"
				            value="#{convertionBean.videoFileList}"
				            rows="5"
				            border="0"
				            styleClass="scrollerTable"
				            headerClass="scrollerTableHeader"
				            footerClass="scrollerTableFooter"
				            rowClasses="scrollerTableRow1,scrollerTableRow2"
				            columnClasses="column1,column2,column3,column4">
				   
						<h:column>
							<f:facet name="header">
								<h:outputText value="#{msg['convetionList.originalFiles']}"/>
							</f:facet>
							<t:popup
								styleClass="popup"
								closePopupOnExitingElement="true"
								closePopupOnExitingPopup="true"
								displayAtDistanceX="10"
								displayAtDistanceY="10">					
									<h:outputText value="#{videoFile.original.filename}" />
									<f:facet name="popup">
										<h:panelGroup>
											<h:panelGrid columns="1" >
												<h:outputText value="Size: #{videoFile.original.size}KB (#{videoFile.original.sizeMB}MB)"/>
												<h:outputText value="Video Codec: #{videoFile.original.videoFormat.codec}"/>
												<h:outputText value="Resolution: #{videoFile.original.videoFormat.width} x #{videoFile.original.videoFormat.height}"/>
												<h:outputText value="FPS: #{videoFile.original.videoFormat.framesPerSecond}"/>
												<h:outputText value="Bitrate: #{videoFile.original.videoFormat.bitrate}"/>
												<h:outputText value="Audio Codec: #{videoFile.original.audioFormat.codec}"/>
												<h:outputText value="Bitrate: #{videoFile.original.audioFormat.bitrate}"/>
												<h:outputText value="Stereo/Mono: #{videoFile.original.audioFormat.soundSystem}"/>
											</h:panelGrid>
										</h:panelGroup>
									</f:facet>
							</t:popup>
						</h:column>
						
						<h:column>
							<h:commandButton id="iPodButton" value="#{msg['convertionList.iPodConvertButton']}" styleClass="button" action="#{convertionBean.iPodConvertRequest}" />
						</h:column>
				
						<h:column>
							<f:facet name="header">
								<h:outputText value="#{msg['convetionList.convertOption']}"/>
							</f:facet>
							<h:commandButton id="palmButton" value="#{msg['convertionList.palmConvertButton']}" styleClass="button" action="#{convertionBean.palmConvertRequest}" />
						</h:column>
				
						<h:column>
							<h:commandButton id="customizeButton" value="#{msg['convertionList.customizeButton']}" styleClass="button" action="#{convertionBean.customizeRequest}" />
						</h:column>		
						
						<f:facet name="footer">
					        <t:dataScroller	id="filesTableScroller"
									for="filesTable"
									fastStep="2"
									paginatorMaxPages="5"
									pageCountVar="pageCount"
									pageIndexVar="pageIndex"
									paginator="true"
									renderFacetsIfSinglePage="false"
									styleClass="dataScrollerStyleClass"
									paginatorTableClass="dsPaginatorTableClass"
									paginatorColumnClass="dataScrollerPaginatorColumnClass"
									paginatorActiveColumnClass="dataScrollerPaginatorActiveColumnClass">
				
					            <f:facet name="first">
						            <t:graphicImage url="/images/scrollerTable/arrowFirst.jpg" border="1" />
					            </f:facet>
					            <f:facet name="last">
						            <t:graphicImage url="/images/scrollerTable/arrowLast.jpg" border="1" />
					            </f:facet>
					            <f:facet name="previous">
									<t:graphicImage url="/images/scrollerTable/arrowPrevious.jpg" border="1" />
					            </f:facet>
					            <f:facet name="next">
					                <t:graphicImage url="/images/scrollerTable/arrowNext.jpg" border="1" />
					            </f:facet>
					            <f:facet name="fastforward">
					                <t:graphicImage url="/images/scrollerTable/arrowFastForward.jpg" border="1" />
					            </f:facet>
					            <f:facet name="fastrewind">
					                <t:graphicImage url="/images/scrollerTable/arrowFastRewind.jpg" border="1" />
					            </f:facet>
					        </t:dataScroller>
						</f:facet>
					</t:dataTable>
					<h:panelGrid columns="2" rendered="#{convertionBean.amountOfVideoFiles > 0}">
						<h:outputText value="#{msg['convertionList.nodes']}" />
						<h:selectOneMenu id="numberOfNodes" value="#{convertionBean.numberOfNodes}">
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
				</h:panelGrid>
			</h:form>
		</h:panelGroup>
	</h:panelGroup>
</h:panelGrid>