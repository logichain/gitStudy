<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>

<html:form action="casemanage.do" onsubmit="return validateCaseForm(this);">
	<html:errors />
	
	<input type="hidden" name="method" value="saveTestCase">
		
	<table CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">		
		<tr><td width="50%"></td>
			<td align="center"><html:submit styleClass="previousbutton" onclick="chgAction(document.all.method,'editTestCasePrevious');chgFormOnsubmit('return true;');">&nbsp;</html:submit></td>
			<td align="center"><html:submit styleClass="nextbutton" onclick="chgAction(document.all.method,'editTestCaseNext');chgFormOnsubmit('return true;');">&nbsp;</html:submit></td>	
			
			<td align="center"><html:submit styleClass="savebutton">&nbsp;</html:submit></td>
<%-- 			<td align="center"><html:submit styleClass="savenewbutton" onclick="chgAction(document.all.method,'saveTestCaseEditNext');">&nbsp;</html:submit></td> --%>
			<td align="center"><html:submit styleClass="savecopybutton" onclick="chgAction(document.all.method,'saveTestCaseCopy');">&nbsp;</html:submit></td>						
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
			<td align="right"><bean:message bundle="case" key="module"/>:</td>
			<td align="left">
				<html:text property="caseInfo.projectModule.pmName" size="14" readonly="true"/>
				<a href="javascript:openDialog('casemanage.do?method=selectProjectModule&opType=caseEdit',960,800);"><img border="0" src="pages\images\icon\16x16\search.gif"></a>
			</td>
			<td align="right"><bean:message bundle="case" key="module_function"/>:</td>
			<td align="left">	
				<html:text property="caseInfo.moduleFunction.muName" size="14" readonly="true"/>
				<a href="javascript:openDialog('casemanage.do?method=selectModuleFunction&opType=caseEdit',960,800);"><img border="0" src="pages\images\icon\16x16\search.gif"></a>
			</td>				
		</tr>	
		<tr>
			<td align="right"><bean:message bundle="case" key="case_code"/>£º</td>
			<td align="left"><html:text property="caseInfo.tcCode" size="14" maxlength="45" readonly="true" disabled="true"/></td>
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
	
	
	<table width="100%">
		<tr>
			<td width="50%" align="center">
				<fieldset style="width:98%;float:left;">
					<legend><bean:message bundle="case" key="test_step"/></legend>					
					<html:textarea cols="60" rows="10" property="caseInfo.tcTestStep"></html:textarea>									
				</fieldset>
			</td>
			<td align="center" rowspan="2" valign="top">				
				<fieldset style="width:98%;float:left;">
				<legend><bean:message bundle="case" key="case_attachment_preview"/></legend>
				<logic:iterate id="am" name="caseForm" property="caseInfo.attachmentList" indexId="i">
				<logic:notEqual name="am" property="caFlag" value="-1">
					<img src="<bean:write name="am" property="caUrl"/>" title="<bean:write name="am" property="caName"/>"  width="200" height="200" border="1">		
				</logic:notEqual>				
				</logic:iterate>
								
				</fieldset>
			</td>
		</tr>
		<tr>		
			<td align="center">
				<fieldset style="width:98%;float:left;">
					<legend><bean:message bundle="case" key="intend_output"/></legend>					
					<html:textarea cols="60" rows="5" property="caseInfo.tcIntendOutput"></html:textarea>										
				</fieldset>
			</td>
		</tr>		
	</table>
	<table width="100%">
	<tr><td>
	<fieldset style="width:98%;float:left;">
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
					</td>				
					<td align="center">
						<logic:notEmpty name="cvr" property="importantLevel"><bean:write name="cvr" property="importantLevel.ilName"/></logic:notEmpty>
					</td>				
					<td align="center">
						<logic:notEmpty name="cvr" property="bugType"><bean:write name="cvr" property="bugType.btName"/></logic:notEmpty>
					</td>
					<td align="left">
						<bean:write name="cvr" property="cvrCaseOutput"/>	
					</td>
				</tr>		
				</logic:notEqual>
			</logic:iterate>		
		</table>
	</fieldset>
		
	<bean:define id="c" name="caseForm" property="caseInfo"></bean:define>
	<c:set var="caseInfo" value="${c}" scope="request" />
	<c:import url="/WEB-INF/pages/casemanage/recordList.jsp"></c:import>
	</td></tr>
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
		chgAction(document.all.method,'refreshCaseEdit');
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
