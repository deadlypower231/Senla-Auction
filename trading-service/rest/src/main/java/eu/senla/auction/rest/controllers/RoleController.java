package eu.senla.auction.rest.controllers;

import eu.senla.auction.api.dto.CreateRoleDto;
import eu.senla.auction.api.mappers.RoleMapper;
import eu.senla.auction.api.repository.RoleRepository;
import eu.senla.auction.entity.entities.Role;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@ComponentScan("eu.senla.auction.api.repository")
public class RoleController {

    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostMapping("/create")
    public Role create(@RequestBody CreateRoleDto roleDto) {
        return roleRepository.save(RoleMapper.mapCreateRole(roleDto));
    }

}
