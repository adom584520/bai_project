package com.pbtd.manager.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.pbtd.manager.domain.LoginInfo;
import com.pbtd.manager.domain.Mac;
import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.mapper.MacMapper;
import com.pbtd.manager.query.MacQueryObject;
import com.pbtd.manager.service.MacService;
import com.pbtd.manager.util.ImportExcelUtil;
import com.pbtd.manager.util.LoginInfoContext;
import com.pbtd.manager.util.PageResult;

@Service
public class MacServiceImpl implements MacService {
	private static final Logger logger = LoggerFactory.getLogger(MacServiceImpl.class);
	@Autowired
	private MacMapper macMapper;

	@Override
	public PageResult queryList(MacQueryObject qo) {
		Long count = macMapper.queryCount(qo);
		if (count < 1) {
			return new PageResult(count, Collections.EMPTY_LIST);
		}
		List<Mac> data = macMapper.queryList(qo);
		return new PageResult(count, data);
	}

	@Override
	@Transactional
	public void insert(Mac mac) {
		LoginInfo current = LoginInfoContext.getCurrent();
		if (current == null) {
			throw new JsonMessageException("账号未登录，请重新登录！");
		}
		Mac macByMacName = macMapper.getMacByMacName(mac.getMacName());
		if (macByMacName != null) {
			throw new JsonMessageException("该MAC已经存在！");
		}
		mac.setCreateTime(new Date());
		mac.setLoginInfoName(current.getUsername());
		int row = macMapper.insert(mac);
		if (row < 1) {
			throw new JsonMessageException("保存失败！");
		}
	}

	@Override
	@Transactional
	public void delete(Long id) {
		int row = macMapper.delete(id);
		if (row < 1) {
			throw new JsonMessageException("删除失败！");
		}

	}

	@Override
	@Transactional
	public void update(Mac mac) {
		if (mac.getLoginInfoName() != null && mac.getLoginInfoName().length() > 0) {
			throw new JsonMessageException("MAC的名称不能为空！");
		}
		Mac macByMacName = macMapper.getMacByMacName(mac.getMacName());
		if (macByMacName != null) {
			if (mac.getId() != null && !macByMacName.getId().equals(mac.getId())) {
				throw new JsonMessageException("该MAC已经存在！");
			}
		}
		int row = macMapper.update(mac);
		if (row < 1) {
			throw new JsonMessageException("修改失败！");
		}
	}

	@Transactional
	public String macBatchImport(Mac mac, MultipartFile file) {
		LoginInfo current = LoginInfoContext.getCurrent();
		if (current == null) {
			throw new JsonMessageException("账号信息过期，请重新登录！");
		}
		if (file.isEmpty()) {
			throw new JsonMessageException("文件不能为空！");
		}
		List<List<Object>> data = ImportExcelUtil.ExcelToList(file);
		List<String> macNameList = new ArrayList<>();// excel中的macName集合
		List<Mac> macList = new ArrayList<>();// 需要存储到数据库中的mac列表
		// 遍历获取excel表中的所有mac名称
		for (int i = 0; i < data.size(); i++) {
			String obj = (String) data.get(i).get(0);
			if (obj == null || "".equals(obj)) {
				continue;
			}
			macNameList.add(obj);
		}
		if (macNameList.size() < 1) {
			throw new JsonMessageException("文件不能为空");
		}
		// 查询数据库中已存在的mac的名称
		List<String> equalList = macMapper.batchQueryByMacName(macNameList);
		if (equalList != null && equalList.size() > 0) {
			Iterator<String> iterator = macNameList.iterator();
			while (iterator.hasNext()) {
				if (equalList.contains(iterator.next())) {
					iterator.remove();
				}

			}
		}
		if (macNameList.size() < 1) {
			return "所有MAC";
		}
		// 将excel中不重复的mac存入数据库中
		for (int i = 0; i < macNameList.size(); i++) {
			Mac newMac = new Mac();
			newMac.setCreateTime(new Date());
			newMac.setGroupId(mac.getGroupId());
			newMac.setMacName(macNameList.get(i));
			newMac.setLoginInfoName(current.getUsername());
			macList.add(newMac);
		}
		int row = macMapper.batchInsert(macList);
		if (row < 1) {
			throw new JsonMessageException("导入失败！");
		}
		// 当有重复的mac时，将重复的mac名称返回，如果没有则返回null
		return equalList.size() > 0 ? equalList.toString() : null;
	}

	public void export(Long groupId, ServletOutputStream out) {
		String[] titles = { "ID", "MAC名称", "分组名称", "创建账号", "创建时间" };
		XSSFWorkbook workbook = null;
		XSSFSheet hssfSheet = null;
		XSSFRow hssfRow = null;
		XSSFCellStyle hssfCellStyle = null;
		XSSFCell hssfCell = null;
		try {
			// 第一步，创建一个workbook，对应一个Excel文件
			workbook = new XSSFWorkbook();
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			hssfSheet = workbook.createSheet("sheet1");
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			hssfRow = hssfSheet.createRow(0);
			// 第四步，创建单元格，并设置值表头 设置表头居中
			hssfCellStyle = workbook.createCellStyle();
			// 居中样式
			hssfCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			for (int i = 0; i < titles.length; i++) {
				hssfCell = hssfRow.createCell(i);// 列索引从0开始
				hssfCell.setCellValue(titles[i]);// 列名1
				hssfCell.setCellStyle(hssfCellStyle);// 列居中显示
			}
			// 第五步，写入实体数据
			List<Mac> macList = macMapper.batchExportByGroupId(groupId);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			if (macList != null && macList.size() > 0) {
				for (int i = 0; i < macList.size(); i++) {
					hssfRow = hssfSheet.createRow(i + 1);
					Mac mac = macList.get(i);
					// 第六步，创建单元格，并设置值
					hssfRow.createCell(0).setCellValue(mac.getId());
					hssfRow.createCell(1).setCellValue(mac.getMacName());
					hssfRow.createCell(2).setCellValue(mac.getGroupName());
					hssfRow.createCell(3).setCellValue(mac.getLoginInfoName());
					hssfRow.createCell(4).setCellValue(sdf.format(mac.getCreateTime()));
				}
			}
			// 第七步，将文件输出到客户端浏览器
			try {
				workbook.write(out);
				out.flush();
				out.close();
			} catch (Exception e) {
				logger.error("运营管理-MAC管理-MAC批量导出-文件输出到客户端失败！",e);
			}
		} catch (Exception e) {
			logger.error("运营管理-MAC管理-MAC批量导出-批量导出失败！",e);
		}
	}

	@Transactional
	public void deleteBatch(List<Long> ids) {
		int row = macMapper.deleteBatch(ids);
		if (row < 1) {
			throw new JsonMessageException("删除失败！");
		}
	}

	@Override
	public Mac queryBymac(String mac) {
		return macMapper.queryBymac(mac);
	}
}