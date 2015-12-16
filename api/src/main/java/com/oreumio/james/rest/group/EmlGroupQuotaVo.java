package com.oreumio.james.rest.group;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlGroupQuotaVo {
    private long serverQuota = 1024 * 1024 * 1024 * 1024;
    private long defaultUserQuota;
    private long defaultAttachmentSize;

    public EmlGroupQuotaVo() {
    }

    public EmlGroupQuotaVo(long serverQuota, long defaultUserQuota, long defaultAttachmentSize) {
        this.serverQuota = serverQuota;
        this.defaultUserQuota = defaultUserQuota;
        this.defaultAttachmentSize = defaultAttachmentSize;
    }

    @Override
    public String toString() {
        return "EmlClientQuotaVo{" +
                "serverQuota=" + serverQuota +
                ", defaultUserQuota=" + defaultUserQuota +
                ", defaultAttachmentSize=" + defaultAttachmentSize +
                '}';
    }

    public long getServerQuota() {
        return serverQuota;
    }

    public void setServerQuota(long serverQuota) {
        this.serverQuota = serverQuota;
    }

    public long getDefaultUserQuota() {
        return defaultUserQuota;
    }

    public void setDefaultUserQuota(long defaultUserQuota) {
        this.defaultUserQuota = defaultUserQuota;
    }

    public long getDefaultAttachmentSize() {
        return defaultAttachmentSize;
    }

    public void setDefaultAttachmentSize(long defaultAttachmentSize) {
        this.defaultAttachmentSize = defaultAttachmentSize;
    }
}
