package org.springblade.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springblade.modules.system.entity.Dict;
import org.springblade.modules.user.entity.Calendar;
import org.springframework.data.repository.query.Param;

import java.util.List;



public interface CalendarService extends IService<Calendar> {


//	@Select("select * from calendar t where t.id = #{id}")
//	Calendar findOne(String id);
//
//	@Select(value = "select * from Calendar  " +
//		"where (#{type}='' or #{type} is null or type=#{type}) and UNIX_TIMESTAMP(start)>=#{start} and UNIX_TIMESTAMP(start)<=#{end}")
//	List<Calendar> findByjson(@Param("start") Long start, @Param("end") Long end, @Param("type")  String type);
//
//	int update(@Param("calendar") Calendar calendar);
//
//	int save(@Param("calendar") Calendar calendar);
//
//	@Delete("delete from calendar where id = #{id}")
//	int delete(String id);
}
