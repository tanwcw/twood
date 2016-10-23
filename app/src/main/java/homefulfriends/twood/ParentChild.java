package homefulfriends.twood;

/**
 * Created by jayne on 10/22/16.
 */

public class ParentChild {
    private String parentEmail;
    private String childEmail;

    public ParentChild(String parentEmail, String childEmail) {
        this.parentEmail = parentEmail;
        this.childEmail = childEmail;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }

    public String getChildEmail() {
        return childEmail;
    }

    public void setChildEmail(String childEmail) {
        this.childEmail = childEmail;
    }

    @Override
    public String toString() {
        return "ParentChild{" +
                "parentEmail='" + parentEmail + '\'' +
                ", childEmail='" + childEmail + '\'' +
                '}';
    }
}
