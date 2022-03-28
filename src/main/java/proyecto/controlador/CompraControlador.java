package proyecto.controlador;

import proyecto.modelo.entidades.Distribuidora;
import proyecto.modelo.entidades.Usuario;

public interface CompraControlador{
	int inicializar();
	int inicializar(Distribuidora distribuidora);
	void filtrarPorConsola();
	void filtrarPorGenero();
	void filtrarPorNombre();
	void agregar();
	void cancelar();
	int procesarCompra();
	void setUsuario(Usuario usuario);
}
