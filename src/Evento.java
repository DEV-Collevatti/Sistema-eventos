import java.time.LocalDateTime;

public class Evento {
    private final String nome;
    private final String endereco;
    private final String categoria;
    private final LocalDateTime horario;
    private final String descricao;

    public Evento(String nome, String endereco, String categoria, LocalDateTime horario, String descricao) {
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.horario = horario;
        this.descricao = descricao;
    }

    // Getters
    public String getNome() { return nome; }
    public String getEndereco() { return endereco; }
    public String getCategoria() { return categoria; }
    public LocalDateTime getHorario() { return horario; }
    public String getDescricao() { return descricao; }

    // Métodos para verificar status do evento
    public boolean estaAcontecendoAgora() {
        LocalDateTime agora = LocalDateTime.now();
        return horario.toLocalDate().equals(agora.toLocalDate()) && horario.getHour() == agora.getHour();
    }

    public boolean jaOcorreu() {
        return LocalDateTime.now().isAfter(horario);
    }

    @Override
    public String toString() {
        return nome + " - " + categoria + " - " + horario + "\n" + endereco + "\n" + descricao;
    }
}