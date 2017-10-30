package org.mds.test.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.struts.upload.FormFile;
import org.king.framework.dao.MyQuery;
import org.king.framework.service.impl.BaseService;
import org.king.security.domain.UsrAccount;
import org.king.util.FileUtil;
import org.mds.common.CommonService;
import org.mds.project.bean.ModuleFunction;
import org.mds.project.bean.ModuleFunctionDAO;
import org.mds.project.bean.Project;
import org.mds.project.bean.ProjectVersion;
import org.mds.project.bean.TeamMember;
import org.mds.test.bean.BugType;
import org.mds.test.bean.BugTypeDAO;
import org.mds.test.bean.CaseAttachment;
import org.mds.test.bean.CaseAttachmentDAO;
import org.mds.test.bean.CaseStatus;
import org.mds.test.bean.CaseStatusDAO;
import org.mds.test.bean.CaseType;
import org.mds.test.bean.CaseTypeDAO;
import org.mds.test.bean.CaseVersionReference;
import org.mds.test.bean.CaseVersionReferenceDAO;
import org.mds.test.bean.CvrAttachment;
import org.mds.test.bean.CvrAttachmentDAO;
import org.mds.test.bean.ImportantLevel;
import org.mds.test.bean.ImportantLevelDAO;
import org.mds.test.bean.TestCase;
import org.mds.test.bean.TestCaseDAO;
import org.mds.test.bean.TestCorrectRecord;
import org.mds.test.bean.TestCorrectRecordDAO;
import org.mds.test.bean.TestResult;
import org.mds.test.bean.TestResultDAO;
import org.mds.test.service.TestCaseService;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class TestCaseServiceImpl extends BaseService implements TestCaseService {
	private TestCaseDAO testCaseDAO;
	
	private ImportantLevelDAO importantLevelDAO;
	private TestResultDAO testResultDAO; 
	private CaseStatusDAO caseStatusDAO; 
	
	private TestCorrectRecordDAO testCorrectRecordDAO;
	private BugTypeDAO bugTypeDAO;
	private CaseVersionReferenceDAO caseVersionReferenceDAO; 
	
	private ModuleFunctionDAO moduleFunctionDAO;
	private CaseAttachmentDAO caseAttachmentDAO;
	private CaseTypeDAO caseTypeDAO;
	private CvrAttachmentDAO cvrAttachmentDAO;
	
	@Override
	public void saveCaseVersionReference(String[] caseIdList, Integer versionId,Integer userId) {
		// TODO Auto-generated method stub
		
		for(String caseId:caseIdList)
		{
			CaseVersionReference cvr = new CaseVersionReference();
			cvr.setCvrFlag(CommonService.NORMAL_FLAG);
			cvr.setCvrTestCase(Integer.parseInt(caseId));
			cvr.setCvrProjectVersion(versionId);
			cvr.setCvrCaseStatus(CaseStatus.WAIT_TEST_STATUS);
			
			caseVersionReferenceDAO.save(cvr);
			
			//关联用例记录
			TestCorrectRecord tcr = new TestCorrectRecord();
			tcr.setTcrTestCase(Integer.parseInt(caseId));
			tcr.setTcrCaseStatus(CaseStatus.WAIT_TEST_STATUS);
			tcr.setTcrTestVersion(versionId);
			tcr.setCurrentOperRecord(true);
			tcr.setTcrOperUser(userId);
			tcr.setTcrOperTime(new Date());
			
			testCorrectRecordDAO.save(tcr);
		}		
	}
	
	public Integer saveImportTestCaseInfo(FormFile formFile,String filePath,UsrAccount user,Project project) 
	{		
		int rtn = 0;
		String fileName = FileUtil.saveUploadFile(formFile,filePath);
		FileInputStream is = null;
		try {
			is = new FileInputStream(filePath + fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		if(fileName.endsWith(".doc"))
		{
			rtn = this.importDOCFile(is, user, project);
		}
		else if(fileName.endsWith(".docx"))
		{
			rtn = this.importDOCXFile(is, user, project);
		}
		else if(fileName.endsWith(".xls"))
		{
			rtn = this.importXLSFile(is, user, project);
		}	
		else if(fileName.endsWith(".xlsx"))
		{
			
		}
				
		return rtn;
	}
	
	private Integer importDOCFile(FileInputStream is,UsrAccount user,Project project)
	{
		Integer rtn = 0;
		try{
			Date operDate = new Date();
			HWPFDocument hwpfDocument = new HWPFDocument(is);
			Range range = hwpfDocument.getRange();// 得到文档的读取范围
			TableIterator it = new TableIterator(range);
			
			// 迭代文档中的表格
			while (it.hasNext()) {
				Table tb = (Table) it.next();
				if(tb.numRows() < 8)
				{
					continue;
				}
				TestCase tc = new TestCase();
				tc.setTcCreateUser(user.getId());
				tc.setTcFlag(CommonService.NORMAL_FLAG);
				tc.setTcType(CaseType.CASE_TYPE_NORMAL);
				tc.setTcCreateTime(operDate);	
				
	
				// 迭代行，默认从0开始
				for (int i = 0; i < tb.numRows(); i++) {
					TableRow tr = tb.getRow(i);
					// 迭代列，默认从0开始
					for (int j = 0; j < tr.numCells(); j++) {
						TableCell td = tr.getCell(j);// 取得单元格
						// 取得单元格的内容
						for (int k = 0; k < td.numParagraphs(); k++) {
							Paragraph para = td.getParagraph(k);
	
							String keyStr = para.text();
							if (tr.numCells() > j + 1) {
																		
								String content = "";

								TableCell contentTd = tr.getCell(j + 1);
								for (int l = 0; l < contentTd.numParagraphs(); l++) {
									Paragraph pl = contentTd.getParagraph(l);
									content = content + "\n" + pl.text().trim();
								}

								if (content.contains("\n") && content.length() > 2) {
									content = content.substring(2);
								}
	
								if(keyStr.contains(CommonService.IMPORT_COLUMN_NAME[1]) || keyStr.contains(CommonService.IMPORT_COLUMN_NAME[0]))//功能点
								{
									String functionName = content;
									
									if(functionName != null && !functionName.isEmpty())
									{			
										for(ModuleFunction mf:project.getAllModuleFunctionList())
										{
											if(mf.getEntireName().endsWith(functionName.trim()))
											{												
												tc.setTcModuleFunction(mf.getMuId());
												String code = this.getTestCaseCode(mf);
												tc.setTcCode(code);
												break;	
											}
										}
									}												
								}
								else if(keyStr.contains(CommonService.IMPORT_COLUMN_NAME[2]))//用例编号
								{
//									tc.setTcCode(content);
								}
								else if(keyStr.contains(CommonService.IMPORT_COLUMN_NAME[3]))//测试目的
								{
									tc.setTcTestObjective(content);
								}
								else if(keyStr.contains(CommonService.IMPORT_COLUMN_NAME[4]))//测试内容
								{
									tc.setTcTestContent(content);
								}
								else if(keyStr.contains(CommonService.IMPORT_COLUMN_NAME[5]))//测试步骤
								{
									tc.setTcTestStep(content);
								}
							
								else if(keyStr.contains(CommonService.IMPORT_COLUMN_NAME[6]))//预期输出
								{
									tc.setTcIntendOutput(content);
								}
								else if(keyStr.contains(CommonService.IMPORT_COLUMN_NAME[9]))//创建人
								{
									String userName = content;
									for(TeamMember tm:project.getMemberList())
									{
										if(tm.getAccount().getPersonName().equals(userName))
										{
											tc.setTcCreateUser(tm.getTmAccount());
											break;
										}
									}					
								}
								else if(keyStr.contains(CommonService.IMPORT_COLUMN_NAME[11]))//用例类型
								{
									String caseType = content;
									List<CaseType> typeList = caseTypeDAO.find("select a from CaseType a where  a.ctFlag != -1");
									for(CaseType ct:typeList)
									{
										if(ct.getCtName().equals(caseType))
										{
											tc.setTcType(ct.getCtId());
											break;
										}
									}					
								}
								
							}
	
						} // end for
					} // end for
				} // end for
				
				try
				{
					testCaseDAO.save(tc);
					rtn++;
					
					// 新建用例记录
					TestCorrectRecord tcr = new TestCorrectRecord();
					tcr.setTcrTestCase(tc.getTcId());
					tcr.setTcrCaseStatus(CaseStatus.WAIT_TEST_STATUS);
					tcr.setCurrentOperRecord(true);
					tcr.setTcrOperUser(user.getId());
					tcr.setTcrOperTime(operDate);
					
					testCorrectRecordDAO.save(tcr);
									
					System.err.println("转换完成行号：" + tc.getTcCode() + ",客户信息Key:" + tc.getTcId());
				}
				catch(Exception e)
				{
					System.err.println("转换行序列号：" + tc.getTcCode() + "时，报错！！！！！");	
					e.printStackTrace();
				}
			} // end while
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rtn;
	}
	
	private Integer importDOCXFile(FileInputStream is,UsrAccount user,Project project)
	{
		Integer rtn = 0;
		try {			
			Date operDate = new Date();
			XWPFDocument xwpfDocument = new XWPFDocument(is);
			List<XWPFTable> tableList = xwpfDocument.getTables();
						
			// 迭代文档中的表格
			for (XWPFTable table : tableList) {			
				if(table.getNumberOfRows() < 8)
				{
					continue;
				}
				TestCase tc = new TestCase();
				tc.setTcCreateUser(user.getId());
				tc.setTcType(CaseType.CASE_TYPE_NORMAL);
				tc.setTcFlag(CommonService.NORMAL_FLAG);
				tc.setTcCreateTime(operDate);	
				
				
				
				// 迭代行，默认从0开始
				for (XWPFTableRow tr : table.getRows()) {
					// 迭代列，默认从0开始
					Iterator<XWPFTableCell> tdIt = tr.getTableCells().iterator();
					while (tdIt.hasNext()) {
						XWPFTableCell td = tdIt.next();
						for (XWPFParagraph keyPara : td.getParagraphs()) {
							String keyStr = keyPara.getText().trim();
							if (tdIt.hasNext()) {
								int column = 0;
								
								String content = "";

								XWPFTableCell contentTd = tdIt.next();
								for (XWPFParagraph pl : contentTd.getParagraphs()) {
									content = content + "\n" + pl.getText().trim();
								}

								if (content.contains("\n") && content.length() > 2) {
									content = content.substring(1);
								}	
								
								
								if(keyStr.contains(CommonService.IMPORT_COLUMN_NAME[1]) || keyStr.contains(CommonService.IMPORT_COLUMN_NAME[0]))//功能点
								{
									String functionName = content;
									if(functionName != null && !functionName.isEmpty())
									{													
										for(ModuleFunction mf:project.getAllModuleFunctionList())
										{
											if(mf.getEntireName().endsWith(functionName.trim()))
											{												
												tc.setTcModuleFunction(mf.getMuId());
												String code = this.getTestCaseCode(mf);
												tc.setTcCode(code);
												break;	
											}
										}											
									}												
								}
								else if(keyStr.contains(CommonService.IMPORT_COLUMN_NAME[2]))//用例编号
								{
//									tc.setTcCode(content);
								}
								else if(keyStr.contains(CommonService.IMPORT_COLUMN_NAME[3]))//测试目的
								{
									tc.setTcTestObjective(content);
								}
								else if(keyStr.contains(CommonService.IMPORT_COLUMN_NAME[4]))//测试内容
								{
									tc.setTcTestContent(content);
								}
								else if(keyStr.contains(CommonService.IMPORT_COLUMN_NAME[5]))//测试步骤
								{
									tc.setTcTestStep(content);
								}
								
								else if(keyStr.contains(CommonService.IMPORT_COLUMN_NAME[6]))//预期输出
								{
									tc.setTcIntendOutput(content);
								}
								else if(keyStr.contains(CommonService.IMPORT_COLUMN_NAME[9]))//创建人
								{
									String userName = content;
									for(TeamMember tm:project.getMemberList())
									{
										if(tm.getAccount().getPersonName().equals(userName))
										{
											tc.setTcCreateUser(tm.getTmAccount());
											break;
										}
									}					
								}
								else if(keyStr.contains(CommonService.IMPORT_COLUMN_NAME[11]))//用例类型
								{
									String caseType = content;
									List<CaseType> typeList = caseTypeDAO.find("select a from CaseType a where  a.ctFlag != -1");
									for(CaseType ct:typeList)
									{
										if(ct.getCtName().equals(caseType))
										{
											tc.setTcType(ct.getCtId());
											break;
										}
									}					
								}
										
									
							}

						} // end for
					} // end for
				} // end for
				
				try
				{
					testCaseDAO.save(tc);
					rtn++;
					
					// 新建用例记录
					TestCorrectRecord tcr = new TestCorrectRecord();
					tcr.setTcrTestCase(tc.getTcId());
					tcr.setTcrCaseStatus(CaseStatus.WAIT_TEST_STATUS);
					tcr.setCurrentOperRecord(true);
					tcr.setTcrOperUser(user.getId());
					tcr.setTcrOperTime(operDate);
					
					testCorrectRecordDAO.save(tcr);
									
					System.err.println("转换完成行号：" + tc.getTcCode() + ",客户信息Key:" + tc.getTcId());
				}
				catch(Exception e)
				{
					System.err.println("转换行序列号：" + tc.getTcCode() + "时，报错！！！！！");	
					e.printStackTrace();
				}
			} // end while
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rtn;
	}
	
	
	private Integer importXLSFile(FileInputStream is,UsrAccount user,Project project)
	{
		Integer rtn = 0;
				
		Workbook wb = null;
		try {
			wb = Workbook.getWorkbook(is);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Date operDate = new Date();
		Sheet st = wb.getSheet(0);
		int rows = st.getRows();
		int cols = st.getColumns();
		int colNameRow = 0;
		
		for(int i = 0;i < rows;i++)
		{
			for(int j = 0;j < cols;j++)
			{
				String content = st.getCell(j, i).getContents().trim();
				if(CommonService.IMPORT_COLUMN_NAME[0].equals(content))
				{
					colNameRow = i;
					break;
				}
			}
		}
		
		for (int i = colNameRow +1; i < rows; i++) {				
			TestCase tc = new TestCase();
			tc.setTcCreateUser(user.getId());
			tc.setTcType(CaseType.CASE_TYPE_NORMAL);
			tc.setTcFlag(CommonService.NORMAL_FLAG);
			tc.setTcCreateTime(operDate);
			
									
			for (int j = 0; j < cols; j++) {
				String colName = st.getCell(j,colNameRow).getContents().trim();
				colName = colName.replaceAll("\n", "");
				if (!colName.isEmpty()) {					
					if(colName.contains(CommonService.IMPORT_COLUMN_NAME[1]))//功能点
					{
						String functionName = st.getCell(j, i).getContents().trim();
						if(functionName != null && !functionName.isEmpty())
						{			
							for(ModuleFunction mf:project.getAllModuleFunctionList())
							{
								if(mf.getEntireName().endsWith(functionName.trim()))
								{												
									tc.setTcModuleFunction(mf.getMuId());
									String code = this.getTestCaseCode(mf);
									tc.setTcCode(code);
									break;	
								}
							}
						}																		
					}
					else if(colName.contains(CommonService.IMPORT_COLUMN_NAME[2]))//用例编号
					{						
//						tc.setTcCode(st.getCell(j, i).getContents().trim());
					}
					else if(colName.contains(CommonService.IMPORT_COLUMN_NAME[3]))//测试目的
					{
						tc.setTcTestObjective(st.getCell(j, i).getContents().trim());
					}
					else if(colName.contains(CommonService.IMPORT_COLUMN_NAME[4]))//测试内容
					{
						tc.setTcTestContent(st.getCell(j, i).getContents().trim());
					}
					else if(colName.contains(CommonService.IMPORT_COLUMN_NAME[5]))//测试步骤
					{
						tc.setTcTestStep(st.getCell(j, i).getContents().trim());
					}
					
					else if(colName.contains(CommonService.IMPORT_COLUMN_NAME[6]))//预期输出
					{
						tc.setTcIntendOutput(st.getCell(j, i).getContents().trim());
					}
					else if(colName.contains(CommonService.IMPORT_COLUMN_NAME[9]))//创建人
					{
						String userName = st.getCell(j, i).getContents().trim();
						for(TeamMember tm:project.getMemberList())
						{
							if(tm.getAccount().getPersonName().equals(userName))
							{
								tc.setTcCreateUser(tm.getTmAccount());
								break;
							}
						}					
					}
					else if(colName.contains(CommonService.IMPORT_COLUMN_NAME[11]))//用例类型
					{
						String caseType = st.getCell(j, i).getContents().trim();
						List<CaseType> typeList = caseTypeDAO.find("select a from CaseType a where  a.ctFlag != -1");
						for(CaseType ct:typeList)
						{
							if(ct.getCtName().equals(caseType))
							{
								tc.setTcType(ct.getCtId());
								break;
							}
						}					
					}
				}
			}	
			
			try
			{
				testCaseDAO.save(tc);
				rtn++;
				
				// 新建用例记录
				TestCorrectRecord tcr = new TestCorrectRecord();
				tcr.setTcrTestCase(tc.getTcId());
				tcr.setTcrCaseStatus(CaseStatus.WAIT_TEST_STATUS);
				tcr.setCurrentOperRecord(true);
				tcr.setTcrOperUser(user.getId());
				tcr.setTcrOperTime(operDate);
				
				testCorrectRecordDAO.save(tcr);
								
				System.err.println("转换完成行号：" + st.getCell(0,i).getContents().trim() + ",客户信息Key:" + tc.getTcId());
			}
			catch(Exception e)
			{
				System.err.println("转换行序列号：" + st.getCell(0,i).getContents().trim() + "时，报错！！！！！");	
				e.printStackTrace();
			}			
		}
												
		wb.close();
		try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
				
		return rtn;
	}
	
	

	@Override
	public List<TestCase> searchTestCaseByVersion(ProjectVersion projectVersion) {
		// TODO Auto-generated method stub
						
		String hqlStr = "select distinct a from TestCase a,CaseVersionReference e " +
				" where a.tcId = e.cvrTestCase and e.projectVersion.pvProject = " + projectVersion.getPvProject() + 
				" and a.tcFlag != " + CaseStatus.DELETE_STATUS + " and e.cvrFlag != " + CommonService.DELETE_FLAG + 
				" and e.projectVersion.pvVersion = '" + projectVersion.getPvVersion() + "'";
				
		hqlStr = hqlStr + " order by a.tcId";
		
        return testCaseDAO.find(hqlStr);
	}
	public void writeTestCaseToXslFile(String filePath,List<TestCase> testCaseList,ProjectVersion projectVersion,boolean design) 
	{		
		File file = new File(filePath);
		
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(file);		
			WritableWorkbook wwb = null;		
			wwb = Workbook.createWorkbook(os);
				
			WritableSheet wst = wwb.createSheet(projectVersion.getPvVersion() + "版测试用例", 0);			
			int j =0;			
			CellFormat cf = null;
			Label lbl = null;
		
			for(String colName:CommonService.IMPORT_COLUMN_NAME)
			{
				cf = wst.getWritableCell(0,0).getCellFormat();
				lbl = new Label(j,0, colName);
				if(cf != null)
				{
					lbl.setCellFormat(cf);
				}							
				wst.addCell(lbl);			
				j++;
			}
			
			int i = 1;
			for(TestCase tc:testCaseList)
			{
				CaseVersionReference cvr = null;
				for(CaseVersionReference cvrTemp:tc.getCaseVersionReferenceList())
				{
					if(cvrTemp.getCvrProjectVersion().equals(projectVersion.getPvId()))
					{
						cvr = cvrTemp;
						break;
					}
				}
					
				//功能模块			
				cf = wst.getWritableCell(0,i).getCellFormat();
				lbl = new Label(0,i, tc.getModuleFunction().getProjectModuleName());
				if(cf != null)
				{
					lbl.setCellFormat(cf);
				}							
				wst.addCell(lbl);
						
				//功能点				
				cf = wst.getWritableCell(1,i).getCellFormat();
				lbl = new Label(1,i, tc.getModuleFunction().getEntireModuleFunctionName());
				if(cf != null)
				{
					lbl.setCellFormat(cf);
				}							
				wst.addCell(lbl);
									
				//用例编号
				cf = wst.getWritableCell(2,i).getCellFormat();
				lbl = new Label(2,i, tc.getTcCode());
				if(cf != null)
				{
					lbl.setCellFormat(cf);
				}							
				wst.addCell(lbl);
				
				//测试目的
				cf = wst.getWritableCell(3,i).getCellFormat();
				lbl = new Label(3,i, tc.getTcTestObjective());
				if(cf != null)
				{
					lbl.setCellFormat(cf);
				}							
				wst.addCell(lbl);
				
				//测试内容
				cf = wst.getWritableCell(4,i).getCellFormat();
				lbl = new Label(4,i, tc.getTcTestContent());
				if(cf != null)
				{
					lbl.setCellFormat(cf);
				}							
				wst.addCell(lbl);
				
				//测试步骤
				cf = wst.getWritableCell(5,i).getCellFormat();
				lbl = new Label(5,i, tc.getTcTestStep());
				if(cf != null)
				{
					lbl.setCellFormat(cf);
				}							
				wst.addCell(lbl);
							
				
				//预期输出
				cf = wst.getWritableCell(7,i).getCellFormat();
				lbl = new Label(7,i, tc.getTcIntendOutput());
				if(cf != null)
				{
					lbl.setCellFormat(cf);
				}							
				wst.addCell(lbl);	
				
				//测试输出		
				if(!design)
				{
					cf = wst.getWritableCell(8,i).getCellFormat();
					lbl = new Label(8,i, cvr.getCvrCaseOutput());
					if(cf != null)
					{
						lbl.setCellFormat(cf);
					}							
					wst.addCell(lbl);
				}
				
					
				//测试结果					
				if(cvr.getTestResult() != null && !design)
				{
					cf = wst.getWritableCell(9,i).getCellFormat();
					lbl = new Label(9,i, cvr.getTestResult().getTrName());
					if(cf != null)
					{
						lbl.setCellFormat(cf);
					}							
					wst.addCell(lbl);
				}
				
				
				//创建人
				cf = wst.getWritableCell(10,i).getCellFormat();
				lbl = new Label(10,i,tc.getCreateUser().getPersonName());
				if(cf != null)
				{
					lbl.setCellFormat(cf);
				}							
				wst.addCell(lbl);
				
				//测试人
				if(cvr.getTestUser() != null && !design)
				{
					cf = wst.getWritableCell(11,i).getCellFormat();
					lbl = new Label(11,i, cvr.getTestUser().getPersonName());
					if(cf != null)
					{
						lbl.setCellFormat(cf);
					}							
					wst.addCell(lbl);
				}
												
				i++;
			}		
		
			wwb.write();		
			wwb.close();		
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private String getTestCaseCode(ModuleFunction mf)
	{
		String rtn = "";
		
		String mfCode = mf.getEntireCode();
		
		String hqlStr = "select a.tcCode from TestCase a where a.tcModuleFunction = " + mf.getMuId() + " and a.tcCode like '%" + mfCode + "%'";
		
		String caseCode = this.retrieveTestCase(hqlStr);
		
		if(caseCode != null)
		{
			Integer index = caseCode.indexOf(TestCase.CODE_SERIALNUM_DIVIDE);
			String code = caseCode.substring(index +1);
			code = "0000" + (Integer.parseInt(code) + 1);
			
			rtn = mfCode + TestCase.CODE_SERIALNUM_DIVIDE + code.substring(code.length() -4);
		}
		else
		{
			rtn = mfCode + TestCase.CODE_SERIALNUM_DIVIDE + "0001";
		}
		
		return rtn;
	}
	
	public void saveTestCase(TestCase testCase,String uploadPath) {		
		// TODO Auto-generated method stub
		ArrayList<TestCorrectRecord> recordList = testCase.getTestCorrectRecordList();
		ArrayList<CaseVersionReference> cvrList = testCase.getCaseVersionReferenceList();
		ArrayList<CaseAttachment> attachmentList = testCase.getAttachmentList();
		
		testCase.setAttachmentList(new ArrayList<CaseAttachment>());
		testCase.setTestCorrectRecordList(new ArrayList<TestCorrectRecord>());
		testCase.setCaseVersionReferenceList(new ArrayList<CaseVersionReference>());
				
		ModuleFunction mf = testCase.getModuleFunction();
		String mfCode = mf.getEntireCode();		
		String oldCode = testCase.getTcCode();
		
		if(oldCode == null || !mfCode.equals(oldCode.substring(0,oldCode.length()-5)))
		{			
			String code = this.getTestCaseCode(mf);
			testCase.setTcCode(code);
		}			
		
		if(testCase.getTcId() == null)
		{
			testCaseDAO.save(testCase);
		}
		else
		{
			testCaseDAO.update(testCase);
		}
		
		testCase.setAttachmentList(attachmentList);
		testCase.setTestCorrectRecordList(recordList);
		testCase.setCaseVersionReferenceList(cvrList);
		
		for(TestCorrectRecord record:recordList)
		{
			if(!record.isCurrentOperRecord())
			{
				continue;
			}
				
			if(record.getTcrId() == null)
			{
				record.setTcrTestCase(testCase.getTcId());
				testCorrectRecordDAO.save(record);
			}
			else
			{
				testCorrectRecordDAO.update(record);
			}
		}
		
		for(CaseVersionReference cvr:cvrList)
		{			
			ArrayList<CvrAttachment> cvrAttachmentList = cvr.getAttachmentList();
			cvr.setAttachmentList(new ArrayList<CvrAttachment>());
			if(cvr.getCvrId() == null)
			{
				cvr.setCvrTestCase(testCase.getTcId());
				caseVersionReferenceDAO.save(cvr);
			}
			else
			{
				caseVersionReferenceDAO.update(cvr);
			}			
			
			cvr.setAttachmentList(cvrAttachmentList);
			
			for (CvrAttachment pa : cvrAttachmentList) {
				if (pa.getCaId() == null && pa.getCaFlag().equals(CommonService.NORMAL_FLAG)) {
					String fileName = FileUtil.saveUploadFile(pa.getAttachmentFile(),
							uploadPath + "uploadImportFile\\cvrAttachment\\" + cvr.getCvrId());

					pa.setCaCaseVersionReference(cvr.getCvrId());
					pa.setCaCreateTime(new Date());
					pa.setCaUrl("uploadImportFile\\cvrAttachment\\" + cvr.getCvrId() + "\\" + fileName);

					cvrAttachmentDAO.save(pa);
				}
				else if (pa.getCaId() != null)
				{
					cvrAttachmentDAO.update(pa);
				}
			}
		}
		
		for (CaseAttachment pa : attachmentList) {
			if (pa.getCaId() == null && pa.getCaFlag().equals(CommonService.NORMAL_FLAG)) {
				String fileName = FileUtil.saveUploadFile(pa.getAttachmentFile(),
						uploadPath + "uploadImportFile\\caseAttachment\\" + testCase.getTcId());

				pa.setCaTestCase(testCase.getTcId());
				pa.setCaCreateTime(new Date());
				pa.setCaUrl("uploadImportFile\\caseAttachment\\" + testCase.getTcId() + "\\" + fileName);

				caseAttachmentDAO.save(pa);
			}
			else if (pa.getCaId() != null)
			{
				caseAttachmentDAO.update(pa);
			}
		}
	}
	
	
	public List<TestCase> searchTestCase(Object[] args) {
		// TODO Auto-generated method stub
		String page = (String) args[1];
		TestCase searchInfo = (TestCase) args[0];
		CaseVersionReference cvrSearchInfo = (CaseVersionReference)args[2];
		String functionList = (String) args[3];
		
		String hqlStr = null;
		
		if(cvrSearchInfo.isSearchInfoEmpty())
		{			
			hqlStr = "select a from TestCase a where a.tcFlag != " + CaseStatus.DELETE_STATUS + " and a.tcModuleFunction in " + functionList;			
		}
		else
		{
			hqlStr = "select distinct a from TestCase a,CaseVersionReference e " +
					" where  a.tcId = e.cvrTestCase and a.tcModuleFunction in " + functionList + 
					" and a.tcFlag != " + CaseStatus.DELETE_STATUS + " and  e.cvrFlag != " + CommonService.DELETE_FLAG ;
		}
						        
        return this.retrieveTestCaseList(searchInfo,cvrSearchInfo, hqlStr,Integer.parseInt(page));		 
	}
	
	public List<TestCase> searchTestCaseForReference(Object[] args) {
		// TODO Auto-generated method stub
		String page = (String) args[1];
		TestCase searchInfo = (TestCase) args[0];
		CaseVersionReference cvrSearchInfo = (CaseVersionReference)args[2];
		String functionList = (String) args[3];
		
		String referedCaseList = this.getReferedCaseListStr(cvrSearchInfo.getReferVersion(), functionList);
		
		String hqlStr = null ;
		if(cvrSearchInfo.isSearchInfoEmpty())
		{			
			hqlStr = "select a from TestCase a " +
					" where a.tcModuleFunction in " + functionList + " and a.tcId not in " + referedCaseList + 
					" and a.tcFlag != " + CaseStatus.DELETE_STATUS ;			
		}
		else
		{
			hqlStr = "select distinct a from TestCase a,CaseVersionReference e " +
					" where  a.tcId = e.cvrTestCase and a.tcModuleFunction in " + functionList + " and a.tcId not in " + referedCaseList +
					" and a.tcFlag != " + CaseStatus.DELETE_STATUS + " and  e.cvrFlag != " + CommonService.DELETE_FLAG ;
		}
						        
        return this.retrieveTestCaseList(searchInfo,cvrSearchInfo, hqlStr,Integer.parseInt(page));		 
	}
	
	public static String processQuerySql(CaseVersionReference cvr,String hqlStr)
	{
		
		if(cvr.getCvrCaseOutput() != null && !cvr.getCvrCaseOutput().isEmpty())
		{
			hqlStr = hqlStr + " and e.cvrCaseOutput like '%" + cvr.getCvrCaseOutput() + "%'";
		}		
		if(cvr.getCvrCaseResult() != null && cvr.getCvrCaseResult() != 0)
		{
			hqlStr = hqlStr + " and e.cvrCaseResult = " + cvr.getCvrCaseResult();
		}
		if(cvr.getCvrCaseStatus() != null && cvr.getCvrCaseStatus() != 0)
		{
			hqlStr = hqlStr + " and e.cvrCaseStatus = " + cvr.getCvrCaseStatus();
		}
		if(cvr.getCvrTestTimeStr() != null && !cvr.getCvrTestTimeStr().isEmpty())
		{
			hqlStr = hqlStr + " and e.cvrTestTime like '%" + cvr.getCvrTestTimeStr() + "%'";
		}		
		if(cvr.getCvrTestUserStr() != null && !cvr.getCvrTestUserStr().isEmpty())
		{
			hqlStr = hqlStr + " and e.testUser.personName like '%" + cvr.getCvrTestUserStr() + "%'";
		}
		if(cvr.getCvrTestUser() != null)
		{
			hqlStr = hqlStr + " and e.cvrTestUser = " + cvr.getCvrTestUser();
		}
		if(cvr.getCvrCorrectTimeStr()  != null && !cvr.getCvrCorrectTimeStr().isEmpty())
		{
			hqlStr = hqlStr + " and e.cvrCorrectTime like '%" + cvr.getCvrCorrectTimeStr() + "%'";
		}		
		if(cvr.getCvrCorrectUserStr()  != null && !cvr.getCvrCorrectUserStr().isEmpty())
		{
			hqlStr = hqlStr + " and e.correctUser.personName like '%" + cvr.getCvrCorrectUserStr() + "%'";
		}		
		if(cvr.getCvrImportantLevel() != null && cvr.getCvrImportantLevel() != 0)
		{
			hqlStr = hqlStr + " and e.cvrImportantLevel = " + cvr.getCvrImportantLevel();
		}
		if(cvr.getCvrBugType() != null && cvr.getCvrBugType() != 0)
		{
			hqlStr = hqlStr + " and e.cvrBugType = " + cvr.getCvrBugType();
		}
		if(cvr.getCvrProjectVersion() != null && cvr.getCvrProjectVersion() != 0)
		{
			hqlStr = hqlStr + " and e.cvrProjectVersion = " + cvr.getCvrProjectVersion();
		}
		
		
		return hqlStr;
	}
	
	public static String processQuerySql(TestCase searchInfo,String hqlStr)
	{		
		if(searchInfo.getTcModuleFunction()!= null)
		{
			hqlStr = hqlStr + " and a.tcModuleFunction = " + searchInfo.getTcModuleFunction();
		}
		
		if(searchInfo.getTcType()!= null && !searchInfo.getTcType().equals(0))
		{
			hqlStr = hqlStr + " and a.tcType = " + searchInfo.getTcType();
		}
		
		if(searchInfo.getTcCode() != null && !searchInfo.getTcCode().isEmpty())
		{
			hqlStr = hqlStr + " and a.tcCode like '%" + searchInfo.getTcCode() + "%'";
		}
		if(searchInfo.getTcTestObjective() != null && !searchInfo.getTcTestObjective().isEmpty())
		{
			hqlStr = hqlStr + " and a.tcTestObjective like '%" + searchInfo.getTcTestObjective() + "%'";
		}
		if(searchInfo.getTcTestContent() != null && !searchInfo.getTcTestContent().isEmpty())
		{
			hqlStr = hqlStr + " and a.tcTestContent like '%" + searchInfo.getTcTestContent() + "%'";
		}
		if(searchInfo.getTcIntendOutput() != null && !searchInfo.getTcIntendOutput().isEmpty())
		{
			hqlStr = hqlStr + " and a.tcIntendOutput like '%" + searchInfo.getTcIntendOutput() + "%'";
		}

		if(searchInfo.getTcCreateTimeStr() != null && !searchInfo.getTcCreateTimeStr().isEmpty())
		{
			hqlStr = hqlStr + " and a.tcCreateTime like '%" + searchInfo.getTcCreateTimeStr() + "%'";
		}		
		if(searchInfo.getTcCreateUserStr() != null && !searchInfo.getTcCreateUserStr().isEmpty())
		{
			hqlStr = hqlStr + " and a.createUser.personName like '%" + searchInfo.getTcCreateUserStr() + "%'";
		}
		
		if(searchInfo.getTcCreateUser() != null)
		{
			hqlStr = hqlStr + " and a.tcCreateUser = " + searchInfo.getTcCreateUser();
		}

		
		return hqlStr;		
	}

	
	public Integer searchTestCaseCount(Object[] args) {
		// TODO Auto-generated method stub
		TestCase searchInfo = (TestCase) args[0];
		CaseVersionReference cvrSearchInfo = (CaseVersionReference)args[2];
		String functionList = (String) args[3];
		
		String hqlStr = null;
		if(cvrSearchInfo.isSearchInfoEmpty())			
		{			
			hqlStr = "select count(a) from TestCase a where a.tcFlag != " + CaseStatus.DELETE_STATUS + " and a.tcModuleFunction in " + functionList;			
		}
		else
		{
			hqlStr = "select  count(distinct a) from TestCase a,CaseVersionReference e " +
					" where a.tcId = e.cvrTestCase and a.tcModuleFunction in " + functionList + 
					" and a.tcFlag != " + CaseStatus.DELETE_STATUS + " and e.cvrFlag != " + CommonService.DELETE_FLAG ;
		}    	
		    	
    	hqlStr = TestCaseServiceImpl.processQuerySql(searchInfo, hqlStr);
    	hqlStr = TestCaseServiceImpl.processQuerySql(cvrSearchInfo, hqlStr);
    	
        return testCaseDAO.getFindCount(hqlStr);		
	}
	
	public Integer searchTestCaseCountForReference(Object[] args) {
		// TODO Auto-generated method stub
		TestCase searchInfo = (TestCase) args[0];
		CaseVersionReference cvrSearchInfo = (CaseVersionReference)args[2];
		String functionList = (String) args[3];
		
		String referedCaseList = this.getReferedCaseListStr(cvrSearchInfo.getReferVersion(), functionList);
		
		String hqlStr = null;
		if(cvrSearchInfo.isSearchInfoEmpty())			
		{			
			hqlStr = "select count(a) from TestCase a where a.tcFlag != " + CaseStatus.DELETE_STATUS + 
					" and a.tcModuleFunction in " + functionList + " and a.tcId not in " + referedCaseList;			
		}
		else
		{
			hqlStr = "select  count(distinct a) from TestCase a,CaseVersionReference e " +
					" where a.tcId = e.cvrTestCase and a.tcModuleFunction in " + functionList + " and a.tcId not in " + referedCaseList + 
					" and a.tcFlag != " + CaseStatus.DELETE_STATUS + " and e.cvrFlag != " + CommonService.DELETE_FLAG ;
		}  
		    			    	
    	hqlStr = TestCaseServiceImpl.processQuerySql(searchInfo, hqlStr);
    	hqlStr = TestCaseServiceImpl.processQuerySql(cvrSearchInfo, hqlStr);
    	
        return testCaseDAO.getFindCount(hqlStr);		
	}
	
	private String getReferedCaseListStr(Integer referVersion,String functionList)
	{
		String hqlStr = "select  distinct a from TestCase a,CaseVersionReference e " +
				" where a.tcId = e.cvrTestCase and a.tcModuleFunction in " + functionList + 
				" and e.cvrProjectVersion = " + referVersion + 
				" and a.tcFlag != " + CaseStatus.DELETE_STATUS + " and e.cvrFlag != " + CommonService.DELETE_FLAG ;
		
		
		MyQuery myQuery = new MyQuery();
    	myQuery.setOffset(false);
        myQuery.setOrderby(" order by a.tcId");
        myQuery.setQueryString(hqlStr);
       
        List<TestCase> list = testCaseDAO.find(myQuery);
        
        String rtn = "";
        for(TestCase tc:list)
        {
        	rtn = rtn + "," + tc.getTcId();
        }
        
        if(!rtn.isEmpty())
        {
        	rtn = " (" + rtn.substring(1) + ") ";
        }
        else
        {
        	rtn = " (0) ";
        }
        
        return rtn;
	}

	public void setTestCaseDAO(TestCaseDAO testCaseDAO) {
		this.testCaseDAO = testCaseDAO;
	}

	public TestCaseDAO getTestCaseDAO() {
		return testCaseDAO;
	}

	public void setImportantLevelDAO(ImportantLevelDAO importantLevelDAO) {
		this.importantLevelDAO = importantLevelDAO;
	}

	public ImportantLevelDAO getImportantLevelDAO() {
		return importantLevelDAO;
	}

	public void setTestResultDAO(TestResultDAO testResultDAO) {
		this.testResultDAO = testResultDAO;
	}

	public TestResultDAO getTestResultDAO() {
		return testResultDAO;
	}
	
	public List<ImportantLevel> getImportantLevelList() {
		// TODO Auto-generated method stub
		String sql = "from ImportantLevel a where a.ilFlag != " + CommonService.DELETE_FLAG;
		
		return importantLevelDAO.find(sql);
	}

	
	public List<TestResult> getTestResultList() {
		// TODO Auto-generated method stub
		String sql = "from TestResult a where a.trFlag != " + CommonService.DELETE_FLAG;
		
		return testResultDAO.find(sql);
	}

	
	public List<CaseStatus> getCaseStatusList() {
		// TODO Auto-generated method stub
		String sql = "from CaseStatus a where a.csFlag != " + CommonService.DELETE_FLAG + " and a.csId != " + CommonService.DELETE_FLAG;
		
		return caseStatusDAO.find(sql);
	}

	public void setCaseStatusDAO(CaseStatusDAO caseStatusDAO) {
		this.caseStatusDAO = caseStatusDAO;
	}

	public CaseStatusDAO getCaseStatusDAO() {
		return caseStatusDAO;
	}
	
	public TestCase getTestCaseById(Integer id) {
		// TODO Auto-generated method stub
		return testCaseDAO.get(id);
	}
	
	public TestCase getTestCaseNextDisplay(Object[] args,Integer id) {
		// TODO Auto-generated method stub
				
		TestCase searchInfo = (TestCase) args[0];
		CaseVersionReference cvrSearchInfo = (CaseVersionReference)args[1];
				
		return this.getTestCaseDisplay(searchInfo, id, true,cvrSearchInfo,(String)args[2]);
	}
		
	private TestCase getTestCaseDisplay(TestCase searchInfo,Integer id,boolean isNext,CaseVersionReference cvrSearchInfo,String functionList)
	{		
		String hqlStr = null;
		if(cvrSearchInfo.isSearchInfoEmpty())
		{
			hqlStr = "select a from TestCase a where a.tcModuleFunction in " + functionList + " and a.tcFlag != " + CaseStatus.DELETE_STATUS ;			
		}
		else
		{
			hqlStr = "select distinct a from TestCase a,CaseVersionReference e " +
					" where a.tcId = e.cvrTestCase and a.tcModuleFunction in " + functionList + 
					" and a.tcFlag != " + CaseStatus.DELETE_STATUS + " and e.cvrFlag != " + CommonService.DELETE_FLAG ;
		}
			
		hqlStr = TestCaseServiceImpl.processQuerySql(searchInfo, hqlStr);
		hqlStr = TestCaseServiceImpl.processQuerySql(cvrSearchInfo, hqlStr);
		
		return this.retrieveTestCase(hqlStr,isNext,id);
	}
	
	public TestCase getTestCasePreviousDisplay(Object[] args,Integer id) {
		// TODO Auto-generated method stub			
		TestCase searchInfo = (TestCase) args[0];
		CaseVersionReference cvrSearchInfo = (CaseVersionReference)args[1];
		String functionList =  (String) args[2];
		
		return this.getTestCaseDisplay(searchInfo, id, false,cvrSearchInfo,functionList);
	}
	
	private TestCase retrieveTestCase(String hqlStr,boolean isNext,Integer id)
	{	    	
    	MyQuery myQuery = new MyQuery();
    	myQuery.setPageSize(1);    	
        myQuery.setPageStartNo(0);
                       
    	if(isNext)
        {
        	hqlStr =  hqlStr + " and a.tcId > " + id;
        	myQuery.setOrderby(" order by a.tcId");
        }
        else
        {
        	hqlStr =  hqlStr + " and a.tcId < " + id;
        	myQuery.setOrderby(" order by a.tcId desc");
        }
                
        myQuery.setQueryString(hqlStr);

        myQuery.setOffset(true);
       
        List<TestCase>  list = testCaseDAO.find(myQuery);
        
        return list.size()==0?null:list.get(0);
	}
	
	private String retrieveTestCase(String hqlStr)
	{	    	
    	MyQuery myQuery = new MyQuery();
    	myQuery.setPageSize(1);    	
        myQuery.setPageStartNo(0);
                                  	
        myQuery.setOrderby(" order by a.tcCode desc");       
        
        myQuery.setQueryString(hqlStr);

        myQuery.setOffset(true);
       
        List<String>  list = testCaseDAO.getBaseDAO().findEntity(myQuery);
        
        return list.size()==0?null:list.get(0);
	}
	
		
	private List<TestCase> retrieveTestCaseList(TestCase searchInfo,CaseVersionReference cvrSearchInfo,String hqlStr,int pageNum)
	{		
		hqlStr = TestCaseServiceImpl.processQuerySql(searchInfo, hqlStr);
		hqlStr = TestCaseServiceImpl.processQuerySql(cvrSearchInfo, hqlStr);
		
    	MyQuery myQuery = new MyQuery();
    	myQuery.setPageSize(searchInfo.getPageItemCount());    	
        myQuery.setPageStartNo(pageNum);
        myQuery.setOrderby(" order by a.tcId");
        myQuery.setQueryString(hqlStr);

        myQuery.setOffset(true);
       
        return testCaseDAO.find(myQuery);        
	}

	public TestCase getTestCaseNextEdit(Object[] args,Integer id) {
		// TODO Auto-generated method stub	
		TestCase searchInfo = (TestCase) args[0];
		CaseVersionReference cvrSearchInfo = (CaseVersionReference)args[1];
				 
		return this.getTestCaseEdit(searchInfo, id, true,cvrSearchInfo,(String)args[2]);
	}
	
	private TestCase getTestCaseEdit(TestCase searchInfo,Integer id,boolean isNext,CaseVersionReference cvrSearchInfo,String functionList)
	{
		String hqlStr = null;
		if(cvrSearchInfo.isSearchInfoEmpty())
		{
			hqlStr = "select a from TestCase a where a.tcFlag != " + CaseStatus.DELETE_STATUS ;
			if(searchInfo.getProjectModule() != null)
			{
				hqlStr = hqlStr + " and a.tcModuleFunction in " + functionList;
			}
		}
		else
		{
			hqlStr = "select distinct a from TestCase a,CaseVersionReference e " +
					" where a.tcId = e.cvrTestCase and a.tcModuleFunction in " + functionList + 
					" and a.tcFlag != " + CaseStatus.DELETE_STATUS + " and e.cvrFlag != " + CommonService.DELETE_FLAG ;
		}
		
		hqlStr = TestCaseServiceImpl.processQuerySql(searchInfo, hqlStr);
		hqlStr = TestCaseServiceImpl.processQuerySql(cvrSearchInfo, hqlStr);
		
		 return this.retrieveTestCase(hqlStr,isNext,id);
	}
	
	public TestCase getTestCasePreviousEdit(Object[] args,Integer id) {
		// TODO Auto-generated method stub		
		TestCase searchInfo = (TestCase) args[0];
		CaseVersionReference cvrSearchInfo = (CaseVersionReference)args[1];
				 
		 return this.getTestCaseEdit(searchInfo, id, false,cvrSearchInfo,(String)args[2]);
	}
	
	public TestCase getTestCaseNextTest(Object[] args,Integer id) {
		// TODO Auto-generated method stub	
		TestCase searchInfo = (TestCase) args[0];
		CaseVersionReference cvrSearchInfo = (CaseVersionReference)args[1];
		String functionList = (String) args[2];
						 
		 return this.getTestCaseTest(searchInfo, id, true,cvrSearchInfo,functionList);
	}
	
	private TestCase getTestCaseTest(TestCase searchInfo,Integer id,boolean isNext,CaseVersionReference cvrSearchInfo,String functionList)
	{		
		String hqlStr = "select a from TestCase a,CaseVersionReference e " +
					" where a.tcId = e.cvrTestCase and a.tcModuleFunction in " + functionList + 
					" and a.tcFlag != " + CaseStatus.DELETE_STATUS + " and e.cvrFlag != " + CommonService.DELETE_FLAG + 
					" and e.cvrProjectVersion = " + cvrSearchInfo.getCvrProjectVersion()  +
					" and ( e.cvrCaseStatus = " + CaseStatus.WAIT_TEST_STATUS + " or e.cvrCaseStatus = " + CaseStatus.CORRECT_STATUS + ")";
				
		hqlStr = TestCaseServiceImpl.processQuerySql(searchInfo, hqlStr);
		hqlStr = TestCaseServiceImpl.processQuerySql(cvrSearchInfo, hqlStr);
		
		 return this.retrieveTestCase(hqlStr,isNext,id);
	}
	
	public TestCase getTestCasePreviousTest(Object[] args,Integer id) {
		// TODO Auto-generated method stub		
		TestCase searchInfo = (TestCase) args[0];
		CaseVersionReference cvrSearchInfo = (CaseVersionReference)args[1];
		String functionList = (String) args[2];
		
		return this.getTestCaseTest(searchInfo, id, false,cvrSearchInfo,functionList);
	}

	public TestCase getTestCaseNextCorrect(Object[] args,Integer id) {
		// TODO Auto-generated method stub		
		TestCase searchInfo = (TestCase) args[0];
		CaseVersionReference cvrSearchInfo = (CaseVersionReference)args[1];
		String functionList = (String) args[2];
		
		 return this.getTestCaseCorrect(searchInfo, id, true,cvrSearchInfo,functionList); 
	}
	
	private TestCase getTestCaseCorrect(TestCase searchInfo,Integer id,boolean isNext,CaseVersionReference cvrSearchInfo,String functionList)
	{
		String hqlStr = "select a from TestCase a ,CaseVersionReference e " +
					" where a.tcId = e.cvrTestCase and a.tcModuleFunction in " + functionList + 
					" and a.tcFlag != " + CaseStatus.DELETE_STATUS + " and  e.cvrFlag != " + CommonService.DELETE_FLAG +
					" and e.cvrCaseStatus = " + CaseStatus.TESTED_STATUS + " and e.cvrCaseResult = " + TestResult.TestResult_FAILED;
				
		hqlStr = TestCaseServiceImpl.processQuerySql(searchInfo, hqlStr);
		hqlStr = TestCaseServiceImpl.processQuerySql(cvrSearchInfo, hqlStr);
			
		 return this.retrieveTestCase(hqlStr,isNext,id);
	}
	
	public TestCase getTestCasePreviousCorrect(Object[] args,Integer id) {
		// TODO Auto-generated method stub	
		TestCase searchInfo = (TestCase) args[0];
		CaseVersionReference cvrSearchInfo = (CaseVersionReference)args[1];
		String functionList = (String) args[2];
		
		 return this.getTestCaseCorrect(searchInfo, id, false,cvrSearchInfo,functionList); 
	}
	
	public List<TestCase> getAllTestCase(Object[] args) {
		// TODO Auto-generated method stub		
		TestCase searchInfo = (TestCase) args[0];
		Project projectInfo = (Project) args[1];		
		CaseVersionReference cvrSearchInfo = (CaseVersionReference)args[2];
		String functionList = (String) args[3];
				
		String hqlStr = null;
		
		if(cvrSearchInfo.isSearchInfoEmpty())
		{
			hqlStr = "select a from TestCase a where a.tcFlag != " + CaseStatus.DELETE_STATUS  + " and a.tcModuleFunction in " + functionList;
		}
		else
		{
			hqlStr = "select distinct a from TestCase a,CaseVersionReference e where a.tcId = e.cvrTestCase and a.tcModuleFunction in " + functionList + 
					" and a.tcFlag != " + CaseStatus.DELETE_STATUS + " and e.cvrFlag != " + CommonService.DELETE_FLAG;
		}
		
		hqlStr = TestCaseServiceImpl.processQuerySql(searchInfo, hqlStr);  
		hqlStr = TestCaseServiceImpl.processQuerySql(cvrSearchInfo, hqlStr);
		
		hqlStr = hqlStr + " order by a.tcId";
		
        return testCaseDAO.find(hqlStr);
	}


	
	public TestCorrectRecord createTestCorrectRecord(CaseVersionReference cvr) {
		// TODO Auto-generated method stub
		TestCorrectRecord rtn = new TestCorrectRecord();
		rtn.setTcrTestCase(cvr.getCvrTestCase());
		rtn.setTcrCaseStatus(cvr.getCvrCaseStatus());
		rtn.setStatus(cvr.getStatus());
		rtn.setTcrTestVersion(cvr.getCvrProjectVersion());
		rtn.setTestVersion(cvr.getProjectVersion());
				
		rtn.setCurrentOperRecord(true);
		
		return rtn;
	}

	public void setTestCorrectRecordDAO(TestCorrectRecordDAO testCorrectRecordDAO) {
		this.testCorrectRecordDAO = testCorrectRecordDAO;
	}

	public TestCorrectRecordDAO getTestCorrectRecordDAO() {
		return testCorrectRecordDAO;
	}

	
	public CaseStatus getCaseStatusById(int id) {
		// TODO Auto-generated method stub
		return caseStatusDAO.get(id);
	}

	public void setBugTypeDAO(BugTypeDAO bugTypeDAO) {
		this.bugTypeDAO = bugTypeDAO;
	}

	public BugTypeDAO getBugTypeDAO() {
		return bugTypeDAO;
	}

	
	public List<BugType> getBugTypeList() {
		// TODO Auto-generated method stub
		String sql = "from BugType a where a.btFlag != " + CommonService.DELETE_FLAG;
		
		return bugTypeDAO.find(sql);
	}
	
	public List<CaseType> getCaseTypeList() {
		// TODO Auto-generated method stub
		String sql = "from CaseType a where a.ctFlag != " + CommonService.DELETE_FLAG;
		
		return caseTypeDAO.find(sql);
	}

	@Override
	public TestResult getTestResultById(Integer id) {
		// TODO Auto-generated method stub
		return testResultDAO.get(id);
	}

	public CaseVersionReferenceDAO getCaseVersionReferenceDAO() {
		return caseVersionReferenceDAO;
	}

	public void setCaseVersionReferenceDAO(CaseVersionReferenceDAO caseVersionReferenceDAO) {
		this.caseVersionReferenceDAO = caseVersionReferenceDAO;
	}

	@Override
	public CaseVersionReference getCaseVersionReferenceById(Integer id) {
		// TODO Auto-generated method stub
		return caseVersionReferenceDAO.get(id);
	}

	public ModuleFunctionDAO getModuleFunctionDAO() {
		return moduleFunctionDAO;
	}

	public void setModuleFunctionDAO(ModuleFunctionDAO moduleFunctionDAO) {
		this.moduleFunctionDAO = moduleFunctionDAO;
	}

	public CaseAttachmentDAO getCaseAttachmentDAO() {
		return caseAttachmentDAO;
	}

	public void setCaseAttachmentDAO(CaseAttachmentDAO caseAttachmentDAO) {
		this.caseAttachmentDAO = caseAttachmentDAO;
	}
	
	public CaseTypeDAO getCaseTypeDAO() {
		return caseTypeDAO;
	}

	public void setCaseTypeDAO(CaseTypeDAO caseTypeDAO) {
		this.caseTypeDAO = caseTypeDAO;
	}

	@Override
	public ModuleFunction getModuleFunctionById(Integer id) {
		// TODO Auto-generated method stub
		return moduleFunctionDAO.get(id);
	}

	@Override
	public CaseType getCaseTypeById(Integer id) {
		// TODO Auto-generated method stub
		return caseTypeDAO.get(id);
	}

	@Override
	public ImportantLevel getImportantLevelById(Integer id) {
		// TODO Auto-generated method stub
		return importantLevelDAO.get(id);
	}

	@Override
	public BugType getBugTypeById(Integer id) {
		// TODO Auto-generated method stub
		return bugTypeDAO.get(id);
	}

	public CvrAttachmentDAO getCvrAttachmentDAO() {
		return cvrAttachmentDAO;
	}

	public void setCvrAttachmentDAO(CvrAttachmentDAO cvrAttachmentDAO) {
		this.cvrAttachmentDAO = cvrAttachmentDAO;
	}

}
