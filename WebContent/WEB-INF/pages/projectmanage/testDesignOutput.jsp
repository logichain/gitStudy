<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>

<table width="100%">
	<tr>
		<td align="left" class="strong">				
			<bean:message bundle="project" key="project_name" />：			
			<bean:write name="projectForm" property="projectInfo.PName" />
		</td>						
	</tr>
		
	<tr><td>&nbsp;</td></tr>
</table>

<logic:iterate name="projectForm" property="projectInfo.projectVersionList" id="version" indexId="i">

	<fieldset style="width:96%;float:left;">
	<legend><bean:message bundle="project" key="project_version"/> <%=i+1 %></legend>
		<table class="win" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">
			<tr>
				<td width="10%"></td>
				<td width="20%"></td><td width="10%"></td><td width="20%"></td><td></td>
			</tr>
			<tr>
				<td align="right"><bean:message bundle="project" key="version_code" />：</td>
				<td align="left">		
					<bean:write name="version" property="pvVersion"/>					
				</td>	
				<td align="right" width="20%"><bean:message bundle="project" key="leader"/>：</td>
				<td align="left">
					<bean:write name="version" property="testLeader.personName"/>								
				</td>
				<td align="center">
					<a href="casemanage.do?method=exportTestCaseXLSByVersion&type=design&pvId=<bean:write name="version" property="pvId"/>">导出xls式样书</a>
					&nbsp;&nbsp;&nbsp;
					<a href="casemanage.do?method=exportTestCaseDOCByVersion&type=design&pvId=<bean:write name="version" property="pvId"/>">导出doc式样书</a>								
				</td>
			</tr>
		
			<tr>
				<td align="right"><bean:message bundle="project" key="begin"/>：</td>
				<td align="left">
					<bean:write name="version" property="pvTestBegin"/>
				</td>
			
				<td align="right"><bean:message bundle="project" key="end"/>：</td>
				<td align="left">
					<bean:write name="version" property="pvTestEnd"/>
				</td>
			</tr>
					
				
			
			<tr>
				<td  align="right">
					<bean:message bundle="project" key="remark" />：	
				</td>
				<td align="left" colspan="4">			
					<bean:write name="version" property="pvRemark"/>
				</td>			
			</tr>	
			
			<tr>
				<td align="right">
					<bean:message bundle="project" key="project_attachment" />：
				</td>
				<td align="left" colspan="4">
					<div style="width:98%;height:20px;background:white;"> 
					<logic:iterate id="am" name="version" property="attachmentList" indexId="j">
						<a href="projectmanage.do?method=downloadAttachment&id=<bean:write name='am' property='paId'/>" title="<bean:write name="am" property="paLocalUrl"/>">
							<bean:write name="am" property="paName"/>；
						</a>												
					</logic:iterate>
					</div>
				</td>			
				
			</tr>	
			
		</table>
	</fieldset>			
</logic:iterate>		

		



