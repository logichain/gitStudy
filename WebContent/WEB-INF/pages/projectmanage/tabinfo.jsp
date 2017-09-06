<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>


<bean:define id="infoTitle">
	<bean:message bundle="project" key="project_info" />
</bean:define>
<bean:define id="versionInfoTitle">
	<bean:message bundle="project" key="project_versionInfo" />
</bean:define>
<bean:define id="moduleInfoTitle">
	<bean:message bundle="project" key="project_moduleInfo" />
</bean:define>
<bean:define id="memberInfoTitle">
	<bean:message bundle="project" key="project_memberInfo" />
</bean:define>
<bean:define id="CEInfoTitle">
	<bean:message bundle="project" key="project_CEInfo" />
</bean:define>
<bean:define id="CFDAInfoTitle">
	<bean:message bundle="project" key="project_CFDAInfo" />
</bean:define>

<gui:window title='<%=infoTitle%>' prototype="boWindow">
	<table width="100%">
		<tr>
			<td align="left" class="strong">				
				<bean:message bundle="project" key="project_name" />£º			
				<bean:write name="projectForm" property="projectInfo.PName" />
			</td>						
		</tr>
		<tr>
			<td>
			<fieldset style="width:90%;float:left;">
				<legend><bean:message bundle="project" key="develop"/></legend>
				<table width="100%">
					<tr>
						<td align="right" width="20%"><bean:message bundle="project" key="leader"/>£º</td>
						<td align="left">
							<bean:write name="projectForm" property="projectInfo.developLeader.personName"/>										
						</td>
					</tr>
					<tr>
						<td align="right"><bean:message bundle="project" key="begin"/>£º</td>
						<td align="left">
							<bean:write name="projectForm" property="projectInfo.PDevelopBegin"/>
						</td>
					</tr>
					<tr>
						<td align="right"><bean:message bundle="project" key="end"/>£º</td>
						<td align="left">
							<bean:write name="projectForm" property="projectInfo.PDevelopEnd" />
						</td>
					</tr>
				</table>
			</fieldset>
			</td>
			<td>
			<fieldset style="width:90%;float:left;">
				<legend><bean:message bundle="project" key="test"/></legend>
				<table width="100%">
					<tr>
						<td align="right" width="20%"><bean:message bundle="project" key="leader"/>£º</td>
						<td align="left">
							<bean:write name="projectForm" property="projectInfo.testLeader.personName" />										
						</td>
					</tr>
					<tr>
						<td align="right"><bean:message bundle="project" key="begin"/>£º</td>
						<td align="left">
							<bean:write name="projectForm" property="projectInfo.PTestBegin" />
						</td>
					</tr>
					<tr>
						<td align="right"><bean:message bundle="project" key="end"/>£º</td>
						<td align="left">
							<bean:write name="projectForm" property="projectInfo.PTestEnd" />
						</td>
					</tr>
				</table>
			</fieldset>
			</td>			
		</tr>
		<tr>
			<td colspan="2" align="left">
				<bean:message bundle="project" key="remark" />£º							
				<bean:write name="projectForm" property="projectInfo.PRemark"/>
			</td>			
		</tr>
	</table>	
	<gui:tabbedPanel selectedTab='<%=request.getParameter("selectTab")%>' prototype="boTabbedPanel" width="52">
		<gui:tab prototype="boTab" name="versionInfo" title="<%=versionInfoTitle%>" followUp="projectmanage.do?method=editProjectVersionInfo">
			<%if("versionInfo".equals(request.getParameter("selectTab"))) {%>
			<c:import url="/WEB-INF/pages/projectmanage/projectVersion.jsp">
			</c:import>
			<%} %>
		</gui:tab>
		<gui:tab prototype="boTab" name="moduleInfo" title="<%=moduleInfoTitle%>" followUp="projectmanage.do?method=editProjectModuleInfo">
			<%if("moduleInfo".equals(request.getParameter("selectTab"))) {%>
			<c:import url="/WEB-INF/pages/projectmanage/projectModule.jsp">
			</c:import>
			<%} %>
		</gui:tab>	
		<gui:tab prototype="boTab" name="memberInfo" title="<%=memberInfoTitle%>" followUp="projectmanage.do?method=editProjectMemberInfo">
			<%if("memberInfo".equals(request.getParameter("selectTab"))) {%>
			<c:import url="/WEB-INF/pages/projectmanage/projectMember.jsp">
			</c:import>
			<%} %>
		</gui:tab>	
		
		<gui:tab prototype="boTab" name="CEInfo" title="<%=CEInfoTitle%>" followUp="projectmanage.do?method=editProjectCEInfo">
			<%if("CEInfo".equals(request.getParameter("selectTab"))) {%>
			<c:import url="/WEB-INF/pages/projectmanage/projectCE.jsp">
			</c:import>
			<%} %>
		</gui:tab>
		<gui:tab prototype="boTab" name="CFDAInfo" title="<%=CFDAInfoTitle%>" followUp="projectmanage.do?method=editProjectCFDAInfo">
			<%if("CFDAInfo".equals(request.getParameter("selectTab"))) {%>
			<c:import url="/WEB-INF/pages/projectmanage/projectCFDA.jsp">
			</c:import>
			<%} %>
		</gui:tab>
		
	
	</gui:tabbedPanel>

</gui:window>

