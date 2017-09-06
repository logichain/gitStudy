<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>

<html:form action="accountManager.do" focus="account.name" onsubmit="return validateAccountForm(this);">
	<html:errors />
	
	<html:hidden property="account.id" />
	<input type="hidden" name="method" value="addAccount"/>
	<table class="win" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="right" width="15%">
				<bean:message bundle="security" key="account.name" />£º
			</td>

			<td align="left" width="25%">
				<html:text property="account.accountName" size="18" maxlength="32" />
			</td>
		
			<td align="right" width="15%">
				<bean:message bundle="security" key="person.code" />£º
			</td>

			<td align="left">
				<html:text property="account.personCode" size="18" maxlength="32" />
			</td>
		</tr>

		<tr>
			<td align="right">
				<bean:message bundle="security" key="account.password" />£º
			</td>

			<td align="left">
				<html:password property="account.password" size="19" maxlength="32" redisplay="true" onchange="passwordChanged(this)" style="width:120"/>
			</td>
			<td align="right">
				<bean:message bundle="security" key="person.name" />£º
			</td>

			<td align="left">
				<html:text property="account.personName" size="18" maxlength="32" />
			</td>
		</tr>

		<tr>
			<td align="right">
				<bean:message bundle="security" key="account.repassword" />£º
			</td>

			<td align="left">
				<html:password property="account.repassword" size="19" maxlength="32" redisplay="true" style="width:120"/>
			</td>

			<td align="right">
				<bean:message bundle="security" key="person.sex" />£º
			</td>

			<td align="left">
				<html:radio property="account.sex" value="1" />
				<bean:message bundle="security" key="person.male" />
				<html:radio property="account.sex" value="2" />
				<bean:message bundle="security" key="person.female" />
			</td>
		</tr>

		
		<tr>
			<td align="right">
				<bean:message bundle="security" key="person.birthday" />£º
			</td>

			<td align="left">
				<html:text property="account.birthdayStr" readonly="true" size="18" maxlength="32" onclick="SelectDate(this)"/>
			</td>
			<td align="right">
				<bean:message bundle="security" key="person.entrydate" />£º
			</td>

			<td align="left">
				<html:text property="account.entryDateStr" readonly="true" size="18" maxlength="32" onclick="SelectDate(this)"/>
			</td>
			
		</tr>
		
		<tr>
			<td align="right">
				<bean:message bundle="security" key="person.email" />£º
			</td>

			<td align="left" colspan="3">
				<html:text property="account.email" size="50" maxlength="100" />
			</td>
		</tr>
	
		<tr>
			<td align="right">
				<bean:message bundle="security" key="person.phone" />£º
			</td>

			<td align="left">
				<html:text property="account.phone" size="18" maxlength="32" />
			</td>			
		</tr>
		<tr>
			<td align="right">
				<bean:message bundle="security" key="person.dept" />£º
			</td>

			<td align="left">
				<html:select property="account.dept" style="width:120px">	
					<html:option value=""></html:option>
					<html:options collection="departmentList" property="DId" labelProperty="DName"/>
				</html:select>				
			</td>
		</tr>
		<tr>
			<td align="right">
				<bean:message bundle="security" key="person.address" />£º
			</td>

			<td align="left" colspan="3">
				<html:text property="account.address" size="86" maxlength="100" />
			</td>
		</tr>
		<tr>
			<td colspan="3"></td>
			<td align="center">
				<html:submit styleClass="savebutton">
					&nbsp;
				</html:submit>

				<html:reset styleClass="resetbutton">
					&nbsp;
				</html:reset>
			</td>
		</tr>
	</table>
	</html:form>
	

<html:javascript formName="accountForm" dynamicJavascript="true" staticJavascript="false" />
<script type="text/javascript" src="<html:rewrite forward='staticjavascript'/>"></script>

<script type="text/javascript">

 function passwordChanged(passwordField) {
     var origPassword = "<c:out value="${accountForm.map.account.password}"/>";
     if (passwordField.value != origPassword) {
         createFormElement("input", "hidden",
                           "encryptPass", "encryptPass",
                           "true", passwordField.form);
     }
 }
</script>

