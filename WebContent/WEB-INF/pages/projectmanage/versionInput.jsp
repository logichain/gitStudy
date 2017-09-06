<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>
<script type="text/javascript" src="<c:url value='/pages/scripts/calendar.js'/>" charset="gbk"></script>

<html:form action="projectmanage.do" onsubmit="return checkFormValidate();">

<input type="hidden" name="method" value="saveProjectVersion">	
<table class="win" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">
	<tr>
		<td width="50%">&nbsp;</td>
		<td></td>
	</tr>
	<tr>
		<td align="left">
			<bean:message bundle="project" key="version_code" />：			
			<html:text property="versionInfo.pvVersion" size="45" maxlength="45"/>
		</td>						
		<td align="right">
			<html:submit styleClass="savebutton">&nbsp;</html:submit>
		</td>
	</tr>
	<tr>
		<td>
		<fieldset style="width:90%;float:left;">
			<legend><bean:message bundle="project" key="develop"/></legend>
			<table width="100%">
				<tr>
					<td align="right" width="25%"><bean:message bundle="project" key="leader"/>：</td>
					<td align="left">
						<html:text property="versionInfo.developLeader.personName" size="18" maxlength="100" readonly="true" style="background-color:LightGray;"/>		
						<html:button property="" onclick="openDialogD('projectmanage.do?method=searchAccount&opType=vdl',800,420);">...</html:button>											
					</td>
				</tr>
				<tr>
					<td align="right"><bean:message bundle="project" key="begin"/>：</td>
					<td align="left"><html:text property="versionInfo.pvDevelopBegin" readonly="true" size="18" maxlength="32" onclick="SelectDate(this);"/></td>
				</tr>
				<tr>
					<td align="right"><bean:message bundle="project" key="end"/>：</td>
					<td align="left"><html:text property="versionInfo.pvDevelopEnd" readonly="true" size="18" maxlength="32" onclick="SelectDate(this);"/></td>
				</tr>
			</table>
		</fieldset>
		</td>
		<td>
		<fieldset style="width:90%;float:left;">
			<legend><bean:message bundle="project" key="test"/></legend>
			<table width="100%">
				<tr>
					<td align="right" width="25%"><bean:message bundle="project" key="leader"/>：</td>
					<td align="left">
						<html:text property="versionInfo.testLeader.personName" size="18" maxlength="100" readonly="true" style="background-color:LightGray;"/>		
						<html:button property="" onclick="openDialogD('projectmanage.do?method=searchAccount&opType=vtl',800,420);">...</html:button>			
					</td>
				</tr>
				<tr>
					<td align="right"><bean:message bundle="project" key="begin"/>：</td>
					<td align="left"><html:text property="versionInfo.pvTestBegin" readonly="true" size="18" maxlength="32" onclick="SelectDate(this);"/></td>
				</tr>
				<tr>
					<td align="right"><bean:message bundle="project" key="end"/>：</td>
					<td align="left"><html:text property="versionInfo.pvTestEnd" readonly="true" size="18" maxlength="32" onclick="SelectDate(this);"/></td>
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
				<td width="8%" align="right">
					<bean:message bundle="project" key="remark" />：
				</td>
				<td align="left">			
					<html:text property="versionInfo.pvRemark" size="80" maxlength="200"/>
				</td>			
			</tr>
			<tr><td>&nbsp;</td></tr>
	
			<tr>
				<td align="right">
					<bean:message bundle="project" key="project_attachment" />：
				</td>
				<td align="left">
					<div style="width:96%;height:20px;background:white;">
					<logic:iterate id="am" name="projectForm" property="versionInfo.attachmentList" indexId="i">
						<span title="<bean:write name="am" property="paLocalUrl"/>">
							<bean:write name="am" property="paName"/>
							<a href="projectmanage.do?method=deleteAttachment&index=<%=i %>"><img border="0" src="pages\images\icon\16x16\delete.gif"></a>；
						</span>					
					</logic:iterate>
					</div>
				</td>			
				<td width="10%" align="left">
					<html:button property="" onclick="openDialog('projectmanage.do?method=addAttachment&opType=versionInput',600,200);"><bean:message bundle="project" key="button_addattachment"/></html:button>
				</td>
			</tr>	
		</table>
		</td>
	</tr>	
	
</table>

</html:form>
	
 <script type="text/javascript">

 function checkFormValidate()
{
	var pname = document.getElementsByName("versionInfo.pvVersion")[0].value	
	if(pname == "")
	{
		alert("版本号  不能为空");
		return false;
	} 
	
	var lp = document.getElementsByName("versionInfo.developLeader.personName")[0].value
	if(lp == "")
	{
		alert("开发组长  不能为空");
		return false;
	} 
	
	var lb = document.getElementsByName("versionInfo.pvDevelopBegin")[0].value
	if(lb == "")
	{
		alert("开发开始时间  不能为空");
		return false;
	}
	var le = document.getElementsByName("versionInfo.pvDevelopEnd")[0].value
	if(le == "")
	{
		alert("开发结束时间  不能为空");
		return false;
	}
	
	var tp = document.getElementsByName("versionInfo.testLeader.personName")[0].value
	if(tp == "")
	{
		alert("测试组长  不能为空");
		return false;
	} 
	var tb = document.getElementsByName("versionInfo.pvTestBegin")[0].value
	if(tb == "")
	{
		alert("测试开始时间  不能为空");
		return false;
	}
	var te = document.getElementsByName("versionInfo.pvTestEnd")[0].value
	if(te == "")
	{
		alert("测试结束时间  不能为空");
		return false;
	}
	 	 	
	return true;
}

 function chgAction(obj,str){
	obj.value=str;
 } 
 
 function chgFormOnsubmit(str){  	 	
	projectForm.onsubmit="function onsubmit(){" + str + "}";	
 }
 

function submitForm()
{
	chgFormOnsubmit('return true;');
	chgAction(document.all.method,'refreshVersionInput');
	projectForm.submit();
}

function openDialogD(loadpos,WWidth,WHeight)//Lock   Size 
{
	submitForm();

	var WLeft = Math.ceil((window.screen.width - WWidth) / 2);   
	var WTop = Math.ceil((window.screen.height - WHeight) / 2); 
	var features = 'width=' + WWidth + 'px,' +	'height=' + WHeight + 'px,' + 'left=' + WLeft + 'px,' + 'top=' + WTop + 'px'; 

	WinOP = window.open(loadpos,"_blank",features); 
	WinOP.focus();   
}


</script>	
		