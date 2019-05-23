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
package org.springblade.modules.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.node.INode;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.entity.Role;
import org.springblade.modules.system.service.IRoleService;
import org.springblade.modules.system.vo.RoleVO;
import org.springblade.modules.system.wrapper.RoleWrapper;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 *
 * @author Chill
 */
@ApiIgnore
@RestController
@AllArgsConstructor
@RequestMapping("/blade-system/role")
@Api(value = "角色", tags = "角色")
public class RoleController extends BladeController {

	private IRoleService roleService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入role", position = 1)
	public R<RoleVO> detail(Role role) {
		Role detail = roleService.getOne(Condition.getQueryWrapper(role));
		RoleWrapper roleWrapper = new RoleWrapper(roleService);
		return R.data(roleWrapper.entityVO(detail));
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "roleName", value = "参数名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "roleAlias", value = "角色别名", paramType = "query", dataType = "string")
	})
	@ApiOperation(value = "列表", notes = "传入role", position = 2)
	public R<List<INode>> list(@ApiIgnore @RequestParam Map<String, Object> role, BladeUser bladeUser) {
		QueryWrapper<Role> queryWrapper = Condition.getQueryWrapper(role, Role.class);
		List<Role> list = roleService.list((!bladeUser.getTenantCode().equals(BladeConstant.ADMIN_TENANT_CODE)) ? queryWrapper.lambda().eq(Role::getTenantCode, bladeUser.getTenantCode()) : queryWrapper);
		RoleWrapper roleWrapper = new RoleWrapper(roleService);
		return R.data(roleWrapper.listNodeVO(list));
	}

	/**
	 * 获取角色树形结构
	 */
	@GetMapping("/tree")
	@ApiOperation(value = "树形结构", notes = "树形结构", position = 3)
	public R<List<RoleVO>> tree(String tenantCode, BladeUser bladeUser) {
		List<RoleVO> tree = roleService.tree(Func.toStr(tenantCode, bladeUser.getTenantCode()));
		return R.data(tree);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入role", position = 4)
	public R submit(@Valid @RequestBody Role role, BladeUser user) {
		if (Func.isEmpty(role.getId())) {
			role.setTenantCode(user.getTenantCode());
		}
		return R.status(roleService.saveOrUpdate(role));
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "传入ids", position = 5)
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(roleService.removeByIds(Func.toIntList(ids)));
	}

	/**
	 * 设置菜单权限
	 *
	 * @param roleIds
	 * @param menuIds
	 * @return
	 */
	@PostMapping("/grant")
	@ApiOperation(value = "权限设置", notes = "传入roleId集合以及menuId集合", position = 6)
	public R grant(@ApiParam(value = "roleId集合", required = true) @RequestParam String roleIds,
				   @ApiParam(value = "menuId集合", required = true) @RequestParam String menuIds) {
		boolean temp = roleService.grant(Func.toIntList(roleIds), Func.toIntList(menuIds));
		return R.status(temp);
	}

}
