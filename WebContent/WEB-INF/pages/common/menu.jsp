<%@page pageEncoding="GBK"%>
<%@ include file = "../common/include.jsp"%>

<html>
<head>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="StyleSheet" href="pages/style/dtree.css" type="text/css" />
</head>		

<BODY  bgcolor="#c0d2ec">
<table width="190" height="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td align="left" height="300" valign="top" class="tdborder01" nowrap> 
<div class="dtree">
<script type="text/javascript" src="pages/scripts/dtree.js"></script>
<script type='text/javascript'>
	tree = new dTree('tree');
	tree.config.folderLinks=false;
	tree.config.useCookies=false;
	<logic:iterate id="menu" name="menuTreeList" scope="session">
    tree.add('<bean:write name="menu" property="id"/>','<bean:write name="menu" property="pid"/>','<bean:write name="menu" property="menuName"/>','<bean:write name="menu" property="url"/>','<bean:write name="menu" property="title"/>','<bean:write name="menu" property="target"/>','<bean:write name="menu" property="icon"/>','<bean:write name="menu" property="iconopen"/>',"true");
	</logic:iterate>
    document.write(tree);
      
</script>
</div>
    </td>
  </tr>
</table>
</BODY>
</html>