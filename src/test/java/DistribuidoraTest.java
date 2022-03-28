import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.*;
import org.junit.runners.MethodSorters;
import proyecto.controlador.DistribuidoraControlador;
import proyecto.controlador.DistribuidoraControladorImpl;
import proyecto.modelo.db.Conexion;
import proyecto.vista.AdministrarDistribuidoraPanel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DistribuidoraTest {

    private DistribuidoraControlador controlador;
    private AdministrarDistribuidoraPanel panel;

    @BeforeClass
    public static void initSql() throws Exception{
        ScriptRunner scriptRunner = new ScriptRunner(Conexion.getConnection());
        String path = VideojuegoTest.class.getResource("/Tienda_De_Videojuegos_Test.sql").getPath();
        Reader r = new BufferedReader(new FileReader(path));
        scriptRunner.runScript(r);
    }

    @Before
    public void initComponents(){
        panel = new AdministrarDistribuidoraPanel();
        controlador = new DistribuidoraControladorImpl(panel);
        controlador.inicializar();
    }

    /**
     * Test para verificar si agregamos una distribuidora sin haber rellenado ningun campo
     * Deberia mostrar un alert que diga "Introduce todos los datos"
     */
    @Test
    public void test1AgregarDistribuidoraInvalido(){
        panel.llenarCampos(null);
        int result = controlador.agregarDistribuidora();
        int expected = 0;
        Assert.assertEquals(expected, result);
    }

    /**
     * Test para verificar si agregamos una distribuidora valido
     * Deberia mostrar un alert que diga "La distribuidora se agrego correctamente"
     */
    @Test
    public void test2AgregarDistribuidora(){
        panel.getNombre().setText("Konami");
        panel.getDireccion().setText("Japon");
        panel.getCuenta().setText("8977364");
        panel.getTelefono().setText("915155060");

        int result = controlador.agregarDistribuidora();
        int expected = 1;
        Assert.assertEquals(expected, result);
    }

    /**
     * Test para verificar si editamos un distribuidora sin llenar todos los campos
     * Deberia mostrar un alert que diga "Introduce todos los datos"
     */
    @Test
    public void test3EditarDistribuidoraInvalido(){
        panel.getId().setText("16");
        panel.getNombre().setText("Konami Inc.");

        int result = controlador.editarDistribuidora();
        int expected = 0;
        Assert.assertEquals(expected, result);
    }

    /**
     * Test para verificar si editamos un distribuidora
     * Deberia mostrar un alert que diga "La distribuidora se edito correctamente"
     */
    @Test
    public void test4EditarDistribuidora(){
        panel.getTabla().setRowSelectionInterval(16,16);
        controlador.filaSeleccionada();
        panel.getNombre().setText("Konami Inc.");
        panel.getCuenta().setText("3145435141");

        int result = controlador.editarDistribuidora();
        int expected = 1;
        Assert.assertEquals(expected, result);
    }

    /**
     * Test para verificar si eliminamos una distribuidora inexistente
     * Deberia mostrar un alert que diga "La distribuidora no se pudo eliminar"
     */
    @Test
    public void test5EliminarDistribuidoraInvalido(){
        panel.getId().setText("18");
        int result = controlador.eliminarDistribuidora();
        int expected = 0;
        Assert.assertEquals(expected,result);
    }

    /**
     * Test para verificar si eliminamos una distribuidora inexistente
     * Deberia mostrar un alert que diga "La distribuidora se elimino correctamente"
     */
    @Test
    public void test6EliminarDistribuidora(){
        panel.getId().setText("17");
        int result = controlador.eliminarDistribuidora();
        int expected = 1;
        Assert.assertEquals(expected,result);
    }
}
