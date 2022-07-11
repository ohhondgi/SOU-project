package com.SOU.mockServer.common.message;

import com.SOU.mockServer.common.util.Input;
import com.SOU.mockServer.common.util.Output;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class Message {
    private final List<Field> fields;
    public Message() {
        this.fields = new LinkedList<>();
    }

    /**
     * add message field
     * @param field
     */
    protected void addField(Field field) {
        this.fields.add(field);
    }

    public void writeTo(Output out, int offset)
        throws UnsupportedOperationException {

        for (Field field : this.fields) {
            if (field.value instanceof Integer) {
                out.writeInt((Integer) field.value, offset, field.length);
            } else if (field.value instanceof Long) {
                out.writeLong((Long) field.value, offset, field.length);
            } else if (field.value instanceof String) {
                out.writeString((String) field.value, offset, field.length);
            } else if (field.value instanceof Message) {
                ((Message) field.value).writeTo(out, offset);
            } else {
                throw new UnsupportedOperationException("Request only supports : Integer, Long, String");
            }
            offset += field.length;
        }
    }

    public void readFrom(Input input, int offset)
        throws UnsupportedOperationException {

        for (Field field : this.fields) {
            if (field.value instanceof Integer) {
                field.value = input.readInt(offset, field.length);
            } else if (field.value instanceof Long) {
                field.value = input.readLong(offset, field.length);
            } else if (field.value instanceof String) {
                field.value = input.readString(offset, field.length);
            } else if (field.value instanceof Message) {
                ((Message) field.value).readFrom(input, offset);
            } else {
                throw new UnsupportedOperationException("Request only supports : Integer, Long, String");
            }
            offset += field.length;
        }
    }

    public void writeTo(Output out) {
        writeTo(out, 0);
    }
    public void readFrom(Input input) {
        readFrom(input, 0);
    }

    public abstract int getTotalLength();
    public abstract Map<String, Object> toMap();

}

