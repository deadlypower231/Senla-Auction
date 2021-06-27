package eu.senla.auction.trading.service.services;

import eu.senla.auction.trading.api.dto.role.CreateRoleDto;
import eu.senla.auction.trading.api.dto.role.RoleDto;
import eu.senla.auction.trading.api.mappers.RoleMapper;
import eu.senla.auction.trading.api.repository.RoleRepository;
import eu.senla.auction.trading.api.services.IRoleService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@ComponentScan("eu.senla.auction.trading.api.repository")
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
