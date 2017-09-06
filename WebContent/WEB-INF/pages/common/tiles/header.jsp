<%@ include file="../../common/include.jsp"%>

<body>
<table width="100%" height="28" border="0" background="pages/style/default/images/top2bg.jpg">  
  <tr>
	<td align="left" style="color:white;">
		<logic:present name="accountPerson" scope="session">
			<bean:write name="accountPerson" property="personName" scope="session"/><bean:message key="welcome"/>
		</logic:present>
	</td>
    <td align="right" valign="top">
		<a href="logoutAction.do?method=logout" target="_top"><img src="pages/style/default/mis/logout.jpg"></a>
	</td>
  </tr>
</table>
</body>

