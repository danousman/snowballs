package kz.desh.snowballs.server.entity;

public enum ActionType {
    FREE, STUDY_SKILL;

    public ActionType of(String value) {
        switch (value) {
            case "STUDY_SKILL":
                return STUDY_SKILL;
            default:
                return FREE;
        }
    }
}