package net.brunel.jerseyservice;

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/customers")
public class CustomerService {

	private final CopyOnWriteArrayList<Customer> cList = CustomerList.getInstance();

	@GET
	@Path("/all")
	@Produces(MediaType.TEXT_PLAIN)
	public String getAllCustomers() {
		return "---Customer List---\n" + cList.stream().map(c -> c.toString()).collect(Collectors.joining("\n"));
	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer[] getAllCustomersAsJson() {
		return cList.toArray(new Customer[0]);
	}                                  

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String getIndex() {
		final StringBuffer sb = new StringBuffer("<h1>Root Object</h1>"); 
		cList.stream().forEach(new Consumer<Customer>() {

			@Override
			public void accept(Customer c) {
				sb.append("<a href=\"" + c.getId() + "\">" + c.getLastName() + "</a><br />");
				
			}
		});
		return  sb.toString();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCustomer(@PathParam("id") long id) {
		Optional<Customer> match = cList.stream().filter(c -> c.getId() == id).findFirst();
		if (match.isPresent()) {
			return "---Customer---\n" + match.get().toString();
		}
		
		return "Customer not found";

	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomerAsJson(@PathParam("id") long id) {
		Optional<Customer> match = cList.stream().filter(c -> c.getId() == id).findFirst();
		if (match.isPresent()) {
			return match.get();
		}
		throw new NotFoundException(new JsonError("Error", "Customer " + id + " not found"));

	}
}