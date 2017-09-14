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

<html:form action="projectmanage.do" onsubmit="return validateProjectForm(this);">
<html:errors />
<input type="hidden" name="method" value="searchAccount">
<input type="hidden" name="id" value="">
	
<table width="100%">
	<tr>
		<td align="left" class="strong">				
			<bean:message bundle="project" key="project_name" />£º			
			<bean:write name="projectForm" property="projectInfo.PName" />
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
	<c:import url="/WEB-INF/pages/projectmanage/projectVersion.jsp"></c:import>	
</div>
<div id="moduleInfo" style="display:none;">	
	<c:import url="/WEB-INF/pages/projectmanage/projectModule.jsp"></c:import>	
</div>
<div id="memberInfo" style="display:none;">	
	<c:import url="/WEB-INF/pages/projectmanage/projectMember.jsp"></c:import>	
</div>		
</fieldset>
</html:form>

<html:javascript formName="projectForm" dynamicJavascript="true" staticJavascript="false" />
<script type="text/javascript" src="<html:rewrite forward='staticjavascript'/>"></script>
	
<script language="JavaScript">
var pageId = "<%=request.getAttribute("tabpageId")%>";

if(pageId != 'null')
{
	displayChange(pageId);
	for(var i=1;i<=3;i++)
	{
		var radio = document.getElementById("radio"+i);
		if(radio.value == pageId)
		{
			radio.checked = true;
		}
	}
	
}

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

function chgAction(obj,str){
	obj.value=str;	
 }

 function openDialog(loadpos,WWidth,WHeight)//Lock   Size 
{
	var WLeft = Math.ceil((window.screen.width - WWidth) / 2);   
	var WTop = Math.ceil((window.screen.height - WHeight) / 2); 
	var features = 'width=' + WWidth + 'px,' +	'height=' + WHeight + 'px,' + 'left=' + WLeft + 'px,' + 'top=' + WTop + 'px'; 
		
	WinOP = window.open(loadpos,"_blank",features); 
	WinOP.focus();	   
}
</script>
