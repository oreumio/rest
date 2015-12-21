package com.oreumio.james.rest.send;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlAddressVo {

    private String personal;

    private String address;

    public EmlAddressVo() {
    }

    public EmlAddressVo(String address) {
        this.address = address;
    }

    public EmlAddressVo(String personal, String address) {
        this.personal = personal;
        this.address = address;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "EmlAddressVo{" +
                "personal='" + personal + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
