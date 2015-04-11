package teste.es.generic;

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


public interface GenericService<T> {

	public CreateResponseEvent<T> request(CreateRequestEvent<T> request);
	
	public ListResponseEvent<T> request(ListRequestEvent<T> request);

	public UpdateResponseEvent<T> request(UpdateRequestEvent<T> request);

	public ViewResponseEvent<T> request(ViewRequestEvent<T> request);

	public DeleteResponseEvent<T> request(DeleteRequestEvent request);

}
