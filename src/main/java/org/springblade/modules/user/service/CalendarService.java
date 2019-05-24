package org.springblade.modules.user.service;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springblade.modules.user.model.Calendar;
import org.springframework.data.repository.query.Param;

import java.util.List;


@Mapper
public interface CalendarService {

	@Select("select * from calendar t where t.id = #{id}")
	Calendar findOne(String id);

	@Select(value = "select * from Calendar  " +
		"where (#{type}='' or #{type} is null or type=#{type}) and UNIX_TIMESTAMP(start)>=#{start} and UNIX_TIMESTAMP(start)<=#{end}")
	List<Calendar> findByjson(@Param("start") Long start, @Param("end") Long end, @Param("type")  String type);

	int update(@Param("calendar") Calendar calendar);

	int save(@Param("calendar") Calendar calendar);

	@Delete("delete from calendar where id = #{id}")
	int delete(String id);
}
