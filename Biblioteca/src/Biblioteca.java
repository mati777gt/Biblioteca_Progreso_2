import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Supplier;

public class Biblioteca extends JPanel {
    final Catalogo catalogo = new Catalogo();
    final Busqueda busqueda = new Busqueda(catalogo);
    final JTabbedPane pestanas = new JTabbedPane();
    final JTextField[] alta = campos(7), consulta = campos(6);
    final JTextArea mensaje = salida(), resultados = salida();
    final JButton registrar = new JButton("Registrar título"), copia = new JButton("Agregar ejemplar"),
        buscar = new JButton("Buscar"), similares = new JButton("Sugerir similares");
    static JTextField[] campos(int n) {
        JTextField[] f = new JTextField[n];
        for (int i=0; i<n; i++) f[i] = new JTextField(18);
        return f;
    }
    static JTextArea salida() {
        JTextArea t = new JTextArea(6,50);
        t.setEditable(false); t.setFont(new Font("SansSerif",Font.PLAIN,14));
        return t;
    }
    Biblioteca() {
        super(new BorderLayout());
        add(pestanas);
        pestanas.addTab("Catálogo", panel(alta,mensaje,registrar,copia));
        pestanas.addTab("Búsqueda", panel(consulta,resultados,buscar,similares));
        registrar.addActionListener(ev -> ejecutar(mensaje, () -> catalogo.registrar(v(alta,0),v(alta,1),v(alta,2),v(alta,3),v(alta,4),v(alta,5),v(alta,6))));
        copia.addActionListener(ev -> ejecutar(mensaje, () -> catalogo.agregarCopia(v(alta,0),v(alta,6))));
        buscar.addActionListener(ev -> ejecutar(resultados, () -> mostrar(busqueda.buscar(v(consulta,0),v(consulta,1),v(consulta,2),v(consulta,3),v(consulta,4),v(consulta,5)))));
        similares.addActionListener(ev -> ejecutar(resultados, () -> mostrar(busqueda.similares(v(consulta,0)))));
    }
    private JPanel panel(JTextField[] campos, JTextArea salida, JButton primero, JButton segundo) {
        String[] etiquetas = {"ISBN-13","Título","Autor(es)","Tema","Editorial","Año","Ubicación (sección y estante)"};
        JPanel base = new JPanel(new BorderLayout(8,8)), form = new JPanel(new GridLayout(0,2,6,5));
        base.setBorder(BorderFactory.createEmptyBorder(14,16,14,16));
        for(int i=0;i<campos.length;i++) {form.add(new JLabel(etiquetas[i])); form.add(campos[i]);}
        form.add(primero); form.add(segundo);
        base.add(form,BorderLayout.NORTH); base.add(new JScrollPane(salida),BorderLayout.CENTER);
        return base;
    }
    static String v(JTextField[] campos, int i) { return campos[i].getText(); }
    static void ejecutar(JTextArea t, Supplier<String> accion) {
        try { t.setText(accion.get()); } catch (IllegalArgumentException e) { t.setText(e.getMessage()); }
        t.setCaretPosition(0);
    }
    static String mostrar(List<Libro> libros) {
        if (libros.isEmpty()) return "Sin coincidencias.";
        StringBuilder b = new StringBuilder("Títulos encontrados: " + libros.size());
        for (Libro l : libros) b.append("\n\n").append(l.ficha());
        return b.toString();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Biblioteca universitaria");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.add(new Biblioteca()); f.setSize(720,540); f.setLocationRelativeTo(null); f.setVisible(true);
        });
    }
}
