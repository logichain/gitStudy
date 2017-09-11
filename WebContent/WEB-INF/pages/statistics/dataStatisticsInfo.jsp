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



	<html:form action="teststatistics.do">
		<html:errors />
		<input type="hidden" name="method" value="dataStatistics">
		<table CELLPADDING="2" CELLSPACING="0" width="100%" border="0">		
			<tr><td width="10%" align="right" class="strong"><bean:message bundle="case" key="project"/>:</td>
				<td width="15%" align="left" class="strong"><bean:write name="statisticsForm" property="projectInfo.PName"/></td>
				<td width="10%"></td><td width="15%"></td><td width="10%"></td><td width="15%"></td>
				<td width="10%"></td><td></td>
			</tr>			
			<tr>
				<td align="right"><bean:message bundle="case" key="module"/>:</td>
				<td align="left">
					<html:select property="caseInfo.moduleId" style="width:120px">	
						<html:option value=""></html:option>
						<html:optionsCollection name="statisticsForm" property="projectInfo.moduleList" value="pmId" label="pmName"/>
					</html:select>
				</td>
							
				<td align="right"><bean:message bundle="project" key="version"/>:</td>
				<td align="left">
					<html:select property="cvrSearchInfo.cvrProjectVersion" style="width:120px">	
						<html:option value=""></html:option>									
						<html:optionsCollection name="statisticsForm" property="projectInfo.projectVersionList" value="pvId" label="pvVersion"/>									
					</html:select>
				</td>			
			</tr>		
			
			<tr>
				<td colspan="8" align="right">
					<html:submit styleClass="searchbutton">
						&nbsp;
					</html:submit>
					<html:submit styleClass="resetbutton" onclick="chgAction(document.all.method,'resetDataStatistics');">
						&nbsp;
					</html:submit>					
				</td>
			</tr>
		</table>
								
	</html:form>	
<div class="radio-toolbar" align="left">   		
    <input type="radio" id="radio1" name="radios" onchange="displayChange(this.value);" value="versionRefer" checked>
    <label for="radio1"><bean:message bundle="project" key="project_version" /></label>	
    <input type="radio" id="radio2" name="radios" onchange="displayChange(this.value);" value="moduleRefer">
    <label for="radio2"><bean:message bundle="case" key="module" /></label>	
    <input type="radio" id="radio3" name="radios" onchange="displayChange(this.value);" value="caseStatusRefer">
    <label for="radio3"><bean:message bundle="case" key="case_status" /></label> 
    <input type="radio" id="radio4" name="radios" onchange="displayChange(this.value);" value="testResultRefer">
    <label for="radio4"><bean:message bundle="case" key="test_result" /></label>	
    <input type="radio" id="radio5" name="radios" onchange="displayChange(this.value);" value="importLevelRefer">
    <label for="radio5"><bean:message bundle="case" key="important_level" /></label>	
    <input type="radio" id="radio6" name="radios" onchange="displayChange(this.value);" value="bugTypeRefer">
    <label for="radio6"><bean:message bundle="case" key="bug_type" /></label> 
    <input type="radio" id="radio7" name="radios" onchange="displayChange(this.value);" value="functionRefer">
    <label for="radio7"><bean:message bundle="case" key="function" /></label> 
</div>

<fieldset style="width:90%;float:left;">
	<div id="versionRefer">
		<table width="100%">
			<tr>
				<td width="60%">
					<%=request.getAttribute("versionSvgStr") %>
				</td>
				<td>
					<table class="sort-table" width="100%">
					<thead>	
						<tr>
							<td><bean:message bundle="case" key="class_name"/></td>
							<td><bean:message bundle="case" key="data_count"/></td>
							<td><bean:message bundle="case" key="data_percent"/></td>
						</tr>
					</thead>
					<tbody>
						<logic:iterate id="data" name="versionDataList">
						<tr>
							<td><bean:write name="data" property="title"/></td>
							<td><bean:write name="data" property="count"/></td>
							<td><bean:write name="data" property="percentStr"/></td>
						</tr>
						</logic:iterate>
					</tbody>
					</table>
				</td>
			</tr>
		</table>		
	</div>
	<div id="moduleRefer" style="display:none;">
		<table width="100%">
			<tr>
				<td width="60%">
					<%=request.getAttribute("moduleSvgStr") %>
				</td>
				<td>
					<table class="sort-table" width="100%">
					<thead>	
						<tr>
							<td><bean:message bundle="case" key="class_name"/></td>
							<td><bean:message bundle="case" key="data_count"/></td>
							<td><bean:message bundle="case" key="data_percent"/></td>
						</tr>
					</thead>
					<tbody>
						<logic:iterate id="data" name="moduleDataList">
						<tr>
							<td><bean:write name="data" property="title"/></td>
							<td><bean:write name="data" property="count"/></td>
							<td><bean:write name="data" property="percentStr"/></td>
						</tr>
						</logic:iterate>
					</tbody>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div id="caseStatusRefer" style="display:none;">
		<table width="100%">
			<tr>
				<td width="60%">
					<%=request.getAttribute("statusSvgStr") %>
				</td>
				<td>
					<table class="sort-table" width="100%">
					<thead>	
						<tr>
							<td><bean:message bundle="case" key="class_name"/></td>
							<td><bean:message bundle="case" key="data_count"/></td>
							<td><bean:message bundle="case" key="data_percent"/></td>
						</tr>
					</thead>
					<tbody>
						<logic:iterate id="data" name="statusDataList">
						<tr>
							<td><bean:write name="data" property="title"/></td>
							<td><bean:write name="data" property="count"/></td>
							<td><bean:write name="data" property="percentStr"/></td>
						</tr>
						</logic:iterate>
					</tbody>
					</table>
				</td>
			</tr>
		</table>
	</div>		
	<div id="testResultRefer" style="display:none;">
		<table width="100%">
			<tr>
				<td width="60%">
					<%=request.getAttribute("resultSvgStr") %>
				</td>
				<td>
					<table class="sort-table" width="100%">
					<thead>	
						<tr>
							<td><bean:message bundle="case" key="class_name"/></td>
							<td><bean:message bundle="case" key="data_count"/></td>
							<td><bean:message bundle="case" key="data_percent"/></td>
						</tr>
					</thead>
					<tbody>
						<logic:iterate id="data" name="resultDataList">
						<tr>
							<td><bean:write name="data" property="title"/></td>
							<td><bean:write name="data" property="count"/></td>
							<td><bean:write name="data" property="percentStr"/></td>
						</tr>
						</logic:iterate>
					</tbody>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div id="importLevelRefer" style="display:none;">
		<table width="100%">
			<tr>
				<td width="60%">
					<%=request.getAttribute("importLevelSvgStr") %>
				</td>
				<td>
					<table class="sort-table" width="100%">
					<thead>	
						<tr>
							<td><bean:message bundle="case" key="class_name"/></td>
							<td><bean:message bundle="case" key="data_count"/></td>
							<td><bean:message bundle="case" key="data_percent"/></td>
						</tr>
					</thead>
					<tbody>
						<logic:iterate id="data" name="importLevelDataList">
						<tr>
							<td><bean:write name="data" property="title"/></td>
							<td><bean:write name="data" property="count"/></td>
							<td><bean:write name="data" property="percentStr"/></td>
						</tr>
						</logic:iterate>
					</tbody>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div id="bugTypeRefer" style="display:none;">
		<table width="100%">
			<tr>
				<td width="60%">
					<%=request.getAttribute("bugTypeSvgStr") %>
				</td>
				<td>
					<table class="sort-table" width="100%">
					<thead>	
						<tr>
							<td><bean:message bundle="case" key="class_name"/></td>
							<td><bean:message bundle="case" key="data_count"/></td>
							<td><bean:message bundle="case" key="data_percent"/></td>
						</tr>
					</thead>
					<tbody>
						<logic:iterate id="data" name="bugTypeDataList">
						<tr>
							<td><bean:write name="data" property="title"/></td>
							<td><bean:write name="data" property="count"/></td>
							<td><bean:write name="data" property="percentStr"/></td>
						</tr>
						</logic:iterate>
					</tbody>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div id="functionRefer" style="display:none;">
		<table width="100%">
			<tr>
				<td width="60%">
					<%=request.getAttribute("functionSvgStr") %>
				</td>
				<td>
					<table class="sort-table" width="100%">
					<thead>	
						<tr>
							<td><bean:message bundle="case" key="class_name"/></td>
							<td><bean:message bundle="case" key="data_count"/></td>
							<td><bean:message bundle="case" key="data_percent"/></td>
						</tr>
					</thead>
					<tbody>
						<logic:iterate id="data" name="functionDataList">
						<tr>
							<td><bean:write name="data" property="title"/></td>
							<td><bean:write name="data" property="count"/></td>
							<td><bean:write name="data" property="percentStr"/></td>
						</tr>
						</logic:iterate>
					</tbody>
					</table>
				</td>
			</tr>
		</table>
	</div>
</fieldset>
<script language="JavaScript">

 function chgAction(obj,str){
	obj.value=str;	
 }

 function displayChange(divId)
 {
 	var divList = ["versionRefer","moduleRefer","caseStatusRefer","testResultRefer","importLevelRefer","bugTypeRefer","functionRefer"];
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
