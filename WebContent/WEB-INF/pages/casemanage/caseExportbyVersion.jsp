<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>
			
<table CELLPADDING="2" CELLSPACING="0" width="100%" border="0">		
	<tr><td width="10%" align="right" class="strong"><bean:message bundle="case" key="project"/>:</td>
		<td width="15%" align="left" class="strong"><bean:write name="projectInfo" property="PName"/></td>
		<td width="10%" align="right"><bean:message bundle="project" key="project_version"/>:</td>
		<td width="15%" align="left"><bean:write name="versionInfo" property="pvVersion"/></td><td width="10%"></td><td width="15%"></td>
		<td width="10%">
			<bean:message bundle="case" key="count"/>:<bean:write name="caseCount"/>
		</td>
		<td>
			<html:button styleClass="exportbutton" property="" onclick="excelExport();">
				&nbsp;
			</html:button>
		</td>
	</tr>					
</table>
			
<div id="exceltable"> 

<logic:present name="caseList">
	<logic:iterate name="caseList" id="c" indexId="i">
		<table CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="1">				
			<tr>
				<td width="15%" align="center"><bean:message bundle="case" key="module"/></td>
				<td width="15%" align="left">
					<bean:write name="c" property="moduleFunction.projectModuleName"/>
				</td>
				<td width="15%" align="center"><bean:message bundle="case" key="module_function"/></td>
				<td width="15%" align="left">			
					<bean:write name="c" property="moduleFunction.entireModuleFunctionName"/>			
				</td>								
				<td width="15%" align="center"><bean:message bundle="case" key="case_code"/></td><td align="left"><bean:write name="c" property="tcCode"/></td>
			</tr>	
			<tr>
				<td align="center"><bean:message bundle="case" key="test_objective"/></td><td colspan="5" align="left"><bean:write name="c" property="tcTestObjective"/></td>
			</tr>
			<tr>
				<td align="center"><bean:message bundle="case" key="test_content"/></td><td colspan="5" align="left"><bean:write name="c" property="tcTestContent"/></td>
			</tr>
			
			<tr>			
				<td align="center"><bean:message bundle="case" key="test_step"/></td>
				<td align="left" colspan="5">
					<fieldset style="width:98%;float: left;">
						<bean:write name="c" property="tcTestStep" filter="false"/>
					</fieldset>
				</td>
			</tr>
		
			<tr>
				<td align="center"><bean:message bundle="case" key="intend_output"/></td>
				<td align="left" colspan="5">
					<fieldset style="width:98%;float: left;">
						<bean:write name="c" property="tcIntendOutput" filter="false"/>
					</fieldset>
				</td>				
			</tr>
			<tr>
				<td align="center"><bean:message bundle="case" key="test_output"/></td>
				<td align="left" colspan="5">
					<logic:equal name="type" value="result">
						<logic:iterate id="cvr" name="c" property="caseVersionReferenceList">
							<logic:equal name="cvr" property="cvrProjectVersion" value='<%=request.getAttribute("version").toString() %>'>
								<bean:write name="cvr" property="cvrCaseOutput"/>								
							</logic:equal>						
						</logic:iterate>
					
					</logic:equal>
				</td>				
			</tr>
			<tr>			
				<td align="center"><bean:message bundle="case" key="test_result"/></td>
				<td align="left" colspan="5">
					<logic:equal name="type" value="result">
						<logic:iterate id="cvr" name="c" property="caseVersionReferenceList">
							<logic:equal name="cvr" property="cvrProjectVersion" value='<%=request.getAttribute("version").toString() %>'>
								<logic:notEmpty name="cvr" property="testResult"><bean:write name="cvr" property="testResult.trName"/></logic:notEmpty>
																
							</logic:equal>						
						</logic:iterate>
					
					</logic:equal>
				</td>				
			</tr>					
		</table>
		<table><tr><td>&nbsp;</td></tr></table>
	</logic:iterate>
</logic:present>
</div>

<iframe name="printframe" style="height:0px;width:1000px;"></iframe>

<script language="JavaScript">
 
 function excelExport(){ 
	var tempStr = document.getElementById('exceltable').innerHTML;
   	var doc = document.frames["printframe"].document;
   	doc.write(tempStr);
  	
   	doc.close();
   	
   	doc.execCommand('saveas',true,'exportData.doc');
 }
	
 
</script>
