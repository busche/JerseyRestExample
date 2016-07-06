package net.brunel.jerseyservice;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

/**
 * Main class.
 *
 */
public class Main {
    
	private static final String PATH = "myJersey";
    
	// Base URI the Grizzly HTTP server will listen on
	private static final Optional<String> HOST;
	private static final Optional<String> PORT;
	
	static {
		HOST = Optional.ofNullable(System.getenv("HOST"));
		PORT = Optional.ofNullable(System.getenv("PORT"));
	}
	
    public static final String BASE_URI = "http://" + HOST.orElse("localhost") + ":" + PORT.orElse("8080") + "/" + PATH + "/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in net.brunel.jersey-service package
        final ResourceConfig rc = new ResourceConfig().packages("net.brunel.jerseyservice");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args the command line arguments
     * @throws IOException if anything goes wrong
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}

