package eu.senla.auction.rest.controllers;

import eu.senla.auction.api.dto.CreateRoleDto;
import eu.senla.auction.api.dto.RoleDto;
import eu.senla.auction.api.mappers.RoleMapper;
import eu.senla.auction.api.services.IRoleService;
import eu.senla.auction.entity.entities.Role;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@ComponentScan("eu.senla.auction.service.services")
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
