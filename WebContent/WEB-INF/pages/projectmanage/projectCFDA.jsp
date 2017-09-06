	<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>

<script type="text/javascript" src="<c:url value='/resources/treetable/jquery.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/treetable/jquery.ui.js'/>"></script>
<link type="text/css" href="<c:url value='/resources/treetable/jquery.treeTable.css'/>" rel="stylesheet" />
<script type="text/javascript" src="<c:url value='/resources/treetable/jquery.treeTable.js'/>"></script>
 
<script type="text/javascript" src="<c:url value='/resources/treetable/treeTableDocumentEvent.js'/>"></script>
 
 <html:form action="projectmanage.do">
	<html:errors />
	<input type="hidden" name="method" value="searchAccount">
	<input type="hidden" name="id" value="">
<table class="win" WIDTH="100%">
	<tr><td width="80%" align="left">
			
		</td>			
		
		<td align="center">
			<a href="javascript:openDialog('projectmanage.do?method=addCFDAJobTask&pid=<bean:write name="projectForm" property="projectInfo.PId"/>',600,280);void(0);"><img border="0" src="pages\style\default\mis\add.jpg" style="cursor: hand;"></a>
		</td>
	</tr>
</table>
<table class="treeSelectTable" id="dnd-example" width="100%">
  	<thead>
	    <tr>
			<th align="center" width="6%"></th>
	      	<th align="center" width="15%"><bean:message bundle="project" key="job_name" /></th>
	      	<th align="center" width="10%"><bean:message bundle="project" key="begin" /></th>
	      	<th align="center" width="10%"><bean:message bundle="project" key="end" /></th>
	      	<th align="center" width="10%"><bean:message bundle="project" key="leader" /></th>
	      	<th align="center"><bean:message bundle="project" key="remark" /></th>
	    	<th align="center" width="6%"><bean:message bundle="project" key="task_status" /></th>
	    	<th align="center" width="6%"><bean:message key="button.add" /></th>
	    	<th align="center" width="6%"><bean:message key="button.edit" /></th>
	    	<th align="center" width="6%"><bean:message key="button.delete" /></th>
	    </tr>
  	</thead>
  	<tbody>
    	<logic:iterate name="projectForm" property="projectInfo.CFDAJobTaskList" id="task" indexId="i">    		
    		<tr id="<%="node-" + i%>">
		    	<td class="droplist"><span class="parentNode"><%=i+1 %></span></td>
		    	<td><bean:write name="task" property="jtName"/></td>
				<td><bean:write name="task" property="jtBegin"/></td>
				<td><bean:write name="task" property="jtEnd"/></td>
				<td><bean:write name="task" property="leader.personName"/></td>
				<td><bean:write name="task" property="jtDescription"/></td>
				<td><bean:write name="task" property="status.jtsName"/></td>		      	
		      	<td align="center"><a href="javascript:openDialog('projectmanage.do?method=addTaskAttachment&id=<bean:write name="task" property="jtId"/>',600,200);void(0);"><img border="0" src="pages\images\icon\16x16\new.gif"></a></td>
		      	<td align="center"><a href="javascript:openDialog('projectmanage.do?method=editJobTask&id=<bean:write name="task" property="jtId"/>',600,280);void(0);"><img border="0" src="pages\images\icon\16x16\modify.gif"></a></td>
		      	<td align="center">
		      		<a href='javascript:if(confirm("确认要删除这条信息吗?")) {chgAction(document.all.id,"<bean:write name="task" property="jtId"/>");chgAction(document.all.method,"deleteJobTask");projectForm.submit();}'><img border="0" src="pages\images\icon\16x16\delete.gif">	</a>
		      	</td>
		    </tr>
		    <bean:size id="listSize" name="task" property="taskAttachmentList"/>
		    <logic:greaterThan name="listSize" value="0">
			    <tr bgcolor="gray" id="<%="node-" + i + "-" + 0%>" class="<%="child-of-node-" + i%>">
			        <td><span class="parentNode"></span></td>
					<td><bean:message bundle="project" key="attachment_filecode" /></td>
			        <td colspan="2"><bean:message bundle="project" key="attachment_filename" /></td>
			        <td colspan="3"><bean:message bundle="project" key="attachment_description" /></td>
			      	<td align="center"></td>
			      	<td align="center"></td>
			      	<td align="center"></td>
			    </tr>
	    		<logic:iterate name="task" property="taskAttachmentList" id="ta" indexId="j">
	    			<tr bgcolor="lightgray" id="<%="node-" + i + "-" + j%>" class="<%="child-of-node-" + i%>">
				        <td><span class="parentNode"><%=j+1 +")" %></span></td>
						<td><bean:write name="ta" property="taCode"/></td>
				        <td colspan="2"><bean:write name="ta" property="taName"/></td>
				        <td colspan="3"><bean:write name="ta" property="taDescription"/></td>
				      	<td align="center">&nbsp;</td>
				      	<td align="center"><a href="javascript:openDialog('projectmanage.do?method=editTaskAttachment&id=<bean:write name="ta" property="taId"/>',600,200);void(0);"><img border="0" src="pages\images\icon\16x16\modify.gif"></a></td>
				      	<td align="center">
				      		<a href='javascript:if(confirm("确认要删除这条信息吗?")) {chgAction(document.all.id,"<bean:write name="ta" property="taId"/>");chgAction(document.all.method,"deleteTaskAttachment");projectForm.submit();}'><img border="0" src="pages\images\icon\16x16\delete.gif"></a>			      		
				      	</td>
				    </tr>
				</logic:iterate>    		
			</logic:greaterThan>
    	</logic:iterate>
	</tbody>
</table>

</html:form>

<script language="JavaScript">

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
