package eu.senla.auction.trading.api.repository;

import eu.senla.auction.trading.entity.entities.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {

    Role findRoleByRoleName(String roleName);

}
