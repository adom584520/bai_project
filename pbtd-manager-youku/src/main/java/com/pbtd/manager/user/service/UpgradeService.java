package com.pbtd.manager.user.service;

import com.pbtd.manager.user.domain.Upgrade;
import com.pbtd.manager.user.page.UpgradeQueryObject;
import com.pbtd.manager.util.PageResult;

public interface UpgradeService {
	/**
	 * 高级查询加分页
	 * @param qo
	 * @return
	 */
	PageResult queryList(UpgradeQueryObject qo);

	/**
	 * 添加upgrade
	 * @param upgrade
	 */
	void insert(Upgrade upgrade);

	/**
	 * 删除upgrade
	 * @param id
	 */
	void delete(Long id);

	/**
	 * 修改upgrade
	 * @param id
	 */
	void update(Upgrade upgrade);
}
