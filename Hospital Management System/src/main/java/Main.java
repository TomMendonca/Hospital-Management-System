
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Random;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rafae
 */
public class Main {

    /**
     * @param ficheiro
     * @param scan
     * @param arraylist
     * @return
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static ArrayList<String> lerFicheiro(String ficheiro, Scanner scan, ArrayList<String> arraylist) throws FileNotFoundException, IOException {

        FileReader fw = new FileReader(ficheiro);
        BufferedReader bw = new BufferedReader(fw);
        String linha = bw.readLine();

        while (linha != null) {
            linha = bw.readLine();
            arraylist.add(linha);
        }

        bw.close();
        return arraylist;
    }

    //método que faz o cálculo para dizer se o doente tem uma doença ou não
    public static boolean probDoente(int rand) {

        return (rand < 90);
    }

    public static int popularHospital(int id, Set opçoes_nao, Scanner scan, Random rand, Hospital H, ArrayList<String> primeiro_nome, ArrayList<String> ultimo_nome) {
        int opcao;
        boolean prob;

        System.out.println("Insira o número de médicos:");
        opcao = scan.nextInt();
        while (opcao > 0) {
            H.addMédico(new Médico(primeiro_nome.get(rand.nextInt(primeiro_nome.size())) + " " + ultimo_nome.get(rand.nextInt(ultimo_nome.size())), rand.nextInt(49) + 1945, id, false), id);
            id++;
            opcao--;
        }
        System.out.println("Insira o número de enfermeiros-especialista:");
        opcao = scan.nextInt();
        while (opcao > 0) {
            H.addEnfermeiroEspecialista(new Enfermeiro(primeiro_nome.get(rand.nextInt(primeiro_nome.size())) + " " + ultimo_nome.get(rand.nextInt(ultimo_nome.size())), rand.nextInt(40) + 1951, id, false, true), id);
            id++;
            opcao--;
        }
        System.out.println("Insira o número de enfermeiros-auxiliares:");
        opcao = scan.nextInt();
        while (opcao > 0) {
            H.addEnfermeiroAuxiliar(new Enfermeiro(primeiro_nome.get(rand.nextInt(primeiro_nome.size())) + " " + ultimo_nome.get(rand.nextInt(ultimo_nome.size())), rand.nextInt(30) + 1969, id, false, false), id);
            id++;
            opcao--;
        }
        System.out.println("Insira o número de enfermeiros-chefe:");
        opcao = scan.nextInt();
        while (opcao > 0) {
            H.addChefe(new Enfermeiro_Chefe(primeiro_nome.get(rand.nextInt(primeiro_nome.size())) + " " + ultimo_nome.get(rand.nextInt(ultimo_nome.size())), rand.nextInt(50) + 1941, id, false), id);
            id++;
            opcao--;
        }
        System.out.println("Insira o número de pacientes para inserir na fila de espera:");
        opcao = scan.nextInt();
        while (opcao > 0) {
            prob = probDoente(rand.nextInt(100));
            H.addPaciente(new Pessoa(primeiro_nome.get(rand.nextInt(primeiro_nome.size())) + " " + ultimo_nome.get(rand.nextInt(ultimo_nome.size())), rand.nextInt(119) + 1903, id, prob), id);
            id++;
            opcao--;
        }
        return id;
    }

    public static void main(String[] args) throws IOException {
        // TODO code application logic here

        Hospital H = new Hospital("Hospital Da Madeira");

        Scanner scan = new Scanner(System.in);
        ArrayList<String> primeiro_nome = new ArrayList();
        ArrayList<String> ultimo_nome = new ArrayList();
        LinkedHashMap<Médico, Integer> pedidos_inverso = new LinkedHashMap();
        Random rand = new Random();

        primeiro_nome = lerFicheiro("primeiro_nome.txt", scan, primeiro_nome);
        ultimo_nome = lerFicheiro("ultimo_nome.txt", scan, ultimo_nome);

        int id = 1;
        boolean sair = false;
        int opcao;
        int x, y, z;
        String popular_hospital;

        // Onde começa o input do utilizador
        System.out.println("Deseja popular o Hospital?(s/n)");
        popular_hospital = scan.next();

        Set<String> opçoes_nao = new HashSet<>();
        opçoes_nao.add("nao");
        opçoes_nao.add("não");
        opçoes_nao.add("Nao");
        opçoes_nao.add("Não");
        opçoes_nao.add("n");
        opçoes_nao.add("N");

        //método para popular o Hospital após a sua criação
        if (!opçoes_nao.contains(popular_hospital)) {
            id = popularHospital(id, opçoes_nao, scan, rand, H, primeiro_nome, ultimo_nome);
        }

        while (sair == false) {

            //atender aos pedidos
            if (!H.getPedidos_auxiliares().isEmpty() && !H.getAuxiliar_livre().isEmpty()) {
                for (Médico i : H.getPedidos_auxiliares().keySet()) {
                    if (H.getPedidos_auxiliares().get(i) <= H.getAuxiliar_livre().size()) {
                        z = H.getPedidos_auxiliares().get(i); //número de auxiliares pedido
                        while (z > 0) {
                            i.getAuxiliar().add(H.getAuxiliar_livre().get(H.getAuxiliar_livre().firstKey()));
                            H.getAuxiliar_livre().remove(H.getAuxiliar_livre().firstKey());
                            z--;
                        }
                        H.getPedidos_auxiliares().remove(i);
                        break;
                    }
                }
            }

            System.out.println("\n");
            System.out.println("-----Menu Inicial-----");
            System.out.println("1. Médico");
            System.out.println("2. Enfermeiro");
            System.out.println("3. Administrador");
            System.out.println("Escolha a opção:");
            opcao = scan.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("Coloque o seu id: ");
                    x = scan.nextInt();

                    if (!H.getMédicos().containsKey(x)) {
                        System.out.println("Não tem permissão");
                        break;
                    }
                    System.out.println("-----Menu Médico-----");
                    System.out.println("1. Listar paciente na fila de espera");
                    System.out.println("2. Listar pacientes a aguardar alta");
                    System.out.println("3. Diagnosticar paciente");
                    System.out.println("4. Dar alta hospitalar");
                    System.out.println("5. Requerimento de auxiliares");
                    System.out.println("6. Voltar ao menu inicial");

                    opcao = scan.nextInt();
                    switch (opcao) {
                        case 1:
                            H.listarPacientes();
                            break;

                        case 2:
                            H.identificarMédicos(x).listarPacientesAguardarAlta();
                            break;

                        case 3:
                            try {
                            //se o médico já tem um paciente que não curado da 1º vez, vai atendê-lo
                            if (!H.identificarMédicos(x).getPacientes_a_diagnosticar().isEmpty()) {
                                H.identificarMédicos(x).diagnosticarPaciente(H.identificarMédicos(x).getPacientes_a_diagnosticar().get(0), rand.nextInt(100), H);
                            } //se o médico já está a atender 3 pacientes
                            else if (H.identificarMédicos(x).listaMedicoPacientes()) {
                                System.out.println("O médico encontra-se indisponível");
                            } //se o médico não tem enfermeiros
                            else if (H.identificarMédicos(x).getEspecialista().isEmpty() && H.identificarMédicos(x).getAuxiliar().isEmpty()) {
                                System.out.println("O Médico não tem nenhum enfermeiro-especialista associado");
                            } else {
                                //médico retira o primeiro paciente da lista de espera, faz o diagnóstico e remove-o da lista de espera
                                H.identificarMédicos(x).pacientesMédico(H.getLista_espera().firstEntry().getValue());
                                H.identificarMédicos(x).diagnosticarPaciente(H.identificarMédicos(x).getPacientes_a_diagnosticar().get(0), rand.nextInt(100), H);
                                H.getLista_espera().remove(H.getLista_espera().firstKey());
                            }
                        } catch (NullPointerException exception) {
                            System.out.println("Não há pacientes na lista de espera! ");
                        }
                        break;

                        case 4:
                            
                            if (!H.identificarMédicos(x).getPacientes_aguardar_alta().isEmpty()) {
                                H.identificarMédicos(x).darAltaPaciente(H.identificarMédicos(x).getPacientes_aguardar_alta().get(0), H);
                            } else {
                                System.out.println("Não há pacientes a aguardar alta! ");
                            }

                            break;

                        case 5:
                            System.out.println("Quantos auxiliares necessita?");
                            opcao = scan.nextInt();
                            pedidos_inverso.putAll(H.getPedidos_auxiliares());//faz uma cópia
                            H.getPedidos_auxiliares().clear();//elimina pedidos originais
                            H.getPedidos_auxiliares().put(H.getMédicos().get(x), opcao);//coloca o pedido criado no Map original
                            H.getPedidos_auxiliares().putAll(pedidos_inverso);//coloca os restantes valores do Map original
                            pedidos_inverso.clear();
                            break;

                        default:
                            System.out.println("Opção Inválida");
                            break;
                    }
                    break;

                case 2:

                    System.out.println("Coloque o seu id: ");
                    x = scan.nextInt();

                    if (!H.getAuxiliar().containsKey(x) && !H.getChefe().containsKey(x) && !H.getEspecialista().containsKey(x)) {
                        System.out.println("Não tem permissão");
                        break;
                    }

                    System.out.println("-----Menu Enfermeiro-----");
                    System.out.println("1. Listar enfermeiros de um médico");
                    System.out.println("2. Listar pacientes a aguardar curativo");
                    System.out.println("3. Atribuir enfermeiro-especialista a médico");
                    System.out.println("4. Aplicar curativo a paciente");
                    System.out.println("5. Voltar ao menu inicial");

                    opcao = scan.nextInt();

                    switch (opcao) {
                        case 1:
                            System.out.println("Coloque o ID do médico a visualizar:");
                            opcao = scan.nextInt();

                            H.getMédicos().get(opcao).listarEspecialista(H.getMédicos().get(opcao));
                            H.getMédicos().get(opcao).listarAuxiliares(H.getMédicos().get(opcao));
                            break;

                        case 2:
                            System.out.println("Coloque o ID do enfermeiro a visualizar:");
                            x = scan.nextInt();
                            if (H.getChefe().containsKey(x)) {
                                System.out.println("Enfermeiro-Chefe não interage com pacientes");

                            } else if (H.getAuxiliar().containsKey(x)) {
                                System.out.print("Pacientes à espera de curativo: ");
                                for (Pessoa p : H.getAuxiliar().get(x).getPacientes_a_curar()) {
                                    System.out.print(p.getNome() + " | ");
                                }
                            } else if (H.getEspecialista().containsKey(x)) {
                                System.out.print("Pacientes à espera de curativo: ");
                                for (int i = 0; i < H.getEspecialista().get(x).getPacientes_a_curar().size(); i++) {
                                    System.out.print(H.getEspecialista().get(x).getPacientes_a_curar().get(i).getNome() + " | ");
                                }
                            } else {
                                System.out.print("ID não corresponde a um enfermeiro.");
                            }
                            System.out.println();

                            break;

                        case 3:
                            try {
                            if (!H.getChefe().containsKey(x)) {
                                System.out.println("Não tem permissão");
                                break;
                            }
                            //procurar com IDs
                            System.out.println("Qual o id do médico: ");
                            z = scan.nextInt();
                            System.out.println("Qual o id do enfermeiro: ");
                            y = scan.nextInt();
                            H.getChefe().get(x).atribuirEspecialistaMédico(H.identificarEspecialistaLivre(y), H.identificarMédicos(z), H);

                        } catch (NullPointerException exception) {
                            System.out.println("Um ou ambos os IDs não correspondem ao pedido! ");
                        }
                        break;

                        case 4:

                            if (H.getEspecialista().containsKey(x) && !H.getEspecialista().get(x).getPacientes_a_curar().isEmpty()) {
                                H.getEspecialista().get(x).aplicarCurativo(H.getEspecialista().get(x).getPacientes_a_curar().get(0), rand.nextInt(21));
                            } else if (H.getChefe().containsKey(x)) {
                                System.out.println("Enfermeiro-Chefe não interage com pacientes");
                            } else if (H.getAuxiliar().containsKey(x) && !H.getAuxiliar().get(x).getPacientes_a_curar().isEmpty()) {
                                H.getAuxiliar().get(x).aplicarCurativo(H.getAuxiliar().get(x).getPacientes_a_curar().get(0), rand.nextInt(21));
                            }
                            else {
                                System.out.println("Não tem pacientes para curar");
                            }
                            
                            break;

                        case 5:
                            break;

                        default:
                            System.out.println("Opção Inválida");
                            break;
                    }
                    break;

                case 3:
                    System.out.println("-----Menu Administrador-----");
                    System.out.println("1. Criar médico");
                    System.out.println("2. Criar enfermeiro-especialista");
                    System.out.println("3. Criar enfermeiro-auxiliar");
                    System.out.println("4. Criar paciente");
                    System.out.println("5. Promover enfermeiro a chefe");
                    System.out.println("6. Aumentar anos de carreira dos enfermeiros");
                    System.out.println("7. Listar enfermeiros");
                    System.out.println("8. Listar médicos");
                    System.out.println("9. Pedidos para enfermeiros auxiliares");
                    System.out.println("10. Pacientes na fila de espera");
                    System.out.println("11. Triturar pedidos para enfermeiros-auxiliares");
                    System.out.println("12. Atende ao pedido para enfermeiros-auxiliares");
                    System.out.println("13. Vírus Outbreak");
                    System.out.println("14. Listar pacientes que faleceram no hospital");
                    System.out.println("15. Patologias activas no hospital");
                    System.out.println("16. Definir anos de carreira minimos para ser promovido (default = 3)");
                    System.out.println("17. Relatório referente a pacientes");
                    System.out.println("18. Relatório referente a funcionários do hospital");
                    System.out.println("19. Relatório referente a doenças");
                    System.out.println("20. Voltar ao menu inicial");
                    System.out.println("21. Sair da aplicação");

                    opcao = scan.nextInt();

                    switch (opcao) {
                        case 1:
                            System.out.println("Introduza o nome e ano de nascimento do médico, respetivamente.");
                            scan.nextLine();
                            H.addMédico(new Médico(scan.nextLine(), scan.nextInt(), id, false), id);
                            id++;
                            break;

                        case 2:
                            System.out.println("Introduza o nome e ano de nascimento do enfermeiro-especialista, respetivamente.");
                            scan.nextLine();
                            H.addEnfermeiroEspecialista(new Enfermeiro(scan.nextLine(), scan.nextInt(), id, true, false), id);
                            id++;
                            break;

                        case 3:
                            System.out.println("Introduza o nome e ano de nascimento do enfermeiro-auxiliar, respetivamente.");
                            scan.nextLine();
                            H.addEnfermeiroAuxiliar(new Enfermeiro(scan.nextLine(), scan.nextInt(), id, false, false), id);
                            id++;
                            break;

                        case 4:
                            System.out.println("Introduza o nome e ano de nascimento do paciente, respetivamente.");
                            scan.nextLine();
                            H.addPaciente(new Pessoa(scan.nextLine(), scan.nextInt(), id, true), id);
                            id++;
                            break;

                        case 5:
                            System.out.println("Qual o ID do enfermeiro a ser promovido:");
                            x = scan.nextInt();
                            //verifica se o id corresponde a um especialista e os anos de carreira são suficientes
                            if (H.getEspecialista().containsKey(x) && Enfermeiro_Chefe.verificaAnos_carreira(H.identificarEspecialista(x).getAnos_carreira())) {
                                H.getChefe().put(x, new Enfermeiro_Chefe(H.identificarEspecialista(x).getNome(), H.identificarEspecialista(x).getAnos_carreira(), H.identificarEspecialista(x).getId(), false));
                                H.getEspecialista().remove(x);
                            } else if (H.getAuxiliar().containsKey(x) && Enfermeiro_Chefe.verificaAnos_carreira(H.identificarAuxiliar(x).getAnos_carreira())) {
                                H.getChefe().put(x, new Enfermeiro_Chefe(H.identificarAuxiliar(x).getNome(), H.identificarAuxiliar(x).getAnos_carreira(), H.identificarAuxiliar(x).getId(), false));
                                H.getAuxiliar().remove(x);
                            } else {
                                System.out.println("Operação não realizada");
                            }
                            break;

                        case 6:
                            System.out.println("Quantidade de anos de carreira a aumentar:");
                            x = scan.nextInt();
                            H.aumentarAnosDeCarreira(x);
                            break;

                        case 7:
                            H.listarEnfermeiros();
                            break;

                        case 8:
                            H.listarMédicos();
                            break;

                        case 9:
                            H.listarPedidosEnfermeiroAuxiliar();
                            break;

                        case 10:
                            H.listarPacientes();
                            break;

                        case 11:
                            H.triturarPedidos(rand);
                            break;

                        case 12:
                            System.out.println("Quantos enfermeiros-auxiliares pretende pôr ao dispor");
                            opcao = scan.nextInt();
                            while (opcao > 0) {
                                H.addEnfermeiroAuxiliar(new Enfermeiro(primeiro_nome.get(rand.nextInt(primeiro_nome.size())) + " " + ultimo_nome.get(rand.nextInt(ultimo_nome.size())), rand.nextInt(30) + 1969, id, false, false), id);
                                id++;
                                opcao--;
                            }
                            break;

                        case 13:
                            H.virusOutbreak(rand);
                            break;

                        case 14:
                            H.listarExPacientesFalecidos();
                            break;

                        case 15:
                            System.out.println("Patologias ativas: | ");
                            for (Doenças i : Doenças.values()) {
                                if (i.getNum_pacientes() != 0) {
                                    System.out.print(i + " | ");
                                }
                            }
                            System.out.println();

                            break;

                        case 16:
                            System.out.println("Indique os anos necessários para a promoção");
                            x = scan.nextInt();
                            Enfermeiro_Chefe.setAnos_de_carreira_minimos(x);
                            System.out.println(Enfermeiro_Chefe.getAnos_de_carreira_minimos());
                            break;

                        case 17:
                            H.relatorioPacientes();
                            break;
                        case 18:
                            H.relatorioFuncionarios();
                            break;

                        case 19:

                            break;

                        case 20:
                            break;

                        case 21:
                            sair = true;

                        default:
                            System.out.println("Opção Inválida");
                            break;
                    }
                    break;

                default:
                    System.out.println("Opção Inválida");
                    break;
            }
        }
        scan.close();
    }
}
