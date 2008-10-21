<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>

<f:verbatim>
<style type="text/css">
	.tech{
		text-align: center;
	}
</style>
</f:verbatim>

<h:panelGrid columns="1" cellspacing="0" columnClasses="tech">
    <h:outputLink value="http://integrade.incubadora.fapesp.br/portal">
		<h:graphicImage value="/images/powered/integrade.jpeg" style="border-width: 0px" height="90%" width="90%"/>
	</h:outputLink>
		
	<h:outputLink value="http://ffmpeg.mplayerhq.hu/">
		<h:graphicImage value="/images/powered/ffmpeg.jpg" style="border-width: 0px;"/>
	</h:outputLink>
</h:panelGrid>
