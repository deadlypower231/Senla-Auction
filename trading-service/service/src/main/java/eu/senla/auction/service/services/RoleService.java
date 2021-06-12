package eu.senla.auction.service.services;

import eu.senla.auction.api.dto.CreateRoleDto;
import eu.senla.auction.api.dto.RoleDto;
import eu.senla.auction.api.mappers.RoleMapper;
import eu.senla.auction.api.repository.RoleRepository;
import eu.senla.auction.api.services.IRoleService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@ComponentScan("eu.senla.auction.api.repository")
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public RoleDto createRole(CreateRoleDto createRoleDto) {
        return RoleMapper.mapRoleDto(roleRepository.save(RoleMapper.mapCreateRole(createRoleDto)));
    }
}
