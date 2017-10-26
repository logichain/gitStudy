<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>

<html:form action="casemanage.do" onsubmit="return checkFormValidate();">
	<html:errors />
	
	<input type="hidden" name="method" value="saveTestCaseTest">
	
	<html:hidden property="caseInfo.tcModuleFunction"/>
	<html:hidden property="caseInfo.tcCode"/>
	<html:hidden property="caseInfo.tcTestObjective"/>
	<html:hidden property="caseInfo.tcTestContent"/>	
	<table CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">		
		<tr><td width="50%"></td>
			<td align="center"><html:submit styleClass="previousbutton" onclick="chgFormOnsubmit('return true;');chgAction(document.all.method,'testTestCasePrevious');">&nbsp;</html:submit></td>
			<td align="center"><html:submit styleClass="nextbutton" onclick="chgFormOnsubmit('return true;');chgAction(document.all.method,'testTestCaseNext');">&nbsp;</html:submit></td>
			
			<td align="center"><html:submit styleClass="savebutton">&nbsp;</html:submit></td>
			<td align="center"><html:submit styleClass="savenewbutton" onclick="chgAction(document.all.method,'saveTestCaseTestNext');">&nbsp;</html:submit></td>						
			<td align="center"><html:reset styleClass="resetbutton">&nbsp;</html:reset></td>				
		</tr>
		<tr>
			<td align="left" class="strong"><bean:message bundle="case" key="project"/>：<bean:write name="caseForm" property="projectInfo.PName"/></td>
		</tr>
	</table>	
	<table CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">	
		<tr>
			<td width="10%">&nbsp;</td><td width="20%"></td>
			<td width="10%"></td><td width="30%"></td>
			<td rowspan="3" align="left">
				<fieldset style="width:96%;float:right;">
					<legend><bean:message bundle="project" key="version"/></legend>
					<logic:iterate name="caseForm" property="projectInfo.projectVersionList" id="ver" indexId="i">
						<html:checkbox disabled="true" name="caseForm" property='<%="projectInfo.projectVersionList[" + i + "].selected" %>'/>
						<bean:write name="ver" property="pvVersion"/>
	      				<input type="hidden" name='<%="projectInfo.projectVersionList[" + i + "].selected" %>' value="false">
					</logic:iterate>
				</fieldset>
			</td>
		</tr>	
		
		<tr>			
			<td align="right"><bean:message bundle="case" key="module_function"/>：</td>
			<td align="left" colspan="3">			
				<bean:write name="caseForm" property="caseInfo.moduleFunction.entireName"/>			
			</td>			
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>	
			<td align="right"><bean:message bundle="case" key="case_code"/>：</td>
			<td align="left"><bean:write name="caseForm" property="caseInfo.tcCode"/></td>		
			
			<td align="right"><bean:message bundle="case" key="case_type"/>：</td>
			<td align="left">
				<logic:notEmpty name="caseForm" property="caseInfo.caseType">
				<bean:write name="caseForm" property="caseInfo.caseType.ctName"/>	
				</logic:notEmpty>
			</td>
		</tr>	
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="right"><bean:message bundle="case" key="test_objective"/>：</td>
			<td colspan="4" align="left"><bean:write name="caseForm" property="caseInfo.tcTestObjective"/></td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="right"><bean:message bundle="case" key="test_content"/>：</td>
			<td colspan="4" align="left"><bean:write name="caseForm" property="caseInfo.tcTestContent"/></td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="right">
				<bean:message bundle="project" key="project_attachment" />：
			</td>
			<td  colspan="3" align="left">
				<div style="width:100%;height:20px;background:white;">
				<logic:iterate id="am" name="caseForm" property="caseInfo.attachmentList" indexId="i">
				<logic:notEqual name="am" property="caFlag" value="-1">											
					<a href="casemanage.do?method=downloadAttachment&id=<bean:write name="am" property="caId"/>"><bean:write name="am" property="caName"/></a>；
				</logic:notEqual>
				</logic:iterate>
				</div>				
			</td>				
		</tr>
		<tr><td>&nbsp;</td></tr>
	</table>		
	
	<fieldset style="width:99%;float:left;">
	<legend><bean:message bundle="case" key="test_result"/></legend>
		<table class="sort-table" cellSpacing="1" cellPadding="1" width="100%" border="0">		
			<thead>
				<tr>
					<th width="10%"><bean:message bundle="project" key="project_version"/></th>
					<th width="10%"><bean:message bundle="case" key="test_result"/></th>
					<th width="10%"><bean:message bundle="case" key="important_level"/></th>
					<th width="10%"><bean:message bundle="case" key="bug_type"/></th>
					<th><bean:message bundle="case" key="test_output"/></th>
					<th width="5%"></th>
				</tr>
			</thead>
				
			<tr>	
				<td align="center"><bean:write name="caseForm" property="caseInfo.currentCaseVersionReference.projectVersion.pvVersion"/></td>				
				<td align="center" valign="top">	
					<html:select styleId="testResult" property="caseInfo.currentCaseVersionReference.cvrCaseResult" style="width:90px">	
														
						<html:optionsCollection name="testResultList" value="trId" label="trName"/>									
					</html:select>
				</td>				
				<td align="center" valign="top">
					<html:select  styleId="importLevel" property="caseInfo.currentCaseVersionReference.cvrImportantLevel" style="width:90px">	
						<html:option value=""></html:option>									
						<html:optionsCollection name="importantLevelList" value="ilId" label="ilName"/>									
					</html:select>
				</td>				
				<td align="center" valign="top">
					<html:select  styleId="bugType" property="caseInfo.currentCaseVersionReference.cvrBugType" style="width:90px">	
						<html:option value=""></html:option>									
						<html:optionsCollection name="bugTypeList" value="btId" label="btName"/>									
					</html:select>
				</td>
				<td align="left">
					<html:textarea styleId="output" property="caseInfo.currentCaseVersionReference.cvrCaseOutput" cols="60" rows="4"></html:textarea>
				</td>
			</tr>	
			<tr>
				<td align="right">
					<bean:message bundle="project" key="project_attachment" />：
				</td>
				<td  colspan="4" align="left">
					<div style="width:100%;height:20px;background:white;">
					<logic:iterate id="am" name="caseForm" property="caseInfo.currentCaseVersionReference.attachmentList" indexId="i">
					<logic:notEqual name="am" property="caFlag" value="-1">
						<span title="<bean:write name="am" property="caLocalUrl"/>">
							<bean:write name="am" property="caName"/>
							<a href="casemanage.do?method=deleteCvrAttachment&index=<%=i %>"><img border="0" src="pages\images\icon\16x16\delete.gif"></a>；
						</span>		
					</logic:notEqual>				
					</logic:iterate>
					</div>
				</td>			
				<td align="left">
					<html:button property="" onclick="openDialog('casemanage.do?method=addCvrAttachment',600,240);"><bean:message bundle="project" key="button_addattachment"/></html:button>
				</td>
			</tr>	
		</table>
	</fieldset>
	
							
	<table width="100%">
		<tr>
			<td width="50%" align="left">				
				<fieldset style="width:98%;float:left;">
					<legend><bean:message bundle="case" key="test_step"/></legend>
					<html:textarea readonly="true" cols="60" rows="10" property="caseInfo.tcTestStep"></html:textarea>													
				</fieldset>	
			</td>
			<td align="left" rowspan="2" valign="top">
				<fieldset style="width:98%;float:left;">
					<legend><bean:message bundle="case" key="case_attachment_preview"/></legend>
					<logic:iterate id="am" name="caseForm" property="caseInfo.attachmentList" indexId="i">
					<logic:notEqual name="am" property="caFlag" value="-1">
						<img src="<bean:write name="am" property="caUrl"/>" title="<bean:write name="am" property="caName"/>" width="200" height="200" border="1">		
					</logic:notEqual>				
					</logic:iterate>					
				</fieldset>
			</td>
		</tr>
		<tr>
			<td align="left">				
				<fieldset style="width:98%;float:left;">
					<legend><bean:message bundle="case" key="intend_output"/></legend>
					<html:textarea readonly="true" cols="60" rows="5" property="caseInfo.tcIntendOutput"></html:textarea>							
				</fieldset>
			</td>
			
		</tr>
				
		<bean:define id="ci" name="caseForm" property="caseInfo"></bean:define>
		<c:set var="caseInfo" value="${ci}" scope="request" />
		<c:import url="/WEB-INF/pages/casemanage/recordList.jsp"></c:import>
	</table>
			 
</html:form>

<script language="JavaScript">	

 function chgAction(obj,str){
	obj.value=str;
 }
 
 function chgFormOnsubmit(str){  	 	
	caseForm.onsubmit="function onsubmit(){" + str + "}";	
 }
 
 function checkFormValidate()
{	
	var pname = document.getElementById("testResult").value;
	if(pname == "")
	{
		alert("测试结果  不能为空");
		return false;
	} 
	
	if(pname==2)
	{
		var level = document.getElementById("importLevel").value;	
		if(level == "")
		{
			alert("重要等级  不能为空");
			return false;
		} 
		
		var type = document.getElementById("bugType").value;	
		if(type == "")
		{
			alert("异常类型  不能为空");
			return false;
		}
		
		var output = document.getElementById("output").value;	
		if(output == "")
		{
			alert("输出  不能为空");
			return false;
		}
		
	}
	 	 	
	return true;
}

 function chgFormOnsubmit(str){  	
 caseForm.onsubmit="function onsubmit(){" + str + "}";	
}

function submitForm()
{
	chgFormOnsubmit('return true;');
	chgAction(document.all.method,'refreshCaseTest');
	caseForm.submit();
}

function openDialog(loadpos,WWidth,WHeight)//Lock   Size 
{   
	submitForm();
	
	var WLeft = Math.ceil((window.screen.width - WWidth) / 2);   
	var WTop = Math.ceil((window.screen.height - WHeight) / 2); 
	var features = 'width=' + WWidth + 'px,' +	'height=' + WHeight + 'px,' + 'left=' + WLeft + 'px,' + 'top=' + WTop + 'px'; 
		
	WinOP = window.open(loadpos,"_blank",features); 
	WinOP.focus();	   
}	

</script>

