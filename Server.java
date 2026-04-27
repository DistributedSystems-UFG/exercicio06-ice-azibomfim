import com.zeroc.Ice.*;

public class Server {
    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args)) {

            // --- Adaptador 1: Functions na porta 5678 ---
            ObjectAdapter adapterFunctions = communicator.createObjectAdapterWithEndpoints(
                    "AdapterFunctions", "default -h 0.0.0.0 -p 5678");

            FunctionsI funcServant = new FunctionsI("ServidorJava");
            adapterFunctions.add(funcServant, Util.stringToIdentity("SimpleFunctions"));
            adapterFunctions.activate();
            System.out.println("Functions Java rodando na porta 5678...");

            // --- Adaptador 2: Calculadora na porta 5679 ---
            ObjectAdapter adapterCalc = communicator.createObjectAdapterWithEndpoints(
                    "AdapterCalc", "default -h 0.0.0.0 -p 5679");

            CalculadoraI calc1 = new CalculadoraI("Calc-A");
            CalculadoraI calc2 = new CalculadoraI("Calc-B");
            adapterCalc.add(calc1, Util.stringToIdentity("SimpleCalc1"));
            adapterCalc.add(calc2, Util.stringToIdentity("SimpleCalc2"));
            adapterCalc.activate();
            System.out.println("Calculadora Java rodando na porta 5679 (Calc-A e Calc-B)...");

            communicator.waitForShutdown();

        } catch (LocalException e) {
            e.printStackTrace();
        }
    }
}
