import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import proyecto.controlador.CorteControlador;
import proyecto.controlador.CorteControladorImpl;
import proyecto.controlador.VentaControlador;
import proyecto.controlador.VentaControladorImpl;
import proyecto.modelo.dao.UsuarioDAO;
import proyecto.modelo.dao.UsuarioDAOImpl;
import proyecto.modelo.db.Conexion;
import proyecto.modelo.entidades.Usuario;
import proyecto.vista.CortePanel;
import proyecto.vista.VentaPanel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CorteTest {

    private VentaPanel ventaPanel;
    private VentaControlador ventaControlador;
    private CortePanel cortePanel;
    private CorteControlador corteControlador;
    private UsuarioDAO usuarioDAO;

    @BeforeClass
    public static void initSql() throws Exception{
        ScriptRunner scriptRunner = new ScriptRunner(Conexion.getConnection());
        String path = VideojuegoTest.class.getResource("/Tienda_De_Videojuegos_Test.sql").getPath();
        Reader r = new BufferedReader(new FileReader(path));
        scriptRunner.runScript(r);
    }

    public CorteTest(){
        usuarioDAO = new UsuarioDAOImpl();
        Usuario usuario = usuarioDAO.conseguirUsuario(1,"Cesar","1234");

        ventaPanel = new VentaPanel();
        ventaControlador = new VentaControladorImpl(ventaPanel);
        ventaControlador.setUsuario(usuario);

        cortePanel = new CortePanel();
        corteControlador = new CorteControladorImpl(cortePanel);
        corteControlador.setUsuario(usuario);

        ventaControlador.inicializar();
        corteControlador.inicializar();
    }

    @Test
    public void test1CorteVacio(){
        corteControlador.generarCorte();
        double val = Double.parseDouble(cortePanel.getTotal().getText());
        Assert.assertEquals(0, val,1);
    }

    @Test
    public void test2CorteTest(){
        //Generamos una ventas
        ventaPanel.getVideojuego().setSelectedIndex(0);
        ventaPanel.getCantidad().setValue(2);
        ventaControlador.agregar();
        ventaPanel.getVideojuego().setSelectedIndex(5);
        ventaPanel.getCantidad().setValue(3);
        ventaControlador.agregar();
        ventaPanel.getVideojuego().setSelectedIndex(4);
        ventaPanel.getCantidad().setValue(3);
        ventaControlador.agregar();
        ventaControlador.procesarVenta();

        //5470
        corteControlador.generarCorte();
        double val = Double.parseDouble(cortePanel.getTotal().getText());
        Assert.assertEquals(5470d, val,1);
    }
}
