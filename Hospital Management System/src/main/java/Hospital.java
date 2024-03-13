
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.TreeMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author tomen
 */
public class Hospital {

    private final String nome;
    private TreeMap<Integer, Médico> medicos;
    private TreeMap<Integer, Pessoa> lista_espera;
    private TreeMap<Integer, Enfermeiro_Chefe> chefe;
    private TreeMap<Integer, Enfermeiro> especialista;
    private TreeMap<Integer, Enfermeiro> especialista_livre;
    private TreeMap<Integer, Enfermeiro> auxiliar;
    private TreeMap<Integer, Enfermeiro> auxiliar_livre;
    private ArrayList<Pessoa> ex_pacientes;
    private LinkedHashMap<Médico, Integer> pedidos_auxiliares; //Integer é o nº de auxiliares pedido pelo médico
    private Integer obj_id;
    private Doenças doenca;
    private boolean x, y;
    private int num_pacientes_hospital;

    public Hospital(String nome) {
        this.nome = nome;
        medicos = new TreeMap<>();
        lista_espera = new TreeMap<>();
        chefe = new TreeMap<>();
        especialista = new TreeMap<>();
        especialista_livre = new TreeMap<>();
        auxiliar = new TreeMap<>();
        auxiliar_livre = new TreeMap<>();
        ex_pacientes = new ArrayList<>();
        pedidos_auxiliares = new LinkedHashMap<>();
        num_pacientes_hospital = 0;
    }

    public String getNome() {
        return nome;
    }

    //Métodos que devolvem os mapas de arvores criados(chave = id, valor = objeto)
    public TreeMap<Integer, Médico> getMédicos() {
        return medicos;

    }

    public TreeMap<Integer, Enfermeiro_Chefe> getChefe() {
        return chefe;
    }

    public TreeMap<Integer, Pessoa> getLista_espera() {
        return lista_espera;
    }

    public TreeMap<Integer, Enfermeiro> getEspecialista() {
        return especialista;
    }

    public TreeMap<Integer, Enfermeiro> getAuxiliar() {
        return auxiliar;
    }

    public ArrayList<Pessoa> getEx_pacientes() {
        return ex_pacientes;
    }

    public TreeMap<Integer, Enfermeiro> getEspecialista_livre() {
        return especialista_livre;
    }

    public TreeMap<Integer, Enfermeiro> getAuxiliar_livre() {
        return auxiliar_livre;
    }

    public LinkedHashMap<Médico, Integer> getPedidos_auxiliares() {
        return pedidos_auxiliares;
    }

    public int getNum_pacientes_hospital() {
        return num_pacientes_hospital;
    }

    public void setNum_pacientes_hospital(int num_pacientes_hospital) {
        this.num_pacientes_hospital = num_pacientes_hospital;
    }

    //Método para adicionar objetos de cada classe ás listas correspondentes
    public void addMédico(Médico medico, int id) {
        obj_id = id;
        medicos.put(obj_id, medico);
    }

    public void addEnfermeiroEspecialista(Enfermeiro enfermeiro, int id) {
        obj_id = id;
        especialista.put(obj_id, enfermeiro);
        especialista_livre.put(obj_id, enfermeiro);
    }

    public void addEnfermeiroAuxiliar(Enfermeiro enfermeiro, int id) {
        obj_id = (Integer) id;
        auxiliar.put(obj_id, enfermeiro);
        auxiliar_livre.put(obj_id, enfermeiro);
    }

    public void addPaciente(Pessoa paciente, int id) {
        obj_id = (Integer) id;
        lista_espera.put(obj_id, paciente);
        this.setNum_pacientes_hospital(this.getNum_pacientes_hospital() + 1);
    }

    public void addChefe(Enfermeiro_Chefe enf_chefe, int id) {
        obj_id = (Integer) id;
        chefe.put(obj_id, enf_chefe);

    }

    //Métodos para aceder aos objetos dos funcionários através do ID
    public Enfermeiro identificarEspecialista(int id) {
        try {
            if (especialista.containsKey(id)) {
                return especialista.get(id);
            }
        } catch (NoSuchElementException exception) {
            System.out.println("Id não existente");
        }
        return null;
    }

    public Enfermeiro identificarEspecialistaLivre(int id) {
        try {
            if (especialista_livre.containsKey(id)) {
                return especialista_livre.get(id);
            }
        } catch (NoSuchElementException exception) {
            System.out.println("Id não existente");
        }
        return null;
    }

    public Enfermeiro identificarAuxiliar(int id) {
        try {
            if (auxiliar.containsKey(id)) {
                return auxiliar.get(id);
            }
        } catch (NoSuchElementException exception) {
            System.out.println("Id não existente");
        }
        return null;
    }

    public Médico identificarMédicos(int id) {
        try {
            if (medicos.containsKey(id)) {
                return medicos.get(id);
            }
        } catch (NoSuchElementException exception) {
            System.out.println("Id não existente");
        }
        return null;
    }

    public void aumentarAnosDeCarreira(int x) {

        for (int t : especialista.keySet()) {
            especialista.get(t).setAnos_carreira(especialista.get(t).getAnos_carreira() + x);
        }
        for (int t : auxiliar.keySet()) {
            auxiliar.get(t).setAnos_carreira(auxiliar.get(t).getAnos_carreira() + x);
        }
    }

    //Método para listar os enfermeiros todos
    public void listarEnfermeiros() {
        System.out.println("\n----Enfermeiros-Chefe----\n");
        for (int e : chefe.keySet()) {
            System.out.println("Enfermeiro: " + chefe.get(e).getNome() + " / ID: " + chefe.get(e).getId());
        }

        System.out.println("\n----Enfermeiros-Especialista----\n");
        for (int e : especialista.keySet()) {
            if (especialista.get(e).getMedico() == null) {
                System.out.println("Enfermeiro: " + especialista.get(e).getNome() + " / ID: " + especialista.get(e).getId() + " / Anos de carreira: " + especialista.get(e).getAnos_carreira() + " / Médico: Nenhum");
            } else {
                System.out.println("Enfermeiro: " + especialista.get(e).getNome() + " / ID: " + especialista.get(e).getId() + " / Anos de carreira: " + especialista.get(e).getAnos_carreira() + " / Médico: " + especialista.get(e).getMedico().getNome());
            }
        }

        System.out.println("\n----Enfermeiros-Auxiliar----\n");
        for (int e : auxiliar.keySet()) {
            if (auxiliar.get(e).getMedico() == null) {
                System.out.println("Enfermeiro: " + auxiliar.get(e).getNome() + " / ID: " + auxiliar.get(e).getId() + " / Anos de carreira: " + auxiliar.get(e).getAnos_carreira() + " / Médico: Nenhum");

            } else {
                System.out.println("Enfermeiro: " + auxiliar.get(e).getNome() + " / ID: " + auxiliar.get(e).getId() + " / Anos de carreira: " + auxiliar.get(e).getAnos_carreira() + " / Médico: " + auxiliar.get(e).getMedico().getNome());
            }
        }
    }

    //Método para listar os Médicos
    public void listarMédicos() {
        for (int i : medicos.keySet()) {
            System.out.println("Médico: " + medicos.get(i).getNome() + " / ID: " + medicos.get(i).getId());
        }
    }

    //Método para listar os objetos na fila de espera
    public void listarPacientes() {
        for (int i : lista_espera.keySet()) {
            System.out.println("Paciente: " + lista_espera.get(i).getNome() + " / ID: " + lista_espera.get(i).getId() + " / Ano de Nascimento: " + lista_espera.get(i).getAno_nascimento() + " / Doença: " + lista_espera.get(i).getDoenca());
        }
    }

    //Método para listar pacientes que saíram do hospital
    public void listarExPacientes() {
        try {
            for (int i = 0; i < ex_pacientes.size(); i++) {
                System.out.print("Paciente: " + ex_pacientes.get(i).getNome() + " / ID: " + ex_pacientes.get(i).getId() + " / Ano de Nascimento: " + ex_pacientes.get(i).getAno_nascimento());
                if (ex_pacientes.get(i).isMorte()) {
                    System.out.println(" / Falecido: Sim");
                } else {
                    System.out.println(" / Falecido: Não");
                }
            }
        } catch (NullPointerException exception) {
            System.out.println("Lista encontra-se vazia");
        }
    }

    //Método para listar pacientes que faleceram no hospital
    public void listarExPacientesFalecidos() {
        try {
            for (int i = 0; i < ex_pacientes.size(); i++) {
                System.out.print("Paciente: " + ex_pacientes.get(i).getNome() + " / ID: " + ex_pacientes.get(i).getId() + " / Ano de Nascimento: " + ex_pacientes.get(i).getAno_nascimento());
                if (ex_pacientes.get(i).isMorte()) {
                    System.out.print("Paciente: " + ex_pacientes.get(i).getNome() + " / ID: " + ex_pacientes.get(i).getId() + " / Ano de Nascimento: " + ex_pacientes.get(i).getAno_nascimento());
                }
            }
        } catch (NullPointerException exception) {
            System.out.println("Não ocorreu nenhum falecimento no Hospital");
        }
    }

    //Listar pedidos para enfermeiros-auxiliares
    public void listarPedidosEnfermeiroAuxiliar() {
        for (Médico i : pedidos_auxiliares.keySet()) {
            System.out.println("Médico: " + i.getNome() + " | Quantidade de auxiliares pedidos: " + pedidos_auxiliares.get(i));
        }
    }

    //Método para criar um vírus outbreak
    public void virusOutbreak(Random rand) {
        doenca = Doenças.randDoença(rand);

        for (int i : especialista.keySet()) {
            x = rand.nextBoolean();
            y = rand.nextBoolean();
            if (x && y) {
                especialista.get(i).setDoenca(doenca);
                lista_espera.put(especialista.get(i).getId(), especialista.get(i));
            }
        }
        for (int i : auxiliar.keySet()) {
            x = rand.nextBoolean();
            y = rand.nextBoolean();
            if (x && y) {
                auxiliar.get(i).setDoenca(doenca);
                lista_espera.put(auxiliar.get(i).getId(), auxiliar.get(i));
            }
        }
        for (int i : medicos.keySet()) {
            x = rand.nextBoolean();
            y = rand.nextBoolean();
            if (x && y) {
                medicos.get(i).setDoenca(doenca);
                lista_espera.put(medicos.get(i).getId(), medicos.get(i));
            }
        }
        for (int i = 0; i < ex_pacientes.size(); i++) {
            x = rand.nextBoolean();
            y = rand.nextBoolean();
            if (x && y) {
                ex_pacientes.get(i).setDoenca(doenca);
                lista_espera.put(ex_pacientes.get(i).getId(), ex_pacientes.get(i));
                ex_pacientes.remove(i);
            }
        }
    }


    //Triturar pedidos de auxiliar
    public void triturarPedidos(Random rand) {
        boolean triturar;
        ArrayList<Médico> pedidos_medico;
        pedidos_medico = new ArrayList<>();

        //criar string com os objetos medico a retirar os pedidos
        for (Médico i : pedidos_auxiliares.keySet()) {
            triturar = rand.nextBoolean();
            if (triturar) {
                pedidos_medico.add(i);
            }
        }

        //percorrer a string criada e retirar os objetos do treemap
        for (int i = 0; i < pedidos_medico.size(); i++) {
            pedidos_auxiliares.remove(pedidos_medico.get(i));
        }
    }

    public int calculoPacientesHospital(int a, int b) {
        return this.getNum_pacientes_hospital() - this.getEx_pacientes().size();
    }

    public int pacientesFalecidos() {
        int soma = 0;

        for (int i = 0; i < this.getEx_pacientes().size(); i++) {
            if(this.getEx_pacientes().get(i).isMorte()){
                soma++;
            }
        }
        return soma;
    }
    
    public int medicosDisponíveis (){
        int soma = 0;
        
        for (int i : this.getMédicos().keySet()){
            if(this.getMédicos().get(i).getPacientes().isEmpty()){
                soma++;
            }
        }
        return soma;
    }

    public void relatorioPacientes() {
        System.out.println("Número de pacientes no Hospital: " + this.getNum_pacientes_hospital());
        System.out.println("Número de pacientes na fila de espera: " + lista_espera.size());
        System.out.println("Número de pacientes que saíram do Hospital: " + this.getEx_pacientes().size());
        System.out.println("Número de pacientes que faleceram no Hospital: " + pacientesFalecidos());
        System.out.println("Número de pacientes com patologias activas: " + Doenças.numeroInfectados());
    }
    
    public void relatorioFuncionarios() {
        System.out.println("Número de médicos do Hospital: " + this.getMédicos().size());
        System.out.println("Número de médicos do Hospital: " + medicosDisponíveis());
        System.out.println("Número de enfermeiros do Hospital: " + (this.getChefe().size() + this.getEspecialista().size() + this.getAuxiliar().size()));
        System.out.println("Número de enfermeiros-chefe do Hospital: " + this.getChefe().size());
        System.out.println("Número de enfermeiros-especialistas do Hospital: " + this.getEspecialista().size());
        System.out.println("Número de enfermeiros-auxiliares do Hospital: " + this.getAuxiliar().size());
    }
}
