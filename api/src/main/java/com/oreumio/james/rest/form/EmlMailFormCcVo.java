package com.oreumio.james.rest.form;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlMailFormCcVo implements EmlAddressVo {

    private String id;

    private String address;

    private String personal;

    public EmlMailFormCcVo() {
    }

    public EmlMailFormCcVo(EmlMailFormCc emlMailFormCc) {
        address = emlMailFormCc.getAddress();
        personal = emlMailFormCc.getPersonal();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    @Override
    public String toString() {
        return "EmlMailFormCcVo{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", personal='" + personal + '\'' +
                '}';
    }
}
