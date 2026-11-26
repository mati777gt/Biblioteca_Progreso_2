import java.util.ArrayList;
import java.util.function.Consumer;
import java.time.Year;

final class Nodo {
    final Libro libro;
    Nodo izq, der;
    Nodo(Libro libro) { this.libro = libro; }
}
final class Catalogo {
    private Nodo raiz;
    static String isbn(String valor) {
        String s = valor.replace("-", "").replace(" ", "");
        if (!s.matches("97[89][0-9]{10}")) throw new IllegalArgumentException("Ingrese un ISBN-13 válido.");
        int suma = 0;
        for (int i = 0; i < 13; i++) suma += (s.charAt(i)-'0') * (i % 2 == 0 ? 1 : 3);
        if (suma % 10 != 0) throw new IllegalArgumentException("Dígito de control ISBN incorrecto.");
        return s;
    }
    static String campo(String s) {
        if (s.isBlank()) throw new IllegalArgumentException("Complete los campos obligatorios.");
        return s.strip();
    }
    static int anio(String valor) {
        if (!valor.strip().matches("[0-9]{4}")) throw new IllegalArgumentException("Año inválido.");
        int a = Integer.parseInt(valor.strip());
        if (a < 1000 || a > Year.now().getValue()) throw new IllegalArgumentException("Año fuera de rango.");
        return a;
    }
    Libro exacto(String id) {
        String s = isbn(id);
        Nodo n = raiz;
        while (n != null) {
            int cmp = s.compareTo(n.libro.isbn);
            if (cmp == 0) return n.libro;
            n = cmp < 0 ? n.izq : n.der;
        }
        return null;
    }
    String registrar(String id, String titulo, String autor, String tema, String editorial, String fecha, String lugar) {
        Libro l = new Libro(isbn(id), campo(titulo), campo(autor), campo(tema), campo(editorial), anio(fecha));
        Ejemplar e = l.agregar(campo(lugar));
        Nodo nuevo = new Nodo(l);
        if (raiz == null) raiz = nuevo;
        else {
            Nodo n = raiz;
            while (true) {
                int cmp = l.isbn.compareTo(n.libro.isbn);
                if (cmp == 0) throw new IllegalArgumentException("ISBN ya registrado.");
                if (cmp < 0) {
                    if (n.izq == null) { n.izq = nuevo; break; }
                    n = n.izq;
                } else {
                    if (n.der == null) { n.der = nuevo; break; }
                    n = n.der;
                }
            }
        }
        return "Título registrado · " + e.codigo();
    }
    String agregarCopia(String id, String lugar) {
        Libro l = exacto(id);
        if (l == null) throw new IllegalArgumentException("ISBN inexistente.");
        return "Ejemplar agregado · " + l.agregar(campo(lugar)).codigo();
    }
    void recorrer(Consumer<Libro> visitar) {
        ArrayList<Nodo> pila = new ArrayList<>();
        Nodo n = raiz;
        while (n != null || !pila.isEmpty()) {
            while (n != null) { pila.add(n); n = n.izq; }
            n = pila.remove(pila.size()-1);
            visitar.accept(n.libro);
            n = n.der;
        }
    }
}
