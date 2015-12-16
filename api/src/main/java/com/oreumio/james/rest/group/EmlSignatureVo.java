package com.oreumio.james.rest.group;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlSignatureVo {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "EmlSignatureVo{" +
                "content='" + content + '\'' +
                '}';
    }
}
