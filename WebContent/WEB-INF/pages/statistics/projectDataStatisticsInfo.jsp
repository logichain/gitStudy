<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>


		<logic:notEmpty name="projectSvgStr">
			<%=request.getAttribute("projectSvgStr") %>
		</logic:notEmpty>
		
			<table class="sort-table" width="100%">
			<thead>	
				<tr>
					<td><bean:message bundle="project" key="project_name" /></td>
					<td><bean:message bundle="case" key="case_count"/></td>
					<td><bean:message bundle="case" key="wait_count"/></td>
					<td><bean:message bundle="case" key="test_count"/></td>
					<td><bean:message bundle="case" key="bug_count"/></td>
					<td><bean:message bundle="case" key="na_count"/></td>
					<td><bean:message bundle="case" key="close_count"/></td>
				</tr>
			</thead>
			<tbody>
				<logic:present name="projectDataList">
					<logic:iterate id="data" name="projectDataList">
						<tr>
							<td><bean:write name="data" property="title"/></td>
							<td><bean:write name="data" property="designCaseCount"/></td>
							<td><bean:write name="data" property="waitTestCaseCount"/></td>
							<td><bean:write name="data" property="testCaseCount"/></td>
							<td><bean:write name="data" property="unpassCaseCount"/></td>
							<td><bean:write name="data" property="NACaseCount"/></td>
							<td><bean:write name="data" property="closeCaseCount"/></td>
						</tr>
					</logic:iterate>
				</logic:present>
			</tbody>
			</table>
		

