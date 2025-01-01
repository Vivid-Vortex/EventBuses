package com.event.bus.poc.sink.entity;

public class MyEntity {

    private Long id;
    private String data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MyEntity{" +
                "id=" + id +
                ", data='" + data + '\'' +
                '}';
    }
}
