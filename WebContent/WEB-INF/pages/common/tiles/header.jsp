<%@ include file="../../common/include.jsp"%>

<body>
<table width="100%" height="60" border="0" style="background-image:url('pages/style/default/mis/title.jpg');background-repeat:no-repeat;background-position:center center;background-size:100% 100%;">  
  <tr>	
    <td align="right" valign="top">
		<a href="logoutAction.do?method=logout" target="_top"><img src="pages/style/default/mis/logout.jpg"></a>
	</td>
  </tr>
  <tr>
	<td align="right" style="color:white;">
		<logic:present name="accountPerson" scope="session">
			<bean:write name="accountPerson" property="personName" scope="session"/><bean:message key="welcome"/>
		</logic:present>
	</td>
	</tr>
</table>
</body>

