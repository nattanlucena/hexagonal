package teste.es;

import java.util.LinkedList;
import java.util.List;

import teste.es.domain.Topic;
import teste.es.rest.TopicRest;


public class TopicData {

	public static Topic buildTopic(Integer id, String name, String description) {
		Topic topic = new Topic();
		topic.setId(id);
		topic.setName(name);
		topic.setDescription(description);
		return topic;
	}

	public static String topicToJson(Topic topic) {
		return TestUtil.toJson(TopicRest.fromCore(topic));
	}

	public static Topic topic1() {
		return buildTopic(null, "Scrum", "Project Details!");
	}

	public static Topic topic2() {
		return buildTopic(null, "XP", "Extreme!");
	}

	public static Topic topic3() {
		return buildTopic(null, "RUP", "Rational!");
	}

	public static Topic topic4() {
		return buildTopic(null, "Espiral", "Espiral Project!");
	}

	public static Topic topic5() {
		return buildTopic(null, "Cascata", "Cascata Project!");
	}

	public static TopicRest topicRest1() {
		return TopicRest.fromCore(topic1());
	}

	public static TopicRest topicRest2() {
		return TopicRest.fromCore(topic2());
	}

	public static TopicRest topicRest3() {
		return TopicRest.fromCore(topic3());
	}

	public static String topicJson1() {
		TopicRest topic = topicRest1();
		topic.setId(13);
		return TestUtil.toJson(topic);
	}

	public static List<Topic> topics() {
		List<Topic> topics = new LinkedList<Topic>();
		Topic t1 = topic1();
		t1.setId(1);
		topics.add(t1);
		Topic t2 = topic2();
		t2.setId(2);
		topics.add(t2);
		Topic t3 = topic3();
		t3.setId(3);
		topics.add(t3);
		return topics;
	}

}
