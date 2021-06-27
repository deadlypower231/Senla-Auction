package eu.senla.auction.trading.api.mappers;

import eu.senla.auction.trading.api.dto.role.CreateRoleDto;
import eu.senla.auction.trading.api.dto.role.RoleDto;
import eu.senla.auction.trading.entity.entities.Role;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RoleMapper {

    public RoleDto mapRoleDto(Role source) {
        return RoleDto.builder()
                .id(source.getId().toHexString())
                .roleName(source.getRoleName())
                .build();
    }

    public Role mapCreateRole(CreateRoleDto source) {
        return Role.builder()
                .roleName(source.getRoleName())
                .build();
    }

}
