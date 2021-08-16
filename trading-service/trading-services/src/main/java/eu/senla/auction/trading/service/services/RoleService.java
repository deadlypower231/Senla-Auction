package eu.senla.auction.trading.service.services;

import eu.senla.auction.trading.api.dto.role.CreateRoleDto;
import eu.senla.auction.trading.api.dto.role.RoleDto;
import eu.senla.auction.trading.api.mappers.RoleMapper;
import eu.senla.auction.trading.api.repository.RoleRepository;
import eu.senla.auction.trading.api.services.IRoleService;
import eu.senla.auction.trading.entity.entities.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleDto createRole(CreateRoleDto createRoleDto) throws DuplicateKeyException{
        Role role = roleRepository.findRoleByRoleName(createRoleDto.getRoleName());
        if (role != null){
            log.info("This role: {} exist!", createRoleDto.getRoleName());
            throw new DuplicateKeyException(String.format("This role: %s exist!", createRoleDto.getRoleName()));
        }
        return RoleMapper.mapRoleDto(roleRepository.save(RoleMapper.mapCreateRole(createRoleDto)));
    }
}
