package app.netlify.nmhillusion.eciapp.model;

import tech.nmhillusion.n2mix.type.Stringeable;

/**
 * date: 2023-03-05
 * <p>
 * created-by: nmhillusion
 */

public class StatusModel extends Stringeable {
    private String statusName;
    private String statusDetail;

    public String getStatusName() {
        return statusName;
    }

    public StatusModel setStatusName(String statusName) {
        this.statusName = statusName;
        return this;
    }

    public String getStatusDetail() {
        return statusDetail;
    }

    public StatusModel setStatusDetail(String statusDetail) {
        this.statusDetail = statusDetail;
        return this;
    }
}
