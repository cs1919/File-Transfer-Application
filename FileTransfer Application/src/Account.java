public class Account {
    private int acc_id;
    private String name;
    private String ip_address;
    private String last_login;
    
    private Account(Builder builder) {
        this.acc_id = builder.acc_id;
        this.name = builder.name;
        this.ip_address = builder.ip_address;
        this.last_login = builder.last_login;
    }
    
    // Getter methods for all fields
    public int getAccId() {
        return acc_id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getIpAddress() {
        return ip_address;
    }
    
    public String getLastLogin() {
        return last_login;
    }
    
    // Setter methods for name, ip_address, and last_login
    public void setName(String name) {
        this.name = name;
    }
    
    public void setIpAddress(String ip_address) {
        this.ip_address = ip_address;
    }
    
    public void setLastLogin(String last_login) {
        this.last_login = last_login;
    }
    
    // Static Builder class (unchanged)
    public static class Builder {
        private int acc_id;
        private String name;
        private String ip_address = "";
        private String last_login = "";
        
        public Builder(int acc_id, String name) {
            this.acc_id = acc_id;
            this.name = name;
        }
        
        public Builder ipAddress(String ip_address) {
            this.ip_address = ip_address;
            return this;
        }
        
        public Builder lastLogin(String last_login) {
            this.last_login = last_login;
            return this;
        }
        
        public Account build() {
            return new Account(this);
        }
    }
}
