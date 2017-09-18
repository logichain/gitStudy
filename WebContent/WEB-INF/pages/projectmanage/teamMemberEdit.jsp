<%@ page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>

	
	<html:form action="projectmanage.do?method=saveTeamMember" onsubmit="return onFormSubmit(this);">
		<html:errors />
		
	<table class="sort-table" cellSpacing="1" cellPadding="1" width="100%" border="0">		
		
		<thead>
			<tr>
				<td width="10%" align="center">
					<bean:message bundle="security" key="person.code" />
				</td>
				<td width="10%" align="center">
					<bean:message bundle="security" key="person.name" />
				</td>
				<td align="center">
					<bean:message bundle="security" key="person.dept" />
				</td>
				<td align="center">
					<bean:message bundle="project" key="member_function" />
				</td>
				<td width="20%" align="center">
					<bean:message bundle="security" key="person.email" />
				</td>			
			</tr>
		</thead>

		<tr>
			<td>
				<bean:write name="currentTeamMember" property="account.personCode" />
			</td>
			<td>
				<bean:write name="currentTeamMember" property="account.personName" />
			</td>
			<td>
				<bean:write name="currentTeamMember" property="account.department.DName" />
			</td>
			<td>
				<logic:iterate id="mfr" name="currentTeamMember" property="memberFunctionReferenceList">
					<bean:write name="mfr" property="moduleFunction.muName"/>
				</logic:iterate>
			</td>
		
			<td>
				<bean:write name="currentTeamMember" property="account.email" />
			</td>					
		</tr>
	</table>
		
	<table class="win" cellSpacing="1" cellPadding="1" width="100%" border="0">
		<tr>			
			<th width="30%" align="center">
				
			</th>
			<th width="10%">
				&nbsp;
			</th>
			<th  width="30%" align="center">
			</th>
			<th></th>
		</tr>
		<tr><td colspan="4">
		<c:set var="leftList" value="${availableFunction}" scope="request" />
		<c:set var="rightList" value="${selectedFunction}" scope="request" />
		<c:import url="/WEB-INF/pages/projectmanage/pickList.jsp">
			<c:param name="leftLabel" value="可选功能" />
			<c:param name="rightLabel" value="已选功能" />
			<c:param name="listCount" value="6" />
			<c:param name="leftId" value="availableFunction" />
			<c:param name="rightId" value="selectedFunction" />
			<c:param name="listSize" value="12" />
			<c:param name="listWidth" value="200px" />
		</c:import>
		</td></tr>
		<tr>
			<td colspan="3"></td>
			<td align="right">
				<html:submit styleClass="button">
					<bean:message key="button.confirm" />
				</html:submit>
				<html:cancel styleClass="button">
					<bean:message key="button.cancel" />
				</html:cancel>
			</td>
		</tr>
	</table>
		<input type="hidden" name="id" value='<c:out value="${currentTeamMember.tmId}"/>'>
	</html:form>


<script type="text/javascript">
function onFormSubmit(theForm) {
	selectAll('selectedFunction');
    return true;
}
</script>
