package io.aadesh.userbooks;

import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@Repository
public interface UserbooksRepo extends CassandraRepository<UserBooks, UserbooksPrimaryKey> {

}
