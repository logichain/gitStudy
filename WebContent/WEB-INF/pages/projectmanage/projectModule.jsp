	<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>

<script type="text/javascript" src="<c:url value='/resources/treetable/jquery.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/treetable/jquery.ui.js'/>"></script>
<link type="text/css" href="<c:url value='/resources/treetable/jquery.treeTable.css'/>" rel="stylesheet" />
<script type="text/javascript" src="<c:url value='/resources/treetable/jquery.treeTable.js'/>"></script>
 
<script type="text/javascript" src="<c:url value='/resources/treetable/treeTableDocumentEvent.js'/>"></script>
 

<table class="win" WIDTH="100%">
	<tr><td width="80%" align="left">
			
		</td>			
		
		<td align="center">
			<a href="javascript:openDialog('projectmanage.do?method=addProjectModule&pid=<bean:write name="projectForm" property="projectInfo.PId"/>',420,200);void(0);"><img border="0" src="pages\style\default\mis\add.jpg" style="cursor: hand;"></a>
		</td>
	</tr>
</table>
<table class="treeSelectTable" id="dnd-example" width="100%">
  	<thead>
	    <tr>
			<th align="center" width="15%"></th>
	      	<th align="center" width="30%"><bean:message bundle="project" key="name" /></th>
	      	<th align="center"><bean:message bundle="project" key="remark" /></th>
	    	
	    	<th align="center" width="6%"><bean:message key="button.add" /></th>
	    	<th align="center" width="6%"><bean:message key="button.edit" /></th>
	    	<th align="center" width="6%"><bean:message key="button.delete" /></th>
	    </tr>
  	</thead>
  	<tbody>
    	<logic:iterate name="projectForm" property="projectInfo.moduleList" id="module" indexId="i">    		
    		<tr id="<%="node-" + i%>">
		    	<td class="droplist"><span class="parentNode"><%=i+1 %></span></td>
		    	<td><bean:write name="module" property="pmName"/></td>
				<td><bean:write name="module" property="pmRemark"/></td>		      	
		      	
		      	<td align="center"><a href="javascript:openDialog('projectmanage.do?method=addModuleFunction&id=<bean:write name="module" property="pmId"/>',420,200);void(0);"><img border="0" src="pages\images\icon\16x16\new.gif"></a></td>
		      	<td align="center"><a href="javascript:openDialog('projectmanage.do?method=editProjectModule&id=<bean:write name="module" property="pmId"/>',420,200);void(0);"><img border="0" src="pages\images\icon\16x16\modify.gif"></a></td>
		      	<td align="center">
		      		<a href='javascript:if(confirm("确认要删除这条信息吗?")) {chgAction(document.all.id,"<bean:write name="module" property="pmId"/>");chgAction(document.all.method,"deleteProjectModule");projectForm.submit();}'><img border="0" src="pages\images\icon\16x16\delete.gif">	</a>
		      	</td>
		    </tr>
    		<logic:iterate name="module" property="moduleFunctionList" id="mf" indexId="j">
    			<tr id="<%="node-" + i + "-" + j%>" class="<%="child-of-node-" + i%>">
			        <td><span class="parentNode"><font color="red"><%=j+1 +")" %></font></span></td>
					<td><bean:write name="mf" property="muName"/></td>
			        <td><bean:write name="mf" property="muRemark"/></td>
			      	
			      	<td align="center"><a href="javascript:openDialog('projectmanage.do?method=addChildModuleFunction&id=<bean:write name="mf" property="muId"/>',420,200);void(0);"><img border="0" src="pages\images\icon\16x16\new.gif"></a></td>
			      	<td align="center"><a href="javascript:openDialog('projectmanage.do?method=editModuleFunction&id=<bean:write name="mf" property="muId"/>',420,200);void(0);"><img border="0" src="pages\images\icon\16x16\modify.gif"></a></td>
			      	<td align="center">
			      		<a href='javascript:if(confirm("确认要删除这条信息吗?")) {chgAction(document.all.id,"<bean:write name="mf" property="muId"/>");chgAction(document.all.method,"deleteModuleFunction");projectForm.submit();}'><img border="0" src="pages\images\icon\16x16\delete.gif"></a>			      		
			      	</td>
			    </tr>

				<logic:iterate name="mf" property="childFunctionList" id="cmf" indexId="k">
	    			<tr id="<%="node-" + i + "-" + j + "-" + k%>" class="<%="child-of-node-" + i + "-" + j%>">
				        <td><span class="parentNode"><font color="blue"><%=k+1 +")" %></font></span></td>
						<td><bean:write name="cmf" property="muName"/></td>
				        <td><bean:write name="cmf" property="muRemark"/></td>
				      	<td align="center">
<%-- 				      		<a href="javascript:openDialog('projectmanage.do?method=addChildModuleFunction&id=<bean:write name="cmf" property="muId"/>',420,200);void(0);"><img border="0" src="pages\images\icon\16x16\new.gif"></a> --%>
				      	</td>
				      	<td align="center"><a href="javascript:openDialog('projectmanage.do?method=editChildModuleFunction&id=<bean:write name="cmf" property="muId"/>',420,200);void(0);"><img border="0" src="pages\images\icon\16x16\modify.gif"></a></td>
				      	<td align="center">
				      		<a href='javascript:if(confirm("确认要删除这条信息吗?")) {chgAction(document.all.id,"<bean:write name="cmf" property="muId"/>");chgAction(document.all.method,"deleteModuleFunction");projectForm.submit();}'><img border="0" src="pages\images\icon\16x16\delete.gif"></a>			      		
				      	</td>
				    </tr>
<%-- 					<logic:iterate name="cmf" property="childFunctionList" id="ccmf" indexId="l"> --%>
<%-- 		    			<tr id="<%="node-" + i + "-" + j + "-" + k + "-" + l%>" class="<%="child-of-node-" + i + "-" + j + "-" + k%>"> --%>
<%-- 					        <td><span class="parentNode"><font color="blue"><%=l+1 +")" %></font></span></td> --%>
<%-- 							<td><bean:write name="ccmf" property="muName"/></td> --%>
<%-- 					        <td><bean:write name="ccmf" property="muRemark"/></td> --%>
<%-- 					      	<td align="center"><a href="javascript:openDialog('projectmanage.do?method=addChildModuleFunction&id=<bean:write name="ccmf" property="muId"/>',420,200);void(0);"><img border="0" src="pages\images\icon\16x16\new.gif"></a></td> --%>
<%-- 					      	<td align="center"><a href="javascript:openDialog('projectmanage.do?method=editChildModuleFunction&id=<bean:write name="ccmf" property="muId"/>',420,200);void(0);"><img border="0" src="pages\images\icon\16x16\modify.gif"></a></td> --%>
<!-- 					      	<td align="center"> -->
<%-- 					      		<a href='javascript:if(confirm("确认要删除这条信息吗?")) {chgAction(document.all.id,"<bean:write name="ccmf" property="muId"/>");chgAction(document.all.method,"deleteModuleFunction");projectForm.submit();}'><img border="0" src="pages\images\icon\16x16\delete.gif"></a>			      		 --%>
<!-- 					      	</td> -->
<!-- 					    </tr> -->
<%-- 						<logic:iterate name="ccmf" property="childFunctionList" id="cccmf" indexId="m"> --%>
<%-- 			    			<tr id="<%="node-" + i + "-" + j + "-" + k + "-" + l + "-" + m%>" class="<%="child-of-node-" + i + "-" + j + "-" + k + "-" + l%>"> --%>
<%-- 						        <td><span class="parentNode"><font color="Magenta"><%=l+1 +")" %></font></span></td> --%>
<%-- 								<td><bean:write name="cccmf" property="muName"/></td> --%>
<%-- 						        <td><bean:write name="cccmf" property="muRemark"/></td> --%>
<!-- 						      	<td align="center"></td> -->
<%-- 						      	<td align="center"><a href="javascript:openDialog('projectmanage.do?method=editChildModuleFunction&id=<bean:write name="cccmf" property="muId"/>',420,200);void(0);"><img border="0" src="pages\images\icon\16x16\modify.gif"></a></td> --%>
<!-- 						      	<td align="center"> -->
<%-- 						      		<a href='javascript:if(confirm("确认要删除这条信息吗?")) {chgAction(document.all.id,"<bean:write name="cccmf" property="muId"/>");chgAction(document.all.method,"deleteModuleFunction");projectForm.submit();}'><img border="0" src="pages\images\icon\16x16\delete.gif"></a>			      		 --%>
<!-- 						      	</td> -->
<!-- 						    </tr> -->
<%-- 						</logic:iterate> --%>
<%-- 					</logic:iterate> --%>
				</logic:iterate>
			</logic:iterate>    		
    	</logic:iterate>
	</tbody>
</table>

<script language="JavaScript">

function chgAction(obj,str){
	obj.value=str;	
 }
</script>