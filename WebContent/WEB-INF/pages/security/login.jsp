<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>

<body>
	
				
<table width="100%" height="100%" border="0">
	<tr>
		<td align="center" valign="middle">
			
			<table border="0" cellspacing="0" cellpadding="0">
				<html:form action="loginAction?method=login" target="_top" focus="name" onsubmit="return validateLoginForm(this);">		
					<input type="hidden" name="DEST_URL" value='<%=request.getAttribute("DEST_URL") %>'/> 			
					<tr>
						<td align="right" valign="middle"><bean:message bundle="security" key="user_name"/>£º</td>
						<td align="left">
							<html:text property="name" size="19" maxlength="18" onkeydown="if(event.keyCode==13) {event.keyCode=9}" />
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle"><bean:message bundle="security" key="password"/>£º	</td>
						<td align="left">
							<html:password property="password" size="20" maxlength="20" redisplay="false" onkeydown="if(event.keyCode==13) {event.keyCode=9}" />
						</td>
					</tr>
					<tr>
						<td align="right" valign="middle"><bean:message bundle="security" key="verify_code"/>£º</td>
						<td>
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td align="left" valign="middle">
										<input name="chkCode" size="10" maxlength="10" onkeydown="if(event.keyCode==13) {if(validateLoginForm(loginForm)) {loginForm.submit();}}">
									</td>
									<td align="right" valign="middle">
										<html:img page="/CheckCodeImage" style="border: 1 dotted black" width="52" height="20" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
					</html:form>
					<tr><td height="20"></td></tr>
					<tr>	
						<td align="right"><img src="pages\style\default\mis\exit.jpg" border="0" usemap="#Map1"></td>					
						<td align="right"><img src="pages\style\default\mis\loginbutton.jpg" border="0" usemap="#Map2"></td>				
					</tr>
					<tr><td colspan="2" align="left"><html:errors/></td></tr>
				</table>
			
		</td>
	</tr>
	

</table>			
	<map name="Map1">
		<area shape="rect" coords="0,0,80,30" href="javascript:window.close();">
	</map>
	<map name="Map2">
		<area shape="rect" coords="0,0,80,30" href="javascript:document.loginForm.submit();">
	</map>

</body>
<html:javascript formName="loginForm" dynamicJavascript="true" staticJavascript="false" />
<script type="text/javascript" src="<html:rewrite forward='staticjavascript'/>"></script>

