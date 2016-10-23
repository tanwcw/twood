package homefulfriends.twood;

public class User {
    private String name;
    private int bankDetails;
    private String email;
    private Boolean isParent;
    private String childKey;
    private String modoId;
    private String childCheckingVaultID;
    private String childSavingVaultID;
    private String parentVaultID;
    private int mobile;

    public User(){}

    public User(String name, int bankDetails, String email, Boolean isParent, String childKey, String modoId, String childCheckingVaultID, String childSavingVaultID, String parentVaultID, int mobile) {
        this.name = name;
        this.bankDetails = bankDetails;
        this.email = email;
        this.isParent = isParent;
        this.childKey = childKey;
        this.modoId = modoId;
        this.childCheckingVaultID = childCheckingVaultID;
        this.childSavingVaultID = childSavingVaultID;
        this.parentVaultID = parentVaultID;
        this.mobile = mobile;
    }

    public User(String name, int mobile){
        this.name = name;
        this.mobile = mobile;
    }

    public User(int bankDetails){this.bankDetails = bankDetails;}

    public User(String name, int bankDetails, String email, Boolean isParent) {
        this.name = name;
        this.bankDetails = bankDetails;
        this.email = email;
        this.isParent = isParent;
    }

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public int getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(int bankDetails) {
        this.bankDetails = bankDetails;
    }

    public Boolean getParent() {
        return isParent;
    }

    public void setParent(Boolean parent) {
        isParent = parent;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getChildKey() {
        return childKey;
    }

    public void setChildKey(String childKey) {
        this.childKey = childKey;
    }

    public String getModoId() {
        return modoId;
    }

    public void setModoId(String modoId) {
        this.modoId = modoId;
    }

    public String getChildCheckingVaultID() {
        return childCheckingVaultID;
    }

    public void setChildCheckingVaultID(String childCheckingVaultID) {
        this.childCheckingVaultID = childCheckingVaultID;
    }

    public String getChildSavingVaultID() {
        return childSavingVaultID;
    }

    public void setChildSavingVaultID(String childSavingVaultID) {
        this.childSavingVaultID = childSavingVaultID;
    }

    public String getParentVaultID() {
        return parentVaultID;
    }

    public void setParentVaultID(String parentVaultID) {
        this.parentVaultID = parentVaultID;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", bankDetails=" + bankDetails +
                ", email='" + email + '\'' +
                ", isParent=" + isParent +
                '}';
    }
}
