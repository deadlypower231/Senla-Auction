package eu.senla.auction.trading.rest.controllers;

import eu.senla.auction.trading.api.dto.role.CreateRoleDto;
import eu.senla.auction.trading.api.dto.role.RoleDto;
import eu.senla.auction.trading.api.services.IRoleService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final IRoleService roleService;

    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/create")
    public RoleDto create(@RequestBody CreateRoleDto roleDto) throws DuplicateKeyException {
        try {
            return roleService.createRole(roleDto);
        } catch (DuplicateKeyException e) {
            throw new DuplicateKeyException(Objects.requireNonNull(e.getMessage()));
        }
    }

}
