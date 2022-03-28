import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.*;
import org.junit.runners.MethodSorters;
import proyecto.controlador.UsuarioControlador;
import proyecto.controlador.UsuarioControladorImpl;
import proyecto.modelo.db.Conexion;
import proyecto.vista.AdministrarUsuarioPanel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuarioTest {

    private AdministrarUsuarioPanel panel;
    private UsuarioControlador controlador;

    @BeforeClass
    public static void initSql() throws Exception{
        ScriptRunner scriptRunner = new ScriptRunner(Conexion.getConnection());
        String path = VideojuegoTest.class.getResource("/Tienda_De_Videojuegos_Test.sql").getPath();
        Reader r = new BufferedReader(new FileReader(path));
        scriptRunner.runScript(r);
    }

    @Before
    public void initComponents(){
        panel = new AdministrarUsuarioPanel();
        controlador = new UsuarioControladorImpl(panel);
        controlador.inicializar();
    }

    /**
     * Test para verificar si agregamos un usuario sin haber rellenado ningun campo
     * Deberia mostrar un alert que diga "Llenar todos los campos antes de continuar"
     */
    @Test
    public void test1AgregarUsuarioInvalido(){
        panel.llenarCampos(null);
        int result = controlador.agregarUsuario();
        int expected = 0;
        Assert.assertEquals(expected, result);
    }

    /**
     * Test para verificar si agregamos un usuario valido
     * Deberia mostrar un alert que diga "El usuario se agrego correctamente"
     */
    @Test
    public void test2AgregarUsuario(){
        panel.getNombre().setText("Alejandro");
        panel.getPassword().setText("1234");
        panel.getNivel().setSelectedIndex(1);

        int result = controlador.agregarUsuario();
        int expected = 1;
        Assert.assertEquals(expected, result);
    }

    /**
     * Test para verificar si editamos un usuario sin llenar todos los campos
     * Deberia mostrar un alert que diga "Llenar todos los campos antes de continuar"
     */
    @Test
    public void test3EditarUsuarioInvalido(){
        panel.getId().setText("5");
        panel.getNombre().setText("Victor");

        int result = controlador.editarUsuario();
        int expected = 0;
        Assert.assertEquals(expected, result);
    }

    /**
     * Test para verificar si editamos un usuario correctamente
     * Deberia mostrar un alert que diga "El Usuario se edito correctamente"
     */
    @Test
    public void test4EditarUsuario(){
        panel.getTabla().setRowSelectionInterval(4,4);
        controlador.filaSeleccionada();
        panel.getNombre().setText("Victor");
        panel.getNivel().setSelectedIndex(0);
        panel.getPassword().setText("9876");

        int result = controlador.editarUsuario();
        int expected = 1;
        Assert.assertEquals(expected, result);
    }

    /**
     * Test para verificar si eliminamos un usuario inexistente
     * Deberia mostrar un alert que diga "El usuario no se pudo eliminar"
     */
    @Test
    public void test5EliminarUsuarioInvalido(){
        panel.getId().setText("6");
        int result = controlador.eliminarUsuario();
        int expected = 0;
        Assert.assertEquals(expected,result);
    }

    /**
     * Test para verificar si eliminamos un videojuego
     * Deberia mostrar un alert que diga "El videojuego se elimino correctamente"
     */
    @Test
    public void test6EliminarUsuario(){
        panel.getId().setText("5");
        int result = controlador.eliminarUsuario();
        int expected = 1;
        Assert.assertEquals(expected,result);
    }
}
