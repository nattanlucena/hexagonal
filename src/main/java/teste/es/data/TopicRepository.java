package teste.es.data;


import org.springframework.data.jpa.repository.JpaRepository;

import teste.es.domain.Topic;

public interface TopicRepository extends JpaRepository<Topic, Integer> {

}
