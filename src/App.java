import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws Exception {

        // 3.1 – Inserir todos os funcionários, na mesma ordem e informações da tabela
        // acima.
        List<Funcionario> funcionarios = new ArrayList<>(Arrays.asList(
                new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
                new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
                new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
                new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
                new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"),
                new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"),
                new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
                new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
                new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
                new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")));

        // 3.2 – Remover o funcionário “João” da lista.
        funcionarios.removeIf(func -> func.getNome().equalsIgnoreCase("João"));

        // 3.3 – Imprimir todos os funcionários com todas suas informações
        imprimirFuncionarios(funcionarios);

        // 3.4 – Os funcionários receberam 10% de aumento de salário
        funcionarios.forEach(func -> func.setSalario(func.getSalario().multiply(new BigDecimal("1.10"))));

        // 3.5 – Agrupar os funcionários por função em um MAP
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // 3.6 – Imprimir os funcionários, agrupados por função.
        imprimirFuncionariosPorFuncao(funcionariosPorFuncao);

        // 3.8 – Imprimir os funcionários que fazem aniversário no mês 10 e 12.
        imprimirFuncionariosPorAniversario(funcionarios, 10);
        imprimirFuncionariosPorAniversario(funcionarios, 12);

        // 3.9 – Imprimir o funcionário com a maior idade, exibir os atributos: nome e
        // idade.
        Funcionario maisVelho = funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElse(null);
        if (maisVelho != null) {
            System.out.println("Funcionário com maior idade: " + maisVelho.getNome() +
                    ", Idade: " + (LocalDate.now().getYear() - maisVelho.getDataNascimento().getYear()));
        }

        // 3.10 – Imprimir a lista de funcionários por ordem alfabética.
        List<Funcionario> funcionariosOrdenados = new ArrayList<>(funcionarios);
        funcionariosOrdenados.sort(Comparator.comparing(Funcionario::getNome));
        imprimirFuncionarios(funcionariosOrdenados);

        // 3.11 – Imprimir o total dos salários dos funcionários.
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total dos salários: " + formatCurrency(totalSalarios));

        // 3.12 – Imprimir quantos salários mínimos ganha cada funcionário
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarios.forEach(func -> {
            @SuppressWarnings("deprecation") //tava dando warning acerca de um método deprecated, mas não afeta a funcionalidade do código
            BigDecimal salariosMinimos = func.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            System.out.println(func.getNome() + " ganha " + salariosMinimos + " salários mínimos.");
        });
    }

    private static void imprimirFuncionarios(List<Funcionario> funcionarios) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Funcionario func : funcionarios) {
            System.out.println(
                    "Nome: " + func.getNome() + ", Data Nascimento: " + func.getDataNascimento().format(formatter) +
                            ", Salário: " + formatCurrency(func.getSalario()) + ", Função: " + func.getFuncao());
        }
    }

    private static void imprimirFuncionariosPorFuncao(Map<String, List<Funcionario>> funcionariosPorFuncao) {
        funcionariosPorFuncao.forEach((funcao, funcs) -> {
            System.out.println("Função: " + funcao);
            imprimirFuncionarios(funcs);
        });
    }

    private static void imprimirFuncionariosPorAniversario(List<Funcionario> funcionarios, int mes) {
        System.out.println("Funcionários que fazem aniversário no mês " + mes + ":");
        funcionarios.stream()
                .filter(func -> func.getDataNascimento().getMonthValue() == mes)
                .forEach(func -> System.out
                        .println("Nome: " + func.getNome() + ", Data Nascimento: " + func.getDataNascimento()));
    }

    private static String formatCurrency(BigDecimal value) {
        return String.format("%,.2f", value).replace('.', 'X').replace(',', '.').replace('X', ',');
    }
}
