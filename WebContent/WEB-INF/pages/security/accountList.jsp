<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>
		
<html:form action="accountManager.do">
	<table class="win" CELLPADDING="2" CELLSPACING="0" width="100%">					
		<tr align="right">
			<td>
				<bean:message bundle="security" key="account.name" />
				<html:text property="para"></html:text>
				<bean:message bundle="security" key="person.name" />
				<html:text property="paraPersonName"></html:text>
				<bean:message bundle="security" key="person.dept" />								
				<html:select property="department" style="width:120px">	
					<html:option value=""></html:option>
					<html:options collection="departmentList" property="DId" labelProperty="DName"/>
				</html:select>
				<html:submit styleClass="searchbutton" onclick="initPageNo();">
					&nbsp;
				</html:submit>
				<html:submit styleClass="resetbutton" onclick="initPageNo();chgAction(document.all.method,'resetSearchAccount');">
					&nbsp;
				</html:submit>
				<html:submit styleClass="addbutton" onclick="chgAction(document.all.method,'loadAccount4Add');chgAction(document.all.opType,'add');">
					&nbsp;
				</html:submit>
			</td>
		</tr>
	</table>

	<input type="hidden" name="method" value="searchAccount">
	<input type="hidden" name="id" value="">
	<table class="sort-table" cellSpacing="1" cellPadding="1" width="100%" border="0">						
		<logic:present name="accounts" scope="request">
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
					<td width="25%" align="center">
						<bean:message bundle="security" key="person.email" />
					</td>
					<td width="5%" align="center">
						<bean:message key="button.edit" />
					</td>
					<td align="center">
						<bean:message key="button.delete" />
					</td>
				</tr>
			</thead>

			<!--page offset start -->
			<%int itemNo = ((Integer) request.getAttribute("accountCount")).intValue();%>
			<pg:pager url="./accountManager.do" items="<%=itemNo%>" index="center" maxPageItems="10" maxIndexPages="10" isOffset="<%= true %>" export="offset,currentPageNumber=pageNumber" scope="request">
				<%-- keep track of preference --%>								
				<pg:param name="method" value="searchAccount"/>

				<%-- save pager offset during form changes --%>
				<input type="hidden" name="pager.offset" value="<%= offset %>">
				<logic:iterate name="accounts" id="account" indexId="i">
					<pg:item>
					  <tr>
						<% int a = i%2;request.setAttribute("a",a);%>
						<logic:equal name="a" value="0"><tr class="even"></logic:equal>
						<logic:equal name="a" value="1"><tr class="odd"></logic:equal>
							<td align="center"><%=i+1 %></td>
							<td>										
								<bean:write name="account" property="accountName" />
							</td>
							<td>
								<bean:write name="account" property="personCode" />
							</td>
							<td>
								<bean:write name="account" property="personName" />
							</td>
							<td>
								<bean:write name="account" property="birthdayStr" />
							</td>
							<td>
								<bean:write name="account" property="department.DName" />
							</td>
						
							<td>
								<bean:write name="account" property="email" />
							</td>
							<td align="center"><a href="accountManager.do?method=loadAccount4Edit&id=<bean:write  name="account" property="id" />"> <img border="0" src="pages\images\icon\16x16\modify.gif"></a></td>	
							<td align="center"><a href='javascript:if(confirm("确认要删除这条信息吗?")) {chgAction(document.all.id,"<bean:write  name="account" property="id" />");chgAction(document.all.method,"removeAccount");accountForm.submit();}'><img border="0" src="pages\images\icon\16x16\delete.gif"></a></td>
							
						</tr>
					</pg:item>
				</logic:iterate>
	
			<jsp:include page="../common/page.jsp" flush="true" />
		</pg:pager>
		<!-- page offset end -->
		</logic:present>
	</table>
	
	
</html:form>
<logic:notPresent name="accounts" scope="request">
	<tr>
		<td COLSPAN="3">
			<bean:message bundle="security" key="account.noRecord" />
		</td>
	</tr>
</logic:notPresent>		

<script language="JavaScript">
  function initPageNo()
 {
 	document.getElementById("pager.offset").value='0';
 }
 function chgAction(obj,str){
	obj.value=str;
 }

</script>
