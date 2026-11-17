import java.util.Scanner;

class Conta {
    int id;
    String cliente;
    double saldo;
    double limite;
}

class Operacao {
    int id;
    int idConta;
    char tipo;
    double valor;
}

public class Prova2Corrigida {

    static int TAM_CONTAS = 50;
    static int TAM_OP = 1000;

    public static int buscaId(Conta[] v, int tam, int x) {
        for (int i = 0; i < tam; i++) {
            if (v[i].id == x) {
                return i;
            }
        }
        return -1;
    }

    public static int cadastrarConta(Conta[] v, int tam) {
        Scanner im = new Scanner(System.in);
        if (tam >= TAM_CONTAS) {
            System.out.println("Vetor cheio!");
            return tam;
        }
        Conta c = new Conta();
        System.out.print("ID: ");
        c.id = im.nextInt();
        im.nextLine();
        if (buscaId(v, tam, c.id) != -1) {
            System.out.println("ID duplicado!");
            return tam;
        }
        System.out.print("Cliente: ");
        c.cliente = im.nextLine();
        System.out.print("Saldo: ");
        c.saldo = im.nextDouble();
        System.out.print("Limite: ");
        c.limite = im.nextDouble();
        if (c.saldo < 0 || c.limite < 0) {
            System.out.println("Valores inválidos!");
            return tam;
        }
        v[tam] = c;
        return tam + 1;
    }

    public static int buscaBinariaCliente(Conta[] v, int tam, String x) {
        int inicio = 0;
        int fim = tam - 1;
        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            int cmp = v[meio].cliente.compareTo(x);
            if (cmp == 0) {
                return meio;
            } else if (cmp < 0) {
                inicio = meio + 1;
            } else {
                fim = meio - 1;
            }
        }
        return -1;
    }

    public static int insertionSortCliente(Conta[] v, int tam) {
        for (int i = 1; i < tam; i++) {
            Conta chave = v[i];
            int j = i - 1;
            while (j >= 0 && v[j].cliente.compareTo(chave.cliente) > 0) {
                v[j + 1] = v[j];
                j--;
            }
            v[j + 1] = chave;
        }
        return tam;
    }

    public static int filtraOperacoes(Operacao[] vOp, int tamOp, Operacao[] vOpFiltrado, int x) {
        int qtd = 0;
        for (int i = 0; i < tamOp; i++) {
            if (vOp[i].idConta == x) {
                vOpFiltrado[qtd] = vOp[i];
                qtd++;
            }
        }
        return qtd;
    }

    public static String nomeOperacao(char tipo) {
        if (tipo == 'D') {
            return "Depósito";
        }
        if (tipo == 'S') {
            return "Saque";
        }
        return "Transferência";
    }

    public static void extrato(Conta[] vContas, int tamConta, Operacao[] vOp, int tamOp) {
        Scanner im = new Scanner(System.in);
        System.out.print("ID da conta: ");
        int id = im.nextInt();
        int pos = buscaId(vContas, tamConta, id);
        if (pos == -1) {
            System.out.println("Conta não existe.");
            return;
        }

        Operacao[] vFiltrado = new Operacao[tamOp];
        int qtd = filtraOperacoes(vOp, tamOp, vFiltrado, id);

        int inicio = qtd - 5;
        if (inicio < 0) {
            inicio = 0;
        }

        double saldo = 0;
        for (int i = 0; i < inicio; i++) {
            saldo += vFiltrado[i].valor;
        }

        System.out.println("CONTA N. " + vContas[pos].id);
        System.out.println("CLIENTE: " + vContas[pos].cliente);
        System.out.println("ID | OPERACAO | VALOR | SALDO");
        System.out.println("Saldo Anterior: " + Math.abs(saldo) + (saldo >= 0 ? "C" : "D"));

        for (int i = inicio; i < qtd; i++) {
            saldo += vFiltrado[i].valor;
            char opSinal = vFiltrado[i].valor >= 0 ? 'C' : 'D';
            char saldoSinal = saldo >= 0 ? 'C' : 'D';
            System.out.println(vFiltrado[i].id + " | " + nomeOperacao(vFiltrado[i].tipo) + " | "
                    + Math.abs(vFiltrado[i].valor) + opSinal + " | "
                    + Math.abs(saldo) + saldoSinal);
        }
        System.out.println("Saldo Atual: " + Math.abs(saldo) + (saldo >= 0 ? "C" : "D"));
    }
}
