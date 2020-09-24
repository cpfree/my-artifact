package com.github.cpfniliu.dal.sql.ele;

public enum SqlOperateType {
    EQ("="),
    NQ("<>"),
    GT(">"),
    GE(">="),
    LT("<"),
    LE("<="),
    LIKE("like"),
    IS_NULL("is null"),
    IS_NON("is not null"),
    IN("in"),
    NOT_IN("not in"),
    BETWEEN("between");

    private final String sign;

    SqlOperateType(String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }
}
