package org.springblade.modules.user.controller;


import org.springblade.modules.user.model.Calendar;
import org.springblade.modules.user.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/calendar")
public class CalendarController {

	@Autowired
	private CalendarService calendarService;

	private static final long time_F = 3600000;

	@ResponseBody
	@GetMapping(value = "/json")
	public List<Calendar> app(Long start,
							  Long end,
							  String _,
							  String type
	) {
		return calendarService.findByjson(start, end, type);
	}

	@GetMapping("/event")
	public String eventIndex(Model model, String date, String end, String action,
							 @RequestParam(value = "id", required = false)String id) {
		if (action.equals("edit")) {
			model.addAttribute("add_display", "display:none");
			Calendar calendar = calendarService.findOne(id);
			SimpleDateFormat simpleDateFormat;
			SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			if (calendar.getAllDay()) {
				simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			} else {
				simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			}
			SimpleDateFormat simpleDateFormath = new SimpleDateFormat("HH");
			SimpleDateFormat simpleDateFormatm = new SimpleDateFormat("mm");
			try {
				String $endtime = calendar.getEnd();
				model.addAttribute("endtime", $endtime);
				if ($endtime.equals("0")) {
					model.addAttribute("end_d", calendar.getStart());
					model.addAttribute("end_chk", "");
					model.addAttribute("end_display", "display:none");
				} else {
					model.addAttribute("end_d", simpleDateFormat1.format(simpleDateFormat1.parse(calendar.getEnd())));
					if (!calendar.getAllDay()) {
						model.addAttribute("end_h", simpleDateFormath.format(simpleDateFormat.parse(calendar.getEnd())));
						model.addAttribute("end_m", simpleDateFormatm.format(simpleDateFormat.parse(calendar.getEnd())));
					}
					model.addAttribute("end_display", "");
					model.addAttribute("end_chk", "checked");
				}
				model.addAttribute("id", calendar.getId());
				model.addAttribute("title", calendar.getTitle());
				model.addAttribute("starttime", calendar.getStart());
				model.addAttribute("start_d", simpleDateFormat1.format(simpleDateFormat1.parse(calendar.getStart())));
				if (!calendar.getAllDay()) {
					model.addAttribute("start_h", simpleDateFormath.format(simpleDateFormat.parse(calendar.getStart())));
					model.addAttribute("start_m", simpleDateFormatm.format(simpleDateFormat.parse(calendar.getStart())));
				}
				model.addAttribute("allday", calendar.getAllDay());

				if (calendar.getAllDay()) {
					model.addAttribute("display", "display:none");
					model.addAttribute("allday_chk", "checked");
				} else {
					model.addAttribute("display", "");
					model.addAttribute("allday_chk", "");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else if (action.equals("add")) {
			model.addAttribute("edit_display", "display:none");
			model.addAttribute("date", date);
			model.addAttribute("enddate", end);
			if (date == end) model.addAttribute("enddate", "");
			if (end == null) {
				model.addAttribute("display", "display:none");
				model.addAttribute("enddate", date);
				model.addAttribute("chk", "");
			} else {
				model.addAttribute("display", "");
				model.addAttribute("chk", "checked");
			}
			//类型

			List<Map> mapList=new ArrayList<>();
			Map map = new HashMap();
			map.put("id","1001");
			map.put("name","alibaba");
			mapList.add(map);
			model.addAttribute("order", mapList);
		}
		model.addAttribute("action", action);
		return "temp/event";
	}

	@ResponseBody
	@PostMapping(value = "/add")
	public String app(String event,
					  String startdate,
					  String s_hour,
					  String s_minute,
					  String enddate,
					  String e_hour,
					  String e_minute,
					  String isallday,
					  String action,
					  String isend,
					  String id,
					  Integer daydiff, Integer minudiff, Boolean allday, String order_id, String type) {
		/*
		 * DOED 添加
		 * */
		if (action == "add" || action.equals("add")) {
			String s_time = s_hour + ":" + s_minute + ":00";//开始时间
			String e_time = e_hour + ":" + e_minute + ":00";//结束时间
			String starttime;
			String endtime = null;
			if (isend == null) {
				isend = "";
			}
			if (isallday == null) {
				isallday = "";
			}
			if (isallday.equals("1") && isend.equals("1")) {
				starttime = startdate;
				endtime = enddate;
			} else if (isallday.equals("1") && isend.equals("")) {
				starttime = startdate;
				endtime = "0";
			} else if (isallday.equals("") && isend.equals("1")) {
				starttime = startdate + " " + s_time;
				endtime = enddate + " " + e_time;
			} else {
				starttime = startdate + " " + s_time;
			}
			String[] array = {"#360", "#f30", "#06c"};
			int ran = new Random().nextInt(2);
			String color = array[ran];
			Boolean isalldays = isallday.equals("1");
			/*
			 * TODO 判断是否可以预约这个课时
			 * */
//            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
//                    .getAuthentication()
//                    .getPrincipal();
//            userDetails.getAuthorities();
//            GrantedAuthority grantedAuthority = userDetails.getAuthorities().iterator().next();
//            if (type!=null&&grantedAuthority.getAuthority().equals("ADMIN")) {
//                type = "time";
//            }
//            else{
//                type = "reserve";
//            }
			Integer calendar = calendarService.save(new Calendar(event, starttime, endtime, isalldays, color, order_id, "123", type));
			if (calendar != null && calendar == 1) {
				return "1";
			} else {
				return "写入失败！";
			}
			/*
			 * DOED 删除
			 * */
		} else if (action == "del" || action.equals("del")) {
			try {
				calendarService.delete(id);
				return "1";
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return "删除失败！";
			}
			/*
			 * DOED 修改
			 * */
		} else if (action.equals("edit")) {

			if (id == null || id.equals("")) {
				return "事件不存在！";
			}
			if (isend == null) {
				isend = "";
			}
			if (isallday == null) {
				isallday = "";
			}
			String starttime;
			String endtime;
			if (s_hour == null || s_hour.equals("")) {
				s_hour = "00";
			}
			if (s_minute == null || s_minute.equals("")) {
				s_minute = "00";
			}
			if (e_hour == null || e_hour.equals("")) {
				e_hour = "00";
			}
			if (e_minute == null || e_minute.equals("")) {
				e_minute = "00";
			}
			String s_time = s_hour + ":" + s_minute + ":00";//开始时间
			String e_time = e_hour + ":" + e_minute + ":00";//结束时间
			Calendar calendar = calendarService.findOne(id);
			if (isallday.equals("1") && isend.equals("1")) {
				starttime = startdate;
				endtime = enddate;
			} else if (isallday.equals("1") && isend.equals("")) {
				starttime = startdate.split(" ")[0] + " " + s_time;
				endtime = "0";
			} else if (isallday.equals("") && isend.equals("1")) {
				if (calendar.getAllDay()) {
					starttime = startdate + " " + s_time;
					endtime = enddate + " " + e_time;
				} else {
					starttime = startdate.split(" ")[0] + " " + s_time;
					endtime = enddate.split(" ")[0] + " " + e_time;
				}
			} else {
				starttime = startdate.split(" ")[0] + " " + s_time;
				endtime = "0";
			}
			Boolean isalldays = isallday.equals("1");

			calendar.edit(event, starttime, endtime, isalldays);
			Integer calendars = calendarService.update(calendar);
			if (calendars != null && calendars == 1) {
				return "1";
			} else {
				return "写入失败！";
			}
		}
		/*
		 * DOED 拖拉
		 * */
		else if (action == "drag" || action.equals("drag")) {
			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
				long daydiffs = daydiff * 24 * 60 * 60 * 1000;
				long minudiffs = minudiff * 60 * 1000;
//                System.out.println("分钟:"+minudiff+"毫秒数:"+minudiffs);
//                System.out.println("天数:"+daydiffs+"毫秒数:"+minudiffs);
				Calendar calendar = calendarService.findOne(id);
				Integer calendars;
				long start;
				long end;
				if (calendar.getAllDay()) {
					start = simpleDateFormat1.parse(calendar.getStart()).getTime();
				} else {
					start = simpleDateFormat.parse(calendar.getStart()).getTime();
				}
				if (allday) {
					if (calendar.getEnd().equals("0")) {
						calendar.setStart(simpleDateFormat1.format(new Date(start + daydiffs)));
					} else {
						if (calendar.getAllDay()) {
							end = simpleDateFormat1.parse(calendar.getEnd()).getTime();
						} else {
							end = simpleDateFormat.parse(calendar.getEnd()).getTime();
						}
						calendar.setStart(simpleDateFormat1.format(new Date(start + daydiffs)));
						calendar.setEnd(simpleDateFormat1.format(new Date(end + daydiffs)));
					}
				} else {
					long $difftime = daydiffs + minudiffs;
					if (calendar.getEnd().equals("0")) {
						calendar.setStart(simpleDateFormat.format(new Date(start + $difftime)));
					} else {
						if (calendar.getAllDay()) {
							end = simpleDateFormat1.parse(calendar.getEnd()).getTime();
						} else {
							end = simpleDateFormat.parse(calendar.getEnd()).getTime();
						}
//                     当前一个是全天的时候才添加一个一小时时间
						if (calendar.getAllDay()) {
							end = end + time_F;
						}
						calendar.setStart(simpleDateFormat.format(new Date(start + $difftime)));
						calendar.setEnd(simpleDateFormat.format(new Date(end + $difftime)));
					}
				}
				calendar.setAllDay(allday);
				calendars = calendarService.update(calendar);
				if (calendars != null && calendars == 1) {
					return "1";
				} else {
					return "出错了！";
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
//        拉伸
		} else if (action.equals("resize")) {
			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				long daydiffs = daydiff * 24 * 60 * 60 * 1000;
				long minudiffs = minudiff * 60 * 1000;
				Calendar calendar = calendarService.findOne(id);
				Integer calendars;
				long $difftime = daydiffs + minudiffs;
				if (calendar.getEnd().equals("0")) {
					long start = simpleDateFormat.parse(calendar.getStart()).getTime();
//                    $sql = "update `calendar` set endtime=starttime+'$difftime' where id='$id'";
					calendar.setEnd(simpleDateFormat.format(new Date(start + $difftime)));
					calendars = calendarService.save(calendar);
				} else {
					long end = simpleDateFormat.parse(calendar.getEnd()).getTime();
//                    $sql = "update `calendar` set endtime=endtime+'$difftime' where id='$id'";
					calendar.setEnd(simpleDateFormat.format(new Date(end + $difftime)));
					calendars = calendarService.update(calendar);
				}
				if (calendars == 1) {
					return "1";
				} else {
					return "出错了！";
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		return null;
	}


}
