import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ArquivoUtils {
    private static final String ARQUIVO = "events.data";
    private static final String ARQUIVO_USUARIOS = "usuarios.data";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void salvarEventos(List<Evento> eventos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Evento e : eventos) {
                String linha = e.nome() + ";" +
                        e.endereco() + ";" +
                        e.categoria() + ";" +
                        e.horario().format(formatter) + ";" +
                        e.descricao();
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar eventos: " + e.getMessage());
        }
    }

    public static List<Evento> carregarEventos() {
        List<Evento> eventos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 5) {
                    String nome = partes[0];
                    String endereco = partes[1];
                    String categoria = partes[2];
                    LocalDateTime horario = LocalDateTime.parse(partes[3], formatter);
                    String descricao = partes[4];

                    Evento evento = new Evento(nome, endereco, categoria, horario, descricao);
                    eventos.add(evento);
                }
            }
        } catch (FileNotFoundException e) {
            // Primeiro uso, sem arquivo ainda — tudo certo
        } catch (IOException e) {
            System.out.println("Erro ao carregar eventos: " + e.getMessage());
        }

        return eventos;
    }

    public static void salvarUsuarios(List<Usuario> usuarios) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_USUARIOS))) {
            for (Usuario u : usuarios) {
                String linha = u.getNome() + ";" +
                        u.getEmail() + ";" +
                        u.getCidade() + ";" +
                        u.getEventosConfirmadosComoTexto();
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }

    public static List<Usuario> carregarUsuarios(List<Evento> eventosDisponiveis) {
        List<Usuario> usuarios = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_USUARIOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 4) {
                    Usuario u = criarUsuarioComEventos(partes, eventosDisponiveis);
                    usuarios.add(u);
                }
            }
        } catch (FileNotFoundException e) {
            // Primeiro uso, tudo certo
        } catch (IOException e) {
            System.out.println("Erro ao carregar usuários: " + e.getMessage());
        }

        return usuarios;
    }

    private static Usuario criarUsuarioComEventos(String[] partes, List<Evento> eventosDisponiveis) {
        String nome = partes[0];
        String email = partes[1];
        String cidade = partes[2];
        String eventosTexto = partes[3];

        Usuario u = new Usuario(nome, email, cidade);

        // Associar eventos confirmados
        String[] eventosNomes = eventosTexto.split("\\|");
        for (String nomeEvento : eventosNomes) {
            for (Evento e : eventosDisponiveis) {
                if (e.nome().equals(nomeEvento)) {
                    u.adicionarEventoConfirmado(e);
                }
            }
        }

        return u;
    }
}