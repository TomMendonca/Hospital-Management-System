

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rafae
 */
public class Pessoa {

    private int id, ano_nascimento;
    private String nome;
    private Doenças doenca;
    private Doenças diagnostico;
    private boolean morte;

    public Pessoa(String nome, int ano_nascimento, int id, boolean doente) {
        this.ano_nascimento = ano_nascimento;
        this.nome = nome;
        this.id = id;
        this.morte = false;

        if (doente) {
            Doenças.addDoença(this);
        }
    }

    public void setDoenca(Doenças doenca) {
        this.doenca = doenca;
    }

    public Doenças getDoenca() {
        return doenca;
    }

    public Doenças getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(Doenças diagnostico) {
        this.diagnostico = diagnostico;
    }

    public void setMorte(boolean morte) {
        this.morte = morte;
    }

    public int getId() {
        return id;
    }

    public int getAno_nascimento() {
        return ano_nascimento;
    }

    public String getNome() {
        return nome;
    }

    public boolean isMorte() {
        return morte;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAno_nascimento(int ano_nascimento) {
        this.ano_nascimento = ano_nascimento;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
