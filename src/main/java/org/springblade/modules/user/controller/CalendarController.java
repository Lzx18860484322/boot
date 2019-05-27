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
package org.springblade.modules.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.user.entity.Calendar;
import org.springblade.modules.user.service.ICalendarService;
import org.springblade.modules.user.vo.CalendarVO;
import org.springblade.modules.user.wrapper.CalendarWrapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 *  控制器
 *
 * @author Blade
 * @since 2019-05-25
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user/calendar")
@Api(value = "", tags = "接口")
public class CalendarController extends BladeController {

	private ICalendarService calendarService;

//	private IDictClient dictClient;

	@ResponseBody
	@GetMapping(value = "/json")
	public List<Calendar> app(String start,
							  String end
	) throws ParseException {
		System.out.println(start.length());
		String startD=start.substring(1,start.length()-1);
		String endD=end.substring(1,end.length()-1);
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		return calendarService.findByjson(dateFormat.parse(startD), dateFormat.parse(endD));
	}


	/**
	* 详情
	*/
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入calendar", position = 1)
	public R<CalendarVO> detail(Calendar calendar) {
		Calendar detail = calendarService.getOne(Condition.getQueryWrapper(calendar));
		CalendarWrapper calendarWrapper = new CalendarWrapper();
		return R.data(calendarWrapper.entityVO(detail));
	}



	/**
	* 分页 
	*/
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入calendar", position = 2)
	public R<IPage<CalendarVO>> list(Calendar calendar, Query query) {
		IPage<Calendar> pages = calendarService.page(Condition.getPage(query), Condition.getQueryWrapper(calendar));
		CalendarWrapper calendarWrapper = new CalendarWrapper();
		return R.data(calendarWrapper.pageVO(pages));
	}

	/**
	* 自定义分页 
	*/
	@GetMapping("/page")
	@ApiOperation(value = "分页", notes = "传入calendar", position = 3)
	public R<IPage<CalendarVO>> page(CalendarVO calendar, Query query) {
		IPage<CalendarVO> pages = calendarService.selectCalendarPage(Condition.getPage(query), calendar);
		return R.data(pages);
	}

	/**
	* 新增 
	*/
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入calendar", position = 4)
	public R save(@Valid @RequestBody Calendar calendar) {
		return R.status(calendarService.save(calendar));
	}

	/**
	* 修改 
	*/
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入calendar", position = 5)
	public R update(@Valid @RequestBody Calendar calendar) {
		return R.status(calendarService.updateById(calendar));
	}

	/**
	 * 修改
	 */
	@PostMapping("/event")
	@ApiOperation(value = "修改", notes = "传入calendar", position = 5)
	public R updateEvents(@Valid @RequestBody JSONObject jsonObject) throws ParseException {
		Calendar calendar=calendarService.getById(jsonObject.getString("id"));
		String start=jsonObject.getString("start");
		String end=jsonObject.getString("end");
        SimpleDateFormat simpleDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Boolean allDay=jsonObject.getBoolean("allDay");

		switch (Update.valueOf(jsonObject.getString("type"))) {
			case DROP:
			{
				if(allDay){
					start=start.split(" ")[0];
					end=start;
					simpleDate=new SimpleDateFormat("yyyy-MM-dd");
				}
				else if(calendar.getAllDay()){
//					start=start.split(" ")[0];
					end=simpleDate.format(new Date(simpleDate.parse(start).getTime()+60*60*1000));
				}
				calendar.setAllDay(allDay);
                calendar.setStart(simpleDate.parse(start));
				calendar.setEnd(simpleDate.parse(end));
				break;
			}
			case RESIZE:
			{
				calendar.setAllDay(allDay);
				calendar.setEnd(simpleDate.parse(end));
				break;
			}
		}
		return R.status(calendarService.updateById(calendar));
	}

	enum Update{
		EDIT,
		DROP,
		RESIZE
	}




	/**
	* 新增或修改 
	*/
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入calendar", position = 6)
	public R submit(@Valid @RequestBody Calendar calendar) {
		String[] array = {"#360", "#f30", "#06c"};
		int ran = new Random().nextInt(2);
		String color = array[ran];
		calendar.setColor(color);
		return R.status(calendarService.saveOrUpdate(calendar));
	}

	/**
	* 删除 
	*/
	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "传入ids", position = 7)
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(calendarService.removeByIds(Func.toIntList(ids)));
	}

//
//	public static void main(String[] args) {
//		System.out.println("2019-04-28");
//	}


}
