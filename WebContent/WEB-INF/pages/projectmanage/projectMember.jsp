<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>
			

<table class="win" width="100%">					
	<tr>
		<td width="80%" align="left">
		
		</td>			
		
		<td align="center">							
			<html:button property="" styleClass="addbutton" onclick="openDialog('projectmanage.do?method=searchAccount&opType=tm',800,420);">
				&nbsp;
			</html:button>
		</td>
	</tr>
</table>
			
<table class="sort-table" width="100%">			
	<thead>
		<tr>
			<td width="5%" align="center">
			</td>
			
			<td width="10%" align="center">
				<bean:message bundle="security" key="person.code" />
			</td>
			<td width="10%" align="center">
				<bean:message bundle="security" key="person.name" />
			</td>
			<td width="10%" align="center">
				<bean:message bundle="security" key="person.dept" />
			</td>
			<td align="center">
				<bean:message bundle="project" key="member_function" />
			</td>
			<td width="20%" align="center">
				<bean:message bundle="security" key="person.email" />
			</td>
			<td width="5%" align="center">
				<bean:message key="button.edit" />
			</td>
			<td width="5%" align="center">
				<bean:message key="button.delete" />
			</td>
		</tr>
	</thead>
	
	
	<logic:iterate name="projectForm" property="projectInfo.memberList" id="m" indexId="i">	
		<logic:equal name="m" property="tmFlag" value="0">	
		  <tr>
			<% int a = i%2;request.setAttribute("a",a);%>
			<logic:equal name="a" value="0"><tr class="even"></logic:equal>
			<logic:equal name="a" value="1"><tr class="odd"></logic:equal>
				<td align="center"><%=i+1 %></td>
				
				<td>
					<bean:write name="m" property="account.personCode" />
				</td>
				<td>
					<bean:write name="m" property="account.personName" />
				</td>
				<td>
					<bean:write name="m" property="account.department.DName" />
				</td>
				<td>
					<logic:iterate id="mfr" name="m" property="memberFunctionReferenceList">					
						<bean:write name="mfr" property="moduleFunction.projectModule.pmName" />-<bean:write name="mfr" property="moduleFunction.muName" />;				
					</logic:iterate>
				</td>
			
				<td>
					<bean:write name="m" property="account.email" />
				</td>					
				<td align="center">
				<logic:equal name="m" property="account.dept" value="2">
					<a href="javascript:openDialog('projectmanage.do?method=editTeamMember&id=<bean:write name="m" property="tmId"/>',800,420);"><img border="0" src="pages\images\icon\16x16\modify.gif"></a>
				</logic:equal>	
				</td>
				<td align="center"><a href='javascript:if(confirm("确认要删除这条信息吗?")) {chgAction(document.all.id,"<bean:write  name="m" property="tmId" />");chgAction(document.all.method,"deleteTeamMember");projectForm.submit();}'><img border="0" src="pages\images\icon\16x16\delete.gif"></a></td>
				
			</tr>
		</logic:equal>
	</logic:iterate>
</table>

