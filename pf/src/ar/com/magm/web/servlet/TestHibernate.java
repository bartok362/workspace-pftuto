package ar.com.magm.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import ar.com.magm.model.Cliente;

@WebServlet("/TestHibernate")
public class TestHibernate extends HttpServlet {
	private Session session;

	public TestHibernate() {
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory sf = configuration.buildSessionFactory(serviceRegistry);
		session = sf.openSession();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int idCliente = 33;
		Cliente cl = (Cliente) session.get(Cliente.class, idCliente);
		StringBuilder str = new StringBuilder();
		if (cl != null) {
			str.append("Cliente: " + cl.getCliente() + " (Id: "
					+ cl.getIdCliente() + ") - Cta Habilitada: "
					+ cl.isCuentaHabilitada() + "\n");
			str.append("\tZona: " + cl.getZona().getZona() + " (Id: "
					+ cl.getZona().getIdZona() + ")");
		} else {
			str.append("No existe el cliente con id=" + idCliente);
		}
		response.getWriter().print(str);
		response.getWriter().flush();
	}
}
