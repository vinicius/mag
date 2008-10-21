<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<h:panelGrid columns="1" styleClass="content" rowClasses="contentTitle,contentBody" cellspacing="1" cellpadding="1">
	<h:outputText value="#{msg['submissionList.title']}" styleClass="pageTitle" />
	<h:panelGroup>
		<h:outputText value="#{msg['submissionList.emptyRequest']}" rendered="#{requestListBean.amountOfRequests < 1}" />
		<h:panelGroup>
			<t:dataTable
					id="requestTable"
					var="execution"
					value="#{requestListBean.requestList}"
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
						<h:outputText value="#{msg['submissionList.column1']}"/>
					</f:facet>
					<f:verbatim> <table width=100> <tr> <td align=center> </f:verbatim> 
					<h:outputText value="#{execution.request.requestId}" />
					<f:verbatim> </td></tr></table> </f:verbatim>
				</h:column>
		
				<h:column>
					<f:facet name="header">
						<h:outputText value="#{msg['submissionList.column2']}"/>
					</f:facet>
					<h:outputText value="#{execution.request.applicationName}" />
				</h:column>
		
				<h:column>
					<f:facet name="header">
						<h:outputText value="#{msg['submissionList.column3']}"/>
					</f:facet>
					<h:outputText value="#{execution.videoFile.original.name}" />
				</h:column>
		
				<h:column>
					<f:facet name="header">
						<h:outputText value="#{msg['submissionList.column4']}"/>
					</f:facet>
					<h:outputText value="#{execution.format}" />
				</h:column>
		
				<h:column>
					<f:facet name="header">
						<h:outputText value="#{msg['submissionList.column5']}"/>
					</f:facet>
					<h:outputText value="#{execution.request.applicationState}" />
				</h:column>
		
				<f:facet name="footer">
		    		<t:dataScroller id="requestTableScroller"
		                        for="requestTable"
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