package eu.senla.auction.api.services;

import eu.senla.auction.api.dto.CreateRoleDto;
import eu.senla.auction.api.dto.RoleDto;

public interface IRoleService {

    RoleDto createRole(CreateRoleDto createRoleDto);

}
