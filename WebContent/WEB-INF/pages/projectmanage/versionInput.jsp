<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>
<script type="text/javascript" src="<c:url value='/pages/scripts/calendar.js'/>" charset="gbk"></script>

<html:form action="projectmanage.do" onsubmit="return checkFormValidate();">

<input type="hidden" name="method" value="saveProjectVersion">	
<table class="win" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">
	<tr>
		<td width="15%">&nbsp;</td><td width="30%"></td><td width="15%"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right">
			<bean:message bundle="project" key="version_code" />：		
		</td>
		<td align="left">	
			<html:text property="versionInfo.pvVersion" size="18" maxlength="18"/>
		</td>						
	
		<td align="right"><bean:message bundle="project" key="leader"/>：</td>
		<td align="left">
			<html:text property="versionInfo.testLeader.personName" size="18" maxlength="100" readonly="true" style="background-color:LightGray;"/>		
			<html:button property="" onclick="openDialogD('projectmanage.do?method=searchAccount&opType=vtl',800,420);">...</html:button>			
		</td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td align="right"><bean:message bundle="project" key="begin"/>：</td>
		<td align="left"><html:text property="versionInfo.pvTestBegin" readonly="true" size="18" maxlength="32" onclick="SelectDate(this);"/></td>
	
		<td align="right"><bean:message bundle="project" key="end"/>：</td>
		<td align="left"><html:text property="versionInfo.pvTestEnd" readonly="true" size="18" maxlength="32" onclick="SelectDate(this);"/></td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td align="right">
			<bean:message bundle="project" key="remark" />：
		</td>
		<td align="left" colspan="3">			
			<html:text property="versionInfo.pvRemark" size="80" maxlength="200"/>
		</td>	
	</tr>
	<tr><td>&nbsp;</td></tr>

	<tr>
		<td align="right">
			<bean:message bundle="project" key="project_attachment" />：
		</td>
		<td align="left" colspan="2">
			<div style="width:100%;height:20px;background:white;">
			<logic:iterate id="am" name="projectForm" property="versionInfo.attachmentList" indexId="i">
			<logic:notEqual name="am" property="paFlag" value="-1">	
				<span title="<bean:write name="am" property="paLocalUrl"/>">
					<bean:write name="am" property="paName"/>
					<a href="projectmanage.do?method=deleteAttachment&opType=versionInput&index=<%=i %>"><img border="0" src="pages\images\icon\16x16\delete.gif"></a>；
				</span>					
			</logic:notEqual>
			</logic:iterate>
			</div>
		</td>
		<td align="left">
			<html:button property="" onclick="openDialog('projectmanage.do?method=addAttachment&opType=versionInput',600,200);"><bean:message bundle="project" key="button_addattachment"/></html:button>
		</td>
	</tr>	
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td colspan="3" align="right"></td>
		<td align="center">
			<html:submit styleClass="savebutton">&nbsp;</html:submit>			
			<html:button property="" onclick="window.close();" styleClass="cancelbutton">
				&nbsp;
			</html:button>
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
		