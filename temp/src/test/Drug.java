package test;

class Drug {

    private String name;
    private String description;

    Drug(String _name, String _description){
    this.name = _name;
    this.description = _description;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


