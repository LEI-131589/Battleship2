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
    public void iniciarJogo() {
        this.jogoAtivo = true;
        this.pontuacao1 = 0;
        this.pontuacao2 = 0;
        this.turnoAtual = 1;
        this.naviosDestruidos1 = 0;
        this.naviosDestruidos2 = 0;
        this.tempoRestante = 3600;
        System.out.println("O jogo foi iniciado!");
    }

    public void terminarJogo() {
        this.jogoAtivo = false;
        System.out.println("O jogo terminou!");
        if (this.pontuacao1 > this.pontuacao2) {
            System.out.println("Vitória do Jogador 1!");
        } else if (this.pontuacao2 > this.pontuacao1) {
            System.out.println("Vitória do Jogador 2!");
        } else {
            System.out.println("Empate!");
        }
    }

    public void pausarJogo() {
        if (this.jogoAtivo) {
            this.jogoAtivo = false;
            System.out.println("O jogo foi pausado!");
            System.out.println("Tempo restante: " + this.tempoRestante + " segundos.");
        } else {
            System.out.println("O jogo já se encontra parado ou não foi iniciado.");
        }
    }

    public void retomarJogo() {
        if (!this.jogoAtivo) {
            this.jogoAtivo = true;
            System.out.println("O jogo foi retomado!");
            System.out.println("É o turno do jogador: " + this.turnoAtual);
        } else {
            System.out.println("O jogo já está em curso.");
        }
    }
    public void validarMovimento() {
        if (this.ultimoMovimento != null && !this.ultimoMovimento.isEmpty()) {
            System.out.println("A validar o movimento: " + this.ultimoMovimento);
            // Simulação de validação utilizando o tamanho da grelha
            if (this.tamanhoGrelhaX > 0 && this.tamanhoGrelhaY > 0) {
                System.out.println("Movimento encontra-se dentro dos limites da grelha (" + this.tamanhoGrelhaX + "x" + this.tamanhoGrelhaY + ").");
            }
        } else {
            System.out.println("Nenhum movimento registado para validar.");
        }
    }

    public void atualizarPontuacao() {
        if (this.turnoAtual == 1) {
            this.pontuacao1 += 100;
            this.naviosDestruidos2++;
            System.out.println("Pontuação atualizada! Jogador 1 tem agora: " + this.pontuacao1 + " pontos.");
        } else {
            this.pontuacao2 += 100;
            this.naviosDestruidos1++;
            System.out.println("Pontuação atualizada! Jogador 2 tem agora: " + this.pontuacao2 + " pontos.");
        }
    }

    public void mudarTurno() {
        if (this.turnoAtual == 1) {
            this.turnoAtual = 2;
            System.out.println("Turno alterado. É agora a vez do jogador 2 (" + this.jogador2 + ").");
        } else {
            this.turnoAtual = 1;
            System.out.println("Turno alterado. É agora a vez do jogador 1 (" + this.jogador1 + ").");
        }
    }
    public void verificarVencedor() {
        if (this.maxNavios > 0) {
            if (this.naviosDestruidos1 >= this.maxNavios) {
                System.out.println("O jogador 1 (" + this.jogador1 + ") venceu a partida!");
                this.jogoAtivo = false;
            } else if (this.naviosDestruidos2 >= this.maxNavios) {
                System.out.println("O jogador 2 (" + this.jogador2 + ") venceu a partida!");
                this.jogoAtivo = false;
            } else {
                System.out.println("Ainda não há vencedor. O jogo continua!");
            }
        } else {
            System.out.println("Erro: O número máximo de navios não está configurado.");
        }
    }

    public void desenharTabuleiro() {
        System.out.println("A renderizar a interface gráfica na resolução: " + this.resolucaoEcra);
        System.out.println("Grelha atual configurada para: " + this.tamanhoGrelhaX + "x" + this.tamanhoGrelhaY);
        // Aproveitamos o método toString já existente na classe para imprimir o tabuleiro base
        System.out.println(this.toString());
    }

    public void reproduzirSomTiro() {
        if (this.somAtivado) {
            System.out.println("🎵 [Efeito Sonoro]: BOOM! Tiro disparado.");
        } else {
            System.out.println("(Efeitos sonoros desativados - disparo silencioso)");
        }
    }
    public void reproduzirSomAgua() {
        if (this.somAtivado) {
            System.out.println("🎵 [Efeito Sonoro]: SPLASH! Tiro na água.");
        } else {
            System.out.println("(Efeitos sonoros desativados - splash silencioso)");
        }
    }

    public void guardarHistorico() {
        System.out.println("A guardar o histórico e estado atual da partida...");
        System.out.println("Dificuldade: " + this.dificuldade);
        System.out.println("Progresso: " + this.jogador1 + " (" + this.pontuacao1 + " pts) vs " + this.jogador2 + " (" + this.pontuacao2 + " pts)");
        System.out.println("Estado do modo cheats: " + (this.modoCheats ? "Ativado" : "Desativado"));
        System.out.println("Histórico guardado com sucesso no disco!");
    }

    public void carregarJogoSalvo() {
        System.out.println("A procurar ficheiro de gravação...");
        // Simulação de carregamento de dados
        this.jogoAtivo = true;
        this.tempoRestante = 1800; // Simula que o jogo foi gravado a meio
        System.out.println("Jogo carregado com sucesso!");
        System.out.println("Os jogadores " + this.jogador1 + " e " + this.jogador2 + " podem retomar a partida.");
        System.out.println("Tempo restante restaurado para: " + this.tempoRestante + " segundos.");
    }
    public void configurarDificuldade() {
        if (this.dificuldade != null && this.dificuldade.equalsIgnoreCase("Dificil")) {
            this.maxNavios = 10;
            this.tempoRestante = 1800;
            System.out.println("Dificuldade configurada para: Difícil. Navios: " + this.maxNavios + ", Tempo: " + this.tempoRestante);
        } else {
            this.dificuldade = "Normal";
            this.maxNavios = 5;
            this.tempoRestante = 3600;
            System.out.println("Dificuldade configurada para: Normal. Navios: " + this.maxNavios + ", Tempo: " + this.tempoRestante);
        }
    }

    public void processarInputRato() {
        if (this.jogoAtivo) {
            // Simulação de clique numa coordenada
            this.ultimoMovimento = "X: 4, Y: 5";
            System.out.println("Input do rato processado. Último movimento registado: " + this.ultimoMovimento);
        } else {
            System.out.println("O jogo não está ativo. O input do rato foi ignorado.");
        }
    }

    public void calcularEstatisticas() {
        System.out.println("--- Estatísticas Atuais ---");
        System.out.println("Jogador 1 (" + this.jogador1 + "): " + this.pontuacao1 + " pontos, " + this.naviosDestruidos1 + " navios destruídos.");
        System.out.println("Jogador 2 (" + this.jogador2 + "): " + this.pontuacao2 + " pontos, " + this.naviosDestruidos2 + " navios destruídos.");

        int totalNaviosDestruidos = this.naviosDestruidos1 + this.naviosDestruidos2;
        System.out.println("Total de navios destruídos no jogo: " + totalNaviosDestruidos);
    }

    public void exibirMenuVitoria() {
        System.out.println("=================================");
        System.out.println("         MENU DE VITÓRIA         ");
        System.out.println("=================================");

        if (this.pontuacao1 > this.pontuacao2) {
            System.out.println("🏆 PARABÉNS " + this.jogador1 + "! És o grande vencedor!");
        } else if (this.pontuacao2 > this.pontuacao1) {
            System.out.println("🏆 PARABÉNS " + this.jogador2 + "! És o grande vencedor!");
        } else {
            System.out.println("🤝 FOI UM EMPATE! Ambos jogaram bem.");
        }

        System.out.println("\n--- Resumo da Partida ---");
        System.out.println(this.jogador1 + ": " + this.pontuacao1 + " pts (" + this.naviosDestruidos1 + " navios afundados)");
        System.out.println(this.jogador2 + ": " + this.pontuacao2 + " pts (" + this.naviosDestruidos2 + " navios afundados)");
        System.out.println("==================================");
    }

    @Override
    public String toString() {
        StringBuilder tabuleiro = new StringBuilder();
        tabuleiro.append("Tabuleiro Padrão (10x10):\n");
        for (int i = 0; i < 10; i++) {
            // Substitui o ciclo for interior por String.repeat()
            tabuleiro.append("~ ".repeat(10));
            tabuleiro.append("\n");
        }
        return tabuleiro.toString();
    }
}
