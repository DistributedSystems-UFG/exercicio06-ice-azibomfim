import com.zeroc.Ice.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String hostRemoto = "3.82.236.30";
        String hostLocal  = "localhost";
        String portaFunctions   = "5678";
        String portaCalculadora = "5679";

        try (Communicator communicator = Util.initialize(args)) {

            // ---- Conectar ao servidor Functions (porta 5678) ----
            Demo.FunctionsPrx funcObj = conectarFunctions(communicator, hostRemoto, hostLocal, portaFunctions);

            // ---- Conectar ao servidor Calculadora (porta 5679) ----
            Demo.CalculadoraPrx calcObj = conectarCalculadora(communicator, hostRemoto, hostLocal, portaCalculadora);

            Scanner scanner = new Scanner(System.in);

            if (funcObj != null) {
                System.out.println("\n========== FUNCTIONS ==========");
                executarLogicaFunctions(funcObj, scanner);
            } else {
                System.out.println("Não foi possível conectar ao servidor Functions.");
            }

            if (calcObj != null) {
                System.out.println("\n========== CALCULADORA ==========");
                executarLogicaCalculadora(calcObj, scanner);
            } else {
                System.out.println("Não foi possível conectar ao servidor Calculadora.");
            }

        } catch (LocalException e) {
            e.printStackTrace();
        }
    }

    // ----------------------------------------------------------------
    // Helpers de conexão
    // ----------------------------------------------------------------

    private static Demo.FunctionsPrx conectarFunctions(
            Communicator comm, String remoto, String local, String porta) {
        try {
            System.out.println("Functions: tentando " + remoto + ":" + porta + "...");
            ObjectPrx base = comm.stringToProxy("SimpleFunctions:tcp -h " + remoto + " -p " + porta);
            return Demo.FunctionsPrx.checkedCast(base);
        } catch (ConnectTimeoutException | ConnectionRefusedException e) {
            try {
                System.out.println("Remoto offline. Tentando localhost:" + porta + "...");
                ObjectPrx base = comm.stringToProxy("SimpleFunctions:tcp -h " + local + " -p " + porta);
                return Demo.FunctionsPrx.checkedCast(base);
            } catch (ConnectionRefusedException ex) {
                System.err.println("Sem conexão Functions em nenhum host.");
                return null;
            }
        }
    }

    private static Demo.CalculadoraPrx conectarCalculadora(
            Communicator comm, String remoto, String local, String porta) {
        try {
            System.out.println("Calculadora: tentando " + remoto + ":" + porta + "...");
            ObjectPrx base = comm.stringToProxy("SimpleCalc1:tcp -h " + remoto + " -p " + porta);
            return Demo.CalculadoraPrx.checkedCast(base);
        } catch (ConnectTimeoutException | ConnectionRefusedException e) {
            try {
                System.out.println("Remoto offline. Tentando localhost:" + porta + "...");
                ObjectPrx base = comm.stringToProxy("SimpleCalc1:tcp -h " + local + " -p " + porta);
                return Demo.CalculadoraPrx.checkedCast(base);
            } catch (ConnectionRefusedException ex) {
                System.err.println("Sem conexão Calculadora em nenhum host.");
                return null;
            }
        }
    }

    // ----------------------------------------------------------------
    // Lógica Functions — chama todos os novos métodos
    // ----------------------------------------------------------------

    private static void executarLogicaFunctions(Demo.FunctionsPrx obj, Scanner scanner) {
        // printString
        System.out.println(obj.printString("Hello do cliente Java! Conexão bem-sucedida!"));

        // cumprimentar
        System.out.print("\nDigite seu nome: ");
        String nome = scanner.nextLine();
        System.out.println(obj.cumprimentar(nome));

        // contarVogais (novo)
        System.out.print("\nDigite um texto para contar vogais: ");
        String textoVogais = scanner.nextLine();
        System.out.println(obj.contarVogais(textoVogais));

        // converterTemperatura (novo)
        System.out.print("\nDigite uma temperatura em Celsius para converter: ");
        String celsius = scanner.nextLine();
        System.out.println(obj.converterTemperatura(celsius));

        // calcularIMC (novo)
        System.out.println("\n--- Cálculo de IMC ---");
        System.out.print("Informe seu peso em kg (ex: 70.5): ");
        String peso = scanner.nextLine();
        System.out.print("Informe sua altura em metros (ex: 1.75): ");
        String altura = scanner.nextLine();
        System.out.println(obj.calcularIMC(peso, altura));
    }

    // ----------------------------------------------------------------
    // Lógica Calculadora — somar, fatorial, ehPrimo
    // ----------------------------------------------------------------

    private static void executarLogicaCalculadora(Demo.CalculadoraPrx obj, Scanner scanner) {
        // somar
        System.out.print("\nDigite números separados por vírgula para somar (ex: 3,7,2): ");
        String numeros = scanner.nextLine();
        System.out.println(obj.somar(numeros));

        // fatorial
        System.out.print("\nDigite um inteiro para calcular o fatorial: ");
        String numFat = scanner.nextLine();
        System.out.println(obj.fatorial(numFat));

        // ehPrimo
        System.out.print("\nDigite um inteiro para verificar se é primo: ");
        String numPrimo = scanner.nextLine();
        System.out.println(obj.ehPrimo(numPrimo));
    }
}
