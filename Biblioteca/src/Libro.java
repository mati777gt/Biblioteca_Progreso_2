import java.util.ArrayList;
import java.text.Normalizer;
import java.util.Locale;

record Ejemplar(String codigo, String ubicacion) {}
final class Libro {
    final String isbn, titulo, autor, tema, editorial;
    final int anio;
    final ArrayList<Ejemplar> ejemplares = new ArrayList<>();
    Libro(String isbn, String titulo, String autor, String tema, String editorial, int anio) {
        this.isbn = isbn; this.titulo = titulo; this.autor = autor;
        this.tema = tema; this.editorial = editorial; this.anio = anio;
    }
    static String clave(String valor) {
        return Normalizer.normalize(valor.strip().toLowerCase(Locale.ROOT), Normalizer.Form.NFD)
            .replaceAll("\\p{M}+", "");
    }
    Ejemplar agregar(String ubicacion) {
        Ejemplar copia = new Ejemplar(isbn + "-" + (ejemplares.size() + 1), ubicacion);
        ejemplares.add(copia);
        return copia;
    }
    String ficha() {
        StringBuilder texto = new StringBuilder(titulo + " | " + isbn + "\n" + autor + " · " + tema + " · " + editorial + " · " + anio);
        for (Ejemplar copia : ejemplares)
            texto.append("\n   ").append(copia.codigo()).append(" · ").append(copia.ubicacion()).append(" · Disponible");
        return texto.toString();
    }
}
