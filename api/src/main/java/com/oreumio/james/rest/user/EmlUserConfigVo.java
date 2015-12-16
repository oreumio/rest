package com.oreumio.james.rest.user;

import java.util.List;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlUserConfigVo {
    private String listDisplayType;
    private int listSize;
    private String listPreviewContent;
    private List<String> listColumns;

    private String readOpenNewWindow;
    private String readDisplaySingleLineSubject;
    private String readDisplayAllRecipients;

    private String writeOpenNewWindow;
    private String writeDisplayBCC;
    private String writeIncludeFooter;
    private String writeIncludeFooterWhenReply;
    private String writePreferredSender;
    private String writeReadConfirmMail;
    private String writeReceiveReadConfirmMail;
    private String writeSendConfirmMailType;

    private String sendPreferredAfterPage;

    private String useFooter;
    private int footer;
    private List<EmlSignatureVo> footers;

    private String useAbsence;
    private String absenceStart;
    private String absenceEnd;
    private String absenceContent;

    private String useBlocker;
    private String blockerAction;
    private List<EmlBlockerVo> blockers;

    private String useForwarder;
    private String forwarderStoreAndForward;
    private List<EmlForwarderVo> forwarders;

    private String useClassifier;

    private String useLabeler;

    public static class EmlSignatureVo {
        private int key;
        private String name;
        private String content;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class EmlBlockerVo {
        private List<String> addresses;

        public List<String> getAddresses() {
            return addresses;
        }

        public void setAddresses(List<String> addresses) {
            this.addresses = addresses;
        }

        @Override
        public String toString() {
            return "EmlBlockerVo{" +
                    ", addresses=" + addresses +
                    '}';
        }
    }

    public static class EmlForwarderVo {
        private String addresses;

        public String getAddresses() {
            return addresses;
        }

        public void setAddresses(String addresses) {
            this.addresses = addresses;
        }

        @Override
        public String toString() {
            return "EmlForwarderVo{" +
                    ", addresses='" + addresses + '\'' +
                    '}';
        }
    }

    public String getListDisplayType() {
        return listDisplayType;
    }

    public void setListDisplayType(String listDisplayType) {
        this.listDisplayType = listDisplayType;
    }

    public int getListSize() {
        return listSize;
    }

    public void setListSize(int listSize) {
        this.listSize = listSize;
    }

    public String getListPreviewContent() {
        return listPreviewContent;
    }

    public void setListPreviewContent(String listPreviewContent) {
        this.listPreviewContent = listPreviewContent;
    }

    public List<String> getListColumns() {
        return listColumns;
    }

    public void setListColumns(List<String> listColumns) {
        this.listColumns = listColumns;
    }

    public String getReadOpenNewWindow() {
        return readOpenNewWindow;
    }

    public void setReadOpenNewWindow(String readOpenNewWindow) {
        this.readOpenNewWindow = readOpenNewWindow;
    }

    public String getReadDisplaySingleLineSubject() {
        return readDisplaySingleLineSubject;
    }

    public void setReadDisplaySingleLineSubject(String readDisplaySingleLineSubject) {
        this.readDisplaySingleLineSubject = readDisplaySingleLineSubject;
    }

    public String getReadDisplayAllRecipients() {
        return readDisplayAllRecipients;
    }

    public void setReadDisplayAllRecipients(String readDisplayAllRecipients) {
        this.readDisplayAllRecipients = readDisplayAllRecipients;
    }

    public String getWriteOpenNewWindow() {
        return writeOpenNewWindow;
    }

    public void setWriteOpenNewWindow(String writeOpenNewWindow) {
        this.writeOpenNewWindow = writeOpenNewWindow;
    }

    public String getWriteDisplayBCC() {
        return writeDisplayBCC;
    }

    public void setWriteDisplayBCC(String writeDisplayBCC) {
        this.writeDisplayBCC = writeDisplayBCC;
    }

    public String getWriteIncludeFooter() {
        return writeIncludeFooter;
    }

    public void setWriteIncludeFooter(String writeIncludeFooter) {
        this.writeIncludeFooter = writeIncludeFooter;
    }

    public String getWriteIncludeFooterWhenReply() {
        return writeIncludeFooterWhenReply;
    }

    public void setWriteIncludeFooterWhenReply(String writeIncludeFooterWhenReply) {
        this.writeIncludeFooterWhenReply = writeIncludeFooterWhenReply;
    }

    public String getWritePreferredSender() {
        return writePreferredSender;
    }

    public void setWritePreferredSender(String writePreferredSender) {
        this.writePreferredSender = writePreferredSender;
    }

    public String getWriteReadConfirmMail() {
        return writeReadConfirmMail;
    }

    public void setWriteReadConfirmMail(String writeReadConfirmMail) {
        this.writeReadConfirmMail = writeReadConfirmMail;
    }

    public String getWriteReceiveReadConfirmMail() {
        return writeReceiveReadConfirmMail;
    }

    public void setWriteReceiveReadConfirmMail(String writeReceiveReadConfirmMail) {
        this.writeReceiveReadConfirmMail = writeReceiveReadConfirmMail;
    }

    public String getWriteSendConfirmMailType() {
        return writeSendConfirmMailType;
    }

    public void setWriteSendConfirmMailType(String writeSendConfirmMailType) {
        this.writeSendConfirmMailType = writeSendConfirmMailType;
    }

    public String getSendPreferredAfterPage() {
        return sendPreferredAfterPage;
    }

    public void setSendPreferredAfterPage(String sendPreferredAfterPage) {
        this.sendPreferredAfterPage = sendPreferredAfterPage;
    }

    public String getUseFooter() {
        return useFooter;
    }

    public void setUseFooter(String useFooter) {
        this.useFooter = useFooter;
    }

    public int getFooter() {
        return footer;
    }

    public void setFooter(int footer) {
        this.footer = footer;
    }

    public List<EmlSignatureVo> getFooters() {
        return footers;
    }

    public void setFooters(List<EmlSignatureVo> footers) {
        this.footers = footers;
    }

    public String getUseAbsence() {
        return useAbsence;
    }

    public void setUseAbsence(String useAbsence) {
        this.useAbsence = useAbsence;
    }

    public String getAbsenceStart() {
        return absenceStart;
    }

    public void setAbsenceStart(String absenceStart) {
        this.absenceStart = absenceStart;
    }

    public String getAbsenceEnd() {
        return absenceEnd;
    }

    public void setAbsenceEnd(String absenceEnd) {
        this.absenceEnd = absenceEnd;
    }

    public String getAbsenceContent() {
        return absenceContent;
    }

    public void setAbsenceContent(String absenceContent) {
        this.absenceContent = absenceContent;
    }

    public String getUseBlocker() {
        return useBlocker;
    }

    public void setUseBlocker(String useBlocker) {
        this.useBlocker = useBlocker;
    }

    public String getBlockerAction() {
        return blockerAction;
    }

    public void setBlockerAction(String blockerAction) {
        this.blockerAction = blockerAction;
    }

    public List<EmlBlockerVo> getBlockers() {
        return blockers;
    }

    public void setBlockers(List<EmlBlockerVo> blockers) {
        this.blockers = blockers;
    }

    public String getUseForwarder() {
        return useForwarder;
    }

    public void setUseForwarder(String useForwarder) {
        this.useForwarder = useForwarder;
    }

    public String getForwarderStoreAndForward() {
        return forwarderStoreAndForward;
    }

    public void setForwarderStoreAndForward(String forwarderStoreAndForward) {
        this.forwarderStoreAndForward = forwarderStoreAndForward;
    }

    public List<EmlForwarderVo> getForwarders() {
        return forwarders;
    }

    public void setForwarders(List<EmlForwarderVo> forwarders) {
        this.forwarders = forwarders;
    }

    public String getUseClassifier() {
        return useClassifier;
    }

    public void setUseClassifier(String useClassifier) {
        this.useClassifier = useClassifier;
    }

    public String getUseLabeler() {
        return useLabeler;
    }

    public void setUseLabeler(String useLabeler) {
        this.useLabeler = useLabeler;
    }

    @Override
    public String toString() {
        return "EmlUserConfigVo{" +
                "listDisplayType='" + listDisplayType + '\'' +
                ", listSize=" + listSize +
                ", listPreviewContent='" + listPreviewContent + '\'' +
                ", listColumns=" + listColumns +
                ", readOpenNewWindow='" + readOpenNewWindow + '\'' +
                ", readDisplaySingleLineSubject='" + readDisplaySingleLineSubject + '\'' +
                ", readDisplayAllRecipients='" + readDisplayAllRecipients + '\'' +
                ", writeOpenNewWindow='" + writeOpenNewWindow + '\'' +
                ", writeDisplayBCC='" + writeDisplayBCC + '\'' +
                ", writeIncludeFooter='" + writeIncludeFooter + '\'' +
                ", writeIncludeFooterWhenReply='" + writeIncludeFooterWhenReply + '\'' +
                ", writePreferredSender='" + writePreferredSender + '\'' +
                ", writeReadConfirmMail='" + writeReadConfirmMail + '\'' +
                ", writeReceiveReadConfirmMail='" + writeReceiveReadConfirmMail + '\'' +
                ", writeSendConfirmMailType='" + writeSendConfirmMailType + '\'' +
                ", sendPreferredAfterPage='" + sendPreferredAfterPage + '\'' +
                ", useFooter='" + useFooter + '\'' +
                ", footer='" + footer + '\'' +
                ", footers=" + footers +
                ", useAbsence='" + useAbsence + '\'' +
                ", useBlocker='" + useBlocker + '\'' +
                ", blockers=" + blockers +
                ", useForwarder='" + useForwarder + '\'' +
                ", forwarders=" + forwarders +
                ", useClassifier='" + useClassifier + '\'' +
                ", useLabeler='" + useLabeler + '\'' +
                '}';
    }
}
