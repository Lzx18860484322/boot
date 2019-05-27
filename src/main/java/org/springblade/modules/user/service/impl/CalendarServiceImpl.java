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
package org.springblade.modules.user.service.impl;

import org.springblade.modules.user.entity.Calendar;
import org.springblade.modules.user.vo.CalendarVO;
import org.springblade.modules.user.mapper.CalendarMapper;
import org.springblade.modules.user.service.ICalendarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2019-05-25
 */
@Service
public class CalendarServiceImpl extends ServiceImpl<CalendarMapper, Calendar> implements ICalendarService {

	@Override
	public IPage<CalendarVO> selectCalendarPage(IPage<CalendarVO> page, CalendarVO calendar) {
		return page.setRecords(baseMapper.selectCalendarPage(page, calendar));
	}

	@Override
	public List<Calendar> findByjson(Date start, Date end) {
		return baseMapper.findByjson(start,end);
	}

	@Override
	public Calendar getById(Serializable id) {
		return baseMapper.getById(id);
	}
}
