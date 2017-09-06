<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>
<script type="text/javascript" charset="utf-8" src="<c:url value='/resources/ueditor/ueditor.config.js'/>"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value='/resources/ueditor/ueditor.all.js'/>"></script>


<html:form action="casemanage.do" onsubmit="return validateCaseForm(this);">
	<html:errors />
	
	<input type="hidden" name="method" value="saveTestCase">	
	
	<table CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">		
		<tr><td width="70%"></td>
			<td align="center"><html:submit styleClass="savebutton">&nbsp;</html:submit></td>
			<td align="center"><html:submit styleClass="savenewbutton" onclick="chgAction(document.all.method,'saveTestCaseNew');">&nbsp;</html:submit></td>
			<td align="center"><html:submit styleClass="savenewbutton" onclick="chgAction(document.all.method,'saveTestCaseCopy');">&nbsp;</html:submit></td>						
			<td align="center"><html:reset styleClass="resetbutton">&nbsp;</html:reset></td>				
		</tr>
		<tr>
			<td align="left" class="strong"><bean:message bundle="case" key="project"/>£º<bean:write name="caseForm" property="projectInfo.PName"/></td>
		</tr>
	</table>	
	<table CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">	
		<tr>
			<td width="12%"></td><td width="12%"></td>
			<td width="8%"></td><td width="20%"></td>
			<td width="8%"></td><td width="15%"></td>
			<td width="20%">&nbsp;</td>			
		</tr>	
		<tr>
			<td align="right"><bean:message bundle="case" key="module"/>£º</td>
			<td align="left">
				<html:select property="caseInfo.moduleId" style="width:120px" onchange="chgAction(document.all.method,'setCaseModuleForInput');caseForm.submit();">	
					<html:option value=""></html:option>
					<html:optionsCollection name="caseForm" property="projectInfo.moduleList" value="pmId" label="pmName"/>
				</html:select>
			</td>
			<td align="right"><bean:message bundle="case" key="module_function"/>£º</td>
			<td align="left">			
				<html:select property="caseInfo.tcModuleFunction" style="width:180px">	
					<html:option value=""></html:option>									
					<html:optionsCollection name="caseForm" property="projectInfo.selectedProjectModule.allModuleFunctionList" value="muId" label="entireName"/>									
				</html:select>			
			</td>			
			<td align="right"><bean:message bundle="case" key="case_code"/>£º</td><td align="left"><html:text property="caseInfo.tcCode" size="14" maxlength="45"/></td>
			<td rowspan="3" align="left">
				<fieldset style="width:80%;float:center;">
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
			<td align="right"><bean:message bundle="case" key="test_objective"/>£º</td>
			<td colspan="6" align="left"><html:text property="caseInfo.tcTestObjective" size="100" maxlength="100"/></td>
		</tr>
		<tr>
			<td align="right"><bean:message bundle="case" key="test_content"/>£º</td>
			<td colspan="6" align="left"><html:text property="caseInfo.tcTestContent" size="100" maxlength="100"/></td>
		</tr>
	</table>		
	<bean:define id="c" name="caseForm" property="caseInfo"></bean:define>
		
	<table width="100%">
		<tr><td width="50%" rowspan="2" align="center">
			<fieldset style="width:98%;float:left;">
				<legend><bean:message bundle="case" key="test_step"/></legend>
				<textarea class="putinscroll2" id="stepeditor" name="caseInfo.tcTestStep"><%=((org.mds.test.bean.TestCase)c).getTcTestStep()%></textarea>
			</fieldset>
		</td>
		<td width="50%" align="center">					
			<fieldset style="width:98%;float:left;">
				<legend><bean:message bundle="case" key="test_remark"/></legend>
				<textarea class="putinscroll1" id="remarkeditor" name="caseInfo.tcRemark"><%=((org.mds.test.bean.TestCase)c).getTcRemark()%></textarea>
			</fieldset>
		</td>
		</tr>
		<tr>			
			<td align="center">
				<fieldset style="width:98%;float:left;">
					<legend><bean:message bundle="case" key="intend_output"/></legend>
					<textarea class="putinscroll1" id="outputeditor" name="caseInfo.tcIntendOutput"><%=((org.mds.test.bean.TestCase)c).getTcIntendOutput()%></textarea>
				</fieldset>
			</td>
		</tr>
	</table>
			
			 
</html:form>


<html:javascript formName="caseForm" dynamicJavascript="true" staticJavascript="false" />
<script type="text/javascript" src="<html:rewrite forward='staticjavascript'/>"></script>

<script language="JavaScript">	
UE.getEditor('stepeditor',{initialFrameWidth:480,initialFrameHeight:600,maximumWords:1000});
UE.getEditor('remarkeditor',{initialFrameWidth:480,initialFrameHeight:200,maximumWords:1000});
UE.getEditor('outputeditor',{initialFrameWidth:480,initialFrameHeight:200,maximumWords:200});

function chgAction(obj,str){
	obj.value=str;
}

</script>

