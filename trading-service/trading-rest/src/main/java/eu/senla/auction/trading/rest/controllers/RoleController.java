package eu.senla.auction.trading.rest.controllers;

import eu.senla.auction.trading.api.dto.role.CreateRoleDto;
import eu.senla.auction.trading.api.dto.role.RoleDto;
import eu.senla.auction.trading.api.services.IRoleService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@ComponentScan("eu.senla.auction.trading.service.services")
public class RoleController {

    private final IRoleService roleService;

    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/create")
    public RoleDto create(@RequestBody CreateRoleDto roleDto) {
        return roleService.createRole(roleDto);
    }

}
