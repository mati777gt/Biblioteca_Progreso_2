import java.util.ArrayList;
final class Busqueda {
    private final Catalogo catalogo;
    Busqueda(Catalogo catalogo) { this.catalogo = catalogo; }
    ArrayList<Libro> buscar(String id, String titulo, String autor, String tema, String editorial, String fecha) {
        ArrayList<Libro> resultado = new ArrayList<>();
        String t = Libro.clave(titulo), a = Libro.clave(autor), m = Libro.clave(tema), e = Libro.clave(editorial);
        int anio = fecha.isBlank() ? 0 : Catalogo.anio(fecha);
        java.util.function.Consumer<Libro> filtrar = l -> {
            if (Libro.clave(l.titulo).contains(t) && Libro.clave(l.autor).contains(a)
                && Libro.clave(l.tema).contains(m) && Libro.clave(l.editorial).contains(e)
                && (anio == 0 || l.anio == anio)) resultado.add(l);
        };
        if (id.isBlank()) catalogo.recorrer(filtrar);
        else {
            Libro libro = catalogo.exacto(id);
            if (libro != null) filtrar.accept(libro);
        }
        return resultado;
    }
    ArrayList<Libro> similares(String id) {
        Libro base = catalogo.exacto(id);
        if (base == null) throw new IllegalArgumentException("ISBN inexistente.");
        ArrayList<Libro> resultado = new ArrayList<>();
        catalogo.recorrer(l -> {
            if (l != base && resultado.size() < 3 && Libro.clave(l.tema).equals(Libro.clave(base.tema)))
                resultado.add(l);
        });
        return resultado;
    }
}
