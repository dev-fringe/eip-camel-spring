package dev.fringe.eip;

import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Route;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@EnableAutoConfiguration
public class App implements InitializingBean {

	@Autowired
	private CamelContext camelContext;

	public static void main(String[] args) throws InterruptedException {
		new AnnotationConfigApplicationContext(App.class).start();
		Thread.sleep(40000);
	}

	public void afterPropertiesSet() throws Exception {
		camelContext.addRoutes(new RouteBuilder() {
			public void configure() throws Exception {
				from("file:C:/inputFolder?noop=true").process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						System.out.println(exchange.getIn());
					}
				}).to("file:C:/outputFolder");
			}
		});
		camelContext.start();
		List<Route> listRoute = camelContext.getRoutes();
		for (Route r : listRoute) {
			System.out.println("camelContext getId() = " + r.getId());
		}
	}
}