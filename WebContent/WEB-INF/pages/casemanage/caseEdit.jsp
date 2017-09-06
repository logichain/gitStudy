<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>
<script type="text/javascript" charset="utf-8" src="<c:url value='/resources/ueditor/ueditor.config.js'/>"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value='/resources/ueditor/ueditor.all.js'/>"></script>


<html:form action="casemanage.do" onsubmit="return validateCaseForm(this);">
	<html:errors />
	
	<input type="hidden" name="method" value="saveTestCase">
	<html:hidden property="caseInfo.moduleId"/>
	<html:hidden property="caseInfo.tcModuleFunction"/>
	
	<table CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">		
		<tr><td width="50%"></td>
			<td align="center"><html:submit styleClass="previousbutton" onclick="chgAction(document.all.method,'editTestCasePrevious');">&nbsp;</html:submit></td>
			<td align="center"><html:submit styleClass="nextbutton" onclick="chgAction(document.all.method,'editTestCaseNext');">&nbsp;</html:submit></td>	
			
			<td align="center"><html:submit styleClass="savebutton">&nbsp;</html:submit></td>
			<td align="center"><html:submit styleClass="savenewbutton" onclick="chgAction(document.all.method,'saveTestCaseEditNext');">&nbsp;</html:submit></td>
			<td align="center"><html:submit styleClass="savenewbutton" onclick="chgAction(document.all.method,'saveTestCaseCopy');">&nbsp;</html:submit></td>						
			<td align="center"><html:reset styleClass="resetbutton">&nbsp;</html:reset></td>				
		</tr>
		<tr>
			<td align="left" class="strong"><bean:message bundle="case" key="project"/>£º<bean:write name="caseForm" property="projectInfo.PName"/></td>
		</tr>
	</table>	
	<table cellSpacing="0" cellPadding="0" WIDTH="100%" border="0">	
		<tr>
			<td width="12%"></td><td width="15%"></td>
			<td width="10%"></td><td width="15%"></td>
			<td width="10%"></td><td width="15%"></td>
			<td width="20%">&nbsp;</td>		
		</tr>	
		<tr>
			<td align="right"><bean:message bundle="case" key="module"/>£º</td>
			<td align="left"><bean:write name="caseForm" property="caseInfo.moduleFunction.projectModule.pmName"/></td>
			<td align="right"><bean:message bundle="case" key="module_function"/>£º</td>
			<td align="left"><bean:write name="caseForm" property="caseInfo.moduleFunction.entireName"/></td>
			
			
			<td align="right"><bean:message bundle="case" key="case_code"/>£º</td>
			<td align="left"><html:text property="caseInfo.tcCode" size="10" maxlength="45"/></td>
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
			<td align="right"><bean:message bundle="case" key="test_objective"/>£º</td>
			<td colspan="5" align="left"><html:text property="caseInfo.tcTestObjective" size="100" maxlength="100"/></td>
		</tr>
		<tr>
			<td align="right"><bean:message bundle="case" key="test_content"/>£º</td>
			<td colspan="5" align="left"><html:text property="caseInfo.tcTestContent" size="100" maxlength="100"/></td>
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
						<html:select name="caseForm" property='<%="caseInfo.caseVersionReferenceList[" + i + "].cvrCaseResult"%>' style="width:120px">	
							<html:option value=""></html:option>									
							<html:optionsCollection name="testResultList" value="trId" label="trName"/>									
						</html:select>
					</td>				
					<td align="center">
						<html:select name="caseForm" property='<%="caseInfo.caseVersionReferenceList[" + i + "].cvrImportantLevel"%>' style="width:120px">	
							<html:option value=""></html:option>									
							<html:optionsCollection name="importantLevelList" value="ilId" label="ilName"/>									
						</html:select>
					</td>				
					<td align="center">
						<html:select name="caseForm" property='<%="caseInfo.caseVersionReferenceList[" + i + "].cvrBugType"%>' style="width:120px">	
							<html:option value=""></html:option>									
							<html:optionsCollection name="bugTypeList" value="btId" label="btName"/>									
						</html:select>
					</td>
					<td align="left"><html:text name="caseForm" property='<%="caseInfo.caseVersionReferenceList[" + i + "].cvrCaseOutput"%>' size="93" maxlength="200"/></td>
				</tr>		
				</logic:notEqual>
			</logic:iterate>		
		</table>
	</fieldset>
	
	<bean:define id="c" name="caseForm" property="caseInfo"></bean:define>		
	<table width="100%">
		<tr>
			<td width="50%" rowspan="2" align="center">
				<fieldset style="width:98%;float:left;">
					<legend><bean:message bundle="case" key="test_step"/></legend>
					<div>
						<textarea class="putinscroll2" id="stepeditor" name="caseInfo.tcTestStep"><%=((org.mds.test.bean.TestCase)c).getTcTestStep()%></textarea>				    
					</div>						
				</fieldset>
			</td>
			<td align="center">				
				<fieldset style="width:98%;float:left;">
					<legend><bean:message bundle="case" key="test_remark"/></legend>
					<div>
						<textarea class="putinscroll1" id="remarkeditor" name="caseInfo.tcRemark"><%=((org.mds.test.bean.TestCase)c).getTcRemark()%></textarea>
					</div>						
				</fieldset>
			</td>
		</tr>
		<tr>		
			<td align="center">
				<fieldset style="width:98%;float:left;">
					<legend><bean:message bundle="case" key="intend_output"/></legend>
					<div>
						<textarea class="putinscroll1" id="outputeditor" name="caseInfo.tcIntendOutput"><%=((org.mds.test.bean.TestCase)c).getTcIntendOutput()%></textarea>
					</div>						
				</fieldset>
			</td>
		</tr>
				
		<c:set var="${caseInfo}" value="${c}" scope="request" />
		<c:import url="/WEB-INF/pages/casemanage/recordList.jsp"></c:import>
	</table>
	
</html:form>


<html:javascript formName="caseForm" dynamicJavascript="true" staticJavascript="false" />
<script type="text/javascript" src="<html:rewrite forward='staticjavascript'/>"></script>

<script language="JavaScript">	
	UE.getEditor('stepeditor',{initialFrameWidth:480,initialFrameHeight:600,maximumWords:1000 });
    UE.getEditor('remarkeditor',{initialFrameWidth:480,initialFrameHeight:200,maximumWords:1000  });
    UE.getEditor('outputeditor',{initialFrameWidth:480,initialFrameHeight:200,maximumWords:200  });

 function chgAction(obj,str){
	obj.value=str;
 }


</script>
