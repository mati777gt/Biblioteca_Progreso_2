import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Verificacion {
    static void llenar(JTextField[] campos, String... valores) {
        for (int i=0; i<campos.length; i++) campos[i].setText(i<valores.length ? valores[i] : "");
    }
    static void comprobar(String caso, JButton boton, JTextArea salida, String... contiene) {
        boton.doClick();
        for (String valor: contiene) if (!salida.getText().contains(valor)) throw new AssertionError(caso);
        System.out.println(caso + " | " + salida.getText().replace("\n"," / "));
    }
    static void distribuir(Container c) {
        c.doLayout();
        for(Component hijo:c.getComponents()) if(hijo instanceof Container panel) distribuir(panel);
    }
    static void captura(Biblioteca b, String nombre) {
        b.setSize(670,490); distribuir(b);
        BufferedImage img = new BufferedImage(670,490,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics(); b.printAll(g); g.dispose();
        try { ImageIO.write(img,"png",new File(nombre)); } catch(Exception e) { throw new RuntimeException(e); }
    }
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            Biblioteca b = new Biblioteca();
            String id="9780306406157", otro="9780132350884";
            llenar(b.alta,id,"Redes de campus","Ana Pérez","Redes","Aula","2024","Sistemas · A1");
            comprobar("VF01",b.registrar,b.mensaje,"Título registrado · "+id+"-1");
            comprobar("VF02",b.registrar,b.mensaje,"ISBN ya registrado.");
            b.alta[6].setText("Sistemas · A2");
            comprobar("VF03",b.copia,b.mensaje,"Ejemplar agregado · "+id+"-2");
            captura(b,"catalogo.png");
            llenar(b.alta,otro,"Protocolos básicos","Luis Vega","Redes","Aula","2023","Sistemas · B1");
            comprobar("Preparación",b.registrar,b.mensaje,"Título registrado");
            b.pestanas.setSelectedIndex(1);
            llenar(b.consulta,"","","perez","redes");
            comprobar("VF04",b.buscar,b.resultados,"Títulos encontrados: 1","Redes de campus",id+"-1",id+"-2");
            captura(b,"busqueda.png");
            llenar(b.consulta,"","","","Historia");
            comprobar("VF05",b.buscar,b.resultados,"Sin coincidencias.");
            llenar(b.consulta,id);
            comprobar("VF06",b.similares,b.resultados,"Títulos encontrados: 1","Protocolos básicos");
            llenar(b.consulta,"9780306406158");
            comprobar("VF07",b.buscar,b.resultados,"Dígito de control ISBN incorrecto.");
            llenar(b.consulta);
            comprobar("VF08",b.buscar,b.resultados,"Títulos encontrados: 2");
            if(b.resultados.getText().indexOf(otro)>b.resultados.getText().indexOf(id)) throw new AssertionError("Orden");
            llenar(b.consulta,id,"","","Historia");
            comprobar("VF09",b.buscar,b.resultados,"Sin coincidencias.");
        });
    }
}
