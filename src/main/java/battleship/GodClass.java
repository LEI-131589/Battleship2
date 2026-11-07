package battleship;

public class GodClass {

    // 6 atributos (Ultrapassa o limite de 5)
    // Já não são detetados como "final" ou "local" porque mudam de valor e são usados por toda a classe
    private String nome;
    private String morada;
    private String email;
    private String telefone;
    private String numeroContribuinte;
    private double saldoConta;

    // Construtor (conta como método, ajuda a ultrapassar o limite NOM)
    public GodClass() {
        this.nome = "Sistema Central";
        this.morada = "Rua do Jogo, 123";
        this.email = "admin@jogo.com";
        this.telefone = "912345678";
        this.numeroContribuinte = "999999999";
        this.saldoConta = 1500.00;
    }

    // Métodos que alteram o estado da classe
    public void gerirUtilizador(String novoNome, String novoTelefone) {
        this.nome = novoNome;           // Reatribuição evita o aviso "may be final"
        this.telefone = novoTelefone;
        System.out.println("A gerir utilizador: " + nome + " | Contacto: " + telefone);
    }

    public void ligarBaseDados(String novaMorada) {
        this.morada = novaMorada;
        System.out.println("A ligar à BD baseada na morada: " + morada);
    }

    public void enviarEmail(String novoEmail) {
        this.email = novoEmail;
        System.out.println("A enviar relatório de segurança para: " + email);
    }

    public void processarPagamento(double valor) {
        this.saldoConta -= valor;
        System.out.println("Pagamento processado. Saldo atual: " + saldoConta);
    }

    public void gerarRelatorioMensal(String novoNif) {
        this.numeroContribuinte = novoNif;
        System.out.println("Relatório do NIF: " + numeroContribuinte + " com saldo " + saldoConta);
    }

    // Método complexo que garante que a WMC ultrapassa os 13
    public void superMetodoComplexo(int valor, boolean ativo) {
        if (ativo) {
            for (int i = 0; i < 10; i++) {
                if (i % 2 == 0) {
                    System.out.println("Iteração par: " + i);
                } else if (i % 3 == 0) {
                    System.out.println("Iteração múltiplo de 3: " + i);
                } else {
                    System.out.println("Outra iteração: " + i);
                }
            }
        }

        if (valor > 100) {
            switch (valor) {
                case 101: System.out.println("Opção 101 acionada"); break;
                case 102: System.out.println("Opção 102 acionada"); break;
                case 103: System.out.println("Opção 103 acionada"); break;
                case 104: System.out.println("Opção 104 acionada"); break;
                case 105: System.out.println("Opção 105 acionada"); break;
                case 106: System.out.println("Opção 106 acionada"); break;
                case 107: System.out.println("Opção 107 acionada"); break;
                case 108: System.out.println("Opção 108 acionada"); break;
                case 109: System.out.println("Opção 109 acionada"); break;
                case 110: System.out.println("Opção 110 acionada"); break;
                default: System.out.println("Valor limite superior acionado"); break;
            }
        }
    }
}