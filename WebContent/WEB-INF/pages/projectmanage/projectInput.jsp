<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>

<bean:define id="infoTitle">
	<bean:message bundle="project" key="project_info" />
</bean:define>

<html:form action="projectmanage.do" onsubmit="return validateProjectForm(this);">
	<html:errors />
	
	<input type="hidden" name="method" value="saveProject">	
	<input type="hidden" name="id">	
	<table class="win" CELLPADDING="0" CELLSPACING="0" WIDTH="90%" border="0">
		<tr><td>&nbsp;</td></tr>	
		<tr><td width="70%">&nbsp;</td>						
			<td align="center" width="12%">
				<html:submit styleClass="savebutton">
					&nbsp;
				</html:submit>						
			</td>	
					
		</tr>
	</table>	
	<fieldset style="width:90%;float:left;border-color:blue;">
	<table class="win" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">
		<tr>
			<td width="50%"></td>
			<td></td>
		</tr>
		<tr>
			<td align="left">
				<bean:message bundle="project" key="project_name" />£∫			
				<html:text property="projectInfo.PName" size="45" maxlength="45"/>
			</td>			
			<td align="left">
				≥ı º<bean:message bundle="project" key="version_code" />£∫			
				<html:text property="projectInfo.initProjectVersion.pvVersion" size="30" maxlength="45"/>
			</td>			
		</tr>

		<tr>
			<td>
			<fieldset style="width:90%;float:left;">
				<legend><bean:message bundle="project" key="develop"/></legend>
				<table width="100%">
					<tr>
						<td align="right" width="20%"><bean:message bundle="project" key="leader"/>£∫</td>
						<td align="left">
							<html:text property="projectInfo.initProjectVersion.developLeader.personName" size="18" maxlength="100" readonly="true" style="background-color:LightGray;"/>		
							<html:button property="" onclick="openDialog('projectmanage.do?method=searchAccount&opType=dl',700,420);">...</html:button>			
						</td>
					</tr>
					<tr>
						<td align="right"><bean:message bundle="project" key="begin"/>£∫</td>
						<td align="left"><html:text property="projectInfo.initProjectVersion.pvDevelopBegin" readonly="true" size="18" maxlength="32" onclick="SelectDate(this);"/></td>
					</tr>
					<tr>
						<td align="right"><bean:message bundle="project" key="end"/>£∫</td>
						<td align="left"><html:text property="projectInfo.initProjectVersion.pvDevelopEnd" readonly="true" size="18" maxlength="32" onclick="SelectDate(this);"/></td>
					</tr>
				</table>
			</fieldset>
			</td>
			<td>
			<fieldset style="width:90%;float:left;">
				<legend><bean:message bundle="project" key="test"/></legend>
				<table width="100%">
					<tr>
						<td align="right" width="20%"><bean:message bundle="project" key="leader"/>£∫</td>
						<td align="left">
							<html:text property="projectInfo.initProjectVersion.testLeader.personName" size="18" maxlength="100" readonly="true" style="background-color:LightGray;"/>		
							<html:button property="" onclick="openDialog('projectmanage.do?method=searchAccount&opType=tl',700,420);">...</html:button>			
						</td>
					</tr>
					<tr>
						<td align="right"><bean:message bundle="project" key="begin"/>£∫</td>
						<td align="left"><html:text property="projectInfo.initProjectVersion.pvTestBegin" readonly="true" size="18" maxlength="32" onclick="SelectDate(this);"/></td>
					</tr>
					<tr>
						<td align="right"><bean:message bundle="project" key="end"/>£∫</td>
						<td align="left"><html:text property="projectInfo.initProjectVersion.pvTestEnd" readonly="true" size="18" maxlength="32" onclick="SelectDate(this);"/></td>
					</tr>
				</table>
			</fieldset>
			</td>			
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td colspan="2">
			<table width="100%">
				<tr>
					<td width="5%" align="right">
						<bean:message bundle="project" key="remark" />£∫	
					</td>
					<td align="left">		
						<html:text property="projectInfo.PRemark" size="100" maxlength="200"/>
					</td>			
				</tr>		
			
				<tr><td>&nbsp;</td></tr>
		
		
				<tr>
					<td align="right">
						<bean:message bundle="project" key="project_attachment" />£∫
					</td>
					<td align="left">
						<div style="width:98%;height:20px;background:white;">
						<logic:iterate id="am" name="projectForm" property="projectInfo.initProjectVersion.attachmentList" indexId="i">
							<span title="<bean:write name="am" property="paLocalUrl"/>">
								<bean:write name="am" property="paName"/>
								<a href="projectmanage.do?method=deleteAttachment&index=<%=i %>"><img border="0" src="pages\images\icon\16x16\delete.gif"></a>£ª
							</span>					
						</logic:iterate>
						</div>
					</td>			
					<td width="20%" align="left">
						<html:button property="" onclick="openDialog('projectmanage.do?method=addAttachment&opType=projectInput',600,240);"><bean:message bundle="project" key="button_addattachment"/></html:button>
					</td>
				</tr>	
			</table>
			</td>
		</tr>					
				
	</table>
	</fieldset>	

</html:form>
		
<html:javascript formName="projectForm" dynamicJavascript="true" staticJavascript="false" />
<script type="text/javascript" src="<html:rewrite forward='staticjavascript'/>"></script>

<script language="JavaScript">

 function chgAction(obj,str){
	obj.value=str;
 } 
 
  function chgFormOnsubmit(str){  	 	
	projectForm.onsubmit="function onsubmit(){" + str + "}";	
 }
 
	
function submitForm()
{
	chgFormOnsubmit('return true;');
	chgAction(document.all.method,'refreshProjectInput');
	projectForm.submit();
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

