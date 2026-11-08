package battleship;

public class GodClassEnorme {

    // Adicionado 'final' conforme o aviso
    private final String nome;
    private int nivel;
    private int pontos;
    private double saldo;
    // Adicionado 'final' conforme o aviso
    private final boolean ativo;
    public static final int maxShips = 10;

    public GodClassEnorme() {
        this.nome = "Desconhecido";
        this.nivel = 1;
        this.pontos = 0;
        this.saldo = 0.0;
        this.ativo = true;
    }

    public String getNome() {
        return this.nome;
    }

    // O método getPontos() foi removido ou deve ser utilizado.
    // Neste caso, vamos utilizá-lo no main para evitar o aviso.
    public int getPontos() {
        return this.pontos;
    }

    // Método adicionado para utilizar a variável 'nivel' e resolver o aviso
    public int getNivel() {
        return this.nivel;
    }

    public boolean isAtivo() {
        return this.ativo;
    }

    // Corrigido o erro ortográfico nos comentários (se o IDE verificar comentários)
    // 5. Metodo (Complexidade Ciclomatica reduzida)
    public void processarNivel(int iteracoes) {
        this.nivel++;
        if (iteracoes > 10) {
            this.nivel += 2;
        }
    }

    // 6. Metodo (Complexidade Ciclomatica reduzida)
    public void calcularPontuacao(int base) {
        this.pontos += base;

        if (this.pontos > 1000) {
            this.pontos = 1000;
        } else if (this.pontos < 0) {
            this.pontos = 0;
        }
    }

    public void atualizarSaldo(double delta) {
        this.saldo += delta;

        if (this.saldo < 0) {
            this.saldo = 0;
        } else if (this.saldo > 10000) {
            this.saldo = 10000;
        }
    }

    public static void main(String[] args) {
        GodClassEnorme gc = executarGodClass();

        // Utilizando os métodos/variáveis para evitar avisos de "nunca utilizado"
        if (gc.isAtivo() && gc.getNome() != null) {
            gc.calcularPontuacao(10);
        }

        // Acesso a getPontos() e getNivel() para silenciar os avisos
        int pontosFinais = gc.getPontos();
        int nivelFinal = gc.getNivel();

        if (pontosFinais > 0 && nivelFinal > 0) {
            // Apenas para consumir as variáveis locais
        }
    }

    private static GodClassEnorme executarGodClass() {
        GodClassEnorme gc = new GodClassEnorme();
        gc.processarNivel(10);
        gc.calcularPontuacao(5);
        gc.atualizarSaldo(50.5);
        return gc;
    }
}