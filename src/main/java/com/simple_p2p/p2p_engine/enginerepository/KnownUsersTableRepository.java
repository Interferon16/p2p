package com.simple_p2p.p2p_engine.enginerepository;

import com.simple_p2p.entity.KnownUsers;
import com.simple_p2p.entity.MessageTable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface KnownUsersTableRepository extends CrudRepository<KnownUsers, Integer> {
    //List<KnownUsers>findByUserHashExistsOrderByUserHash();
}
