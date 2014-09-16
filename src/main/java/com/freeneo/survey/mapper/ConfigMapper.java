package com.freeneo.survey.mapper;

import java.util.List;

import com.freeneo.survey.domain.ConfigItem;

public interface ConfigMapper {
	public List<ConfigItem> list();
	public ConfigItem select(String keyName);
	public void insert(ConfigItem configItem);
	public void update(ConfigItem configItem);
}
