<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>

<table width="100%">
	<tr>
		<td align="left" class="strong">				
			<bean:message bundle="project" key="project_name" />£º			
			<bean:write name="projectForm" property="projectInfo.PName" />
		</td>						
	</tr>

	<tr><td>&nbsp;</td></tr>
</table>

<logic:iterate name="projectForm" property="projectInfo.projectVersionList" id="version" indexId="i">

	<fieldset style="width:96%;float:left;border-color:blue;">
	<legend><bean:message bundle="project" key="project_version"/> <%=i+1 %></legend>
		<table class="win" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">
			<tr>
				<td width="50%"></td>
				<td></td><td></td>
			</tr>
			<tr>
				<td align="left">
					<bean:message bundle="project" key="version_code" />£º			
					<bean:write name="version" property="pvVersion"/>					
				</td>	
				<td align="center">
					<a href="javascript:openDialog('projectmanage.do?method=resetSearchTestCaseforRenference&pvId=<bean:write name="version" property="pvId"/>',960,600);">¹ØÁªÓÃÀý</a>								
				</td>					
				<td align="right">								
				</td>
			</tr>
			<tr>
				<td>
				<fieldset style="width:90%;float:left;">
					<legend><bean:message bundle="project" key="develop"/></legend>
					<table width="100%">
						<tr>
							<td align="right" width="20%"><bean:message bundle="project" key="leader"/>£º</td>
							<td align="left">
								<logic:notEmpty name="version" property="developLeader">
									<bean:write name="version" property="developLeader.personName"/>
								</logic:notEmpty>																			
							</td>
						</tr>
						<tr>
							<td align="right"><bean:message bundle="project" key="begin"/>£º</td>
							<td align="left">
								<bean:write name="version" property="pvDevelopBegin"/>								
							</td>
						</tr>
						<tr>
							<td align="right"><bean:message bundle="project" key="end"/>£º</td>
							<td align="left">
								<bean:write name="version" property="pvDevelopEnd"/>
							</td>
						</tr>
					</table>
				</fieldset>
				</td>
				<td colspan="2">
				<fieldset style="width:90%;float:left;">
					<legend><bean:message bundle="project" key="test"/></legend>
					<table width="100%">
						<tr>
							<td align="right" width="20%"><bean:message bundle="project" key="leader"/>£º</td>
							<td align="left">
								<bean:write name="version" property="testLeader.personName"/>								
							</td>
						</tr>
						<tr>
							<td align="right"><bean:message bundle="project" key="begin"/>£º</td>
							<td align="left">
								<bean:write name="version" property="pvTestBegin"/>
							</td>
						</tr>
						<tr>
							<td align="right"><bean:message bundle="project" key="end"/>£º</td>
							<td align="left">
								<bean:write name="version" property="pvTestEnd"/>
							</td>
						</tr>
					</table>
				</fieldset>
				</td>			
			</tr>
			<tr><td colspan="3">
			<table width="100%">
			<tr>
				<td width="6%" align="right">
					<bean:message bundle="project" key="remark" />£º	
				</td>
				<td align="left">			
					<bean:write name="version" property="pvRemark"/>
				</td>			
			</tr>	
			
			<tr>
				<td align="right">
					<bean:message bundle="project" key="project_attachment" />£º
				</td>
				<td align="left">
					<div style="width:98%;height:20px;background:white;"> 
					<logic:iterate id="am" name="version" property="attachmentList" indexId="j">
						<a href="projectmanage.do?method=downloadAttachment&id=<bean:write name='am' property='paId'/>" title="<bean:write name="am" property="paLocalUrl"/>">
							<bean:write name="am" property="paName"/>£»
						</a>												
					</logic:iterate>
					</div>
				</td>			
				
			</tr>	
			</table>
			</td></tr>
		</table>
	</fieldset>			
</logic:iterate>		

		
<script language="JavaScript">

 function openDialog(loadpos,WWidth,WHeight)//Lock   Size 
{
	var WLeft = Math.ceil((window.screen.width - WWidth) / 2);   
	var WTop = Math.ceil((window.screen.height - WHeight) / 2); 
	var features = 'width=' + WWidth + 'px,' +	'height=' + WHeight + 'px,' + 'left=' + WLeft + 'px,' + 'top=' + WTop + 'px'; 
		
	WinOP = window.open(loadpos,"_blank",features); 
	WinOP.focus();	   
}
</script>


