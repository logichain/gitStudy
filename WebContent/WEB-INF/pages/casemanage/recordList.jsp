<%@ include file = "../common/include.jsp"%>
<tr><td>&nbsp;</td></tr>
<tr>			
	<td colspan="6">			
		<fieldset style="width:98%;float:left;min-height:100px;">
			<legend><bean:message bundle="case" key="record"/></legend>					
			<table class="sort-table" cellSpacing="1" cellPadding="1" width="100%" border="0">			
				<thead>
					<tr>
						<td width="10%"></td>
						<td width="20%" align="center"><bean:message bundle="case" key="oper_user"/></td>
						<td width="30%" align="center"><bean:message bundle="case" key="oper_time"/></td>
						<td width="20%" align="center"><bean:message bundle="project" key="project_version"/></td>
						<td width="10%" align="center"><bean:message bundle="case" key="oper_status"/></td>
						<td align="center"><bean:message bundle="case" key="test_result"/></td>
					</tr>
				</thead>
			<logic:iterate id="tcr" name="caseInfo" property="testCorrectRecordList">
				<tr>
					<td><bean:write name="tcr" property="tcrId"/></td>
					<td><bean:write name="tcr" property="operUser.personName"/></td>
					<td><bean:write name="tcr" property="tcrOperTime"/></td>
					<td>
						<logic:notEmpty name="tcr" property="testVersion">
							<bean:write name="tcr" property="testVersion.pvVersion"/>
						</logic:notEmpty>
					</td>
					<td><bean:write name="tcr" property="status.csName"/></td>
					<td>
						<logic:notEmpty name="tcr" property="testResult">
							<bean:write name="tcr" property="testResult.trName"/>
						</logic:notEmpty>
					</td>
				</tr>		
			</logic:iterate>
			</table>				
		</fieldset>				
	</td>			
</tr>