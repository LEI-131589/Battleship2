package battleship;

public class GodClass {

    // 1. VIOLA O LIMITE DE ATRIBUTOS (7 atributos > Limite de 5)
    private String nome;
    private String categoria;
    private int estado;
    private int nivel;
    private int pontos;
    private double saldo;
    private boolean ativo;

    // Construtor (Complexidade = 1)
    public GodClass() {
        this.nome = "Desconhecido";
        this.categoria = "Inicial";
        this.estado = 0;
        this.nivel = 1;
        this.pontos = 0;
        this.saldo = 0.0;
        this.ativo = true;
    }

    // 2. Método com Complexidade ~8
    public void processarNivel(int iteracoes) {
        this.nivel++;
        for (int i = 0; i < iteracoes; i++) {
            if (i % 2 == 0) {
                this.estado += 1;
            } else if (i % 3 == 0) {
                this.estado += 2;
            } else if (i % 5 == 0) {
                this.estado += 3;
            } else if (i % 7 == 0) {
                this.estado += 4;
            } else {
                this.estado += 5;
            }
        }
        if (this.estado > 100) this.estado = 0;
        if (this.nivel > 10) this.nivel = 1;
    }

    // 3. Método com Complexidade ~17 (O grande responsável pelo volume, espalhado por 'cases')
    public void calcularPontuacao(int base) {
        this.pontos += base;
        switch (base) {
            case 1: this.pontos += 10; break;
            case 2: this.pontos += 20; break;
            case 3: this.pontos += 30; break;
            case 4: this.pontos += 40; break;
            case 5: this.pontos += 50; break;
            case 6: this.pontos += 60; break;
            case 7: this.pontos += 70; break;
            case 8: this.pontos += 80; break;
            case 9: this.pontos += 90; break;
            case 10: this.pontos += 100; break;
            case 11: this.pontos += 110; break;
            case 12: this.pontos += 120; break;
            case 13: this.pontos += 130; break;
            case 14: this.pontos += 140; break;
            default: this.pontos += 5; break;
        }
        if (this.pontos > 1000) {
            this.pontos = 1000;
        } else if (this.pontos < 0) {
            this.pontos = 0;
        }
    }

    // 4. Método com Complexidade ~11 (Devido aos && e ||)
    public void avaliarCategoria(String tipo, boolean flag1, boolean flag2) {
        this.categoria = tipo;
        if (tipo != null && (flag1 || flag2)) {
            this.nivel++;
        } else if (tipo == null && !flag1 && !flag2) {
            this.nivel--;
        }

        if (this.nivel > 5 || this.pontos > 500) {
            this.categoria = "Premium";
        } else if (this.nivel < 2 && this.pontos < 100) {
            this.categoria = "Iniciante";
        }
    }

    // 5. Método com Complexidade ~7 (Ciclos aninhados)
    public void atualizarSaldo(double delta) {
        this.saldo += delta;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i == j) {
                    this.saldo += 1.0;
                } else if (i > j) {
                    this.saldo += 0.5;
                }
            }
        }
        if (this.saldo < 0) {
            this.saldo = 0;
        } else if (this.saldo > 10000) {
            this.saldo = 10000;
        }
    }

    // 6. Método com Complexidade ~9 (Mantém o WMC alto mas sem redundâncias lógicas)
    public void validarEstadoAtivo(int verificacoes) {
        this.ativo = verificacoes > 0;
        int contador = 0;
        while (contador < verificacoes) {

            // Substitui "Ímpar" por "ValorDiferente" para evitar o Typo
            if (contador % 2 == 0) {
                this.nome = "ValorPar";
            } else {
                this.nome = "ValorDiferente";
            }

            // Lógica sem redundâncias (evita o "always true" e "already assigned")
            if (this.ativo && this.saldo > 50.0) {
                this.pontos += 1;
            } else if (this.pontos < 5 && contador > 2) {
                this.ativo = false;
            } else if (contador == 5 || this.saldo < 0) {
                this.pontos -= 1;
            }

            contador++;
        }
    }

    // O método main força a utilização de tudo, evitando warnings de "unused" (Complexidade = 1)
    public static void main(String[] args) {
        GodClass gc = new GodClass();
        gc.processarNivel(10);
        gc.calcularPontuacao(5);
        gc.avaliarCategoria("Especial", true, false);
        gc.atualizarSaldo(50.5);
        gc.validarEstadoAtivo(3);

        // Impressão apenas para justificar a existência dos dados na memória
        System.out.println("Fim do processo para " + gc.nome + " da categoria " + gc.categoria);
    }
}