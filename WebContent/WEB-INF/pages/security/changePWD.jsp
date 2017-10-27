<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>

<html:errors />	
<table class="win" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">
	<html:form action="accountManager.do?method=changeAccountPWD" onsubmit="return submitChange()">
	
	<tr><td width="15%">&nbsp;</td><td width="25%">&nbsp;</td><td>&nbsp;</td></tr>
		<tr>
			<td align="right">
				<bean:message bundle="security" key="oldpwd.input" />：
			</td>

			<td>
				<input name="oldPWD" type="password" size="15" maxlength="15">
			<td>
		</tr>
		<tr>
			<td align="right">
				<bean:message bundle="security" key="newpwd.input" />：
			</td>

			<td>
				<input name="newPWD" type="password" size="15" maxlength="15">
			<td>
		</tr>

		<tr>
			<td align="right">
				<bean:message bundle="security" key="newpwd.reinput" />：
			</td>

			<td>
				<input name="newPWD2" type="password" size="15" maxlength="15">
			<td>
		</tr>
		<tr><td>&nbsp;</td></tr>

		<tr>
			<td></td>
			<td align="center">
				<html:submit styleClass="savebutton">
					&nbsp;
				</html:submit>

				<html:reset styleClass="resetbutton">
					&nbsp;
				</html:reset>
			</td>
		</tr>
	</html:form>
</table>

<script type="text/javascript">
function submitChange(){
	if(document.all("oldPWD").value==""){
	    alert("请输入原始密码");
	    return false;
	    }
	 else if(document.all("newPWD").value==""){
	    alert("请输入新密码");
	    return false;
	    }
	 else if(document.all("newPWD").value!=document.all("newPWD2").value){
	   alert("两次输入新密码不匹配");  
	   return false;
	 }
       return true;
	 }
</script>
