package camilo.opertationquasarfire.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import camilo.opertationquasarfire.entities.UserEntity;
import java.util.List;


@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>{

    List<UserEntity> findByUsername(String username);

    @Query("Select u from UserEntity u where u.username = ?1")
    Optional<UserEntity> getName(String username);
    
}
