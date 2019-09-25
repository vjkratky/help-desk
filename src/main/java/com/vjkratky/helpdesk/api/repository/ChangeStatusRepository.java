package com.vjkratky.helpdesk.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.vjkratky.helpdesk.api.entity.ChangeStatus;

public interface ChangeStatusRepository extends MongoRepository<ChangeStatus, String> {

	@Query(value = "{ 'ticket.id' : ?0 }")
	Iterable<ChangeStatus> findByTicketIdOrderByDateChangeStatusDesc(String tickedId);
	
	//Descobrir pq da erro assim?!
	//Iterable<ChangeStatus> findByTicketId(String tickedId);
	Iterable<ChangeStatus> findByUserChangeId(String tickedId);
}
