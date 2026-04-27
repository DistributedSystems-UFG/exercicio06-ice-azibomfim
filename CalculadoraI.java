import com.zeroc.Ice.Current;

class CalculadoraI implements Demo.Calculadora {
    private String name;

    public CalculadoraI(String name) {
        this.name = name;
    }

    private void registra(String texto) {
        System.out.println("[Calculadora:" + name + "] " + texto);
    }

    @Override
    public String somar(String numeros, Current current) {
        registra("somando: " + numeros);
        try {
            String[] partes = numeros.split(",");
            double total = 0;
            StringBuilder expressao = new StringBuilder();
            for (int i = 0; i < partes.length; i++) {
                double val = Double.parseDouble(partes[i].trim());
                total += val;
                if (i > 0) expressao.append(" + ");
                expressao.append(partes[i].trim());
            }
            // Exibe inteiro quando não há casas decimais
            if (total == Math.floor(total)) {
                return expressao + " = " + (long) total;
            }
            return expressao + " = " + total;
        } catch (NumberFormatException e) {
            return "Formato inválido! Use vírgulas para separar (ex: 1,2,3).";
        }
    }

    @Override
    public String fatorial(String numero, Current current) {
        registra("fatorial de " + numero);
        try {
            int n = Integer.parseInt(numero.trim());
            if (n < 0)  return "Fatorial não é definido para negativos!";
            if (n > 20) return "Número muito grande! Informe até 20.";
            long resultado = 1;
            for (int i = 2; i <= n; i++) resultado *= i;
            return n + "! = " + resultado;
        } catch (NumberFormatException e) {
            return "Valor inválido! Informe um número inteiro.";
        }
    }

    @Override
    public String ehPrimo(String numero, Current current) {
        registra("verificando se " + numero + " é primo");
        try {
            int n = Integer.parseInt(numero.trim());
            if (n < 2) return n + " não é primo.";
            for (int i = 2; i <= Math.sqrt(n); i++) {
                if (n % i == 0) return n + " NÃO é primo (divisível por " + i + ").";
            }
            return n + " É primo!";
        } catch (NumberFormatException e) {
            return "Valor inválido! Informe um número inteiro.";
        }
    }
}
