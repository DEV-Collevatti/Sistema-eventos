import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private final String nome;
    private final String email;
    private final String cidade;
    private final List<Evento> eventosConfirmados;

    public Usuario(String nome, String email, String cidade) {
        this.nome = nome;
        this.email = email;
        this.cidade = cidade;
        this.eventosConfirmados = new ArrayList<>();
    }

    public void confirmarParticipacao(Evento evento) {
        if (!eventosConfirmados.contains(evento)) {
            eventosConfirmados.add(evento);
        }
    }

    public void cancelarParticipacao(Evento evento) {
        eventosConfirmados.remove(evento);
    }

    public void listarParticipacoes() {
        System.out.println("Eventos confirmados por " + nome + ":");
        for (Evento e : eventosConfirmados) {
            System.out.println(e);
        }
    }

    public String getEventosConfirmadosComoTexto() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < eventosConfirmados.size(); i++) {
            sb.append(eventosConfirmados.get(i).getNome());
            if (i < eventosConfirmados.size() - 1) sb.append("|");
        }
        return sb.toString();
    }

    public void adicionarEventoConfirmado(Evento evento) {
        if (!eventosConfirmados.contains(evento)) {
            eventosConfirmados.add(evento);
        }
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCidade() {
        return cidade;
    }

    public List<Evento> getEventosConfirmados() {
        return eventosConfirmados;
    }
}
