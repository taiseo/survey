package com.freeneo.survey.mapperMms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.freeneo.survey.domain.Mms;
import com.freeneo.survey.domain.User;

public interface MmsMapper {
    public void insertAll(@Param("mmsList") List<Mms> mmsList);
    
}
