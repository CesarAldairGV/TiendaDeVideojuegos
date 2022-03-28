import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import proyecto.controlador.CompraControlador;
import proyecto.controlador.CompraControladorImpl;
import proyecto.modelo.dao.DistribuidoraDAO;
import proyecto.modelo.dao.DistribuidoraDAOImpl;
import proyecto.modelo.dao.UsuarioDAO;
import proyecto.modelo.dao.UsuarioDAOImpl;
import proyecto.modelo.db.Conexion;
import proyecto.modelo.entidades.Distribuidora;
import proyecto.modelo.entidades.Usuario;
import proyecto.vista.CompraPanel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

public class CompraTest {

    private CompraPanel panel;
    private CompraControlador controlador;
    private UsuarioDAO usuarioDAO;
    private DistribuidoraDAO distribuidoraDAO;

    @BeforeClass
    public static void initSql() throws Exception{
        ScriptRunner scriptRunner = new ScriptRunner(Conexion.getConnection());
        String path = VideojuegoTest.class.getResource("/Tienda_De_Videojuegos_Test.sql").getPath();
        Reader r = new BufferedReader(new FileReader(path));
        scriptRunner.runScript(r);
    }

    @Before
    public void initComponents(){
        usuarioDAO = new UsuarioDAOImpl();
        distribuidoraDAO = new DistribuidoraDAOImpl();
        panel = new CompraPanel();
        controlador = new CompraControladorImpl(panel);

        Usuario usuario = usuarioDAO.conseguirUsuario(1,"Cesar","1234");
        controlador.setUsuario(usuario);

        Distribuidora dis = distribuidoraDAO.conseguirDistribuidora(1);
        controlador.inicializar(dis);
    }

    /**
     * Test para verificar la compra sin nada agregado
     * Deberia mostrar un alert que diga "Agrege algo para proceder"
     */
    @Test
    public void compraInvalida(){
        int result = controlador.procesarCompra();
        int expected = 0;

        Assert.assertEquals(expected,result);
    }

    /**
     * Test para verificar la compra con videojuegos agregados
     * Deberia mostrar un alert que diga "La compra se realizo correctamente"
     */
    @Test
    public void compraValida(){
        panel.getVideojuego().setSelectedIndex(0);
        panel.getCantidad().setValue(2);
        controlador.agregar();

        int result = controlador.procesarCompra();
        int expected = 1;

        Assert.assertEquals(expected,result);
    }
}
