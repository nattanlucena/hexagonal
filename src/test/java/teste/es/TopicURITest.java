package teste.es;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
import teste.es.rest.TopicController;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TopicURITest {

	MockMvc mockMvc;

	@InjectMocks
	TopicController controller;

	@Mock
	TopicService service;

	private Topic topic = TopicData.topic1();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = standaloneSetup(controller).build();

		topic.setId(1);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void test1ListTopics() throws Exception {

		when(service.request(any(ListRequestEvent.class))).thenReturn(
				new ListResponseEvent<Topic>(TopicData.topics()));

		mockMvc.perform(
				MockMvcRequestBuilders.get("/topics").accept(
						MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].name").value("Scrum"))
				.andExpect(
						jsonPath("$[0].description").value("Project Details!"))
				.andExpect(jsonPath("$[1].id").value(2))
				.andExpect(jsonPath("$[1].name").value("XP"))
				.andExpect(jsonPath("$[1].description").value("Extreme!"))
				.andExpect(jsonPath("$[2].id").value(3))
				.andExpect(jsonPath("$[2].name").value("RUP"))
				.andExpect(jsonPath("$[2].description").value("Rational!"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test2CreateTopic() throws Exception {

		when(service.request(any(CreateRequestEvent.class))).thenReturn(
				new CreateResponseEvent<Topic>(topic));

		mockMvc.perform(
				MockMvcRequestBuilders.post("/topics")
						.content(TopicData.topicJson1())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Scrum"))
				.andExpect(jsonPath("$.description").value("Project Details!"));
		;
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test3UpdateTopic() throws Exception {

		when(service.request(any(UpdateRequestEvent.class))).thenReturn(
				new UpdateResponseEvent<Topic>(topic));

		String uri = "/topics/"
				+ topic.getId();

		topic.setName("Name Changed!");
		topic.setDescription("Description Changed!");

		mockMvc.perform(
				MockMvcRequestBuilders.put(uri)
						.content(TopicData.topicToJson(topic))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Name Changed!"))
				.andExpect(
						jsonPath("$.description").value("Description Changed!"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test4ViewTopic() throws Exception {

		when(service.request(any(ViewRequestEvent.class))).thenReturn(
				new ViewResponseEvent<Topic>(topic.getId(), topic));

		String uri = "/topics/"
				+ topic.getId();
		mockMvc.perform(
				MockMvcRequestBuilders.get(uri).accept(
						MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Scrum"))
				.andExpect(jsonPath("$.description").value("Project Details!"));
	}

	@Test
	public void test5DeleteTopic() throws Exception {

		when(service.request(any(DeleteRequestEvent.class))).thenReturn(
				new DeleteResponseEvent<Topic>(topic));

		String uri = "/topics/"
				+ topic.getId();
		mockMvc.perform(
				MockMvcRequestBuilders.delete(uri).accept(
						MediaType.APPLICATION_JSON)).andExpect(
				status().isNoContent());
	}

}
