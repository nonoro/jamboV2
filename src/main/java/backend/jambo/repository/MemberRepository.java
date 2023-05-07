package backend.jambo.repository;

import backend.jambo.domain.Member;
import backend.jambo.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.status = :inUsed")
    <S extends Member> List<S> findAllRegisteredMember(@Param("inUsed") Status inUsed);
}
