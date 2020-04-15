package nohi.builder;

import lombok.Data;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-04-07 12:11
 **/
@Data
public class Persion {
    private String id;
    private String name;
    private int age;
    private String address;

    public Persion(Builder builder) {
        this.id = builder.getId();
        this.name = builder.getName();
        this.age = builder.getAge();
        this.address = builder.getAddress();
    }
    @Data
    public static class Builder{
        private String id;
        private String name;
        private int age;
        private String address;

        public Builder id(String id){
            this.id = id;
            return this;
        }
        public Builder name(String name){
            this.name = name;
            return this;
        }
        public Builder age(int age){
            this.age = age;
            return this;
        }
        public Builder address(String address){
            this.address = address;
            return this;
        }
        public Persion builder(){
            return new Persion(this);
        }
    }
}
