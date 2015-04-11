package teste.es.rest;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import teste.es.domain.Topic;
import teste.es.generic.RestUtil;

@XmlRootElement
public class TopicRest implements Serializable {

	private static final long serialVersionUID = 4431345413823505552L;

	private Integer id;
	private String name;
	private String description;

	public TopicRest() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static TopicRest fromCore(Topic topic) {
		return RestUtil.convert(topic, new TopicRest());
	}

	public Topic toCore() {
		return RestUtil.convert(this, new Topic());
	}

	@Override
	public String toString() {
		return "TopicRest [id=" + id + ", name=" + name + ", description="
				+ description + "]";
	}

}
