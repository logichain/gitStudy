<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>

<style>
<!--
.radio-toolbar input[type="radio"] {
    display:none;     
}

.radio-toolbar label {
	border-width:1px;
	border-top-style:solid;
	border-right-style:solid;
	border-left-style:solid;
    display:inline-block;
    background-color:#c0d2ec;
    padding:4px 11px;
    font-family:Arial;
    font-size:12px;
}

.radio-toolbar input[type="radio"]:checked + label { 
    background-color:blue;
}
-->
</style>

<table width="100%">
	<tr>
		<td align="left" class="strong">				
			<bean:message bundle="project" key="project_name" />£º			
			<bean:write name="projectForm" property="projectInfo.PName" />
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
						<bean:write name="projectForm" property="projectInfo.developLeader.personName"/>										
					</td>
				</tr>
				<tr>
					<td align="right"><bean:message bundle="project" key="begin"/>£º</td>
					<td align="left">
						<bean:write name="projectForm" property="projectInfo.PDevelopBegin"/>
					</td>
				</tr>
				<tr>
					<td align="right"><bean:message bundle="project" key="end"/>£º</td>
					<td align="left">
						<bean:write name="projectForm" property="projectInfo.PDevelopEnd" />
					</td>
				</tr>
			</table>
		</fieldset>
		</td>
		<td>
		<fieldset style="width:100%;float:left;">
			<legend><bean:message bundle="project" key="test"/></legend>
			<table width="100%">
				<tr>
					<td align="right" width="20%"><bean:message bundle="project" key="leader"/>£º</td>
					<td align="left">
						<bean:write name="projectForm" property="projectInfo.testLeader.personName" />										
					</td>
				</tr>
				<tr>
					<td align="right"><bean:message bundle="project" key="begin"/>£º</td>
					<td align="left">
						<bean:write name="projectForm" property="projectInfo.PTestBegin" />
					</td>
				</tr>
				<tr>
					<td align="right"><bean:message bundle="project" key="end"/>£º</td>
					<td align="left">
						<bean:write name="projectForm" property="projectInfo.PTestEnd" />
					</td>
				</tr>
			</table>
		</fieldset>
		</td>			
	</tr>
	<tr>
		<td colspan="2" align="left">
			<bean:message bundle="project" key="remark" />£º							
			<bean:write name="projectForm" property="projectInfo.PRemark"/>
		</td>			
	</tr>
	
	<tr><td>&nbsp;</td></tr>
</table>	
	
<div class="radio-toolbar" align="left">   		
    <input type="radio" id="radio1" name="radios" onchange="displayChange(this.value);" value="versionInfo" checked>
    <label for="radio1"><bean:message bundle="project" key="project_versionInfo" /></label>	
    <input type="radio" id="radio2" name="radios" onchange="displayChange(this.value);" value="moduleInfo">
    <label for="radio2"><bean:message bundle="project" key="project_moduleInfo" /></label>	
    <input type="radio" id="radio3" name="radios" onchange="displayChange(this.value);" value="memberInfo">
    <label for="radio3"><bean:message bundle="project" key="project_memberInfo" /></label> 
</div>
<fieldset style="width:90%;float:left;">	
<div id="versionInfo">	
	<jsp:include page="/WEB-INF/pages/projectmanage/projectVersion.jsp"></jsp:include>
</div>
<div id="moduleInfo" style="display:none;">	
	<jsp:include page="/WEB-INF/pages/projectmanage/projectModule.jsp"></jsp:include>
</div>
<div id="memberInfo" style="display:none;">	
	<jsp:include page="/WEB-INF/pages/projectmanage/projectMember.jsp"></jsp:include>
</div>		
</fieldset>

	
<script language="JavaScript">
function displayChange(divId)
{
	var divList = ["versionInfo","moduleInfo","memberInfo"];
	for(var i=0;i<divList.length;i++)
	{
		if(divList[i] == divId)
		{	
			document.getElementById(divList[i]).style.display = "block";			
		}
		else
		{
			document.getElementById(divList[i]).style.display = "none";
		}
	}	
}
</script>
