package proyecto.controlador;

import javax.swing.JOptionPane;
import javax.swing.JFrame;

import proyecto.main.AdministradorNivel;
import proyecto.main.CajeroNivel;
import proyecto.modelo.dao.UsuarioDAO;
import proyecto.modelo.dao.UsuarioDAOImpl;
import proyecto.modelo.entidades.Usuario;
import proyecto.vista.LoginPanel;

public class LoginControladorImpl implements LoginControlador {

	private LoginPanel vista;
	private UsuarioDAO dao;
	private JFrame marco;

	public LoginControladorImpl(LoginPanel vista, JFrame marco) {
		this.vista = vista;
		this.marco = marco;
	}

	@Override
	public int inicializar() {
		dao = new UsuarioDAOImpl();
		return 1;
	}

	@Override
	public JFrame iniciarSesion() {
		String idString = vista.getId().getText();
		int id = 0;
		try {
			id = Integer.parseInt(idString);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(vista, "Introduce un Id valido");
			return null;
		}
		String nombre = vista.getNombre().getText();
		String password = new String(vista.getPassword().getPassword());
		if (nombre.equals("") || password.equals("")) {
			JOptionPane.showMessageDialog(vista, "Llena todos los campos");
			return null;
		}
		Usuario usuario = dao.conseguirUsuario(id, nombre, password);
		if (usuario == null) {
			JOptionPane.showMessageDialog(vista, "No hay conexion con la base de datos");
			return null;
		}
		if(usuario.getId()==0) {
			JOptionPane.showMessageDialog(vista, "Datos Incorrectos");
			return null;
		}
		JOptionPane.showMessageDialog(vista, "Sesion iniciada Correctamente!");
		JFrame marco;
		if (usuario.getNivel().equals("Administrador")) {
			AdministradorNivel nivel = new AdministradorNivel();
			nivel.setUsuario(usuario);
			nivel.iniciar();
			marco = nivel.getMarco();
		} else {
			CajeroNivel nivel = new CajeroNivel();
			nivel.setUsuario(usuario);
			nivel.iniciar();
			marco = nivel.getMarco();
		}
		this.marco.setVisible(false);
		return marco;
	}

}
