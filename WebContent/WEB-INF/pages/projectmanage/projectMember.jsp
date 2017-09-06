<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>
			
<html:form action="projectmanage.do">
	<html:errors />
	<input type="hidden" name="method" value="searchAccount">
	<input type="hidden" name="id" value="">
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
				<bean:message bundle="security" key="account.name" />
			</td>
			<td width="10%" align="center">
				<bean:message bundle="security" key="person.code" />
			</td>
			<td width="20%" align="center">
				<bean:message bundle="security" key="person.name" />
			</td>
			<td width="10%" align="center">
				<bean:message bundle="security" key="person.birthday" />
			</td>
			<td width="10%" align="center">
				<bean:message bundle="security" key="person.dept" />
			</td>
			<td width="30%" align="center">
				<bean:message bundle="security" key="person.email" />
			</td>
			<td align="center">
				<bean:message key="button.delete" />
			</td>
		</tr>
	</thead>
	
	<logic:iterate  name="projectForm" property="projectInfo.memberList" id="m" indexId="i">		
		  <tr>
			<% int a = i%2;request.setAttribute("a",a);%>
			<logic:equal name="a" value="0"><tr class="even"></logic:equal>
			<logic:equal name="a" value="1"><tr class="odd"></logic:equal>
				<td align="center"><%=i+1 %></td>
				<td>										
					<bean:write name="m" property="account.accountName" />
				</td>
				<td>
					<bean:write name="m" property="account.personCode" />
				</td>
				<td>
					<bean:write name="m" property="account.personName" />
				</td>
				<td>
					<bean:write name="m" property="account.birthdayStr" />
				</td>
				<td>
					<bean:write name="m" property="account.department.DName" />
				</td>
			
				<td>
					<bean:write name="m" property="account.email" />
				</td>					
				<td align="center"><a href='javascript:if(confirm("确认要删除这条信息吗?")) {chgAction(document.all.id,"<bean:write  name="m" property="tmId" />");chgAction(document.all.method,"deleteTeamMember");projectForm.submit();}'><img border="0" src="pages\images\icon\16x16\delete.gif"></a></td>
				
			</tr>
	</logic:iterate>
</table>
			
</html:form>	

<script language="JavaScript">

 function chgAction(obj,str){
	obj.value=str;	
 }

 function initPageNo()
 {
 	document.getElementById("pager.offset").value='0';
 }
 
 function openDialog(loadpos,WWidth,WHeight)//Lock   Size 
{
	var WLeft = Math.ceil((window.screen.width - WWidth) / 2);   
	var WTop = Math.ceil((window.screen.height - WHeight) / 2); 
	var features = 'width=' + WWidth + 'px,' +	'height=' + WHeight + 'px,' + 'left=' + WLeft + 'px,' + 'top=' + WTop + 'px'; 
		
	WinOP = window.open(loadpos,"_blank",features); 
	WinOP.focus();	   
}
 
</script>
