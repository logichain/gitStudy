<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>
			
<html:form action="projectmanage.do">
	<html:errors />
	<input type="hidden" name="method" value="searchProject">
	<input type="hidden" name="id">
	<table CELLPADDING="2" CELLSPACING="0" width="100%" border="0">		
		<tr><td width="10%"></td><td width="10%"></td><td width="10%"></td>
			<td></td>
		</tr>			
		<tr>
			<td><bean:message bundle="project" key="project_name"/></td>
			<td><html:text property="projectInfo.PName"></html:text></td>
			<td align="right"><bean:message bundle="project" key="page_itemcount"/>:</td>
			<td align="left">
				<html:select property="projectInfo.pageItemCount" style="width:120px" onchange="projectForm.submit();">	
					<html:option value="20">20</html:option>
					<html:option value="30">30</html:option>
					<html:option value="40">40</html:option>
					<html:option value="50">50</html:option>
					<html:option value="60">60</html:option>																	
				</html:select>
			</td>
		</tr>
		<tr>
			<td colspan="3"></td>
			<td align="right">
				<html:submit styleClass="searchbutton" onclick="initPageNo();">
					&nbsp;
				</html:submit>
			
				<html:submit styleClass="resetbutton" onclick="chgAction(document.all.method,'resetSearchProject');">
					&nbsp;
				</html:submit>
			
				<html:submit styleClass="addbutton" onclick="chgAction(document.all.method,'createProject');">
					&nbsp;
				</html:submit>
			</td>
		</tr>
	</table>
				
	<table class="sort-table" cellSpacing="1" cellPadding="1" width="100%" border="0">		
		
		<thead>
			<tr>
				<td rowspan="2" width="4%"></td>
				<td rowspan="2" width="10%" align="center"><bean:message bundle="project" key="project_name"/></td>
				<td rowspan="2" width="10%" align="center">初始<bean:message bundle="project" key="project_version"/></td>
				
				<td colspan="3" width="25%" align="center"><bean:message bundle="project" key="test"/></td>
				<td rowspan="2" align="center"><bean:message bundle="project" key="remark"/></td>
				<td rowspan="2" width="5%" align="center"><bean:message bundle="security" key="account.enabled" /></td>
				<td rowspan="2" width="5%" align="center"><bean:message bundle="project" key="edit"/></td>
				<td rowspan="2" width="5%" align="center"><bean:message bundle="project" key="delete"/></td>				
			</tr>
			<tr>
				<td align="center"><bean:message bundle="project" key="leader"/></td>
				<td align="center"><bean:message bundle="project" key="begin"/></td>
				<td align="center"><bean:message bundle="project" key="end"/></td>			
			</tr>
		</thead>
		<tbody>
			<logic:present name="projectCount">
			<bean:define id="pageCount" name="projectForm" property="projectInfo.pageItemCount"></bean:define>
			<bean:define id="itemNo" name="projectCount"></bean:define>
			<pg:pager url="./projectmanage.do" items="<%=Integer.valueOf(itemNo.toString())%>" index="center" maxPageItems="<%=Integer.valueOf(pageCount.toString())%>" maxIndexPages="10" isOffset="<%= true %>" export="offset,currentPageNumber=pageNumber" scope="request">
				<%-- keep track of preference --%>						
				<pg:param name="method" value="searchProject"/>
				
				<%-- save pager offset during form changes --%>
				<input type="hidden" name="pager.offset" value="<%= offset %>"/>
				<logic:iterate name="projectList" id="p" indexId="i">
					<pg:item>						
						<% int a = i%2;request.setAttribute("a",a);%>
						<logic:equal name="a" value="0"><tr class="even"></logic:equal>
						<logic:equal name="a" value="1"><tr class="odd"></logic:equal>
							<td align="center">										
								<%=i+1 %>
							</td>							
							<td align="center"><bean:write name="p" property="PName"/></td>					
							<td align="center"><bean:write name="p" property="initProjectVersion.pvVersion"/></td>		
							
							<td align="center"><bean:write name="p" property="initProjectVersion.testLeader.personName"/></td>
							<td align="center"><bean:write name="p" property="initProjectVersion.pvTestBegin"/></td>
							<td align="center"><bean:write name="p" property="initProjectVersion.pvTestEnd"/></td>
							<td align="center"><bean:write name="p" property="initProjectVersion.pvRemark"/></td>
							<td align="center">
								<logic:equal name="p" property="PStatus" value="1">
									<input type="checkbox" checked onclick='chgAction(document.all.id,"<bean:write  name="p" property="PId" />");chgAction(document.all.method,"enableProject");projectForm.submit();'>
								</logic:equal>	
								<logic:equal name="p" property="PStatus" value="0">
									<input type="checkbox" onclick='chgAction(document.all.id,"<bean:write  name="p" property="PId" />");chgAction(document.all.method,"enableProject");projectForm.submit();'>
								</logic:equal>
							</td>
							<td align="center">								
								<a href='projectmanage.do?method=editProject&id=<bean:write name="p" property="PId"/>'><img border="0" src="pages\images\icon\16x16\modify.gif"></a>
							</td>							
							<td align="center">								
								<a href='javascript:if(confirm("确认要删除这条信息吗?")){chgAction(document.all.id,"<bean:write name="p" property="PId"/>");chgAction(document.all.method,"deleteProject");projectForm.submit();}'><img border="0" src="pages\images\icon\16x16\delete.gif"></a>
							</td>					
						</tr>
					</pg:item>
				</logic:iterate>

			<jsp:include page="../common/page.jsp" flush="true" />

			</pg:pager>
			<logic:equal name="itemNo" value="0">
				<tr>
					<td align="left" COLSPAN="16">
						<bean:message key="noRecord" />
					</td>
				</tr>
			</logic:equal>	
			</logic:present>
			<logic:notPresent name="projectCount">
				<tr>
					<td align="left" COLSPAN="16">
						<bean:message key="noRecord" />
					</td>
				</tr>
			</logic:notPresent>			
		</tbody>
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
 
</script>
