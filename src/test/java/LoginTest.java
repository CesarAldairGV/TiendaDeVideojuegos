import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.*;
import proyecto.controlador.LoginControlador;
import proyecto.controlador.LoginControladorImpl;
import proyecto.modelo.db.Conexion;
import proyecto.vista.LoginPanel;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

public class LoginTest {

    private JFrame frame;
    private LoginPanel loginPanel;
    private LoginControlador loginControlador;

    @BeforeClass
    public static void initSql() throws Exception{
        ScriptRunner scriptRunner = new ScriptRunner(Conexion.getConnection());
        String path = VideojuegoTest.class.getResource("/Tienda_De_Videojuegos_Test.sql").getPath();
        Reader r = new BufferedReader(new FileReader(path));
        scriptRunner.runScript(r);
    }

    @Before
    public void initComponents(){
        frame = new JFrame();
        loginPanel = new LoginPanel();
        loginControlador = new LoginControladorImpl(loginPanel, frame);
        loginControlador.inicializar();
    }

    /**
     * Test para probar inicio de sesion con datos invalidos
     * Deberia de mostrar un Alert con un mensaje de inicio de sesion exitosa
     */
    @Test
    public void invalidLogin(){
        loginPanel.getId().setText("1");
        loginPanel.getNombre().setText("Cesar");
        loginPanel.getPassword().setText("Ni idea de la pass");

        JFrame result = loginControlador.iniciarSesion();

        Assert.assertNull(result);
    }

    /**
     * Test para probar inicio de sesion con datos validos
     * Deberia de mostrar un Alert con un mensaje de inicio de sesion exitosa
     */
    @Test
    public void validLogin(){

        loginPanel.getId().setText("1");
        loginPanel.getNombre().setText("Cesar");
        loginPanel.getPassword().setText("1234");

        JFrame result = loginControlador.iniciarSesion();

        Assert.assertNotNull(result);

        result.setVisible(false);
    }

    @After
    public void closeComponents(){
        frame.dispose();
    }
}
