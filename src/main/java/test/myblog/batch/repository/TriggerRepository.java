package test.myblog.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import test.myblog.batch.model.Trigger;

public interface TriggerRepository extends JpaRepository<Trigger, Integer> {

}
