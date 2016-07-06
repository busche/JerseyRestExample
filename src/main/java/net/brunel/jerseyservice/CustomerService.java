package net.brunel.jerseyservice;

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
		return sb.toString();
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

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCustomer(Customer customer) {
		cList.add(customer);
		return Response.status(201).build();
	}

	@PUT
	@Path("{id}/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCustomer(Customer customer, @PathParam("id") long id) {
		int matchIdx = 0;

		if (id != customer.getId())
			return Response.status(Response.Status.BAD_REQUEST).build();

		Optional<Customer> match = cList.stream().filter(c -> c.getId() == customer.getId()).findFirst();
		if (match.isPresent()) {
			matchIdx = cList.indexOf(match.get());
			cList.set(matchIdx, customer);
			return Response.status(Response.Status.OK).build();
		}

		return Response.status(Response.Status.NOT_FOUND).build();
	}

	@DELETE
	@Path("/remove/{id}")
	public void deleteCustomer(@PathParam("id") long id) {
		Predicate<Customer> customer = c -> c.getId() == id;

		if (!cList.removeIf(customer)) {
			throw new NotFoundException(new JsonError("Error", "Customer " + id + " not found"));
		}
	}
}