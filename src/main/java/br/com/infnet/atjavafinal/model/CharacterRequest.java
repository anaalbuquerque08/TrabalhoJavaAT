package br.com.infnet.atjavafinal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterRequest {
    private String name;
    private Integer age;
    private List<Object> customData;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Object> getCustomData() {
        return customData;
    }

    public void setCustomData(List<Object> customData) {
        this.customData = customData;
    }
}
