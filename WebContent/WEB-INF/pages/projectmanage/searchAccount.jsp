<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>
		
<html:form action="projectmanage.do">
	<table class="win" CELLPADDING="2" CELLSPACING="0" width="100%">					
		<tr align="right">
			<td>				
				<bean:message bundle="security" key="person.name" />
				<html:text property="paraPersonName"></html:text>
				<bean:message bundle="security" key="person.dept" />								
				<html:select property="department" style="width:120px">	
					<html:option value=""></html:option>
					<html:options collection="departmentList" property="DId" labelProperty="DName"/>
				</html:select>
				<html:submit styleClass="searchbutton">
					&nbsp;
				</html:submit>
				<html:submit styleClass="resetbutton" onclick="chgAction(document.all.method,'resetSearchAccount');">
					&nbsp;
				</html:submit>
			</td>
		</tr>
	</table>

	<input type="hidden" name="method" value="searchAccount">
	<input type="hidden" name="opType" value="<%=request.getParameter("opType")%>">
	<table class="sort-table" cellSpacing="1" cellPadding="1" width="100%" border="0">						
		<logic:present name="accounts" scope="request">
			<thead>
				<tr>
					<td width="8%" align="center">
					</td>
					
					<td width="10%" align="center">
						<bean:message bundle="security" key="person.code" />
					</td>
					<td width="20%" align="center">
						<bean:message bundle="security" key="person.name" />
					</td>
					
					<td width="10%" align="center">
						<bean:message bundle="security" key="person.dept" />
					</td>
					<td align="center">
						<bean:message bundle="security" key="person.email" />
					</td>					
				</tr>
			</thead>

			<logic:iterate name="accounts" id="account" indexId="i">
			
			  <tr>
				<% int a = i%2;request.setAttribute("a",a);%>
				<logic:equal name="a" value="0"><tr class="even"></logic:equal>
				<logic:equal name="a" value="1"><tr class="odd"></logic:equal>
					<td align="center">
						<input type="radio" name="id" value="<bean:write  name="account" property="id" />">
						<%=i+1 %></td>
					
					<td>
						<bean:write name="account" property="personCode" />
					</td>
					<td>
						<bean:write name="account" property="personName" />
					</td>
					
					<td>
						<bean:write name="account" property="department.DName" />
					</td>
				
					<td>
						<bean:write name="account" property="email" />
					</td>
					
				</tr>
			</logic:iterate>
	
		</logic:present>
		
		<tr>
			<td COLSPAN="5" ALIGN="RIGHT">
				<html:submit styleClass="confirmbutton" onclick="chgAction(document.all.method,'confirmAccount');return validSelectedIdForEdit();">
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

 function validSelectedIdForEdit(){
     var flag = 0;
     var count = 0;
     var a = document.all("id");
     if(a==null){
        alert("没有用户可以编辑");
        return false;
     }   
     if(a.length==null){
        if(!a.checked) {
          alert("请选择用户!");
          return false;
        }    
     }else {
        for  (var  i=0;  i<a.length;  i++){  
	         if(a[i].checked==true){
	            flag=flag+1;
	         }
        }  
	     if(flag==0){
	         alert("请选择用户!");
	         return false;
	     }    
	     if(flag>1){
	         alert("请选择一个用户!");
	         return false;
	     }
     
     }
     
     return true;    
 }
 
   
 function chgAction(obj,str){
	obj.value=str;
 }

</script>
