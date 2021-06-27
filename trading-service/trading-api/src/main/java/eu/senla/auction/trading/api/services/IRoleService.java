package eu.senla.auction.trading.api.services;

import eu.senla.auction.trading.api.dto.role.CreateRoleDto;
import eu.senla.auction.trading.api.dto.role.RoleDto;

public interface IRoleService {

    RoleDto createRole(CreateRoleDto createRoleDto);

}
