<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<h:panelGrid columns="1" styleClass="content" rowClasses="contentTitle,contentBody" cellspacing="1" cellpadding="1">
	<h:outputText value="#{msg['downloadList.title']}" styleClass="pageTitle" />
	<h:panelGroup>
		<h:outputText value="#{msg['emptyFileList']}" rendered="#{convertionBean.amountOfVideoFiles < 1}" />
		<h:panelGroup>
			<t:dataTable
					id="filesTable"
					var="videoFile"
					value="#{convertionBean.videoFileList}"
					preserveDataModel="false"
					rows="5"
					renderedIfEmpty="false"
					styleClass="scrollerTable"
					headerClass="scrollerTableHeader"
					footerClass="scrollerTableFooter"
					rowClasses="scrollerTableRow1,scrollerTableRow2"
					columnClasses="scrollerTableColumn1,scrollerTableColumn2">
		
				<h:column>
		        	<f:facet name="header">
		         	   <h:outputText value="Original"/>
		            </f:facet>
					<t:popup
						styleClass="popup"
						closePopupOnExitingElement="true"
						closePopupOnExitingPopup="true"
						displayAtDistanceX="10"
						displayAtDistanceY="10">
		
			            <h:outputLink value="/GVC/files/upload/#{videoFile.original.idFilename}">
							<h:outputText value="#{videoFile.original.filename}"/>
						</h:outputLink>			
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
					<f:facet name="header">
		         	   <h:outputText value="Converted" />
		            </f:facet>	            
				    <t:dataTable id="convertedTable"
		                     var="aux"
		                     value="#{videoFile.aux}"
		                     preserveDataModel="false"
		                     rows="5"
		                     preserveSort="true"
		                     align="center">
		
		                <t:columns id="convertedColumns" value="#{videoFile.converted}" var="videoFile">
							<t:popup
								styleClass="popup"
								closePopupOnExitingElement="true"
								closePopupOnExitingPopup="true"
								displayAtDistanceX="10"
								displayAtDistanceY="10">
									
									<h:outputLink value="/GVC/files/download/#{videoFile.idFilename}">
										<h:graphicImage value="/images/fileFormat/#{videoFile.videoFormat.format}.jpg" style="border: thin solid white"/>
									</h:outputLink>
									<f:facet name="popup">
										<h:panelGroup>
											<h:panelGrid columns="1" >
												<h:outputText value="Size: #{videoFile.size}KB (#{videoFile.sizeMB}MB)"/>
												<h:outputText value="Video Codec: #{videoFile.videoFormat.codec}"/>
												<h:outputText value="Resolution: #{videoFile.videoFormat.width} x #{videoFile.videoFormat.height}"/>
												<h:outputText value="FPS: #{videoFile.videoFormat.framesPerSecond}"/>
												<h:outputText value="Bitrate: #{videoFile.videoFormat.bitrate}"/>
												<h:outputText value="Audio Codec: #{videoFile.audioFormat.codec}"/>
												<h:outputText value="Bitrate: #{videoFile.audioFormat.bitrate}"/>
												<h:outputText value="Stereo/Mono: #{videoFile.audioFormat.soundSystem}"/>
											</h:panelGrid>
										</h:panelGroup>
									</f:facet>
							</t:popup>
		            	</t:columns>
		        	</t:dataTable>			
				</h:column>
		    	
		    	<f:facet name="footer">
		    		<t:dataScroller id="filesTableScroller"
		                        for="filesTable"
		                        fastStep="5"
		                        pageCountVar="pageCount"
		                        pageIndexVar="pageIndex"
		                        paginator="true"
		                        paginatorMaxPages="5"
		                        renderFacetsIfSinglePage="false"
								styleClass="dataScrollerStyleClass"
								paginatorTableClass="dsPaginatorTableClass"
								paginatorColumnClass="dataScrollerPaginatorColumnClass"
								paginatorActiveColumnClass="dataScrollerPaginatorActiveColumnClass">
		
			            <f:facet name="first">
				            <t:graphicImage styleClass="a:link{color: white;};" url="/images/scrollerTable/arrowFirst.jpg" border="1"/>
			            </f:facet>
			            <f:facet name="last">
				            <t:graphicImage url="/images/scrollerTable/arrowLast.jpg" border="1"/>
			            </f:facet>
			            <f:facet name="previous">
							<t:graphicImage url="/images/scrollerTable/arrowPrevious.jpg" border="1"/>
			            </f:facet>
			            <f:facet name="next">
			                <t:graphicImage url="/images/scrollerTable/arrowNext.jpg" border="1"/>
			            </f:facet>
			            <f:facet name="fastforward">
			                <t:graphicImage url="/images/scrollerTable/arrowFastForward.jpg" border="1"/>
			            </f:facet>
			            <f:facet name="fastrewind">
			                <t:graphicImage url="/images/scrollerTable/arrowFastRewind.jpg" border="1"/>
			            </f:facet>
		        	</t:dataScroller>
		    	</f:facet>
			</t:dataTable>
		</h:panelGroup>
</h:panelGroup>
</h:panelGrid>