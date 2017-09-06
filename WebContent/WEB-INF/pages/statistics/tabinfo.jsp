<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>


<bean:define id="infoTitle">
	<bean:message bundle="case" key="statistics_title" />
</bean:define>
<bean:define id="versionInfoTitle">
	<bean:message bundle="project" key="project_version"/>
</bean:define>
<bean:define id="moduleInfoTitle">
	<bean:message bundle="case" key="module"/>
</bean:define>
<bean:define id="statusInfoTitle">
	<bean:message bundle="case" key="case_status"/>
</bean:define>
<bean:define id="resultInfoTitle">
	<bean:message bundle="case" key="test_result"/>
</bean:define>
<bean:define id="importLevelInfoTitle">
	<bean:message bundle="case" key="important_level"/>
</bean:define>
<bean:define id="bugTypeInfoTitle">
	<bean:message bundle="case" key="bug_type"/>
</bean:define>
<bean:define id="functionInfoTitle">
	<bean:message bundle="case" key="function"/>
</bean:define>

<gui:window title='<%=infoTitle%>' prototype="boWindow">
	<html:form action="teststatistics.do">
		<html:errors />
		<input type="hidden" name="method" value="versionStatistics">
		<table CELLPADDING="2" CELLSPACING="0" width="100%" border="0">		
			<tr><td width="10%" align="right" class="strong"><bean:message bundle="case" key="project"/>:</td>
				<td width="15%" align="left" class="strong"><bean:write name="statisticsForm" property="projectInfo.PName"/></td>
				<td width="10%"></td><td width="15%"></td><td width="10%"></td><td width="15%"></td>
				<td width="10%"></td><td></td>
			</tr>			
			<tr>
				<td align="right"><bean:message bundle="case" key="module"/>:</td>
				<td align="left">
					<html:select property="caseInfo.moduleId" style="width:120px">	
						<html:option value=""></html:option>
						<html:optionsCollection name="statisticsForm" property="projectInfo.moduleList" value="pmId" label="pmName"/>
					</html:select>
				</td>
							
				<td align="right"><bean:message bundle="project" key="version"/>:</td>
				<td align="left">
					<html:select property="cvrSearchInfo.cvrProjectVersion" style="width:120px">	
						<html:option value=""></html:option>									
						<html:optionsCollection name="statisticsForm" property="projectInfo.projectVersionList" value="pvId" label="pvVersion"/>									
					</html:select>
				</td>			
			</tr>		
			
			<tr>
				<td colspan="8" align="right">
					<html:submit styleClass="searchbutton">
						&nbsp;
					</html:submit>
					<html:submit styleClass="resetbutton" onclick="chgAction(document.all.method,'resetVersionStatistics');">
						&nbsp;
					</html:submit>					
				</td>
			</tr>
		</table>
								
	</html:form>	
	
	<gui:tabbedPanel selectedTab='<%=request.getParameter("selectTab")%>' prototype="boTabbedPanel" width="40">
		<gui:tab prototype="boTab" name="versionInfo" title="<%=versionInfoTitle%>" followUp="teststatistics.do?method=versionStatistics">
			<%if("versionInfo".equals(request.getParameter("selectTab"))) {%>
			<table width="100%">
				<tr>
					<td width="60%">
						<%=request.getAttribute("versionSvgStr") %>
					</td>
					<td>
						<table class="sort-table" width="100%">
						<thead>	
							<tr>
								<td><bean:message bundle="case" key="class_name"/></td>
								<td><bean:message bundle="case" key="data_count"/></td>
								<td><bean:message bundle="case" key="data_percent"/></td>
							</tr>
						</thead>
						<tbody>
							<logic:iterate id="data" name="versionDataList">
							<tr>
								<td><bean:write name="data" property="title"/></td>
								<td><bean:write name="data" property="count"/></td>
								<td><bean:write name="data" property="percentStr"/></td>
							</tr>
							</logic:iterate>
						</tbody>
						</table>
					</td>
				</tr>
			</table>
			<%} %>
		</gui:tab>
		<gui:tab prototype="boTab" name="moduleInfo" title="<%=moduleInfoTitle%>" followUp="teststatistics.do?method=moduleStatistics">
			<%if("moduleInfo".equals(request.getParameter("selectTab"))) {%>
			<table width="100%">
				<tr>
					<td width="60%">
						<%=request.getAttribute("moduleSvgStr") %>
					</td>
					<td>
						<table class="sort-table" width="100%">
						<thead>	
							<tr>
								<td><bean:message bundle="case" key="class_name"/></td>
								<td><bean:message bundle="case" key="data_count"/></td>
								<td><bean:message bundle="case" key="data_percent"/></td>
							</tr>
						</thead>
						<tbody>
							<logic:iterate id="data" name="moduleDataList">
							<tr>
								<td><bean:write name="data" property="title"/></td>
								<td><bean:write name="data" property="count"/></td>
								<td><bean:write name="data" property="percentStr"/></td>
							</tr>
							</logic:iterate>
						</tbody>
						</table>
					</td>
				</tr>
			</table>
			<%} %>
		</gui:tab>	
		<gui:tab prototype="boTab" name="statusInfo" title="<%=statusInfoTitle%>" followUp="teststatistics.do?method=statusStatistics">
			<%if("statusInfo".equals(request.getParameter("selectTab"))) {%>
			<table width="100%">
				<tr>
					<td width="60%">
						<%=request.getAttribute("statusSvgStr") %>
					</td>
					<td>
						<table class="sort-table" width="100%">
						<thead>	
							<tr>
								<td><bean:message bundle="case" key="class_name"/></td>
								<td><bean:message bundle="case" key="data_count"/></td>
								<td><bean:message bundle="case" key="data_percent"/></td>
							</tr>
						</thead>
						<tbody>
							<logic:iterate id="data" name="statusDataList">
							<tr>
								<td><bean:write name="data" property="title"/></td>
								<td><bean:write name="data" property="count"/></td>
								<td><bean:write name="data" property="percentStr"/></td>
							</tr>
							</logic:iterate>
						</tbody>
						</table>
					</td>
				</tr>
			</table>
			<%} %>
		</gui:tab>			
		<gui:tab prototype="boTab" name="resultInfo" title="<%=resultInfoTitle%>" followUp="teststatistics.do?method=resultStatistics">
			<%if("resultInfo".equals(request.getParameter("selectTab"))) {%>
			<table width="100%">
				<tr>
					<td width="60%">
						<%=request.getAttribute("resultSvgStr") %>
					</td>
					<td>
						<table class="sort-table" width="100%">
						<thead>	
							<tr>
								<td><bean:message bundle="case" key="class_name"/></td>
								<td><bean:message bundle="case" key="data_count"/></td>
								<td><bean:message bundle="case" key="data_percent"/></td>
							</tr>
						</thead>
						<tbody>
							<logic:iterate id="data" name="resultDataList">
							<tr>
								<td><bean:write name="data" property="title"/></td>
								<td><bean:write name="data" property="count"/></td>
								<td><bean:write name="data" property="percentStr"/></td>
							</tr>
							</logic:iterate>
						</tbody>
						</table>
					</td>
				</tr>
			</table>
			<%} %>
		</gui:tab>
		<gui:tab prototype="boTab" name="importLevelInfo" title="<%=importLevelInfoTitle%>" followUp="teststatistics.do?method=importLevelStatistics">
			<%if("importLevelInfo".equals(request.getParameter("selectTab"))) {%>
			<table width="100%">
				<tr>
					<td width="60%">
						<%=request.getAttribute("importLevelSvgStr") %>
					</td>
					<td>
						<table class="sort-table" width="100%">
						<thead>	
							<tr>
								<td><bean:message bundle="case" key="class_name"/></td>
								<td><bean:message bundle="case" key="data_count"/></td>
								<td><bean:message bundle="case" key="data_percent"/></td>
							</tr>
						</thead>
						<tbody>
							<logic:iterate id="data" name="importLevelDataList">
							<tr>
								<td><bean:write name="data" property="title"/></td>
								<td><bean:write name="data" property="count"/></td>
								<td><bean:write name="data" property="percentStr"/></td>
							</tr>
							</logic:iterate>
						</tbody>
						</table>
					</td>
				</tr>
			</table>
			<%} %>
		</gui:tab>
		<gui:tab prototype="boTab" name="bugTypeInfo" title="<%=bugTypeInfoTitle%>" followUp="teststatistics.do?method=bugTypeStatistics">
			<%if("bugTypeInfo".equals(request.getParameter("selectTab"))) {%>
			<table width="100%">
				<tr>
					<td width="60%">
						<%=request.getAttribute("bugTypeSvgStr") %>
					</td>
					<td>
						<table class="sort-table" width="100%">
						<thead>	
							<tr>
								<td><bean:message bundle="case" key="class_name"/></td>
								<td><bean:message bundle="case" key="data_count"/></td>
								<td><bean:message bundle="case" key="data_percent"/></td>
							</tr>
						</thead>
						<tbody>
							<logic:iterate id="data" name="bugTypeDataList">
							<tr>
								<td><bean:write name="data" property="title"/></td>
								<td><bean:write name="data" property="count"/></td>
								<td><bean:write name="data" property="percentStr"/></td>
							</tr>
							</logic:iterate>
						</tbody>
						</table>
					</td>
				</tr>
			</table>
			<%} %>
		</gui:tab>
		<gui:tab prototype="boTab" name="functionInfo" title="<%=functionInfoTitle%>" followUp="teststatistics.do?method=functionStatistics">
			<%if("functionInfo".equals(request.getParameter("selectTab"))) {%>
			<table width="100%">
				<tr>
					<td width="60%">
						<%=request.getAttribute("functionSvgStr") %>
					</td>
					<td>
						<table class="sort-table" width="100%">
						<thead>	
							<tr>
								<td><bean:message bundle="case" key="class_name"/></td>
								<td><bean:message bundle="case" key="data_count"/></td>
								<td><bean:message bundle="case" key="data_percent"/></td>
							</tr>
						</thead>
						<tbody>
							<logic:iterate id="data" name="functionDataList">
							<tr>
								<td><bean:write name="data" property="title"/></td>
								<td><bean:write name="data" property="count"/></td>
								<td><bean:write name="data" property="percentStr"/></td>
							</tr>
							</logic:iterate>
						</tbody>
						</table>
					</td>
				</tr>
			</table>
			<%} %>
		</gui:tab>
	</gui:tabbedPanel>

</gui:window>

<script language="JavaScript">

 function chgAction(obj,str){
	obj.value=str;	
 }

</script>
