<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>
<html:form action="casemanage.do" onsubmit="return true;">
	<html:errors />
	
	<input type="hidden" name="method" value="displayTestCaseNext">
	<input type="hidden" name="id" value="<bean:write name="caseInfo" property="tcId"/>">	
	
	<table CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">		
		<tr><td width="80%"></td>				
			<td align="center"><html:submit styleClass="previousbutton" onclick="chgAction(document.all.method,'displayTestCasePrevious');">&nbsp;</html:submit></td>
			<td align="center"><html:submit styleClass="nextbutton">&nbsp;</html:submit></td>			
		</tr>
		<tr>
			<td align="left" class="strong"><bean:message bundle="case" key="project"/>£º<bean:write name="caseForm" property="projectInfo.PName"/></td>
		</tr>
	</table>

	<table CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">	
		<tr>
			<td width="10%">&nbsp;</td><td width="40%"></td>
			<td width="10%"></td><td width="10%"></td>
			<td rowspan="3" align="left">
				<fieldset style="width:80%;float:right;">
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
			
			<td align="right"><bean:message bundle="case" key="module_function"/>£º</td>
			<td align="left">			
				<bean:write name="caseInfo" property="moduleFunction.entireName"/>			
			</td>			
			<td align="right"><bean:message bundle="case" key="case_code"/>£º</td>
			<td align="left"><bean:write name="caseInfo" property="tcCode"/></td>
			
		</tr>	
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="right"><bean:message bundle="case" key="test_objective"/>£º</td>
			<td colspan="4" align="left"><bean:write name="caseInfo" property="tcTestObjective"/></td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="right"><bean:message bundle="case" key="test_content"/>£º</td>
			<td colspan="4" align="left"><bean:write name="caseInfo" property="tcTestContent"/></td>
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
			<logic:iterate name="caseInfo" property="caseVersionReferenceList" id="cvr" indexId="i">	
				<tr>	
					<td align="center"><bean:write name="cvr" property="projectVersion.pvVersion"/></td>				
					<td align="center">
						<logic:notEmpty name="cvr" property="testResult">
							<bean:write name="cvr" property="testResult.trName"/>
						</logic:notEmpty>						
					</td>										
					<td align="center">
						<logic:notEmpty name="cvr" property="importantLevel">
							<bean:write name="cvr" property="importantLevel.ilName"/>
						</logic:notEmpty>
					</td>
					<td align="center">
						<logic:notEmpty name="cvr" property="bugType">
							<bean:write name="cvr" property="bugType.btName"/>
						</logic:notEmpty>
					</td>
					<td align="left"><bean:write name="cvr" property="cvrCaseOutput"/></td>
				</tr>		
			</logic:iterate>		
		</table>
	</fieldset>
							
	<table width="100%">
		<tr>
			<td width="50%" rowspan="2" align="left">				
				<fieldset style="width:98%;float:left;">
					<legend><bean:message bundle="case" key="test_step"/></legend>
					<html:textarea readonly="true" cols="56" rows="30" name="caseInfo" property="tcTestStep"></html:textarea>													
				</fieldset>	
			</td>
			<td align="left">
				<fieldset style="width:98%;float:left;">
					<legend><bean:message bundle="case" key="test_remark"/></legend>
					<html:textarea readonly="true" cols="56" rows="16" name="caseInfo" property="tcRemark"></html:textarea>					
				</fieldset>
			</td>
		</tr>
		<tr>
			<td align="left">				
				<fieldset style="width:98%;float:left;">
					<legend><bean:message bundle="case" key="intend_output"/></legend>
					<html:textarea readonly="true" cols="56" rows="10" name="caseInfo" property="tcIntendOutput"></html:textarea>							
				</fieldset>
			</td>
			
		</tr>
	</table>		

	<table width="100%">		
		<c:import url="/WEB-INF/pages/casemanage/recordList.jsp"></c:import>
	</table>
			
</html:form>	
			
<script language="JavaScript">

 function chgAction(obj,str){
	obj.value=str;	
 }
 
 </script>
	

