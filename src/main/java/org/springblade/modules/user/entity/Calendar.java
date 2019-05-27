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
package org.springblade.modules.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实体类
 *
 * @author Blade
 * @since 2019-05-25
 */
@Data
@TableName("user_calendar")
@ApiModel(value = "Calendar对象", description = "Calendar对象")
public class Calendar implements Serializable {

    private static final long serialVersionUID = 1L;

  private String id;
  private String title;
  private Date start;
  private Date end;
  private Boolean allDay;
  private String color;
  private String url;

  //前端私有
//  private String start_d;
//  private String start_s;
//  private String end_d;
//  private String end_s;
//  private Boolean allday;


}
