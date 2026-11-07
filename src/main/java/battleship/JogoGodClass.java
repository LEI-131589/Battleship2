package battleship;

public class JogoGodClass {
    // Mais de 15 atributos (viola ClassWithTooManyFields)
    private String jogador1;
    private String jogador2;
    private int pontuacao1;
    private int pontuacao2;
    private boolean jogoAtivo;
    private int turnoAtual;
    private int tempoRestante;
    private String dificuldade;
    private int maxNavios;
    private int naviosDestruidos1;
    private int naviosDestruidos2;
    private boolean somAtivado;
    private String resolucaoEcra;
    private int tamanhoGrelhaX;
    private int tamanhoGrelhaY;
    private String ultimoMovimento;
    private boolean modoCheats;

    // Mais de 15 métodos (viola ClassWithTooManyMethods e possivelmente OverlyComplexClass)
    public void iniciarJogo() {}
    public void terminarJogo() {}
    public void pausarJogo() {}
    public void retomarJogo() {}
    public void validarMovimento() {}
    public void atualizarPontuacao() {}
    public void mudarTurno() {}
    public void verificarVencedor() {}
    public void desenharTabuleiro() {}
    public void reproduzirSomTiro() {}
    public void reproduzirSomAgua() {}
    public void guardarHistorico() {}
    public void carregarJogoSalvo() {}
    public void configurarDificuldade() {}
    public void processarInputRato() {}
    public void calcularEstatisticas() {}
    public void exibirMenuVitoria() {}

    @Override
    public String toString() {
        StringBuilder tabuleiro = new StringBuilder();
        tabuleiro.append("Tabuleiro Padrão (10x10):\n");
        for (int i = 0; i < 10; i++) {
            // Substitui o ciclo for interior por String.repeat()
            tabuleiro.append("+ ".repeat(10));
            tabuleiro.append("\n");
        }
        return tabuleiro.toString();
    }
}
