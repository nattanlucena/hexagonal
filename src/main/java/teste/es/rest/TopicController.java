package teste.es.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import teste.es.domain.Topic;
import teste.es.domain.TopicService;
import teste.es.generic.event.CreateRequestEvent;
import teste.es.generic.event.CreateResponseEvent;
import teste.es.generic.event.DeleteRequestEvent;
import teste.es.generic.event.DeleteResponseEvent;
import teste.es.generic.event.ListRequestEvent;
import teste.es.generic.event.ListResponseEvent;
import teste.es.generic.event.UpdateRequestEvent;
import teste.es.generic.event.UpdateResponseEvent;
import teste.es.generic.event.ViewRequestEvent;
import teste.es.generic.event.ViewResponseEvent;

@Controller
@RequestMapping("/topics")
public class TopicController {

	@Autowired
	private TopicService service;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<TopicRest> listTopics() {

		ListResponseEvent<Topic> response = service
				.request(new ListRequestEvent<Topic>());

		List<TopicRest> topics = new ArrayList<TopicRest>();

		for (Topic topic : response.getObjects()) {
			topics.add(TopicRest.fromCore(topic));
		}

		return topics;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<TopicRest> createTopic(
			@RequestBody TopicRest topicRest, UriComponentsBuilder builder) {

		CreateRequestEvent<Topic> request = new CreateRequestEvent<Topic>(
				topicRest.toCore());

		CreateResponseEvent<Topic> response;

		try {
			response = service.request(request);
		} catch (ConstraintViolationException exception) {
			Logger.getLogger(TopicController.class.getSimpleName()).
			log(Level.ALL, exception.getMessage(), exception);
			return new ResponseEntity<TopicRest>(HttpStatus.BAD_REQUEST);
		}

		TopicRest createdTopicRest = TopicRest.fromCore(response.getObject());			

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/topic/id")
				.buildAndExpand(createdTopicRest.getId().toString()).toUri());

		return new ResponseEntity<TopicRest>(createdTopicRest, headers,
				HttpStatus.CREATED);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<TopicRest> viewTopic(@PathVariable Integer id) {

		ViewResponseEvent<Topic> response = service
				.request(new ViewRequestEvent<Topic>(id));

		if (!response.isEntityFound()) {
			return new ResponseEntity<TopicRest>(HttpStatus.NOT_FOUND);
		}

		TopicRest topicRest = TopicRest.fromCore(response.getObject());

		return new ResponseEntity<TopicRest>(topicRest, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<TopicRest> deleteTopic(@PathVariable Integer id) {

		DeleteResponseEvent<Topic> response = service
				.request(new DeleteRequestEvent(id));

		if (!response.isEntityFound()) {
			return new ResponseEntity<TopicRest>(HttpStatus.NOT_FOUND);
		}

		TopicRest topicRest = TopicRest.fromCore(response.getObject());

		if (!response.isOperationCompleted()) {
			return new ResponseEntity<TopicRest>(topicRest, HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity<TopicRest>(topicRest, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<TopicRest> updateTopic(@PathVariable Integer id,
			@RequestBody TopicRest topicRest) {
		
		UpdateResponseEvent<Topic> response;
		try {
			response = service.request(new UpdateRequestEvent<Topic>(id,
					topicRest.toCore()));
		} catch (ConstraintViolationException exception) {
			Logger.getLogger(TopicController.class.getSimpleName()).
			log(Level.ALL, exception.getMessage(), exception);
			return new ResponseEntity<TopicRest>(HttpStatus.BAD_REQUEST);
		}

		return generateReturn(topicRest, response);
	}

	private ResponseEntity<TopicRest> generateReturn(TopicRest topicRest,
			UpdateResponseEvent<Topic> response) {
		if (!response.isEntityFound()) {
			return new ResponseEntity<TopicRest>(HttpStatus.NOT_FOUND);
		}

		TopicRest updatedTopicRest = TopicRest.fromCore(response.getObject());

		if (!response.isOperationCompleted()) {
			return new ResponseEntity<TopicRest>(topicRest,
					HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity<TopicRest>(updatedTopicRest, HttpStatus.OK);
	}
}
