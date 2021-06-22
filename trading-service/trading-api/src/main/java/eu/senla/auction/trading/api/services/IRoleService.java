package eu.senla.auction.trading.api.services;

import eu.senla.auction.trading.api.dto.CreateRoleDto;
import eu.senla.auction.trading.api.dto.RoleDto;

public interface IRoleService {

    RoleDto createRole(CreateRoleDto createRoleDto);

}
