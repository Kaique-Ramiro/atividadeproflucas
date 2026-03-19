import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.Period;

public class Main {

    private static ArrayList<Aluno> listaAlunos = new ArrayList<>();
    private static ArrayList<Turma> listaTurmas = new ArrayList<>();

    public static void main(String[] args) {
        menuPrincipal();
    }

    public static void menuPrincipal() {
        System.out.println("\n==== Secretaria ====");
        System.out.println("1 - Alunos");
        System.out.println("2 - Turmas");
        System.out.println("3 - Sair");
        String opcao = Leitura.dados("Digite a opção desejada: ");
        switch (opcao) {
            case "1":
                menuAlunos();
                break;
            case "2":
                menuTurmas();
                break;
            case "3":
                System.out.println("Até breve...");
                System.exit(0);
                break;
            default:
                System.out.println("Opção inválida! Tente novamente");
                menuPrincipal();
        }

    }

    private static void menuTurmas() {
        System.out.println("\n==== Turmas ====");
        System.out.println("1 - Listar Turmas");
        System.out.println("2 - Cadastrar Turma");
        System.out.println("3 - Atualizar Turma");
        System.out.println("4 - Excluir Turma");
        System.out.println("5 - Voltar ao menu principal");
        String opcao = Leitura.dados("Digite a opção desejada: ");
        switch (opcao) {
            case "1":
                listarTurmas();
                menuTurmas();
                break;
            case "2":
                cadastrarTurma();
                menuTurmas();
                break;
            case "3":
                atualizarTurma();
                menuTurmas();
                break;
            case "4":
                excluirTurma();
                menuTurmas();
                break;
            case "5":
                menuPrincipal();
                break;
            default:
                System.out.println("Opção inválida! Tente novamente");
                menuTurmas();
        }
    }

    private static void menuAlunos() {
        System.out.println("\n==== Alunos ====");
        System.out.println("1 - Listar Alunos");
        System.out.println("2 - Cadastrar Aluno");
        System.out.println("3 - Atualizar Aluno");
        System.out.println("4 - Excluir Aluno");
        System.out.println("5 - Voltar ao menu principal");
        String opcao = Leitura.dados("Digite a opção desejada: ");
        switch (opcao) {
            case "1":
                listarAlunos();
                menuAlunos();
                break;
            case "2":
                cadastrarAluno();
                break;
            case "3":
                atualizarAluno();
                break;
            case "4":
                excluirAluno();
                break;
            case "5":
                menuPrincipal();
                break;
            default:
                System.out.println("Opção inválida! Tente novamente");
                menuAlunos();
        }
    }

    private static void excluirTurma() {
        if(isVazio(listaTurmas)) {
            System.out.println("Não há turmas cadastradas");
            return;
        }

        listarTurmasIndiceSigla();

        int idExcluir = validaIdTurma();

        if (confirmaExclusao()){
//            listaTurmas.remove(idExcluir);
            listaTurmas.get(idExcluir).setAtivo(false);
            System.out.println("Turma excluída com sucesso!");
        }
    }

    private static boolean isVazio(ArrayList<Turma> listaTurmas) {
        if (listaTurmas.isEmpty()) return true;

        for (Turma turma : listaTurmas){
            if (turma.isAtivo()) return false;
        }

        return true;
    }

    private static boolean confirmaExclusao() {
        while (true) {
            String confirma = Leitura.dados("Você tem certeza? (S/N): ").toUpperCase();
            switch (confirma) {
                case "S":
                    return true;
                case "N":
                    return false;
                default:
                    System.out.println("Opção inválida, digite S para sim ou N para não!");
                    break;
            }
        }
    }

    private static int validarItemLista(String opcao) {
        if (opcao.isBlank()) return -1;

        int opcaoNumero = -1;

        try{
            opcaoNumero = Integer.parseInt(opcao);
        } catch (NumberFormatException e) {
            return -1;
        }

        int indiceLista = opcaoNumero-1;
        return indiceLista >= 0 && listaTurmas.size() > indiceLista ? indiceLista : -1;
    }

    private static void listarTurmasIndiceSigla() {
        System.out.println("\nLista das Turmas:");
        for (int i=0;i<listaTurmas.size();i++){
            if (listaTurmas.get(i).isAtivo())
                System.out.printf("\n%d - %s",i+1, listaTurmas.get(i).getSigla());
        }
    }

    private static void atualizarTurma() {
        if(isVazio(listaTurmas)) {
            System.out.println("Não há turmas cadastradas");
            return;
        }

        listarTurmasIndiceSigla();

        int idAtualizar = validaIdTurma();

        System.out.printf("O período atual é: %s", listaTurmas.get(idAtualizar).getPeriodo());
        atualizarParcial("período", idAtualizar);

        System.out.printf("O curso atual é: %s", listaTurmas.get(idAtualizar).getCurso());
        atualizarParcial("curso", idAtualizar);

        System.out.printf("A sigla atual é: %s", listaTurmas.get(idAtualizar).getSigla());
        atualizarParcial("sigla", idAtualizar);

//        System.out.println("O período atual é: " + listaTurmas.get(idAtualizar).getPeriodo());
//        System.out.printf("O período atual é: %s", listaTurmas.get(idAtualizar).getPeriodo());
//        atualizarPeriodo(idAtualizar);
//
//        System.out.printf("O curso atual é: %s", listaTurmas.get(idAtualizar).getCurso());
//        atualizarCurso(idAtualizar);
//
//        System.out.printf("A sigla atual é: %s", listaTurmas.get(idAtualizar).getSigla());
//        atualizarSigla(idAtualizar);
    }

    private static void atualizarParcial(String atributo, int idAtualizar){
        boolean rodarNovamente = true;
        while (rodarNovamente) {
            String opcao = Leitura.dados("\nDeseja modificar "+ atributo +" ? (S/N): ").toUpperCase();
            switch (opcao) {
                case "S":
                    switch (atributo){
                        case "período":
                            Periodo periodo = validarPeriodo();
                            listaTurmas.get(idAtualizar).setPeriodo(periodo);
                            break;
                        case "curso":
                            String curso = validarCurso();
                            listaTurmas.get(idAtualizar).setCurso(curso);
                            break;
                        case "sigla":
                            String sigla = validarSigla();
                            listaTurmas.get(idAtualizar).setSigla(sigla);
                            break;
                    }
                    System.out.println(atributo + " atualizado com sucesso!");
                    rodarNovamente = false;
                    break;
                case "N":
                    rodarNovamente = false;
                    break;
                default:
                    System.out.println("Opção inválida! Escolha S para SIM ou N para NÃO");
            }
        }
    }

    private static String validarSigla() {
        String sigla = Leitura.dados("Digite a sigla: ");
        while(!validarSigla(sigla)){
            System.out.println("Sigla inválida! Precisa conter texto e não pode ser repetida");
            sigla = Leitura.dados("Digite a sigla: ");
        }
        return sigla;
    }

    private static String validarCurso() {
        String curso = Leitura.dados("Digite o curso: ");
        while(!isCharacter(curso)) {
            System.out.println("Nome de curso inválido! Não use números ou caracteres especiais, por favor");
            curso = Leitura.dados("Digite o curso: ");
        }
        return curso;
    }

    private static void atualizarPeriodo(int idAtualizar) {
        boolean rodarNovamente = true;
        while (rodarNovamente) {
            String opcaoPeriodo = Leitura.dados("\nDeseja modificar o período? (S/N): ").toUpperCase();
            switch (opcaoPeriodo) {
                case "S":
                    Periodo periodo = validarPeriodo();
                    listaTurmas.get(idAtualizar).setPeriodo(periodo);
                    System.out.println("Período atualizado com sucesso para " + periodo);
                    rodarNovamente = false;
                    break;
                case "N":
                    rodarNovamente = false;
                    break;
                default:
                    System.out.println("Opção inválida! Escolha S para SIM ou N para NÃO");
            }
        }

//        while (true) {
//            String opcaoPeriodo = Leitura.dados("\nDeseja modificar o período? (S/N): ").toUpperCase();
//            switch (opcaoPeriodo) {
//                case "S":
////                Periodo periodo = validarPeriodo();
////                listaTurmas.get(idAtualizar).setPeriodo(periodo);
//                    listaTurmas.get(idAtualizar).setPeriodo(validarPeriodo());
//                    break;
//                case "N":
//                    break;
//                default:
//                    System.out.println("Opção inválida! Escolha S para SIM ou N para NÃO");
//                    continue;
//            }
//            break;
//        }
    }

    private static int validaIdTurma() {
        String opcao = Leitura.dados("\nDigite o número da turma desejada: ");
        int opcaoValida = -1;
        int opcaoUsuario = -1;
        while (opcaoValida==-1){
            opcaoUsuario = validarItemLista(opcao);

            if (opcaoUsuario==-1) {
                System.out.println("Opção inválida! Digite novamente: ");
                opcao = Leitura.dados("Digite o número da turma desejada: ");
            } else {
                opcaoValida = opcaoUsuario;
            }
        }
        return opcaoValida;
    }

    private static void cadastrarTurma() {
        Periodo periodo = validarPeriodo();
        String curso = validarCurso();
        String sigla = validarSigla();

        Turma turma = new Turma(curso, sigla, periodo);
        listaTurmas.add(turma);
    }

    private static boolean validarSigla(String sigla) {
        if (sigla.isBlank()) return false;

        for (Turma turma : listaTurmas){
            if (turma.getSigla().equals(sigla)){
                return false;
            }
        }
        return true;
    }

    private static boolean isCharacter(String texto) {
        String textoSemNumeros = texto.replaceAll("\\d", "");
        return !texto.isBlank() && texto.equals(textoSemNumeros);
    }

    private static Periodo validarPeriodo() {
        String opcaoPeriodo = Leitura.dados("""
                Digite o número do período escolhido:
                1 - Matutino
                2 - Vespertino
                3 - Noturno
                4 - Integral""");
        switch (opcaoPeriodo){
            case "1":
                return Periodo.MATUTINO;
            case "2":
                return Periodo.VESPERTINO;
            case "3":
                return Periodo.NOTURNO;
            case "4":
                return Periodo.INTEGRAL;
            default:
                System.out.println("Opção inválida, digite novamente");
                return validarPeriodo();
        }
    }

    private static void listarTurmas() {
        if(isVazio(listaTurmas)) {
            System.out.println("Não há turmas cadastradas");
            return;
        }
        for(Turma t : listaTurmas){
            if (t.isAtivo())
                System.out.println(t);
        }
    }

    private static void excluirAluno() {
        if (listaAlunos.isEmpty()) {
            System.out.println("Não há alunos cadastrados");
            return;
        }

        for (int i = 0; i < listaAlunos.size(); i++) {
            if (listaAlunos.get(i).isAtivo())
                System.out.println((i + 1) + " - " + listaAlunos.get(i).getNome());
        }

        int id = Integer.parseInt(Leitura.dados("Digite o número do aluno: ")) - 1;

        if (id < 0 || id >= listaAlunos.size()) {
            System.out.println("Opção inválida!");
            return;
        }

        if (confirmaExclusao()) {
            listaAlunos.get(id).setAtivo(false);
            System.out.println("Aluno excluído!");
        }

        menuAlunos();
    }

    private static void atualizarAluno() {
        if (listaAlunos.isEmpty()) {
            System.out.println("Não há alunos cadastrados");
            return;
        }

        for (int i = 0; i < listaAlunos.size(); i++) {
            if (listaAlunos.get(i).isAtivo())
                System.out.println((i + 1) + " - " + listaAlunos.get(i).getNome());
        }

        int id = Integer.parseInt(Leitura.dados("Digite o número do aluno: ")) - 1;

        if (id < 0 || id >= listaAlunos.size()) {
            System.out.println("Opção inválida!");
            return;
        }

        Aluno aluno = listaAlunos.get(id);

        // Nome
        String opcao = Leitura.dados("Deseja alterar o nome? (S/N)").toUpperCase();
        if (opcao.equals("S")) {
            aluno.setNome(validarNomeAluno());
        }

        // Data
        opcao = Leitura.dados("Deseja alterar a data? (S/N)").toUpperCase();
        if (opcao.equals("S")) {
            LocalDate data = validarDataNascimento();
            if (data != null)
                aluno.setDataNascimento(data);
        }

        // Turma
        opcao = Leitura.dados("Deseja alterar a turma? (S/N)").toUpperCase();
        if (opcao.equals("S")) {
            Turma turma = validarTurmaAluno();
            if (turma != null)
                aluno.setTurma(turma);
        }

        System.out.println("Aluno atualizado com sucesso!");
        menuAlunos();
    }

    private static void cadastrarAluno() {
        String nome = validarNomeAluno();

        LocalDate data = validarDataNascimento();
        if (data == null) {
            menuAlunos();
            return;
        }

        Turma turma = validarTurmaAluno();
        if (turma == null) {
            menuAlunos();
            return;
        }

        Aluno aluno = new Aluno(nome, data, turma);
        listaAlunos.add(aluno);

        System.out.println("Aluno cadastrado com sucesso!");
        menuAlunos();
    }

    private static void listarAlunos() {
        if (isVazioAlunos()) {
            System.out.println("Não há alunos cadastrados");
            return;
        }

        for (Aluno a : listaAlunos) {
            if (a.isAtivo())
                System.out.println(a);
        }
    }
    private static String validarNomeAluno() {
        String nome = Leitura.dados("Digite o nome do aluno: ");

        while (!nome.matches("[A-Za-zÀ-ÿ ]+") || nome.isBlank()) {
            System.out.println("Nome inválido! Não use números ou caracteres especiais.");
            nome = Leitura.dados("Digite o nome do aluno: ");
        }

        return nome;
    }

    private static LocalDate validarDataNascimento() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            try {
                String dataStr = Leitura.dados("Digite a data de nascimento (dd/MM/aaaa): ");
                LocalDate data = LocalDate.parse(dataStr, formatter);

                int idade = Period.between(data, LocalDate.now()).getYears();

                if (idade < 14) {
                    System.out.println("Aluno deve ter no mínimo 14 anos!");
                    return null;
                }

                if (idade > 130) {
                    System.out.println("Idade inválida! Máximo permitido: 130 anos.");
                    return null;
                }

                return data;

            } catch (Exception e) {
                System.out.println("Data inválida! Use o formato dd/MM/aaaa.");
            }
        }
    }

    private static Turma validarTurmaAluno() {
        if (isVazio(listaTurmas)) {
            System.out.println("Não há turmas disponíveis!");
            return null;
        }

        listarTurmasIndiceSigla();
        int id = validaIdTurma();

        Turma turma = listaTurmas.get(id);

        if (!turma.isAtivo()) {
            System.out.println("Turma inválida!");
            return null;
        }

        return turma;
    }
    private static boolean isVazioAlunos() {
        if (listaAlunos.isEmpty()) return true;

        for (Aluno aluno : listaAlunos) {
            if (aluno.isAtivo()) return false;
        }

        return true;
    }
}