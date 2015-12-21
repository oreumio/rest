package com.oreumio.james.rest.send;

import org.apache.james.mailbox.model.MessageResult;

import java.util.Set;

/**
 * Specifies a fetch group.
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class FetchGroupImpl implements MessageResult.FetchGroup {

    public static final MessageResult.FetchGroup MINIMAL = new FetchGroupImpl(MessageResult.FetchGroup.MINIMAL);

    public static final MessageResult.FetchGroup HEADERS = new FetchGroupImpl(MessageResult.FetchGroup.HEADERS);

    public static final MessageResult.FetchGroup FULL_CONTENT = new FetchGroupImpl(MessageResult.FetchGroup.FULL_CONTENT);

    public static final MessageResult.FetchGroup BODY_CONTENT = new FetchGroupImpl(MessageResult.FetchGroup.BODY_CONTENT);

    private int content = MessageResult.FetchGroup.MINIMAL;

    private Set<PartContentDescriptor> partContentDescriptors;

    public FetchGroupImpl() {
        super();
    }

    public FetchGroupImpl(int content) {
        super();
        this.content = content;
    }

    public FetchGroupImpl(int content, Set<PartContentDescriptor> partContentDescriptors) {
        super();
        this.content = content;
        this.partContentDescriptors = partContentDescriptors;
    }

    public int content() {
        return content;
    }

    public void or(int content) {
        this.content = this.content | content;
    }

    public String toString() {
        return "Fetch " + content;
    }

    /**
     * Gets content descriptors for the parts to be fetched.
     * 
     * @return <code>Set</code> of {@link org.apache.james.mailbox.model.MessageResult.FetchGroup.PartContentDescriptor},
     *         possibly null
     */
    public Set<PartContentDescriptor> getPartContentDescriptors() {
        return partContentDescriptors;
    }
}
