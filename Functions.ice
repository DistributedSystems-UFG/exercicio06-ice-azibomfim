module Demo
{
    interface Functions
    {
        string printString(string s);
        string cumprimentar(string nome);
        string contarVogais(string texto);
        string converterTemperatura(string valor);
        string calcularIMC(string peso, string altura);
    }

    interface Calculadora
    {
        string somar(string numeros);
        string fatorial(string numero);
        string ehPrimo(string numero);
    }
}
