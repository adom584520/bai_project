package com.pbtd.manager.service;

import java.util.List;

import javax.servlet.ServletOutputStream;

import org.springframework.web.multipart.MultipartFile;

import com.pbtd.manager.domain.Mac;
import com.pbtd.manager.query.MacQueryObject;
import com.pbtd.manager.util.PageResult;

public interface MacService {
	/**
	 * 高级查询加分页
	 * @param qo
	 * @return
	 */
	PageResult queryList(MacQueryObject qo);

	/**
	 * 添加mac
	 * @param mac
	 */
	void insert(Mac mac);

	/**
	 * 删除mac
	 * @param id
	 */
	void delete(Long id);

	/**
	 * 修改mac
	 * @param id
	 */
	void update(Mac mac);

	/**
	 * 批量导入，返回值为未成功导入的mac名称
	 * @param mac
	 * @param file
	 * @return
	 */
	String macBatchImport(Mac mac,MultipartFile file);

	/**
	 * 导出分组下的所有MAC
	 * @param groupId
	 * @param out
	 */
	void export(Long groupId,ServletOutputStream out);

	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteBatch(List<Long> ids);
	
	Mac queryBymac(String mac);
}
