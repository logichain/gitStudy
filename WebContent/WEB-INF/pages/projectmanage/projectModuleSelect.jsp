<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>

<script type="text/javascript" src="<c:url value='/resources/treetable/jquery.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/treetable/jquery.ui.js'/>"></script>
<link type="text/css" href="<c:url value='/resources/treetable/jquery.treeTable.css'/>" rel="stylesheet" />
<script type="text/javascript" src="<c:url value='/resources/treetable/jquery.treeTable.js'/>"></script>
 
<script type="text/javascript" src="<c:url value='/resources/treetable/treeTableDocumentEvent.js'/>"></script>
 
 <html:form action="projectmanage.do">
 <input type="hidden" name="method" value="confirmProjectModule">
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
    	<logic:iterate name="projectForm" property="projectInfo.moduleList" id="module" indexId="i">    		
    		<tr id="<%="node-" + i%>">
		    	<td align="left" class="droplist"><input type="radio" name="id" value="<bean:write name="module" property="pmId" />"><span class="parentNode"><%=i+1 %></span></td>
		    	<td><bean:write name="module" property="pmName"/></td>
				<td><bean:write name="module" property="pmRemark"/></td>
		    </tr>    		   		
    	</logic:iterate>
    	
    		
	</tbody>
</table>

<table width="100%">
	<tr>
		<td width="100%" ALIGN="RIGHT">
			<html:submit styleClass="confirmbutton" onclick="return validSelectedIdForEdit();">
				&nbsp;
			</html:submit>	
			<html:button property="" onclick="window.close();" styleClass="cancelbutton">
				&nbsp;
			</html:button>
		</td>
	</tr>
</table>

</html:form>
<script language="JavaScript">

function validSelectedIdForEdit(){
    var flag = 0;
    var count = 0;
    var a = document.all("id");
    if(a==null){
       alert("没有模块可以编辑");
       return false;
    }   
    if(a.length==null){
       if(!a.checked) {
         alert("请选择模块!");
         return false;
       }    
    }else {
       for  (var  i=0;  i<a.length;  i++){  
	         if(a[i].checked==true){
	            flag=flag+1;
	         }
       }  
	     if(flag==0){
	         alert("请选择模块!");
	         return false;
	     }    
	     if(flag>1){
	         alert("请选择一个模块!");
	         return false;
	     }
    
    }
    
    return true;    
}

function chgAction(obj,str){
	obj.value=str;	
 }
</script>