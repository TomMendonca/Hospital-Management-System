
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rafae
 */
public class Enfermeiro extends Pessoa {

    private int anos_carreira;
    private boolean especialista;
    private ArrayList<Pessoa> pacientes_a_curar;
    private Médico medico;

    //Método de passagem de auxiliar para especialista (criar array para 3 pacientes)
    public Enfermeiro(String nome, int ano_nascimento, int id, boolean especialista, boolean doente) {
        super(nome, ano_nascimento, id, doente);
        this.anos_carreira = 0;
        this.especialista = especialista;
        this.pacientes_a_curar = new ArrayList<>(3);
        this.medico = null;
    }

    public void auxiliarParaEspecialista(Object Enfermeiro) {
        this.especialista = true;

    }

    public boolean isEspecialista() {
        return especialista;
    }

    public void setAnos_carreira(int anos_carreira) {
        this.anos_carreira = anos_carreira;
    }

    public int getAnos_carreira() {
        return anos_carreira;
    }

    public ArrayList<Pessoa> getPacientes_a_curar() {
        return pacientes_a_curar;
    }

    public void setPacientes_a_curar(ArrayList<Pessoa> pacientes_a_curar) {
        this.pacientes_a_curar = pacientes_a_curar;
    }

    public void setMedico(Médico medico) {
        this.medico = medico;
    }

    public Médico getMedico() {
        return medico;
    }

    public void aplicarCurativo(Pessoa paciente, int x) { // x = rand.nextInt(21)

        //Curativo errado/verificar fatalidade 
        if (!paciente.getDiagnostico().equals(paciente.getDoenca())) {
            if ((x * paciente.getDoenca().getFatalidade()) >= 40) {
                paciente.getDoenca().setNum_pacientes(paciente.getDoenca().getNum_pacientes() - 1);
                paciente.setMorte(true);
            }
            paciente.setDiagnostico(null);
        } //Curativo correto/verificar eficácia
        else {
            switch (paciente.getDoenca()) {
                case Covid_19:
                    if (x < 5) { //Verifica se o curativo resultou (x varia entre 0 e 20
                        paciente.getDoenca().setNum_pacientes(paciente.getDoenca().getNum_pacientes() - 1);
                        paciente.setDoenca(null);
                    }
                    break;
                case Ébola:
                    if (x < 3) {
                        paciente.getDoenca().setNum_pacientes(paciente.getDoenca().getNum_pacientes() - 1);
                        paciente.setDoenca(null);
                    }
                    break;
                case Gripe:
                    if (x < 15) {
                        paciente.getDoenca().setNum_pacientes(paciente.getDoenca().getNum_pacientes() - 1);
                        paciente.setDoenca(null);
                    }
                    break;
                case Tuberculose:
                    if (x < 8) {
                        paciente.getDoenca().setNum_pacientes(paciente.getDoenca().getNum_pacientes() - 1);
                        paciente.setDoenca(null);
                    }
                    break;
                case Doença_das_vacas_loucas:
                    //Não tem curativo, morte certa
                    paciente.getDoenca().setNum_pacientes(paciente.getDoenca().getNum_pacientes() - 1);
                    paciente.setMorte(true);
                    break;
                case Varíola:
                    if (x < 5) {
                        paciente.getDoenca().setNum_pacientes(paciente.getDoenca().getNum_pacientes() - 1);
                        paciente.setDoenca(null);
                    }
                    break;

            }
        }
        this.medico.getPacientes_aguardar_alta().add(paciente);
        for (Enfermeiro e : this.medico.getEspecialista()) {
            e.getPacientes_a_curar().remove(paciente);
        }
        for (Enfermeiro e : this.medico.getAuxiliar()) {
            e.getPacientes_a_curar().remove(paciente);
        }
    }
}
