package nl.marcusink.mmo.client.model;


import com.google.gson.Gson;

public class Avatar {

    private String name;
    private String profession;
    private String race;
    private String gender;
    private double attack;
    private double strength;
    private double defence;
    private double range;
    private double prayer;
    private double magic;
    private double constitution;
    private double crafting;
    private double mining;
    private double smithing;
    private double fishing;
    private double cooking;
    private double firemaking;
    private double woodcutting;
    private double herblore;
    private double thieving;
    private double fletching;
    private double slayer;
    private double farming;
    private double hunting;

    public String getName() {
        return name;
    }

    public void setCharacterName(String characterName) {
        this.name = characterName;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public double getDefence() {
        return defence;
    }

    public void setDefence(double defence) {
        this.defence = defence;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public double getPrayer() {
        return prayer;
    }

    public void setPrayer(double prayer) {
        this.prayer = prayer;
    }

    public double getMagic() {
        return magic;
    }

    public void setMagic(double magic) {
        this.magic = magic;
    }

    public double getConstitution() {
        return constitution;
    }

    public void setConstitution(double constitution) {
        this.constitution = constitution;
    }

    public double getCrafting() {
        return crafting;
    }

    public void setCrafting(double crafting) {
        this.crafting = crafting;
    }

    public double getMining() {
        return mining;
    }

    public void setMining(double mining) {
        this.mining = mining;
    }

    public double getSmithing() {
        return smithing;
    }

    public void setSmithing(double smithing) {
        this.smithing = smithing;
    }

    public double getFishing() {
        return fishing;
    }

    public void setFishing(double fishing) {
        this.fishing = fishing;
    }

    public double getCooking() {
        return cooking;
    }

    public void setCooking(double cooking) {
        this.cooking = cooking;
    }

    public double getFiremaking() {
        return firemaking;
    }

    public void setFiremaking(double firemaking) {
        this.firemaking = firemaking;
    }

    public double getWoodcutting() {
        return woodcutting;
    }

    public void setWoodcutting(double woodcutting) {
        this.woodcutting = woodcutting;
    }

    public double getHerblore() {
        return herblore;
    }

    public void setHerblore(double herblore) {
        this.herblore = herblore;
    }

    public double getThieving() {
        return thieving;
    }

    public void setThieving(double thieving) {
        this.thieving = thieving;
    }

    public double getFletching() {
        return fletching;
    }

    public void setFletching(double fletching) {
        this.fletching = fletching;
    }

    public double getSlayer() {
        return slayer;
    }

    public void setSlayer(double slayer) {
        this.slayer = slayer;
    }

    public double getFarming() {
        return farming;
    }

    public void setFarming(double farming) {
        this.farming = farming;
    }

    public double getHunting() {
        return hunting;
    }

    public void setHunting(double hunting) {
        this.hunting = hunting;
    }

    public int getSkillLevel(double exp) {
        double x = 0;
        double growth = 1.1040895136738123376495053876233;
        for (int i = 1; i < 99; i++) {
            x = Math.round(x + (75 * Math.pow(growth, i)));

            if (exp < x) {
                return i;
            }
        }
        return 99;
    }


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
