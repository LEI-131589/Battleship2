
package battleship;

public class GodClass {

    // 12 atributos: ultrapassa o limite de 5
    private String nome;
    private String categoria;
    private int estado;
    private int nivel;
    private int pontos;
    private double saldo;
    private boolean ativo;
    private String descricao;
    private int experiencia;
    private int vidas;
    private double bonus;
    private String identificador;

    public GodClass() {
        this.nome = "Desconhecido";
        this.categoria = "Inicial";
        this.estado = 0;
        this.nivel = 1;
        this.pontos = 0;
        this.saldo = 0.0;
        this.ativo = true;
        this.descricao = "Sem descrição";
        this.experiencia = 0;
        this.vidas = 3;
        this.bonus = 0.0;
        this.identificador = "GOD-001";
    }

    public String getDescricao() {
        return this.descricao;
    }

    public String getIdentificador() {
        return this.identificador;
    }

    // 1. Complexidade elevada
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
            } else if (i % 11 == 0) {
                this.estado += 5;
            } else {
                this.estado += 6;
            }

            if (this.estado > 100) {
                this.estado = 0;
            }

            if (this.estado < 10 && this.nivel > 5) {
                this.experiencia += 2;
            }
        }

        if (this.nivel > 10) {
            this.nivel = 1;
        }
    }

    // 2. Complexidade elevada através de switch
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
            case 15: this.pontos += 150; break;
            default: this.pontos += 5; break;
        }

        if (this.pontos > 1000) {
            this.pontos = 1000;
        } else if (this.pontos < 0) {
            this.pontos = 0;
        }
    }

    // 3. Complexidade elevada
    public void avaliarCategoria(
            String tipo, boolean flag1, boolean flag2) {

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

        if (this.ativo && this.saldo > 100) {
            this.bonus += 10;
        } else if (!this.ativo && this.vidas > 0) {
            this.vidas--;
        }
    }

    // 4. Ciclos aninhados
    public void atualizarSaldo(double delta) {
        this.saldo += delta;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i == j) {
                    this.saldo += 1.0;
                } else if (i > j) {
                    this.saldo += 0.5;
                } else if (j % 2 == 0) {
                    this.saldo += 0.25;
                }
            }
        }

        if (this.saldo < 0) {
            this.saldo = 0;
        } else if (this.saldo > 10000) {
            this.saldo = 10000;
        }
    }

    // 5. Complexidade elevada
    public void validarEstadoAtivo(int verificacoes) {
        this.ativo = verificacoes > 0;
        int contador = 0;

        while (contador < verificacoes) {
            if (contador % 2 == 0) {
                this.nome = "ValorPar";
            } else {
                this.nome = "ValorDiferente";
            }

            if (this.ativo && this.saldo > 50.0) {
                this.pontos++;
            } else if (this.pontos < 5 && contador > 2) {
                this.ativo = false;
            } else if (contador == 5 || this.saldo < 0) {
                this.pontos--;
            }

            if (this.nivel > 5 && this.experiencia > 10) {
                this.bonus += 5;
            } else if (this.vidas <= 0 || !this.ativo) {
                this.estado = 0;
            }

            contador++;
        }
    }

    // 6. Métodos adicionais: ultrapassa o limite de 10

    public void adicionarExperiencia(int valor) {
        if (valor > 0 && this.ativo) {
            this.experiencia += valor;
        } else if (valor < 0 || !this.ativo) {
            this.experiencia = 0;
        }
    }

    public void perderVida() {
        if (this.vidas > 0) {
            this.vidas--;
        } else {
            this.ativo = false;
        }
    }

    public void recuperarVida(int quantidade) {
        if (quantidade > 0 && this.vidas < 10) {
            this.vidas = Math.min(this.vidas + quantidade, 10);
        } else if (this.vidas >= 10) {
            this.vidas = 10;
        } else {
            this.vidas = Math.max(this.vidas, 0);
        }
    }

    public void aplicarBonus(double valor) {
        if (valor > 0 && this.nivel >= 5) {
            this.bonus += valor;
        } else if (valor < 0 && this.bonus > 0) {
            this.bonus = 0;
        }
    }

    public void atualizarDescricao(String texto) {
        if (texto != null && !texto.isEmpty()) {
            this.descricao = texto;
        } else if (texto == null) {
            this.descricao = "Sem descrição";
        } else {
            this.descricao = "Descrição vazia";
        }
    }

    public void verificarDesempenho() {
        if (this.pontos > 500 && this.experiencia > 100) {
            this.categoria = "Especial";
        } else if (this.pontos < 100 || this.experiencia < 10) {
            this.categoria = "Inicial";
        }

        if (this.saldo > 5000 && this.ativo) {
            this.bonus += 100;
        } else if (this.saldo <= 0 || !this.ativo) {
            this.bonus = 0;
        }
    }

    public void reiniciarEstado() {
        if (this.vidas <= 0 || this.estado > 100) {
            this.estado = 0;
            this.vidas = 3;
            this.ativo = true;
        }

        if (this.pontos > 1000 || this.experiencia > 1000) {
            this.pontos = 0;
            this.experiencia = 0;
        }
    }

    public void processarIdentificador(int valor) {
        if (valor > 0 && valor % 2 == 0) {
            this.identificador = "PAR-" + valor;
        } else if (valor > 0) {
            this.identificador = "IMPAR-" + valor;
        } else {
            this.identificador = "INVALIDO";
        }
    }
    public void verificarLimites() {
        if (this.saldo < 0 || this.pontos < 0) {
            this.ativo = false;
        } else if (this.saldo > 10000 && this.pontos > 1000) {
            this.categoria = "Premium";
        }

        if (this.nivel > 10 || this.vidas > 10) {
            this.nivel = 1;
            this.vidas = 3;
        }
    }

    public static void main(String[] args) {
        GodClass gc = new GodClass();

        gc.processarNivel(10);
        gc.calcularPontuacao(5);
        gc.avaliarCategoria("Especial", true, false);
        gc.atualizarSaldo(50.5);
        gc.validarEstadoAtivo(3);

        gc.adicionarExperiencia(100);
        gc.perderVida();
        gc.recuperarVida(2);
        gc.aplicarBonus(20);
        gc.atualizarDescricao("Teste God Class");
        gc.verificarDesempenho();
        gc.reiniciarEstado();
        gc.processarIdentificador(10);
        gc.verificarLimites();

        String d = gc.getDescricao();
        String id = gc.getIdentificador();
        String n = gc.nome;
        String c = gc.categoria;

        // Uma operação condicional simples apenas para gastar as variáveis lidas
        if (d != null && id != null && n != null && c != null) {
            gc.experiencia++;
        }

    }
}
