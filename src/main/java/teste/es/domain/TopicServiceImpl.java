package teste.es.domain;


import org.springframework.stereotype.Service;

import teste.es.data.TopicRepository;
import teste.es.generic.GenericServiceImpl;

@Service
public class TopicServiceImpl extends
		GenericServiceImpl<Topic, TopicRepository> implements TopicService {

}
