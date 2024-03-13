
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rafae
 */
public enum Doenças {
    Covid_19(3), Ébola(60), Gripe(3), Tuberculose(10), Doença_das_vacas_loucas(100), Varíola(30), DoençaErrada(0);

    private final int fatalidade;
    private int num_pacientes;

    private Doenças(int fatalidade) {
        this.fatalidade = fatalidade;
        this.num_pacientes = 0;
    }

    public int getNum_pacientes() {
        return num_pacientes;
    }

    public void setNum_pacientes(int num_pacientes) {
        this.num_pacientes = num_pacientes;
    }

    public static void addDoença(Pessoa paciente) {
        Random rand = new Random();
        int x = rand.nextInt(100);

        if (x < 5) {
            paciente.setDoenca(Doença_das_vacas_loucas);
        } else if (x >= 5 && x < 15) {
            paciente.setDoenca(Ébola);
        } else if (x >= 15 && x < 28) {
            paciente.setDoenca(Varíola);
        } else if (x >= 28 && x < 48) {
            paciente.setDoenca(Tuberculose);
        } else if (x >= 48 && x < 72) {
            paciente.setDoenca(Gripe);
        } else {
            paciente.setDoenca(Covid_19);
        }

        paciente.getDoenca().setNum_pacientes(paciente.getDoenca().getNum_pacientes() + 1);
    }

    public static Doenças getCovid_19() {
        return Covid_19;
    }

    public static Doenças getÉbola() {
        return Ébola;
    }

    public static Doenças getGripe() {
        return Gripe;
    }

    public static Doenças getTuberculose() {
        return Tuberculose;
    }

    public static Doenças getDoença_das_vacas_loucas() {
        return Doença_das_vacas_loucas;
    }

    public static Doenças getVaríola() {
        return Varíola;
    }

    public int getFatalidade() {
        return fatalidade;
    }

    public static Doenças randDoença(Random rand) {
        int x = rand.nextInt(100);

        if (x < 2) {
            return Doença_das_vacas_loucas;
        }
        if (x >= 2 && x < 6) {
            return Ébola;
        }
        if (x >= 6 && x < 16) {
            return Varíola;
        }
        if (x >= 16 && x < 31) {
            return Tuberculose;
        }
        if (x >= 31 && x < 65) {
            return Gripe;
        } else {
            return Covid_19;
        }
    }

    public static int numeroInfectados() {
        return Covid_19.getNum_pacientes() + Doença_das_vacas_loucas.getNum_pacientes() + Ébola.getNum_pacientes() + Tuberculose.getNum_pacientes() + Varíola.getNum_pacientes() + Gripe.getNum_pacientes();
    }
}
