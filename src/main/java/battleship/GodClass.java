package battleship;

public class GodClass {

    // 1. VIOLAÇÃO DE ATRIBUTOS (ClassWithTooManyFields)
    // O seu limite é 5. Aqui temos 6 atributos.
    private String nome;
    private String morada;
    private String email;
    private String telefone;
    private String numeroContribuinte;
    private double saldoConta; // O 6º atributo que provoca a falha!

    // 2. VIOLAÇÃO DE MÉTODOS (ClassWithTooManyMethods)
    // O seu limite é 5. Aqui temos 7 métodos (incluindo o complexo abaixo).
    public void gerirUtilizador() {}
    public void ligarBaseDados() {}
    public void enviarEmail() {}
    public void processarPagamento() {}
    public void gerarRelatorioMensal() {}
    public void calcularImpostos() {} // O 6º método que provoca a falha!

    // 3. VIOLAÇÃO DE COMPLEXIDADE (OverlyComplexClass)
    // O seu limite WMC (Weighted Methods per Class) é 13.
    // Apenas este método, com os seus múltiplos ifs, fors e switch, tem uma
    // complexidade superior a 13, garantindo que a regra dispara.
    public void superMetodoComplexo(int valor, boolean ativo) {
        if (ativo) {
            for (int i = 0; i < 10; i++) {
                if (i % 2 == 0) {
                    System.out.println("Par");
                } else if (i % 3 == 0) {
                    System.out.println("Múltiplo de 3");
                } else {
                    System.out.println("Outro");
                }
            }
        }

        if (valor > 100) {
            switch (valor) {
                case 101: System.out.println("101"); break;
                case 102: System.out.println("102"); break;
                case 103: System.out.println("103"); break;
                case 104: System.out.println("104"); break;
                case 105: System.out.println("105"); break;
                case 106: System.out.println("106"); break;
                case 107: System.out.println("107"); break;
                case 108: System.out.println("108"); break;
                case 109: System.out.println("109"); break;
                case 110: System.out.println("110"); break;
                default: System.out.println("Maior que 110"); break;
            }
        }
    }
}
