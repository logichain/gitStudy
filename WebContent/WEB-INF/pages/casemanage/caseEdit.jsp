<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>

<html:form action="casemanage.do" onsubmit="return validateCaseForm(this);">
	<html:errors />
	
	<input type="hidden" name="method" value="saveTestCase">
	<html:hidden property="caseInfo.moduleId"/>
	<html:hidden property="caseInfo.tcModuleFunction"/>
	
	<table CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">		
		<tr><td width="50%"></td>
			<td align="center"><html:submit styleClass="previousbutton" onclick="chgAction(document.all.method,'editTestCasePrevious');chgFormOnsubmit('return true;');">&nbsp;</html:submit></td>
			<td align="center"><html:submit styleClass="nextbutton" onclick="chgAction(document.all.method,'editTestCaseNext');chgFormOnsubmit('return true;');">&nbsp;</html:submit></td>	
			
			<td align="center"><html:submit styleClass="savebutton">&nbsp;</html:submit></td>
			<td align="center"><html:submit styleClass="savenewbutton" onclick="chgAction(document.all.method,'saveTestCaseEditNext');">&nbsp;</html:submit></td>
			<td align="center"><html:submit styleClass="" onclick="chgAction(document.all.method,'saveTestCaseCopy');">copy new</html:submit></td>						
			<td align="center"><html:reset styleClass="resetbutton">&nbsp;</html:reset></td>				
		</tr>
		<tr>
			<td align="left" class="strong"><bean:message bundle="case" key="project"/>£º<bean:write name="caseForm" property="projectInfo.PName"/></td>
		</tr>
	</table>	
	<table cellSpacing="0" cellPadding="0" WIDTH="100%" border="0">	
		<tr>
			<td width="10%">&nbsp;</td><td width="20%"></td>
			<td width="10%"></td><td width="20%"></td>
			<td rowspan="3" align="left">
				<fieldset style="width:96%;float:right;">
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
			<td align="right"><bean:message bundle="case" key="module_function"/>£º</td>
			<td align="left" colspan="3"><bean:write name="caseForm" property="caseInfo.moduleFunction.entireName"/></td>					
		</tr>	
		<tr>
			<td align="right"><bean:message bundle="case" key="case_code"/>£º</td>
			<td align="left"><html:text property="caseInfo.tcCode" size="14" maxlength="45"/></td>
			<td align="right"><bean:message bundle="case" key="case_type"/>£º</td>
			<td align="left">
				<html:select property="caseInfo.tcType" style="width:120px">													
					<html:optionsCollection name="caseTypeList" value="ctId" label="ctName"/>									
				</html:select>
			</td>
		</tr>
		<tr>
			<td align="right"><bean:message bundle="case" key="test_objective"/>£º</td>
			<td colspan="3" align="left"><html:text property="caseInfo.tcTestObjective" size="100" maxlength="100"/></td>
		</tr>
		<tr>
			<td align="right"><bean:message bundle="case" key="test_content"/>£º</td>
			<td colspan="3" align="left"><html:text property="caseInfo.tcTestContent" size="100" maxlength="100"/></td>
		</tr>
		
		<tr>
			<td align="right">
				<bean:message bundle="project" key="project_attachment" />£º
			</td>
			<td  colspan="3" align="left">
				<div style="width:100%;height:20px;background:white;">
				<logic:iterate id="am" name="caseForm" property="caseInfo.attachmentList" indexId="i">
				<logic:notEqual name="am" property="caFlag" value="-1">
					<span title="<bean:write name="am" property="caLocalUrl"/>">
						<bean:write name="am" property="caName"/>
						<a href="casemanage.do?method=deleteAttachment&opType=caseEdit&index=<%=i %>"><img border="0" src="pages\images\icon\16x16\delete.gif"></a>£»
					</span>		
				</logic:notEqual>				
				</logic:iterate>
				</div>
			</td>			
			<td align="left">
				<html:button property="" onclick="openDialog('casemanage.do?method=addAttachment&opType=caseEdit',600,240);"><bean:message bundle="project" key="button_addattachment"/></html:button>
			</td>
		</tr>
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
				</tr>
			</thead>
			<logic:iterate name="caseForm" property="caseInfo.caseVersionReferenceList" id="cvr" indexId="i">
				<logic:notEqual name="cvr" property="cvrCaseStatus" value="2">
				<tr>	
					<td align="center"><bean:write name="cvr" property="projectVersion.pvVersion"/></td>				
					<td align="center">	
						<logic:notEmpty name="cvr" property="testResult"><bean:write name="cvr" property="testResult.trName"/></logic:notEmpty>
						
<%-- 						<html:select name="caseForm" property='<%="caseInfo.caseVersionReferenceList[" + i + "].cvrCaseResult"%>' style="width:120px">	 --%>
<%-- 							<html:option value=""></html:option>									 --%>
<%-- 							<html:optionsCollection name="testResultList" value="trId" label="trName"/>									 --%>
<%-- 						</html:select> --%>
					</td>				
					<td align="center">
						<logic:notEmpty name="cvr" property="importantLevel"><bean:write name="cvr" property="importantLevel.ilName"/></logic:notEmpty>
						
<%-- 						<html:select name="caseForm" property='<%="caseInfo.caseVersionReferenceList[" + i + "].cvrImportantLevel"%>' style="width:120px">	 --%>
<%-- 							<html:option value=""></html:option>									 --%>
<%-- 							<html:optionsCollection name="importantLevelList" value="ilId" label="ilName"/>									 --%>
<%-- 						</html:select> --%>
					</td>				
					<td align="center">
						<logic:notEmpty name="cvr" property="bugType"><bean:write name="cvr" property="bugType.btName"/></logic:notEmpty>
						
<%-- 						<html:select name="caseForm" property='<%="caseInfo.caseVersionReferenceList[" + i + "].cvrBugType"%>' style="width:120px">	 --%>
<%-- 							<html:option value=""></html:option>									 --%>
<%-- 							<html:optionsCollection name="bugTypeList" value="btId" label="btName"/>									 --%>
<%-- 						</html:select> --%>
					</td>
					<td align="left">
						<bean:write name="cvr" property="cvrCaseOutput"/>
<%-- 						<html:text name="caseForm" property='<%="caseInfo.caseVersionReferenceList[" + i + "].cvrCaseOutput"%>' size="93" maxlength="200"/> --%>
						
					</td>
				</tr>		
				</logic:notEqual>
			</logic:iterate>		
		</table>
	</fieldset>
	
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
				
		<bean:define id="c" name="caseForm" property="caseInfo"></bean:define>
		<c:set var="caseInfo" value="${c}" scope="request" />
		<c:import url="/WEB-INF/pages/casemanage/recordList.jsp"></c:import>
	</table>
	
</html:form>


<html:javascript formName="caseForm" dynamicJavascript="true" staticJavascript="false" />
<script type="text/javascript" src="<html:rewrite forward='staticjavascript'/>"></script>

<script language="JavaScript">	

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
