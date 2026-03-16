import java.util.ArrayList;

public class Turma {
    private String curso;
    private String sigla;
    private Periodo periodo;
    private boolean ativo;

    public Turma(String curso, String sigla, Periodo periodo) {
        this.curso = curso;
        this.sigla = sigla;
        this.periodo = periodo;
        this.ativo = true;
    }

    public Turma() {
        this.curso = "";
        this.sigla = "";
        this.periodo = Periodo.MATUTINO;
        this.ativo = true;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "Turma{" +
                "curso='" + curso + '\'' +
                ", sigla='" + sigla + '\'' +
                ", periodo=" + periodo +
                '}';
    }
}