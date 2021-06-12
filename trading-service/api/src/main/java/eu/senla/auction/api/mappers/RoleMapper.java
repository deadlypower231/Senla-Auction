package eu.senla.auction.api.mappers;

import eu.senla.auction.api.dto.CreateRoleDto;
import eu.senla.auction.api.dto.RoleDto;
import eu.senla.auction.entity.entities.Role;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RoleMapper {

    public Role mapRole(RoleDto source){
        return Role.builder()
                .id(source.getId())
                .roleName(source.getRoleName())
                .build();
    }

    public RoleDto mapRoleDto(Role source){
        return RoleDto.builder()
                .id(source.getId())
                .roleName(source.getRoleName())
                .build();
    }

    public Role mapCreateRole(CreateRoleDto source){
        return Role.builder()
                .roleName(source.getRoleName())
                .build();
    }

}
