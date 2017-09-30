<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>
			
<html:form action="projectmanage.do">
	<html:errors />
	<input type="hidden" name="method" value="searchTestCaseforReference">
	<input type="hidden" name="id" value="">
	<table CELLPADDING="2" CELLSPACING="0" width="100%" border="0">		
		<tr><td width="10%" align="right" class="strong"><bean:message bundle="case" key="project"/>:</td>
			<td width="15%" align="left" class="strong"><bean:write name="caseForm" property="projectInfo.PName"/></td>
			<td width="10%"></td><td width="15%"></td><td width="10%"></td><td width="15%"></td>
			<td width="10%"></td><td></td>
		</tr>			
		<tr>
			<td align="right"><bean:message bundle="case" key="module"/>:</td>
			<td align="left">
				<html:select property="searchInfo.moduleId" style="width:120px">	
					<html:option value=""></html:option>
					<html:optionsCollection name="caseForm" property="projectInfo.moduleList" value="pmId" label="pmName"/>
				</html:select>
			</td>
			<td align="right"><bean:message bundle="case" key="module_function"/>:</td>
			<td align="left">			
				<html:select property="searchInfo.tcModuleFunction" style="width:120px">	
					<html:option value=""></html:option>									
					<html:optionsCollection name="caseForm" property="projectInfo.allModuleFunctionList" value="muId" label="entireName"/>									
				</html:select>			
			</td>
			<td align="right"><bean:message bundle="case" key="case_code"/>:</td><td align="left"><html:text property="searchInfo.tcCode" size="14"/></td>
			<td align="right"><bean:message bundle="case" key="test_objective"/>:</td><td align="left"><html:text property="searchInfo.tcTestObjective" size="14"/></td>
		</tr>	
		<tr>
			<td align="right"><bean:message bundle="case" key="test_content"/>:</td><td align="left"><html:text property="searchInfo.tcTestContent" size="14"/></td>
			<td align="right"><bean:message bundle="case" key="intend_output"/>:</td><td align="left"><html:text property="searchInfo.tcIntendOutput" size="14"/></td>
			<td align="right"><bean:message bundle="case" key="test_output"/>:</td><td align="left"><html:text property="cvrSearchInfo.cvrCaseOutput" size="14"/></td>
			<td align="right"><bean:message bundle="case" key="test_result"/>:</td>
			<td align="left">
				<html:select property="cvrSearchInfo.cvrCaseResult" style="width:120px">	
					<html:option value=""></html:option>									
					<html:optionsCollection name="testResultList" value="trId" label="trName"/>									
				</html:select>
			</td>
		</tr>
		<tr>
			<td align="right"><bean:message bundle="case" key="case_type"/>:</td>
			<td align="left">
				<html:select property="searchInfo.tcType" style="width:120px">		
					<html:option value=""></html:option>											
					<html:optionsCollection name="caseTypeList" value="ctId" label="ctName"/>									
				</html:select>
			</td>
			<td align="right"><bean:message bundle="case" key="case_status"/>:</td>
			<td align="left">
				<html:select property="cvrSearchInfo.cvrCaseStatus" style="width:120px">	
					<html:option value=""></html:option>									
					<html:optionsCollection name="caseStatusList" value="csId" label="csName"/>									
				</html:select>
			</td>
			<td align="right"><bean:message bundle="case" key="create_time"/>:</td>
			<td align="left">
				<html:text property="searchInfo.tcCreateTimeStr" readonly="true" size="14" maxlength="32" onclick="SelectDate(this)"/>
			</td>
			<td align="right"><bean:message bundle="case" key="create_user"/>:</td><td align="left"><html:text property="searchInfo.tcCreateUserStr" size="14"/></td>			
		</tr>	
		<tr>
			<td align="right"><bean:message bundle="case" key="test_time"/>:</td>
			<td align="left">
				<html:text property="cvrSearchInfo.cvrTestTimeStr" readonly="true" size="14" maxlength="32" onclick="SelectDate(this)"/>
			</td>
			<td align="right"><bean:message bundle="case" key="test_user"/>:</td><td align="left"><html:text property="cvrSearchInfo.cvrTestUserStr" size="14"/></td>			
			<td align="right"><bean:message bundle="case" key="correct_time"/>:</td>
			<td align="left">
				<html:text property="cvrSearchInfo.cvrCorrectTimeStr" readonly="true" size="14" maxlength="32" onclick="SelectDate(this)"/>
			</td>
			<td align="right"><bean:message bundle="case" key="correct_user"/>:</td><td align="left"><html:text property="cvrSearchInfo.cvrCorrectUserStr" size="14"/></td>			
		</tr>		
		<tr>
			<td align="right"><bean:message bundle="case" key="important_level"/>:</td>
			<td align="left">
				<html:select property="cvrSearchInfo.cvrImportantLevel" style="width:120px">	
					<html:option value=""></html:option>									
					<html:optionsCollection name="importantLevelList" value="ilId" label="ilName"/>									
				</html:select>
			</td>
			<td align="right"><bean:message bundle="case" key="bug_type"/>:</td>
			<td align="left">
				<html:select property="cvrSearchInfo.cvrBugType" style="width:120px">	
					<html:option value=""></html:option>									
					<html:optionsCollection name="bugTypeList" value="btId" label="btName"/>									
				</html:select>
			</td>
			<td align="right"><bean:message bundle="project" key="version"/>:</td>
			<td align="left">
				<html:select property="cvrSearchInfo.cvrProjectVersion" style="width:120px">	
					<html:option value=""></html:option>									
					<html:optionsCollection name="caseForm" property="projectInfo.projectVersionList" value="pvId" label="pvVersion"/>									
				</html:select>
			</td>
			<td align="right"><bean:message bundle="case" key="page_itemcount"/>:</td>
			<td align="left">
				<html:select property="searchInfo.pageItemCount" style="width:120px" onchange="caseForm.submit();">	
					<html:option value="20">20</html:option>
					<html:option value="30">30</html:option>
					<html:option value="40">40</html:option>
					<html:option value="50">50</html:option>
					<html:option value="60">60</html:option>																	
				</html:select>
			</td>
		</tr>
		<tr>
					
			<td colspan="8" align="right">
				<html:submit styleClass="searchbutton" onclick="initPageNo();">
					&nbsp;
				</html:submit>
			
				<html:submit styleClass="resetbutton" onclick="initPageNo();chgAction(document.all.method,'resetSearchTestCaseforRenference');">
					&nbsp;
				</html:submit>
			</td>
		</tr>
	</table>
				
			

<bean:define id="pageCount" name="caseForm" property="searchInfo.pageItemCount"></bean:define>
<bean:define id="itemNo" name="caseCount"></bean:define>
<pg:pager url="./projectmanage.do" items="<%=Integer.valueOf(itemNo.toString())%>" index="center" maxPageItems="<%=Integer.valueOf(pageCount.toString())%>" maxIndexPages="10" isOffset="<%= true %>" export="offset,currentPageNumber=pageNumber" scope="request">
	<%-- keep track of preference --%>						
	<pg:param name="method" value="searchTestCaseforReference"/>												
	<%-- save pager offset during form changes --%>				
	<input id="pageoffset" type="hidden" name="pager.offset" value="<%= offset %>"/>	
	<jsp:include page="../common/page.jsp" flush="true" />
	
	<table id="tb1" class="sort-table" cellSpacing="1" cellPadding="1" width="100%" border="0">					
		<thead>
			<tr id="node-0">
				<td width="4%"><a href="javascript:expendhideall();"><font color="white">--</font></a></td>				
				<td width="20%" colspan="2" align="center"><bean:message bundle="case" key="module_function"/></td>
				<td width="8%" align="center"><bean:message bundle="case" key="case_code"/></td>
				<td align="center"><bean:message bundle="case" key="test_objective"/></td>				
				<td width="7%" align="center"><bean:message bundle="case" key="create_user"/></td>
				<td width="7%" align="center"><bean:message bundle="case" key="create_time"/></td>				
				
				<td width="4%" align="center"><bean:message bundle="case" key="display"/></td>
				<td width="4%"><input type="checkbox"  name="all" onclick="allCheck(this)"/></td>
			</tr>
			<tr id="node-0-0">
				<td ></td>
				<td align="center"><bean:message bundle="project" key="version_code"/></td>
				<td align="center"><bean:message bundle="case" key="case_status"/></td>
				<td align="center"><bean:message bundle="case" key="test_result"/></td>
				<td align="center"><bean:message bundle="case" key="test_output"/></td>
				<td align="center"><bean:message bundle="case" key="test_user"/></td>
				<td align="center" colspan="3"><bean:message bundle="case" key="test_time"/></td>
				
			</tr>
		</thead>
		<tbody>
				
		<logic:iterate name="caseList" id="c" indexId="i">
			<pg:item>						
				<% int a = i % 2;request.setAttribute("a",a);%>
				<logic:equal name="a" value="0"><tr class="even"  id="<%="node-" + (i+1)%>"></logic:equal>
				<logic:equal name="a" value="1"><tr class="odd" id="<%="node-" + (i+1)%>"></logic:equal>
					<td align="center"><a href="javascript:expendhide('node-<%=(i+1) %>');"><%=i+1 %></a></td>							
												
					<td colspan="2" align="center"><bean:write name="c" property="moduleFunction.entireName"/></td>
					<td align="center"><bean:write name="c" property="tcCode"/></td>
					<td align="left" title='<bean:write name="c" property="tcTestObjective"/>'><bean:write name="c" property="tcTestObjective"/></td>
					<td align="center"><bean:write name="c" property="createUser.personName"/></td>								
					<td align="center"><bean:write name="c" property="tcCreateTimeStr"/></td>
										
					<td align="center">						
						<a href="javascript:openDialog('casemanage.do?method=displayTestCase&id=<bean:write name="c" property="tcId"/>',960,800);"><img border="0" src="pages\images\icon\16x16\authority.gif"></a> 								
					</td>			
					
					<td align="center">	<input type="checkbox" name="selectedCaseList" value="<bean:write name="c" property="tcId"/>"/>	</td>	
				</tr>
				<logic:iterate name="c" property="caseVersionReferenceList" id="cvr" indexId="j">
				<tr id="<%="node-" + (i+1) + "-" + (j+1)%>">
					<td><%=j+1 +")" %></td>
					<td align="center"><bean:write name="cvr" property="projectVersion.pvVersion"/></td>					
					<td align="center">
						<logic:notEmpty name="cvr" property="status">
							<bean:write name="cvr" property="status.csName"/></td>
						</logic:notEmpty>
					<td align="center">
						<logic:notEmpty name="cvr" property="testResult">
							<bean:write name="cvr" property="testResult.trName"/></td>
						</logic:notEmpty>
					</td>
					<td><bean:write name="cvr" property="cvrCaseOutput"/></td></td>
					<td align="center">
						<logic:notEmpty name="cvr" property="testUser">
							<bean:write name="cvr" property="testUser.personName"/></td>
						</logic:notEmpty>
					</td>								
					<td align="center" colspan="3"><bean:write name="cvr" property="cvrTestTimeStr"/></td>
					
				</tr>
				</logic:iterate>
			</pg:item>
		</logic:iterate>
				
		<logic:notPresent name="caseCount">
			<tr>
				<td align="left" COLSPAN="16">
					<bean:message key="noRecord" />
				</td>
			</tr>
		</logic:notPresent>			
		</tbody>
	</table>
	<jsp:include page="../common/page.jsp" flush="true" />

	</pg:pager>
	
<table CELLPADDING="2" CELLSPACING="0" width="100%" border="0">	
<tr>
	<td align="right">
		<html:submit styleClass="confirmbutton" onclick="chgAction(document.all.method,'confirmTestCaseforReference');">
			&nbsp;
		</html:submit>
		<html:button property="" onclick="window.close();" styleClass="button">
			<bean:message key="button.cancel" />
		</html:button>	
	</td>	
</tr>		
</table>
	
</html:form>


<script language="JavaScript">
function allCheck(node1)
{  
	var node=document.getElementsByName("selectedCaseList");  
	for (var x = 0; x < node.length; x++) {  
	     node[x].checked=node1.checked;  
	}  
}  


 function expendhide(trid)
 {    
    var o = document.getElementById("tb1"), r0;
    for(var i=0; i<o.rows.length; i++)
    {
    	r0 = o.rows[i];
    	
    	fi = r0.id.indexOf("-");
    	li = r0.id.lastIndexOf("-");
    	
    	if(fi != li && r0.id.substring(0,li) == trid)
    	{
    		if(r0.style.display == "none")
    		{
    			r0.style.display = "";
    		}
    		else
    		{
    			r0.style.display = "none";
    		}            
    	}        
    }
 }
 
 function expendhideall()
 {    
    var o = document.getElementById("tb1"), r0;
    for(var i=0; i<o.rows.length; i++)
    {
    	r0 = o.rows[i];
    	
    	fi = r0.id.indexOf("-");
    	li = r0.id.lastIndexOf("-");
    	
    	if(fi != li)
    	{
    		if(r0.style.display == "none")
    		{
    			r0.style.display = "";
    		}
    		else
    		{
    			r0.style.display = "none";
    		}            
    	}        
    }
 }


 function chgAction(obj,str){
	obj.value=str;	
 }

 function initPageNo()
 {	
 	document.getElementById("pageoffset").value='0';
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
