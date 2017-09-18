<%@ page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>
<table width="100%" border="0">
<tr><td width="30%"></td><td width="10%"></td><td></td></tr>
<tr>
	<td align="right">
		<fieldset style="float:right;">
		<legend align="top"><c:out value="${param.leftLabel}"/></legend>
		<select name="<c:out value="${param.leftId}"/>" multiple="multiple" onDblClick="moveSelectedOptions(this,document.getElementById('<c:out value="${param.rightId}"/>'),true)" id="<c:out value="${param.leftId}"/>" size="15" style="width:200px;">
			<c:if test="${leftList != null}">
				<c:forEach var="list" items="${leftList}" varStatus="status">
					<option value="<c:out value="${list.value}"/>">
						<c:out value="${list.label}" escapeXml="false" />
					</option>
				</c:forEach>
			</c:if>
		</select>
		</fieldset>
	</td>
	<td class="moveOptions" align="center">
		<button name="moveRight" id="moveRight<c:out value="${param.listCount}"/>" type="button" onclick="moveSelectedOptions(document.getElementById('<c:out value="${param.leftId}"/>'),document.getElementById('<c:out value="${param.rightId}"/>'),true)">
			&gt;
		</button>
		<br /><br />
		<button name="moveAllRight" id="moveAllRight<c:out value="${param.listCount}"/>" type="button" onclick="moveAllOptions(document.getElementById('<c:out value="${param.leftId}"/>'),document.getElementById('<c:out value="${param.rightId}"/>'),true)">
			&gt;&gt;
		</button>
		<br /><br />
		<button name="moveLeft" id="moveLeft<c:out value="${param.listCount}"/>" type="button" onclick="moveSelectedOptions(document.getElementById('<c:out value="${param.rightId}"/>'),document.getElementById('<c:out value="${param.leftId}"/>'),true)">
			&lt;
		</button>
		<br /><br />
		<button name="moveAllLeft" id="moveAllLeft<c:out value="${param.listCount}"/>" type="button" onclick="moveAllOptions(document.getElementById('<c:out value="${param.rightId}"/>'),document.getElementById('<c:out value="${param.leftId}"/>'),true)">
			&lt;&lt;
		</button>
	</td>
	<td align="left">
		<fieldset style="float:left;">
		<legend><c:out value="${param.rightLabel}"/></legend>
		<select name="<c:out value="${param.rightId}"/>" multiple="multiple" id="<c:out value="${param.rightId}"/>" size="15" style="width:200px;">
			<c:if test="${rightList != null}">
				<c:forEach var="list" items="${rightList}" varStatus="status">
					<option value="<c:out value="${list.value}"/>">
						<c:out value="${list.label}" escapeXml="false" />
					</option>
				</c:forEach>
			</c:if>
		</select>
		</fieldset>
	</td>
</tr>
</table>