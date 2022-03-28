import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import proyecto.controlador.VentaControlador;
import proyecto.controlador.VentaControladorImpl;
import proyecto.modelo.dao.UsuarioDAO;
import proyecto.modelo.dao.UsuarioDAOImpl;
import proyecto.modelo.db.Conexion;
import proyecto.modelo.entidades.Usuario;
import proyecto.vista.VentaPanel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

public class VentaTest {

    private VentaPanel panel;
    private VentaControlador controlador;
    private UsuarioDAO usuarioDAO;

    @BeforeClass
    public static void initSql() throws Exception{
        ScriptRunner scriptRunner = new ScriptRunner(Conexion.getConnection());
        String path = VideojuegoTest.class.getResource("/Tienda_De_Videojuegos_Test.sql").getPath();
        Reader r = new BufferedReader(new FileReader(path));
        scriptRunner.runScript(r);
    }

    public VentaTest(){
        usuarioDAO = new UsuarioDAOImpl();
        panel = new VentaPanel();
        controlador = new VentaControladorImpl(panel);

        Usuario usuario = usuarioDAO.conseguirUsuario(1,"Cesar","1234");
        controlador.setUsuario(usuario);

        controlador.inicializar();
    }

    /**
     * Test para verificar la venta sin nada agregado
     * Deberia mostrar un alert que diga "Agrege algo para proceder"
     */
    @Test
    public void ventaInvalida(){
        int result = controlador.procesarVenta();
        int expected = 0;

        Assert.assertEquals(expected,result);
    }

    /**
     * Test para verificar la venta con videojuegos agregados
     * Deberia mostrar un alert que diga "La venta se realizo correctamente"
     */
    @Test
    public void compraValida(){
        panel.getVideojuego().setSelectedIndex(0);
        panel.getCantidad().setValue(2);
        controlador.agregar();
        panel.getVideojuego().setSelectedIndex(5);
        panel.getCantidad().setValue(3);
        controlador.agregar();
        panel.getVideojuego().setSelectedIndex(4);
        panel.getCantidad().setValue(3);
        controlador.agregar();

        int result = controlador.procesarVenta();
        int expected = 1;

        Assert.assertEquals(expected,result);
    }
}
