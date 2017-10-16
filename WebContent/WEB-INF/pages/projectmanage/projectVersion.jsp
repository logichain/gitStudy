<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>



<table class="win"  width="100%">		
	<tr><td width="70%" align="left">
								
		</td>	
		<td align="center">
			<a href="javascript:openDialog('projectmanage.do?method=addProjectVersion&pid=<bean:write name="projectForm" property="projectInfo.PId"/>',760,360);void(0);"><img border="0" src="pages\style\default\mis\add.jpg" style="cursor: hand;"></a>
		</td>			
	</tr>
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
					<bean:message bundle="project" key="version_code" />：			
					<bean:write name="version" property="pvVersion"/>					
				</td>	
				<td align="center"></td>					
				<td align="right">								
					<a href="javascript:openDialog('projectmanage.do?method=editProjectVersion&id=<bean:write name="version" property="pvId"/>',760,360);"><img border="0" src="pages\images\icon\16x16\modify.gif"></a>
					<logic:notEqual name="version" property="pvInit" value="1">
						<a href='javascript:if(confirm("确认要删除这条信息吗?")){chgAction(document.all.id,"<bean:write name="version" property="pvId"/>");chgAction(document.all.method,"deleteProjectVersion");projectForm.submit();}'><img border="0" src="pages\images\icon\16x16\delete.gif"></a>
					</logic:notEqual>
				</td>
			</tr>
			<tr>
				<td>
				<fieldset style="width:90%;float:left;">
					<legend><bean:message bundle="project" key="develop"/></legend>
					<table width="100%">
						<tr>
							<td align="right" width="20%"><bean:message bundle="project" key="leader"/>：</td>
							<td align="left">
								<logic:notEmpty name="version" property="developLeader">
									<bean:write name="version" property="developLeader.personName"/>
								</logic:notEmpty>																			
							</td>
						</tr>
						<tr>
							<td align="right"><bean:message bundle="project" key="begin"/>：</td>
							<td align="left">
								<bean:write name="version" property="pvDevelopBegin"/>								
							</td>
						</tr>
						<tr>
							<td align="right"><bean:message bundle="project" key="end"/>：</td>
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
							<td align="right" width="20%"><bean:message bundle="project" key="leader"/>：</td>
							<td align="left">
								<bean:write name="version" property="testLeader.personName"/>								
							</td>
						</tr>
						<tr>
							<td align="right"><bean:message bundle="project" key="begin"/>：</td>
							<td align="left">
								<bean:write name="version" property="pvTestBegin"/>
							</td>
						</tr>
						<tr>
							<td align="right"><bean:message bundle="project" key="end"/>：</td>
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
					<bean:message bundle="project" key="remark" />：	
				</td>
				<td align="left">			
					<bean:write name="version" property="pvRemark"/>
				</td>			
			</tr>	
			
			<tr>
				<td align="right">
					<bean:message bundle="project" key="project_attachment" />：
				</td>
				<td align="left">
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
			</td></tr>
		</table>
	</fieldset>				
</logic:iterate>		

		



