import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SistemaEventos {
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Evento> eventos = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private Usuario usuarioLogado = null;

    public void iniciar() {
        eventos = ArquivoUtils.carregarEventos();
        usuarios = ArquivoUtils.carregarUsuarios(eventos);
        System.out.println("=== Bem-vindo ao Sistema de Eventos ===");

        boolean executando = true;
        while (executando) {
            System.out.println("\nMenu:");
            System.out.println("1. Cadastrar usuário");
            System.out.println("2. Login");
            System.out.println("3. Cadastrar evento");
            System.out.println("4. Listar eventos");
            System.out.println("5. Confirmar participação");
            System.out.println("6. Cancelar participação");
            System.out.println("7. Meus eventos confirmados");
            System.out.println("8. Ver eventos em andamento ou passados");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            switch (opcao) {
                case 1 -> cadastrarUsuario();
                case 2 -> fazerLogin();
                case 3 -> cadastrarEvento();
                case 4 -> listarEventos();
                case 5 -> confirmarParticipacao();
                case 6 -> cancelarParticipacao();
                case 7 -> listarMeusEventos();
                case 8 -> mostrarStatusEventos();
                case 0 -> {
                    ArquivoUtils.salvarEventos(eventos);
                    ArquivoUtils.salvarUsuarios(usuarios);
                    System.out.println("Encerrando o sistema.");
                    executando = false;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void cadastrarUsuario() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();

        usuarios.add(new Usuario(nome, email, cidade));
        System.out.println("Usuário cadastrado com sucesso!");
    }

    private void fazerLogin() {
        System.out.print("Digite seu email: ");
        String email = scanner.nextLine();

        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                usuarioLogado = u;
                System.out.println("Login realizado como " + u.getNome());
                return;
            }
        }
        System.out.println("Usuário não encontrado.");
    }

    private void cadastrarEvento() {
        if (usuarioLogado == null) {
            System.out.println("Faça login primeiro.");
            return;
        }

        System.out.print("Nome do evento: ");
        String nome = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Categoria (Festa, Show, Esporte...): ");
        String categoria = scanner.nextLine();
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Data e hora (formato: yyyy-MM-dd HH:mm): ");
        String dataHoraStr = scanner.nextLine();

        LocalDateTime horario = LocalDateTime.parse(dataHoraStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        Evento evento = new Evento(nome, endereco, categoria, horario, descricao);
        eventos.add(evento);

        System.out.println("Evento cadastrado com sucesso!");
    }

    private void listarEventos() {
        System.out.println("\n=== Eventos cadastrados ===");
        eventos.sort(Comparator.comparing(Evento::horario));
        for (int i = 0; i < eventos.size(); i++) {
            System.out.println("[" + i + "] " + eventos.get(i));
        }
    }

    private void confirmarParticipacao() {
        if (usuarioLogado == null) {
            System.out.println("Faça login primeiro.");
            return;
        }
        listarEventos();
        System.out.print("Escolha o índice do evento: ");
        int i = scanner.nextInt();
        scanner.nextLine();

        if (i >= 0 && i < eventos.size()) {
            usuarioLogado.confirmarParticipacao(eventos.get(i));
            System.out.println("Participação confirmada.");
        } else {
            System.out.println("Índice inválido.");
        }
    }

    private void cancelarParticipacao() {
        if (usuarioLogado == null) {
            System.out.println("Faça login primeiro.");
            return;
        }

        List<Evento> lista = usuarioLogado.getEventosConfirmados();
        for (int i = 0; i < lista.size(); i++) {
            System.out.println("[" + i + "] " + lista.get(i));
        }

        System.out.print("Escolha o índice do evento a cancelar: ");
        int i = scanner.nextInt();
        scanner.nextLine();

        if (i >= 0 && i < lista.size()) {
            usuarioLogado.cancelarParticipacao(lista.get(i));
            System.out.println("Participação cancelada.");
        } else {
            System.out.println("Índice inválido.");
        }
    }

    private void listarMeusEventos() {
        if (usuarioLogado == null) {
            System.out.println("Faça login primeiro.");
            return;
        }
        usuarioLogado.listarParticipacoes();
    }

    private void mostrarStatusEventos() {
        System.out.println("\nStatus dos eventos:");

        for (Evento evento : eventos) {
            if (evento.estaAcontecendoAgora()) {
                System.out.println(evento.nome() + ": ACONTECENDO AGORA");
            } else if (evento.jaOcorreu()) {
                System.out.println(evento.nome() + ": JÁ OCORREU");
            } else {
                System.out.println(evento.nome() + ": FUTURO");
            }
        }
    }
}