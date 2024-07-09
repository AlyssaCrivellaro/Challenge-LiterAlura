package com.challengeliteratura.challengeliteratura.entity;

import com.challengeliteratura.challengeliteratura.model.Autor;
import com.challengeliteratura.challengeliteratura.model.Livro;
import com.challengeliteratura.challengeliteratura.util.CadenasUtil;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Livro")
public class LivroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String idioma;
    private Integer Entregas;
    @OneToOne(mappedBy = "livros", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private AutorEntity autor;

    public LivroEntity() {

    }

    public LivroEntity(Livro livro) {
        this.titulo = CadenasUtil.limitarLongitud(livro.title(), 200);
        this.Entregas = livro.download();
        if (!livro.idioma().isEmpty())
            this.idioma = livro.idiomas().get(0);
        if (!livro.autores().isEmpty()) {
            for (Autor autor : livro.autores()) {
                this.autor = new AutorEntity(autor);
                break;
            }
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getEntregas() {
        return entregas;
    }

    public void setEntregas(Integer entregas) {
        this.entregas = entregas;
    }

    @Override
    public String toString() {
        return "LivroEntity [id=" + id + ", titulo=" + titulo + ", idioma=" + idioma + ", entregas=" + entregas
                + ", autores=" + autor + "]";
    }

    public AutorEntity getAutor() {
        return autor;
    }

    public void setAutor(AutorEntity autor) {
        this.autor = autor;
    }

}
