package hr.brocom.generic.ping;

import hr.brocom.generic.repository.AbstractRepository;
import hr.brocom.generic.repository.AbstractRepositoryImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PingDao extends AbstractRepositoryImpl<Ping, JpaRepository<Ping, Long>> implements AbstractRepository<Ping> {

    public PingDao() {
        super(Ping.class);
    }
}
