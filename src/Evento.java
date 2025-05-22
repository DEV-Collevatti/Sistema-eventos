import java.time.LocalDateTime;

public record Evento(
        String nome,
        String endereco,
        String categoria,
        LocalDateTime horario,
        String descricao
) {
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