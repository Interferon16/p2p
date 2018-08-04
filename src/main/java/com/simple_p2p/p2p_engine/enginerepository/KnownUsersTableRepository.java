package com.simple_p2p.p2p_engine.enginerepository;

import com.simple_p2p.entity.KnownUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface KnownUsersTableRepository extends JpaRepository<KnownUsers, String> {
    /*KnownUsers getKnownUsersByUserHash(String userHash);*/
}
