package ma.projet.classes;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * EmployeTache 的复合主键：由员工编号和任务编号组成。
 */
@Embeddable
public class EmployeTachePK implements Serializable {

    // 员工编号
    @Column(name = "employe_id")
    private int employeId;

    // 任务编号
    @Column(name = "tache_id")
    private int tacheId;

    public EmployeTachePK() {
    }

    public EmployeTachePK(int employeId, int tacheId) {
        this.employeId = employeId;
        this.tacheId = tacheId;
    }

    public int getEmployeId() { return employeId; }
    public void setEmployeId(int employeId) { this.employeId = employeId; }
    public int getTacheId() { return tacheId; }
    public void setTacheId(int tacheId) { this.tacheId = tacheId; }

    // 复合主键必须重写 equals 和 hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeTachePK)) return false;
        EmployeTachePK that = (EmployeTachePK) o;
        return employeId == that.employeId && tacheId == that.tacheId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeId, tacheId);
    }
}
