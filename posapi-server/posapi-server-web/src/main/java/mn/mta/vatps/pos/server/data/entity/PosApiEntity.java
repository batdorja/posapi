package mn.mta.vatps.pos.server.data.entity;

import javax.persistence.*;

/**
 * Created by nasanjargal on 1/5/16.
 */

@Entity
@Table(name = "pos_api", schema = "public", uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
public class PosApiEntity {
    private String id;

    @Id
    @Column(name = "id", nullable = false, unique = true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String path;

    @Column(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String registerNo;

    @Column(name = "registerNo")
    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }

    private String branchNo;

    @Column(name = "branchNo")
    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }
}
