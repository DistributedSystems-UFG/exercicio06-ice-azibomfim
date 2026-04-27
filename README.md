# Exercício 06 — ICE Java (Modificado)

## O que foi alterado em relação ao original

### `Functions.ice`
- Removidas: `inverter`
- Adicionadas em `Functions`: `contarVogais`, `converterTemperatura`, `calcularIMC`
- **Nova interface** `Calculadora` com: `somar`, `fatorial`, `ehPrimo`

### Arquivos Java
| Arquivo | Papel |
|---|---|
| `FunctionsI.java` | Servant Java para a interface `Functions` (métodos novos) |
| `CalculadoraI.java` | **Novo** servant Java para a interface `Calculadora` |
| `Server.java` | Sobe `Functions` na porta 5678 e `Calculadora` na 5679 |
| `Client.java` | Conecta a ambos os servidores e chama todos os métodos |

---

## Pré-requisitos

- ZeroC Ice para Java instalado (jar `ice.jar` no classpath)
- `slice2java` disponível no PATH

---

## Compilar

```bash
# 1. Gerar os stubs Java a partir do .ice
slice2java Functions.ice

# 2. Compilar tudo (ajuste o caminho do ice.jar conforme sua instalação)
javac -cp .:/usr/share/java/ice.jar *.java Demo/*.java
```

---

## Executar

### Servidor Java (substitui ou complementa o servidor Python do Ex05)
```bash
java -cp .:/usr/share/java/ice.jar Server
```

### Cliente Java
```bash
java -cp .:/usr/share/java/ice.jar Client
```

O cliente tenta primeiro o host remoto (`54.196.85.80`) e, se falhar, cai para `localhost`.

---

## Interoperabilidade com Ex05 (Python)

O `Client.java` é compatível com o `server.py` / `server2.py` do Exercício 05 modificado,
pois compartilham exatamente a mesma interface `Functions.ice`.

- `server.py` (Python, porta 5678) ↔ `Client.java` (seção Functions)
- `server2.py` (Python, porta 5679) ↔ `Client.java` (seção Calculadora)

Ambos podem rodar em máquinas separadas normalmente.
