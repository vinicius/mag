<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<f:view>
<f:verbatim>
<style type="text/css">
.logo {
	width: 200px;
	vertical-align: top;
	border-spacing: 0px;
	border-collapse: collapse;
}
.search {
	width: 250px;
	vertical-align: top;
	border-spacing: 0px;
	border-collapse: collapse;
}
.login {
	width: 250px;
	vertical-align: top;
	border-spacing: 0px;
	border-collapse: collapse;
}
.header {
	height: 130px;
	vertical-align: top;
}

.menu {
	width: 180px;
		margin-left: 0px;
		margin-right: 20px;
	vertical-align: top;
	border-spacing: 0px;
	border-collapse: collapse;
}
.content {
	width: 500px;
	max-width: 500px;
	vertical-align: top;
	border-spacing: 0px;
	border-collapse: collapse;
}
.about {
	height: 25px;
}
.technology {
	height: 25px;
	text-align: center;
}
</style>
</f:verbatim>
<t:document>
<t:documentHead>
	<title>GVC</title>
	<link rel="shortcut icon" type="image/x-icon" href="http://localhost:8080/GVC/images/bolinha.gif"/>
	<script type="text/javascript" src="/GVC/javascript/aa/aa.js"></script>
	<t:stylesheet path="/css/basic.css"/>
</t:documentHead>

<t:documentBody>
	<f:loadBundle basename="resources.GVCresources" var="msg"/>
	<h:panelGrid columns="1" style="width:700px;" rowClasses="header," cellspacing="0" cellpadding="1">
		<h:panelGrid columns="3" columnClasses="logo,search,login">
			<h:panelGroup>
				<tiles:insert attribute="logo" flush="false" />
			</h:panelGroup>
			<h:panelGroup>
				<tiles:insert attribute="search" flush="false" />
			</h:panelGroup>
			<h:panelGroup>
				<tiles:insert attribute="login" flush="false" />
			</h:panelGroup>
		</h:panelGrid>
		<h:panelGrid columns="2" columnClasses="menu,content">
			<h:panelGrid columns="1" align="center">
				<h:panelGroup>
					<tiles:insert attribute="menu" flush="false" />
				</h:panelGroup>
				<h:panelGroup>
					<tiles:insert attribute="technology" flush="false" />
				</h:panelGroup>
			</h:panelGrid>
			<h:panelGroup>
				<tiles:insert attribute="content" flush="false" />
			</h:panelGroup>
		</h:panelGrid>
		<h:panelGroup>
			<tiles:insert attribute="about" flush="false" />
		</h:panelGroup>
	</h:panelGrid>
</t:documentBody>
</t:document>
</f:view>