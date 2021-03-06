<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>
<html:form action="casemanage.do" onsubmit="return true;">
	<html:errors />
	
	<input type="hidden" name="method" value="displayTestCaseNext">
	<input type="hidden" name="id" value="<bean:write name="caseForm" property="caseInfo.tcId"/>">	
	
	<table CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">		
		<tr><td width="80%"></td>				
			<td align="center"><html:submit styleClass="previousbutton" onclick="chgAction(document.all.method,'displayTestCasePrevious');">&nbsp;</html:submit></td>
			<td align="center"><html:submit styleClass="nextbutton">&nbsp;</html:submit></td>			
		</tr>
		<tr>
			<td align="left" class="strong"><bean:message bundle="case" key="project"/>��<bean:write name="caseForm" property="projectInfo.PName"/></td>
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
			
			<td align="right"><bean:message bundle="case" key="module_function"/>��</td>
			<td align="left" colspan="3">			
				<bean:write name="caseForm" property="caseInfo.moduleFunction.entireName"/>			
			</td>			
		</tr>	
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="right"><bean:message bundle="case" key="case_code"/>��</td>
			<td align="left"><bean:write name="caseForm" property="caseInfo.tcCode"/></td>
			<td align="right"><bean:message bundle="case" key="case_type"/>��</td>
			<td align="left"><bean:write name="caseForm" property="caseInfo.caseType.ctName"/></td>
		</tr>	
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="right"><bean:message bundle="case" key="test_objective"/>��</td>
			<td align="left" colspan="4"><bean:write name="caseForm" property="caseInfo.tcTestObjective"/></td>			
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="right"><bean:message bundle="case" key="test_content"/>��</td>
			<td colspan="4" align="left"><bean:write name="caseForm" property="caseInfo.tcTestContent"/></td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="right">
				<bean:message bundle="project" key="project_attachment" />��
			</td>
			<td  colspan="4" align="left">
				<div style="width:98%;height:20px;background:white;">
				<logic:iterate id="am" name="caseForm" property="caseInfo.attachmentList" indexId="i">
				<logic:notEqual name="am" property="caFlag" value="-1">											
					<a href="casemanage.do?method=downloadAttachment&id=<bean:write name="am" property="caId"/>"><bean:write name="am" property="caName"/></a>��
				</logic:notEqual>
				</logic:iterate>
				</div>				
			</td>			
			
		</tr>
		<tr><td>&nbsp;</td></tr>
	</table>
						
							
	<table width="100%">
		<tr>
			<td width="50%" align="left">				
				<fieldset style="width:98%;float:left;">
					<legend><bean:message bundle="case" key="test_step"/></legend>
					<html:textarea readonly="true" cols="56" rows="10" name="caseForm" property="caseInfo.tcTestStep"></html:textarea>													
				</fieldset>	
			</td>
			<td align="left" rowspan="2" valign="top">
				<fieldset style="width:98%;float:left;">
					<legend><bean:message bundle="case" key="case_attachment_preview"/></legend>
					<logic:iterate id="am" name="caseForm" property="caseInfo.attachmentList">
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
					<html:textarea readonly="true" cols="56" rows="5" name="caseForm" property="caseInfo.tcIntendOutput"></html:textarea>							
				</fieldset>
			</td>
			
		</tr>
	</table>


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
				<tr>	
					<td align="center" rowspan="2"><bean:write name="cvr" property="projectVersion.pvVersion"/></td>				
					<td align="center">&nbsp;
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
					<td align="left">
						<html:textarea readonly="true" name="cvr" property="cvrCaseOutput" cols="60" rows="4"></html:textarea>
					</td>
				</tr>		
				<tr>
				
				<td align="left" colspan="4">&nbsp;					
					<logic:iterate id="am" name="cvr" property="attachmentList">
					<logic:notEqual name="am" property="caFlag" value="-1">
						<img src="<bean:write name="am" property="caUrl"/>" title="<bean:write name="am" property="caName"/>" width="200" height="200" border="1">		
					</logic:notEqual>				
					</logic:iterate>					
				</td>
				</tr>
			</logic:iterate>		
		</table>
	</fieldset>		
	
	<table width="100%">	
		<bean:define id="c" name="caseForm" property="caseInfo"></bean:define>
		<c:set var="caseInfo" value="${c}" scope="request" />
		<c:import url="/WEB-INF/pages/casemanage/recordList.jsp"></c:import>
	</table>
			
</html:form>	
			
<script language="JavaScript">

 function chgAction(obj,str){
	obj.value=str;	
 }
 
 </script>
	

