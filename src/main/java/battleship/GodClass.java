package battleship;
public class GodClass {

    // 1. VIOLA O LIMITE DE ATRIBUTOS (7 atributos > Limite de 5)
    private String nome;
    private String morada;
    private String email;
    private int telefone;
    private int nif;
    private double saldo;
    private boolean ativo;

    // Construtor (conta como método)
    public GodClass() {
        this.nome = "Sistema";
        this.morada = "Rua A";
        this.email = "a@a.com";
        this.telefone = 999;
        this.nif = 111;
        this.saldo = 0.0;
        this.ativo = true;
    }

    // 2. VIOLA O LIMITE DE MÉTODOS (7 métodos > Limite de 5)
    // Além disso, estes métodos alteram o estado, evitando "Field may be final"
    public void atualizarDados(String novoNome, String novaMorada) {
        this.nome = novoNome;
        this.morada = novaMorada;
        System.out.println("Dados: " + this.nome + " na " + this.morada);
    }

    public void atualizarContactos(String novoEmail, int novoTelefone) {
        this.email = novoEmail;
        this.telefone = novoTelefone;
        System.out.println("Contactos: " + this.email + " - " + this.telefone);
    }

    public void faturar(int novoNif, double valorAcrescentar) {
        this.nif = novoNif;
        this.saldo += valorAcrescentar; // Lê e altera, impossibilitando "local variable"
        System.out.println("NIF " + this.nif + " tem saldo de " + this.saldo);
    }

    public void alternarEstado() {
        this.ativo = !this.ativo;
        System.out.println("Estado ativo? " + this.ativo);
    }

    // 3. VIOLA O LIMITE DE COMPLEXIDADE (WMC > 13)
    public void superMetodoComplexo(int valor) {
        this.saldo += valor;

        if (this.ativo) {
            for (int i = 0; i < 10; i++) {
                if (i % 2 == 0) {
                    System.out.println("Par");
                } else if (i % 3 == 0) {
                    System.out.println("Multiplo 3");
                } else {
                    System.out.println("Outro");
                }
            }
        }

        if (valor > 100) {
            switch (valor) {
                case 101: System.out.println("1"); break;
                case 102: System.out.println("2"); break;
                case 103: System.out.println("3"); break;
                case 104: System.out.println("4"); break;
                case 105: System.out.println("5"); break;
                case 106: System.out.println("6"); break;
                case 107: System.out.println("7"); break;
                case 108: System.out.println("8"); break;
                case 109: System.out.println("9"); break;
                case 110: System.out.println("10"); break;
                default: System.out.println("Max"); break;
            }
        }
    }

    // O método Main força a utilização de tudo, evitando o warning de "Unused declaration"
    public static void main(String[] args) {
        GodClass gc = new GodClass();
        gc.atualizarDados("Novo Sistema", "Rua B");
        gc.atualizarContactos("b@b.com", 123456);
        gc.faturar(999999, 10.50);
        gc.alternarEstado();
        gc.superMetodoComplexo(105);
    }
}