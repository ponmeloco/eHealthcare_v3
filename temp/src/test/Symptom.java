package test;
class Symptom {

    private String severeness;
    private String name;
    private String description;

    Symptom(String _name, String _description, String _severeness){
        setName(_name);
        setDescription(_description);
        setSevereness(_severeness);
    }

    public String getSevereness() {
        return severeness;
    }

    public void setSevereness(String severeness) {
        this.severeness = severeness;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
