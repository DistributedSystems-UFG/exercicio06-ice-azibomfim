import com.zeroc.Ice.Current;

class FunctionsI implements Demo.Functions {
    private String name;

    public FunctionsI(String name) {
        this.name = name;
    }

    private void registra(String texto) {
        System.out.println("[" + name + "] " + texto);
    }

    @Override
    public String printString(String s, Current current) {
        registra("disse '" + s + "'");
        return "mensagem [" + s + "] recebida por " + name;
    }

    @Override
    public String cumprimentar(String nome, Current current) {
        registra("cumprimentou " + nome);
        return "Seja bem-vindo(a), " + nome + "! Ótimo te ver por aqui! (Java Server)";
    }

    @Override
    public String contarVogais(String texto, Current current) {
        registra("contou vogais em '" + texto + "'");
        String vogais = "aeiouAEIOU";
        StringBuilder lista = new StringBuilder("[");
        int contagem = 0;
        for (char c : texto.toCharArray()) {
            if (vogais.indexOf(c) >= 0) {
                if (contagem > 0) lista.append(", ");
                lista.append("'").append(c).append("'");
                contagem++;
            }
        }
        lista.append("]");
        return "'" + texto + "' possui " + contagem + " vogal(is): " + lista;
    }

    @Override
    public String converterTemperatura(String valor, Current current) {
        registra("converteu temperatura " + valor);
        try {
            double celsius = Double.parseDouble(valor);
            double fahrenheit = celsius * 9.0 / 5.0 + 32.0;
            double kelvin = celsius + 273.15;
            return String.format("%.1f°C equivale a %.2f°F e %.2fK", celsius, fahrenheit, kelvin);
        } catch (NumberFormatException e) {
            return "Valor inválido! Informe um número (ex: 25 para 25°C).";
        }
    }

    @Override
    public String calcularIMC(String peso, String altura, Current current) {
        registra("calculou IMC peso=" + peso + " altura=" + altura);
        try {
            double p = Double.parseDouble(peso);
            double h = Double.parseDouble(altura);
            if (p <= 0 || h <= 0) return "Valores devem ser positivos!";
            double imc = p / (h * h);
            String classificacao;
            if (imc < 18.5)      classificacao = "Abaixo do peso";
            else if (imc < 25.0) classificacao = "Peso normal";
            else if (imc < 30.0) classificacao = "Sobrepeso";
            else                 classificacao = "Obesidade";
            return String.format("IMC = %.2f → %s", imc, classificacao);
        } catch (NumberFormatException e) {
            return "Valores inválidos! Informe números (ex: peso=70 altura=1.75).";
        }
    }
}
