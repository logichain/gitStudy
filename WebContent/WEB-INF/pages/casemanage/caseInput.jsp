<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>

<html:form action="casemanage.do" onsubmit="return validateCaseForm(this);">
	<html:errors />
	
	<input type="hidden" name="method" value="saveTestCase">	
	
	<table CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">		
		<tr><td width="70%"></td>
			<td align="center"><html:submit styleClass="savebutton">&nbsp;</html:submit></td>
			<td align="center"><html:submit styleClass="savenewbutton" onclick="chgAction(document.all.method,'saveTestCaseNew');">&nbsp;</html:submit></td>
			<td align="center"><html:submit styleClass="" onclick="chgAction(document.all.method,'saveTestCaseCopy');">copy new</html:submit></td>						
			<td align="center"><html:reset styleClass="resetbutton">&nbsp;</html:reset></td>				
		</tr>
		<tr>
			<td align="left" class="strong"><bean:message bundle="case" key="project"/>��<bean:write name="caseForm" property="projectInfo.PName"/></td>
		</tr>
	</table>	
	<table CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">	
		<tr>
			<td width="10%">&nbsp;</td><td width="20%"></td>
			<td width="10%"></td><td width="20%"></td>
			<td rowspan="3" align="left">
				<fieldset style="width:96%;float:center;">
					<legend><bean:message bundle="project" key="version"/></legend>
					<logic:iterate name="caseForm" property="projectInfo.projectVersionList" id="ver" indexId="i">
						<html:checkbox name="caseForm" property='<%="projectInfo.projectVersionList[" + i + "].selected" %>'/>
						<bean:write name="ver" property="pvVersion"/>
	      				<input type="hidden" name='<%="projectInfo.projectVersionList[" + i + "].selected" %>' value="false">
					</logic:iterate>
				</fieldset>
			</td>			
		</tr>	
		<tr>

			<td align="right"><bean:message bundle="case" key="module_function"/>��</td>
			<td align="left">			
				<html:select styleId="editable-select2" styleClass="form-control" property="caseInfo.tcModuleFunction" style="width:180px">	
														
					<html:optionsCollection name="caseForm" property="projectInfo.allModuleFunctionList" value="muId" label="entireName"/>									
				</html:select>			
			</td>						
			
		</tr>	
		<tr>
			<td align="right"><bean:message bundle="case" key="case_code"/>��</td>
			<td align="left"><html:text property="caseInfo.tcCode" size="14" maxlength="45" readonly="true" disabled="true"/></td>
			<td align="right"><bean:message bundle="case" key="case_type"/>��</td>
			<td align="left">
				<html:select property="caseInfo.tcType" style="width:120px">													
					<html:optionsCollection name="caseTypeList" value="ctId" label="ctName"/>									
				</html:select>
			</td>
		</tr>
		<tr>
			<td align="right"><bean:message bundle="case" key="test_objective"/>��</td>
			<td colspan="3" align="left"><html:text property="caseInfo.tcTestObjective" size="100" maxlength="100"/></td>
		</tr>
		<tr>
			<td align="right"><bean:message bundle="case" key="test_content"/>��</td>
			<td colspan="3" align="left"><html:text property="caseInfo.tcTestContent" size="100" maxlength="100"/></td>
		</tr>
		
		<tr>
			<td align="right">
				<bean:message bundle="project" key="project_attachment" />��
			</td>
			<td colspan="3" align="left">
				<div style="width:100%;height:20px;background:white;">
				<logic:iterate id="am" name="caseForm" property="caseInfo.attachmentList" indexId="i">
				<logic:notEqual name="am" property="caFlag" value="-1">
					<span title="<bean:write name="am" property="caLocalUrl"/>">
						<bean:write name="am" property="caName"/>
						<a href="casemanage.do?method=deleteAttachment&opType=caseInput&index=<%=i %>"><img border="0" src="pages\images\icon\16x16\delete.gif"></a>��
					</span>			
				</logic:notEqual>		
				</logic:iterate>
				</div>
			</td>			
			<td align="left">
				<html:button property="" onclick="openDialog('casemanage.do?method=addAttachment&opType=caseInput',600,240);"><bean:message bundle="project" key="button_addattachment"/></html:button>
			</td>
		</tr>	
	</table>		
				
	<table width="100%">
		<tr>
			<td width="50%" rowspan="2" align="center">
				<fieldset style="width:98%;float:left;">
					<legend><bean:message bundle="case" key="test_step"/></legend>					
					<html:textarea cols="60" rows="30" property="caseInfo.tcTestStep"></html:textarea>									
				</fieldset>
			</td>
			<td align="center">				
				<fieldset style="width:98%;float:left;">
					<legend><bean:message bundle="case" key="test_remark"/></legend>					
					<html:textarea cols="60" rows="16" property="caseInfo.tcRemark"></html:textarea>										
				</fieldset>
			</td>
		</tr>
		<tr>		
			<td align="center">
				<fieldset style="width:98%;float:left;">
					<legend><bean:message bundle="case" key="intend_output"/></legend>					
					<html:textarea cols="60" rows="10" property="caseInfo.tcIntendOutput"></html:textarea>										
				</fieldset>
			</td>
		</tr>
	</table>
			
			 
</html:form>


<html:javascript formName="caseForm" dynamicJavascript="true" staticJavascript="false" />
<script type="text/javascript" src="<html:rewrite forward='staticjavascript'/>"></script>

<script language="JavaScript">	

$('#editable-select2').editableSelect({
	effects: 'slide'
});
function chgAction(obj,str){
	obj.value=str;
}
function chgFormOnsubmit(str){  	 	
	caseForm.onsubmit="function onsubmit(){" + str + "}";	
 }
 
function submitForm()
{
	chgFormOnsubmit('return true;');
	chgAction(document.all.method,'refreshCaseInput');
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

