import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.*;
import org.junit.runners.MethodSorters;
import proyecto.controlador.VideojuegoControlador;
import proyecto.controlador.VideojuegoControladorImpl;
import proyecto.modelo.db.Conexion;
import proyecto.vista.AdministrarVideojuegoPanel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VideojuegoTest {

    private AdministrarVideojuegoPanel panel;
    private VideojuegoControlador controlador;

    @BeforeClass
    public static void initSql() throws Exception{
        ScriptRunner scriptRunner = new ScriptRunner(Conexion.getConnection());
        String path = VideojuegoTest.class.getResource("/Tienda_De_Videojuegos_Test.sql").getPath();
        Reader r = new BufferedReader(new FileReader(path));
        scriptRunner.runScript(r);
    }

    @Before
    public void initComponents(){
        panel = new AdministrarVideojuegoPanel();
        controlador = new VideojuegoControladorImpl(panel);
        controlador.inicializar();
    }

    /**
     * Test para verificar si agregamos un videojuego sin haber rellenado ningun campo
     * Deberia mostrar un alert que diga "Llenar todos los campos antes de continuar"
     */
    @Test
    public void test1AgregarVideojuegoInvalido(){
        panel.llenarCampos(null);
        int result = controlador.agregarVideojuego();
        int expected = 0;
        Assert.assertEquals(expected, result);
    }

    /**
     * Test para verificar si agregamos un videojuego valido
     * Deberia mostrar un alert que diga "El videojuego se agrego correctamente"
     */
    @Test
    public void test2AgregarVideojuego(){
        panel.getNombre().setText("Super Mario Bros.");
        panel.getDistribuidora().setSelectedIndex(0);
        panel.getDesarrolladora().setText("Nintento");
        panel.getConsola().setSelectedIndex(6);
        panel.getClasificacion().setSelectedIndex(0);
        panel.getPrecio().setText("50");
        panel.getGeneros().setSelectedIndex(3);

        int result = controlador.agregarVideojuego();
        int expected = 1;
        Assert.assertEquals(expected, result);
    }

    /**
     * Test para verificar si editamos un videojuego sin llenar todos los campos
     * Deberia mostrar un alert que diga "Llenar todos los campos antes de continuar"
     */
    @Test
    public void test3EditarVideojuegoInvalido(){
        panel.getId().setText("34");
        panel.getNombre().setText("New Super Mario Bros. 2");
        panel.getPrecio().setText("100");

        int result = controlador.editarVideojuego();
        int expected = 0;
        Assert.assertEquals(expected, result);
    }

    /**
     * Test para verificar si editamos un videojuego correctamente
     * Deberia mostrar un alert que diga "El Videojuego se edito correctamente"
     */
    @Test
    public void test4EditarVideojuego(){
        panel.getTabla().setRowSelectionInterval(33,33);
        controlador.filaSeleccionada();
        panel.getNombre().setText("New Super Mario Bros. 2");
        panel.getPrecio().setText("100");

        int result = controlador.editarVideojuego();
        int expected = 1;
        Assert.assertEquals(expected, result);
    }

    /**
     * Test para verificar si eliminamos un videojuego inexistente
     * Deberia mostrar un alert que diga "El videojuego no se pudo eliminar"
     */
    @Test
    public void test5EliminarVideojuegoInvalido(){
        panel.getId().setText("35");
        int result = controlador.eliminarVideojuego();
        int expected = 0;
        Assert.assertEquals(expected,result);
    }

    /**
     * Test para verificar si eliminamos un videojuego
     * Deberia mostrar un alert que diga "El videojuego se elimino correctamente"
     */
    @Test
    public void test6EliminarVideojuego(){
        panel.getId().setText("34");
        int result = controlador.eliminarVideojuego();
        int expected = 1;
        Assert.assertEquals(expected,result);
    }
}
