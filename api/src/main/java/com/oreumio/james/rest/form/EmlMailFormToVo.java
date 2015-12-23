package com.oreumio.james.rest.form;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlMailFormToVo implements EmlAddressVo {

    private String id;

    private EmlMailFormVo mailForm;

    private String address;

    private String personal;

    public EmlMailFormToVo() {
    }

    public EmlMailFormToVo(EmlMailFormTo emlMailFormTo) {
        address = emlMailFormTo.getAddress();
        personal = emlMailFormTo.getPersonal();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EmlMailFormVo getMailForm() {
        return mailForm;
    }

    public void setMailForm(EmlMailFormVo mailForm) {
        this.mailForm = mailForm;
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
        return "EmlMailFormToVo{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", personal='" + personal + '\'' +
                '}';
    }
}
