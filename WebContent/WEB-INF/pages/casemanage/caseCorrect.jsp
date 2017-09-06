<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>
<script type="text/javascript" charset="utf-8" src="<c:url value='/resources/ueditor/ueditor.config.js'/>"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value='/resources/ueditor/ueditor.all.js'/>"></script>

<html:form action="casemanage.do" onsubmit="return checkFormValidate();">
	<html:errors />
	
	<input type="hidden" name="method" value="saveTestCaseCorrect">
	<html:hidden property="caseInfo.moduleId"/>
	<html:hidden property="caseInfo.tcModuleFunction"/>	
	<table CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">		
		<tr><td width="50%"></td>
			<td align="center"><html:submit styleClass="previousbutton" onclick="chgAction(document.all.method,'correctTestCasePrevious');">&nbsp;</html:submit></td>
			<td align="center"><html:submit styleClass="nextbutton" onclick="chgAction(document.all.method,'correctTestCaseNext');">&nbsp;</html:submit></td>
			
			<td align="center"><html:submit styleClass="savebutton">&nbsp;</html:submit></td>
			<td align="center"><html:submit styleClass="savenewbutton" onclick="chgAction(document.all.method,'saveTestCaseCorrectNext');">&nbsp;</html:submit></td>						
			<td align="center"><html:reset styleClass="resetbutton">&nbsp;</html:reset></td>				
		</tr>
		<tr>
			<td align="left" class="strong"><bean:message bundle="case" key="project"/>：<bean:write name="caseForm" property="projectInfo.PName"/></td>
		</tr>
	</table>	
	<table CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">	
		<tr>
			<td width="10%">&nbsp;</td><td width="18%"></td>
			<td width="10%"></td><td width="18%"></td>
			<td width="10%"></td><td width="18%"></td>
			<td></td>	
		</tr>	
		<tr>
			<td align="right"><bean:message bundle="case" key="module"/>：</td>
			<td align="left">
				<bean:write name="caseForm" property="caseInfo.moduleFunction.projectModule.pmName"/>
			</td>
			<td align="right"><bean:message bundle="case" key="module_function"/>：</td>
			<td align="left">			
				<bean:write name="caseForm" property="caseInfo.moduleFunction.entireName"/>			
			</td>			
			<td align="right"><bean:message bundle="case" key="case_code"/>：</td>
			<td align="left"><bean:write name="caseForm" property="caseInfo.tcCode"/></td>
			
			<td rowspan="3" align="center">
				<fieldset style="width:96%;">
					<legend><bean:message bundle="project" key="version"/></legend>
					<logic:iterate name="caseForm" property="projectInfo.projectVersionList" id="ver" indexId="i">
						<html:checkbox disabled="true" name="caseForm" property='<%="projectInfo.projectVersionList[" + i + "].selected" %>'/>
						<bean:write name="ver" property="pvVersion"/>
	      				<input type="hidden" name='<%="projectInfo.projectVersionList[" + i + "].selected" %>' value="false">
					</logic:iterate>
				</fieldset>
			</td>
		</tr>	
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="right"><bean:message bundle="case" key="test_objective"/>：</td>
			<td colspan="6" align="left"><bean:write name="caseForm" property="caseInfo.tcTestObjective"/></td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="right"><bean:message bundle="case" key="test_content"/>：</td>
			<td colspan="6" align="left"><bean:write name="caseForm" property="caseInfo.tcTestContent"/></td>
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
				<logic:equal name="cvr" property="currentReference" value="true">
				<tr>	
					<td align="center"><bean:write name="cvr" property="projectVersion.pvVersion"/></td>				
					<td align="center">						
						<bean:write name="cvr" property="testResult.trName"/>
					</td>				
					<td align="center">						
						<bean:write name="cvr" property="importantLevel.ilName"/>
					</td>				
					<td align="center">
						<bean:write name="cvr" property="bugType.btName"/>
					</td>
					<td align="left"><bean:write name="cvr" property="cvrCaseOutput"/></td>
				</tr>	
				</logic:equal>		
			</logic:iterate>		
		</table>
	</fieldset>
			
<bean:define id="c" name="caseForm" property="caseInfo"></bean:define>
						
<table width="100%">
	<tr>
		<td width="50%" align="center">				
			<fieldset style="width:98%;float:left;">
				<legend><bean:message bundle="case" key="test_step"/></legend>
				<div class="putinscroll1">				
					<bean:write name="c" property="tcTestStep" filter="false"/>
				</div>	
			</fieldset>	
		</td>
		<td align="center" rowspan="2">
			<fieldset style="width:98%;float:left;">
				<legend><bean:message bundle="case" key="test_remark"/></legend>
				<textarea class="putinscroll3" id="remarkeditor" name="caseInfo.tcRemark"><%=((org.mds.test.bean.TestCase)c).getTcRemark()%></textarea>
			</fieldset>
		</td>
	</tr>
	<tr>
		<td align="center">				
			<fieldset style="width:98%;float:left;">
				<legend><bean:message bundle="case" key="intend_output"/></legend>
				<div class="putinscroll0">
				<bean:write name="c" property="tcIntendOutput" filter="false"/>
				</div>
			</fieldset>
		</td>
	</tr>
	
	<c:set var="${caseInfo}" value="${c}" scope="request" />
	<c:import url="/WEB-INF/pages/casemanage/recordList.jsp"></c:import>
</table>
			 
</html:form>


<script language="JavaScript">	
UE.getEditor('remarkeditor',{initialFrameWidth:480,initialFrameHeight:310,maximumWords:1000});

 function chgAction(obj,str){
	obj.value=str;
 }
 
function checkFormValidate()
{
	var type = document.getElementsByName("caseInfo.tcBugType")[0].value	
	if(type == "")
	{
		alert("异常类型  不能为空");
		return false;
	}
}

</script>


