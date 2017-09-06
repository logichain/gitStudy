<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>
<script type="text/javascript" src="<c:url value='/pages/scripts/calendar.js'/>" charset="gbk"></script>


<html:form action="projectmanage.do" onsubmit="return checkFormValidate();">
	<html:errors />
	
	<input type="hidden" name="method" value="saveJobTask">	
	<table class="win" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">
			<tr><td>&nbsp;</td></tr>	
			<tr>
				<td width="70%">
					
				</td>						
				<td align="center" width="12%">
					<html:submit styleClass="savebutton">
						&nbsp;
					</html:submit>						
				</td>				
			</tr>
		</table>	
	<table class="win" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">		
		<tr>
			<td width="20%"></td>
			<td width="30%"></td><td width="20%"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right" class="strong">
				<bean:message bundle="case" key="project"/>:
			</td>
			<td align="left" colspan="3">
				<bean:write name="projectForm" property="jobTaskInfo.project.PName"/>
			</td>			
		</tr>
		
		<tr>
			<td align="right"><bean:message bundle="project" key="begin"/>：</td>
			<td align="left"><html:text styleId="begin" property="jobTaskInfo.jtBegin" readonly="true" size="18" maxlength="32" onclick="SelectDate(this);"/></td>
		
			<td align="right"><bean:message bundle="project" key="end"/>：</td>
			<td align="left"><html:text styleId="end" property="jobTaskInfo.jtEnd" readonly="true" size="18" maxlength="32" onclick="SelectDate(this);"/></td>
		</tr>
		<tr>
			<td align="right" width="20%"><bean:message bundle="project" key="leader"/>：</td>
			<td align="left">
				<html:text styleId="leaderName" property="jobTaskInfo.leader.personName" size="18" maxlength="100" readonly="true" style="background-color:LightGray;"/>		
				<html:button property="" onclick="openDialogD('projectmanage.do?method=searchAccount&opType=jtl',800,420);">...</html:button>			
			</td>
		</tr>
		
		<tr>
			<td align="right">
				<bean:message bundle="project" key="job_name" />：	
			</td>
			<td align="left">		
				<html:text styleId="jtname" property="jobTaskInfo.jtName" size="30" maxlength="45"/>
			</td>						
		</tr>
		
		<tr>
			<td align="right">
				<bean:message bundle="project" key="remark" />：
			</td>
			<td align="left" colspan="3">			
				<html:text property="jobTaskInfo.jtDescription" size="75" maxlength="200"/>
			</td>
			
		</tr>	
		<tr>
			<td align="right">
				<bean:message bundle="project" key="task_status" />：
			</td>
			<td align="left">						
				<html:select styleId="status" property="jobTaskInfo.jtStatus" style="width:120px">	
					<html:option value=""></html:option>
					<html:options collection="statusList" property="jtsId" labelProperty="jtsName"/>
				</html:select>
			</td>	
		</tr>
		<tr><td>&nbsp;</td></tr>					
		
	</table>
			
</html:form>
	
<script language="JavaScript">

 function checkFormValidate()
{
	var pname = document.getElementById("jtname").value;
	if(pname == "")
	{
		alert("任务名称  不能为空");
		return false;
	}
	var leaderName = document.getElementById("leaderName").value;
	if(leaderName == "")
	{
		alert("组长  不能为空");
		return false;
	} 
	
	var begin = document.getElementById("begin").value;
	if(begin == "")
	{
		alert("开始时间  不能为空");
		return false;
	}
	
	var end = document.getElementById("end").value;
	if(end == "")
	{
		alert("结束时间  不能为空");
		return false;
	}
	
	var status = document.getElementById("status").value;
	if(status == "")
	{
		alert("状态  不能为空");
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
	chgAction(document.all.method,'refreshJobTaskInput');
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

