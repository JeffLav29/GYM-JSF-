package jl.zona_fit;

import jl.zona_fit.modelo.Cliente;
import jl.zona_fit.servicio.ClienteServicio;
import jl.zona_fit.servicio.IClienteServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

//@SpringBootApplication
public class ZonaFitApplication implements CommandLineRunner {

	@Autowired
	private IClienteServicio clienteServicio;

	private static final Logger logger =
			LoggerFactory.getLogger(ZonaFitApplication.class);

	String nl = System.lineSeparator();

	public static void main(String[] args) {
		logger.info("Iniciando la aplicacion");
		// Levantar la fabrica de spring
		SpringApplication.run(ZonaFitApplication.class, args);
		logger.info("Aplicacion finalizada");

	}

	@Override
	public void run(String... args) throws Exception {
		zonFitApp();
	}

	private void zonFitApp(){
		boolean salir = false;
		Scanner sc = new Scanner(System.in);
		while (!salir){
			try {
				int opcion = mostrarMenu(sc);
				salir = ejecutarOpciones(sc, opcion);
				logger.info(nl);
			}catch (Exception e){
				System.out.println("Error al digitar la opcion: "+e.getMessage());
				sc.next();
			}
		}
	}

	private int mostrarMenu(Scanner sc){
		logger.info("""
		\n*** Aplicacion Zona Fit(GYM) ***
		1. Listar Clientes
		2. Buscar Cliente
		3. Agregar Cliente
		4. Modificar Cliente
		5. Eliminar Cliente
		6. Salir
		Elige un opcion:\s""");
		return sc.nextInt();
	}

	private boolean ejecutarOpciones(Scanner sc, int opcion){
		boolean salir = false;
		switch (opcion){
			case 1 ->{
				logger.info(nl+"--- Listado de Clientes: ---"+nl);
				List<Cliente> clientes = clienteServicio.listarClientes();
				clientes.forEach(cliente -> logger.info(cliente.toString()+nl));
			}
			case 2 ->{
				logger.info(nl+"--- Buscar Cliente(id) ---"+nl);
				logger.info("Digite el id: ");
				int id = sc.nextInt();
				Cliente cliente = clienteServicio.buscarClientePorId(id);
				if (cliente!=null){
					logger.info("Cliente encontrado: "+cliente);
				}else{
					logger.info("Cliente con id: "+id+" no encontrado");
				}
			}
			case 3->{
				logger.info(nl+"--- Agregar Cliente ---"+nl);
				logger.info("Digitar nombre: ");
				String nombre = sc.next();
				logger.info("Digitar apellido: ");
				String apellido = sc.next();
				logger.info("Digitar membresia: ");
				int membresia = sc.nextInt();
				Cliente cliente = new Cliente();
				cliente.setNombre(nombre);
				cliente.setApellido(apellido);
				cliente.setMembresia(membresia);
				clienteServicio.guardarCliente(cliente);
				logger.info("Cliente agregado: "+cliente+nl);
			}
			case 4->{
				logger.info(nl+"--- Modificar Cliente ---"+nl);
				logger.info("Digite id del cliente a modificar: ");
				int id = sc.nextInt();
				Cliente cliente = clienteServicio.buscarClientePorId(id);
				if (cliente!=null){
					logger.info("Digite nuevo nombre: ");
					String nombre = sc.next();
					logger.info("Digite nuevo apellido: ");
					String apellido = sc.next();
					logger.info("Digite nueva membresia: ");
					int membresia = sc.nextInt();
					cliente.setNombre(nombre);
					cliente.setApellido(apellido);
					cliente.setMembresia(membresia);
					clienteServicio.guardarCliente(cliente);
					logger.info("Cliente modificado: "+cliente+nl);
				}else{
					System.out.println("El cliente con id: "+id+" no existe");
				}
			}
			case 5->{
				logger.info(nl+"--- Eliminar Cliente ---"+nl);
				logger.info("Digite el id del cliente a eliminar: ");
				int id = sc.nextInt();
				Cliente cliente = clienteServicio.buscarClientePorId(id);
				if (cliente!=null){
					clienteServicio.eliminarCliente(cliente);
					logger.info("Cliente eliminado: "+cliente+nl);
				}else{
					System.out.println("El cliente con id: "+id+" no existe");
				}
			}
			case 6->{
				salir = true;
			}
			default -> logger.info("Opcion No reconocida: "+opcion+nl);
		}
		return salir;
	}
}

