/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rafae
 */
public class Enfermeiro_Chefe extends Pessoa {

    private static int anos_de_carreira_minimos = 3;

    public Enfermeiro_Chefe(String nome, int ano_nascimento, int id, boolean doente) {
        super(nome, ano_nascimento, id, doente);
    }

    public static void setAnos_de_carreira_minimos(int anos_de_carreira_minimos) {
        Enfermeiro_Chefe.anos_de_carreira_minimos = anos_de_carreira_minimos;
    }

    public static int getAnos_de_carreira_minimos() {
        return anos_de_carreira_minimos;
    }

    public void atribuirEspecialistaMédico(Enfermeiro enfermeiro, Médico medico, Hospital H) {

        medico.getEspecialista().add(enfermeiro);
        enfermeiro.setPacientes_a_curar(medico.getPacientes());
        enfermeiro.setMedico(medico);
        H.getEspecialista_livre().remove(enfermeiro.getId());

    }

    public static boolean verificaAnos_carreira(int anos_carreira) {
        return anos_carreira >= Enfermeiro_Chefe.anos_de_carreira_minimos;
    }
}
