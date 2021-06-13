package eu.senla.auction.api.repository;

import eu.senla.auction.entity.entities.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {

    Role findRoleByRoleName(String roleName);

}
