package com.oreumio.james.rest.form;

import javax.persistence.*;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Entity
@Table(name = "EML_MAIL_FORM_CC")
public class EmlMailFormCc {

    @Id
    @Column(name = "MAIL_FORM_FROM_ID", nullable = false, length = 30)
    private String id;

    @ManyToOne
    @JoinColumn(name = "MAIL_FORM_ID", nullable = false)
    private EmlMailForm mailForm;

    @Column(name = "MAIL_FORM_FROM_ADDRESS", nullable = false)
    private String address;

    @Column(name = "MAIL_FORM_FROM_PERSONAL")
    private String personal;

    public EmlMailFormCc() {
    }

    public EmlMailFormCc(EmlMailFormCcVo emlAddressVo) {
        address = emlAddressVo.getAddress();
        personal = emlAddressVo.getPersonal();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EmlMailForm getMailForm() {
        return mailForm;
    }

    public void setMailForm(EmlMailForm mailForm) {
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
        return "EmlMailFormCc{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", personal='" + personal + '\'' +
                '}';
    }
}
