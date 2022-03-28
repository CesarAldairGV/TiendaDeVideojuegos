package proyecto.controlador;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import proyecto.modelo.dao.DistribuidoraDAO;
import proyecto.modelo.dao.DistribuidoraDAOImpl;
import proyecto.modelo.db.Conexion;
import proyecto.modelo.entidades.Distribuidora;
import proyecto.vista.AdministrarDistribuidoraPanel;

public class DistribuidoraControladorImpl implements DistribuidoraControlador{
	
	private AdministrarDistribuidoraPanel vista;
	private DistribuidoraDAO dao;
	
	public DistribuidoraControladorImpl(AdministrarDistribuidoraPanel vista) {
		this.vista = vista;
	}
	
	@Override
	public int inicializar() {
		if(Conexion.thereIsConnection()) {
			vista.llenarCampos(null);
			dao = new DistribuidoraDAOImpl();
			ArrayList<Distribuidora> distribuidoras = dao.conseguirDistribuidoras();
			vista.llenarTabla(distribuidoras);
			vista.getAgregar().setEnabled(true);
			vista.getEditar().setEnabled(false);
			vista.getEliminar().setEnabled(false);
			vista.getId().setEnabled(false);
			return 1;
		}else {
			JOptionPane.showMessageDialog(vista, "No hay Conexion");
			return 0;
		}
	}

	@Override
	public int agregarDistribuidora() {
		String idString = vista.getId().getText();
		int id = 0;
		try {
			id = Integer.parseInt(idString);
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(vista, "Introduce un id valido");
			return 0;
		}
		String nombre = vista.getNombre().getText();
		String telefono = vista.getTelefono().getText();
		String cuenta = vista.getCuenta().getText();
		String direccion = vista.getDireccion().getText();
		if("".equals(nombre) || "".equals(telefono) || "".equals(cuenta) || "".equals(direccion)) {
			JOptionPane.showMessageDialog(vista, "Introduce todos los datos");
			return 0;
		}
		Distribuidora distribuidora = new Distribuidora(id,nombre,telefono,direccion,cuenta);
		int success = dao.agregarDistribuidora(distribuidora);
		if(success == 1) {
			JOptionPane.showMessageDialog(vista, "La distribuidora se agrego correctamente");
			vista.llenarCampos(null);
			ArrayList<Distribuidora> distribuidoras = dao.conseguirDistribuidoras();
			vista.llenarTabla(distribuidoras);
			return 1;
		}else {
			JOptionPane.showMessageDialog(vista, "La distriuidora no se pudo agregar");
			return 0;
		}
	}

	@Override
	public int editarDistribuidora() {
		String idString = vista.getId().getText();
		int id = 0;
		try {
			id = Integer.parseInt(idString);
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(vista, "Introduce un id valido");
			return 0;
		}
		String nombre = vista.getNombre().getText();
		String telefono = vista.getTelefono().getText();
		String cuenta = vista.getCuenta().getText();
		String direccion = vista.getDireccion().getText();
		if("".equals(nombre) || "".equals(telefono) || "".equals(cuenta) || "".equals(direccion)) {
			JOptionPane.showMessageDialog(vista, "Introduce todos los datos");
			return 0;
		}
		Distribuidora distribuidora = new Distribuidora(id,nombre,telefono,direccion,cuenta);
		int success = dao.editarDistribuidora(distribuidora);
		if(success == 1) {
			int index = vista.getTabla().getSelectedRow();
			JOptionPane.showMessageDialog(vista, "La distribuidora se edito correctamente");
			ArrayList<Distribuidora> distribuidoras = dao.conseguirDistribuidoras();
			vista.llenarTabla(distribuidoras);
			vista.getTabla().setRowSelectionInterval(index, index);
			return 1;
		}else {
			JOptionPane.showMessageDialog(vista, "La distriuidora no se pudo editar");
			return 0;
		}
	}

	@Override
	public int eliminarDistribuidora() {
		String idString = vista.getId().getText();
		int id = 0;
		try {
			id = Integer.parseInt(idString);
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(vista, "Introduce un id valido");
			return 0;
		}
		int success = dao.eliminarDistribuidora(id);
		if(success == 1) {
			JOptionPane.showMessageDialog(vista, "La distribuidora se elimino correctamente");
			ArrayList<Distribuidora> distribuidoras = dao.conseguirDistribuidoras();
			vista.llenarTabla(distribuidoras);
			vista.llenarCampos(null);
			vista.getAgregar().setEnabled(true);
			vista.getEditar().setEnabled(false);
			vista.getEliminar().setEnabled(false);
			return 1;
		}else {
			JOptionPane.showMessageDialog(vista, "La distriuidora no se pudo eliminar");
			return 0;
		}
	}

	@Override
	public void nuevaDistribuidora() {
		vista.llenarCampos(null);
		vista.getAgregar().setEnabled(true);
		vista.getEditar().setEnabled(false);
		vista.getEliminar().setEnabled(false);
		vista.getTabla().clearSelection();
	}
	
	@Override
	public void filaSeleccionada() {
		Distribuidora distribuidora = vista.getDistribuidoraSeleccionada();
		vista.llenarCampos(distribuidora);
		vista.getAgregar().setEnabled(false);
		vista.getEditar().setEnabled(true);
		vista.getEliminar().setEnabled(true);
	}
	
}
