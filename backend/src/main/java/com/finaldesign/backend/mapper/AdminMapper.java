package com.finaldesign.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.finaldesign.backend.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
}
