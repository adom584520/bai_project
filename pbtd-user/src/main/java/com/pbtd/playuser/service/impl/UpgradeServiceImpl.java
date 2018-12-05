package com.pbtd.playuser.service.impl;

import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.playuser.domain.ActivitiesBaseInfo;
import com.pbtd.playuser.domain.ActivitiesUpgrade;
import com.pbtd.playuser.dto.UpgradeDTO;
import com.pbtd.playuser.mapper.ActivitiesBaseInfoMapper;
import com.pbtd.playuser.mapper.ActivitiesUpgradeMapper;
import com.pbtd.playuser.mapper.UpgradeMapper;
import com.pbtd.playuser.service.UpgradeService;
import com.pbtd.playuser.util.UpgradeConstant;

@Service
public class UpgradeServiceImpl implements UpgradeService {
	@Autowired
	private UpgradeMapper upgradeMapper;
	@Autowired
	private ActivitiesBaseInfoMapper activitiesBaseInfoMapper;
	@Autowired
	private ActivitiesUpgradeMapper activitiesUpgradeMapper;

	@Override
	public UpgradeDTO queryValidateVersion(Integer type, String version, String activity, String userId) {
		UpgradeDTO upgradeDTO = upgradeMapper.queryByTypeStatus(type, UpgradeConstant.UPGRADE_STATUS_NEW);
		if (upgradeDTO != null) {
			if (upgradeDTO.getVersion().equals(version)) {
				if (activity != null && activity.length() > 0) {
					if (userId != null && userId.length() > 0) {
						HashMap<String, Object> params = new HashMap<>();
						params.put("activeCode", activity);
						ActivitiesBaseInfo activityInfo = activitiesBaseInfoMapper.queryOne(params);
						if (activityInfo != null) {
							upgradeDTO.setActivity(UpgradeDTO.ACTIVITY_EXIST);
							ActivitiesUpgrade upgradeInfo = activitiesUpgradeMapper.queryOneByUserId(userId);
							if (upgradeInfo == null) {
								upgradeDTO.setActivityRecord(UpgradeDTO.NOT_ACTIVITYRECORD);
							} else {
								upgradeDTO.setActivityRecord(UpgradeDTO.ONE_ACTIVITYRECORD);
							}
						} else {
							upgradeDTO.setActivity(UpgradeDTO.ACTIVITY_NOT_EXIST);
						}
					} else {
						upgradeDTO.setActivity(UpgradeDTO.ACTIVITY_EXIST);
						upgradeDTO.setActivityRecord(UpgradeDTO.NOT_ACTIVITYRECORD);
					}
				} else {
					upgradeDTO.setActivityRecord(UpgradeDTO.NOT_ACTIVITYRECORD);
					upgradeDTO.setActivity(UpgradeDTO.ACTIVITY_NOT_EXIST);
				}
			}
			return upgradeDTO;
		}
		return null;
	}

	@Override
	@Transactional
	public UpgradeDTO getFluxByUpgrade(String userId, String version, String activity, Integer type) {
		UpgradeDTO dto = upgradeMapper.queryByTypeStatus(type, UpgradeConstant.UPGRADE_STATUS_NEW);
		if (dto != null) {
			if (activity != null && activity.length() > 0) {
				if (!(userId != null && userId.length() > 0)) {
					dto.setActivity(UpgradeDTO.ACTIVITY_EXIST);
					dto.setActivity(UpgradeDTO.NOT_ACTIVITYRECORD);
					return dto;
				}
				HashMap<String, Object> params = new HashMap<>();
				params.put("activeCode", activity);
				ActivitiesBaseInfo activityInfo = activitiesBaseInfoMapper.queryOne(params);
				if (activityInfo != null) {
					dto.setActivity(UpgradeDTO.ACTIVITY_EXIST);
					ActivitiesUpgrade upgradeInfo = activitiesUpgradeMapper.queryOneByUserId(userId);
					if (upgradeInfo != null) {
						dto.setActivityRecord(UpgradeDTO.ONE_ACTIVITYRECORD);
					} else {
						ActivitiesUpgrade upgrade = new ActivitiesUpgrade();
						upgrade.setActiveCode(activity);
						upgrade.setCreateTime(new Date());
						upgrade.setFlux(10);
						upgrade.setUserId(userId);
						upgrade.setVersion(version);
						activitiesUpgradeMapper.insert(upgrade);
						dto.setActivityRecord(UpgradeDTO.NOT_ACTIVITYRECORD);
					}
				}
			}
		} else {
			dto = new UpgradeDTO();
			dto.setActivityRecord(UpgradeDTO.NOT_ACTIVITYRECORD);
			dto.setActivity(UpgradeDTO.ACTIVITY_NOT_EXIST);
			dto.setDownload("");
			dto.setGradeName("");
			dto.setType(-1);
			dto.setVersion("");
			dto.setVersionInfo("");
		}
		return dto;
	}
}