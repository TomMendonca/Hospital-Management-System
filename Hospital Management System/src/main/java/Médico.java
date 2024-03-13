/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rafae
 */
import java.util.ArrayList;
import java.util.Iterator;

public class Médico extends Pessoa {

    private ArrayList<Enfermeiro> especialista;
    private ArrayList<Enfermeiro> auxiliar;
    private ArrayList<Pessoa> pacientes; //Pacientes os quais o médico é responsavel (MAX=3)
    private ArrayList<Pessoa> pacientes_aguardar_alta;
    private ArrayList<Pessoa> pacientes_a_diagnosticar;

    public Médico(String nome, int ano_nascimento, int id, boolean doente) {
        super(nome, ano_nascimento, id, doente);
        this.especialista = new ArrayList<>();
        this.auxiliar = new ArrayList<>();
        this.pacientes = new ArrayList<>(3);
        this.pacientes_aguardar_alta = new ArrayList<>(3);
        this.pacientes_a_diagnosticar = new ArrayList<>(3);

    }

    public ArrayList<Pessoa> getPacientes() {
        return pacientes;
    }

    public ArrayList<Enfermeiro> getEspecialista() {
        return especialista;
    }

    public ArrayList<Enfermeiro> getAuxiliar() {
        return auxiliar;
    }

    public ArrayList<Pessoa> getPacientes_aguardar_alta() {
        return pacientes_aguardar_alta;
    }

    public ArrayList<Pessoa> getPacientes_a_diagnosticar() {
        return pacientes_a_diagnosticar;
    }

    public void setPacientes(ArrayList<Pessoa> pacientes) {
        this.pacientes = pacientes;
    }

    //2 métodos que juntos adicionam pacientes a um médico, uma para adicionar e outro para verificar se a lista de médicos não está cheia
    public void pacientesMédico(Pessoa paciente) {
        this.getPacientes().add(paciente);
        this.getPacientes_a_diagnosticar().add(paciente);
    }

    public void diagnosticarPaciente(Pessoa paciente, int rand, Hospital H) {

        if (rand < 25) { //Médico errou no diagnóstico (rand.nextInt(100))
            paciente.setDiagnostico(Doenças.DoençaErrada);
        } else if (paciente.getDoenca() == null) {//Médico dá alta ao paciente porque ele não tem uma doença
            darAltaPaciente(paciente, H);
            this.getPacientes_a_diagnosticar().remove(paciente);
            return;
        } else {
            paciente.setDiagnostico(paciente.getDoenca()); //Médico acertou no diagnóstico e o paciente tem uma doença
        }
        for (Enfermeiro e: this.getAuxiliar()){
            e.getPacientes_a_curar().add(paciente);
        }
        this.getPacientes_a_diagnosticar().remove(paciente);

    }

    public void darAltaPaciente(Pessoa paciente, Hospital H) {
        //Se já estiver curado ou morto
        if (paciente.getDoenca() == null || paciente.isMorte()) {

            H.getEx_pacientes().add(paciente); //Adicionar aos pacientes que saíram do Hospital
            this.getPacientes().remove(paciente);
            for (Enfermeiro i : this.especialista) {
                i.getPacientes_a_curar().remove(paciente); // Retirar o paciente do cuidado dos enfermeiros
            }
            for (Enfermeiro i : this.auxiliar) {
                i.getPacientes_a_curar().remove(paciente); // Retirar o paciente do cuidado dos enfermeiros-auxiliar
            }

        } else {
            paciente.setDiagnostico(null);
            this.pacientes_a_diagnosticar.add(paciente);
        }
        this.getPacientes_aguardar_alta().remove(paciente);
    }

    //Métodos que listam enfermeiros de um médico
    public void listarAuxiliares(Médico medico) {

        if (medico.getEspecialista().isEmpty()) {
            return;
        }

        Iterator<Enfermeiro> iter_aux = medico.getAuxiliar().iterator();
        System.out.print("Enfermeiros-Auxiliares: ");

        while (iter_aux.hasNext()) {
            System.out.print("| " + iter_aux.next().getNome() + " | ");
        }
        System.out.println();
    }

    public void listarEspecialista(Médico medico) {

        if (medico.getEspecialista().isEmpty()) {
            return;
        }

        Iterator<Enfermeiro> iter_esp = medico.getEspecialista().iterator();
        System.out.print("Enfermeiros-Especialista: ");

        while (iter_esp.hasNext()) {
            System.out.print("| " + iter_esp.next().getNome() + " | ");
        }
        System.out.println();
    }

    public void listarPacientesAguardarAlta() {
        for (int i = 0; i < pacientes_aguardar_alta.size(); i++) {
            System.out.println("Paciente: " + pacientes_aguardar_alta.get(i).getNome() + " / ID: " + pacientes_aguardar_alta.get(i).getId() + " / Ano de Nascimento: " + pacientes_aguardar_alta.get(i).getAno_nascimento());
        }
    }

    public boolean listaMedicoPacientes() {
        return this.getPacientes().size() == 3;
    }
}
