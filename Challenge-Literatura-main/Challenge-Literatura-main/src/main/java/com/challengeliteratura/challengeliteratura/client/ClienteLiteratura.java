package com.challengeliteratura.challengeliteratura.client;

import java.util.List;
import java.util.Scanner;

import com.challengeliteratura.challengeliteratura.entity.AutorEntity;
import com.challengeliteratura.challengeliteratura.entity.LivroEntity;
import com.challengeliteratura.challengeliteratura.mapper.ConverterDados;
import com.challengeliteratura.challengeliteratura.model.Resposta;
import com.challengeliteratura.challengeliteratura.repository.AutorRepository;
import com.challengeliteratura.challengeliteratura.repository.LivroRepository;
import com.challengeliteratura.challengeliteratura.service.ConsumoAPI;

public class ClienteLiteratura {

    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConverterDados conversor = new ConverterDados();

    private LivroRepository livroRepositorio;
    private AutorRepository autorRepositorio;

    public ClienteLiteratura(LivroRepository livroRepositorio, AutorRepository autorRepositorio) {
        this.livroRepositorio = livroRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public void menu() {
        var opção = -1;
        while (opção != 0) {
            var menu = """
					Escolha a opção atraves do numero:
						1.- Buscar livro através do titulo
						2.- Listar livros registrados
						3.- Listar autores registrados
						4.- Escolher autores por ano
						5.- Escolher autores por idioma
						0 - Sair
						""";
            System.out.println(menu);
            opção = teclado.nextInt();
            teclado.nextLine();

            switch (opção) {
                case 1:
                    buscarLivroWeb();
                    break;
                case 2:
                    buscarLivros();
                    break;
                case 3:
                    buscarAutores();
                    break;
                case 4:
                    buscarAutoresVivo();
                    break;
                case 5:
                    buscarPorIdiomas();
                    break;
                case 0:
                    System.out.println("Obrigado! Volte mais tarde!");
                    break;
                default:
                    System.out.println("Opção Invalida");
            }
        }

    }

    private void buscarLivros() {

        List<LivroEntity> livros = livroRepositorio.findAll();

        if (!livros.isEmpty()) {

            for (LivroEntity livro : livros) {
                System.out.println("\n\n---------- LIVROS -------\n");
                System.out.println(" Titulo: " + livro.getTitulo());
                System.out.println(" Autor: " + livro.getAutor().getNome());
                System.out.println(" Idioma: " + livro.getIdioma());
                System.out.println(" Entregas: " + livro.getEntregas());
                System.out.println("\n-------------------------\n\n");
            }

        } else {
            System.out.println("\n\n ----- Resulatados não Encontrados ---- \n\n");
        }

    }

    private void buscarAutores() {
        List<AutorEntity> autores = autorRepositorio.findAll();

        if (!autores.isEmpty()) {
            for (AutorEntity autor : autores) {
                System.out.println("\n\n---------- Autores -------\n");
                System.out.println(" Nome: " + autor.getNome());
                System.out.println(" Data de Nascimento: " + autor.getDataNascimento());
                System.out.println(" Data de Falecimento: " + autor.getDataFalecimento());
                System.out.println(" Livros: " + autor.getLivros().getTitulo());
                System.out.println("\n-------------------------\n\n");
            }
        } else {
            System.out.println("\n\n ----- Resultados não Encontrados ---- \n\n");

        }

    }

    private void buscarAutoresVivo() {
        System.out.println("Escreva o ano da busca: ");
        var ano = teclado.nextInt();
        teclado.nextLine();

        List<AutorEntity> autores = autorRepositorio.findForYear(ano);

        if (!autores.isEmpty()) {
            for (AutorEntity autor : autores) {
                System.out.println("\n\n---------- Autores Ano -------\n");
                System.out.println(" Nome: " + autor.getNome());
                System.out.println(" Data de nascimento: " + autor.getDataNasciemnto());
                System.out.println(" Data de falecimento: " + autor.getDataFalecimento());
                System.out.println(" Livros: " + autor.getLivros().getTitulo());
                System.out.println("\n-------------------------\n\n");
            }
        } else {
            System.out.println("\n\n ----- Resultados não Encontrados ---- \n\n");

        }
    }

    private void buscarPorIdiomas() {

        var menu = """
				Selecione o Idioma:
					1.- Português
					2.- Inglês
				
					""";
        System.out.println(menu);
        var idioma = teclado.nextInt();
        teclado.nextLine();

        String Selecionar = "";

        if(idioma == 1) {
            Selecionar = "pt";
        } else 	if(idioma == 2) {
            Selecionar = "en";
        }

        List<LivroEntity> livros = livroRepositorio.findForIdioma(Selecionar);

        if (!livros.isEmpty()) {

            for (LivroEntity livro : livros) {
                System.out.println("\n\n---------- LIVROS POR IDIOMA-------\n");
                System.out.println(" Titulo: " + livro.getTitulo());
                System.out.println(" Autor: " + livro.getAutor().getNome());
                System.out.println(" Idioma: " + livro.getIdioma());
                System.out.println(" Entrega: " + livro.getEntregas());
                System.out.println("\n-------------------------\n\n");
            }

        } else {
            System.out.println("\n\n ----- Resultados não Encontrados ---- \n\n");
        }


    }

    private void buscarLivroWeb() {
        Resposta dados = getDadosSerie();

        if (!dados.results().isEmpty()) {

            LivroEntity livro = new LivroEntity(dados.results().get(0));
            livro = livroRepositorio.save(livro);

        }

        System.out.println("Dados: ");
        System.out.println(dados);
    }

    private Resposta getDadosSerie() {
        System.out.println("Qual é o livro que deseja buscar?: ");
        var titulo = teclado.nextLine();
        titulo = titulo.replace(" ", "%20");
        System.out.println("Titulo : " + titulo);
        System.out.println(URL_BASE + titulo);
        var json = consumoApi.obterDatos(URL_BASE + titulo);
        System.out.println(json);
        Resposta dados = conversor.obterDados(json, Resposta.class);
        return dados;
    }

}

