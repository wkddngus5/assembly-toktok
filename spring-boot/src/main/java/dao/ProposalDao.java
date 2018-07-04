package dao;

import domain.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProposalDao extends JpaRepository<Proposal, Long> {
}
