	<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>

<script type="text/javascript" src="<c:url value='/resources/treetable/jquery.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/treetable/jquery.ui.js'/>"></script>
<link type="text/css" href="<c:url value='/resources/treetable/jquery.treeTable.css'/>" rel="stylesheet" />
<script type="text/javascript" src="<c:url value='/resources/treetable/jquery.treeTable.js'/>"></script>
 
<script type="text/javascript" src="<c:url value='/resources/treetable/treeTableDocumentEvent.js'/>"></script>
 
 <html:form action="projectmanage.do">
 <input type="hidden" name="method" value="confirmModuleFunction">
<input type="hidden" name="opType" value="<%=request.getParameter("opType")%>">	
<table class="treeSelectTable" id="dnd-example" width="100%">
  	<thead>
	    <tr>
			<th align="center" width="15%"></th>
	      	<th align="center" width="30%"><bean:message bundle="project" key="name" /></th>
	      	<th align="center"><bean:message bundle="project" key="remark" /></th>
	    	
	    </tr>
  	</thead>
  	<tbody>
  		<logic:equal name="opType" value="caseList">
   			<logic:iterate name="projectForm" property="searchInfo.projectModule.moduleFunctionList" id="mf" indexId="j">
	   			<tr id="<%="node-" + "-" + j%>">
			        <td align="center"><input type="radio" name="id" value="<bean:write name="mf" property="muId" />"><span class="parentNode"><font color="red"><%=j+1 +")" %></font></span></td>
					<td><bean:write name="mf" property="muName"/></td>
			        <td><bean:write name="mf" property="muRemark"/></td>
			    </tr>
	
				<logic:iterate name="mf" property="childFunctionList" id="cmf" indexId="k">
	    			<tr id="<%="node-" + "-" + j + "-" + k%>" class="<%="child-of-node-" + "-" + j%>">
				        <td align="right"><input type="radio" name="id" value="<bean:write name="cmf" property="muId" />"><span class="parentNode"><font color="blue"><%=k+1 +">" %></font></span></td>
						<td><bean:write name="cmf" property="muName"/></td>
				        <td><bean:write name="cmf" property="muRemark"/></td>
				    </tr>
				</logic:iterate>
			</logic:iterate> 
   		</logic:equal>
   		<logic:notEqual name="opType" value="caseList">
   			<logic:iterate name="projectForm" property="searchInfo.projectModule.moduleFunctionList" id="mf" indexId="j">
	   				<tr id="<%="node-" + "-" + j%>">
			        <td align="center"><input type="radio" name="id" value="<bean:write name="mf" property="muId" />"><span class="parentNode"><font color="red"><%=j+1 +")" %></font></span></td>
					<td><bean:write name="mf" property="muName"/></td>
			        <td><bean:write name="mf" property="muRemark"/></td>
			    </tr>
	
				<logic:iterate name="mf" property="childFunctionList" id="cmf" indexId="k">
	    			<tr id="<%="node-" + "-" + j + "-" + k%>" class="<%="child-of-node-" + "-" + j%>">
				        <td align="right"><input type="radio" name="id" value="<bean:write name="cmf" property="muId" />"><span class="parentNode"><font color="blue"><%=k+1 +">" %></font></span></td>
						<td><bean:write name="cmf" property="muName"/></td>
				        <td><bean:write name="cmf" property="muRemark"/></td>
				    </tr>
				</logic:iterate>
			</logic:iterate> 
   		</logic:notEqual>
   			   		
   
	</tbody>
</table>

<table width="100%">
	<tr>
		<td width="100%" ALIGN="RIGHT">
			<html:submit styleClass="confirmbutton" onclick="return validSelectedIdForEdit();">
				&nbsp;
			</html:submit>	
			<html:button property="" onclick="window.close();" styleClass="button">
				<bean:message key="button.cancel" />
			</html:button>	
		</td>
	</tr>
</table>

</html:form>

<script language="JavaScript">
<script language="JavaScript">

function validSelectedIdForEdit(){
    var flag = 0;
    var count = 0;
    var a = document.all("id");
    if(a==null){
       alert("没有功能点可以编辑");
       return false;
    }   
    if(a.length==null){
       if(!a.checked) {
         alert("请选择功能点!");
         return false;
       }    
    }else {
       for  (var  i=0;  i<a.length;  i++){  
	         if(a[i].checked==true){
	            flag=flag+1;
	         }
       }  
	     if(flag==0){
	         alert("请选择功能点!");
	         return false;
	     }    
	     if(flag>1){
	         alert("请选择一个功能点!");
	         return false;
	     }
    
    }
    
    return true;    
}

function chgAction(obj,str){
	obj.value=str;	
 }
</script>