package test;
class Medication {

    private Drug drug;
    private double Dosis;
    private int TimesperDay;



    public Medication(Drug _drug, double _dosis, int _timesperDay) {
        this.drug = _drug;
        Dosis = _dosis;
        TimesperDay = _timesperDay;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public double getDosis() {
        return Dosis;
    }

    public void setDosis(double dosis) {
        Dosis = dosis;
    }

    public int getTimesperDay() {
        return TimesperDay;
    }

    public void setTimesperDay(int timesperDay) {
        TimesperDay = timesperDay;
    }
}
