package kz.desh.snowballs.server.entity;

public enum ActionType {
    FREE, PRECISION, STRENGTH, DODGE;

    public ActionType of(String value) {
        switch (value) {
            case "PRECISION":
                return PRECISION;
            case "STRENGTH":
                return STRENGTH;
            case "DODGE":
                return DODGE;
            default:
                return FREE;
        }
    }
}