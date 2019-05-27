/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.modules.user.service;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springblade.modules.user.entity.Calendar;
import org.springblade.modules.user.vo.CalendarVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *  服务类
 *
 * @author Blade
 * @since 2019-05-25
 */
public interface ICalendarService extends IService<Calendar> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param calendar
	 * @return
	 */
	IPage<CalendarVO> selectCalendarPage(IPage<CalendarVO> page, CalendarVO calendar);

	List<Calendar> findByjson(Date start, Date end);

	@Override
	Calendar getById(Serializable id);
}
