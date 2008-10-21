<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<f:verbatim>
<style type="text/css">
	.menuTitle {
		color:#294747;
		background-color: white;
		font-weight: bold;
	}
	#subnavigation {
		padding: 0px 0px 20px 0px;
		border: 1px solid #546359;
		background-color: white;
	}
	
	#subnavigation ul li a {
		display:block;
		background-color: LightSteelBlue;
		color: #294747;
		text-decoration: none;
		border-bottom: 1px solid #87A8A8;
		padding: 2px 20px;
		margin: 0px;
		font-weight: bold;
	}	
	
	#subnavigation ul {
		list-style-type: none;
		padding: 0px;
		margin: 0px;
	}
	
	#subnavigation li {
		margin: 0px;
		padding: 0px;
		display:inline;
	}
	
	#subnavigation ul li a:hover {
		color: white;
		background-color: SteelBlue;
	}
	
	#subnavigation ul li a:active {
		color:#294747;
		background-color: white;
	}
	
	#subnavigation ul li a.selected {
		color:#294747;
		background-color: white;
	}
</style>
</f:verbatim>

<t:div id="subnavigation">
    <t:panelNavigation2 id="management" layout="list" styleClass="subnavigation" activeItemClass="selected" openItemClass="selected" >
		<t:commandNavigation2 id="managementMenu" disabled="true" value="Management" open="true">
			<t:outputText value="Management" styleClass="menuTitle"/>
			<t:commandNavigation2 action="userManagement" rendered="#{loginBean.admin}">
	            <t:outputText value="#{msg['menu.subitem6']}"/>
	        </t:commandNavigation2>
	        
			<t:commandNavigation2 action="userDataManagement">
	            <t:outputText value="#{msg['menu.subitem7']}"/>
	        </t:commandNavigation2>
	
			<t:commandNavigation2 action="submissionlist" >
				<t:outputText value="#{msg['menu.subitem4']}"/>
			</t:commandNavigation2>
			
			<t:commandNavigation2 action="fileManagement" >
				<t:outputText value="#{msg['menu.subitem5']}"/>
			</t:commandNavigation2>			
		</t:commandNavigation2>
		
		<t:commandNavigation2 id="gvcSystemMenu" disabled="true" value="Gvc System" open="true">
			<t:outputText value="Gvc System" styleClass="menuTitle"/>
			<t:commandNavigation2 action="fileupload" >
	            <t:outputText value="#{msg['menu.subitem1']}"/>
	        </t:commandNavigation2>
	            
			<t:commandNavigation2 action="fileconvert" >
				<t:outputText value="#{msg['menu.subitem2']}"/>
			</t:commandNavigation2>
		            
			<t:commandNavigation2 action="filedownload" >
				<t:outputText value="#{msg['menu.subitem3']}"/>
			</t:commandNavigation2>
		</t:commandNavigation2>
    </t:panelNavigation2>	
</t:div>